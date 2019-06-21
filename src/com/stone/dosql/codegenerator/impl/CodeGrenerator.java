package com.stone.dosql.codegenerator.impl;

import java.util.List;

import com.stone.dosql.util.Syntax;

public class CodeGrenerator {
	private Syntax syntaxTree;
	private List tableList;
	private List columnList;
	private List conditionList;
	private List resultList;
	
	public CodeGrenerator(Syntax syntaxTree) {
		this.syntaxTree=syntaxTree;
	}
	//1得到table
	//2 修改列的信息为table.
	public void analySyntaxTree(){
		
	}
	public List getTableList(){
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
