package com.stone.dosql.sqlcompiler.ll1.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.stone.dosql.accessories.ST_APP;
import com.stone.dosql.accessories.ST_TYPE_ARRAY;
import com.stone.dosql.lexical.impl.SqlTokens;
import com.stone.dosql.util.Token;

public class SyntaxFile {
	private String selectFile;
	
	public SyntaxFile(String selectFile) {
		this.selectFile=selectFile;
	}
	
	//获得终结符的列表list
	public List getVtList(){	
		List vtList=new ArrayList();		
		SqlTokens st=new SqlTokens();
		
		List allkob=new ArrayList();
		allkob=st.getKeyTokens();
		for(int i=0;i<st.getBoundTokens().size();i++){
			allkob.add(st.getBoundTokens().get(i));
		}
		
		for(int i=0;i<st.getOperTokens().size();i++){
			allkob.add(st.getOperTokens().get(i));
			
		}

		
		
		boolean isFileExist=true;
		FileReader fileReader=null;
		try {
			fileReader = new  FileReader(this.selectFile);
		}catch(FileNotFoundException e) {
			isFileExist=false;
		}
		if(isFileExist){
			BufferedReader readFileIn = new  BufferedReader(fileReader); 
	        String oneLine=null;     
			try {
				

				
				while((oneLine=readFileIn.readLine())!=null){  
			     if(oneLine != null && oneLine.length() > 1 && !oneLine.trim().startsWith("//")){
					 StringTokenizer tokenizer=new StringTokenizer(oneLine," ");//" "为分隔符
					 int number=tokenizer.countTokens();
					 while(tokenizer.hasMoreTokens()){
						 
						 boolean isVt=false;
						 String str = tokenizer.nextToken().toString().trim();
				    	 
						 for(int j=0;j<allkob.size();j++){
							 boolean ise=false;
							 String kobs=((Token)allkob.get(j)).getWordUpper();
							
							 if(str.toUpperCase().equals(kobs)){
									 for(int k=0;k<vtList.size();k++){
										 String svt=(String)vtList.get(k);
										 if(kobs.equals(svt)){
											 ise=true;
										 }
									 }
									 if(!ise){
										 vtList.add(str);
									 }
								 
							 }

						 }

						 
					 }
			     }
 
			    }
			    readFileIn.close();
			}catch (IOException e) {
				isFileExist=false;
			}   
//			for(int i=0;i<allkob.size();i++){
//				String si=(String)((Token)allkob.get(i)).getWordUpper();
//				
//				for(int j=0;j<vtList.size();j++){
//					String sj=(String)vtList.get(j);
//					if(si.equals(sj)){
//						((Token)allkob.get(i)).setWordUpper("...");
//						break;
//					}
//				}
//			}
//			for(int i=0;i<allkob.size();i++){
//				System.out.println(((Token)allkob.get(i)).getWordUpper());
//			}
//			
//			System.out.println("listVT.size():"+vtList.size());
			
		}

		
		
	//添加 	public static final String[] various={
	//	"DIGITALCONST","IDENT","STRINGCONST"
	//	};
		for(int vi=0;vi<ST_TYPE_ARRAY.various.length;vi++){
			vtList.add(ST_TYPE_ARRAY.various[vi]);
		}
		return vtList;
	}
	
