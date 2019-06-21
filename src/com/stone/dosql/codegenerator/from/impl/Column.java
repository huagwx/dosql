package com.stone.dosql.codegenerator.from.impl;

//table 或view的列的信息
public class Column {
	private String tableName;  //表名
	private String name;   //列名
	private String type;  //类型
	private String leng;  //长度
	
	public Column() {
		this.tableName=null;
		this.name=null;
		this.type=null;
		this.leng=null;
	}
	public Column(String tableName,String name,String type,String leng){
		this.tableName=tableName.toUpperCase();
		this.name=name.toUpperCase();
		this.type=type.toUpperCase();
		this.leng=leng.toUpperCase();
	}
	//取得全名
	public String getFullName(){
		if(this.tableName==null||this.tableName.trim().length()==0){
			return this.name;
		}else{
			
		}
		return this.tableName+"."+this.name;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public String getTableName() {
		return tableName.toUpperCase();
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getName() {
		return name.toUpperCase();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type.toUpperCase();
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLeng() {
		return leng.toUpperCase();
	}

	public void setLeng(String leng) {
		this.leng = leng;
	}

}
