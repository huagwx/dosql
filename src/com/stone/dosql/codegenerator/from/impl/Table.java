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

//table或view的信息

public class Table {
	private String name;  //名称
	private String otherName;
	private String dbUrl;
	private List columns=new ArrayList();  //列的信息
	
	private List errListTable=new ArrayList();
	
	//private boolean hasError=false;
	
	public Table(String name,String otherName,String dbUrl) {
		this.dbUrl=dbUrl;
		this.name=name;
		this.otherName=otherName;
		//System.out.println("otherName:"+this.otherName);
		this.columns=this.getColumnFromFile();
	}
	public Table(String tableName,String dbUrl){
		this.dbUrl=dbUrl;
		this.name=tableName;
		this.columns=this.getColumnFromFile();	
	}
	public List getErrList(){
		return this.errListTable;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Table t=new Table("user",ST_APP.DBMS_NAME+ST_APP.DATABASE_NAME);
		System.out.println(t.getColumns().size());
		System.out.println("err:"+t.getErrList().size());

	}

	public String getName() {
		return name.toUpperCase();
	}

	public void setName(String name) {
		this.name = name;
	}

	//没有错误的时候返回所有的列
	//有错误的时候返回null
	public List getColumns() {
			return columns;
	}


	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		
		this.otherName = otherName;
	}
	//得到查找的table的所有列 在文件中读取 
	private List getColumnFromFile(){
		List list=new ArrayList();
			boolean isFileExist=true;
			FileReader fileReader=null;//读取中间代码文件
			String tableFile=this.dbUrl+this.name;
			String tabletype=".table";
			String viewtype=".view";
			String realFileTable=tableFile+tabletype;
			String realFileView=tableFile+viewtype;
			
			//System.out.println(realFileTable);
			
			File f;
			InputStreamReader read=null ;
			
			try {
				f=new File(realFileTable);
				read = new InputStreamReader (new FileInputStream(f),"UTF-8");

			}catch(Exception e) {
				isFileExist=false;
			}
			if(!isFileExist){
				isFileExist=true;
				try {
					f=new File(realFileView);
					read = new InputStreamReader (new FileInputStream(f),"UTF-8");
				}catch(Exception e) {
					isFileExist=false;
				}
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
						 //System.out.println("colSize:"+listTemp.size());
						 Column col=new Column((String)listTemp.get(0),(String)listTemp.get(1),(String)listTemp.get(2),(String)listTemp.get(3));
						 list.add(col);
				     }
	 
				    }
				    readFileIn.close();
				}catch (IOException e) {
					isFileExist=false;
				} 
			}else{
				
				String err="表:"+this.name+"没有找到!";
				System.out.println(err);
				this.errListTable.add(err);
				//this.hasError=true;
			}
		
		
		return list;
	}

}
