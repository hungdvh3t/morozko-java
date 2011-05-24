package test.daogen;

import java.io.Reader;
import java.util.Date;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import test.daogen.myibatis.dao.TestTableMapper;
import test.daogen.myibatis.model.TestTable;

public class MybatisTest {

	public static void main( String[] args ) {
		try {
			String resource = "test/daogen/myibatis/xml/config.xml";
			Reader reader = Resources.getResourceAsReader( resource );
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build( reader );
			SqlSession sqlSession = sqlSessionFactory.openSession();
			try {
				TestTableMapper testTableMapper = sqlSession.getMapper( TestTableMapper.class );
				TestTable tt = new TestTable();
				tt.setTestDate( new Date() );
				tt.setTestDecimal( new Long( (long)(Math.random()*10000000) ) );
				//tt.setTestId( new Long( 1 )  );
				tt.setTestString( "ciao" );
				tt.setTestInt( new Integer( 2 ) );
				testTableMapper.insert( tt );
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				sqlSession.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
