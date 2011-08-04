package util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UtilityServlet
 */
public class UtilityServlet extends HttpServlet {
	
	private static final long serialVersionUID = 133432432432L;
       
	private String usePassword;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UtilityServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        try {
        	String currentUser = (String)request.getSession().getAttribute( "currentUser" );
        	ServletContext context = this.getServletContext();
        	if ( this.usePassword == null || currentUser != null ) {
        		String action = request.getParameter( "action" );
                if ( action == null ) {
                	handleFile(request, response, context);
                } else if ( "edit".equalsIgnoreCase( action ) ) {
                	handleEdit(request, response, context);
                }
        	} else {
        		handleUser(request, response, context);
        	}
        } catch(Exception e) {
            PrintWriter pw = new PrintWriter(response.getWriter());
            pw.println("<pre>");
            e.printStackTrace(pw);
            pw.println("</pre>");
        }
	}

	private void handleUser( HttpServletRequest request, HttpServletResponse response, ServletContext context ) throws Exception {
		String user = request.getParameter( "user" );
		String pass = request.getParameter( "pass" );
		boolean logged = false;
		if ( user != null && pass != null ) {
			if ( pass.equals( this.usePassword ) ) {
				request.getSession().setAttribute( "currentUser" , user );
				RequestDispatcher rd = request.getRequestDispatcher( "/fs" );
				rd.forward( request , response );
				logged = true;
			}
		} 
		if ( !logged ) {
			PrintWriter pw = new PrintWriter(response.getWriter());
			pw.println("<html>");
			pw.println("<body>");
			pw.println("<form method='post' action='fs'>");
			pw.println("Username <input type='text' name='user'>");
			pw.println("Password <input type='password' name='pass'>");
			pw.println("<input type='submit' name='LogIn'>");
			pw.println("</form>");
			pw.println("</body>");
			pw.println("</html>");
		}
	}
	
	private static String fileAtts( File file ) {
		StringBuffer atts = new StringBuffer();
		if ( file.canRead() ) {
			atts.append( "r" );
		}
		if ( file.canWrite() ) {
			atts.append( "w" );
		}
		if ( file.canExecute() ) {
			atts.append( "x" );
		}
		if ( file.isHidden() ) {
			atts.append( "h" );
		}
		return atts.toString();
	}
	
	private static String fileLabel( File file ) throws IOException {
		StringBuffer label = new StringBuffer();
		if ( file.getParentFile() != null ) {
			label.append( file.getName() );
			if ( file.isDirectory() && file.getParentFile() != null ) {
				label.append( File.separator );
			}			
		} else {
			label.append( file.getCanonicalPath() );
		}
		return label.toString();
	}
	
	private static void handleFile( HttpServletRequest request, HttpServletResponse response, ServletContext context ) throws Exception {
		// percorso da processare
		String path = request.getParameter("path");
		
		// current file ed, eventualmente suo figli
		File currentFile = null;
		File[] listKids = null;
		
		// se il percorso non è impostato, allora parto dalle radici
        if( path == null ) {
        	listKids = File.listRoots();
        // se il percorso è impostato allora uso il parametro
        } else {
        	currentFile = new File( path );
        	if ( currentFile.isDirectory() ) {
        		listKids = currentFile.listFiles();
        	}
        }
			
		if ( listKids != null ) {
			PrintWriter pw = new PrintWriter(response.getWriter());
			pw.println("<html>");
			pw.println("<body>");
			
			if ( currentFile == null ) {
				pw.println( "<h3>No path provided, listing file system roots : </h3>" );
			} else {
				String back = null;
				if ( currentFile.getParentFile() != null ) {
					back = "<a href='fs?path="+currentFile.getParentFile().getCanonicalPath()+"'>(..)</a>" ;	
				} else {
					back = "<a href='fs'>(..)</a></p>" ;
				}
				pw.println( "<h3>Directory listing for : "+currentFile.getCanonicalPath()+" "+back+"</h3>" );
			}
			
			String tdSyle = " style='border: 1px solid black;' ";
			
			pw.println("<table style='width: 100%; border: 1px solid black;'>");
			pw.println("<tr>");
			pw.println("<th"+tdSyle+">Name</th>");
			pw.println("<th"+tdSyle+">Size</th>");
			pw.println("<th"+tdSyle+">Attributes</th>");
			pw.println("<th"+tdSyle+">Last modified</th>");
			pw.println("<th"+tdSyle+">Actions</th>");
			pw.println("</tr>");
			
			for (int k = 0; k < listKids.length; k++) {
				File current = listKids[k];
				pw.println("<tr>");
				pw.println("<td"+tdSyle+"><a href='fs?path="+current.getCanonicalPath()+"'>"+fileLabel( current )+"</a></td>");
				pw.println("<td"+tdSyle+" align='right'>"+current.length()+"</td>");
				pw.println("<td"+tdSyle+">"+fileAtts( current )+"</td>");
				pw.println("<td"+tdSyle+" align='right'>"+new Timestamp( current.lastModified() )+"</td>");
				pw.println("<td"+tdSyle+">&nbsp;" );
				pw.println("<a target='editFrame' href='fs?action=edit&path="+current.getCanonicalPath()+"'>Edit</a>" );
				pw.println("</td>" );
				pw.println("</tr>");
			}
			
			pw.println("</table>");
			pw.println("</body>");
			pw.println("</html>");
		} else {
			String contentDisposition = "attachment; filename="+currentFile.getName();
			response.addHeader("Content-Disposition", contentDisposition);
			byte buffer[] = new byte[1024];
			FileInputStream fis = new FileInputStream(currentFile);
			OutputStream os = response.getOutputStream();
			for ( int read = fis.read(buffer); read > 0; read = fis.read(buffer) ) {
				os.write( buffer, 0, read );
			}
			fis.close();
		}
	}
	
	private static void handleEdit( HttpServletRequest request, HttpServletResponse response, ServletContext context ) throws Exception {
		
		String path = request.getParameter( "path" );
		File currentFile = new File( path );
		String fileContent = null;
		
		if ( request.getParameter( "save" ) != null ) {
			fileContent = request.getParameter( "fileContent" );
			FileOutputStream fos = new FileOutputStream( currentFile );
			fos.write( fileContent.getBytes() );
			fos.close();
		} else {
			
			byte buffer[] = new byte[1024];
			FileInputStream fis = new FileInputStream(currentFile);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			for ( int read = fis.read(buffer); read > 0; read = fis.read(buffer) ) {
				os.write( buffer, 0, read );
			}
			fis.close();
			fileContent = os.toString();			
		}
		
		PrintWriter pw = new PrintWriter(response.getWriter());
		pw.println("<html>");
		pw.println("<body>");
		pw.println("<form method='post' action='fs'>");
		pw.println("<input type='hidden' name='action' value='edit'>");
		pw.println("<input type='hidden' name='path' value='"+path+"'>");
		pw.println("<textarea cols='120' rows='40' name='fileContent'>"+fileContent+"</textarea>");
		pw.println("<input type='submit' name='save'>");
		pw.println("</form>");
		pw.println("</body>");
		pw.println("</html>");
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.usePassword = config.getInitParameter( "usePassword" );
		System.out.println( "UtilityServlet startup OK" );
	}
	
}
