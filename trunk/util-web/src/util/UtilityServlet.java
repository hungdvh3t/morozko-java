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
import util.handler.SecurityHandler;
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
        		SecurityHandler.handleUser(request, response, context, this.usePassword);
        	}
        } catch(Exception e) {
            PrintWriter pw = Util.startHtml(response);
            pw.println("<pre>");
            e.printStackTrace(pw);
            pw.println("</pre>");
        }
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.usePassword = config.getInitParameter( "usePassword" );
		System.out.println( "UtilityServlet startup OK" );
	}
	
}
