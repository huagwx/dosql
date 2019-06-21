/**
 * 
 */
package com.stone.dosql.codegenerator.from.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.stone.dosql.accessories.ST_APP;

/**
 * @author hwx
 *
 */
public class RecordTable {
	//表的名称
	private String tableName;
	
	//得到表的信息 .table
	private Table table;
	
	//得到表的记录 .dat
	private List records=new ArrayList();
	
	private List errList = new ArrayList();
	
	//数据库地址
	private String dbUrl;
	
	
	/**
	 * 
	 */
	public RecordTable(String tableName,String dbUrl) {
		this.tableName=tableName;
		this.dbUrl=dbUrl;
		//得到table的信息
		table=new Table(this.tableName,this.dbUrl);
		//得到table的记录 
		this.records=this.getRecordByTableName();
	}
	//返回table的信息
	public Table getTable(){
		return this.table;
	}
	//获得错误信息 
	public List getErrList(){
		return this.errList;
	}
	
	//取得某个表的所有记录
	public List getRecordList(){
		return this.records;
	}
	private List getRecordByTableName(){
		List recordTemp=new ArrayList();
		//File f = new File("E:\\eclipse\\workspace\\box\\Data\\2003-3-7-100.inc");
		//InputStreamReader read = new InputStreamReader (new FileInputStream(f),"UTF-8");
		//BufferedReader reader=new BufferedReader(read);
		boolean isFileExist=true;
		FileReader fileReader=null;//读取中间代码文件
		String tableFile=this.dbUrl+this.tableName;
		String recordType=".dat";
		File f;
		InputStreamReader read=null ;
		String recordFile=tableFile+recordType;
		try {
			f=new File(recordFile);
			read = new InputStreamReader (new FileInputStream(f),"UTF-8");
			
		}catch(Exception e) {
			isFileExist=false;
		}
		
		if(isFileExist){
			BufferedReader readFileIn = new  BufferedReader(read); 
	        String oneLine=null;     
			try {
				
				while((oneLine=readFileIn.readLine())!=null){  
			     if(oneLine != null && oneLine.length() > 1){
			    	
					 StringTokenizer tokenizer=new StringTokenizer(oneLine,"||");//" "为分隔符
					 List listTemp=new ArrayList();
					 while(tokenizer.hasMoreTokens()){
						 String str=tokenizer.nextToken();
						 listTemp.add(str);
					 }
					
					 recordTemp.add(listTemp);
			     }
 
			    }
			    readFileIn.close();
			}catch (IOException e) {
				isFileExist=false;
			} 
		}else{
			String err="表:"+this.tableName+"没有找到!";
			this.errList.add(err);
		}
	
		
		
		return recordTemp;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RecordTable rt=new RecordTable("user",ST_APP.DBMS_NAME+ST_APP.DATABASE_NAME);
		for(int k=0;k<rt.getTable().getColumns().size();k++){
			Column col=(Column)rt.getTable().getColumns().get(k);
			System.out.print(" "+rt.getTable().getName()+"."+col.getName());
		}
		System.out.println();
		List list=rt.getRecordList();
		for(int i=0;i<list.size();i++){

			
			List list1=(List)list.get(i);
			for(int j=0;j<list1.size();j++){
				System.out.print(" "+list1.get(j));
			}
			System.out.println();
		}
		

	}

}
