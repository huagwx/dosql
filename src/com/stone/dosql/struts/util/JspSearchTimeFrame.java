package com.stone.dosql.struts.util;

import java.util.ArrayList;
import java.util.List;

import com.stone.dosql.codegenerator.impl.ResultSet;
import com.stone.dosql.face.Face;

//时间对象 输入输入的字符串和时间
public class JspSearchTimeFrame {
	private JspSelectTime jst;
	private String searchTimeShow;
	private int timeListMaxSize=JspConst.timeListMaxSize;
	private int timeListSize=JspConst.timeList.size();
	public JspSearchTimeFrame(String dbName,String inputSelect,long useTime) {
		jst=new JspSelectTime(dbName,inputSelect,useTime);
		if(timeListMaxSize<=timeListSize){
			JspConst.timeList.remove(0);
		}
		JspConst.timeList.add(jst);
		this.doAction();
	}
	private void doAction(){
		StringBuilder sb=new StringBuilder();
		sb.append("<table  class='table1'>");
		//<tr class='head'><td class='td1'>
		sb.append("<tr class='head'>");
		sb.append("<td class='td1'>");
		sb.append("数据库");
		sb.append("</td>");
		sb.append("<td class='td1'>");
		sb.append("最新查询语句");
		sb.append("</td>");
		sb.append("<td class='td1'>");
		sb.append("查询时间(毫秒)");
		sb.append("</td>");
		sb.append("</tr>");
		int size=JspConst.timeList.size();
		for(int i=size-1;i>=0;i--){
			JspSelectTime jstt=(JspSelectTime)JspConst.timeList.get(i);
			if(i%2==0){
				sb.append("<tr bgcolor='#cccccc'>");
			}else{
				sb.append("<tr>");
			}
			
			sb.append("<td>");
			sb.append(jstt.getDbName());
			sb.append("</td>");
			sb.append("<td>");
			sb.append(jstt.getInputSelect());
			sb.append("</td>");
			sb.append("<td>");
			sb.append(jstt.getTime());
			sb.append("</td>");
			sb.append("</tr>");
		}
		
		sb.append("</table>");
		this.searchTimeShow=sb.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public String getSearchTimeShow() {
		return searchTimeShow;
	}



}
