package com.stone.dosql.accessories;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ST_APP {
	//中间代码的ti
	public static int ti=0;
	
	//查找表的信息
	public static String testSql="select all * from user as a where id>0 and Not id<0 or a.id<any(select id from user ) and id between 1 and 2 group by id having id>0 order by id";
	
	public static String DBMS_SQL_SELECT_EXP="stone//dosql//sql_select//select.txt";//select文法的位置
	public static String DBMS_SQL_SELECT_EXP_ATTRIBUTE="stone//dosql//sql_select//attribute2.txt";//select语义的位置
	public static String DATABASE_NAME="test//"; //访问的数据库默认为test
	public static String DBMS_NAME="stone//dosql//data//";//DBMS的路径
	public static String FILE_TABLE=".table"; //表的信息 字段名，类型，大小
	public static String FILE_VIEW=".view"; //视图的信息
	public static String FILE_DAT=".dat"; //表的记录
	
	
	//错误信息
	public static List ERR_List=new ArrayList(); //出错列表
	
	
	//select.txt文件信息
	//推导数 推导出 空的字符串
	public static String ST_NULL_S="ST_NULL_S";
	public static String ST_STRINGCONST="STRINGCONST";
	public static String ST_DIGITALCONST="DIGITALCONST";
	public static String ST_IDENT="IDENT";
	public static String ST_query_spec="query_spec";  // follow的开头同时是在语法分析中加入到文法的开头
	
	
	public static String ST_opt_all_distinct="opt_all_distinct";
	public static String ST_scalar_exp_commalist="scalar_exp_commalist";
	public static String ST_from_clause="from_clause"; //得到table语义分析
	public static List ruleList=new ArrayList();
	
	

	
	
}
