package org.morozko.java.mod.codegen;

import java.util.HashMap;

import org.morozko.java.core.xml.config.PropertyXML;
import org.morozko.java.mod.daogen.gen.config.DGConfig;

public class NavMap extends NavNode {

	public NavMap() {
		this.formMap = new HashMap<String, NavForm>();
	}
	
	public HashMap<String, NavForm> getFormMap() {
		return formMap;
	}

	private HashMap<String, NavForm> formMap;
	
	private PropertyXML generalProps;

	public PropertyXML getGeneralProps() {
		return generalProps;
	}

	public void setGeneralProps(PropertyXML generalProps) {
		this.generalProps = generalProps;
	}
	
	private DGConfig daogen;

	public DGConfig getDaogen() {
		return daogen;
	}

	public void setDaogen(DGConfig daogen) {
		this.daogen = daogen;
	}
	
}
