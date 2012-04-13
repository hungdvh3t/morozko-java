package org.morozko.java.mod.parser.ds;

public class RenderInput {

	private RecordIterator records;

	public RecordIterator getRecords() {
		return records;
	}

	public RenderInput(RecordIterator records) {
		super();
		this.records = records;
	}

	public void setRecords(RecordIterator records) {
		this.records = records;
	}
	
}
