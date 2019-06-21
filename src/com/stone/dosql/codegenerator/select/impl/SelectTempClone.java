package com.stone.dosql.codegenerator.select.impl;

//对selectTemp进行clone
public class SelectTempClone {
	private SelectTemp st;
	private SelectTemp stClone=new SelectTemp();
	public SelectTempClone(SelectTemp st) {
		this.st=st;
		this.doAction();
	}
	private void doAction(){
		 String type=this.st.getType();
		 System.out.println("type:"+type);
		 String object1=this.st.getObject().toString();
		 double object=Double.valueOf(object1);
		 String tempName=this.st.getTempName();
		 this.stClone.setType(type);
		 this.stClone.setObject(object);
		 this.stClone.setTempName(tempName);
	}
	public SelectTemp getSelectTempClone(){
		return this.stClone;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SelectTemp st=new SelectTemp();
		st.setObject("111");
		st.setTempName("t");
		st.setType("Double");
		SelectTempClone stc=new SelectTempClone(st);
		st=stc.getSelectTempClone();
		System.out.println((Double)st.getObject());

	}

}
