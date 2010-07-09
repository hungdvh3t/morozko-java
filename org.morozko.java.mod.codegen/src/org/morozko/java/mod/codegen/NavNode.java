package org.morozko.java.mod.codegen;

import java.util.ArrayList;
import java.util.List;

public class NavNode {

	public NavNode() {
		this.kids = new ArrayList<NavNode>();
	}
	
	public List<NavNode> getKids() {
		return kids;
	}

	private String name;
	
	private String module;
	
	private String description;
	
	private String facade;
	
	private String overwrite;
	
	private String operation;
	
	private String jspInclude;
	
	private boolean formValidate;
	
	private String formInput;
	
	private String auth;
	
	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public boolean isFormValidate() {
		return formValidate;
	}

	public void setFormValidate(boolean formValidate) {
		this.formValidate = formValidate;
	}

	public String getFormInput() {
		return formInput;
	}

	public void setFormInput(String formInput) {
		this.formInput = formInput;
	}

	public String getJspInclude() {
		return jspInclude;
	}

	public void setJspInclude(String jspInclude) {
		this.jspInclude = jspInclude;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	private NavForm form;

	public NavForm getForm() {
		return form;
	}

	public void setForm(NavForm form) {
		this.form = form;
	}

	public String getOverwrite() {
		return overwrite;
	}

	public void setOverwrite(String overwrite) {
		this.overwrite = overwrite;
	}

	public String getFacade() {
		return facade;
	}

	public void setFacade(String facade) {
		this.facade = facade;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
	
	private List<NavNode> kids;
	
}
