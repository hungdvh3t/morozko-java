/*
 * @(#)TestTableDAOHelper.java
 *
 * @project    : Daogen Test
 * @package    : test.daogen.dao.helpers
 * @creation   : 18/05/2011 09/52/37
 */
package test.daogen.dao.helpers;

import java.util.List;
import org.morozko.java.core.util.result.PagedResult;
import org.morozko.java.mod.db.dao.PageInfoDB;
import test.daogen.model.TestTableModel;
import org.morozko.java.mod.db.dao.Field;
import org.morozko.java.mod.db.dao.OpDAO;
import org.morozko.java.mod.db.dao.FieldList;
import org.morozko.java.mod.db.dao.LoadResult;
import org.morozko.java.mod.db.dao.DAOException;
import org.morozko.java.mod.db.dao.IdGenerator;

/**
 * <p>Classe per la gestione dell' accesso al DB per oggetti di TestTableModel.</p>
 *
 * @author Matteo a.k.a. Fugerit
 */
public class TestTableDAOHelper extends org.morozko.java.mod.db.dao.BasicDAO {

	private final static long serialVersionUID = 13057051577540L;

    protected static final TestTableModelRSEHelper RSE_MAIN = new TestTableModelRSEHelper();

    protected static final String QUERY_VIEW = "SELECT v.* FROM test_table v";

    protected String queryView;

    protected static final String SQL_UPDATE = "test_table";

    protected String sqlUpdate;


	 public String getQueryViewString() { return QUERY_VIEW; }
	 public String getSqlUpdateString() { return SQL_UPDATE; }

    private TestDAOFactoryHelper moduleDaoFactory;

    /** 
     * <p>Imposta il valore di moduleDaoFactory</p> 
     * 
     * @param      moduleDaoFactory il valore di moduleDaoFactory da impostare
     */ 
    public void setModuleDaoFactory( TestDAOFactoryHelper moduleDaoFactory ) {
        this.moduleDaoFactory = moduleDaoFactory;
    }

    /** 
     * <p>Restituisce il valore di moduleDaoFactory</p> 
     * 
     * @return      restituisce il valore di moduleDaoFactory
     */ 
    public TestDAOFactoryHelper getModuleDaoFactory() {
        return this.moduleDaoFactory;
    }



    protected TestTableModel loadByPkWorker( Object testId  ) throws DAOException { 
        String query = "SELECT * FROM ( "+this.queryView+") v WHERE test_id = ? ";
        FieldList fl = this.newFieldList(); 
        fl.addField( this.getFieldFactory().newField( testId ) ); 
        return ( TestTableModel ) this.loadOne( query, fl );
    } 
    public int deleteByPk( org.morozko.java.mod.db.dao.DAOID testId  ) throws DAOException { 
        String query = "DELETE FROM "+this.sqlUpdate+" WHERE test_id = ? ";
        FieldList fl = this.newFieldList(); 
        fl.addField( this.getFieldFactory().newField( testId ) ); 
        return this.update( query, fl );
    } 
    public OpDAO newInsertOpDAOMysql( TestTableModel model ) throws DAOException { 
       String query = "INSERT INTO "+this.sqlUpdate+" ( test_int, test_date, test_string, test_decimal ) VALUES (  ? , ?, ?, ? )";
       FieldList fl = this.newFieldList();
       fl.addField( this.getFieldFactory().newField( model.getTestInt(), 4 ) );
       fl.addField( model.getTestDate(), 91 );
       fl.addField( model.getTestString(), 12 );
       fl.addField( model.getTestDecimal(), 3 );
       return OpDAO.newUpdateOp( query, fl );
    }
    public int newInsertMysql( TestTableModel model ) throws DAOException { 
       int result = this.update( this.newInsertOpDAO( model ) );
       return result;
    }
    public OpDAO newInsertOpDAO( TestTableModel model ) throws DAOException { 
       String query = "INSERT INTO "+this.sqlUpdate+" ( test_id, test_int, test_date, test_string, test_decimal ) VALUES (  ? , ?, ?, ?, ? )";
       FieldList fl = this.newFieldList();
       fl.addField( this.getFieldFactory().newField( model.getTestId(), -5 ) );
       fl.addField( model.getTestInt(), 4 );
       fl.addField( model.getTestDate(), 91 );
       fl.addField( model.getTestString(), 12 );
       fl.addField( model.getTestDecimal(), 3 );
       return OpDAO.newUpdateOp( query, fl );
    }
    public int insert( TestTableModel model ) throws DAOException { 
       return this.update( this.newInsertOpDAO( model ) );
    }
    public OpDAO newUpdateOpDAO( TestTableModel model ) throws DAOException { 
       String query = "UPDATE "+this.sqlUpdate+" SET test_id=? , test_int = ?, test_date = ?, test_string = ?, test_decimal = ? WHERE test_id=?     ";
       FieldList fl = this.newFieldList();
       fl.addField( this.getFieldFactory().newField( model.getTestId(), -5 ) );
       fl.addField( model.getTestInt(), 4 );
       fl.addField( model.getTestDate(), 91 );
       fl.addField( model.getTestString(), 12 );
       fl.addField( model.getTestDecimal(), 3 );
       fl.addField( model.getTestId(), -2147483648 );
       return OpDAO.newUpdateOp( query, fl );
    }

