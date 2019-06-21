package com.stone.dosql.codegenerator.from.impl;

import java.util.ArrayList;
import java.util.List;


// 返回的是查询的table信息，包括所有的记录records
public class FromDo {
	private List fromTableList=new ArrayList();
	private List fromList=new ArrayList();
	private List oneQueryList;
	private String dbUrl;
	
	private String isRECORDTABLE="RECORDTABLE";
	private String isFROM="FROM";
	public FromDo(List oneQueryList,String dbUrl) {
		this.oneQueryList=oneQueryList;
		this.dbUrl=dbUrl;
		this.doAction();
	}
	//返回查找的table列表
	public List getTableList(){
		return this.fromTableList;
	}
	
	private void doAction(){
		int startId=this.getStartId();
		int endId=this.getEndId();
		for(int i=startId;i<=endId;i++){
			List oneLine=(List)this.oneQueryList.get(i);
			String oper=(String)oneLine.get(0);
			if(oper.equals(this.isRECORDTABLE)){
				String t1=(String)oneLine.get(1);
				RecordTable rt=new RecordTable(t1,this.dbUrl);
				this.fromTableList.add(rt);
			}
		}
		
	}
	//开始的id是0
	private int getStartId(){
		return 0;
	}
	
	//得到oper为from的中间代码的id
	private int getEndId(){
		int operOfFromId=0;
		int size=this.oneQueryList.size();
		for(int i=0;i<size;i++){
			List list=(List)this.oneQueryList.get(i);
			String oper=(String)list.get(0);
			if(oper.equals(this.isFROM)){
				operOfFromId=i;
				break;
				
			}
		}
		return operOfFromId;
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
