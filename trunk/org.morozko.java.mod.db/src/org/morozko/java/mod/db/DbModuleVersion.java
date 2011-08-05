package org.morozko.java.mod.db;

import org.morozko.java.core.cfg.BaseModuleVersion;

public class DbModuleVersion extends BaseModuleVersion {

	public static final String NAME = "Module Db";
	public static final String VERSION = "1.0.2";
	public static final String DATE = "2011-08-05";
	public static final String DEPENDANCIES = "/org/morozko/java/mod/db/dependancies.properties";
	
	public DbModuleVersion() {
		super( NAME, VERSION, DATE, DEPENDANCIES );
	}
	
}
