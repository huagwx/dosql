package com.stone.dosql.struts.util;

import java.util.List;

import com.stone.dosql.face.Face;

public class JspMiddleCodeFrame {
	private Face face;
	private String middlecodeShow;
	private List middleCodeList;
	public JspMiddleCodeFrame(Face face) {
		this.face=face;
		this.middleCodeList=this.face.getMiddleCodeList();
		this.doAction();
	}
	private void doAction(){
		StringBuilder sb=new StringBuilder();
		sb.append("<table  class='table1'>");
		sb.append("<tr class='head' ><td class='td1' align='center'>中间代码</td></tr>");
		int size=this.middleCodeList.size();
		for(int i=0;i<size;i++){
			List oneMc=(List)this.middleCodeList.get(i);
			if(i%2==1){
				sb.append("<tr bgcolor='#cccccc'>"); 
			}else{
				sb.append("<tr>");
			}
			
			int oneSize=oneMc.size();
			sb.append("<td>");
			for(int j=0;j<oneSize;j++){
				
			  sb.append(((String)oneMc.get(j)).toLowerCase()+" &nbsp;&nbsp;&nbsp;&nbsp;");
				
			}
			sb.append("</td>");

			sb.append("</tr>");
		}
		sb.append("<tr bgcolor='#eeeeee'>");
		sb.append("<td>");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;"+JspConst.middleMessage);
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</table>");
		this.middlecodeShow=sb.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public String getMiddlecodeShow() {
		return middlecodeShow;
	}

}
