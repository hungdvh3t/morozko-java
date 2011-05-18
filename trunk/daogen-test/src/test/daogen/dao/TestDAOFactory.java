/*
 * @(#)TestDAOFactory.java
 *
 * @project    : Daogen Test
 * @package    : test.daogen.dao
 * @creation   : 18/05/2011 09/52/37
 */
package test.daogen.dao;

import test.daogen.dao.helpers.TestDAOFactoryHelper;

/**
 * <p>Classe TestDAOFactory.</p>
 *
 * @author Matteo a.k.a. Fugerit
 */
public class TestDAOFactory extends TestDAOFactoryHelper {

	private final static long serialVersionUID = 130570515776959L;

    private static TestDAOFactory instance;
    public static TestDAOFactory getInstance() {
        return instance;
    }
    public static void init( org.morozko.java.mod.db.dao.BasicDAOFactory bdf ) {
        instance = new TestDAOFactory( bdf );
    }
    public static void init( org.morozko.java.mod.db.connect.ConnectionFactory cf ) {
        instance = new TestDAOFactory( new org.morozko.java.mod.db.dao.BasicDAOFactory( cf ) );
    }
    public TestDAOFactory(org.morozko.java.mod.db.dao.BasicDAOFactory daoFactory ) {
        super(daoFactory);
    }

}
