package com.stone.dosql.optimizesyntax.impl;

import java.util.ArrayList;
import java.util.List;

import com.stone.dosql.accessories.ST_TYPE;
import com.stone.dosql.codegenerator.from.impl.Column;
import com.stone.dosql.codegenerator.from.impl.Table;
import com.stone.dosql.codegenerator.impl.RuleType;
import com.stone.dosql.util.Scalar_exp_one;
import com.stone.dosql.util.Syntax;
import com.stone.dosql.util.SyntaxClone;


//有groupby的having
public class HavingHasGroupBy {
	private Syntax havingSyntax;
	private List errList=new ArrayList();
	
	private List scalar_exp_oneList;
	private List havingTempNameList=new ArrayList();
	
	private List fromTableList;
	public HavingHasGroupBy(Syntax havingSyntax,List scalar_exp_oneList,List fromTableList) {
		this.havingSyntax=havingSyntax;
		this.scalar_exp_oneList=scalar_exp_oneList;
		this.fromTableList=fromTableList;
		this.doHavingTempName();
		this.doAction(this.havingSyntax);
		this.doSelColumnList();
		this.doSelColumnAction(this.havingSyntax);
		this.doFindLeftNameExists(this.havingSyntax);
	}
	//查找剩下的名称是否存在selcolumn中
	//private String subquery="subquery";  //如果是子查询就跳过 
	private  void doFindLeftNameExists(Syntax syntax){
		if(syntax.getLeft().getType()==ST_TYPE.ST_VN&&syntax.getLeft().getName().equals(this.column_ref)){
			this.syntaxNameValue="";
			this.getSyntaxNameValue(syntax);
			String identName=this.syntaxNameValue;
			int selcolumsize=this.selColumnFullNameList.size();
			boolean isyes=false;
			for(int i=0;i<selcolumsize;i++){
				String str=(String)this.selColumnFullNameList.get(i);
				if(identName.equals(str)){
					isyes=true;
					break;
				}
			}
			if(!isyes){
				this.errList.add(identName+" 没有在select查找的列中!");
			}
		}
		int size=syntax.getRight().size();
		for(int i=0;i<size;i++){
			Syntax s=(Syntax)syntax.getRight().get(i);
			if(syntax.getLeft().getType()==ST_TYPE.ST_VN&&syntax.getLeft().getName().equals(this.subquery)){
				continue;
			}
			this.doFindLeftNameExists(s);
		}
	}
	private List selColumnFullNameList=new ArrayList();
	private void doSelColumnList(){
		int size=this.scalar_exp_oneList.size();
		//System.out.println(selColumnFullNameList.size()+" size");
		for(int i=0;i<size;i++){
			Scalar_exp_one seo=(Scalar_exp_one)this.scalar_exp_oneList.get(i);
			if(seo.getScalar_name().equals("*")){  //添加所有的列
				int sizetable=this.fromTableList.size();
				for(int ti=0;ti<sizetable;ti++){
					Table t=(Table)this.fromTableList.get(ti);
					int colsize=t.getColumns().size();
					for(int ci=0;ci<colsize;ci++){
						Column col=(Column)t.getColumns().get(ci);
						this.selColumnFullNameList.add(col.getFullName());
					}
				}
			}else if(seo.getScalar_name().endsWith(".*")){ //一个表的所有列
				Table tempTable=null;
				int seonameleng=seo.getScalar_name().length();
				String tableName=seo.getScalar_name().substring(0, seonameleng-2);
				
				int sizetable=this.fromTableList.size();
				for(int ti=0;ti<sizetable;ti++){
					Table t=(Table)this.fromTableList.get(ti);
					System.out.println(tableName+ "   "+t.getName());
					if(t.getName().equals(tableName)){
						tempTable=t;
					}
				}
				if(tempTable!=null){
					int colsize=tempTable.getColumns().size();
					for(int ci=0;ci<colsize;ci++){
						Column col=(Column)tempTable.getColumns().get(ci);
						this.selColumnFullNameList.add(col.getFullName());
					}
				}
				
				
			}else{
				this.selColumnFullNameList.add(seo.getScalar_name());
			}
			
			
		
		}
		//size=this.selColumnFullNameList.size();
		//System.out.println(size+" aftersize................................................");
		
	}
	//对having的查找列跟selcolumn的列名对应 替换名称
	private void doSelColumnAction(Syntax syntax){
		this.syntaxNameValue="";
		this.getSyntaxNameValue(syntax);
		int size=this.selColumnFullNameList.size();
		for(int i=0;i<size;i++){
			String selFullName=(String)this.selColumnFullNameList.get(i);
			if(this.syntaxNameValue.equals(selFullName)){
				syntax.getLeft().setName(selFullName);
				syntax.getLeft().setType(ST_TYPE.ST_VT);
				syntax.getLeft().setTokenType(ST_TYPE.ST_IDENT);
				syntax.getRight().clear();
				System.out.println("在havinghasgroupby中 sdf鞥sdfsdfsd fsd飞sdf sd飞sd飞士大夫杀毒杀毒腐蚀毒粉士大夫杀毒杀毒杀毒");
			}
		}
		int rsize=syntax.getRight().size();
		for(int j=0;j<rsize;j++){
			Syntax s=(Syntax)syntax.getRight().get(j);
			if(s.getLeft().getType()==ST_TYPE.ST_VN&&s.getLeft().getName().equals(this.subquery)){
				continue;
			}
			doSelColumnAction(s);
		}
	}
	private String syntaxNameValue="";
	private void getSyntaxNameValue(Syntax syntax){
		int i=syntax.getRight().size();
		if(i==0){
			if(syntax.getLeft().getType()!=ST_TYPE.ST_NULL_VT){
				this.syntaxNameValue+=syntax.getLeft().getName();
			}
			
		}else {
			for(int j=0;j<i;j++){
				Syntax s=(Syntax)syntax.getRight().get(j);
				getSyntaxNameValue(s);
			}
		}
	}
	
