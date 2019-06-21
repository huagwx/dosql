package com.stone.dosql.util;

import java.util.ArrayList;
import java.util.List;

public class SelectSetWord {
	private Syntax syntax;
	private List set;

	public SelectSetWord() {
		set=new ArrayList();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public Syntax getSyntax() {
		return syntax;
	}

	public void setSyntax(Syntax syntax) {
		this.syntax = syntax;
	}

	public List getSet() {
		return set;
	}

	public void setSet(List set) {
		this.set = set;
	}

}
