package com.stone.dosql.syntax.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.stone.dosql.accessories.ST_APP;
import com.stone.dosql.accessories.ST_TYPE;
import com.stone.dosql.analyzetree.impl.From_clause;
import com.stone.dosql.analyzetree.impl.Group_by_clause;
import com.stone.dosql.analyzetree.impl.Having_clause;
import com.stone.dosql.analyzetree.impl.Order_by_clause;
import com.stone.dosql.analyzetree.impl.Query;
import com.stone.dosql.analyzetree.impl.Select_clause;
import com.stone.dosql.analyzetree.impl.Where_clause;
import com.stone.dosql.codegenerator.from.impl.Column;
import com.stone.dosql.codegenerator.from.impl.Table;
import com.stone.dosql.codegenerator.impl.AnalyzeSyntax;
import com.stone.dosql.codegenerator.impl.EveryQuery;
import com.stone.dosql.lexical.impl.TokenAnalyze;
import com.stone.dosql.optimizesyntax.impl.From;
import com.stone.dosql.optimizesyntax.impl.Group_by;
import com.stone.dosql.optimizesyntax.impl.HavingHasGroupBy;
import com.stone.dosql.optimizesyntax.impl.Order_by;
import com.stone.dosql.optimizesyntax.impl.Select;
import com.stone.dosql.optimizesyntax.impl.Where;
import com.stone.dosql.sqlcompiler.ll1.impl.LL1;
import com.stone.dosql.sqlcompiler.ll1.impl.Syntaxs;
import com.stone.dosql.util.AnalyzeStack;
import com.stone.dosql.util.Syntax;
import com.stone.dosql.util.SyntaxClone;
import com.stone.dosql.util.Token;
import com.stone.dosql.util.VWord;

public class SyntaxGrenerator {
	private String ident="IDENT";
	private String digitalConst="DIGITALCONST";
	private String stringConst="STRINGCONST";
	private String sharp="#";
	private Syntax[][] forecastAnalyzeTable;
	private List tokens;  //token列表
	private AnalyzeStack analyzeStack;  //VWord类型分析栈
	private Stack tokensStack=new Stack();//剩余输入串的栈
	private String sharpStr="#";
	private String startSyntaxString;
	
	private List errList=new ArrayList();
	
	
	private static LL1 ll1;
	private static boolean hasLL1=false;
	
	private Syntax syntaxTree; //语法分析树
	Token sharpToken=new Token();
	
	//取得文法的list
	private List allSyntaxs;
	
	//语法有误信息
	public List getErrList(){
		return this.errList;
	}
	
	public SyntaxGrenerator(String selectFile,List tokens,String startSyntaxString) {
		if(!SyntaxGrenerator.hasLL1){
			System.out.println("hasLL1:false");
			ll1=new LL1(selectFile);
			SyntaxGrenerator.hasLL1=true;
		}else{
			System.out.println("hasLL1:true");
		}
		
		forecastAnalyzeTable=ll1.getForecastAnalyzeTable();
		this.tokens=tokens;
		this.analyzeStack=new AnalyzeStack();
		
		VWord sharp=new VWord();
		sharp.setName(this.sharpStr);
		sharp.setType(ST_TYPE.ST_SHARP);
		//sharp.setId(ST_VT.ST_SHARP_ID);
		Syntax synSharp=new Syntax();
		synSharp.setLeft(sharp);
		this.analyzeStack.addTop(synSharp);  //添加 Syntax类型 的 #
		
		
		
		Token token=new Token();
		token.setType(ST_TYPE.ST_SHARP);
		token.setWord(this.sharpStr);
		token.setWordUpper(this.sharpStr);
		//token.setWordId(ST_VT.ST_SHARP_ID);
		
		this.tokens.add(token);  //添加tokens的 #
		this.startSyntaxString=startSyntaxString;  //开始符号
		Syntaxs syntaxs=new Syntaxs(selectFile);
		this.allSyntaxs=syntaxs.getAllSyntaxs();
    	//添加开头非终结符到栈中
    	this.analyzeStack.addTop(this.getFirstSyntaxByFirstName(this.startSyntaxString));
    	this.initInputTokenStack();
    	
    	this.syntaxTree=new Syntax();
    	
    	this.analyzeTokens();
	}
	private void initInputTokenStack(){
		for(int i=this.tokens.size()-1;i>=0;i--){
			this.tokensStack.add(this.tokens.get(i));
			//System.out.println(((Token)tokens.get(i)).getWord()+" : "+((Token)tokens.get(i)).getUPPER());
		}
	}

