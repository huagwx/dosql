package com.stone.dosql.codegenerator.where.impl;

import java.util.ArrayList;
import java.util.List;

import com.stone.dosql.codegenerator.from.impl.Column;
import com.stone.dosql.codegenerator.impl.ResultSetInit;
import com.stone.dosql.codegenerator.impl.ResultSetTemp;
/*
 * FROM  T2  -  T3  
=  NAME  "SDF"  T4  
=  1  ID  T5  
>  ID  NAME  T6  
AND  T5  T6  T7  
OR  T7  T4  T8  
WHERE  T8  -  T9  */
public class WhereAction {
	private List whereMiddleCode;
	private List oneRecord;
	private List tempList=new ArrayList();
	
	//所有的记录信息
	private ResultSetInit resultSetWhere;
	//前面的子查询结果 
	private List resultSetTempList;

	
	//List whereMiddleCode where可执行的中间代码,当前的一个记录ResultSetInit resultSetWhere,所有的记录的信息,List resultSetTempList子查询的信息
	public WhereAction(List whereMiddleCodeList,ResultSetInit resultSetWhere,List resultSetTempList) {
		this.whereMiddleCode=whereMiddleCodeList;
		this.resultSetWhere=resultSetWhere;
		this.resultSetTempList=resultSetTempList;
	}
	public boolean getBoolResult(List oneRecord){
		this.tempList.clear();   //清除前面的tempList的内容
		this.oneRecord=oneRecord;
		return this.doAction();
	}
	private boolean doAction(){
		boolean is=false;
		int size=this.whereMiddleCode.size(); //没有from和where
		for(int i=0;i<size;i++){
			List oneLine=(List)this.whereMiddleCode.get(i);
			String oper=(String)oneLine.get(0);
			if(oper.equals("+")){
				this.doCompute(oneLine);
			}else if(oper.equals("-")){
				this.doCompute(oneLine);
			}else if(oper.equals("*")){
				this.doCompute(oneLine);
			}else if(oper.equals("/")){
				this.doCompute(oneLine);
			}else if(oper.equals("=")){
				this.doCompare(oneLine);
			}else if(oper.equals(">")){
				this.doCompare(oneLine);
			}else if(oper.equals(">=")){
				this.doCompare(oneLine);
			}else if(oper.equals("<")){
				this.doCompare(oneLine);
			}else if(oper.equals("<=")){
				this.doCompare(oneLine);
			}else if(oper.equals("<>")){
				this.doCompare(oneLine);
			}else if(oper.equals("NOT")){
				this.doNOT(oneLine);
			}else if(oper.equals("EXISTS")){
				this.doEXISTS(oneLine);
			}else if(oper.equals("IS_NULL")){
				this.doIS_NULL_OR_NOT(oneLine);
			}else if(oper.equals("IS_NOT_NULL")){
				this.doIS_NULL_OR_NOT(oneLine);
			}else if(oper.equals("IN")){
				this.doIN_OR_NOT_IN(oneLine);
			}else if(oper.equals("NOT_IN")){
				this.doIN_OR_NOT_IN(oneLine);
			}else if(oper.equals("=ALL")){
				this.doCompareAll_Any_Some(oneLine);
			}else if(oper.equals("=ANY")){
				this.doCompareAll_Any_Some(oneLine);
			}else if(oper.equals("=SOME")){
				this.doCompareAll_Any_Some(oneLine);
			}else if(oper.equals("<>ALL")){
				this.doCompareAll_Any_Some(oneLine);
			}else if(oper.equals("<>ANY")){
				this.doCompareAll_Any_Some(oneLine);
			}else if(oper.equals("<>SOME")){
				this.doCompareAll_Any_Some(oneLine);
			}else if(oper.equals("<ALL")){
				this.doCompareAll_Any_Some(oneLine);
			}else if(oper.equals("<ANY")){
				this.doCompareAll_Any_Some(oneLine);
			}else if(oper.equals("<SOME")){
				this.doCompareAll_Any_Some(oneLine);
			}else if(oper.equals("<=ALL")){
				this.doCompareAll_Any_Some(oneLine);
			}else if(oper.equals("<=ANY")){
				this.doCompareAll_Any_Some(oneLine);
			}else if(oper.equals("<=SOME")){
				this.doCompareAll_Any_Some(oneLine);
			}else if(oper.equals(">ALL")){
				this.doCompareAll_Any_Some(oneLine);
			}else if(oper.equals(">ANY")){
				this.doCompareAll_Any_Some(oneLine);
			}else if(oper.equals(">SOME")){
				this.doCompareAll_Any_Some(oneLine);
			}else if(oper.equals(">=ALL")){
				this.doCompareAll_Any_Some(oneLine);
			}else if(oper.equals(">=ANY")){
				this.doCompareAll_Any_Some(oneLine);
			}else if(oper.equals(">=SOME")){
				this.doCompareAll_Any_Some(oneLine);
			}else if(oper.equals("LIKE")){   //5个的
				this.doLikeOrNotLike(oneLine);
			}else if(oper.equals("NOT_LIKE")){
				this.doLikeOrNotLike(oneLine);
			}else if(oper.equals("BETWEEN")){
				this.doBetween_Or_not_between(oneLine);
			}else if(oper.equals("NOT_BETWEEN")){
				this.doBetween_Or_not_between(oneLine);
			}else if(oper.equals("NEW")){
				this.doNEW(oneLine);
			}else if(oper.equals("ADD")){
				this.doADD(oneLine);
			}else if(oper.equals("AND")){
				this.doAndOr(oneLine);
			}else if(oper.equals("OR")){
				this.doAndOr(oneLine);
			}
		}
		
		int tlsize=this.tempList.size();
		WhereTemp lastWt=(WhereTemp)this.tempList.get(tlsize-1);
		is=(Boolean)lastWt.getObject();  //得到最后的boolean
		return is;
		
	}
	//处理 and ，or
	private void doAndOr(List oneLine){
		String oper=(String)oneLine.get(0);
		String t1=(String)oneLine.get(1);
		String t2=(String)oneLine.get(2);
		String t=(String)oneLine.get(3);
		WhereTemp wtT1=this.getWhereTempByTx(t1);
		WhereTemp wtT2=this.getWhereTempByTx(t2);
		boolean bt1=(Boolean)wtT1.getObject();
		boolean bt2=(Boolean)wtT2.getObject();
		boolean resultBol=false;
		
		WhereTemp wt=new WhereTemp();
		wt.setTempName(t);
		wt.setType("boolean");
		if(oper.equals("AND")){
			resultBol=bt1&&bt2;
		}else if(oper.equals("OR")){
			resultBol=bt1||bt2;
		}
		wt.setObject(resultBol);
		this.tempList.add(wt);
		
	}
	//处理between , not between
	private void doBetween_Or_not_between(List oneLine){
		String oper=(String)oneLine.get(0);
		String t1=(String)oneLine.get(1);
		String t2=(String)oneLine.get(2);
		String t3=(String)oneLine.get(3);
		String t=(String)oneLine.get(4);
		WhereTemp wt=new WhereTemp();
		wt.setTempName(t);
		wt.setType("boolean");
		boolean resultBol=false;
		//默认是between
		double dt1=this.getValueByTx(t1);
		double dt2=this.getValueByTx(t2);
		double dt3=this.getValueByTx(t3);
		System.out.println(dt1+"  "+dt2+"  "+dt3);
		if(dt2>dt3){   //是dt2 <=dt3
			double dtT=dt2;
			dt2=dt3;
			dt3=dtT;
		}
		if(dt1>=dt2&&dt1<=dt3){
			resultBol=true;
		}else{
			resultBol=false;
		}
		
		
		if(oper.equals("NOT_BETWEEN")){
			resultBol=!resultBol;
		}
		wt.setObject(resultBol);
		this.tempList.add(wt);
	}
	
