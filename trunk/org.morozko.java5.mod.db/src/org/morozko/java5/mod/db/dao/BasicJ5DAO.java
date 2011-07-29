package org.morozko.java5.mod.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.morozko.java.mod.db.connect.ConnectionFactory;
import org.morozko.java.mod.db.dao.BasicDAO;
import org.morozko.java.mod.db.dao.BasicDAOFactory;
import org.morozko.java.mod.db.dao.BufferedDAO;
import org.morozko.java.mod.db.dao.DAOException;
import org.morozko.java.mod.db.dao.DAOID;
import org.morozko.java.mod.db.dao.Field;
import org.morozko.java.mod.db.dao.FieldFactory;
import org.morozko.java.mod.db.dao.FieldList;
import org.morozko.java.mod.db.dao.LoadResult;
import org.morozko.java.mod.db.dao.OpDAO;
import org.morozko.java.mod.db.dao.RSExtractor;

public class BasicJ5DAO extends BasicDAO {

    protected BasicJ5DAO(BasicDAOFactory daoFactory) {
        super( daoFactory );
    }
    
    protected BasicJ5DAO(ConnectionFactory connectionFactory) {
        super( connectionFactory );
    }    
	
	public BufferedDAO newBufferedDAO( int commitOn ) {
		return new BufferedDAO( this, commitOn );
	}
	
	public BufferedDAO newBufferedDAO() {
		return new BufferedDAO( this );
	}
	
	protected void extractAllJ5( ResultSet rs, List<Object> list, RSExtractor rse ) throws DAOException {
		try {
			while ( rs.next() ) {
				list.add( rse.extractNext( rs ) );
			}
		} catch (SQLException e) {
			throw ( new DAOException( e ) );
		}
	}

	public FieldList newFieldList( Field field ) {
		return new FieldList( this.getFieldFactory(), field );
	}
	
	public FieldList newFieldList() {
		return new FieldList( this.getFieldFactory() );
	}
	
	public List<Object> newListJ5() {
		return new ArrayList<Object>();
	}	
	
	private int update( OpDAO op, Connection conn ) throws SQLException {
		int result = 0;
		PreparedStatement pstm = conn.prepareStatement( op.getSql() );
		this.setAll( pstm, op.getFieldList() );
		result = pstm.executeUpdate();
		return result;
	}		

