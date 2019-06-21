package com.stone.dosql.codegenerator.having.impl;

import java.util.ArrayList;
import java.util.List;

import com.stone.dosql.codegenerator.impl.ResultSetInit;
import com.stone.dosql.codegenerator.impl.ResultSetTemp;
import com.stone.dosql.codegenerator.where.impl.WhereAction;

public class HavingGroupByDo {
	private WhereAction havingAction;
	//一个查询的中间代码
	private List allMiddleCodeListOfOneQuery;

	//前面的查询得到的resultSetTemp的list
	private List resultSetTempList;
	
	//having的中间代码
	private List havingMiddleCodeList=new ArrayList();
	
	private ResultSetTemp resultSetTemp;
	
	public HavingGroupByDo(List allMiddleCodeListOfOneQuery,ResultSetTemp resultSetTemp,List resultSetTempList) {
		this.allMiddleCodeListOfOneQuery=allMiddleCodeListOfOneQuery;
		this.resultSetTemp=resultSetTemp;
		this.resultSetTempList=resultSetTempList;
		this.doAction();
	}
	//取得最后的结果
	public ResultSetTemp getResultSetTemp(){
		return this.resultSetTemp;
	}
	private void doAction(){
		int startId=this.getStartId();
		int endId=this.getEndId();
		int size=this.allMiddleCodeListOfOneQuery.size();
		
		for(int i=startId;i<=endId;i++){
			List oneLine=(List)this.allMiddleCodeListOfOneQuery.get(i);
			this.havingMiddleCodeList.add(oneLine);
		}
		//得到where的中间代码进行where中间代码的处理
		this.doHavingMiddleCodeAction();
	}
	
	private void doHavingMiddleCodeAction(){
		int size=this.havingMiddleCodeList.size();
//		//所有列的笛卡尔乘积
//		this.doAllSet();
		
		//说明where的条件为空
		// 返回的结果的flag都是false标识
		if(size==0){
			
			//设置所有的表示为false
			//默认就是

			
		}else{  //查找的条件不为空
			HavingGroupByAction havingGroupByAction=new HavingGroupByAction(this.havingMiddleCodeList,this.resultSetTemp,this.resultSetTempList);
			this.resultSetTemp=havingGroupByAction.getResultSetTemp();
		}
		
		


	}
	
	private String isGROUP_BY="GROUP_BY";
	//在oper为FROM的后面 即 from的id+1
	private int getStartId(){
		int startId=0;
		int size=this.allMiddleCodeListOfOneQuery.size();
		for(int i=0;i<size;i++){
			List oneLine=(List)this.allMiddleCodeListOfOneQuery.get(i);
			String oper=(String)oneLine.get(0);
			if(oper.equals(this.isGROUP_BY)){
				startId=i+1;
				break;
				
			}
		}
		return startId;
	}
	private String isHAVING="HAVING";
	//返回oper为WHERE的id
	private int getEndId(){
		int endId=0;
		int size=this.allMiddleCodeListOfOneQuery.size();
		for(int i=0;i<size;i++){
			List oneLine=(List)this.allMiddleCodeListOfOneQuery.get(i);
			String oper=(String)oneLine.get(0);
			if(oper.equals(this.isHAVING)){
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
