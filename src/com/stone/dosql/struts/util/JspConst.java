package com.stone.dosql.struts.util;

import java.util.ArrayList;
import java.util.List;

public interface JspConst {
	public static List timeList=new ArrayList();
	public static int timeListMaxSize=10;    //查询时间列表显示个数
	public static boolean isHasDbName=false; //
	
	public static String ShowResulstSet="ShowResulstSet";  //默认当前显示的是resultset
	public static String ShowTokens="ShowTokens";
	public static String ShowMiddleCodes="ShowMiddleCodes";
	public static String dbsSetUrl="stone\\dosql\\data\\";  //所有数据库的位置，用于获取所有的数据库
	
	public static String href_tokens="cifa";
	public static String href_middlecodes="mc";
	public static String href_resultset="result";
	
	public static String chinase_href_tokens="词法分析token代码";
	public static String chinase_href_middlecodes="产生中间代码";
	public static String chinase_href_resultset="select查询结果";
	
	public static String hreftoken="?type="+href_tokens;
	public static String hrefmiddlecode="?type="+href_middlecodes;
	public static String hrefresultset="?type="+href_resultset;
	
	public static String tokenMessage="token代码说明:词法分析的token代码分为6类:关键字，分界符，标识符，数字常量，字符串常量，运算符。";
	public static String middleMessage="中间代码说明:生成中间代码分为4元式和5元式:组成由算符op，第一个和第二运算对象ARG1和ARG2及运算结果RESULT。五元式是对于LIKE，NOT LIKE，BETWEEN ，NOT BETWEEN对操作,其他的都是采用四元式来表示。";
	
}
