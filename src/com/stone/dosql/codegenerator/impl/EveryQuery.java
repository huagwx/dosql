package com.stone.dosql.codegenerator.impl;

import java.util.ArrayList;
import java.util.List;

//取得每一个查询 右下到上 
public class EveryQuery {
	private List queryList=new ArrayList();
	private List middleCodeList;
	private String ResultSet="RESULTSETTEMP";
	
	public EveryQuery(List middleCodeList) {
		this.middleCodeList=middleCodeList;
		this.doAction();
	}
	public List getAllQueryList(){
		return this.queryList;
	}
	private void doAction(){
		boolean isContinue=true;
		int i=0;
		int size=this.middleCodeList.size();
		while(isContinue){
			List oneQuery=new ArrayList();
			
			for(int j=i;j<size;j++){
				List oneLine=(List)this.middleCodeList.get(j);
				oneQuery.add(oneLine);
				String rs=(String)oneLine.get(0);
				if(rs.equals(this.ResultSet)){
					i=j+1;
					break;
				}
			}
			this.queryList.add(oneQuery);
			if(i==size){
				isContinue=false;
			}
		}
		System.out.println("everyquery中所有查询的查询的数量为："+this.queryList.size());
	}

	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
