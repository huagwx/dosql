package com.stone.dosql.analyzetree.impl;

import com.stone.dosql.accessories.ST_TYPE;
import com.stone.dosql.util.Syntax;

public class Group_by_clause {
	private String opt_group_by_clause="opt_group_by_clause";
	private Syntax syntaxTree;
	private Syntax groupbySyntax;
	private boolean isFind=false;

    public Syntax getGroupBySyntax(){
    	this.doGroupBySyntax(this.syntaxTree);
    	return this.groupbySyntax;
    }
	public Group_by_clause(Syntax syntaxTree) {
		this.syntaxTree=syntaxTree;
	}
	private String subquery="subquery";
	private void doGroupBySyntax(Syntax syntax){
		if(!isFind){
			if(syntax.getLeft().getType()==ST_TYPE.ST_VN&&syntax.getLeft().getName().equals(this.opt_group_by_clause)){
				this.groupbySyntax=syntax;
				isFind=true;
			}else{
				int i=syntax.getRight().size();
				for(int j=0;j<i;j++){
					Syntax s=(Syntax)syntax.getRight().get(j);
					if(s.getLeft().getName().equals(this.subquery)){
						continue;
					}
					this.doGroupBySyntax(s);
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
