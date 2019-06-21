package com.stone.dosql.util;

import java.util.List;

public class VWord {
	private int type;  //vn或vt 或 vt_null 或#
	private String name;
	private int tokenType;
	private String syntaxResult="";	
	private String rule="";
	


	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}




	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}



	public int getTokenType() {
		return tokenType;
	}



	public void setTokenType(int tokenType) {
		this.tokenType = tokenType;
	}



	public String getSyntaxResult() {
		return syntaxResult;
	}



	public void setSyntaxResult(String syntaxResult) {
		this.syntaxResult = syntaxResult;
	}



	public String getRule() {
		return rule;
	}



	public void setRule(String rule) {
		this.rule = rule;
	}



}
