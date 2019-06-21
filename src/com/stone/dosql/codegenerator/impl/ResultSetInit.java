package com.stone.dosql.codegenerator.impl;

import java.util.ArrayList;
import java.util.List;

public class ResultSetInit {
	//结果的列集合
	//private List columnsNameList=new ArrayList(); //table.column组合
	private List columnsList=new ArrayList();  //每一个列的信息<Column>类型
	//结果对应的记录的集合 
	private List recordList=new ArrayList();
	
	//标识每一个查询的位置
	//标识查询的位置 初始化为false 得到所有的个数 获得所有的笛卡尔乘积个数
	private boolean[] flagArray;
	
	//groupby的表示位
	private boolean[] groupbyArray;
	private int[] groupMapId;// 为true是对应的id

	public int[] getGroupMapId() {
		return groupMapId;
	}

	public void setGroupMapId(int[] groupMapId) {
		this.groupMapId = groupMapId;
	}

	public boolean[] getGroupbyArray() {
		return groupbyArray;
	}

	public void setGroupbyArray(boolean[] groupbyArray) {
		this.groupbyArray = groupbyArray;
	}

	public ResultSetInit() {
		// TODO Auto-generated constructor stub
	}

	public List getColumnsList() {
		return columnsList;
	}

	public void setColumnsList(List columnsList) {
		this.columnsList = columnsList;
	}

	public List getRecordList() {
		return recordList;
	}

	public void setRecordList(List recordList) {
		this.recordList = recordList;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public boolean[] getFlagArray() {
		return flagArray;
	}

	public void setFlagArray(boolean[] flagArray) {
		this.flagArray = flagArray;
	}


//	public List getColumnsNameList() {
//		return columnsNameList;
//	}
//
//	public void setColumnsNameList(List columnsNameList) {
//		this.columnsNameList = columnsNameList;
//	}

}
