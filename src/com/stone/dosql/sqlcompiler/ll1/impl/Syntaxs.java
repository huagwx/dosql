package com.stone.dosql.sqlcompiler.ll1.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.stone.dosql.accessories.ST_APP;
import com.stone.dosql.accessories.ST_TYPE;
import com.stone.dosql.util.Syntax;
import com.stone.dosql.util.SyntaxClone;
import com.stone.dosql.util.VWord;

//考虑 退出 空 

//取得文法文件的内容
public class Syntaxs {
	private List<Syntax> syntaxsList;
	private List vnList;
	private List vtList;
	private List grammarsList;
	private String file;
	private SyntaxFile syntaxFile;  //取得文件得非终结符和终结符和每一个文法
	
	
	
	private String type="type";
	private String value="value";
	
	public Syntaxs(String file) {
		syntaxFile=new SyntaxFile(file);	
		//this.vnAndvt=new VnAndVt();
		this.file=file;
		this.syntaxsList=new ArrayList();
		this.vnList=syntaxFile.getVnList();
		this.vtList=syntaxFile.getVtList();
		this.grammarsList=syntaxFile.getEveryGrammarWord();
	}
	
	//处理一个VWord
	private VWord getVWord(int type,String name){
		VWord vword=new VWord();
		vword.setType(type);
		vword.setName(name);
		return vword;
		
	}
	
	//处理左边的标识符
	private VWord getLeft(String left){
		String name=left;

		VWord vWord;
		int type=ST_TYPE.ST_VN;
		vWord=this.getVWord(type,name);

		return vWord;
	}
	
	
    //处理右边的标识符	判断推倒出 **********
	 //** 空**************************
	//******************
	private List getRight(List right){
		List synList=new ArrayList();
		Iterator iter=right.iterator();

		while(iter.hasNext()){
			Syntax syntax;
			
			int type=-1;
			boolean isVt=false;
			boolean isVn=false;
			boolean isVt_Null=false;
			boolean isIdent=false;
			boolean isStringConst=false;
			boolean isDigitalConst=false;
			String name=(String)iter.next();
			
			//推出空
			if(name.equals(ST_APP.ST_NULL_S)){
				type=ST_TYPE.ST_NULL_VT;
				isVt_Null=true;
			}else if(name.equals(ST_APP.ST_IDENT)){
				type=ST_TYPE.ST_VT;
				isIdent=true;
			}else if(name.equals(ST_APP.ST_STRINGCONST)){
				type=ST_TYPE.ST_VT;
				isStringConst=true;
			}else if(name.equals(ST_APP.ST_DIGITALCONST)){
				type=ST_TYPE.ST_VT;
				isDigitalConst=true;
			}else{
				//终结符id
				isVt=this.isVtByName(name);
				if(isVt){
					type=ST_TYPE.ST_VT;
				}else {
					//非终结符
					isVn=this.isVnByName(name);
					if(isVn){
						type=ST_TYPE.ST_VN;
					}
				}
				
				
			}
			
			if(isVt||isVn||isVt_Null||isIdent||isStringConst||isDigitalConst){
				VWord vWord=this.getVWord(type, name);
				syntax=this.getInitSyntax(vWord);
				synList.add(syntax);
				
			}else{
				ST_APP.ERR_List.add(name+"文法文件有错误!");
			}
			
		}
		return synList;
		
	}
	private boolean isVtByName(String name){
		boolean is=false;
		int size_vt=this.vtList.size();
		for(int i=0;i<size_vt;i++){
			String vt=(String)this.vtList.get(i);
			if(vt.equals(name)){
				is=true;
				break;
			}
		}
		return is;
	}
	private boolean isVnByName(String name){
		boolean is=false;
		int size_vn=this.vnList.size();
		for(int i=0;i<size_vn;i++){
			String vn=(String)this.vnList.get(i);
			if(vn.equals(name)){
				is=true;
				break;
			}
		}
		return is;
	}
	
