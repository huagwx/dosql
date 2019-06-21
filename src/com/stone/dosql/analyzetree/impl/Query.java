package com.stone.dosql.analyzetree.impl;

import java.util.ArrayList;
import java.util.List;

import com.stone.dosql.util.Syntax;

public class Query {
	private List queryList=new ArrayList();
	private Syntax syntaxInput;
	
	private String query="query_spec";
	private String subQuery="subquery";
	
	public Query(Syntax syntax) {
		this.syntaxInput=syntax;
		this.doQuerySyntax(this.syntaxInput);
	}
	
	public List getQueryList(){
		
		return this.queryList;
	}

	private void doQuerySyntax(Syntax syntax){
		if(syntax.getLeft().getName().equals(this.query)||syntax.getLeft().getName().equals(this.subQuery)){
			this.queryList.add(syntax);
		}
			
		int i=syntax.getRight().size();
		if(i>0){
			for(int j=0;j<i;j++){
					this.doQuerySyntax(syntax.getRight().get(j));
			}
		}
			
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
