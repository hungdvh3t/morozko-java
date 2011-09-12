package sql.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.morozko.java.core.ent.log.helpers.LogObjectServlet;
import org.morozko.java.core.xml.dom.DOMIO;
import org.morozko.java.core.xml.dom.SearchDOM;
import org.morozko.java.mod.cmd.CMD;
import org.morozko.java.mod.cmd.CMDOutput;
import org.morozko.java.mod.cmd.CMDOutputBean;
import org.morozko.java.mod.db.cmd.sql.SQLCMDUtils;
import org.morozko.java.mod.db.connect.ConnectionFactory;
import org.morozko.java.mod.db.connect.ConnectionFactoryImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Servlet implementation class SqlServlet
 */
public class SqlServlet extends LogObjectServlet {
	
	private static final long serialVersionUID = 1454335435L;
       
    /** 
     * @see LogObjectServlet#LogObjectServlet()
     */
    public SqlServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String query = request.getParameter( "query" );
		if ( query != null ) {
			SqlQuery sqlQuery = this.sqlConfig.getQuery( query );
			try {
				ConnectionFactory cf = null;
				CMD cmd = SQLCMDUtils.newSQLCMD( cf );
				CMDOutput output = cmd.handleCommand( sqlQuery.getSql() );
				request.setAttribute( "output" , new CMDOutputBean( output ) );
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		RequestDispatcher rd = request.getRequestDispatcher( "/index.jsp" );
		rd.forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

	private SqlConfig sqlConfig;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			File configFile = new File( config.getServletContext().getRealPath( "/WEB-INF" ), "sql-config.xml" );
			Document configXml = DOMIO.loadDOMDoc( configFile );
			Element root = configXml.getDocumentElement();
			SearchDOM search = SearchDOM.newInstance( true , true );
			List<Element> listQuery = search.findAllTags( root , "query" );
			Iterator<Element> itQuery = listQuery.iterator();
			SqlConfig sqlConfig = new SqlConfig();
			while ( itQuery.hasNext() ) { 
				Element queryTag = itQuery.next();
				SqlQuery query = new SqlQuery();
				String name = queryTag.getAttribute( "name" );
				String type = queryTag.getAttribute( "type" );
				String description = queryTag.getAttribute( "description" );
				query.setName( name );
				query.setType( type );
				query.setDescription( description );
				Element sqlTag = search.findTag( queryTag , "sql" );
				String sql = search.findText( sqlTag );
				query.setSql( sql );
				sqlConfig.addQuery( query );
			}
			this.sqlConfig = sqlConfig;
			config.getServletContext().setAttribute( "sqlConfig" , sqlConfig );
			this.getLog().info( "INIT END >>>>>>>>>> OK" );
		} catch (Exception e) {
			throw new ServletException( e );
		}
	}

}
