package com.stone.dosql.accessories;

public interface ST_VT {
	
	//key
	public static final int ST_SELECT_ID=0; 
	public static final int ST_FROM_ID=1; 
	public static final int ST_WHERE_ID=2;
	public static final int ST_GROUP_ID=3;
	public static final int ST_BY_ID=4;
	public static final int ST_HAVING_ID=5;
	public static final int ST_ORDER_ID=6;
	public static final int ST_ALL_ID =7;
	public static final int ST_DISTINCT_ID=8;
	public static final int ST_AS_ID=9;
	public static final int ST_ASC_ID=10;
	public static final int ST_DESC_ID=11;
    public static final int ST_DOT_ID=12; //.	        
	public static final int ST_NOT_ID =14; //NOT
	public static final int ST_NULL_ID=15; //NULL
	public static final int ST_IN_ID=16; //in
	public static final int ST_LIKE_ID=17; //like
	public static final int ST_BETWEEN_ID=18;//between
	public static final int ST_AND_ID=19; //and
	public static final int ST_OR_ID=20;//or
	public static final int ST_ANY_ID=21; //any
	public static final int ST_EXISTS_ID=22;//exists
	public static final int ST_ESCAPE_ID=23; //ESCAPE 通配符
	public static final int ST_IS_ID=24;  //is
	public static final int ST_SOME_ID=25;//some
	
	//oper
	public static final int ST_NOT_EQUATION_ID=100; // <>
	public static final int ST_LESS_ID=101; // <
	public static final int ST_MORE_ID=102; // >
	public static final int ST_LESS_EQUATION_ID = 103; //<=
	public static final int ST_MORE_EQUATION_ID =104; //>=
	public static final int ST_ADD_ID=105; //+
	public static final int ST_MINUS_ID =106; //-
	// * 有两个意思 为 乘 或 所有列  ST_STAR_ID=13
	          public static final int ST_STAR_ID=13; //*
	public static final int ST_DIV_ID =107; // /
	public static final int ST_L_BRACKET_ID=108;  //左括号
	public static final int ST_R_BRACKET_ID=109;  //右括号
	public static final int ST_EQUATION_ID=110;  // =
	

	//界符 bound
	public static final int ST_COMMA_ID=200;  //逗号
	public static final int ST_SINGLE_QUOTES_ID=201; //单引号
	public static final int ST_DOUBLE_QUOTES_ID=202;  //双引号
	
	
	//func
	public static final int ST_COUNT_ID=300;  //count
	public static final int ST_SUM_ID=301;    //sum
	public static final int ST_AVG_ID=302;    //avg
	public static final int ST_MAX_ID=303;    //max
	public static final int ST_MIN_ID=304;    //min
	
	
	
	
	public static final int ST_IDENT_ID=400;  //ident标识符id
	public static final int ST_DIGITAL_CONST_ID=401;  //digitalConst常量id
	public static final int ST_STRING_CONST_ID=402;   //stringConst常量id
	
	public static final int ST_NULL_S_ID=500;  //语法分析退出空
	
	public static final int ST_VT_NOT_FIND_ID=501;
	
	public static final int ST_SHARP_ID=600;
}
