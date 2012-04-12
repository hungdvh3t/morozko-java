package org.morozko.java.mod.parser.ds.pos;

import java.util.ArrayList;
import java.util.List;

public class PositionalRecordDescription {

	public PositionalRecordDescription() {
		this.fieldDescriptionList = new ArrayList<PositionalFieldDescription>();
	}
	
	public List<PositionalFieldDescription> getFieldDescriptionList() {
		return fieldDescriptionList;
	}

	private int recordLength;
	
	public int getRecordLength() {
		return recordLength;
	}

	public void setRecordLength(int recordLength) {
		this.recordLength = recordLength;
	}

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	private List<PositionalFieldDescription> fieldDescriptionList;
	
}
