package com.stone.dosql.codegenerator.orderby.impl;


public class OrderByTemp {
	private String name;
	private String orderType;
	public OrderByTemp(String name,String orderType) {
		this.name=name;
		this.orderType=orderType;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

}
