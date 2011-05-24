package test.daogen;

import java.math.BigDecimal;
import java.sql.Date;

import org.morozko.java.mod.db.connect.ConnectionFactory;
import org.morozko.java.mod.db.connect.ConnectionFactoryImpl;

import test.daogen.dao.TestDAOFactory;
import test.daogen.model.TestTableModel;

public class DaogenTest {

	public static void main( String[] args ) {
		try {
			ConnectionFactory cf = ConnectionFactoryImpl.newInstance( 
					"com.mysql.jdbc.Driver",
					"jdbc:mysql://localhost:3306/fugerit",
					"fugerit",
					"fugerit" );
			TestDAOFactory.init( cf );
			TestDAOFactory tdf = TestDAOFactory.getInstance();
			try {
				TestTableModel tt = new TestTableModel();
				tt.setTestDate( new Date( System.currentTimeMillis() ) );
				tt.setTestDecimal( new BigDecimal( (long)(Math.random()*10000000) ) );
				//tt.setTestId( new Long( 1 )  );
				tt.setTestString( "ciao" );
				tt.setTestInt( new Integer( 2 ) );
				tdf.getTestTableDAO().insert( tt );
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
