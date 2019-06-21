package com.stone.dosql.struts.util;

public class JspNavigator {
	private String navigatorString;
	private String showUrl;

	public JspNavigator(String showUrl) {
		this.showUrl=showUrl;
		this.doAction();
	}
	private void doAction(){
		StringBuilder sb=new StringBuilder();
		sb.append("<table border='0'>");
		sb.append("<tr>");
		sb.append("<td><a href='"+this.showUrl+JspConst.hreftoken+"'>"+JspConst.chinase_href_tokens+"</a>&nbsp;&nbsp;&nbsp;&nbsp;</td>");
		sb.append("<td><a href='"+this.showUrl+JspConst.hrefmiddlecode+"'>"+JspConst.chinase_href_middlecodes+"</a>&nbsp;&nbsp;&nbsp;&nbsp;</td>");
		sb.append("<td><a href='"+this.showUrl+JspConst.hrefresultset+"'>"+JspConst.chinase_href_resultset+"</a>&nbsp;&nbsp;&nbsp;&nbsp;</td>");
		sb.append("</tr>");
		sb.append("</table>");
		this.navigatorString=sb.toString();
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public String getNavigatorString() {
		return navigatorString;
	}

}
