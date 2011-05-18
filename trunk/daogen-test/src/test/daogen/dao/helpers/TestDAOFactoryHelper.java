/*
 * @(#)TestDAOFactoryHelper.java
 *
 * @project    : Daogen Test
 * @package    : test.daogen.dao.helpers
 * @creation   : 18/05/2011 09/52/37
 */
package test.daogen.dao.helpers;

import test.daogen.dao.TestTableDAO;

/**
 * <p>Dao factory.</p>
 *
 * @author Matteo a.k.a. Fugerit
 */
public class TestDAOFactoryHelper extends org.morozko.java.mod.db.dao.BasicDAOFactory {

	private final static long serialVersionUID = 130570515778541L;

	private TestTableDAO testTableDAO;

	public TestDAOFactoryHelper( org.morozko.java.mod.db.dao.BasicDAOFactory daoFactory ) {
		super(daoFactory.getConnectionFactory(), daoFactory.getFieldFactory());
		this.testTableDAO = new TestTableDAO(daoFactory);
		this.testTableDAO.setModuleDaoFactory(this);
	}

    /** 
     * <p>Restituisce il valore di testTableDAO</p> 
     * 
     * @return      restituisce il valore di testTableDAO
     */ 
    public TestTableDAO getTestTableDAO() {
        return this.testTableDAO;
    }

}
