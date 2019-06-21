package com.stone.dosql.struts.util;

public class JspSelectTime {
	private String dbName;
	private String inputSelect;
	private long time;
	public JspSelectTime(String dbName,String inputSelect,long time) {
		this.dbName=dbName;
		this.inputSelect=inputSelect;
		this.time=time;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public String getInputSelect() {
		return inputSelect;
	}

	public void setInputSelect(String inputSelect) {
		this.inputSelect = inputSelect;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getDbName() {
		return dbName;
	}

}
