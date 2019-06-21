package com.stone.dosql.analyzetree.impl;

import java.util.List;

import com.stone.dosql.accessories.ST_APP;
import com.stone.dosql.accessories.ST_TYPE;
import com.stone.dosql.lexical.impl.TokenAnalyze;
import com.stone.dosql.syntax.impl.SyntaxGrenerator;
import com.stone.dosql.util.Syntax;

public class From_clause {
	private String from_clause="from_clause";
	private Syntax syntaxTree;
	private Syntax fromSyntax;
	private boolean isFind=false;
	public From_clause(Syntax syntaxTree) {
		this.syntaxTree=syntaxTree;
	}
    public Syntax getFromSyntax(){
    	this.doFromSyntax(this.syntaxTree);
    	return this.fromSyntax;
    }
	private void doFromSyntax(Syntax syntax){
		if(!isFind){
			if(syntax.getLeft().getType()==ST_TYPE.ST_VN&&syntax.getLeft().getName().equals(this.from_clause)){
				this.fromSyntax=syntax;
				isFind=true;
			}else{
				int i=syntax.getRight().size();
				for(int j=0;j<i;j++){
					
					this.doFromSyntax(syntax.getRight().get(j));
				}
				
			}

		}

	}
	


	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String testStr = ST_APP.testSql;
		System.out.println("testStr:"+testStr);
       TokenAnalyze ta = new TokenAnalyze(testStr);
	   List tokens=ta.getTokens();
	   SyntaxGrenerator sa=new SyntaxGrenerator(ST_APP.DBMS_SQL_SELECT_EXP,tokens,ST_APP.ST_query_spec);
	   
	   Syntax syntax=sa.getSyntaxTree();  //得到语法树
	   
	   
	   Query query=new Query(syntax);

	   

	}
	//显示查找到的talbe
	private void showTree(Syntax syntax){
		
		int i=syntax.getRight().size();
		if(i==0){
			if(syntax.getLeft().getType()!=ST_TYPE.ST_NULL_VT){
				System.out.print(" "+syntax.getLeft().getName());
			}
			
		}else {
			for(int j=0;j<i;j++){
				Syntax s=(Syntax)syntax.getRight().get(j);
				System.out.println(".. "+s.getLeft().getName()+"..");
				showTree(s);
			}
		}
		
	}
	
	

}
