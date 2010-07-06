package org.morozko.java.core.util.result;

public class PageInfo {

	private int number;
	
	private int size;
	
	public PageInfo(int number, int size) {
		super();
		this.number = number;
		this.size = size;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
}
