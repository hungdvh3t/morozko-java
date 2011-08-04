package util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.handler.EditHandler;
import util.handler.FileHandler;
import util.handler.Util;

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
                	FileHandler.handleFile(request, response, context);
                } else if ( "edit".equalsIgnoreCase( action ) ) {
                	EditHandler.handleEdit(request, response, context);
                }
        	} else {
        		handleUser(request, response, context);
        	}
        } catch(Exception e) {
            PrintWriter pw = Util.startHtml(response);
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
			PrintWriter pw = Util.startHtml(response);
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

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.usePassword = config.getInitParameter( "usePassword" );
		System.out.println( "UtilityServlet startup OK" );
	}
	
}