	//处理 like 或notlike         ///还没有做escape
	//LIKE  USER.ID  "AB/_"  "/"  T4 
	//LIKE  USER.ID  "AB/_"  -  T4  
	private void doLikeOrNotLike(List oneLine){
		String oper=(String)oneLine.get(0);
		String t1=(String)oneLine.get(1);
		String t2=(String)oneLine.get(2);
		String t3=(String)oneLine.get(3);
		String t=(String)oneLine.get(4);
		WhereTemp wt=new WhereTemp();
		wt.setTempName(t);
		wt.setType("boolean");
		
		//默认是like
		if(t1.startsWith("\"")){   //字符串
			t1=t1.substring(1,t1.length()-1);
		}else if(t1.startsWith("T")&&t1.indexOf(".")==-1||t1.toUpperCase().equals(t1.toLowerCase())&&!t1.startsWith("\"")){  //变量或数字
			//System.out.println(t1+" sdf sdf");
			t1=this.getValueByTx(t1)+"";
			//System.out.println(t1+" sdf sdf");
			if(t1.endsWith(".0")){
				t1=t1.substring(0,t1.length()-2);
			}
			
			 
		}else if(!t1.toUpperCase().equals(t1.toLowerCase())&&!t1.startsWith("\"")){
			ColumnTemp ct=this.getColumnTempByFullName(t1);
			t1=(String)this.oneRecord.get(ct.getId());
			t1=t1.trim();
		}
		boolean resultBol=false;
		t2=t2.substring(1,t2.length()-1); //t2 去掉两边的引号
		t1=t1.toUpperCase();
		t2=t2.toUpperCase();
		
		if(t3.length()>1){
			t3=t3.substring(1, t3.length()-1);   //escape
		}
		//System.out.println(t3+"  sdf");
		boolean isEscape=false;
		if(!t3.equals("-")){
			isEscape=true;
		}
		boolean isContinue=true;
		int t1Size=t1.length();
		int t2Size=t2.length();
		int t1P=0;
		int t2P=0;
		int k=0;
		while(isContinue&&k<100){
			try {
				k++;
				char ct1=t1.charAt(t1P);
				char ct2=t2.charAt(t2P);
				//System.out.println(ct1+" "+ct2);
				if(isEscape){        //有escape
				//	System.out.println("escape");
					char ct3=t3.charAt(0);  
					//System.out.println(ct3+"  sdfffff");
					if(ct2=='_'){
						if(t1P<1){
							t1P++;
							t2P++;
						}else {
							char pre=t1.charAt(t1P-1);
							//System.out.println("pre:"+pre+" ct3:"+ct3);
							if(pre==ct3){
								if(ct1!=ct2){
									//System.out.println("ct1!=ct2");
									isContinue=false;
									resultBol=false;
									break;
								}else{
									t1P++;
									t2P++;
								}
							}else{
								t1P++;
								t2P++;
							}
						}

					}else if(ct2=='%'){
						if(t1P<1){
							t1P++;
							t2P++;
						}else {
							char pre=t1.charAt(t1P-1);
							//System.out.println("pre:"+pre+" ct3:"+ct3);
							if(pre==ct3){
								if(ct1!=ct2){
									//System.out.println("ct1!=ct2");
									isContinue=false;
									resultBol=false;
									break;
								}else{
									t1P++;
									t2P++;
								}
							}else{
								t1P++;
								t2P++;
							}
						}
					}else{
						if(ct1!=ct2){
							isContinue=false;
							resultBol=false;
							break;
						}else{
							t1P++;
							t2P++;	
						}
					}
				}else{  //没有escape
					if(ct2=='_'){
						t1P++;
						t2P++;
					}else if(ct2=='%'){
						t1P=t1Size;
						t2P=t1P;
					}else{
						if(ct1!=ct2){
							isContinue=false;
							resultBol=false;
							break;
						}else{
							t1P++;
							t2P++;	
						}
					}
				}
				if(t1P==t1Size&&t1P==t2P){  //符合
					resultBol=true;
					isContinue=false;
					break;
				}else if(t1P==t1Size&&t2P!=t2Size||t1P!=t1Size&&t2P==t2Size){
					resultBol=false;
					isContinue=false;
					break;
				}
			} catch (RuntimeException e) {
				
				resultBol=false;
				isContinue=false;
				break;
			}
		}
//System.out.println(t1+"  : "+t2);
//        	int t1Size=t1.length();
//        	int t2Size=t2.length();
//        	int t1P=0;
//        	int t2P=0;
//        	boolean isContinue=true;
//        	int k=0;   //k 是临时的用来防止错误出现
//        	while(isContinue && k<100){
//        		k++;
//            	try {
//					String t1T=t1.substring(t1P,t1P+1).toUpperCase();
//					String t2T=t2.substring(t2P, t2P+1).toUpperCase();
//					
//					if(t2T.equals("_")){
//						if(t3.equals("-")){
//							t1P++;
//							t2P++;
//							if(t1P==t1Size&&t2P==t2Size){
//						    	isContinue=false;
//						    	resultBol=true;
//							}
//						}else{
//							boolean isPreBool=true;
//							String t1TPre="";
//							try {
//								t1TPre=t1.substring(t1P-1,t1P);
//							} catch (RuntimeException e) {
//								// TODO Auto-generated catch block
//								isPreBool=false;
//							}
//							if(isPreBool){
//								if(t1TPre.equals(t3)){
//									//System.out.println(t3+"...");
//									if(t2T.equals(t1T)){
//										t1P++;
//										t2P++;
//										if(t1P==t1Size&&t2P==t2Size){
//									    	isContinue=false;
//									    	resultBol=true;
//										}
//									}
//								}else{
//							    	isContinue=false;
//							    	resultBol=false;
//								}
//							}
//						}
//
//						
//					}else if(t2T.equals("%")){ //ab% absdfsdf
//						if(t3.equals("-")){
//							isContinue=false;
//							resultBol=true;
//						}
//
//						
//					}else if(t2T.equals(t1T)){
//						t1P++;
//						t2P++;
//						if(t1P==t1Size&&t2P==t2Size){
//					    	isContinue=false;
//					    	resultBol=true;
//						}
//					}else{
//						isContinue=false;
//						resultBol=false;
//					}
//				} catch (RuntimeException e) {
//					// TODO Auto-generated catch block
//					isContinue=false;
//					resultBol=false;
//				}
//            	
//        	}



		
		
		if(oper.equals("NOT_LIKE")){
			resultBol=!resultBol;
		}
		wt.setObject(resultBol);
		this.tempList.add(wt);
		
		
	}
	//处理compare all_any_some
	private void doCompareAll_Any_Some(List oneLine){
		String oper=(String)oneLine.get(0);
		String t1=((String)oneLine.get(1)).toUpperCase();
		String t2=((String)oneLine.get(2)).toUpperCase();
		String t=(String)oneLine.get(3);
		WhereTemp wt=new WhereTemp();
		wt.setTempName(t);
		wt.setType("boolean");
		boolean resultBol=false;
		
		if(t1.startsWith("\"")){   //字符串
			t1=t1.substring(1,t1.length()-1);
		}else if(t1.startsWith("T")&&t1.indexOf(".")==-1||t1.toUpperCase().equals(t1.toLowerCase())&&!t1.startsWith("\"")){  //变量或数字
			//System.out.println(t1+" sdf sdf");
			t1=this.getValueByTx(t1)+"";
			//System.out.println(t1+" sdf sdf");
			if(t1.endsWith(".0")){
				t1=t1.substring(0,t1.length()-2);
			}
			
			 
		}else if(!t1.toUpperCase().equals(t1.toLowerCase())&&!t1.startsWith("\"")){
			ColumnTemp ct=this.getColumnTempByFullName(t1);
			t1=(String)this.oneRecord.get(ct.getId());
			t1=t1.trim();
		}
		t1=t1.toUpperCase();
		t2=t2.toUpperCase();
		ResultSetTemp rst=this.getResultSetTempByTx(t2);
		if(rst.getResultSet().getSelColumnsList().size()==1){  //只有一个列
			List recordList=rst.getResultSet().getRecordList();
			int recordSize=recordList.size();
			if(oper.equals("=ALL")){
				boolean isDengyuAll=true;
				for(int i=0;i<recordSize;i++){
					List oneReocrdList=(List)recordList.get(i);
					String strTemp=(String)oneReocrdList.get(0);  //取第一个 因为也只有一个
					if(!t1.equals(strTemp.toUpperCase())){
						isDengyuAll=false;
						break;
					}
				}
				if(isDengyuAll){
					resultBol=true;
				}else{
					resultBol=false;
				}
			}else if(oper.equals("=ANY")){
				boolean isDengyuANY=false;
				for(int i=0;i<recordSize;i++){
					List oneReocrdList=(List)recordList.get(i);
					String strTemp=(String)oneReocrdList.get(0);  //取第一个 因为也只有一个
					if(t1.equals(strTemp.toUpperCase())){
						isDengyuANY=true;
						break;
					}
				}
				if(isDengyuANY){
					resultBol=true;
				}else{
					resultBol=false;
				}
			}else if(oper.equals("=SOME")){
				boolean isDengyuSome=false;
				for(int i=0;i<recordSize;i++){
					List oneReocrdList=(List)recordList.get(i);
					String strTemp=(String)oneReocrdList.get(0);  //取第一个 因为也只有一个
					if(t1.equals(strTemp.toUpperCase())){
						isDengyuSome=true;
						break;
					}
				}
				if(isDengyuSome){
					resultBol=true;
				}else{
					resultBol=false;
				}
			}else if(oper.equals("<>ALL")){
				boolean isBudengyuAll=true;
				for(int i=0;i<recordSize;i++){
					List oneReocrdList=(List)recordList.get(i);
					String strTemp=(String)oneReocrdList.get(0);  //取第一个 因为也只有一个
					if(t1.equals(strTemp.toUpperCase())){
						isBudengyuAll=false;
						break;
					}
				}
				if(isBudengyuAll){
					resultBol=true;
				}else{
					resultBol=false;
				}
			}else if(oper.equals("<>ANY")){
				boolean isBudengyuAny=false;
				for(int i=0;i<recordSize;i++){
					List oneReocrdList=(List)recordList.get(i);
					String strTemp=(String)oneReocrdList.get(0);  //取第一个 因为也只有一个
					if(!t1.equals(strTemp.toUpperCase())){
						isBudengyuAny=true;
						break;
					}
				}
				if(isBudengyuAny){
					resultBol=true;
				}else{
					resultBol=false;
				}
			}else if(oper.equals("<>SOME")){
				boolean isBudengyuSome=false;
				for(int i=0;i<recordSize;i++){
					List oneReocrdList=(List)recordList.get(i);
					String strTemp=(String)oneReocrdList.get(0);  //取第一个 因为也只有一个
					if(!t1.equals(strTemp.toUpperCase())){
						isBudengyuSome=true;
						break;
					}
				}
				if(isBudengyuSome){
					resultBol=true;
				}else{
					resultBol=false;
				}
			}else{
				double dt;
				try {
					dt=Double.parseDouble(t1);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					dt=0;
				}
				
				
				double dtemp;

				
				if(oper.equals("<ALL")){
					boolean isXiaoYuAll=true;
					for(int i=0;i<recordSize;i++){
						List oneReocrdList=(List)recordList.get(i);
						String strTemp=(String)oneReocrdList.get(0);  //取第一个 因为也只有一个
						try {
							dtemp=Double.parseDouble(strTemp);
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							dtemp=0;
						}
						if(dt>=dtemp){
							isXiaoYuAll=false;
							break;
						}
					}
					if(isXiaoYuAll){
						resultBol=true;
					}else{
						resultBol=false;
					}
					
				}else if(oper.equals("<ANY")){
					boolean isXiaoYuAny=false;
					for(int i=0;i<recordSize;i++){
						List oneReocrdList=(List)recordList.get(i);
						String strTemp=(String)oneReocrdList.get(0);  //取第一个 因为也只有一个
						try {
							dtemp=Double.parseDouble(strTemp);
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							dtemp=0;
						}
						if(dt<dtemp){
							isXiaoYuAny=true;
							break;
						}
					}
					if(isXiaoYuAny){
						resultBol=true;
					}else{
						resultBol=false;
					}
				}else if(oper.equals("<SOME")){
					boolean isXiaoYuSOME=false;
					for(int i=0;i<recordSize;i++){
						List oneReocrdList=(List)recordList.get(i);
						String strTemp=(String)oneReocrdList.get(0);  //取第一个 因为也只有一个
						try {
							dtemp=Double.parseDouble(strTemp);
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							dtemp=0;
						}
						if(dt<dtemp){
							isXiaoYuSOME=true;
							break;
						}
					}
					if(isXiaoYuSOME){
						resultBol=true;
					}else{
						resultBol=false;
					}
				}else if(oper.equals("<=ALL")){
					boolean isXiaoYudengyuAll=true;
					for(int i=0;i<recordSize;i++){
						List oneReocrdList=(List)recordList.get(i);
						String strTemp=(String)oneReocrdList.get(0);  //取第一个 因为也只有一个
						try {
							dtemp=Double.parseDouble(strTemp);
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							dtemp=0;
						}
						if(dt>dtemp){
							isXiaoYudengyuAll=false;
							break;
						}
					}
					if(isXiaoYudengyuAll){
						resultBol=true;
					}else{
						resultBol=false;
					}
				}else if(oper.equals("<=ANY")){
					boolean isXiaoYuDengyuAny=false;
					for(int i=0;i<recordSize;i++){
						List oneReocrdList=(List)recordList.get(i);
						String strTemp=(String)oneReocrdList.get(0);  //取第一个 因为也只有一个
						try {
							dtemp=Double.parseDouble(strTemp);
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							dtemp=0;
						}
						if(dt<=dtemp){
							isXiaoYuDengyuAny=true;
							break;
						}
					}
					if(isXiaoYuDengyuAny){
						resultBol=true;
					}else{
						resultBol=false;
					}
				}else if(oper.equals("<=SOME")){
					boolean isXiaoYuDengyuSOME=false;
					for(int i=0;i<recordSize;i++){
						List oneReocrdList=(List)recordList.get(i);
						String strTemp=(String)oneReocrdList.get(0);  //取第一个 因为也只有一个
						try {
							dtemp=Double.parseDouble(strTemp);
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							dtemp=0;
						}
						if(dt<=dtemp){
							isXiaoYuDengyuSOME=true;
							break;
						}
					}
					if(isXiaoYuDengyuSOME){
						resultBol=true;
					}else{
						resultBol=false;
					}
				}else if(oper.equals(">ALL")){
					boolean isDaYuAll=true;
					for(int i=0;i<recordSize;i++){
						List oneReocrdList=(List)recordList.get(i);
						String strTemp=(String)oneReocrdList.get(0);  //取第一个 因为也只有一个
						try {
							dtemp=Double.parseDouble(strTemp);
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							dtemp=0;
						}
						if(dt<=dtemp){
							isDaYuAll=false;
							break;
						}
					}
					if(isDaYuAll){
						resultBol=true;
					}else{
						resultBol=false;
					}
				}else if(oper.equals(">ANY")){
					boolean isDaYuAny=false;
					for(int i=0;i<recordSize;i++){
						List oneReocrdList=(List)recordList.get(i);
						String strTemp=(String)oneReocrdList.get(0);  //取第一个 因为也只有一个
						try {
							dtemp=Double.parseDouble(strTemp);
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							dtemp=0;
						}
						if(dt>dtemp){
							isDaYuAny=true;
							break;
						}
					}
					if(isDaYuAny){
						resultBol=true;
					}else{
						resultBol=false;
					}
				}else if(oper.equals(">SOME")){
					boolean isDaYuSome=false;
					for(int i=0;i<recordSize;i++){
						List oneReocrdList=(List)recordList.get(i);
						String strTemp=(String)oneReocrdList.get(0);  //取第一个 因为也只有一个
						try {
							dtemp=Double.parseDouble(strTemp);
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							dtemp=0;
						}
						if(dt>dtemp){
							isDaYuSome=true;
							break;
						}
					}
					if(isDaYuSome){
						resultBol=true;
					}else{
						resultBol=false;
					}
				}else if(oper.equals(">=ALL")){
					boolean isDaYudengyuAll=true;
					for(int i=0;i<recordSize;i++){
						List oneReocrdList=(List)recordList.get(i);
						String strTemp=(String)oneReocrdList.get(0);  //取第一个 因为也只有一个
						try {
							dtemp=Double.parseDouble(strTemp);
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							dtemp=0;
						}
						if(dt<dtemp){
							isDaYudengyuAll=false;
							break;
						}
					}
					if(isDaYudengyuAll){
						resultBol=true;
					}else{
						resultBol=false;
					}
				}else if(oper.equals(">=ANY")){
					boolean isDaYuDengyuAny=false;
					for(int i=0;i<recordSize;i++){
						List oneReocrdList=(List)recordList.get(i);
						String strTemp=(String)oneReocrdList.get(0);  //取第一个 因为也只有一个
						try {
							dtemp=Double.parseDouble(strTemp);
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							dtemp=0;
						}
						if(dt>=dtemp){
							isDaYuDengyuAny=true;
							break;
						}
					}
					if(isDaYuDengyuAny){
						resultBol=true;
					}else{
						resultBol=false;
					}
				}else if(oper.equals(">=SOME")){
					boolean isDaYuDengyuSOME=false;
					for(int i=0;i<recordSize;i++){
						List oneReocrdList=(List)recordList.get(i);
						String strTemp=(String)oneReocrdList.get(0);  //取第一个 因为也只有一个
						try {
							dtemp=Double.parseDouble(strTemp);
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							dtemp=0;
						}
						if(dt>=dtemp){
							isDaYuDengyuSOME=true;
							break;
						}
					}
					if(isDaYuDengyuSOME){
						resultBol=true;
					}else{
						resultBol=false;
					}
				}
			} 
			

		}else{
			resultBol=false;
		}
		wt.setObject(resultBol);
		this.tempList.add(wt);
		
	}

