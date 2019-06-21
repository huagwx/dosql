package com.stone.dosql.codegenerator.select.impl;

import java.util.ArrayList;
import java.util.List;

import com.stone.dosql.codegenerator.from.impl.Column;
import com.stone.dosql.codegenerator.impl.ResultSet;
import com.stone.dosql.codegenerator.impl.ResultSetInit;
import com.stone.dosql.codegenerator.impl.ResultSetTemp;
import com.stone.dosql.codegenerator.where.impl.ColumnTemp;
import com.stone.dosql.codegenerator.where.impl.WhereTemp;

public class SelectAction {
	private List tempList=new ArrayList();
	private List selectMiddleCodeList;
	private ResultSetInit resultSetWhere;
	private ResultSetTemp resultSetTemp; // 初始的resultSetTemp 的列就只有列的名称 ，还要加入类型和大小 没有tableName
	public SelectAction(List selectMiddleCodeList,ResultSetInit resultSetWhere,ResultSetTemp resultSetTemp) {
		this.selectMiddleCodeList=selectMiddleCodeList;
		this.resultSetWhere=resultSetWhere;
		this.resultSetTemp=resultSetTemp;
		selColumnSize=this.resultSetTemp.getResultSet().getSelColumnsList().size();
		//System.out.println("selColumnSizefgdfg:"+selColumnSize);
		this.doEachRecord();
	}
	public ResultSetTemp getResultSetTemp(){

		return this.resultSetTemp;
	}
	//一个记录
	private List oneRecord;
	private void doEachRecord(){
		int recSize=this.resultSetWhere.getRecordList().size();
		for(int i=0;i<recSize;i++){
			this.oneRecord=(List)this.resultSetWhere.getRecordList().get(i);
			this.doAction();
		}
	}

	private void doAction(){
		//清空templist的内容 存放中间结果
		this.tempList.clear();
		int size=this.selectMiddleCodeList.size();
		for(int i=0;i<size;i++){
			List oneLine=(List)this.selectMiddleCodeList.get(i);
			String oper=(String)oneLine.get(0);
			if(oper.equals("+")){
				this.doCompute(oneLine);
			}else if(oper.equals("-")){
				this.doCompute(oneLine);
			}else if(oper.equals("*")){
				this.doCompute(oneLine);
			}else if(oper.equals("/")){
				this.doCompute(oneLine);
			}else if(oper.equals("COUNT")){
				this.doFunc(oneLine);
			}else if(oper.equals("SUM")){
				this.doFunc(oneLine);
			}else if(oper.equals("AVG")){
				this.doFunc(oneLine);
			}else if(oper.equals("MAX")){
				this.doFunc(oneLine);
			}else if(oper.equals("MIN")){
				this.doFunc(oneLine);
			}else if(oper.equals("SELCOLUMN")){
				this.doSelColumn(oneLine);
			}else if(oper.equals("RESULTSETTEMP")){
				this.doRESULTSETTEMP(oneLine);
			}
		}
	}
	private void doRESULTSETTEMP(List oneLine){
		String oper=(String)oneLine.get(0);
		String t1=(String)oneLine.get(1);
		String t2=(String)oneLine.get(2);
		String t=(String)oneLine.get(3);
		//System.out.println(t2+ " t2");
		if(t2.equals("ALL")){
			//不做事情
		}else if(t2.equals("DISTINCT")){
			//去掉相同的部分
			
			ResultSet rs=this.resultSetTemp.getResultSet();
			int recSize=rs.getRecordList().size();
			//System.out.println(recSize);
			this.resultSetTemp.getResultSet().setFlagArray(new boolean[recSize]);
			
			for(int i=0;i<recSize;i++){
				if(this.resultSetTemp.getResultSet().getFlagArray()[i]==true){
					continue;
				}
				
				List oneRec1=(List)this.resultSetTemp.getResultSet().getRecordList().get(i);
				for(int j=i+1;j<recSize;j++){
					if(this.resultSetTemp.getResultSet().getFlagArray()[j]==true){
						continue;
					}
					List oneRec2=(List)this.resultSetTemp.getResultSet().getRecordList().get(j);
					int kk=0;
					for(int k=0;k<oneRec2.size();k++){
						String r1=(String)oneRec1.get(k);
						String r2=(String)oneRec2.get(k);
						if(r1.equals(r2)){
							kk++;
						}
					}
					if(kk==oneRec2.size()){
						this.resultSetTemp.getResultSet().getFlagArray()[j]=true;
						//System.out.println("sdfsdf");
					}
				}
				
			}
			for(int i=0;i<recSize;i++){
				if(this.resultSetTemp.getResultSet().getFlagArray()[i]==true){
					this.resultSetTemp.getResultSet().getRecordList().set(i, null);
				}
			}
			boolean isContinue=true;
			while(isContinue){
				if(this.resultSetTemp.getResultSet().getRecordList().remove(null)){
					
				}else{
					isContinue=false;
				}
			}
			
			
			
		}
	}
	//selColumn的个数
	private int selColumnSize;
	private List selColumnRecord=new ArrayList();
	private int selColumnId=0;
	