	public boolean updateTransactionJ5( List<OpDAO> opList ) throws DAOException {
		boolean result = true;
		Connection conn = this.getConnection();
		try {
			conn.setAutoCommit( false );
			int tmp = 0;
			for (int k=0; k<opList.size(); k++) {
				OpDAO currentOp = (OpDAO)opList.get( k );
				if (currentOp.getType()==OpDAO.TYPE_UPDATE) {
					tmp+= this.update( currentOp, conn );
				}
			}
			this.getLog().debug( "result : "+tmp+" / "+opList.size() );
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				this.getLog().error( "Errore durante il rollback : ", e );
			}
			throw (new DAOException(e));
		} finally {
			try {
				conn.setAutoCommit( true );
			} catch (SQLException e) {
				this.getLog().error( "Errore durante deallocazione connessione [conn.setAutoCommit( true )]", e );
			} finally {
				this.close( conn );
			}
		}
		return result;
	}

    public void init(BasicDAOFactory daoFactory ) {
        
    }
    
	public RSExtractor RSE_LONG = new RSExtractor() {
		/* (non-Javadoc) 
		 * @see it.finanze.secin.shared.dao.RSExtractor#extractNext(java.sql.ResultSet)
		 */
		public Object extractNext(ResultSet rs) throws SQLException {
			return new Long( rs.getLong( 1 ) );
		}
    
	};    
	
	public RSExtractor RSE_DAOID = new RSExtractor() {
		/* (non-Javadoc) 
		 * @see it.finanze.secin.shared.dao.RSExtractor#extractNext(java.sql.ResultSet)
		 */
		public Object extractNext(ResultSet rs) throws SQLException {
			return new DAOID( rs.getLong( 1 ) );
		}
    
	};   	
    
	public RSExtractor RSE_INT = new RSExtractor() {
		/* (non-Javadoc) 
		 * @see it.finanze.secin.shared.dao.RSExtractor#extractNext(java.sql.ResultSet)
		 */
		public Object extractNext(ResultSet rs) throws SQLException {
			return new Integer( rs.getInt( 1 ) );
		}
    
	};
        
    
    public RSExtractor RSE_STRING = new RSExtractor() {
        /* (non-Javadoc) 
         * @see it.finanze.secin.shared.dao.RSExtractor#extractNext(java.sql.ResultSet)
         */
        public Object extractNext(ResultSet rs) throws SQLException {
            return rs.getString(1);
        }
    
    };
    
    private static final FieldList NO_FIELDS = new FieldList( null );
    
    private BasicDAOFactory daoFactory;
    

    
    /**
     * <p>Restituisce il valore di daoFactory.</p>
     *
     * @return il valore di daoFactory.
     */
    protected BasicDAOFactory getDaoFactory() {
        return daoFactory;
    }
    
    protected FieldFactory getFieldFactory() {
        return this.getDaoFactory().getFieldFactory();
    }
    
    protected Connection getConnection() throws DAOException {
        return this.getDaoFactory().getConnection();
    }
    
    protected void close(Connection conn) throws DAOException {
        try {
            conn.close();
        } catch (SQLException e) {
            throw (new DAOException(e));
        }
    }
    
    protected void setAll(PreparedStatement ps, FieldList fields) throws SQLException {
    	this.getLog().debug( "Total Param Number : "+fields.size() );
    	int np = 0;
    	int k = 0;
		while ( k<fields.size() ) {
			np++;
			int param = (k+1);
			Field f = fields.getField(k);
			this.getLog().debug( "Setting param n. "+param+", value: "+f.toString()+"(fl.size:"+fields.size()+")" );
			f.setField(ps, param);
			k++;
			this.getLog().debug( "test : "+(k<fields.size())+" k:"+k+" fields.size:"+fields.size() );
		}    		
    	this.getLog().debug( "Total param set : "+np );
    }

    protected boolean execute(String query, FieldList fields) throws DAOException {
		this.getLog().debug( "execute: query 1 '"+query+"'" );
		query = this.queryFormat( query );
		this.getLog().debug( "execute: query 2 '"+query+"'" );	    	
        boolean result = false;
        Connection conn = this.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement( query );
            this.setAll(ps, fields);
            result = ps.execute();
            ps.close();
        } catch (SQLException e) {
            throw (new DAOException(e));
        } finally {
            this.close( conn );
        }
        return result;
    }
    
	protected boolean execute(String query) throws DAOException {
		this.getLog().debug( "execute: query 1 '"+query+"'" );
		query = this.queryFormat( query );
		this.getLog().debug( "execute: query 2 '"+query+"'" );		
		   boolean result = false;
		   Connection conn = this.getConnection();
		   try {
			   PreparedStatement ps = conn.prepareStatement( query );
			   result = ps.execute();
			   ps.close();
		   } catch (SQLException e) {
			   throw (new DAOException(e));
		   } finally {
			   this.close( conn );
		   }
		   return result;
	   }
    
	protected int update(OpDAO op) throws DAOException {
		return this.update( op.getSql(), op.getFieldList() );
	}
    
    protected int update(String query, Field field) throws DAOException {
        return this.update( query, this.newFieldList( field ) );
    }
    
    protected int update(String query) throws DAOException {
        return this.update( query, this.newFieldList() );
    }
    
    protected int update(String query, FieldList fields) throws DAOException {
		this.getLog().debug( "update: query 1 '"+query+"'" );
		query = this.queryFormat( query );
		this.getLog().debug( "update: query 2 '"+query+"'" );
        int result = 0;
        Connection conn = this.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement( query );
            this.setAll(ps, fields);
            result = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw (new DAOException(e));
        } finally {
            this.close( conn );
        }
        this.getLog().debug( "update: result '"+result+"'" );
        return result;
    }



	protected int delete(String query, FieldList fields) throws DAOException {
		this.getLog().debug( "delete: query 1 '"+query+"'" );
		query = this.queryFormat( query );
		this.getLog().debug( "delete: query 2 '"+query+"'" );
		int result = 0;
		Connection conn = this.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement( query );
			this.setAll(ps, fields);
			result = ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw (new DAOException(e));
		} finally {
			this.close( conn );
		}
		return result;
	}




    protected void loadAllJ5(List<Object> l, String query, Field f, RSExtractor re) throws DAOException {
        this.loadAll(l, query, this.newFieldList(f), re);
    }    
    
    protected List<Object> loadAllJ5(String query, Field f, RSExtractor re) throws DAOException {
        return this.loadAllJ5(query, this.newFieldList(f), re);
    }    
    
    protected Object loadOne(String query, Field f,  RSExtractor re) throws DAOException {
        return this.loadOne(query, this.newFieldList(f), re);
    }

    protected void loadAllJ5(List<Object> l, String query, RSExtractor re) throws DAOException {
        this.loadAllJ5(l, query, NO_FIELDS, re);
    }        
    
    
	protected void loadAllJ5(List<Object> l, String query, RSExtractor re, boolean emptyFirst) throws DAOException {
		this.loadAllJ5(l, query, NO_FIELDS, re, emptyFirst);
	}        
    
    
    
    protected List<Object> loadAllJ5(String query, RSExtractor re) throws DAOException {
        return this.loadAllJ5(query, NO_FIELDS, re);
    }    
    
    protected Object loadOne(String query, RSExtractor re) throws DAOException {
        return this.loadOne(query, NO_FIELDS, re);
    }
    
    protected Object loadOne(String query, FieldList fields, RSExtractor re) throws DAOException {
	    this.getLog().debug("loadOne START ");
		this.getLog().debug("loadOne Query 1       : '"+query+"'");
		query = this.queryFormat( query );
		this.getLog().debug("loadOne Query 2       : '"+query+"'");
        this.getLog().debug("loadOne fields        : '"+fields.size()+"'");
        this.getLog().debug("loadOne RSExtractor   : '"+re+"'");    	
        Object result = null;
        Connection conn = this.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement( query );
            this.setAll(ps, fields);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result =  re.extractNext( rs );
            } 
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw (new DAOException(e));
        } finally {
            this.close( conn );
        }
        this.getLog().debug("loadOne END : "+result );
        return result;
    }
    
    protected List<Object> loadAllJ5(String query, FieldList fields, RSExtractor re) throws DAOException {
        List<Object> l = new ArrayList<Object>();
        this.loadAll(l, query, fields, re);
        return l;
    }
    
    protected LoadResult loadAllResult( String query, FieldList fields, RSExtractor re) throws DAOException {
	   return LoadResult.initResult( this , query, fields, re );

    }    
    
    protected void loadAllJ5(List<Object> l, String query, FieldList fields, RSExtractor re) throws DAOException {
	    this.getLog().debug("loadAll START list : '"+l.size()+"'");
		this.getLog().debug("loadAll Query 1       : '"+query+"'");
		query = this.queryFormat( query );
		this.getLog().debug("loadAll Query 2       : '"+query+"'");
        this.getLog().debug("loadAll fields        : '"+fields.size()+"'");
        this.getLog().debug("loadAll RSExtractor   : '"+re+"'");
        Connection conn = this.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement( query );
            this.setAll(ps, fields);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                l.add( re.extractNext( rs ) );
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw (new DAOException(e));
        } finally {
            this.close( conn );
        }
        this.getLog().debug("loadAll END list : '"+l.size()+"'");

    }


	protected void loadAllJ5(List<Object> l, String query, FieldList fields, RSExtractor re, boolean emptyFirst) throws DAOException {
			this.getLog().debug("loadAll START list : '"+l.size()+"'");
			this.getLog().debug("loadAll Query 1       : '"+query+"'");
			query = this.queryFormat( query );
			this.getLog().debug("loadAll Query 2       : '"+query+"'");
			this.getLog().debug("loadAll fields        : '"+fields.size()+"'");
			this.getLog().debug("loadAll RSExtractor   : '"+re+"'");
			Connection conn = this.getConnection();
			try {
				PreparedStatement ps = conn.prepareStatement( query );
				this.setAll(ps, fields);
				ResultSet rs = ps.executeQuery();
				int i=0;
				while (rs.next()) {
					l.add( re.extractNext( rs ) );
					i++;
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				throw (new DAOException(e));
			} finally {
				this.close( conn );
			}
			this.getLog().debug("loadAll END list : '"+l.size()+"'");
		}


	protected String queryFormat( String sql ) {
		MessageFormat f = new MessageFormat( sql );
		return f.format( this.getDaoFactory().getSqlArgs() );
	}

    protected String loadString(String query, Field field) throws DAOException {
        return (String)this.loadOne( query, field, RSE_STRING );
    }
    
    protected String loadString(String query, FieldList fields) throws DAOException {
        return (String)this.loadOne( query, fields, RSE_STRING );
    }
	
	
}
