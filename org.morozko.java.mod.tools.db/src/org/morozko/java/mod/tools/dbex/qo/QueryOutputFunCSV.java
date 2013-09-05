package org.morozko.java.mod.tools.dbex.qo;

import java.io.OutputStream;
import java.io.OutputStreamWriter;

import au.com.bytecode.opencsv.CSVWriter;


public class QueryOutputFunCSV extends QueryOutputFun {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4881263802285769359L;

	private CSVWriter writer;
	
	@Override
	public void start( OutputStream os ) throws Exception {
		OutputStreamWriter streamWriter = new OutputStreamWriter( os );
		this.writer = new CSVWriter( streamWriter , ';' );
	}

	@Override
	public void end() throws Exception {
		this.writer.close();
	}

	@Override
	public void outputRow( String[] row ) throws Exception {
		this.writer.writeNext( row );	
	}

	@Override
	public void flush() throws Exception {
		this.writer.flush();
	}

}
