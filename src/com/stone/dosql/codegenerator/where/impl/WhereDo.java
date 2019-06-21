package com.stone.dosql.codegenerator.where.impl;

import java.util.ArrayList;
import java.util.List;

import com.stone.dosql.codegenerator.from.impl.Column;
import com.stone.dosql.codegenerator.from.impl.RecordTable;
import com.stone.dosql.codegenerator.from.impl.Table;
import com.stone.dosql.codegenerator.impl.ResultSetInit;

import com.stone.dosql.util.StringListClone;


public class WhereDo {
	//进行where中间代码的处理
	
	private WhereAction whereAction;
	//一个查询的中间代码
	private List allMiddleCodeListOfOneQuery;
	//fromdo得到的recordTable信息
	private List recordTableList;
	//前面的查询得到的resultSetTemp的list
	private List resultSetTempList;
	private boolean whereIsNull=false;
	
	public boolean getWhereIsNull(){
		return this.whereIsNull;
	}
	
	//where的中间代码
	private List whereMiddleCodeList=new ArrayList();
	
	//wheredo 后得到的resultset (包括所查找的所有table的column的集合，和记录集合)
	private ResultSetInit resultSetWhere=new ResultSetInit();
	//返回resultSet的结果
	// 返回的结果的flag都是false标识
	public ResultSetInit getResultSet(){
		//this.doAction已经在WhereDo的构造函数做好了
		return this.resultSetWhere;
	}
	
	public WhereDo(List allMiddleCodeListOfOneQuery,List recordTableList,List resultSetTempList) {
		this.allMiddleCodeListOfOneQuery=allMiddleCodeListOfOneQuery;
		this.recordTableList=recordTableList;
		this.resultSetTempList=resultSetTempList;
		//初始化resultset的列的信息
		this.initResultSetAllColumns();
		//判断查找的table有没有记录存在
		this.doFindTableRecord();
	}
	private void doFindTableRecord(){
		boolean hasRecord=true;
		int tableSize=this.recordTableList.size();
		for(int i=0;i<tableSize;i++){
			RecordTable rt=(RecordTable)this.recordTableList.get(i);
			if(rt.getRecordList().size()==0){
				hasRecord=false;
				break;
			}
		}
		if(hasRecord){
			this.doAction();
		}
	}
	
	//把所有的table的column添加到whereResult的ColumnsList中 
	//注意 按顺序添加
	private void initResultSetAllColumns(){
		int size=this.recordTableList.size();
		for(int i=0;i<size;i++){
			RecordTable recordTable=(RecordTable)this.recordTableList.get(i);
			Table table=recordTable.getTable();
			List columns=(List)table.getColumns();
			int colSize=columns.size();
			for(int cId=0;cId<colSize;cId++){
				Column column=(Column)columns.get(cId);
				//System.out.println(column.getName()+"....");
				//System.out.println(column.getType());
				this.resultSetWhere.getColumnsList().add(column);
			}
			
		}
		
		
		
	}

	private void doAction(){
		int startId=this.getStartId();
		int endId=this.getEndId();
		int size=this.allMiddleCodeListOfOneQuery.size();
		
		for(int i=startId;i<=endId;i++){
			List oneLine=(List)this.allMiddleCodeListOfOneQuery.get(i);
			this.whereMiddleCodeList.add(oneLine);
		}
		//得到where的中间代码进行where中间代码的处理
		this.doWhereMiddleCodeAction();
		
	}

