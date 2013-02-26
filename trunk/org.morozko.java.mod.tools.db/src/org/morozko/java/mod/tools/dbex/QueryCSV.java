package org.morozko.java.mod.tools.dbex;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.demo.CSV;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.morozko.java.core.io.FileIO;
import org.morozko.java.core.io.StreamIO;
import org.morozko.java.mod.db.connect.ConnectionFactory;
import org.morozko.java.mod.tools.db.ConnArgs;
import org.morozko.java.mod.tools.util.args.ArgList;
import org.morozko.java.mod.tools.util.args.ArgUtils;

import au.com.bytecode.opencsv.CSVWriter;

public class QueryCSV {

	private static void output( ConnectionFactory cf, String sql, String file ) throws Exception {
		
		FileWriter fw = new FileWriter( file );
		CSVWriter writer = new CSVWriter( fw , ';' );
		Connection conn = cf.getConnection();
		
		try {
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery( sql );
			ResultSetMetaData rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			String[] buffer = new String[ cols ];
			
			for ( int k=0; k<cols; k++ ) {
				buffer[k] = rsmd.getColumnLabel( (k+1) );
			}

			writer.writeNext(buffer);	
			int rows = 1;
			while ( rs.next() ) {
				for ( int k=0; k<cols; k++ ) {
					String v = String.valueOf( rs.getObject( (k+1) ) );
					buffer[k] = v;
				}
				writer.writeNext(buffer);		
				if ( rows % 100000 == 0 ) {
					writer.flush();
					System.out.println( "ROW COUNT > "+rows );
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
		writer.flush();
		writer.close();
	}
	
	public static void main( String[] args ) {
		try {
			ArgList list = ArgUtils.parseArgs( args );
			ConnectionFactory cf = ConnArgs.createConnectionFactory( list );
			String file = list.findArgValue( "f" );
			String sql = list.findArgValue( "q" );
			if ( sql == null ) {
				sql = FileIO.readString( list.findArgValue( "qf" ) );
			}
			System.out.println( "SQL > "+sql );
			output(cf, sql, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
}
