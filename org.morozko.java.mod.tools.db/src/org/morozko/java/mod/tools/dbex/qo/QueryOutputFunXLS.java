package org.morozko.java.mod.tools.dbex.qo;

import java.io.OutputStream;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


public class QueryOutputFunXLS extends QueryOutputFun {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4881263802285769359L;

	private WritableWorkbook ww;
	private WritableSheet ws;
	
	@Override
	public void start( OutputStream os ) throws Exception {
		this.ww = Workbook.createWorkbook( os );
		this.ws = this.ww.createSheet( "export", 0 );
	}

	@Override
	public void end() throws Exception {
		this.ww.write();
		this.ww.close();
	}

	@Override
	public void outputRow( String[] row ) throws Exception {
		for ( int k=0; k< row.length; k++ ) {
			this.ws.addCell( new Label( k, this.getCurrentRow(), row[k] ) );
		}
	}

	@Override
	public void flush() throws Exception {
		this.ww.write();
	}

	
	
}
