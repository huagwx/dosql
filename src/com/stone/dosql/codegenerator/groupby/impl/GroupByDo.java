package com.stone.dosql.codegenerator.groupby.impl;

import java.util.ArrayList;
import java.util.List;

import com.stone.dosql.codegenerator.from.impl.Column;
import com.stone.dosql.codegenerator.impl.ResultSetInit;
import com.stone.dosql.codegenerator.where.impl.ColumnTemp;

public class GroupByDo {
	//一个查询的中间代码
	//private List groupbyTempList=new ArrayList(); //groupby的信息<GroupByTemp>类型
	private ResultSetInit resultSetWhere;  //前面处理where和orderby后的resultset集合
	
	private List allMiddleCodeListOfOneQuery;
	//group by 的中间代码
	private List groupbyColumnList=new ArrayList();
	private List groupByMiddleCodeList=new ArrayList();
	public GroupByDo(List allMiddleCodeListOfOneQuery,ResultSetInit resultSetWhere) {
		this.allMiddleCodeListOfOneQuery=allMiddleCodeListOfOneQuery;
		this.resultSetWhere=resultSetWhere;
		this.doAction();
	}
	public List getGroupByColumnList(){
		
		return this.groupbyColumnList;
	}
//	//获得groupby的列得到的结果 用到
//	public List getGroupByTempList(){
//		return this.groupbyTempList;
//	}
	private void doAction(){
		//取得groupby的中间代码
		
		int startId=this.getStartId();
		int endId=this.getEndId();
		int size=this.allMiddleCodeListOfOneQuery.size();
		
		for(int i=startId;i<=endId;i++){
			List oneLine=(List)this.allMiddleCodeListOfOneQuery.get(i);
			this.groupByMiddleCodeList.add(oneLine);
		}
		this.doGroupByAction();
	}
	private void doGroupByAction(){
		int size=this.groupByMiddleCodeList.size();
		if(size==0){  //groupby的条件为空
			
		}else{
			this.doGroupByNotNull();
		}
	}
	//处理groupby 不为空
	private void doGroupByNotNull(){
		int size=this.groupByMiddleCodeList.size();
		for(int i=0;i<size;i++){
			List oneLine=(List)this.groupByMiddleCodeList.get(i);
			String oper=(String)oneLine.get(0);
			String t1=(String)oneLine.get(1);
			if(oper.equals("COLUMN")){
				this.groupbyColumnList.add(t1);
			}
		}
		//得到groupbyColumnList后来处理doGroupByTempList用于select的处理
		this.doGroupByTempList();
	}
	//如group by USER.ID  USER.NAME  
	//初始化都为false被选择的为true
	private void doGroupByTempList(){
		int size=this.groupbyColumnList.size();
		int rsize=this.resultSetWhere.getRecordList().size();
		//groupby 的flag的标识为false 选择一个groupby的记录为false
		this.resultSetWhere.setGroupbyArray(new boolean[rsize]);
		this.resultSetWhere.setGroupMapId(new int[rsize]); //初始化的都为0
		for(int i=0;i<rsize;i++){
			if(this.resultSetWhere.getGroupbyArray()[i]){
				continue;
			}
			List oneRecordi=(List)this.resultSetWhere.getRecordList().get(i);
			for(int k=i+1;k<rsize;k++){
				if(this.resultSetWhere.getGroupbyArray()[k]){
					continue;
				}
				List oneRecordk=(List)this.resultSetWhere.getRecordList().get(k);
				boolean isSame=true;
				for(int j=0;j<size;j++){
					String columnFullName=(String)this.groupbyColumnList.get(j);
					ColumnTemp ct=this.getColumnTempByFullName(columnFullName);
					String rk=(String)oneRecordk.get(ct.getId());
					String ri=(String)oneRecordi.get(ct.getId());
					if(!rk.equals(ri)){
						isSame=false;
						break;
					}
				}
				if(isSame){
					this.resultSetWhere.getGroupbyArray()[k]=true;
					this.resultSetWhere.getGroupMapId()[k]=i;//跟i的id对应
				}
			}

		}
		
		
//		
//		for(int i=0;i<size;i++){
//			String columnFullName=(String)this.groupbyColumnList.get(i);
//			ColumnTemp ct=this.getColumnTempByFullName(columnFullName);
//			Column col=ct.getColumn();
//			List groupbyRecord=this.getGroupByReocrdListByColRecordId(ct.getId());
//			GroupByTemp gbt=new GroupByTemp();
//			gbt.setGroupbyColumn(col);
//			gbt.setGroupbyRecord(groupbyRecord);
//			gbt.setColumnInResultSetId(ct.getId());
//			this.groupbyTempList.add(gbt);
			
//			
//		}
	}
	//通过groupby的col的fullname取得该col的所有记录
	private List getGroupByReocrdListByColRecordId(int id){
		List recList=new ArrayList(); // 是<String>类型的
		int recordSize=this.resultSetWhere.getRecordList().size();
		for(int i=0;i<recordSize;i++){
			List oneRecord=(List)this.resultSetWhere.getRecordList().get(i);
			String colRecord=(String)oneRecord.get(id);
			recList.add(colRecord);
		}
		return recList;
		
		
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
	private String isWhere="WHERE";
	//在oper为where的后面 即 where的id+1
	private int getStartId(){
		int startId=0;
		int size=this.allMiddleCodeListOfOneQuery.size();
		for(int i=0;i<size;i++){
			List oneLine=(List)this.allMiddleCodeListOfOneQuery.get(i);
			String oper=(String)oneLine.get(0);
			if(oper.equals(this.isWhere)){
				startId=i+1;
				break;
				
			}
		}
		return startId;
	}
	private String isHaving="HAVING";
	//返回oper为having的id-1
	private int getEndId(){
		int endId=0;
		int size=this.allMiddleCodeListOfOneQuery.size();
		for(int i=0;i<size;i++){
			List oneLine=(List)this.allMiddleCodeListOfOneQuery.get(i);
			String oper=(String)oneLine.get(0);
			if(oper.equals(this.isHaving)){
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