	//得到一个文法的文法树
	private Syntax getInitSyntax(VWord vWord){	
		VWord left=vWord;		
		Syntax syntax = new Syntax();
		syntax.setLeft(vWord);
		return syntax;
	}
	
	public List getAllSyntaxs(){
		ST_APP.ruleList=new ArrayList(); //更新rulelist
		
		Iterator iter1=this.grammarsList.iterator();
		while(iter1.hasNext()){
			List oneGram=(List)iter1.next();
			if(oneGram.size()<2){
				ST_APP.ERR_List.add("文件的文法有错误！或一个文法应该在一行");
				break;
			}else{
				String left=(String)oneGram.get(0);  //左边
				oneGram.remove(0);   //删除掉左边的 标识符
				List right=oneGram;
				VWord vWord=this.getLeft(left);
				List synRight=this.getRight(right);
				Syntax syntax=new Syntax();
				syntax.setLeft(vWord);
				syntax.setRight(synRight);
				
				//添加rule 
				
				String rule=this.getSyntaxRule(syntax);		
//				String s1=syntax.getLeft().getName();
//				
//				if(this.sTemp.equals(s1)){
//					this.i++;
//				}else{
//					this.sTemp=s1;
//					this.i=1;
//				}
//				
//				
//				System.out.println("protected String "+syntax.getLeft().getName()+"_"+i+" = \""+rule+"\";");
//				
//	        	
//	        	String str=syntax.getLeft().getName()+"_"+i;
//	        	String sa="     }else if(rule.equals(this."+str+")){";
//	        	this.outPutList.add(sa);
//	        	String sb="          this.do_"+str+"();";
//				this.outPutList.add(sb);
				
				syntax.getLeft().setRule(rule);
				
				
				this.syntaxsList.add(syntax);
				
			}

			
		}
		
		return this.syntaxsList;
	}
//	private String sTemp="";
//	private int i=1;
//	public List outPutList=new ArrayList();
	//取得规则
	public String getSyntaxRule(Syntax syntax){
		String rule="";
    	Syntax syn=syntax;
    	VWord left=syn.getLeft();
    	List right=syn.getRight();
    	rule+=left.getName()+" ->";
    	for(int j=0;j<right.size();j++){
    		Syntax syn2=(Syntax)right.get(j);
    		VWord left2=syn2.getLeft();
    		rule+="  "+left2.getName();
    	}
    	return rule;
	}


	public static void main(String[] args) {
		Syntaxs syntax=new Syntaxs(ST_APP.DBMS_SQL_SELECT_EXP);
        List list=syntax.getAllSyntaxs();
//        System.out.println(list.size());
//       
//        for(int i=0;i<list.size();i++){
//        	 SyntaxClone sc=new SyntaxClone((Syntax)list.get(i));
//        	Syntax s=sc.getCloneSyntax();
//        	System.out.println(s.getLeft().getRule());
//        }
        
//	}else if(rule.equals(this.COMPARISON_6)){
//		this.doCOMPARISON_6();
        
        for(int i=0;i<list.size();i++){
        	Syntax syn=(Syntax)list.get(i);
        	VWord left=syn.getLeft();
        	List right=syn.getRight();
//        	System.out.print("<tr><td>&nbsp;&nbsp;  "+left.getName()+"&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ");
//        	for(int j=0;j<right.size();j++){
//        		Syntax syn2=(Syntax)right.get(j);
//        		VWord left2=syn2.getLeft();
//        		System.out.print(left2.getName()+"&nbsp;&nbsp; &nbsp;  ");
//        	}
//        	System.out.println("</td></tr>");
        	System.out.print("("+i+") "+left.getName()+" → ");
        	for(int j=0;j<right.size();j++){
        		Syntax syn2=(Syntax)right.get(j);
        		VWord left2=syn2.getLeft();
        		System.out.print(left2.getName()+"  ");
        	}
        	System.out.println();
        }
	}

}
