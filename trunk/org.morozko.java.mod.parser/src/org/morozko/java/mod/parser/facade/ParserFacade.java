package org.morozko.java.mod.parser.facade;

import java.io.FileInputStream;

import org.morozko.java.mod.tools.util.args.ArgList;
import org.morozko.java.mod.tools.util.args.ArgUtils;

public class ParserFacade {

	public static void main( String[] args ) {
		try {
			
			ArgList argList = ArgUtils.parseArgsDefault( args );
			
			String config = argList.findArgValue( "c" );
			FileInputStream fis = new FileInputStream( config );
			
			ParserConfig parserConfig = ConfigReader.read( fis );
			
			fis.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
