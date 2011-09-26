package org.morozko.java.mod.web.servlet.sql;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.morozko.java.core.ent.log.helpers.LogObjectServlet;
import org.morozko.java.core.ent.servlet.context.ContextHelper;
import org.morozko.java.core.xml.dom.DOMIO;
import org.morozko.java.core.xml.dom.DOMUtils;
import org.morozko.java.core.xml.dom.SearchDOM;
import org.morozko.java.mod.db.connect.ConnectionFactory;
import org.morozko.java.mod.db.connect.ConnectionFactoryImpl;
import org.morozko.java.mod.db.dao.BasicDAOFactory;
import org.morozko.java.mod.db.dao.GenericDAO;
import org.morozko.java.mod.web.servlet.sql.query.Result;
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
		String update = request.getParameter( "update" );
		List messageList = new ArrayList();
		if ( query != null ) {
			SqlQuery sqlQuery = this.sqlConfig.getQuery( query );
			try {
				ConnectionFactory cf = this.sqlConfig.getConnectionFactory();
				if ( update != null ) {
					String updateName = request.getParameter( "name" );
					String updateKey = request.getParameter( "key" );
					String updateValue = request.getParameter( "value" );
					SqlUpdate sqlUpdate = (SqlUpdate)sqlQuery.getUpdateMap().get( updateName );
					StringBuffer buffer = new StringBuffer();
					buffer.append( "update " );
					buffer.append( sqlUpdate.getTable() );
					buffer.append( " set " );
					buffer.append( sqlUpdate.getColumn() );
					buffer.append( " = " );
					buffer.append( updateValue );
					buffer.append( " WHERE " );
					buffer.append( sqlQuery.getKey() );
					buffer.append( " = " );	
					buffer.append( updateKey );
					this.getLog().info( "buffer : "+buffer );
					UpdateDAO dao = new UpdateDAO( cf );
					if ( dao.update( buffer.toString() ) > 0 ) {
						messageList.add( "OK" );
					}
				}
				Result result = Result.query(cf, sqlQuery);
				request.setAttribute( "output" , result );
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		request.setAttribute( "messageList" , messageList );
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
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			String configParam = config.getInitParameter( "sql-config" );
			this.sqlConfig = SqlConfig.configure(configParam, config.getServletContext());
		} catch (Exception e) {
			throw new ServletException( e );
		}
	}

}

class UpdateDAO extends GenericDAO {

	public UpdateDAO( ConnectionFactory cf ) {
		super( new BasicDAOFactory( cf ) );
	}

}