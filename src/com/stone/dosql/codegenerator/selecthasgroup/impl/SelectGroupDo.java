package com.stone.dosql.codegenerator.selecthasgroup.impl;

import java.util.ArrayList;
import java.util.List;

import com.stone.dosql.codegenerator.from.impl.Column;
import com.stone.dosql.codegenerator.impl.ResultSetInit;
import com.stone.dosql.codegenerator.impl.ResultSetTemp;
import com.stone.dosql.codegenerator.where.impl.ColumnTemp;

//做select
public class SelectGroupDo {
	private ResultSetTemp resultSetTemp=new ResultSetTemp();
	private List allMiddleCodeListOfOneQuery;
	private SelectGroupAction selectGroupAction;
	
	private List selectMiddleCodeList=new ArrayList();
	private ResultSetInit resultSetWhere;
	
	
	public SelectGroupDo(List allMiddleCodeListOfOneQuery,ResultSetInit resultSetWhere) {
		this.allMiddleCodeListOfOneQuery=allMiddleCodeListOfOneQuery;
		this.resultSetWhere=resultSetWhere;
		//this.doGroupByAction();
		this.doActionHasGroup();
	}
	//groupby的标识位设置
//	private List getOneGroupByReocrdList(int id){
//		List list=new ArrayList();
//		int size=this.groupByTempList.size();
//		for(int i=0;i<size;i++){
//			GroupByTemp groupByTemp=(GroupByTemp)this.groupByTempList.get(i);
//			String strT=(String)groupByTemp.getGroupbyRecord().get(id);
//			list.add(strT);
//		}
//		return list;
//	}
//	private void doGroupByAction(){
//		int size=this.resultSetWhere.getRecordList().size();
//		this.resultSetWhere.setGroupbyArray(new boolean [size]);
//		int groupbysize=this.groupByTempList.size();
//		for(int i=0;i<size;i++){
//			if(this.resultSetWhere.getGroupbyArray()[i]){
//				continue;
//			}
//			List oneGroupbyReocrdI=this.getOneGroupByReocrdList(i);
//			for(int j=i+1;j<size;j++){
//				if(this.resultSetWhere.getGroupbyArray()[j]){
//					continue;
//				}
//				List oneGroupByReocrdJ=this.getOneGroupByReocrdList(j);
//			    boolean isTrue=true;
//			    for(int k=0;k<groupbysize;k++){
//			    	String st1=(String)oneGroupbyReocrdI.get(k);
//			    	String st2=(String)oneGroupByReocrdJ.get(k);
//			    	if(st1.equals(st2)){
//			    		
//			    	}else{
//			    		isTrue=false;
//			    		break;
//			    	}
//			    }
//			    if(isTrue){
//			    	this.resultSetWhere.getGroupbyArray()[j]=true;
//			    }
//				
//			}
//		}
//		//去掉true部分
//		for(int i=0;i<size;i++){
//			if(this.resultSetWhere.getGroupbyArray()[i]){
//				this.resultSetWhere.getRecordList().set(i, null);
//			}
//		}
//		//去掉null
//		boolean isContinue=true;
//		while(isContinue){
//			if(this.resultSetWhere.getRecordList().remove(null)){
//				
//			}else{
//				isContinue=false;
//			}
//			
//		}
//	}
	public ResultSetTemp getResultSetTemp(){
		int size=this.resultSetTemp.getResultSet().getRecordList().size();
		for(int i=0;i<size;i++){
			List oneRecord=(List)this.resultSetTemp.getResultSet().getRecordList().get(i);
			int si=oneRecord.size();
			for(int j=0;j<si;j++){
				System.out.print((String)oneRecord.get(j)+"  ");
			}
			System.out.println();
		}
		System.out.println(this.resultSetTemp.getTempName());
		return this.resultSetTemp;
	}
	//有group by
	private void doActionHasGroup(){
		
		int startId=this.getStartId();
		int endId=this.getEndId();
		int size=this.allMiddleCodeListOfOneQuery.size();
		
		for(int i=startId;i<=endId;i++){
			List oneLine=(List)this.allMiddleCodeListOfOneQuery.get(i);
			this.selectMiddleCodeList.add(oneLine);
		}
		this.doSelectAction();
	}
	//分析select中间代码
	private void doSelectAction(){
		//得到select的行 按顺序
		this.doSelColumn();
		this.selectGroupAction=new SelectGroupAction(this.selectMiddleCodeList,this.resultSetWhere,this.resultSetTemp);
		this.resultSetTemp=this.selectGroupAction.getResultSetTemp();
	}
	private void doSelColumn(){

		int size=this.selectMiddleCodeList.size();
		for(int i=0;i<size;i++){
			List oneLine=(List)this.selectMiddleCodeList.get(i);
			String oper=(String)oneLine.get(0);
			if(oper.equals("ADD")){
				String t2=(String)oneLine.get(2);
				List returnOneLine=this.getSelColumnLineByTx(t2);
				t2=(String)returnOneLine.get(2);
				String t1=(String)returnOneLine.get(1);
				String type="DOUBLE";
				
				if(t1.startsWith("T")&&t1.indexOf(".")==-1){
					type="DOUBLE";
					Column column=new Column();
					column.setName(t2);
					column.setType(type);
					this.resultSetTemp.getResultSet().getSelColumnsList().add(column);
				}else if(t1.equals("*")){
					int allsize=this.resultSetWhere.getColumnsList().size();
					for(int ai=0;ai<allsize;ai++){
						Column colW=(Column)this.resultSetWhere.getColumnsList().get(ai);
						this.resultSetTemp.getResultSet().getSelColumnsList().add(colW);
					}
				}else if(t1.endsWith(".*")){
					String tableName=t1.substring(0,t1.length()-2);
					int allsize=this.resultSetWhere.getColumnsList().size();
					for(int ai=0;ai<allsize;ai++){
						Column colW=(Column)this.resultSetWhere.getColumnsList().get(ai);
						if(colW.getTableName().equals(tableName)){
							this.resultSetTemp.getResultSet().getSelColumnsList().add(colW);
						}
						
					}
					
					
				}else{
					type=this.getTypeByT1(t1);
					Column column=new Column();
					column.setName(t2);
					column.setType(type);
					this.resultSetTemp.getResultSet().getSelColumnsList().add(column);
				}
				
//				Column column=new Column();
//				column.setName(t2);
//				column.setType(type);
//				this.resultSetTemp.getResultSet().getSelColumnsList().add(column);
				
				
			}else if(oper.equals("RESULTSETTEMP")){
				String t=(String)oneLine.get(3);
				this.resultSetTemp.setTempName(t);
			}
		}
	}
	//获得selcolumn的t1为表的列的类型
	private String getTypeByT1(String t1){
		String type="";
		int size=this.resultSetWhere.getColumnsList().size();
		for(int i=0;i<size;i++){
			Column col=(Column)this.resultSetWhere.getColumnsList().get(i);
			if(col.getFullName().equals(t1)){
				type=col.getType();
				break;
			}
		}
		return type;
	}
	//返回SelColumn的中间代码
	private List getSelColumnLineByTx(String tx){
		int size=this.selectMiddleCodeList.size();
		List oneLineRe=null;
		for(int i=0;i<size;i++){
			List oneLine=(List)this.selectMiddleCodeList.get(i);
			String oper=(String)oneLine.get(0);
			if(oper.equals("SELCOLUMN")){
				String t=(String)oneLine.get(3);
				if(t.equals(tx)){
					oneLineRe=oneLine;
					break;
				}
			}
		}
		return oneLineRe;
	}
	