	private void doSelColumn(List oneLine){
		String oper=(String)oneLine.get(0);
		String t1=(String)oneLine.get(1);
		String t2=(String)oneLine.get(2);
		String t=(String)oneLine.get(3);
		String type="";
		String result;
		if(t1.equals("*")){//*
			int  size=this.resultSetWhere.getColumnsList().size();
			for(int i=0;i<size;i++){
				Column colT=(Column)this.resultSetWhere.getColumnsList().get(i);
				String allTableFullName=colT.getFullName();
				ColumnTemp ct=this.getColumnTempByFullName(allTableFullName);
				result=(String)this.oneRecord.get(ct.getId());
				SelColumnTemp sct=new SelColumnTemp(allTableFullName,result);
				this.selColumnRecord.add(sct);
				this.selColumnId++;
			}
		}else if(t1.endsWith(".*")){//table.*
			int  size=this.resultSetWhere.getColumnsList().size();
			for(int i=0;i<size;i++){
				Column colT=(Column)this.resultSetWhere.getColumnsList().get(i);
				String tableName=t1.substring(0,t1.length()-2);
				if(colT.getTableName().equals(tableName)){
					String allTableFullName=colT.getFullName();
					ColumnTemp ct=this.getColumnTempByFullName(allTableFullName);
					result=(String)this.oneRecord.get(ct.getId());
					//System.out.println(".*result:"+result);
					SelColumnTemp sct=new SelColumnTemp(allTableFullName,result);
					this.selColumnRecord.add(sct);
					this.selColumnId++;
				}

			}
		}else if(!t1.toUpperCase().equals(t1.toLowerCase())&&!t1.startsWith("\"")&&t1.indexOf(".")!=-1){
			//是 具体的列 
			ColumnTemp ct=this.getColumnTempByFullName(t1);
			result=(String)this.oneRecord.get(ct.getId());
			SelColumnTemp sct=new SelColumnTemp(t2,result);
			this.selColumnRecord.add(sct);
			this.selColumnId++;
		}else{
			result=this.getValueByTx(t1)+"";
			SelColumnTemp sct=new SelColumnTemp(t2,result);
			this.selColumnRecord.add(sct);
			this.selColumnId++;
		}
//		SelColumnTemp sct=new SelColumnTemp(t2,result);
//		this.selColumnRecord.add(sct);
//		this.selColumnId++;
		if(this.selColumnId==this.selColumnSize){
			//System.out.println("this.selColumnId==this.selColumnSize:"+selColumnSize);
			this.selColumnId=0;
			List oneList=new ArrayList();
			for(int i=0;i<selColumnSize;i++){
				String name=((Column)this.resultSetTemp.getResultSet().getSelColumnsList().get(i)).getFullName();
				//System.out.println("name:"+name);
				for(int j=0;j<selColumnSize;j++){
					SelColumnTemp sctt=(SelColumnTemp)this.selColumnRecord.get(j);
					if(name.equals(sctt.getName())){
						String result1=sctt.getResult();
						oneList.add(result1);
						break;
					}
				}
				
				
			}
			this.resultSetTemp.getResultSet().getRecordList().add(oneList);
			this.selColumnRecord.clear();
		}
		
		
	}
	
	private List funcList=new ArrayList();  //存放selecttemp的func的内容
	//每一次遇到func的时候查看有没有存在funcList中如果没有就执行操作，再保存如funcList中
	
