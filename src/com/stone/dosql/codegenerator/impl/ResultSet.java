package com.stone.dosql.codegenerator.impl;

import java.util.ArrayList;
import java.util.List;

public class ResultSet {
	private List selColumnsList=new ArrayList();  //每一个列的信息<SelColumn>类型
	//结果对应的记录的集合 
	private List recordList=new ArrayList();
	
	private boolean[] flagArray;
	public boolean[] getFlagArray() {
		return flagArray;
	}

	public void setFlagArray(boolean[] flagArray) {
		this.flagArray = flagArray;
	}

	public ResultSet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public List getSelColumnsList() {
		return selColumnsList;
	}

	public void setSelColumnsList(List selColumnsList) {
		this.selColumnsList = selColumnsList;
	}

	public List getRecordList() {
		return recordList;
	}

	public void setRecordList(List recordList) {
		this.recordList = recordList;
	}

}