	//in or not in
	private void doIN_OR_NOT_IN(List oneLine){
		String oper=(String)oneLine.get(0);
		String t1=(String)oneLine.get(1);
		String t2=(String)oneLine.get(2);
		String t=(String)oneLine.get(3);
		WhereTemp wt=new WhereTemp();
		wt.setTempName(t);
		wt.setType("boolean");
		boolean resultBol=false;
		
		//默认是in
		
		if(t1.startsWith("\"")){   //字符串
			t1=t1.substring(1,t1.length()-1);
		}else if(t1.startsWith("T")&&t1.indexOf(".")==-1||t1.toUpperCase().equals(t1.toLowerCase())&&!t1.startsWith("\"")){  //变量或数字
			//System.out.println(t1+" sdf sdf");
			t1=this.getValueByTx(t1)+"";
			//System.out.println(t1+" sdf sdf");
			if(t1.endsWith(".0")){
				t1=t1.substring(0,t1.length()-2);
			}
			
			 
		}else if(!t1.toUpperCase().equals(t1.toLowerCase())&&!t1.startsWith("\"")){
			ColumnTemp ct=this.getColumnTempByFullName(t1);
			t1=(String)this.oneRecord.get(ct.getId());
			t1=t1.trim();
		}
		t1=t1.toUpperCase();
		t2=t2.toUpperCase();
		ResultSetTemp rst=this.getResultSetTempByTx(t2);
		if(rst!=null){   //是子查询
			if(rst.getResultSet().getSelColumnsList().size()==1){  //只有一个列
				List recordList=rst.getResultSet().getRecordList();
				int recordSize=recordList.size();
				for(int i=0;i<recordSize;i++){
					System.out.println(recordSize);
					List oneReocrdList=(List)recordList.get(i);
					String strTemp=(String)oneReocrdList.get(0);  //取第一个 因为也只有一个
					System.out.println(t1+" "+strTemp);
					if(t1.equals(strTemp.toUpperCase())){
						resultBol=true;
						break;
					}
				}
			}else{
				resultBol=false;
			}
		}else {    //是字符常量
			WhereTemp wtT=this.getWhereTempByTx(t2);
			List list=(List)wtT.getObject();
			int size=list.size();
			for(int i=0;i<size;i++){
				String strTemp=(String)list.get(i);
				//System.out.println(t1+" "+strTemp);
				if(t1.equals(strTemp.toUpperCase())){
					resultBol=true;
					break;
				}
			}
		}
		if(oper.equals("NOT_IN")){
			resultBol=!resultBol;
		}
		wt.setObject(resultBol);
		this.tempList.add(wt);

		
	}
	
