package org.morozko.java.mod.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.text.html.HTMLDocument.HTMLReader.PreAction;

import org.morozko.java.core.log.BasicLogObject;
import org.morozko.java.mod.db.connect.ConnectionFactory;

public class LoadResult extends BasicLogObject {

	private LoadResult(RSExtractor re, FieldList fields, String query, BasicDAO dao) {
		super();
		this.re = re;
		this.fields = fields;
		this.query = query;
		this.basicDAO = dao;
	}

	public static LoadResult initResult( BasicDAO basicDAO, String query, FieldList fields, RSExtractor re ) {
		LoadResult loadResult = new LoadResult( re, fields, query, basicDAO );
		return loadResult;
	}
	
	private RSExtractor re;
	
	private FieldList fields;
	
	private String query;
	
	private BasicDAO basicDAO;
	
	private Connection conn;
	
	private PreparedStatement ps;
	
	private ResultSet rs;
	
    public long startCount() throws DAOException {
    	long count = 0;
	    this.getLog().debug("start START");
		query = this.basicDAO.queryFormat( query, "LoadResult.startCount" );
        this.getLog().debug("start fields        : '"+fields.size()+"'");
        this.getLog().debug("start RSExtractor   : '"+re+"'");
        Connection conn = this.basicDAO.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement( "SELECT count(*) FROM ( "+query+" ) tmp" );
            this.basicDAO.setAll(ps, fields);
            ResultSet rs = ps.executeQuery();
            if ( rs.next() ) {
            	count = rs.getLong( 1 );
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw (new DAOException(e));
        }
        this.getLog().debug("start END" );
        start();
        return count;
    } 
	
    public void start() throws DAOException {
	    this.getLog().debug("start START");
		query = this.basicDAO.queryFormat( query, "LoadResult.start" );
        this.getLog().debug("start fields        : '"+fields.size()+"'");
        this.getLog().debug("start RSExtractor   : '"+re+"'");
        this.conn = this.basicDAO.getConnection();
        try {
            this.ps = conn.prepareStatement( query );
            this.basicDAO.setAll(ps, fields);
            this.rs = ps.executeQuery();
        } catch (SQLException e) {
            throw (new DAOException(e));
        }
        this.getLog().debug("start END" );
    } 
    
    public boolean hasNext() throws DAOException {
    	boolean result = false;
    	try {
			result = this.rs.next();
		} catch (SQLException e) {
			throw ( new DAOException( e ) );
		}
		return result;
    }
    
    public Object getNext() throws DAOException {
    	Object result = null;
    	try {
			result = this.re.extractNext( rs );
		} catch (SQLException e) {
			throw ( new DAOException( e ) );
		}
		return result;
    }
    
    public void end() throws DAOException {
    	try {
    		this.rs.close();
    		this.ps.close();
			this.conn.close();
		} catch (SQLException e) {
			throw ( new DAOException( e ) );
		}
    }
    
	
}