    public int update( TestTableModel model ) throws DAOException { 
       return this.update( newUpdateOpDAO( model ) );
    }

    public TestTableModel loadOne( String sql, FieldList fl ) throws DAOException {
        return (TestTableModel)loadOne( sql, fl, RSE_MAIN );
    }
    public TestTableModel loadOne( String sql, Field f ) throws DAOException {
        return (TestTableModel)loadOne( sql, f, RSE_MAIN );
    }
    protected void loadAll( List list, String sql, FieldList fl ) throws DAOException {
        this.loadAll( list, sql, fl, RSE_MAIN );
    }
    protected void loadAll( List list, String sql, Field f ) throws DAOException {
        this.loadAll( list, sql, f, RSE_MAIN );
    }
    protected void loadAll( List list, String sql ) throws DAOException {
        this.loadAll( list, sql, this.newFieldList(), RSE_MAIN );
    }
    public void loadAll( List list ) throws DAOException {
        String sql = this.queryView; 
        this.loadAll( list, sql, this.newFieldList(), RSE_MAIN );
    }
    public List loadAll() throws DAOException {
        List list = this.newList();
        this.loadAll( list );
        return list;
    }
    public LoadResult loadAllResult() throws DAOException {
        String sql = this.queryView; 
        return LoadResult.initResult( this, sql, this.newFieldList(), RSE_MAIN ) ;
    }
    public PagedResult loadAllPaged( int perPage, int loadPage ) throws DAOException {
        return this.loadAllPaged( this.queryView, this.newFieldList(), RSE_MAIN, new PageInfoDB( loadPage, perPage ) );
    }
    public PagedResult loadAllPaged( int perPage, int loadPage, String orderBy ) throws DAOException {
        return this.loadAllPaged( this.queryView, this.newFieldList(), RSE_MAIN, new PageInfoDB( loadPage, perPage, orderBy ) );
    }

    public org.morozko.java.mod.db.dao.BasicDAOFactory getMainDAOFactory() {
        return (org.morozko.java.mod.db.dao.BasicDAOFactory)this.getDaoFactory();
    }
    public TestTableDAOHelper(org.morozko.java.mod.db.dao.BasicDAOFactory daoFactory, String queryView, String sqlUpdate ) {
        super(daoFactory);
        this.init(daoFactory);
        this.queryView = queryView;
        this.sqlUpdate = sqlUpdate;
    }
    public TestTableDAOHelper(org.morozko.java.mod.db.dao.BasicDAOFactory daoFactory ) {
        this( daoFactory, QUERY_VIEW, SQL_UPDATE );
    }
    protected void loadAllRelations( List list ) throws DAOException { 
        for ( int k=0; k<list.size(); k++) { 
        	this.loadAllRelations( (TestTableModel)list.get(k) ); 
        } 
    }

	public void loadAllRelations(TestTableModel model) throws DAOException {

    }

	public TestTableModel loadOnePrimary( org.morozko.java.mod.db.dao.DAOID test_id ) throws DAOException { 
		TestTableModel result = null;
		String sql = "SELECT v.* FROM ("+this.queryView+") v WHERE  v.test_id=? ";
		FieldList fl = this.newFieldList();
		fl.addField(test_id);
		result = this.loadOne( sql, fl );
		this.loadAllRelations( result );
		return result;
	}
}
