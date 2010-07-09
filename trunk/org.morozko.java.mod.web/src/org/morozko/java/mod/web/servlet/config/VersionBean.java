package org.morozko.java.mod.web.servlet.config;

public class VersionBean {

	private String appName;
	
	private String appVersion;
	
	private String appDate;
	
	private String lastStartup;

	public VersionBean(String appName, String appVersion, String appDate,
			String lastStartup) {
		super();
		this.appName = appName;
		this.appVersion = appVersion;
		this.appDate = appDate;
		this.lastStartup = lastStartup;
	}

	public String getAppName() {
		return appName;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public String getAppDate() {
		return appDate;
	}

	public String getLastStartup() {
		return lastStartup;
	}
	
}
