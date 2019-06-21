package com.stone.dosql.util;

import java.util.ArrayList;
import java.util.List;

//对list中的类型是String的clone
public class StringListClone {
	private List inputList;
	private List cloneList=new ArrayList();
	public StringListClone(List inputList) {
		this.inputList=inputList;
		this.doAction();
	}
	private void doAction(){
		int size=this.inputList.size();
		for(int i=0;i<size;i++){
			String str=(String)this.inputList.get(i);
			this.cloneList.add(str);
		}
	}
	public List getStringListClone(){
		return this.cloneList;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
