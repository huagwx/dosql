package com.stone.dosql.face;

import java.util.ArrayList;
import java.util.List;

import com.stone.dosql.accessories.ST_APP;
import com.stone.dosql.accessories.ST_TYPE;
import com.stone.dosql.analyzetree.impl.From_clause;
import com.stone.dosql.analyzetree.impl.Group_by_clause;
import com.stone.dosql.analyzetree.impl.Having_clause;
import com.stone.dosql.analyzetree.impl.Order_by_clause;
import com.stone.dosql.analyzetree.impl.Query;
import com.stone.dosql.analyzetree.impl.Select_clause;
import com.stone.dosql.analyzetree.impl.Where_clause;
import com.stone.dosql.codegenerator.from.impl.Column;
import com.stone.dosql.codegenerator.from.impl.Table;
import com.stone.dosql.codegenerator.impl.AnalyzeSyntax;
import com.stone.dosql.codegenerator.impl.DoSelectAction;
import com.stone.dosql.codegenerator.impl.EveryQuery;
import com.stone.dosql.codegenerator.impl.ResultSet;
import com.stone.dosql.codegenerator.impl.ResultSetInit;
import com.stone.dosql.codegenerator.impl.ResultSetTemp;
import com.stone.dosql.lexical.impl.TokenAnalyze;
import com.stone.dosql.optimizesyntax.impl.From;
import com.stone.dosql.optimizesyntax.impl.Group_by;
import com.stone.dosql.optimizesyntax.impl.HavingHasGroupBy;
import com.stone.dosql.optimizesyntax.impl.HavingNotGroupBy;
import com.stone.dosql.optimizesyntax.impl.Order_by;
import com.stone.dosql.optimizesyntax.impl.Select;
import com.stone.dosql.optimizesyntax.impl.Where;
import com.stone.dosql.syntax.impl.SyntaxGrenerator;
import com.stone.dosql.util.Syntax;

public class Face {
	//数据库名称
	private String dbUrl;
	//查询语句
	private String selectStr;
	private String selectSyntaxFile="stone//dosql//sql_select//select.txt";
	
	
	
	//数据库地址
	private static final String DBMS_URL="stone//dosql//data//"; //相对路径
	
	
	//查询结果 ,token信息，中间代码,错误信息
	private ResultSet resultSet=new ResultSet();  //结果集合
	private List tokenList;     //token信息
	private List middleCodeList=new ArrayList(); //优化后的中间代码
	private List errList=new ArrayList(); //错误信息
	
	//单机时候的构造函数
	public Face(String dbName,String selectStr) {
		this.dbUrl=this.DBMS_URL+dbName+"//";
		this.selectStr=selectStr;
		
		this.doAnalyze();
	}
	//网络版本 数据库的位置
	private static final String DBMS_REAL_URL="stone\\dosql\\data\\"; //数据绝对路径
	private static final String SELECT_RULE_URL="stone\\dosql\\sql_select\\select.txt"; //select的文法位置
	public Face(String serverUrl,String dbName,String selectStr){
		this.dbUrl=serverUrl.trim()+this.DBMS_REAL_URL+dbName+"\\";
		this.selectSyntaxFile=serverUrl.trim()+this.SELECT_RULE_URL;
		//System.out.println(this.dbUrl);
		//System.out.println(this.selectSyntaxFile);
		this.selectStr=selectStr;
		this.doAnalyze();
		
	}
	public String getSelectError(){
		if(this.errList.size()==0){
			return "";
		}else{
			return (String)this.errList.get(0);
		}
	}
	public List getTokenList() {
		return tokenList;
	}
	public List getMiddleCodeList() {
		return middleCodeList;
	}
	//resultSetTempList存放所有的查询的resultSet
	List resultSetTempList=new ArrayList(); 
	//开始进行查询分析
	private void doAnalyze(){
		long start=System.currentTimeMillis();
		this.doAction();
		//如果没有的话，就返回null 
		long end=System.currentTimeMillis();
		this.userTime=end-start;
	}
	public ResultSet getResultSet(){

		int size=this.resultSetTempList.size();
		//如果存在就返回ResultSetTemp的做后一个就是主查询
		if(size>0&&this.errList.size()==0){
			this.resultSet=((ResultSetTemp)this.resultSetTempList.get(size-1)).getResultSet();
			this.doResultSetAction();
		}

		return this.resultSet;
	}
	private long userTime;
	public long getUserTime(){
		return userTime;
	}
	//对resultSet的类型处理 处理掉为Integer，或Double后面为的 .0
	private void doResultSetAction(){
		int size=this.resultSet.getSelColumnsList().size();
		int recSize=this.resultSet.getRecordList().size();
		
		for(int i=0;i<size;i++){
			Column col=(Column)this.resultSet.getSelColumnsList().get(i);
			if(col.getType().equals("DOUBLE")||col.getType().equals("INTEGER")){
				for(int ri=0;ri<recSize;ri++){
					List oneRecord=(List)this.resultSet.getRecordList().get(ri);
					String curContent=(String)oneRecord.get(i);
					if(curContent!=null&&curContent.trim().length()>0&&curContent.endsWith(".0")){
						curContent=curContent.substring(0,curContent.length()-2);
						((List)this.resultSet.getRecordList().get(ri)).set(i, curContent);
					}
				}
			}
		}
	}
	