	private void doWhereMiddleCodeAction(){
		int size=this.whereMiddleCodeList.size();
		//所有列的笛卡尔乘积
		this.doAllSet();
		
		//说明where的条件为空
		// 返回的结果的flag都是false标识
		if(size==0){
			this.whereIsNull=true;
			//设置所有的表示为false
			//默认就是

			
		}else{  //查找的条件不为空
			this.whereAction=new WhereAction(this.whereMiddleCodeList,this.resultSetWhere,this.resultSetTempList);
			this.doWhereConditionNotNull();
			//处理掉所有的为false的行
			//为false的时候设置为null
			int falgsize=this.resultSetWhere.getFlagArray().length;
			for(int i=0;i<falgsize;i++){
				
				if(this.resultSetWhere.getFlagArray()[i]==false){
					this.resultSetWhere.getRecordList().set(i, null);
				}
			}
			//删除为null的record
			this.deleteResultSetWhereRecordHasNull();
			//返回的结果的flag都设置为flag
			int leftsize=this.resultSetWhere.getRecordList().size();
			this.resultSetWhere.setFlagArray(new boolean[leftsize]);
		}
		
		


	}
	//清楚记录中含有null的记录
	private void deleteResultSetWhereRecordHasNull(){
		boolean isContinue=true;
		while(isContinue){
			if(this.resultSetWhere.getRecordList().remove(null)){
				
			}else{
				isContinue=false;
			}
		}
	}
	
	private void doWhereConditionNotNull(){

		int allRecordSize=this.resultSetWhere.getRecordList().size();
		//列的信息
		for(int ri=0;ri<allRecordSize;ri++){
			//如果已经被选了就跳过 
			if(this.resultSetWhere.getFlagArray()[ri]==true){
				continue;
			}else{
				this.resultSetWhere.getFlagArray()[ri]=this.whereAction.getBoolResult((List)this.resultSetWhere.getRecordList().get(ri));
				
			}
					
		}
	}
	
	
	//处理where的条件为空的时候所有table的记录的笛卡尔乘积加入resultset的recordlist中
	private void doAllSet(){
//		int size=this.recordTableList.size();
//		System.out.println("this.recordTableList.size():"+this.recordTableList.size());
		//对第一个表要 new ArrayList()
		//取出第一个recordtable get（0）
		RecordTable recordTable=(RecordTable)this.recordTableList.get(0);
		int recordsSize=recordTable.getRecordList().size();
		for(int i=0;i<recordsSize;i++){
			List recordList=new ArrayList();
			List record=(List)recordTable.getRecordList().get(i);
			int recordLeng=record.size();
			for(int j=0;j<recordLeng;j++){
				recordList.add(record.get(j));
			}
			this.doNextTable(1, recordList);
		}
		int size=this.resultSetWhere.getRecordList().size();
		this.resultSetWhere.setFlagArray(new boolean[size]);
		//System.out.println("false:::"+this.resultSetWhere.getFlagArray()[0]);
	}
	//where条件为空的时候对每一个表记录的操作
	private void doNextTable(int tableId,List recordList){
		if(tableId==this.recordTableList.size()){
			this.resultSetWhere.getRecordList().add(recordList);
		}else{
			

			RecordTable recordTable=(RecordTable)this.recordTableList.get(tableId);
			int recordsSize=recordTable.getRecordList().size();
			for(int i=0;i<recordsSize;i++){
				//对类型是String的list的clone克隆
				StringListClone slc=new StringListClone(recordList);
				List recordListClone=slc.getStringListClone();
				List record=(List)recordTable.getRecordList().get(i);
				int recordLeng=record.size();
				for(int j=0;j<recordLeng;j++){
					recordListClone.add(record.get(j));
				}
				this.doNextTable(tableId+1, recordListClone);
			}
			
		}
	}
	
	
	private String isFROM="FROM";
	//在oper为FROM的后面 即 from的id+1
	private int getStartId(){
		int startId=0;
		int size=this.allMiddleCodeListOfOneQuery.size();
		for(int i=0;i<size;i++){
			List oneLine=(List)this.allMiddleCodeListOfOneQuery.get(i);
			String oper=(String)oneLine.get(0);
			if(oper.equals(this.isFROM)){
				startId=i+1;
				break;
				
			}
		}
		return startId;
	}
	private String isWHERE="WHERE";
	//返回oper为WHERE的id
	private int getEndId(){
		int endId=0;
		int size=this.allMiddleCodeListOfOneQuery.size();
		for(int i=0;i<size;i++){
			List oneLine=(List)this.allMiddleCodeListOfOneQuery.get(i);
			String oper=(String)oneLine.get(0);
			if(oper.equals(this.isWHERE)){
				endId=i-1;
				break;
				
			}
		}
		return endId;
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

}
