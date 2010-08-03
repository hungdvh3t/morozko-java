package org.morozko.java.mod.tools.dbex;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.morozko.java.mod.db.connect.ConnectionFactory;
import org.morozko.java.mod.tools.db.ConnArgs;
import org.morozko.java.mod.tools.util.args.ArgList;
import org.morozko.java.mod.tools.util.args.ArgUtils;

public class QueryExcel {

	private static void output( ConnectionFactory cf, String sql, String file ) throws Exception {
		WritableWorkbook ww = Workbook.createWorkbook( new File( file ) );
		Connection conn = cf.getConnection();
		WritableSheet s = ww.createSheet( "export", 0);
		try {
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery( sql );
			ResultSetMetaData rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			for ( int k=0; k<cols; k++ ) {
				s.addCell( new Label( k, 0, rsmd.getColumnLabel( (k+1) ) ) );
			}
			int rows = 1;
			while ( rs.next() ) {
				for ( int k=0; k<cols; k++ ) {
					String v = String.valueOf( rs.getObject( (k+1) ) );
					s.addCell( new Label( k, rows, v ) );
				}
				rows++;
			}
			rs.close();
			stm.close();
		} catch (Exception e) {
			throw e;
		} finally {
			conn.close();
		}
		ww.write();
		ww.close();
	}
	
	public static void main( String[] args ) {
		try {
			ArgList list = ArgUtils.parseArgs( args );
			ConnectionFactory cf = ConnArgs.createConnectionFactory( list );
			String file = list.findArgValue( "f" );
			String sql = list.findArgValue( "q" );
			output(cf, sql, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
}
