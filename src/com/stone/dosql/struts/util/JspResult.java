package com.stone.dosql.struts.util;

import java.util.List;

import com.stone.dosql.codegenerator.impl.ResultSet;
import com.stone.dosql.face.Face;

//如果没有错误的时候进行才操作

public class JspResult {
	private JspSearchTimeFrame jspSearchTime;   //查找的时间类
	private JspSelectResultFrame jspSelectResult; //查找的结果类
	private JspMiddleCodeFrame jspMiddleCode;  //查找的中间代码类
	private JspTokensFrame jspTokensFrame;
	private String  jspResultShowType;
	private Face face;     
	private String selectString;
	private String dbName;
	public JspResult(Face face,String dbName,String selectString) {  //没有错误的时候就申请这个对象
		this.face=face;
		this.selectString=selectString;
		this.dbName=dbName;
		jspResultShowType=JspConst.ShowResulstSet;  //默认是显示查找的结果
		this.doAction();
	}
	private void doAction(){
		jspTokensFrame=new JspTokensFrame(face);
		jspSelectResult=new JspSelectResultFrame(face);
		jspMiddleCode=new JspMiddleCodeFrame(face);
		jspSearchTime=new JspSearchTimeFrame(dbName,selectString,face.getUserTime());
	}
	

	public JspSearchTimeFrame getJspSearchTime() {
		return jspSearchTime;
	}
	public JspSelectResultFrame getJspSelectResult() {
		return jspSelectResult;
	}
	public JspMiddleCodeFrame getJspMiddleCode() {
		return jspMiddleCode;
	}
	public JspTokensFrame getJspTokensFrame() {
		
		return jspTokensFrame;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public String getJspResultShowType() {
		return jspResultShowType;
	}
	public void setJspResultShowType(String jspResultShowType) {
		this.jspResultShowType = jspResultShowType;
	}

}
