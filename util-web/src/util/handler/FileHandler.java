package util.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileHandler {

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
	
	public static void handleFile( HttpServletRequest request, HttpServletResponse response, ServletContext context ) throws Exception {
		// percorso da processare
		String path = request.getParameter("path");
		
		// current file ed, eventualmente suo figli
		File currentFile = null;
		File[] listKids = null;
		
		// se il percorso non � impostato, allora parto dalle radici
        if( path == null ) {
        	listKids = File.listRoots();
        // se il percorso � impostato allora uso il parametro
        } else {
        	currentFile = new File( path );
        	if ( currentFile.isDirectory() ) {
        		listKids = currentFile.listFiles();
        	}
        }
			
		if ( listKids != null ) {
			PrintWriter pw = Util.startHtml(response);
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
	
}
