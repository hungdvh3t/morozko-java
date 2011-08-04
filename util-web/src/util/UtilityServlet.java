package util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UtilityServlet
 */
public class UtilityServlet extends HttpServlet {
	
	private static final long serialVersionUID = 133432432432L;
       
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getParameter( "path" );
		File[] list = null;
		if ( path != null ) {
			list = File.listRoots();
		} else {
			File current = new File( path );
			if ( current.isDirectory() ) {
				list = current.listFiles();
			}
		}
		if ( list != null ) {
			PrintWriter pw = new PrintWriter( response.getWriter() );
			pw.println( "<html>" );
			pw.println( "<body>" );
			if ( path == null ) {
				pw.println( "h" );
			} else {
				
			}
			pw.println( "</body>" );
			pw.println( "</html>" );
		}
	}

}
