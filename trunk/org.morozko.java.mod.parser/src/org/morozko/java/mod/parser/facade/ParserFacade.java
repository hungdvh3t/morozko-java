package org.morozko.java.mod.parser.facade;

import java.io.FileInputStream;

import org.morozko.java.mod.parser.ds.DataSource;
import org.morozko.java.mod.parser.ds.ParserInput;
import org.morozko.java.mod.parser.ds.ParserOutput;
import org.morozko.java.mod.parser.ds.RecordIterator;
import org.morozko.java.mod.parser.model.RecordModel;
import org.morozko.java.mod.tools.util.args.ArgList;
import org.morozko.java.mod.tools.util.args.ArgUtils;

public class ParserFacade {

	public static void main( String[] args ) {
		try {
			
			ArgList argList = ArgUtils.parseArgsDefault( args );
			
			// reading configuration
			String config = argList.findArgValue( "c" );
			FileInputStream fis = new FileInputStream( config );
			ParserConfig parserConfig = ConfigReader.read( fis );
			fis.close();
			
			String dsInParam = argList.findArgValue( "ds-in" );
			String input = argList.findArgValue( "input" );
			String metadata = argList.findArgValue( "metadata" );
			
			
			DataSource inDS = parserConfig.getDS( dsInParam );
			ParserInput parserInput = new ParserInput( input, metadata );
			ParserOutput parserOutput = inDS.parse( parserInput );
			
			RecordIterator ri = parserOutput.getRecords();
			ri.open();
			while ( ri.hasNext() ) {
				RecordModel record = ri.getNext();
			}
			ri.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