	private Token getTopToken(){
		return (Token)this.tokensStack.pop();
		
	}
	private Syntax getTopSyntax(){
		return (Syntax)this.analyzeStack.getTop();
	}
	private Syntax getSyntaxOnlyLeft(VWord vword){
		Syntax syntaxOnlyName=new Syntax();
//**********************************************************
		//新建一个vword类型，否则会共用vword的
		VWord vw=new VWord();
		vw.setName(vword.getName());
		vw.setType(vword.getType());
		vw.setRule(vword.getRule());
		vw.setSyntaxResult(vword.getSyntaxResult());
		syntaxOnlyName.setLeft(vw);
		return syntaxOnlyName;
		
	}
	private Syntax getFirstSyntaxByFirstName(String firstName){
		Syntax syntax=new Syntax();
		for(int i=0;i<this.allSyntaxs.size();i++){
			if(((Syntax)this.allSyntaxs.get(i)).getLeft().getName().equals(firstName)){
				syntax.setLeft(((Syntax)this.allSyntaxs.get(i)).getLeft());
				
				break;
			}
		}
		SyntaxClone sClone=new SyntaxClone(syntax);
		Syntax syntaxClone=sClone.getCloneSyntax();
		return syntaxClone;
	}
	
    private void analyzeTokens(){
       try {
		boolean isContinue=true;
		  
		   boolean isFirst=true;
		   while(isContinue){
			   Syntax curSyntax;
			   Syntax syntax=this.getTopSyntax();//分析栈
			   if(isFirst){  //第一个开始符号的vn 加入语法树的开头
				   this.syntaxTree=this.getSyntaxOnlyLeft(syntax.getLeft());
				   curSyntax=this.syntaxTree;
				   isFirst=false;	   
			   }else{  //得到栈顶元素，对应语法数的节点
				   curSyntax=syntax;
			   }
			   
		       int vwordType=syntax.getLeft().getType(); //分析栈顶元素的类型 ST_VN,ST_VT,ST_NULL_VT,ST_SHARP
			   Token token=this.getTopToken();  //ST_KEY,ST_OPER,ST_BOUND,ST_IDENT,ST_DIGITAL_CONST,ST_STRING_CONST
			   int tokenType=token.getType();
		       if(vwordType==ST_TYPE.ST_VN){
		    	   int vnId=this.ll1.getVnIdAtTable(curSyntax.getLeft().getName());
		    	   int vtId=-1;
		    	   if(token.getType()==ST_TYPE.ST_KEY||token.getType()==ST_TYPE.ST_OPER||token.getType()==ST_TYPE.ST_BOUND){
		    		   vtId=this.ll1.getVtIdAtTable(token.getWordUpper()); 
		    		  // System.out.println(".,.,.,."+token.getWordUpper());
		    	   }else if(token.getType()==ST_TYPE.ST_IDENT){
		    		   vtId=this.ll1.getVtIdAtTable(this.ident); 
		    	   }else if(token.getType()==ST_TYPE.ST_DIGITAL_CONST){
		    		   vtId=this.ll1.getVtIdAtTable(this.digitalConst); 
		    	   }else if(token.getType()==ST_TYPE.ST_STRING_CONST){
		    		   vtId=this.ll1.getVtIdAtTable(this.stringConst); 
		    	   }else if(token.getType()==ST_TYPE.ST_SHARP){
		    		   vtId=this.ll1.getVtIdAtTable(this.sharp);
		    	   }

		    	  // System.out.println(curSyntax.getLeft().getName()+" vnId:"+vnId+" "+token.getWordUpper()+" vtId:"+vtId);
		    	   Syntax syntaxTable=this.forecastAnalyzeTable[vnId][vtId];
		    	   
		    	   //克隆syntax
		   		   SyntaxClone sClone=new SyntaxClone(syntaxTable);
				   syntaxTable=sClone.getCloneSyntax();
		    	   
		    	   
		    	   //把属性添加到语法树的当前Syntax的left中

		    	   
		    	   curSyntax.setLeft(syntaxTable.getLeft());
		    	   
		    	   
		    	   List list=syntaxTable.getRight();  //得到对应的产生式
		    	   if(list==null||list.size()<1){  //文法有误
		    		   this.errList.add(token.getWord()+"附近1 语义错误!!");
		    		  // ST_APP.ERR_List.add(token.getWord()+ST_ERROR.ST_SYNTAX);
		    		   break;
		    	   }
		    	   List curSynRightList=new ArrayList();
		    	  // curSyntax.setRight(list);
		    	   for(int i=list.size()-1;i>=0;i--){
		    		   VWord vword=((Syntax)list.get(i)).getLeft();
		    		   Syntax s=this.getSyntaxOnlyLeft(vword);
		    		   curSynRightList.add(s);
		    		  this.analyzeStack.addTop(s);
		    		  
		    		   //this.analyzeStack.add(curSyntax.getRight().get(i));
		    	   }
		    	   for(int i=curSynRightList.size()-1;i>=0;i--){
		    		   curSyntax.getRight().add((Syntax)curSynRightList.get(i));
		    	   }
		    	   this.tokensStack.add(token);
		           continue;
		    	   
		    	  
		       }else if(vwordType==ST_TYPE.ST_VT){
		    	 // System.out.println(token.getWordUpper()+ "  "+curSyntax.getLeft().getName().toUpperCase());
		    	   if(token.getType()==ST_TYPE.ST_KEY||token.getType()==ST_TYPE.ST_OPER||token.getType()==ST_TYPE.ST_BOUND){
		    		   //添加属性 
		    		   curSyntax.getLeft().setTokenType(token.getType()); 
		    		  continue;
		    	   }else if(token.getType()==ST_TYPE.ST_IDENT){
		    		   curSyntax.getLeft().setName(token.getUPPER());
		    		   
		    		   //添加属性 
		    		   curSyntax.getLeft().setTokenType(token.getType());  
		    		   continue;
		    	   }else if(token.getType()==ST_TYPE.ST_DIGITAL_CONST){
		    		   curSyntax.getLeft().setName(token.getUPPER());
		    		   //添加属性 
		    		   curSyntax.getLeft().setTokenType(token.getType());
		    		   continue;
		    	   }else if(token.getType()==ST_TYPE.ST_STRING_CONST){
		    		   curSyntax.getLeft().setName(token.getUPPER());
		    		   
		    		   //添加属性 
		    		   curSyntax.getLeft().setTokenType(token.getType());
		    		   
		    		   continue;
		    	   }else{   //文法有误
		    		  // System.out.println("....f....");
		    		   this.errList.add(token.getWord()+"附近2 语义错误!!");
		    		  // ST_APP.ERR_List.add(token.getWord()+ST_ERROR.ST_SYNTAX);
		    		   break;
		    	   }
		       }else if(vwordType==ST_TYPE.ST_NULL_VT){
		    	   //是空符号，把token填入输入串栈

		    	  // curSyntax.getLeft().setTokenType(token.getType());
		    	   this.tokensStack.add(token);
		       }else if(vwordType==ST_TYPE.ST_SHARP){
		    	   if(token.getWordUpper().equals(curSyntax.getLeft().getName().toUpperCase())){
		    		   isContinue=false;
		    		   break;
		    	   }else{
		    		   //文法有误!
		    		   this.errList.add(token.getWord()+"附近3 语义错误!!");
		    		   //ST_APP.ERR_List.add(token.getWord()+ST_ERROR.ST_SYNTAX);
		    		   isContinue=false;
		    		   break; 
		    	   }
		       }

			  
			   
		   }
//		   for(int i=0;i<ST_APP.ERR_List.size();i++){
//			   
//			   
//			  // System.out.println("语法有误!");
//			  // System.out.println((String)ST_APP.ERR_List.get(i));
//		   }
		   if(this.errList.size()==0){
			   System.out.println("语义没有错误!");
		   }
	} catch (RuntimeException e) {
		this.errList.add("语义错误!!");
		//e.printStackTrace();
	}
    	
    	
    }
    
    
    //得到最终的语法树
	public Syntax getSyntaxTree(){
		
		return this.syntaxTree;
	}
	