	public void doAction(){
		//词法分析
		TokenAnalyze tokenAnalyze=new TokenAnalyze(this.selectStr);
		if(this.analyzeToken(tokenAnalyze)){
			//语法分析正确
			System.out.println("词法分析没有错误");
			List tokens=tokenAnalyze.getTokens();
			
			// .....start....JSP  token代码列表...........
			this.tokenList=tokenAnalyze.getTokens();
			
		    //.........end  JSP  token代码类表......
			
			SyntaxGrenerator sg=new SyntaxGrenerator(this.selectSyntaxFile,tokens,ST_APP.ST_query_spec);
			if(this.analyzeSyntax(sg)){
				//语法分析没有错误
				System.out.println("语法分析没有错误");
				//优化语法树
				//分析语法树
				int columnOrTableTrue=0;
				Syntax syntaxTree=sg.getSyntaxTree();  //得到语法树
				AnalyzeSyntax analyzeSyntax=new AnalyzeSyntax();
				//得到每一个查询或子查询 从先后顺序开始
				Query query=new Query(syntaxTree);
				System.out.println("共有的查询数目为:"+query.getQueryList().size());
				//优化每一个查询 如 column改为table.column
				List queryList=query.getQueryList(); //取得所有的查询
				
				for(int k=queryList.size()-1;k>=0;k--){
					   Syntax lastSyntax=(Syntax)queryList.get(k);
					   
					   From_clause fc=new From_clause(lastSyntax);
					   Syntax fromSyntax=fc.getFromSyntax();
					   
//					   System.out.println("from语法树：");
//					   this.showTree(fromSyntax);
					   
					   From from=new From(fromSyntax,this.dbUrl);
					   if(this.analyzeFrom(from)){
						   
					   }else{
						   columnOrTableTrue++;
						   break;
					   }
					   
					   List tableList=from.getFromTableList();
					   
//					   System.out.println();
//					   System.out.println("from");
//					   System.out.println();
					   analyzeSyntax.greneratorMiddleCode(fromSyntax);
					   
//					   this.showOneQueryMiddleCode(analyzeSyntax.getMiddleCodeList());
					   
//					   //显示查找的table信息
//					   for(int i=0;i<tableList.size();i++){
//						   Table t=(Table)tableList.get(i);
//						   System.out.println(t.getName());
//						   List list=t.getColumns();
//						   for(int j=0;j<list.size();j++){
//							   Column col=(Column)list.get(j);
//							   System.out.print(col.getName()+ "  ");
//						   }
//						   System.out.println();
//					   }
					   

					   
					   
					   Select_clause sc=new Select_clause(lastSyntax);
					   Syntax selectSyntax=sc.getSelect_result_Syntax();
					   
//					   System.out.println("select语法树：");
//					   this.showTree(selectSyntax);
					   
					   Select select=new Select(selectSyntax,tableList);
					   if(this.analyzeSelect(select)){
						   
					   }else{
						   columnOrTableTrue++;
						   break;
					   }
					   
					   List scalar_one=select.getScalar_exp_oneList();
					  // List selectColumnList=select.getSelectColumnList();

					   
					   
					   Where_clause wc=new Where_clause(lastSyntax);
					   Syntax whereSyntax=wc.getWhereSyntax();
//					   
//					   System.out.println("where语法树：");
//					   this.showTree(whereSyntax);
//					   
					   Where where=new Where(whereSyntax,tableList,scalar_one);
					   if(this.analyzeWhere(where)){
						   
					   }else{
						   columnOrTableTrue++;
						   break;
					   }
					   
//					   
//					   System.out.println();
//					   System.out.println("where");
//					   System.out.println();
					   analyzeSyntax.greneratorMiddleCode(whereSyntax);
					   
//					   this.showOneQueryMiddleCode(analyzeSyntax.getMiddleCodeList());
					   
					   int beforeGroupbyId=analyzeSyntax.getMiddleCodeList().size();
					   
//					  System.out.println("K:"+k);
//					  System.out.println("before groupby"+analyzeSyntax.getMiddleCodeList().size());
//					   
					   Group_by_clause gc=new Group_by_clause(lastSyntax);
					   Syntax groupSyntax=gc.getGroupBySyntax();
					   
//					   System.out.println("groupby语法树：");
//					   this.showTree(groupSyntax);
					 
					   
					   Group_by group_by = new Group_by(groupSyntax,tableList);
					   if(this.analyzeGroupBy(group_by)){
						   
					   }else{
						   columnOrTableTrue++;
						   break;
					   }
				
					   
//					   System.out.println();
//					   System.out.println("group_by");
//					   System.out.println();
					   analyzeSyntax.greneratorMiddleCode(groupSyntax);
					   
					  // this.showOneQueryMiddleCode(analyzeSyntax.getMiddleCodeList());
					   
					   int afterGroupbyId=analyzeSyntax.getMiddleCodeList().size();
//					   System.out.println("after groupby:"+analyzeSyntax.getMiddleCodeList().size());
					   
					   Having_clause hc=new Having_clause(lastSyntax);
					   Syntax havingSyntax=hc.getHavingSyntax();
					   
//					   System.out.println("having语法树：");
//					   this.showTree(havingSyntax);
					   
					   //没有groupby
					   if(beforeGroupbyId+1==afterGroupbyId){
//						   System.out.println("face:没有groupby");
						   HavingNotGroupBy havingNotGroupBy=new HavingNotGroupBy(havingSyntax,tableList,scalar_one);
						   if(this.analyzeHavingNotGroupBy(havingNotGroupBy)){
							   
						   }else{
							   columnOrTableTrue++;
							   break;
						   }
						  
					   }else{
						   //有groupby的时候
//						   System.out.println("face:有groupby");
						   HavingHasGroupBy having=new HavingHasGroupBy(havingSyntax,scalar_one,tableList); 
						   if(this.analyzeHaving(having)){
							   
						   }else{
							   columnOrTableTrue++;
							   break;
						   }
					   }
					   
					   

					  
					   
//					   System.out.println();
//					   System.out.println("having");
//					   System.out.println();
					   analyzeSyntax.greneratorMiddleCode(havingSyntax);
					   
					   
					   
					   Order_by_clause oc=new Order_by_clause(lastSyntax);
					   Syntax orderSyntax=oc.getOrderBySyntax();
					   
//					   System.out.println("orderby语法树：");
//					   this.showTree(orderSyntax);
					   
					   Order_by order_by=new Order_by(orderSyntax,tableList);	 
					   if(this.analyzeOrderBy(order_by)){
						   
					   }else{
						   columnOrTableTrue++;
						  
					   }
					   
//					   System.out.println();
//					   System.out.println("order");
//					   System.out.println();
					   analyzeSyntax.greneratorMiddleCode(orderSyntax);
					   
					   
					   //最后输出中间代码的是select
//					   System.out.println();
//					   System.out.println("select");
//					   System.out.println();
					   analyzeSyntax.greneratorMiddleCode(selectSyntax);
					   if(this.doAnalyzeSyntax(analyzeSyntax)){
							
					   }else{
							 columnOrTableTrue++;
							 break;
					  }
				   }
				
				//所有的输入table，或columnd都没有错误
				if(columnOrTableTrue==0){
				//	System.out.println("face所有的输入table，或columnd都没有错误");
					List middleCodeList=analyzeSyntax.getMiddleCodeList();
					
					//显示中间代码
					//this.showMiddleCode(middleCodeList);
					
					//得到每一个查询或子查询的中间代码
					EveryQuery everyQuery=new EveryQuery(middleCodeList);
					//保存每一个子查询后得到的resultSet
					
//resultSetTemp 有查询的结果和中间变量 t1，t2.。。。。等等 
//最后的resultsetTemp为最后查询的结果					
					 
					//所有的子查询中间代码list
					List allMiddleCodeQueryList=everyQuery.getAllQueryList();
					int allMiddleCodeQueryListsize=allMiddleCodeQueryList.size();
					//System.out.println("allMiddleCodesdfsdfsdfsdfsdf的防守对方士大夫士大夫士大夫QueryListsize:"+allMiddleCodeQueryListsize);
					for(int i=0;i<allMiddleCodeQueryListsize;i++){
						List allMiddleCodeListOfOneQuery=(List)allMiddleCodeQueryList.get(i);
						
						
						
						//...........JSP.......start.添加中间代码
						int oneQueryMCSize=allMiddleCodeListOfOneQuery.size();
						for(int mci=0;mci<oneQueryMCSize;mci++){
							List tempmci=(List)allMiddleCodeListOfOneQuery.get(mci);
							this.middleCodeList.add(tempmci);
						}
						
						//...........JSP.....end添加中间代码
						
						
						//显示该子查询的中间代码
						//this.showOneQueryMiddleCode(allMiddleCodeListOfOneQuery);
						
						
						//DoSelectAction传入当前的查询的中间代码和前面子查询的resultsetTemp的list
						DoSelectAction doSelectAction=new DoSelectAction(allMiddleCodeListOfOneQuery,this.resultSetTempList,this.dbUrl);
						this.resultSetTempList.add(doSelectAction.getResultSetTemp());
						
					}
					
					
					
					
				}else{
					this.errList.add("有错误");
				}

			}
		}
		
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String dbName="test";
		String testStr="select id,name from user where (11+(22+1))=id or id>name and name='sdf' and  id in (select * from user) order by name desc,id";
		testStr="select name+1 as dd from user,message where user.id=1 group by name having sum(name)>111 order by user.id";
		
		//testStr="select id+1+2 from user where user.id=1 or user.name='hwx'";
		testStr="select * from user,type where not user.id=1 and type.id=1 and  name between 3 and 5 or not exists(select * from user)";
		testStr = "select * from user where id in('1','3')";
		testStr="select * from user where  name not like '人_' ";
		testStr="select sum(id) as t from user where name='人2' or name='人3'  group by id,name having t>3 order by id ,name";
		testStr="select id+1,name from user order by id desc ,score asc";
		//testStr=ST_APP.testSql;
		//testStr="select id from user where id+3=1+2 or  id+1 not in(select id from user) and name like 'sf%' ";
		testStr="select count(distinct id) as k from user having k+1>0";
		testStr="select id,name from user where id in(select id from user having id>2 order by id desc) and exists(select * from user) order by id desc";
		testStr="select user.*  from user,type  where user.id>2  group by user.id having user.id>4";
		//有问题代码testStr="select * from user where id<4 and id < some(select id from user) and name like '人%_' escape '_'";
		testStr="select * from user where id >=any(select id from user) and name like '人_' order by id desc";
		testStr="select * from user where \r name like '%'";
		testStr="select * from message where 'Message1' in(select message from message)";
		testStr="select id+1 as k,name  from user where  k between 1 and 3";
		testStr="select *, name as k from user as u where u.id+1>2 or user.id in (2,3,5)";
		System.out.println(testStr);
		Face face=new Face(dbName,testStr);
		ResultSet rs=face.getResultSet();
		
		
		//jieguo
		System.out.println("查找的结果");
		int size=rs.getRecordList().size();
		for(int i=0;i<size;i++){
			List oneRecord=(List)rs.getRecordList().get(i);
			int si=oneRecord.size();
			for(int j=0;j<si;j++){
				System.out.print((String)oneRecord.get(j)+" ");
			}
			System.out.println();
		}
		System.out.println("结束");
	}
	private boolean doAnalyzeSyntax(AnalyzeSyntax analyzeSyntax){
		//this.errList.clear();  //清除前面的error
		
		if(analyzeSyntax.getErrList().size()>0){
			this.showErrList(analyzeSyntax.getErrList());	
			return false;
		}else{
			return true;
		}
	}
	//显示一个子查询的中间代码
	private void showOneQueryMiddleCode(List list){
		int size=list.size();
		System.out.println("face 某一个子查询的中间代码");
		for(int i=0;i<size;i++){
			List oneLine=(List)list.get(i);
			int oneSize=oneLine.size();
			for(int j=0;j<oneSize;j++){
				System.out.print((String)oneLine.get(j)+"  ");
			}
			System.out.println();
		}
	}
	
