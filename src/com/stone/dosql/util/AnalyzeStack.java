package com.stone.dosql.util;

import java.util.ArrayList;
import java.util.List;

public class AnalyzeStack {
	private int allFalse=-1;
	private List list;
	private List boolList;
	public AnalyzeStack() {
		list=new ArrayList();
		boolList=new ArrayList();
	}
	public Syntax getTop(){
		int topI=this.getTopFalse();
		if(topI==allFalse){
			System.out.println("AnalyzeStack的list都访问过了");
			return null;
		}else{
			return (Syntax)this.list.get(topI);
		}
		
		
	}
	public void addTop(Syntax syntax){
		list.add(syntax);
		boolList.add(false);
	}
	private int getTopFalse(){
		int falseI=-1;
		for(int i=boolList.size()-1;i>=0;i--){
			if((Boolean)boolList.get(i)==false){
				falseI= i;
				boolList.set(i, true);
				break;
				
			}
		}
		return falseI;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
