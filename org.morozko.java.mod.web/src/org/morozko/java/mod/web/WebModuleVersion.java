package org.morozko.java.mod.web;

import org.morozko.java.core.cfg.BaseModuleVersion;

public class WebModuleVersion extends BaseModuleVersion {

	public static final String NAME = "Module Web";
	public static final String VERSION = "1.1.4";
	public static final String DATE = "2011-09-30";
	public static final String DEPENDANCIES = "/org/morozko/java/mod/db/dependancies.properties";
	
	public WebModuleVersion() {
		super( NAME, VERSION, DATE, DEPENDANCIES );
	}

}
