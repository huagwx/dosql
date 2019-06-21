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

public class Select {
	private Syntax selectResultSyntax;
	private List fromTableList;
	
	private List errList=new ArrayList();
	
	private List scalar_exp_oneList=new ArrayList();
	
	//查找的列的信息用于跟别名对比
	private List selectColumnList=new ArrayList();
	public List getSelectColumnList(){
		return this.selectColumnList;
	}
	
	public Select(Syntax selectResultSyntax,List fromTableList) {
		this.selectResultSyntax=selectResultSyntax;
		this.fromTableList=fromTableList;
		this.doAction(this.selectResultSyntax);
		
	}
	
	public List getSelect_resultErrList(){
		this.do_scalar_exp_oneList();
		return this.errList;
	}
	private void do_scalar_exp_oneList(){
		//不能table同名，别名也不能相同,不能跟列的名相同
		int size=this.scalar_exp_oneList.size();
		for(int i=0;i<size;i++){
			Scalar_exp_one seo=(Scalar_exp_one)this.scalar_exp_oneList.get(i);
			String param=seo.getParam();
			
			//System.out.println("selectColumnparam:"+param);
			
			int table_size=this.fromTableList.size();
			
			String err="";
			for(int i1=0;i1<table_size;i1++){
				Table t=(Table)this.fromTableList.get(i1);
				String tabName=t.getName();
				String tabOtherName=t.getOtherName();
				if(param.equals(tabName)){
					err="参数:"+param+" 跟表:"+tabName+"不能相同!";
					this.errList.add(err);
					break;
				}else if(param.equals(tabOtherName)&&param.length()>0){
					err="参数:"+param+" 跟表别名:"+tabOtherName+"不能相同!";
					this.errList.add(err);
					break;
				}else{
					
				}
			}
			int selectCol_size=this.selectColumnList.size();
			for(int i2=0;i2<selectCol_size;i2++){
				Column col=(Column)this.selectColumnList.get(i2);
				String colName=col.getName();
				if(colName.equals(param)){
				     err=param+" 列名和参数不能相同!";
				     this.errList.add(err);
				     break;
				}
			}
		}
	}
	
	//用于where后面的名称比较
	public List getScalar_exp_oneList(){
		return this.scalar_exp_oneList;
	}
	
	private String scalar_exp_one="scalar_exp_one";
	private String column_ref="column_ref";
	
	private void doAction(Syntax syntax){
		//System.out.println(syntax.getLeft().getName()+".....");
		if(syntax.getLeft().getType()==ST_TYPE.ST_VN&&syntax.getLeft().getName().equals(this.scalar_exp_one)){ //后面没有加入&&syntax.getRight().size()==2 20080606
			this.do_scalar_exp_one(syntax);
		}else if(syntax.getLeft().getType()==ST_TYPE.ST_VN&&syntax.getLeft().getName().equals(this.column_ref)&&syntax.getRight().size()==2){
			this.listIdent=new ArrayList();
			this.do_column_ref(syntax);
			//System.out.println("listIdentSize:"+listIdent.size());
			if(this.listIdent.size()==1){
				//查看属于的table和column
				this.do_column_has_one(this.listIdent,syntax);
			}else if(this.listIdent.size()==2){
				//查看属于的table和column
				this.do_column_has_two(this.listIdent,syntax);
			}
		}
		
		int size=syntax.getRight().size();
		for(int i=0;i<size;i++){
			Syntax s=(Syntax)syntax.getRight().get(i);
			if(s.getLeft().getType()==ST_TYPE.ST_VN&&s.getLeft().getName().equals(this.table_exp)){
				continue;
			}
			this.doAction(s);
		}
	}
	private String table_exp="table_exp";
	private void do_column_has_one(List list,Syntax syntax){
		String col=(String)list.get(0);
		this.do_findColumn1(col, syntax);
	}
	private void do_column_has_two(List list,Syntax syntax){
		String table=(String)list.get(0);
		String col=(String)list.get(1);  //col为*时处理
		this.do_findColumn2(table, col, syntax);
		
	}
	private void do_findColumn1(String col,Syntax syntax){
		//考虑col是否有多个 和是否在table中
		int colNum=0;
		int size=this.fromTableList.size();
		String tableName="";
		for(int i=0;i<size;i++){
			Table t=(Table)this.fromTableList.get(i);
			List cols=t.getColumns();
			int col_size=cols.size();
			for(int j=0;j<col_size;j++){
				Column column=(Column)cols.get(j);
				//System.out.println(column.getName());
				if(column.getName().equals(col)){
					tableName=t.getName();
					colNum++;
				}
			}
		}
		//System.out.println("colNum:"+colNum);
		if(colNum==0){
			String err="列："+col+" 没有找到!";
			this.errList.add(err);
		}else if(colNum==1){
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
			this.showTree(syntax);
			
			
			Column column1=new Column();
			column1.setTableName(tableName);
			column1.setName(col);
			this.selectColumnList.add(column1);
			
			
			
			
			
		}else{
			String err="列："+col+" 在查找的表中有"+colNum+ "个";
			this.errList.add(err);
		}
	}
	
	private void do_findColumn2(String table,String col,Syntax syntax){
		int size=this.fromTableList.size();
		boolean isFind=false;
		for(int i=0;i<size;i++){
			Table t=(Table)this.fromTableList.get(i);
			
			if(t.getName().equals(table)){
				//跟table的name相同
				if(col.equals("*")){
					
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
					
				}else{
					this.do_findOneColumnFromTable(t.getName(),col,t);
				}
				isFind=true;
				break;
			}
		}
		if(!isFind){
			String str="表:"+table+" 不存在！ ";
			this.errList.add(str);
		}else{
			Column column1=new Column();
			column1.setTableName(table);
			column1.setName(col);
			this.selectColumnList.add(column1);
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
	
	
	
	//scalar_exp_one -> scalar_exp parameter_ref
	//scalar_exp_one -> all_table_allcolumn 20080606加入
	private void do_scalar_exp_one(Syntax syntax){
		// *
		if(syntax.getRight().size()==1){
			Syntax scalar_expSyntax=(Syntax)syntax.getRight().get(0);
			String paramName="";
			Scalar_exp_one seo=new Scalar_exp_one(scalar_expSyntax,paramName);
			this.scalar_exp_oneList.add(seo);
		}else{
			Syntax scalar_expSyntax=(Syntax)syntax.getRight().get(0);
			Syntax paramSyntax=(Syntax)syntax.getRight().get(1);
			Parameter_ref param_ref=new Parameter_ref(paramSyntax);
			String paramName=param_ref.getParam();
			Scalar_exp_one seo=new Scalar_exp_one(scalar_expSyntax,paramName);
			this.scalar_exp_oneList.add(seo);
		}

	}
	public void showTree(Syntax syntax){
		int i=syntax.getRight().size();
		if(i==0){
			if(syntax.getLeft().getType()!=ST_TYPE.ST_NULL_VT){
				//System.out.print(" "+syntax.getLeft().getName());
			}
			
		}else {
			for(int j=0;j<i;j++){
				Syntax s=(Syntax)syntax.getRight().get(j);
				showTree(s);
			}
		}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