	private void showMiddleCode(List middleList){
		int size=middleList.size();
		System.out.println("face生成中间代码。。。。。");
		for(int i=0;i<size;i++){
			List list=(List)middleList.get(i);
			for(int j=0;j<list.size();j++){
				System.out.print((String)list.get(j)+"  ");
			}
			System.out.println();
		}
	}
	
	private boolean analyzeOrderBy(Order_by order_by){
		//this.errList.clear();  //清除前面的error
		
		if(order_by.getGroupByErrList().size()>0){
			this.showErrList(order_by.getGroupByErrList());	
			return false;
		}else{
			return true;
		}
	}
	private boolean analyzeHaving(HavingHasGroupBy having){
		//this.errList.clear();  //清除前面的error
		
		if(having.getHavingErrList().size()>0){
			this.showErrList(having.getHavingErrList());	
			return false;
		}else{
			return true;
		}
	}
	private boolean analyzeHavingNotGroupBy(HavingNotGroupBy havingNotGroupBy){
		//this.errList.clear();  //清除前面的error

		if(havingNotGroupBy.getHavingNotGroupbyErrList().size()>0){
			this.showErrList(havingNotGroupBy.getHavingNotGroupbyErrList());	
			return false;
		}else{
			return true;
		}
	}
	
	private boolean analyzeGroupBy(Group_by group_by){
		//this.errList.clear();  //清除前面的error
		
		if(group_by.getGroupByErrList().size()>0){
			this.showErrList(group_by.getGroupByErrList());	
			return false;
		}else{
			return true;
		}
	}
	private boolean analyzeWhere(Where where){
		//this.errList.clear();  //清除前面的error
		
		if(where.getWhereErrList().size()>0){
			this.showErrList(where.getWhereErrList());	
			return false;
		}else{
			return true;
		}
	}
	private boolean analyzeSelect(Select select){
		//this.errList.clear();  //清除前面的error
		
		if(select.getSelect_resultErrList().size()>0){
			this.showErrList(select.getSelect_resultErrList());	
			return false;
		}else{
			return true;
		}
	}
	private boolean analyzeFrom(From from){
		//this.errList.clear();  //清除前面的error
		
		if(from.getFromErrList().size()>0){
			this.showErrList(from.getFromErrList());	
			return false;
		}else{
			return true;
		}
	}
	
