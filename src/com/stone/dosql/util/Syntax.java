package com.stone.dosql.util;

import java.util.ArrayList;
import java.util.List;

public class Syntax {
	//还没有使用的3个 

	
	
	private VWord left;
	
	private List<Syntax> right;  
	
	public Syntax() {
		left=new VWord();
		right=new ArrayList();
	}
	
	
	public VWord getLeft() {
		return left;
	}

	public void setLeft(VWord left) {
		this.left = left;
	}

	public List<Syntax> getRight() {
		return right;
	}

	public void setRight(List<Syntax> right) {
		this.right = right;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}





}