	//如果返回的是null说明没有找到 要进行计算
	private SelectTemp getFuncSelectTemp(String t){
		SelectTemp st=null;
		int size=this.funcList.size();
		for(int i=0;i<size;i++){
			SelectTemp stT=(SelectTemp)this.funcList.get(i);
			if(stT.getTempName().equals(t)){
				SelectTempClone stc=new SelectTempClone(stT);
				st=stc.getSelectTempClone();
				break;
			}
		}
		return st;
	}
	
	private void doFunc(List oneLine){

		String oper=(String)oneLine.get(0);
		String t1=(String)oneLine.get(1);
		String t2=(String)oneLine.get(2);
		String t=(String)oneLine.get(3);
		SelectTemp st=new SelectTemp();
		SelectTemp stFind=this.getFuncSelectTemp(t);
		if(stFind!=null){
			st=stFind;
		}else{
			st.setTempName(t);
			
			st.setType("DOUBLE");
			//double dt1=this.getValueByTx(t1);
			double object=0;
			if(oper.equals("COUNT")){
				object=this.getCount(t1,t2);
			}else if(oper.equals("SUM")){
				object=this.getSum(t1,t2);	
			}else if(oper.equals("AVG")){
				object=this.getAvg(t1, t2);
			}else if(oper.equals("MAX")){
				object=this.getMax(t1, t2);
			}else if(oper.equals("MIN")){  //空 的值是最小的
				object=this.getMin(t1, t2);
				
			}

			st.setObject(object);
			//保存入funclist中
			SelectTempClone stc=new SelectTempClone(st);
			SelectTemp saveSt=stc.getSelectTempClone();
			this.funcList.add(saveSt);
		}
		

		this.tempList.add(st);
//		System.out.println(st.getTempName());
//		System.out.println(this.tempList.size());
	}
	//处理min
	private double getMin(String t1,String t2){
		double object=0;
		ColumnTemp ct=this.getColumnTempByFullName(t1);
		int ctId=ct.getId();
		int size=this.resultSetWhere.getRecordList().size();
//		if(t2.equals("ALL")){
			Column col=ct.getColumn();
			if(col.getType().equals("DOUBLE")||col.getType().equals("INTEGER")){
				double temp=Double.MAX_VALUE;
				for(int i=0;i<size;i++){
					List oneRecord=(List)this.resultSetWhere.getRecordList().get(i);
					double dt=Double.valueOf((String)oneRecord.get(ctId));
					if(dt<temp){
						temp=dt;
					}
				}
				object=temp;
			}else{
				object=0;
			}
//		}else if(t2.equals("DISTINCT")){
//			Column col=ct.getColumn();
//			if(col.getType().equals("DOUBLE")||col.getType().equals("INTEGER")){
//				
//			}else{
//				object=0;
//			}
//		}
		return object;
	}
	
