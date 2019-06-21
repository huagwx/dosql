package com.stone.dosql.optimizesyntax.impl;

import com.stone.dosql.accessories.ST_TYPE;
import com.stone.dosql.util.Syntax;

public class Parameter_ref {
	private Syntax paramSyntax;
	private String param="";
	public Parameter_ref(Syntax paramSyntax) {
		this.paramSyntax=paramSyntax;
	}
	public String getParam(){
		this.doAction();
		return this.param;
	}
	private void doAction(){
		this.doRec(this.paramSyntax);
	}
	private void doRec(Syntax syntax){
		if(syntax.getLeft().getType()==ST_TYPE.ST_VT||syntax.getLeft().getTokenType()==ST_TYPE.ST_IDENT){
			this.param=syntax.getLeft().getName();
			
		}
		int size=syntax.getRight().size();
		for(int i=0;i<size;i++){
			Syntax s=(Syntax)syntax.getRight().get(i);
			this.doRec(s);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