	private String isOrder_by="ORDER_BY";
	//在oper为where的后面 即 where的id+1
	private int getStartId(){
		int startId=0;
		int size=this.allMiddleCodeListOfOneQuery.size();
		for(int i=0;i<size;i++){
			List oneLine=(List)this.allMiddleCodeListOfOneQuery.get(i);
			String oper=(String)oneLine.get(0);
			if(oper.equals(this.isOrder_by)){
				startId=i+1;
				break;
				
			}
		}
		return startId;
	}
	private String isRESULTSETTEMP="RESULTSETTEMP";
	//返回oper为RESULTSETTEMP的id
	private int getEndId(){
		int endId=0;
		int size=this.allMiddleCodeListOfOneQuery.size();
		for(int i=0;i<size;i++){
			List oneLine=(List)this.allMiddleCodeListOfOneQuery.get(i);
			String oper=(String)oneLine.get(0);
			if(oper.equals(this.isRESULTSETTEMP)){
				endId=i;
				break;
				
			}
		}
		return endId;
	}
	//输入column的全名 获得 在记录中的位置
	private ColumnTemp  getColumnTempByFullName(String columnFullName){
		ColumnTemp ct=new ColumnTemp();
		List list=this.resultSetWhere.getColumnsList();
		int size=list.size();
		for(int i=0;i<size;i++){
			Column col=(Column)list.get(i);
			
			if(col.getFullName().equals(columnFullName)){
				//System.out.println(columnFullName+ "sdfsddddddddddddddddd");
				ct.setColumn(col);
				ct.setId(i);
				break;
			}
		}
		//System.out.println(".s.df.sd.f.sd.f:"+ct.getColumn().getType());
		return ct;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

