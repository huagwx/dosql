package com.stone.dosql.optimizesyntax.impl;

import java.util.ArrayList;
import java.util.List;

import com.stone.dosql.codegenerator.from.impl.Table;

public class TableName {
	private List tableList=new ArrayList();
	private List errList=new ArrayList();
	public TableName(List tableList) {
		this.tableList=tableList;
		this.doAction();
	}
	private void doAction(){
		int size=this.tableList.size();
		for(int i=0;i<size;i++){
			Table table=(Table)this.tableList.get(i);
			String name=table.getName();
			String otherName=table.getOtherName();
//			System.out.println(name);
//			System.out.println(otherName);
			if(name.equals(otherName)){
				String err="from的表："+name+" 和别名不能相同!";
				this.errList.add(err);
			}else{
				for(int j=i+1;j<size;j++){
					Table t=(Table)this.tableList.get(j);
					String name1=t.getName();
					String otherName2=t.getOtherName();
					if(name.equals(name1)){
						String err1="from的表:"+name+" 重复查找!";
						this.errList.add(err1);
					}
					if(name.equals(otherName2)){
						String err2="表名:"+name+" 和别名相同!";
						this.errList.add(err2);
					}
				}
			}
		}
	}
	public List getTableNameErrList(){
		return this.errList;
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
