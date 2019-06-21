package com.stone.dosql.optimizesyntax.impl;

import java.util.ArrayList;
import java.util.List;

import com.stone.dosql.util.Syntax;

public class HavingNotGroupBy extends Where{
	
	//table的信息和表达式别名的信息
	private List fromTableList;
	private List scalar_exp_oneList;
	public HavingNotGroupBy(Syntax whereSyntax,List fromTableList,List scalar_exp_oneList) {
		super(whereSyntax,fromTableList,scalar_exp_oneList);
	}
	public List getHavingNotGroupbyErrList(){
		return super.getWhereErrList();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
