package com.stone.dosql.analyzetree.impl;

import com.stone.dosql.accessories.ST_TYPE;
import com.stone.dosql.util.Syntax;

public class Where_clause {
	private Syntax whereSyntax;
	private Syntax syntaxTree;
	private String opt_where_clause="opt_where_clause";
	boolean isFind=false;
	public Where_clause(Syntax syntaxTree) {
		this.syntaxTree=syntaxTree;
	}
	public Syntax getWhereSyntax(){
		this.doAction(this.syntaxTree);
		return this.whereSyntax;
	}
	private void doAction(Syntax syntax){
		if(!isFind){
			if(syntax.getLeft().getType()==ST_TYPE.ST_VN&&syntax.getLeft().getName().equals(this.opt_where_clause)){
				this.whereSyntax=syntax;
				isFind=true;
			}else{
				int i=syntax.getRight().size();
				for(int j=0;j<i;j++){
					this.doAction(syntax.getRight().get(j));
				}
				
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