	private boolean analyzeSyntax(SyntaxGrenerator sg){
		//this.errList.clear();  //清除前面的error
		
		if(sg.getErrList().size()>0){
			this.showErrList(sg.getErrList());	
			return false;
		}else{
			return true;
		}
	}
	private boolean analyzeToken(TokenAnalyze tokenAnalyze){
		//词法分析
		//TokenAnalyze tokenAnalyze=new TokenAnalyze(this.selectStr);
		//this.errList.clear();  //清除前面的error
		
		if(tokenAnalyze.getErrList().size()>0){
			this.showErrList(tokenAnalyze.getErrList());	
			return false;
		}else{
			return true;
		}
	}
	private void showErrList(List errList){
		int size=errList.size();
		for(int i=0;i<size;i++){
			this.errList.add(errList.get(i));
			System.out.println(errList.get(i));
		}
	}
	public void showTree(Syntax syntax){
		int i=syntax.getRight().size();
		if(i==0){
			if(syntax.getLeft().getType()!=ST_TYPE.ST_NULL_VT){
				System.out.print(" "+syntax.getLeft().getName());
			}
			
		}else {
			for(int j=0;j<i;j++){
				Syntax s=(Syntax)syntax.getRight().get(j);
				showTree(s);
			}
		}
		
	}

	

}
