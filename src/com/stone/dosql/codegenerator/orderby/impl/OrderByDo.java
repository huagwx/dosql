package com.stone.dosql.codegenerator.orderby.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.stone.dosql.codegenerator.from.impl.Column;
import com.stone.dosql.codegenerator.impl.ResultSetInit;
import com.stone.dosql.codegenerator.where.impl.ColumnTemp;
import com.stone.dosql.util.StringListClone;

public class OrderByDo {
	//一个查询的中间代码
	private List allMiddleCodeListOfOneQuery;
	//order by 的中间代码
	private List orderbyColumnList=new ArrayList();
	private List orderByMiddleCodeList=new ArrayList();
	private ResultSetInit resultSetWhere;
	public OrderByDo(List allMiddleCodeListOfOneQuery,ResultSetInit resultSetWhere) {
		this.allMiddleCodeListOfOneQuery=allMiddleCodeListOfOneQuery;
		this.resultSetWhere=resultSetWhere;
		this.doAction();
	}
	public ResultSetInit getOrderByResultSetInit(){
		return this.resultSetWhere;
	}
	
	private void doAction(){
		//取得orderby的中间代码
		
		int startId=this.getStartId();
		int endId=this.getEndId();
		int size=this.allMiddleCodeListOfOneQuery.size();
		
		for(int i=startId;i<=endId;i++){
			List oneLine=(List)this.allMiddleCodeListOfOneQuery.get(i);
			this.orderByMiddleCodeList.add(oneLine);
		}
		this.doOrderByAction();
	}
	
	private void doOrderByAction(){
		int size=this.orderByMiddleCodeList.size();
		if(size==0){  //groupby的条件为空
			
		}else{
			this.doOrderByNotNull();
		}
	}
	private void doOrderByNotNull(){
		int size=this.orderByMiddleCodeList.size();
		Stack stack=new Stack();
		for(int i=0;i<size;i++){
			List oneLine=(List)this.orderByMiddleCodeList.get(i);
			String oper=(String)oneLine.get(0);
			String t1=(String)oneLine.get(1);
			String t2=(String)oneLine.get(2);
			if(oper.equals("COLUMN")){
				OrderByTemp obt=new OrderByTemp(t1,t2);
				stack.add(obt);
			}
		}
		this.doOrderByByAttribute(stack);
	}
	// 排序的位置颠倒 就可以得到最后的结果
	private void doOrderByByAttribute(Stack stack){
		//获取name 的属性 如有不是整数就不排序
		while(!stack.isEmpty()){
			OrderByTemp obt=(OrderByTemp)stack.pop();
			ColumnTemp ct=this.getColumnTempByFullName(obt.getName());
			if(ct.getColumn().getType().equals("INTEGER")||ct.getColumn().getType().equals("DOUBLE")){
				int recordId=ct.getId();
				if(obt.getOrderType().equals("ASC")){
					List recordsList=this.resultSetWhere.getRecordList();
					int size=recordsList.size();
					for(int i=1;i<size;i++){
						for(int j=0;j<size-i;j++){
							List oneRecordJ1=(List)recordsList.get(j);
							List oneRecordJ2=(List)recordsList.get(j+1);
							
							double recordContentJ1;
							try {
								recordContentJ1=Double.parseDouble((String)oneRecordJ1.get(recordId));
							} catch (NumberFormatException e) {
								//如果是空的时候就为整数的最小值
								recordContentJ1=Double.MIN_VALUE;
							}
							
							double recordContentJ2;
							try {
								recordContentJ2=Double.parseDouble((String)oneRecordJ2.get(recordId));
							} catch (NumberFormatException e) {
								//如果是空的时候就为整数的最小值
								recordContentJ2=Double.MIN_VALUE;
							}
							if(recordContentJ1>recordContentJ2){
								StringListClone slc1=new StringListClone(oneRecordJ1);
								StringListClone slc2=new StringListClone(oneRecordJ2);
								this.resultSetWhere.getRecordList().set(j, slc2.getStringListClone());
								this.resultSetWhere.getRecordList().set(j+1, slc1.getStringListClone());
							}
							
							
						}
						
					}
					
				}else if(obt.getOrderType().equals("DESC")){

					List recordsList=this.resultSetWhere.getRecordList();
					int size=recordsList.size();
					for(int i=1;i<size;i++){
						for(int j=0;j<size-i;j++){
							List oneRecordJ1=(List)recordsList.get(j);
							List oneRecordJ2=(List)recordsList.get(j+1);
							
							double recordContentJ1;
							try {
								recordContentJ1=Double.parseDouble((String)oneRecordJ1.get(recordId));
							} catch (NumberFormatException e) {
								//如果是空的时候就为整数的最小值
								recordContentJ1=Double.MIN_VALUE;
							}
							
							double recordContentJ2;
							try {
								recordContentJ2=Double.parseDouble((String)oneRecordJ2.get(recordId));
							} catch (NumberFormatException e) {
								//如果是空的时候就为整数的最小值
								recordContentJ2=Double.MIN_VALUE;
							}
							if(recordContentJ1<recordContentJ2){
								StringListClone slc1=new StringListClone(oneRecordJ1);
								StringListClone slc2=new StringListClone(oneRecordJ2);
								this.resultSetWhere.getRecordList().set(j, slc2.getStringListClone());
								this.resultSetWhere.getRecordList().set(j+1, slc1.getStringListClone());
							}
							
							
						}
						
					}
					
				
				}
			}
		}

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
	
	private String isHaving="HAVING";
	//在oper为where的后面 即 where的id+1
	private int getStartId(){
		int startId=0;
		int size=this.allMiddleCodeListOfOneQuery.size();
		for(int i=0;i<size;i++){
			List oneLine=(List)this.allMiddleCodeListOfOneQuery.get(i);
			String oper=(String)oneLine.get(0);
			if(oper.equals(this.isHaving)){
				startId=i+1;
				break;
				
			}
		}
		return startId;
	}
	private String isOrder_by="ORDER_BY";
	//返回oper为having的id-1
	private int getEndId(){
		int endId=0;
		int size=this.allMiddleCodeListOfOneQuery.size();
		for(int i=0;i<size;i++){
			List oneLine=(List)this.allMiddleCodeListOfOneQuery.get(i);
			String oper=(String)oneLine.get(0);
			if(oper.equals(this.isOrder_by)){
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
		// TODO Auto-generated method stub

	}

}
