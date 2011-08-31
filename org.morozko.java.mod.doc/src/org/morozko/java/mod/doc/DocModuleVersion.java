package org.morozko.java.mod.doc;

import org.morozko.java.core.cfg.BaseModuleVersion;

public class DocModuleVersion extends BaseModuleVersion {

	public static final String NAME = "Module Doc";
	public static final String VERSION = "1.1.9";
	public static final String DATE = "2011-08-31";
	public static final String DEPENDANCIES = "/org/morozko/java/mod/doc/dependancies.properties";
	
	public DocModuleVersion() {
		super( NAME, VERSION, DATE, DEPENDANCIES );
	}

}