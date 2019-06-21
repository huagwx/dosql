package com.stone.dosql.struts.util;

import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.stone.dosql.codegenerator.from.impl.Column;
import com.stone.dosql.codegenerator.impl.ResultSet;
import com.stone.dosql.face.Face;

public class JspSelectResultFrame {
	private Face face;
	private String resultSetShow;
	public JspSelectResultFrame(Face face) {
		this.face=face;
		this.doAction();
	}
	private void doAction(){
		StringBuilder sb=new StringBuilder();
		ResultSet rs=face.getResultSet();
		int recordSize=rs.getRecordList().size();
		int colSize=rs.getSelColumnsList().size();
		
		sb.append("<table class='table1'>");
		sb.append("<tr class='head'>");
		for(int i=0;i<colSize;i++){
			Column col=(Column)rs.getSelColumnsList().get(i);
			sb.append("<td class='td1'>");
			sb.append(col.getFullName().toLowerCase());
			sb.append("</td>");
				
		}
		sb.append("</tr>");
			
			
			
		for(int i=0;i<recordSize;i++){
			List oneRecord=(List)rs.getRecordList().get(i);
			if(i%2==1){
				sb.append("<tr bgcolor='#cccccc'>"); 
			}else{
				sb.append("<tr>");
			}
			for(int j=0;j<colSize;j++){
				sb.append("<td>");
				sb.append((String)oneRecord.get(j)+"&nbsp;");
				sb.append("</td>");
			}
			sb.append("</tr>");
				
		}

		sb.append("</table>");
		this.resultSetShow=sb.toString();


	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public String getResultSetShow() {
		return resultSetShow;
	}

}