	//获取非终结符的列表list
	public List getVnList(){  
		

		
		List vnList=new ArrayList();
		boolean exist;		
		boolean isFileExist=true;
		FileReader fileReader=null;
		try {
			fileReader = new  FileReader(this.selectFile);
		}catch(FileNotFoundException e) {
			isFileExist=false;
		}
		if(isFileExist){
			BufferedReader readFileIn = new  BufferedReader(fileReader); 
	        String oneLine=null;    
			try {
				while((oneLine=readFileIn.readLine())!=null){  
					exist=false;
			     if(oneLine != null && oneLine.trim().length() > 1 && !oneLine.trim().startsWith("//")){
			    	 StringTokenizer tokenizer=new StringTokenizer(oneLine," ");//" "空格为分隔符
			    	 String str = tokenizer.nextToken().toString().trim();
			    	 

			    		 
			    	 for(int i=0;i<vnList.size();i++){
			    		 String s=(String)vnList.get(i);
			    		 if(str.equals(s)){
			    			 exist=true;
			    		 }
			    	 }
			    	 if(!exist){
			    		 vnList.add(str);
			    	 }
			     }
 
				}
			    readFileIn.close();
			}catch (IOException e) {
				isFileExist=false;
			} 
		}
//		for(int i=0;i<vnList.size();i++){
//			String s=(String)vnList.get(i);
//			System.out.println(s);
//		}
//		System.out.println("size:"+vnList.size()+"............................................");
//		
		return vnList;
	}
	
	public List getEveryGrammarWord(){
		List allList=new ArrayList();
		boolean isFileExist=true;
		FileReader fileReader=null;
		try {
			fileReader = new  FileReader(this.selectFile);
		}catch(FileNotFoundException e) {
			isFileExist=false;
		}
		if(isFileExist){
			BufferedReader readFileIn = new  BufferedReader(fileReader); 
	        String oneLine=null;   
	        int lineId=0;
			try {				
				while((oneLine=readFileIn.readLine())!=null){  
					lineId++;
					List oneLineWord=new ArrayList();
					if(oneLine != null && oneLine.length() > 1 && !oneLine.trim().startsWith("//")){
						//System.out.println("lineID:"+lineId);
						StringTokenizer tokenizer=new StringTokenizer(oneLine," ");//" "为分隔符
						
						while(tokenizer.hasMoreTokens()){						 
							boolean isVt=false;
							String str = tokenizer.nextToken().toString().trim();		
						 
							//去掉 -> 第二个
							if(str.equals("->")){
							 str = tokenizer.nextToken().toString().trim();		
							}
						    oneLineWord.add(str);
							
						 
						}
						if(oneLineWord.size()>0){
							allList.add(oneLineWord);
						}
					}

 
				}
			    readFileIn.close();
			}catch (IOException e) {
				isFileExist=false;
			}  
			//System.out.println("size::::"+allList.size());
		}
		
		return allList;
	}
	public List getAttributeList(){
		List allList=new ArrayList();
		boolean isFileExist=true;
		FileReader fileReader=null;
		try {
			fileReader = new  FileReader(this.selectFile);
		}catch(FileNotFoundException e) {
			isFileExist=false;
		}
		if(isFileExist){
			BufferedReader readFileIn = new  BufferedReader(fileReader); 
	        String oneLine=null;   
	        boolean is=false;
	        int lineId=0;
			try {				
				while((oneLine=readFileIn.readLine())!=null){  
					lineId++;
					List oneLineWord=new ArrayList();
					if(oneLine != null && oneLine.length() > 1 && !oneLine.trim().startsWith("//")){
						//System.out.println("lineID:"+lineId);
						StringTokenizer tokenizer=new StringTokenizer(oneLine," ");//" "为分隔符
						is=!is;
						while(tokenizer.hasMoreTokens()){						 
							boolean isVt=false;
							String str = tokenizer.nextToken().toString().trim();		
						 
							//去掉 -> 第二个
							if(str.equals("->")){
							 str = "=";		
							}
						    oneLineWord.add(str);
							
						 
						}
						if(is){
							if(oneLineWord.size()>0){
								int size=oneLineWord.size();
								for(int i=0;i<size;i++){
									System.out.print((String)oneLineWord.get(i)+" ");
								}
								System.out.println();
								allList.add(oneLineWord);
								
							}
						}

					}

 
				}
			    readFileIn.close();
			}catch (IOException e) {
				isFileExist=false;
			}  
			//System.out.println("size::::"+allList.size());
		}
		
		return allList;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SyntaxFile vav=new SyntaxFile(ST_APP.DBMS_SQL_SELECT_EXP);
		List vtList=vav.getVtList();
		System.out.println(vtList.size());


	}

}
