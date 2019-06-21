package com.stone.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.stone.dosql.accessories.ST_APP;
import com.stone.dosql.lexical.impl.SqlTokens;

public class DosqlTest {

	public DosqlTest() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List listVN=new ArrayList();		
		boolean exist;		
		boolean isFileExist=true;
		FileReader fileReader=null;
		try {
			fileReader = new  FileReader(ST_APP.DBMS_SQL_SELECT_EXP);
		}catch(FileNotFoundException e) {
			isFileExist=false;
		}
		if(isFileExist){
			BufferedReader readFileIn = new  BufferedReader(fileReader); 
	        String oneLine=null;   
			int lineId=0;   
			try {
				while((oneLine=readFileIn.readLine())!=null){  
					exist=false;
				 lineId++; 
			     if(oneLine != null && oneLine.length() > 1 && !oneLine.trim().startsWith("//")){
					 StringTokenizer tokenizer=new StringTokenizer(oneLine," ");//" -> "为分隔符
					 int number=tokenizer.countTokens();
						 String str = tokenizer.nextToken().toString().trim();
						 for(int i=0;i<listVN.size();i++){
							 String s=(String)listVN.get(i);
							 if(str.equals(s)){
								 exist=true;
							 }
						 }
						 if(!exist){
							 listVN.add(str);
						 }
			     }
 
			    }
			    readFileIn.close();
			}catch (IOException e) {
				isFileExist=false;
			}   
			for(int i=0;i<listVN.size();i++){
				String s=(String)listVN.get(i);
				System.out.println(s);
			}
			System.out.println("size:"+listVN.size());
			 
			
			
		}
	}

}
