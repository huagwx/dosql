package com.stone.dosql.analyzetree.impl;

import com.stone.dosql.accessories.ST_TYPE;
import com.stone.dosql.util.Syntax;

public class Order_by_clause {
	private String opt_order_by_clause="opt_order_by_clause";
	private String subquery="subquery";
	private Syntax syntaxTree;
	private Syntax orderbySyntax;
	private boolean isFind=false;
	public Order_by_clause(Syntax syntaxTree) {
		this.syntaxTree=syntaxTree;
	}
	public Syntax getOrderBySyntax(){
		this.doAction(this.syntaxTree);
		return this.orderbySyntax;
	}
	private void doAction(Syntax syntax){
		if(!isFind){
			if(syntax.getLeft().getType()==ST_TYPE.ST_VN&&syntax.getLeft().getName().equals(this.opt_order_by_clause)){
	
				this.orderbySyntax=syntax;
				isFind=true;
			}else{
				int i=syntax.getRight().size();
				for(int j=0;j<i;j++){
					Syntax s=(Syntax)syntax.getRight().get(j);
					if(s.getLeft().getName().equals(this.subquery)){
						continue;
					}
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
