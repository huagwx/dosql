package com.stone.dosql.codegenerator.where.impl;

public class WhereTemp {
	private String type;  //值为 Integer,Double或boolean List
	private Object object;
	private String tempName;
	public String getTempName() {
		return tempName;
	}

	public void setTempName(String tempName) {
		this.tempName = tempName;
	}

	public String getType() {
		return type.toUpperCase();
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public WhereTemp() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
