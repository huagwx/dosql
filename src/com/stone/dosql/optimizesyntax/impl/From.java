package com.stone.dosql.optimizesyntax.impl;

import java.util.ArrayList;
import java.util.List;

import com.stone.dosql.accessories.ST_APP;
import com.stone.dosql.accessories.ST_TYPE;
import com.stone.dosql.codegenerator.from.impl.Table;
import com.stone.dosql.lexical.impl.TokenAnalyze;
import com.stone.dosql.syntax.impl.SyntaxGrenerator;
import com.stone.dosql.util.Syntax;

public class From {
	private Syntax fromSyntax;
	private List tableList=new ArrayList();
	private List errListFrom=new ArrayList();
	private String dbUrl;
	
	public From(Syntax fromSyntax,String dbUrl) {
		this.dbUrl=dbUrl;
		this.fromSyntax=fromSyntax;
		this.doAction();
		this.doTableName();
	}
	public List getFromTableList(){
		
		return this.tableList;
	}
	public List getFromErrList(){
		
		return this.errListFrom;
	}
	private void doTableName(){
		//System.out.println(this.tableList.size()+"..........");
		TableName tn=new TableName(this.tableList);
		List eList=tn.getTableNameErrList();
		for(int i=0;i<eList.size();i++){
			this.errListFrom.add(eList.get(i));
		}
	}
	
	private void doAction(){
		this.doRec(this.fromSyntax);
	}
	
	private String table_ref_one="table_ref_one";
	private String subquery="subquery";  //如果是子查询就跳过 
	private void doRec(Syntax syntax){
		if(syntax.getLeft().getType()==ST_TYPE.ST_VN&&syntax.getLeft().getName().equals(this.table_ref_one)){
			this.do_table_ref_one(syntax);
		}
		int size=syntax.getRight().size();
		for(int i=0;i<size;i++){
			Syntax s=(Syntax)syntax.getRight().get(i);
			if(syntax.getLeft().getType()==ST_TYPE.ST_VN&&syntax.getLeft().getName().equals(this.subquery)){
				continue;
			}
			this.doRec(s);
		}
	}
	private void do_table_ref_one(Syntax syntax){
		//table_ref_one -> IDENT parameter_ref
		String tableName=((Syntax)syntax.getRight().get(0)).getLeft().getName();
		Syntax param_ref=(Syntax)syntax.getRight().get(1);
		String tableParam=this.getParam(param_ref);
		//System.out.println(tableParam+".........");
		Table table=new Table(tableName,tableParam,this.dbUrl);
		//添加错误信息 
		List errListTemp=table.getErrList();
		for(int i=0;i<errListTemp.size();i++){
			this.errListFrom.add(errListTemp.get(i));
		}
		this.tableList.add(table);
	}
	private String getParam(Syntax syntax){
		Parameter_ref pr=new Parameter_ref(syntax);
		return pr.getParam();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String testStr = ST_APP.testSql;
		testStr = "select a from c ,c as d,c as c";
		System.out.println("testStr:"+testStr);
       TokenAnalyze ta = new TokenAnalyze(testStr);
	   List tokens=ta.getTokens();
	   SyntaxGrenerator sa=new SyntaxGrenerator(ST_APP.DBMS_SQL_SELECT_EXP,tokens,ST_APP.ST_query_spec);
	   
	   Syntax syntax=sa.getSyntaxTree();  //得到语法树
	   From from=new From(syntax,"数据库地址");
	   List list=from.getFromTableList();
	   TableName tn=new TableName(list);
	   List l=tn.getTableNameErrList();
	   for(int i=0;i<l.size();i++){
		   System.out.println(l.get(i));
	   }
	  
	   
	}

}