	private void doHavingTempName(){
		int size=this.scalar_exp_oneList.size();
		for(int i=0;i<size;i++){
			this.syntaxName="";
			HavingNameTemp hnt=new HavingNameTemp();
			Scalar_exp_one seo=(Scalar_exp_one)this.scalar_exp_oneList.get(i);
			this.getSyntaxName(seo.getScalar_exp_one());
			String getSelColumnName=this.syntaxName;
			hnt.setName(getSelColumnName);
			hnt.setOtherName(seo.getParam());
			this.havingTempNameList.add(hnt);
		}
	}
	private String syntaxName="";
	private void getSyntaxName(Syntax syntax){

		int i=syntax.getRight().size();
		if(i==0){
			if(syntax.getLeft().getType()!=ST_TYPE.ST_NULL_VT){
				this.syntaxName+=syntax.getLeft().getName();
			}
			
		}else {
			for(int j=0;j<i;j++){
				Syntax s=(Syntax)syntax.getRight().get(j);
				this.getSyntaxName(s);
			}
		}
		
	
	}
	
	public List getHavingErrList(){
		return this.errList;
	}
	private String subquery="subquery";  //如果是子查询就跳过 
	private String column_ref="column_ref";
	private void doAction(Syntax syntax){
		//如果是一个查询的要去掉
		if(syntax.getLeft().getType()==ST_TYPE.ST_VN&&syntax.getLeft().getName().equals(this.column_ref)){
			this.listIdent=new ArrayList();
			this.do_column_ref(syntax);
			if(this.listIdent.size()==1){
				//查看属于table的column或别名
				this.do_column_has_one(this.listIdent,syntax);
			}else if(this.listIdent.size()==2){
				//查看属于的table和column
				this.do_column_has_two(this.listIdent,syntax);
			}
		}
		int size=syntax.getRight().size();
		for(int i=0;i<size;i++){
			Syntax s=(Syntax)syntax.getRight().get(i);
			if(syntax.getLeft().getType()==ST_TYPE.ST_VN&&syntax.getLeft().getName().equals(this.subquery)){
				continue;
			}
			
			this.doAction(s);
		}
		
		
	}
	private void do_column_has_one(List list,Syntax syntax){
		String col=(String)list.get(0);
		this.do_findColumn1(col, syntax);
	}
	private void do_findColumn1(String col,Syntax syntax){
//		int size=this.havingTempNameList.size();
//		for(int i=0;i<size;i++){
//			HavingNameTemp hnt=(HavingNameTemp)this.havingTempNameList.get(i);
//			//考虑 hnt的name为* 的时候 或 
//		}
		
		
		//考虑col是否有多个 和是否在table中
		int colNum=0;
		int paramNum=0;
		Syntax syntaxTemp=null;
		int size=this.fromTableList.size();
		String tableName="";
		for(int i=0;i<size;i++){
			Table t=(Table)this.fromTableList.get(i);
			
			List cols=t.getColumns();
			int col_size=cols.size();
			for(int j=0;j<col_size;j++){
				Column column=(Column)cols.get(j);
				if(column.getName().equals(col)){
					tableName=t.getName();
					colNum++;
				}
			}
		}
		int selSize=this.scalar_exp_oneList.size();
		for(int i=0;i<selSize;i++){
			Scalar_exp_one seo=(Scalar_exp_one)this.scalar_exp_oneList.get(i);
			if(seo.getParam().equals(col)){
				paramNum++;
				//复制一个syntax
				SyntaxClone sc=new SyntaxClone(seo.getScalar_exp_one());
				syntaxTemp=sc.getCloneSyntax();
			}
		}
       	if(colNum==1&&paramNum==0){

			/*column_ref -> IDENT column_ref1
            column_ref1 -> ST_NULL_S
            column_ref1 -> . column_ref2
            column_ref2 ->  *
            column_ref2 ->  IDENT 
   protected String column_ref_1 = "column_ref ->  IDENT  column_ref1";
	protected String column_ref1_2 = "column_ref1 ->  .  column_ref2";
	protected String column_ref2_2 = "column_ref2 ->  IDENT";*/
			
			//ident
			Syntax identSyntax=syntax.getRight().get(0);
			SyntaxClone sc=new SyntaxClone(identSyntax);
			identSyntax=sc.getCloneSyntax();
			
			Syntax column_ref2=new Syntax();
			Syntax column_ref1=new Syntax();
			Syntax column_ref=new Syntax();
			
			RuleType rule=new RuleType();
			
			
			
			//column_ref2
			column_ref2.getLeft().setName("column_ref2");
			column_ref2.getLeft().setRule(rule.getColumn_ref2_2());
			column_ref2.getLeft().setTokenType(-1);
			column_ref2.getLeft().setType(ST_TYPE.ST_VN);
			column_ref2.getRight().add(identSyntax);
			
			//.
			Syntax dotSyntax=new Syntax();
			dotSyntax.getLeft().setName(".");
			dotSyntax.getLeft().setRule("");
			dotSyntax.getLeft().setTokenType(ST_TYPE.ST_KEY);
			dotSyntax.getLeft().setType(ST_TYPE.ST_VT);
			
			
			//column_ref1
			column_ref1.getLeft().setName("column_ref1");
			column_ref1.getLeft().setRule(rule.getColumn_ref1_2());
			column_ref1.getLeft().setTokenType(-1);
			column_ref1.getLeft().setType(ST_TYPE.ST_VN);
			column_ref1.getRight().add(dotSyntax);
			column_ref1.getRight().add(column_ref2);
			
			//ident
			Syntax identSyntax1=new Syntax();
			identSyntax1.getLeft().setName(tableName);
			identSyntax1.getLeft().setRule("");
			identSyntax1.getLeft().setTokenType(ST_TYPE.ST_IDENT);
			identSyntax1.getLeft().setType(ST_TYPE.ST_VT);
			
			//column_ref
			column_ref.getLeft().setName("column_ref");
			column_ref.getLeft().setRule(rule.getColumn_ref_1());
			column_ref.getLeft().setTokenType(-1);
			column_ref.getLeft().setType(ST_TYPE.ST_VN);
			column_ref.getRight().add(identSyntax1);
			column_ref.getRight().add(column_ref1);
			
			//syntax修改成table.column格式
			
			syntax.getRight().remove(0);
			syntax.getRight().remove(0);
			syntax.getRight().add(column_ref);
				
		
       	}else if(colNum==0&&paramNum==1){
       		syntax.getRight().remove(0);
       		syntax.getRight().remove(0);
       		syntax.getRight().add(syntaxTemp);
       		
       		
       	}else if(colNum==0&&paramNum==0){
       		System.out.println(colNum+"  "+paramNum);
       		String err="列："+col+" 不存在!~";
       		this.errList.add(err);
       	}else{
       		
       		String err="列："+col+" 不能确定!~";
       		this.errList.add(err);
       	}
		
	}
	
	
	
