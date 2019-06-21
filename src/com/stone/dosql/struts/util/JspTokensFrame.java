package com.stone.dosql.struts.util;


import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.stone.dosql.accessories.ST_TYPE;
import com.stone.dosql.face.Face;
import com.stone.dosql.util.Token;

public class JspTokensFrame {
	//显示一个token的面板
	private Face face;
	private List tokenList;
	private String tokensShow;
	public JspTokensFrame(Face face) {
		
		this.face=face;
		this.tokenList=face.getTokenList();
		this.doAction();
	}
	private void doAction(){
		int size=this.tokenList.size();
		StringBuilder sb=new StringBuilder();
		sb.append("<table class='table1' >");
		sb.append("<tr class='head'><td class='td1'>token单词</td><td class='td1'>类型</td></tr>");
		for(int i=0;i<size-1;i++){
			if(i%2==1){
				sb.append("<tr bgcolor='#cccccc'>"); 
			}else{
				sb.append("<tr>");
			}
			Token token=(Token)this.tokenList.get(i);
			sb.append("<td>");
			sb.append(token.getWord());
			sb.append("</td>");
			sb.append("<td>");
			sb.append(this.getTokenStringByTokenTypeId(token.getType()));
			sb.append("</td>");
			sb.append("</tr>");
		}
		sb.append("<tr bgcolor='#eeeeee'>");
		sb.append("<td colspan='2'>");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;"+JspConst.tokenMessage);
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</table>");
		this.tokensShow=sb.toString();
	}
	private String getTokenStringByTokenTypeId(int tokenTypeId){
		String tokenString="";
		if(tokenTypeId==ST_TYPE.ST_KEY){
			tokenString="关键字";
		}else if(tokenTypeId==ST_TYPE.ST_BOUND){
			tokenString="分界符";
		}else if(tokenTypeId==ST_TYPE.ST_DIGITAL_CONST){
			tokenString="数字常量";
		}else if(tokenTypeId==ST_TYPE.ST_IDENT){
			tokenString="标识符";
		}else if(tokenTypeId==ST_TYPE.ST_OPER){
			tokenString="运算符";
		}else if(tokenTypeId==ST_TYPE.ST_STRING_CONST){
			tokenString="字符串常量";
		}
		return tokenString;
	}
	public String getTokensShow(){
		return this.tokensShow;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