	public static void main(String[] args) {
		String testStr = ST_APP.testSql;
		//testStr = "select user.id+1 as f,name as e from user where f=2 and f not in('a','b') group by id having sum(*)>0 order by id";
       // testStr="select id from user where id is not null and name like '2' escape '/' ";
       testStr="select id,name from user where 1=id and id>name or name='sdf'";
		System.out.println("testStr:"+testStr);
       TokenAnalyze ta = new TokenAnalyze(testStr);
	   List tokens=ta.getTokens();
	   SyntaxGrenerator sa=new SyntaxGrenerator(ST_APP.DBMS_SQL_SELECT_EXP,tokens,ST_APP.ST_query_spec);
	   
	   System.out.println("saERROR:......................................................................"+sa.getErrList().size());
	   Syntax syntax=sa.getSyntaxTree();  //得到语法树
	   System.out.println(".........");
	   AnalyzeSyntax as=new AnalyzeSyntax();
	   Query query=new Query(syntax);
	   System.out.println("queryListSize:"+query.getQueryList().size());
	   List queryList=query.getQueryList();
	   for(int k=queryList.size()-1;k>=0;k--){
		   Syntax lastSyntax=(Syntax)queryList.get(k);
		   
		   List err=null;
		   
		   From_clause fc=new From_clause(lastSyntax);
		   Syntax fromSyntax=fc.getFromSyntax();
		   From from=new From(fromSyntax,"");
		   List tableList=from.getFromTableList();
		   
		   for(int i=0;i<tableList.size();i++){
			   Table t=(Table)tableList.get(i);
			   System.out.println(t.getName());
			   List list=t.getColumns();
			   for(int j=0;j<list.size();j++){
				   Column col=(Column)list.get(j);
				   System.out.print(col.getName()+ "  ");
			   }
			   System.out.println();
		   }
		   err=from.getFromErrList();
		   sa.showError(err);
		   System.out.println();
		   System.out.println("from");
		   System.out.println();
		   as.greneratorMiddleCode(fromSyntax);
		   
		   Select_clause sc=new Select_clause(lastSyntax);
		   Syntax selectSyntax=sc.getSelect_result_Syntax();
		   Select select=new Select(selectSyntax,tableList);
		   
		   System.out.println();
		   System.out.println("showTree");
		   sa.showTree(selectSyntax);
		   

		   
		   
		   List scalar_one=select.getScalar_exp_oneList();
		   err=select.getSelect_resultErrList();
		   sa.showError(err);
		   
		   
		   Where_clause wc=new Where_clause(lastSyntax);
		   Syntax whereSyntax=wc.getWhereSyntax();
		   Where where=new Where(whereSyntax,tableList,scalar_one);
		   System.out.println();
		   System.out.println("where");
		   System.out.println();
		   as.greneratorMiddleCode(whereSyntax);
		   
		   Group_by_clause gc=new Group_by_clause(lastSyntax);
		   Syntax groupSyntax=gc.getGroupBySyntax();
		   Group_by gb = new Group_by(groupSyntax,tableList);
		   System.out.println();
		   System.out.println("group_by");
		   System.out.println();
		   as.greneratorMiddleCode(groupSyntax);
		   
		   Having_clause hc=new Having_clause(lastSyntax);
		   Syntax havingSyntax=hc.getHavingSyntax();
		   HavingHasGroupBy having=new HavingHasGroupBy(havingSyntax,tableList,scalar_one);   
		   System.out.println();
		   System.out.println("having");
		   System.out.println();
		   as.greneratorMiddleCode(havingSyntax);
		   
		   Order_by_clause oc=new Order_by_clause(lastSyntax);
		   Syntax orderSyntax=oc.getOrderBySyntax();
		   System.out.println("tablesize:"+tableList.size());
		   Order_by ob=new Order_by(orderSyntax,tableList);	   
		   System.out.println();
		   System.out.println("order");
		   System.out.println();
		   as.greneratorMiddleCode(orderSyntax);
		   
		   
		   //最后输出中间代码的是select
		   System.out.println();
		   System.out.println("select");
		   System.out.println();
		   as.greneratorMiddleCode(selectSyntax);
		   
		   sa.showTree(selectSyntax);
		   System.out.println();
	   }
	   List listt=as.getMiddleCodeList();
	   for(int i=0;i<listt.size();i++){
		   List lis=(List)listt.get(i);
		   for(int j=0;j<lis.size();j++){
			   System.out.print((String)lis.get(j)+"  ");
		   }
		   System.out.println();
	   }
	   System.out.println("................");
	 //  Action action=new Action(listt);
	   EveryQuery eq=new EveryQuery(listt);
	   
	   System.out.println("................");


	}
	public void showError(List list){
		int size=list.size();
		for(int i=0;i<size;i++){
			System.out.println(list.get(i));
		}
	}
	public void showTree(Syntax syntax){
		int i=syntax.getRight().size();
		if(i==0){
			if(syntax.getLeft().getType()!=ST_TYPE.ST_NULL_VT){
				System.out.print(" "+syntax.getLeft().getName());
			}
			
		}else {
			for(int j=0;j<i;j++){
				Syntax s=(Syntax)syntax.getRight().get(j);
				showTree(s);
			}
		}
		
	}
	public void showAttribute(Syntax syntax){
		int i=syntax.getRight().size();
		System.out.println(syntax.getLeft().getName()+" 规则:"+syntax.getLeft().getRule());
		if(i>0){
			for(int j=0;j<i;j++){
				Syntax s=(Syntax)syntax.getRight().get(j);
				this.showAttribute(s);
			}
		}
	}

}
