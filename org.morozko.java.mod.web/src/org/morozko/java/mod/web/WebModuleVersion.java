package org.morozko.java.mod.web;

import org.morozko.java.core.cfg.BaseModuleVersion;

public class WebModuleVersion extends BaseModuleVersion {

	public static final String NAME = "Module Web";
	public static final String VERSION = "1.0.1";
	public static final String DATE = "2011-04-21";
	public static final String DEPENDANCIES = "/org/morozko/java/mod/db/dependancies.properties";
	
	public WebModuleVersion() {
		super( NAME, VERSION, DATE, DEPENDANCIES );
	}

}