	private void do_column_has_two(List list,Syntax syntax){
		String table=(String)list.get(0);
		String col=(String)list.get(1);  //col为*时处理
		this.do_findColumn2(table, col, syntax);
		
	}
	
	
	
	private void do_findColumn2(String table,String col,Syntax syntax){
		int size=this.fromTableList.size();
		boolean isFind=false;
		for(int i=0;i<size;i++){
			Table t=(Table)this.fromTableList.get(i);
			
			if(t.getName().equals(table)){
				//跟table的name相同
				if(col.equals("*")){
					//在中间代码分析是考虑 * 是不是属于聚函数，如果不是聚函数返回的是false
//					String str=table+".*  语义有误!";
//					this.errList.add(str);
				}else{
					this.do_findOneColumnFromTable(t.getName(),col,t);
				}
				isFind=true;
				break;
			}else if(t.getOtherName().equals(table)){
				//跟table的别名相同
				//把table的name代替syntax的第一个ident中
				syntax.getRight().get(0).getLeft().setName(t.getName());
				if(col.equals("*")){
//					String str=table+".*  语义有误!";
//					this.errList.add(str);
				}else{
					this.do_findOneColumnFromTable(t.getName(),col,t);
				}
				isFind=true;
				break;
			}
		}
		if(!isFind){
			String str=table+"."+col+" 不存在！ ";
			this.errList.add(str);
		}
	}
	private void do_findOneColumnFromTable(String tableName,String colName,Table t){
		List columns=t.getColumns();
		boolean isfind=false;
		for(int i=0;i<columns.size();i++){
			Column col=(Column)columns.get(i);
			if(col.getName().equals(colName)){
				isfind=true;
				break;
			}
		}
		if(!isfind){
			String str1=tableName+"."+colName+"不存在!";
			this.errList.add(str1);
		}
	}
	
	//column_ref -> IDENT column_ref1
	private List listIdent=new ArrayList();
	private void do_column_ref(Syntax syntax){	
		if(syntax.getLeft().getType()==ST_TYPE.ST_VT&&syntax.getLeft().getTokenType()==ST_TYPE.ST_IDENT){
			this.listIdent.add(syntax.getLeft().getName());
		}
		int size=syntax.getRight().size();
		for(int i=0;i<size;i++){
			Syntax s=(Syntax)syntax.getRight().get(i);
			this.do_column_ref(s);
		}
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