	//add
	private void doADD(List oneLine){
		String oper=(String)oneLine.get(0);
		String t1=(String)oneLine.get(1);
		String t2=(String)oneLine.get(2);
		String t=(String)oneLine.get(3);
		
		WhereTemp wt=this.getWhereTempByTx(t1);
		wt.setTempName(t);
		if(wt.getType().equals("LIST")){
			if(t2.startsWith("\"")){
				t2=t2.substring(1, t2.length()-1);
			}else{
				t2=t2;
			}
			
			//System.out.println("t2dd:"+t2);
			((List)wt.getObject()).add(t2);
		}
		this.tempList.add(wt);
		//System.out.println(((List)wt.getObject()).size()+" ...........");
	}
	//new
	private void doNEW(List oneLine){
		String oper=(String)oneLine.get(0);
		String t1=(String)oneLine.get(1);
		String t2=(String)oneLine.get(2);
		String t=(String)oneLine.get(3);
		WhereTemp wt=new WhereTemp();
		wt.setTempName(t);
		List list=new ArrayList();
		wt.setObject(list);
		wt.setType(t1);
		this.tempList.add(wt);
	}
	
	//处理IS_NOT_NULL  IS_NULL 
	private void doIS_NULL_OR_NOT(List oneLine){
		//IS_NOT_NULL  IS_NULL
		String oper=(String)oneLine.get(0);
		String t1=(String)oneLine.get(1);
		String t2=(String)oneLine.get(2);
		String t=(String)oneLine.get(3);
		WhereTemp wt=new WhereTemp();
		wt.setTempName(t);
		wt.setType("boolean");
		boolean resultBol=false;
		//默认是is not null
		if(t1.startsWith("\"")){   //是字符串
			resultBol=true;
		}else if(t1.startsWith("T")&&t1.indexOf(".")==-1){ //是临时变量
			resultBol=true;
		}else if(t1.toUpperCase().equals(t1.toLowerCase())&& !t1.startsWith("\"")){
			resultBol=true;   //是数值
		}else {   //是列
			ColumnTemp colTemp=this.getColumnTempByFullName(t1);
			

			int leng=((String)this.oneRecord.get(colTemp.getId())).trim().length();
			if(leng>0){
				resultBol=true; 
			}else{
				resultBol=false;
			}
		}
		if(oper.equals("IS_NULL")){
			resultBol=!resultBol;
		}
		wt.setObject(resultBol);
		this.tempList.add(wt);
	}
	//处理exists   ................................................还没有测试
	private void doEXISTS(List oneLine){
		String oper=(String)oneLine.get(0);
		String t1=(String)oneLine.get(1);
		String t2=(String)oneLine.get(2);
		String t=(String)oneLine.get(3);
		WhereTemp wt=new WhereTemp();
		wt.setTempName(t);
		wt.setType("boolean");
		ResultSetTemp rst=this.getResultSetTempByTx(t1);
		//子查询的个数>0说明存在
		if(rst.getResultSet().getRecordList().size()>0){
			wt.setObject(true);
		}
		this.tempList.add(wt);
	}
	//处理not
	private void doNOT(List oneLine){
		String oper=(String)oneLine.get(0);
		String t1=(String)oneLine.get(1);
		//System.out.println("not t1:"+t1);
		String t2=(String)oneLine.get(2);
		String t=(String)oneLine.get(3);
		WhereTemp wt=new WhereTemp();
		wt.setTempName(t);
		wt.setType("boolean");
		int size=this.tempList.size();
		WhereTemp wtT=(WhereTemp)this.getWhereTempByTx(t1);
		boolean resultBol=!(Boolean)wtT.getObject();
		
		System.out.println(resultBol);
		
		wt.setObject(resultBol);
		this.tempList.add(wt);
	}
	
