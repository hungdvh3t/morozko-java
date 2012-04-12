package org.morozko.java.mod.parser.ds.pos;

import java.util.ArrayList;
import java.util.List;

public class PositionalMetadata {

	public PositionalMetadata() {
		this.recordDescriptionList = new ArrayList<PositionalRecordDescription>();
	}
	
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	private List<PositionalRecordDescription> recordDescriptionList;

	public List<PositionalRecordDescription> getRecordDescriptionList() {
		return recordDescriptionList;
	}
	
}
