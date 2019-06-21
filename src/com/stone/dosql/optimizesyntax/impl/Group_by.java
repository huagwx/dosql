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

public class Group_by {
	private List fromTableList;
	private Syntax groupbySyntax;
	
	private List errList=new ArrayList();
	
	public Group_by(Syntax groupbySyntax,List fromTableList) {
		this.groupbySyntax=groupbySyntax;
		this.fromTableList=fromTableList;
		
		this.doAction(this.groupbySyntax);
	}
	public List getGroupByErrList(){
		
		return this.errList;
	}
	private String column_ref="column_ref";
	private String subquery="subquery";  //如果是子查询就跳过 
	private List listIdent=new ArrayList();
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
				if(column.getName().equals(col)){
					tableName=t.getName();
					colNum++;
				}
			}
		}

       	if(colNum==1){

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
				
		
       	}else if(colNum==0){
			String err="列："+col+" 没有找到!";
			this.errList.add(err);
		}else {
       		String err="列："+col+" 多个不能确定!~";
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
					String str=table+".*  语义有误!";
					this.errList.add(str);
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
					String str=table+".*  语义有误!";
					this.errList.add(str);
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