	//处理max
	private double getMax(String t1,String t2){
		double object=0;
		ColumnTemp ct=this.getColumnTempByFullName(t1);
		int ctId=ct.getId();
		int size=this.resultSetWhere.getRecordList().size();
//		if(t2.equals("ALL")){
			Column col=ct.getColumn();
			if(col.getType().equals("DOUBLE")||col.getType().equals("INTEGER")){
				double temp=Double.MIN_VALUE;
				for(int i=0;i<size;i++){
					List oneRecord=(List)this.resultSetWhere.getRecordList().get(i);
					double dt=Double.valueOf((String)oneRecord.get(ctId));
					if(dt>temp){
						temp=dt;
					}
				}
				object=temp;
			}else{
				object=0;
			}
//		}else if(t2.equals("DISTINCT")){
//			Column col=ct.getColumn();
//			if(col.getType().equals("DOUBLE")||col.getType().equals("INTEGER")){
//				
//			}else{
//				object=0;
//			}
//		}
		return object;
	}
	private double getAvg(String t1,String t2){

		double object=0;
		ColumnTemp ct=this.getColumnTempByFullName(t1);
		int ctId=ct.getId();
		int size=this.resultSetWhere.getRecordList().size();
		
		if(t2.equals("ALL")){
			Column col=ct.getColumn();
			if(col.getType().equals("DOUBLE")||col.getType().equals("INTEGER")){
				double allSum=0;
				
				for(int i=0;i<size;i++){
					List oneRecord=(List)this.resultSetWhere.getRecordList().get(i);
					double di=0;
					try {
						di=Double.valueOf((String)oneRecord.get(ctId));
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						di=0;
					}
					allSum+=di;
				}
				object=allSum/size;
			}else{
				object=0;
			}
		}else if(t2.equals("DISTINCT")){
			Column col=ct.getColumn();
			
			if(col.getType().equals("DOUBLE")||col.getType().equals("INTEGER")){
				double allSum=0;
				int num=0;
				for(int i=0;i<size;i++){
					if(this.resultSetWhere.getFlagArray()[i]==true){
						continue;
					}
					
					List oneRec1=(List)this.resultSetWhere.getRecordList().get(i);
					for(int j=i+1;j<size;j++){
						if(this.resultSetWhere.getFlagArray()[j]==true){
							continue;
						}
						List oneRec2=(List)this.resultSetWhere.getRecordList().get(j);
						String r1=(String)oneRec1.get(ctId);
						String r2=(String)oneRec2.get(ctId);

						if(r1.equals(r2)){
							this.resultSetWhere.getFlagArray()[j]=true;
						}
					}
					num++;
					allSum+=Double.valueOf((String)oneRec1.get(ctId));
				}
				object=allSum/num;
			}else{
				object=0;
			}
		}
		
		return object;
	
	}
	//处理Sum
	private double getSum(String t1,String t2){
		double object=0;
		ColumnTemp ct=this.getColumnTempByFullName(t1);
		int ctId=ct.getId();
		int size=this.resultSetWhere.getRecordList().size();
		
		if(t2.equals("ALL")){
			Column col=ct.getColumn();
			if(col.getType().equals("DOUBLE")||col.getType().equals("INTEGER")){
				double allSum=0;
				
				for(int i=0;i<size;i++){
					List oneRecord=(List)this.resultSetWhere.getRecordList().get(i);
					double di=0;
					try {
						di=Double.valueOf((String)oneRecord.get(ctId));
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						di=0;
					}
					allSum+=di;
				}
				object=allSum;
			}else{
				object=0;
			}
		}else if(t2.equals("DISTINCT")){
			Column col=ct.getColumn();
			
			if(col.getType().equals("DOUBLE")||col.getType().equals("INTEGER")){
				double allSum=0;
				for(int i=0;i<size;i++){
					if(this.resultSetWhere.getFlagArray()[i]==true){
						continue;
					}
					
					List oneRec1=(List)this.resultSetWhere.getRecordList().get(i);
					for(int j=i+1;j<size;j++){
						if(this.resultSetWhere.getFlagArray()[j]==true){
							continue;
						}
						List oneRec2=(List)this.resultSetWhere.getRecordList().get(j);
						String r1=(String)oneRec1.get(ctId);
						String r2=(String)oneRec2.get(ctId);

						if(r1.equals(r2)){
							this.resultSetWhere.getFlagArray()[j]=true;
						}
					}
					allSum+=Double.valueOf((String)oneRec1.get(ctId));
				}
				object = allSum;
			}else{
				object=0;
			}
		}
		
		return object;
	}
	//处理count
	private double getCount(String t1,String t2){
		double object=0;
		if(t2.equals("ALL")){
			if(t1.equals("*")||t1.endsWith(".*")){
				object=this.resultSetWhere.getRecordList().size();
			}else{
				ColumnTemp ct=this.getColumnTempByFullName(t1);
				int recId=ct.getId();
				int iTemp=0;
				int recordSize=this.resultSetWhere.getRecordList().size();
				for(int i=0;i<recordSize;i++){
					List oneRecord=(List)this.resultSetWhere.getRecordList().get(i);
					String rT=(String)oneRecord.get(recId);
					if(rT.trim().length()==0){
						//字段的内容为空的时候就不加1
					}else{
						iTemp++;
					}
				}
				object=iTemp;
			}
			
		}else if(t2.equals("DISTINCT")){
			if(t1.equals("*")||t1.endsWith(".*")){
				//比较所有了列是否都相同
				//初始的所有的flag都是false 
				int tempT=0;
				int size1=this.resultSetWhere.getRecordList().size();
				for(int i=0;i<size1;i++){
					if(this.resultSetWhere.getFlagArray()[i]==true){
						continue;
					}
					
					List oneRec1=(List)this.resultSetWhere.getRecordList().get(i);
					for(int j=i+1;j<size1;j++){
						if(this.resultSetWhere.getFlagArray()[j]==true){
							continue;
						}
						List oneRec2=(List)this.resultSetWhere.getRecordList().get(j);
						int kk=0;
						for(int k=0;k<oneRec2.size();k++){
							String r1=(String)oneRec1.get(k);
							String r2=(String)oneRec2.get(k);
							if(r1.equals(r2)){
								kk++;
							}
						}
						if(kk==oneRec2.size()){
							this.resultSetWhere.getFlagArray()[j]=true;
						}
					}
					tempT++;
				}
				object=tempT;
				this.resultSetWhere.setFlagArray(new boolean[size1]); //设置为false
				
			}else{
				ColumnTemp ct=this.getColumnTempByFullName(t1);
				int recId=ct.getId();
				int tempT=0;
				int size1=this.resultSetWhere.getRecordList().size();
				for(int i=0;i<size1;i++){
					if(this.resultSetWhere.getFlagArray()[i]==true){
						continue;
					}
					
					List oneRec1=(List)this.resultSetWhere.getRecordList().get(i);
					for(int j=i+1;j<size1;j++){
						if(this.resultSetWhere.getFlagArray()[j]==true){
							continue;
						}
						List oneRec2=(List)this.resultSetWhere.getRecordList().get(j);

						String r1=(String)oneRec1.get(recId);
						String r2=(String)oneRec2.get(recId);

						if(r1.equals(r2)){
							this.resultSetWhere.getFlagArray()[j]=true;
						}
					}
					tempT++;
				}
				object=tempT;
				this.resultSetWhere.setFlagArray(new boolean[size1]); //设置为false
				
			}
		}
		return object;
	}

