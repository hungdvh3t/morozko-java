package org.morozko.java.mod.parser.facade;

import java.util.HashMap;
import java.util.Map;

import org.morozko.java.mod.parser.ds.DataSource;

public class ParserConfig {

	public ParserConfig() {
		this.mapDS = new HashMap<String, DataSource>();
	}
	
	private Map<String, DataSource> mapDS;
	
	public void addDS( String id, DataSource ds ) {
		this.mapDS.put( id, ds );
	}
	
	public DataSource getDS( String id ) {
		return this.mapDS.get( id );
	}
	
}
