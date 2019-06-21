package com.stone.dosql.accessories;

public interface ST_TYPE {
	public static final int ST_KEY=0;
	public static final int ST_OPER=1;
	public static final int ST_BOUND=3;
	public static final int ST_IDENT=4;
	public static final int ST_DIGITAL_CONST=5; //数字常量
	public static final int ST_STRING_CONST=6;  //字符串常量
	
	public static final int ST_VN=7;  //非终结符
	public static final int ST_VT=8; //终结符
	public static final int ST_NULL_VT=9; //推导 空
	
	public static final int ST_SHARP=12;  //语法分析的 '#'
	
	//在求follow集合的类型区别
	public static final int SetWord_TYPE_FOLLOW=10;  //FOLLOW
	public static final int SetWord_TYPE_FIRST=11;  //FIRST
	
	
}
