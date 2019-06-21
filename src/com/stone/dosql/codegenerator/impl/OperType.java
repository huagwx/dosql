package com.stone.dosql.codegenerator.impl;

public interface OperType {
	public static final String NEW="NEW";	
	public static final String ADD="ADD";
	
	public static final String RECORDTABLE = "RECORDTABLE";
	public static final String SELCOLUMN="SELCOLUMN";
	
	public static final String RESULTSET="RESULTSET";
	public static final String FROM="FROM";
	public static final String WHERE="WHERE";
	public static final String GROUP_BY="GROUP_BY";
	public static final String HAVING="HAVING";
	public static final String ORDER_BY="ORDER_BY";

	
	public static final String JIA="+";
	public static final String JIAN="-";
	public static final String CHENG="*";
	public static final String CHU="/";
	
	
	public static final String COUNT="COUNT";
	public static final String SUM="SUM";
	public static final String AVG="AVG";
	public static final String MAX="MAX";
	public static final String MIN="MIN";
	
	public static final String AND="AND";
	public static final String OR="OR";
	public static final String NOT = "NOT";
	
	public static final String DENG_YU="=";
	public static final String BU_DENG_YU="<>";
	public static final String XIAO_YU="<";
	public static final String DA_YU=">";
	public static final String XIAO_YU_DENG_YU="<=";
	public static final String DA_YU_DENG_YU=">=";
	
	
	public static final String DENG_YU_ANY="=ANY";
	public static final String DENG_YU_ALL="=ALL";
	public static final String DENG_YU_SOME="=SOME";
	
	public static final String BU_DENG_YU_ANY="<>ANY";
	public static final String BU_DENG_YU_ALL="<>ALL";
	public static final String BU_DENG_YU_SOME="<>SOME";
	
	public static final String XIAO_YU_ANY="<ANY";
	public static final String XIAO_YU_ALL="<ALL";
	public static final String XIAO_YU_SOME="<SOME";
	
	public static final String DA_YU_ANY=">ANY";
	public static final String DA_YU_ALL=">ALL";
	public static final String DA_YU_SOME=">SOME";
	
	public static final String XIAO_YU_DENG_YU_ANY="<=ANY";
	public static final String XIAO_YU_DENG_YU_ALL="<=ALL";
	public static final String XIAO_YU_DENG_YU_SOME="<=SOME";
	
	public static final String DA_YU_DENG_YU_ANY=">=ANY";
	public static final String DA_YU_DENG_YU_ALL=">=ALL";
	public static final String DA_YU_DENG_YU_SOME=">=SOME";
	
	
	
	public static final String EXISTS="EXISTS";
	
	public static final String BETWEEN="BETWEEN";
	public static final String NOT_BETWEEN="NOT_BETWEEN";
	
	public static final String IN="IN";
	public static final String NOT_IN="NOT_IN";
	
	public static final String LIKE="LIKE";
	public static final String NOT_LIKE="NOT_LIKE";
	
	public static final String IS_NOT_NULL="IS_NOT_NULL";
	public static final String IS_NULL="IS_NULL";
	
	public static final String ALL="ALL";
	public static final String DISTINCT="DISTINCT";
	
	public static final String ASC="ASC";
	public static final String DESC="DESC";
	



}
