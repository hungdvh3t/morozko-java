/*
 * @(#)TestTableModelRSEHelper.java
 *
 * @project    : Daogen Test
 * @package    : test.daogen.dao.helpers
 * @creation   : 18/05/2011 09/52/37
 */
package test.daogen.dao.helpers;

/**
 * <p>Classe per l' estrazione di oggetti TestTableModel da un ResultSet.</p>
 *
 * @author Matteo a.k.a. Fugerit
 */
import test.daogen.model.TestTableModel;
import java.sql.SQLException;
import java.sql.ResultSet;

public class TestTableModelRSEHelper implements org.morozko.java.mod.db.dao.RSExtractor {

	private final static long serialVersionUID = 130570515772346L;

    public Object extractNext(ResultSet rs) throws SQLException {
        TestTableModel model = new TestTableModel();
        model.setTestId( org.morozko.java.mod.db.dao.DAOID.valueOf( rs.getLong("test_id") ) );
        model.setTestInt( new Integer( rs.getInt("test_int") ) );
        model.setTestDate( rs.getDate("test_date") );
        model.setTestString( rs.getString("test_string") );
        model.setTestDecimal( rs.getBigDecimal("test_decimal") );
        return model;
    }

}
