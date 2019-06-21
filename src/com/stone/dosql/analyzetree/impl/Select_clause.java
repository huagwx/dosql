package com.stone.dosql.analyzetree.impl;

import com.stone.dosql.accessories.ST_TYPE;
import com.stone.dosql.util.Syntax;

public class Select_clause {
	private String query_spec="query_spec";
	private String subquery="subquery";
	private Syntax syntaxTree;
	private Syntax select_resultSyntax;
	private boolean isFind=false;
	public Select_clause(Syntax syntaxTree) {
		this.syntaxTree=syntaxTree;
	}
	public Syntax getSelect_result_Syntax(){
		this.doAction(this.syntaxTree);
		return this.select_resultSyntax;
	}
	private void doAction(Syntax syntax){
		if(!isFind){
			if(syntax.getLeft().getType()==ST_TYPE.ST_VN&&syntax.getLeft().getName().equals(this.query_spec)||syntax.getLeft().getType()==ST_TYPE.ST_VN&&syntax.getLeft().getName().equals(this.subquery)){
				this.select_resultSyntax=syntax;
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