	//= <> > < >= <=操作
	private void doCompare(List oneLine){
		String oper=(String)oneLine.get(0);
		String t1=(String)oneLine.get(1);
		String t2=(String)oneLine.get(2);
		String t=(String)oneLine.get(3);
		WhereTemp wt=new WhereTemp();
		wt.setTempName(t);
		wt.setType("BOOLEAN");
		
		double dt1=this.getValueByTx(t1);
		double dt2=this.getValueByTx(t2);
		String st1="";
		String st2=" ";
		//两个数
		//两个
		
		boolean resultBol=false;
		if(oper.equals("=")||oper.equals("<>")){
			//不是列名 和 不是 “ 开头的   就是列
			int strNum=0;  //是字符串的个数
			if(t1.startsWith("\"")){
				st1=t1.substring(1, t1.length()-1);
				strNum++;
			}else if(t1.indexOf(".")!=-1&&!t1.toUpperCase().equals(t1.toLowerCase())&&!t1.startsWith("\"")){
				//System.out.println(t1+"  t1");
				ColumnTemp ct=this.getColumnTempByFullName(t1);
				//System.out.println(ct.getColumn().getType());
				//是字符类型
				if(!ct.getColumn().getType().equals("INTEGER")&&!ct.getColumn().getType().equals("DOUBLE")){
					strNum++;
					st1=(String)this.oneRecord.get(ct.getId());
				}
			}
			if(t2.startsWith("\"")){
				st2=t2.substring(1, t2.length()-1);
				strNum++;
			}else if(t2.indexOf(".")!=-1&&!t2.toUpperCase().equals(t2.toLowerCase())&&!t2.startsWith("\"")){
				ColumnTemp ct=this.getColumnTempByFullName(t2);
				//是字符类型
				if(!ct.getColumn().getType().equals("INTEGER")&&!ct.getColumn().getType().equals("DOUBLE")){
					strNum++;
					st2=(String)this.oneRecord.get(ct.getId());
				}
			}
			//System.out.println("str1:"+st1+" str2:"+st2);
			if(strNum==2){
				if(st1.toUpperCase().equals(st2.toUpperCase())){
					resultBol=true;
					
				}else{
					resultBol=false;
					
				}
			}else{
				//System.out.println(dt1+" ... "+dt2);
				if(dt1==dt2){
					resultBol=true;
				
				}else{
					resultBol=false;
						
				}
			}
			if(oper.equals("<>")){
				resultBol=!resultBol;
			}
			
			
		}else{
			if(oper.equals(">")){
				if(dt1>dt2){
					resultBol=true;
				}else{
					resultBol=false;
				}
			}else if(oper.equals(">=")){
				if(dt1>=dt2){
					resultBol=true;
				}else{
					resultBol=false;
				}
			}else if(oper.equals("<")){
				if(dt1<dt2){
					resultBol=true;
				}else{
					resultBol=false;
				}
			}else if(oper.equals("<=")){
				if(dt1<=dt2){
					resultBol=true;
				}else{
					resultBol=false;
				}
			}
		}
		wt.setObject(resultBol);
		this.tempList.add(wt);
	}
	//+ - * / 操作
	private void doCompute(List oneLine){
		String oper=(String)oneLine.get(0);
		String t1=(String)oneLine.get(1);
		String t2=(String)oneLine.get(2);
		String t=(String)oneLine.get(3);
		WhereTemp wt=new WhereTemp();
		wt.setTempName(t);
		double dt1=this.getValueByTx(t1);
		double dt2=this.getValueByTx(t2);
		double result=0;
		
		if(oper.equals("+")){
			//System.out.println("dt1:"+dt1+" dt2:"+dt2);
			result=dt1+dt2;
		}else if(oper.equals("-")){
			result=dt1-dt2;
		}else if(oper.equals("*")){
			result=dt1*dt2;
		}else if(oper.equals("/")){
			result=dt1/dt2;
		}

		wt.setObject(result);
		wt.setType("DOUBLE");
		
		//System.out.println(t+"  sdfsdfsdfsdfsdfsdf");
		
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
			WhereTemp wt=this.getWhereTempByTx(tx);
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
	//获取一个子查询 通过临时变量 tx
	private ResultSetTemp getResultSetTempByTx(String tx){
		ResultSetTemp rst=null;
		int size=this.resultSetTempList.size();
		for(int i=0;i<size;i++){
			//System.out.println("size:"+size);
			//System.out.println("tx:"+tx);
			ResultSetTemp rstT=(ResultSetTemp)this.resultSetTempList.get(i);
			if(rstT.getTempName().equals(tx)){
				rst=rstT;
				break;
			}
		}
		return rst;
	}
	//获得中间的值
	private WhereTemp getWhereTempByTx(String tx){
		WhereTemp wt=null;
		int size=this.tempList.size();
		for(int i=0;i<size;i++){
			WhereTemp wtT=(WhereTemp)this.tempList.get(i);
			if(wtT.getTempName().equals(tx)){
				wt=wtT;
				break;
			}
		}
		return wt;
		
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