	//+ - * / 操作
	private void doCompute(List oneLine){
		String oper=(String)oneLine.get(0);
		String t1=(String)oneLine.get(1);
		String t2=(String)oneLine.get(2);
		String t=(String)oneLine.get(3);
		SelectTemp wt=new SelectTemp();
		wt.setTempName(t);
		wt.setType("DOUBLE");
		double dt1=this.getValueByTx(t1);
		double dt2=this.getValueByTx(t2);
		double result=0;
		
		if(oper.equals("+")){
			result=dt1+dt2;
		}else if(oper.equals("-")){
			result=dt1-dt2;
		}else if(oper.equals("*")){
			result=dt1*dt2;
		}else if(oper.equals("/")){
			result=dt1/dt2;
		}

		wt.setObject(result);
		
		
		this.tempList.add(wt);
		
	}
	
	private double getValueByTx(String tx){
		double dt1;
		//System.out.println("tx:::::"+tx);
		if(tx.toUpperCase().equals(tx.toLowerCase())&&!tx.startsWith("\"")){ //是数字

				dt1=Double.parseDouble(tx);
		}else if(tx.startsWith("\"")){   //是字符串 那么值为0 
			dt1=0;
		}else if(tx.startsWith("T")&&tx.indexOf(".")==-1){           //是临时变量
			SelectTemp wt=this.getSelectTempByTx(tx);
			dt1=(Double)wt.getObject();
			
			//System.out.println(dt1+ " T6");
			
		}else{                     //是列
			ColumnTemp ct=this.getColumnTempByFullName(tx);
			if(ct.getColumn().getType().equals("INTEGER")||ct.getColumn().getType().equals("DOUBLE")){ //是数
				String str=(String)this.oneRecord.get(ct.getId());
				if(str.trim().length()>0){
					dt1=Double.parseDouble(str);
				}else{
					dt1=0;
				}
				
			}else{
				dt1=0;
			}
		}
		//System.out.println(dt1);
		return dt1;
	}
	//获得中间的值
	private SelectTemp getSelectTempByTx(String tx){
//		System.out.println(tx);
		SelectTemp st=null;
		int size=this.tempList.size();
		for(int i=0;i<size;i++){
			SelectTemp stT=(SelectTemp)this.tempList.get(i);
			if(stT.getTempName().equals(tx)){
				st=stT;
				break;
			}
		}
		return st;
		
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
