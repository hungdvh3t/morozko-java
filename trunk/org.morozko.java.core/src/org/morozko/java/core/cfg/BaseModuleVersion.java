package org.morozko.java.core.cfg;

import java.io.IOException;
import java.util.Properties;

import org.morozko.java.core.util.PropsIO;

public class BaseModuleVersion implements ModuleVersion {

	private Properties dependancies;
	
	private String date;
	
	private String version;
	
	private String name;
	
	public String getDate() {
		return this.date;
	}

	public Properties getDependancies() {
		return this.dependancies;
	}

	private Properties loadSafe( String cs ) {
		Properties props = null;
		try {
			props = PropsIO.loadFromClassLoader( cs );
		} catch (IOException e) {
			throw new RuntimeException( e );
		}
		return props;
	}
	
	public BaseModuleVersion( String name,
			String version, String date, String dependanciesCS ) {
		this( name, version, date, (Properties)null );
		this.dependancies = loadSafe( dependanciesCS );
	}
	
	public BaseModuleVersion( String name,
			String version, String date, Properties dependancies ) {
		super();
		this.dependancies = dependancies;
		this.date = date;
		this.version = version;
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public String getVersion() {
		return this.version;
	}

}
