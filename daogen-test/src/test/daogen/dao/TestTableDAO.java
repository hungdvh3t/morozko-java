/*
 * @(#)TestTableDAO.java
 *
 * @project    : Daogen Test
 * @package    : test.daogen.dao
 * @creation   : 18/05/2011 09/52/37
 */
package test.daogen.dao;

import test.daogen.dao.helpers.TestTableDAOHelper;

/**
 * <p>Classe TestTableDAO.</p>
 *
 * @author Matteo a.k.a. Fugerit
 */
public class TestTableDAO extends TestTableDAOHelper {

	private final static long serialVersionUID = 130570515776986L;

    public TestTableDAO(org.morozko.java.mod.db.dao.BasicDAOFactory daoFactory ) {
        super(daoFactory);
        this.init(daoFactory);
    }

}
