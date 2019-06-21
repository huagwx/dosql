package com.stone.dosql.codegenerator.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import com.stone.dosql.accessories.ST_APP;
import com.stone.dosql.accessories.ST_TYPE;
import com.stone.dosql.accessories.ST_TYPE_ARRAY;
import com.stone.dosql.analyzetree.impl.Query;
import com.stone.dosql.lexical.impl.TokenAnalyze;
import com.stone.dosql.syntax.impl.SyntaxGrenerator;
import com.stone.dosql.util.Syntax;
import com.stone.dosql.util.SyntaxClone;


public class AnalyzeSyntax extends RuleType{
	
	//所有的查询或子查询
	//private List queryList = new ArrayList();
	
	private Syntax syntax;
	private Syntax inputSyntax;
	private String outPutFile;
	//private Writer fileWriter;  //生成的中间代码到文件中
	
	private boolean isFinish=false;
	private static List middleCodeList=new ArrayList();
	private List errList=new ArrayList();
	public List getErrList(){
		return this.errList;
	}
	
	public AnalyzeSyntax() {
		//this.outPutFile=outPutFile;
		//删除已经存在的outPutFile文件 
		//this.delExistInputFile();
		ST_APP.ti=0;
		AnalyzeSyntax.middleCodeList=new ArrayList();
	}
	public List getMiddleCodeList(){
		return AnalyzeSyntax.middleCodeList;
	}
//	private void delExistInputFile(){
//		File delFile=new File(this.outPutFile);
//		if(delFile.delete()){
//			System.out.println("删除文件:"+this.outPutFile);
//		}else{
//			System.out.println("不存在文件"+this.outPutFile);
//		}	
//	}
	
//	//返回所有的查询list
//	public List getQueryList(){  //在写入中间代码后才可以得到结果
//		if(this.isFinish){
//			return this.queryList;
//		}else{
//			return null;
//		}
//	}
	//写入中间代码到文件中
	public void greneratorMiddleCode(Syntax inputSyntax){

		
//		try {
//			FileOutputStream fos = new FileOutputStream(this.outPutFile);
//			Writer out = new OutputStreamWriter(fos, "UTF-8");
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			System.out.println();
//			e.printStackTrace();
//		}
		this.inputSyntax=inputSyntax;
		boolean isContinue=true;
		while(isContinue){
			//System.out.println(".....................................................................");
			if(this.inputSyntax.getLeft().getSyntaxResult().length()==0){
				this.doSyntaxRec(this.inputSyntax);
			}else{
				isContinue=false;
			}
		}
		//System.out.println(isContinue);
//		if(!isContinue){
//			this.isFinish=true;   //用于返回查询的语句包括子查询
//			this.doCloseWriter();
//			System.out.println("finish  GreneCode");
//		}

	}
	private void doSyntaxRec(Syntax syntax){
		//syntax是vn
	//	System.out.println("................result:"+syntax.getLeft().getSyntaxResult());
		this.doSyntax(syntax);
			for(int i=0;i<syntax.getRight().size();i++){
				Syntax s=(Syntax)syntax.getRight().get(i);
				if(s.getLeft().getType()==ST_TYPE.ST_VN&&s.getLeft().getSyntaxResult().length()==0){
					this.doSyntaxRec(s);
				}
			}	

	}
	
	private boolean doCommon(){
		//this.syntax是vn
		int size=this.syntax.getRight().size();
		boolean isFinish=true;
		String result="";
		int num=0;	
		for(int i=0;i<size;i++){
			Syntax ss=(Syntax)this.syntax.getRight().get(i);
			if(ss.getLeft().getType()==ST_TYPE.ST_VN&&ss.getLeft().getSyntaxResult().length()==0){
			//	System.out.println("1");
				num++;
				
			}else if(ss.getLeft().getType()==ST_TYPE.ST_VT){
				if(ss.getLeft().getTokenType()==ST_TYPE.ST_IDENT){
				//	System.out.println("2");
					result=" "+result+" "+ss.getLeft().getName();
				//	ss.getLeft().setSyntaxResult(ss.getLeft().getName());
				}else if(ss.getLeft().getTokenType()==ST_TYPE.ST_DIGITAL_CONST){
				//	System.out.println("3");
					result=" "+result+" "+ss.getLeft().getName();
				//	ss.getLeft().setSyntaxResult(ss.getLeft().getName());
				}else if(ss.getLeft().getTokenType()==ST_TYPE.ST_STRING_CONST){
				//	System.out.println("4");
					result=" "+result+" "+ss.getLeft().getName();
				//	ss.getLeft().setSyntaxResult("\""+ss.getLeft().getName()+"\"");
				}else if(this.isVtAdd(ss.getLeft().getName())){
					result=" "+result+" "+ss.getLeft().getName();
				}else {
				//	System.out.println("5");
					result=" "+result+" ";
				}
			}else if(ss.getLeft().getType()==ST_TYPE.ST_NULL_VT){
				result=" "+result+" ";
			}else if(ss.getLeft().getType()==ST_TYPE.ST_VN&&ss.getLeft().getSyntaxResult().length()>0){
			
				result=" "+result+" "+ss.getLeft().getSyntaxResult()+" ";
			}
		}
		
		if(num==0){
			//System.out.println(this.syntax.getLeft().getName()+"....................resutl::::::...:"+result);
			this.syntax.getLeft().setSyntaxResult(result);
		}else {
			isFinish=false;
		}
		return isFinish;
		
	}
	private boolean isVtAdd(String vtName){
		boolean isExist=false;
		int leng=ST_TYPE_ARRAY.analyzeWant.length;
		for(int i=0;i<leng;i++){
			if(vtName.equals(ST_TYPE_ARRAY.analyzeWant[i])){
				isExist=true;
				break;
			}
		}
		return isExist;
	}
	
	private void doSyntax(Syntax syntax){
		this.syntax=syntax;
		String rule=this.syntax.getLeft().getRule();
		//System.out.println("rule:  "+rule);
		
		 if(rule.equals(this.query_spec_1)){
	          this.do_query_spec_1();
	     }else if(rule.equals(this.select_result_1)){
	          this.do_select_result_1();
	     }else if(rule.equals(this.opt_all_distinct_1)){
	          this.do_opt_all_distinct_1();
	     }else if(rule.equals(this.opt_all_distinct_2)){
	          this.do_opt_all_distinct_2();
	     }else if(rule.equals(this.opt_all_distinct_3)){
	          this.do_opt_all_distinct_3();
	     }else if(rule.equals(this.parameter_ref_1)){
	          this.do_parameter_ref_1();
	     }else if(rule.equals(this.parameter_ref_2)){
	          this.do_parameter_ref_2();
	     }else if(rule.equals(this.parameter_ref_3)){
	          this.do_parameter_ref_3();
	     }else if(rule.equals(this.all_table_allcolumn_1)){
	          this.do_all_table_allcolumn_1();
	     }else if(rule.equals(this.scalar_exp_one_1)){
	          this.do_scalar_exp_one_1();
	     }else if(rule.equals(this.scalar_exp_one_2)){
	          this.do_scalar_exp_one_2();
	     }else if(rule.equals(this.scalar_exp_commalist_1)){
	          this.do_scalar_exp_commalist_1();
	     }else if(rule.equals(this.scalar_exp_commalist1_1)){
	          this.do_scalar_exp_commalist1_1();
	     }else if(rule.equals(this.scalar_exp_commalist1_2)){
	          this.do_scalar_exp_commalist1_2();
	     }else if(rule.equals(this.OPERATOR_1)){
	          this.do_OPERATOR_1();
	     }else if(rule.equals(this.OPERATOR_2)){
	          this.do_OPERATOR_2();
	     }else if(rule.equals(this.OPERATOR1_1)){
	          this.do_OPERATOR1_1();
	     }else if(rule.equals(this.OPERATOR1_2)){
	          this.do_OPERATOR1_2();
	     }else if(rule.equals(this.atom_1)){
	          this.do_atom_1();
	     }else if(rule.equals(this.atom_2)){
	          this.do_atom_2();
	     }else if(rule.equals(this.atom_3)){
	          this.do_atom_3();
	     }else if(rule.equals(this.column_ref_1)){
	          this.do_column_ref_1();
	     }else if(rule.equals(this.column_ref1_1)){
	          this.do_column_ref1_1();
	     }else if(rule.equals(this.column_ref1_2)){
	          this.do_column_ref1_2();
	     }else if(rule.equals(this.column_ref2_1)){
	          this.do_column_ref2_1();
	     }else if(rule.equals(this.column_ref2_2)){
	          this.do_column_ref2_2();
	     }else if(rule.equals(this.scalar_exp4_1)){
	          this.do_scalar_exp4_1();
	     }else if(rule.equals(this.scalar_exp4_2)){
	          this.do_scalar_exp4_2();
	     }else if(rule.equals(this.scalar_exp7_1)){
	          this.do_scalar_exp7_1();
	     }else if(rule.equals(this.scalar_exp7_2)){
	          this.do_scalar_exp7_2();
	     }else if(rule.equals(this.scalar_exp_1)){
	          this.do_scalar_exp_1();
	     }else if(rule.equals(this.scalar_exp8_1)){
	          this.do_scalar_exp8_1();
	     }else if(rule.equals(this.scalar_exp8_2)){
	          this.do_scalar_exp8_2();
	     }else if(rule.equals(this.scalar_exp5_1)){
	          this.do_scalar_exp5_1();
	     }else if(rule.equals(this.scalar_exp6_1)){
	          this.do_scalar_exp6_1();
	     }else if(rule.equals(this.scalar_exp6_2)){
	          this.do_scalar_exp6_2();
	     }else if(rule.equals(this.scalar_exp6_3)){
	          this.do_scalar_exp6_3();
	     }else if(rule.equals(this.scalar_exp9_1)){
	          this.do_scalar_exp9_1();
	     }else if(rule.equals(this.scalar_exp9_2)){
	          this.do_scalar_exp9_2();
	     }else if(rule.equals(this.scalar_exp9_3)){
	          this.do_scalar_exp9_3();
	     }else if(rule.equals(this.scalar_exp9_4)){
	          this.do_scalar_exp9_4();
	     }else if(rule.equals(this.func_exp_1)){
	          this.do_func_exp_1();
	     }else if(rule.equals(this.FUNC_1)){
	          this.do_FUNC_1();
	     }else if(rule.equals(this.FUNC_2)){
	          this.do_FUNC_2();
	     }else if(rule.equals(this.FUNC_3)){
	          this.do_FUNC_3();
	     }else if(rule.equals(this.FUNC_4)){
	          this.do_FUNC_4();
	     }else if(rule.equals(this.FUNC_5)){
	          this.do_FUNC_5();
	     }else if(rule.equals(this.table_exp_1)){
	          this.do_table_exp_1();
	     }else if(rule.equals(this.from_clause_1)){
	          this.do_from_clause_1();
	     }else if(rule.equals(this.table_ref_one_1)){
	          this.do_table_ref_one_1();
	     }else if(rule.equals(this.table_ref_commalist_1)){
	          this.do_table_ref_commalist_1();
	     }else if(rule.equals(this.table_ref_commalist1_1)){
	          this.do_table_ref_commalist1_1();
	     }else if(rule.equals(this.table_ref_commalist1_2)){
	          this.do_table_ref_commalist1_2();
	     }else if(rule.equals(this.opt_where_clause_1)){
	          this.do_opt_where_clause_1();
	     }else if(rule.equals(this.opt_where_clause_2)){
	          this.do_opt_where_clause_2();
	     }else if(rule.equals(this.opt_group_by_clause_1)){
	          this.do_opt_group_by_clause_1();
	     }else if(rule.equals(this.opt_group_by_clause_2)){
	          this.do_opt_group_by_clause_2();
	     }else if(rule.equals(this.column_ref_commalist_1)){
	          this.do_column_ref_commalist_1();
	     }else if(rule.equals(this.column_ref_commalist1_1)){
	          this.do_column_ref_commalist1_1();
	     }else if(rule.equals(this.column_ref_commalist1_2)){
	          this.do_column_ref_commalist1_2();
	     }else if(rule.equals(this.opt_having_clause_1)){
	          this.do_opt_having_clause_1();
	     }else if(rule.equals(this.opt_having_clause_2)){
	          this.do_opt_having_clause_2();
	     }else if(rule.equals(this.COMPARISON_1)){
	          this.do_COMPARISON_1();
	     }else if(rule.equals(this.COMPARISON_2)){
	          this.do_COMPARISON_2();
	     }else if(rule.equals(this.COMPARISON_3)){
	          this.do_COMPARISON_3();
	     }else if(rule.equals(this.COMPARISON_4)){
	          this.do_COMPARISON_4();
	     }else if(rule.equals(this.COMPARISON_5)){
	          this.do_COMPARISON_5();
	     }else if(rule.equals(this.COMPARISON_6)){
	          this.do_COMPARISON_6();
	     }else if(rule.equals(this.search_condition_1)){
	          this.do_search_condition_1();
	     }else if(rule.equals(this.search_condition13_1)){
	          this.do_search_condition13_1();
	     }else if(rule.equals(this.search_condition13_2)){
	          this.do_search_condition13_2();
	     }else if(rule.equals(this.search_condition14_1)){
	          this.do_search_condition14_1();
	     }else if(rule.equals(this.search_condition14_2)){
	          this.do_search_condition14_2();
	     }else if(rule.equals(this.search_condition11_1)){
	          this.do_search_condition11_1();
	     }else if(rule.equals(this.search_condition12_1)){
	          this.do_search_condition12_1();
	     }else if(rule.equals(this.search_condition12_2)){
	          this.do_search_condition12_2();
	     }else if(rule.equals(this.search_condition15_1)){
	          this.do_search_condition15_1();
	     }else if(rule.equals(this.search_condition15_2)){
	          this.do_search_condition15_2();
	     }else if(rule.equals(this.search_condition9_1)){
	          this.do_search_condition9_1();
	     }else if(rule.equals(this.search_condition1_1)){
	          this.do_search_condition1_1();
	     }else if(rule.equals(this.search_condition1_2)){
	          this.do_search_condition1_2();
	     }else if(rule.equals(this.search_condition1_3)){
	          this.do_search_condition1_3();
	     }else if(rule.equals(this.search_condition1_4)){
	          this.do_search_condition1_4();
	     }else if(rule.equals(this.search_condition1_5)){
	          this.do_search_condition1_5();
	     }else if(rule.equals(this.search_condition1_6)){
	          this.do_search_condition1_6();
	     }else if(rule.equals(this.search_condition2_1)){
	          this.do_search_condition2_1();
	     }else if(rule.equals(this.search_condition3_1)){
	          this.do_search_condition3_1();
	     }else if(rule.equals(this.search_condition4_1)){
	          this.do_search_condition4_1();
	     }else if(rule.equals(this.search_condition5_1)){
	          this.do_search_condition5_1();
	     }else if(rule.equals(this.search_condition5_2)){
	          this.do_search_condition5_2();
	     }else if(rule.equals(this.search_condition6_1)){
	          this.do_search_condition6_1();
	     }else if(rule.equals(this.search_condition6_2)){
	          this.do_search_condition6_2();
	     }else if(rule.equals(this.search_condition7_1)){
	          this.do_search_condition7_1();
	     }else if(rule.equals(this.search_condition7_2)){
	          this.do_search_condition7_2();
	     }else if(rule.equals(this.search_condition7_3)){
	          this.do_search_condition7_3();
	     }else if(rule.equals(this.search_condition8_1)){
	          this.do_search_condition8_1();
	     }else if(rule.equals(this.search_condition8_2)){
	          this.do_search_condition8_2();
	     }else if(rule.equals(this.opt_escape_1)){
	          this.do_opt_escape_1();
	     }else if(rule.equals(this.opt_escape_2)){
	          this.do_opt_escape_2();
	     }else if(rule.equals(this.atom_commalist_1)){
	          this.do_atom_commalist_1();
	     }else if(rule.equals(this.atom_commalist1_1)){
	          this.do_atom_commalist1_1();
	     }else if(rule.equals(this.atom_commalist1_2)){
	          this.do_atom_commalist1_2();
	     }else if(rule.equals(this.any_all_some_1)){
	          this.do_any_all_some_1();
	     }else if(rule.equals(this.any_all_some_2)){
	          this.do_any_all_some_2();
	     }else if(rule.equals(this.any_all_some_3)){
	          this.do_any_all_some_3();
	     }else if(rule.equals(this.subquery_1)){
	          this.do_subquery_1();
	     }else if(rule.equals(this.opt_order_by_clause_1)){
	          this.do_opt_order_by_clause_1();
	     }else if(rule.equals(this.opt_order_by_clause_2)){
	          this.do_opt_order_by_clause_2();
	     }else if(rule.equals(this.ordering_spec_commalist_1)){
	          this.do_ordering_spec_commalist_1();
	     }else if(rule.equals(this.ordering_spec_commalist1_1)){
	          this.do_ordering_spec_commalist1_1();
	     }else if(rule.equals(this.ordering_spec_commalist1_2)){
	          this.do_ordering_spec_commalist1_2();
	     }else if(rule.equals(this.ordering_spec_1)){
	          this.do_ordering_spec_1();
	     }else if(rule.equals(this.opt_asc_desc_1)){
	          this.do_opt_asc_desc_1();
	     }else if(rule.equals(this.opt_asc_desc_2)){
	          this.do_opt_asc_desc_2();
	     }else if(rule.equals(this.opt_asc_desc_3)){
	          this.do_opt_asc_desc_3();
	     }
	}

	//query_spec -> SELECT  select_result  table_exp  
	//(= t1 - t)
	private void do_query_spec_1(){
		boolean bool=this.doCommon();
		if(bool){
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();
//			if(list.size()>=1){
//				oper="ResultSetTemp";
//				t1=(String)list.get(0);
//				t2="-";
//				this.showResult(oper, t1, t2);
//				//this.queryList.add(this.syntax);  //把查询添加到查询list中
//			}
			
			String first=(String)list.get(0);
			if(first.equals("ALL")||first.equals("DISTINCT")){
				oper="ResultSetTemp";
				t1=(String)list.get(1);
				t2=first;
				this.showResult(oper, t1, t2);
			}else{
				oper="ResultSetTemp";
				t1=(String)list.get(0);
				t2="ALL";
				this.showResult(oper, t1, t2);
			}

		}
	}


	//select_result -> opt_all_distinct  scalar_exp_commalist  
	private void do_select_result_1(){
		boolean bool=this.doCommon();
//		if(bool){
//		
//			String oper="";
//			String t1="";	
//			String t2="";
//			List list=this.getResultList();
//			t2="-";
//			if(list.size()==1){
//				oper="ALL";
//				t1=(String)list.get(0);
//				this.showResult(oper, t1, t2);
//			}else if(list.size()==2){
//				oper=(String)list.get(0);
//				t1=(String)list.get(1);
//				this.showResult(oper, t1, t2);
//			}
//			
//			
//		}
	}


	//opt_all_distinct -> ALL  
	private void do_opt_all_distinct_1(){
		boolean bool=this.doCommon();
	}


	//opt_all_distinct -> DISTINCT  
	private void do_opt_all_distinct_2(){
		boolean bool=this.doCommon();
	}	


	//opt_all_distinct -> ST_NULL_S  
	private void do_opt_all_distinct_3(){
		boolean bool=this.doCommon();

	}


	//parameter_ref -> ST_NULL_S  
	private void do_parameter_ref_1(){
		boolean bool=this.doCommon();
	}


	//parameter_ref -> IDENT  
	private void do_parameter_ref_2(){
		boolean bool=this.doCommon();
	}


	//parameter_ref -> AS  IDENT  
	private void do_parameter_ref_3(){
		boolean bool=this.doCommon();
	}


	//all_table_allcolumn -> *  
	private void do_all_table_allcolumn_1(){
		boolean bool=this.doCommon();
	}


	//scalar_exp_one -> scalar_exp  parameter_ref  
	//select_one t1 t2 t3   //t2是一个参数 空的时候说明没有参数
	private String selColumnName="";

	private void do_scalar_exp_one_1(){
		boolean bool=this.doCommon();
		if(bool){
			this.selColumnName="";
			this.showTree(this.syntax.getRight().get(0));
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();
			oper="SelColumn";
			if(list.size()==1){
				t1=(String)list.get(0);
				t2=this.selColumnName;
				this.showResult(oper, t1, t2);
			}else if(list.size()==2){
				t1=(String)list.get(0);
				t2=this.selColumnName;
				this.showResult(oper, t1, t2);
			}
			
			
			
		}
	}


	//scalar_exp_one -> all_table_allcolumn  
	private void do_scalar_exp_one_2(){
		boolean bool=this.doCommon();
		if(bool){
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();
			oper="SelColumn";
			if(list.size()==1){
				t1=(String)list.get(0);
				t2="-";
				this.showResult(oper, t1, t2);
			}
			
			
			
		}
	}


	//scalar_exp_commalist -> scalar_exp_one  scalar_exp_commalist1  
	private void do_scalar_exp_commalist_1(){
		boolean bool=this.doCommon();
		
		if(bool){
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();
			if(list.size()>=2){
				int size=list.size();
				t1=(String)list.get(size-1);
				list.remove(size-1);
				Iterator iter=list.iterator();	
				while(iter.hasNext()){
					oper="add";
					t2=(String)iter.next();
					this.showResult(oper, t1, t2);
					t1=this.syntax.getLeft().getSyntaxResult();
				}
				
			}
		}
	}


	//scalar_exp_commalist1 -> ,  scalar_exp_one  scalar_exp_commalist1  
	private void do_scalar_exp_commalist1_1(){
		boolean bool=this.doCommon();
//		if(bool){
//			String oper="";
//			String t1="";	
//			String t2="";
//			List list=this.getResultList();
//			if(list.size()==2){
//				oper="add";
//	            t1=(String)list.get(1);
//
//				t2=(String)list.get(0);
//				
//				this.showResult(oper, t1, t2);
//			}
//		}
	}


	//scalar_exp_commalist1 -> ST_NULL_S  
	private void do_scalar_exp_commalist1_2(){
		boolean bool=this.doCommon();
		if(bool){
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();
			if(list.size()==0){
				oper="new";
	            t1="List";
				t2="SelColumn";			
				this.showResult(oper, t1, t2);
			}

		}
	}


	//OPERATOR -> +  
	private void do_OPERATOR_1(){
		boolean bool=this.doCommon();
	}


	//OPERATOR -> -  
	private void do_OPERATOR_2(){
		boolean bool=this.doCommon();
	}


	//OPERATOR1 -> *  
	private void do_OPERATOR1_1(){
		boolean bool=this.doCommon();
	}


	//OPERATOR1 -> /  
	private void do_OPERATOR1_2(){
		boolean bool=this.doCommon();
	}


    //atom -> ' STRINGCONST '
	private void do_atom_1(){
		boolean bool=this.doCommon();
		if(bool){
			List list=this.getResultList();
			String str="";
			for(int i=0;i<list.size();i++){
				str=(String)list.get(i);
			}
			str="  \""+str+"\" ";
			this.syntax.getLeft().setSyntaxResult(str);
		}
	}
	
	
    //atom -> " STRINGCONST "
	private void do_atom_2(){
		boolean bool=this.doCommon();
		if(bool){
			List list=this.getResultList();
			String str="";
			for(int i=0;i<list.size();i++){
				str=(String)list.get(i);
			}
			str="  \""+str+"\" ";
			this.syntax.getLeft().setSyntaxResult(str);
		}
	}
	
	
    //atom -> DIGITALCONST
	private void do_atom_3(){
		boolean bool=this.doCommon();
	}  


    //column_ref -> IDENT column_ref1
	private void do_column_ref_1(){
		boolean bool=this.doCommon();
		if(bool){
			List list = this.getResultList();
			String col="";
			for(int i=0;i<list.size();i++){
				col+=(String)list.get(i);
			}
			col=" "+col+" ";
			this.syntax.getLeft().setSyntaxResult(col);
		}
	}
	//column_ref1 -> ST_NULL_S
	private void do_column_ref1_1(){
		boolean bool=this.doCommon();
	}
	//column_ref1 -> . column_ref2
	private void do_column_ref1_2(){
		boolean bool=this.doCommon();
	}
	//column_ref2 ->  *
	private void do_column_ref2_1(){
		boolean bool=this.doCommon();
	}
	//column_ref2 ->  IDENT
	private void do_column_ref2_2(){
		boolean bool=this.doCommon();
	}



	//scalar_exp4 -> * )
	private void do_scalar_exp4_1(){
		boolean bool=this.doCommon();
	}
	//scalar_exp4 -> column_ref )
	private void do_scalar_exp4_2(){
		boolean bool=this.doCommon();
	}




	//scalar_exp7 -> ST_NULL_S  
	private void do_scalar_exp7_1(){

		boolean bool=this.doCommon();
	}


	//scalar_exp7 -> OPERATOR  scalar_exp5  scalar_exp7  
	private void do_scalar_exp7_2(){

		boolean bool=this.doCommon();
	}


	//scalar_exp -> scalar_exp5 scalar_exp7
	private void do_scalar_exp_1(){
		
		boolean bool=this.doCommon();
		if(bool){
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();
			if(list.size()>=3){
				t1=(String)list.get(0);
				list.remove(0);
				Iterator iter=list.iterator();
				while(iter.hasNext()){
					oper=(String)iter.next();
					t2=(String)iter.next();
					this.showResult(oper, t1, t2);
					t1=this.syntax.getLeft().getSyntaxResult();
				}
				
			}
		}
	}



	//scalar_exp8 -> OPERATOR1  scalar_exp6  scalar_exp8  
	private void do_scalar_exp8_1(){

		boolean bool=this.doCommon();
		
	}


	//scalar_exp8 -> ST_NULL_S  
	private void do_scalar_exp8_2(){
		boolean bool=this.doCommon();
		
	}


	//scalar_exp5 -> scalar_exp6 scalar_exp8
	private void do_scalar_exp5_1(){

		boolean bool=this.doCommon();
		if(bool){
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();
			if(list.size()>=3){
				t1=(String)list.get(0);
				list.remove(0);
				Iterator iter=list.iterator();
				while(iter.hasNext()){
					oper=(String)iter.next();
					t2=(String)iter.next();
					this.showResult(oper, t1, t2);
					t1=this.syntax.getLeft().getSyntaxResult();
				}
				
			}
			
//			if(list.size()==3){
//				t1=(String)list.get(0);
//				oper=(String)list.get(1);
//				t2=(String)list.get(2);
//				this.showResult(oper, t1, t2);
//			}

			
			
			
		}
	}



	//scalar_exp6 -> + scalar_exp9  
	//+ 0 t t
	private void do_scalar_exp6_1(){
		boolean bool=this.doCommon();
		if(bool){
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();
			
			if(list.size()==2){
				oper=(String)list.get(0);
				t1="0";
				t2=(String)list.get(1);
				this.showResult(oper, t1, t2);
			}
			
			
			
		}
	}
	
	//scalar_exp6 -> - scalar_exp9 
	private void do_scalar_exp6_2(){
		boolean bool=this.doCommon();
		if(bool){
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();
			
			if(list.size()==2){
				oper=(String)list.get(0);
				t1="0";
				t2=(String)list.get(1);
				this.showResult(oper, t1, t2);
			}
			
			
			
		}
	}
	//scalar_exp6 -> scalar_exp9
	private void do_scalar_exp6_3(){
		boolean bool=this.doCommon();

	}



	//scalar_exp9 -> ( scalar_exp ) 
	private void do_scalar_exp9_1(){
		boolean bool=this.doCommon();
	}
	//scalar_exp9 -> atom 
	private void do_scalar_exp9_2(){
		boolean bool=this.doCommon();
	}
	//scalar_exp9 -> column_ref
	private void do_scalar_exp9_3(){
		boolean bool=this.doCommon();
	}
	//scalar_exp9 -> FUNC ( func_exp
	//func t - t
	private void do_scalar_exp9_4(){
		boolean bool=this.doCommon();
		if(bool){
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();
            if(list.size()==2){
				oper=(String)list.get(0);
				t1=(String)list.get(1);
				if(t1.equals("*")&&!oper.equals("COUNT")){
					this.errList.add("聚函数:"+oper+"有误!");
				}
				t2="ALL";
				this.showResult(oper, t1, t2);
			}else if(list.size()==3){
				oper=(String)list.get(0);
				t1=(String)list.get(2);
				t2=(String)list.get(1);
				if(t1.equals("*")&&!oper.equals("COUNT")){
					this.errList.add("聚函数:"+oper+"有误!");
				}
				this.showResult(oper, t1, t2);
			}
			
			
			
		}
	}



	//func_exp -> opt_all_distinct scalar_exp4
	//t1 t2 - t3 //如果 select_result的token为一个就加入t1为all
	private void do_func_exp_1(){
		boolean bool=this.doCommon();
//		if(bool){
//			
//			String oper="";
//			String t1="";	
//			String t2="";
//			List list=this.getResultList();
//			
//			if(list.size()==1){
//				oper="ALL";
//				t1=(String)list.get(0);
//				t2="-";
//				this.showResult(oper, t1, t2);
//			}else if(list.size()==2){
//				oper=(String)list.get(0);
//				t1=(String)list.get(1);
//				this.showResult(oper, t1, t2);
//			}
//			
//			
//			
//		}
	}



	//FUNC -> COUNT  
	private void do_FUNC_1(){
		boolean bool=this.doCommon();
	}


	//FUNC -> SUM  
	private void do_FUNC_2(){
		boolean bool=this.doCommon();
	}


	//FUNC -> AVG  
	private void do_FUNC_3(){
		boolean bool=this.doCommon();
	}


	//FUNC -> MAX  
	private void do_FUNC_4(){
		boolean bool=this.doCommon();
	}


	//FUNC -> MIN  
	private void do_FUNC_5(){
		boolean bool=this.doCommon();
	}


	//table_exp -> from_clause  opt_where_clause  opt_group_by_clause  opt_having_clause  opt_order_by_clause  
	private void do_table_exp_1(){
		boolean bool=this.doCommon();
	}


	//from_clause -> FROM  table_ref_commalist  
	//from t2 - t
	private void do_from_clause_1(){
		boolean bool=this.doCommon();
		if(bool){
			
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();
			
			if(list.size()==1){
				t2="-";
				oper="from";
				t1=(String)list.get(0);
				this.showResult(oper, t1, t2);
			}
			
			
			
		}
	}


	//table_ref_one -> IDENT  parameter_ref  
//	table t t t
//	table t - t  没有参数
	private void do_table_ref_one_1(){
		boolean bool=this.doCommon();
		if(bool){
			
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();
			
			oper="RecordTable";
			if(list.size()==1){
				t1=(String)list.get(0);
				t2="-";
				this.showResult(oper, t1, t2);
			}else if(list.size()==2){
				t1=(String)list.get(0);
				t2=(String)list.get(1);
				this.showResult(oper, t1, t2);
			}
			
			
			
		}
	}
	

	//table_ref_commalist -> table_ref_one  table_ref_commalist1  
	//add t1 - t
	private void do_table_ref_commalist_1(){
		boolean bool=this.doCommon();
//		if(bool){
//			String oper="";
//			String t1="";	
//			String t2="";
//			List list=this.getResultList();
//			if(list.size()==2){
//				oper="add";
//	            t1=(String)list.get(1);
//
//				t2=(String)list.get(0);
//				
//				this.showResult(oper, t1, t2);
//			}
//
//			
//		}
		if(bool){
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();
			if(list.size()>=2){
				int size=list.size();
				t1=(String)list.get(size-1);
				list.remove(size-1);
				Iterator iter=list.iterator();	
				while(iter.hasNext()){
					oper="add";
					t2=(String)iter.next();
					this.showResult(oper, t1, t2);
					t1=this.syntax.getLeft().getSyntaxResult();
				}
				
			}
		}
		
	}


	//table_ref_commalist1 -> ST_NULL_S  
	private void do_table_ref_commalist1_1(){
		boolean bool=this.doCommon();
		if(bool){
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();
			if(list.size()==0){
				oper="new";
	            t1="List";
				t2="RecordTable";		//List 的类型	
				this.showResult(oper, t1, t2);
			}

		}
	}


	//table_ref_commalist1 -> ,  table_ref_one  table_ref_commalist1  
	private void do_table_ref_commalist1_2(){
		boolean bool=this.doCommon();
//		if(bool){
//			String oper="";
//			String t1="";	
//			String t2="";
//			List list=this.getResultList();
//			if(list.size()==2){
//				oper="add";
//	            t1=(String)list.get(1);
//
//				t2=(String)list.get(0);
//				
//				this.showResult(oper, t1, t2);
//			}
//
//			
//		}
	}


	//opt_where_clause -> WHERE  search_condition  
	//where t1 - t
	private void do_opt_where_clause_1(){
		boolean bool=this.doCommon();
		if(bool){
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();
			if(list.size()==1){
				oper="where";
	            t1=(String)list.get(0);

				t2="-";
				
				this.showResult(oper, t1, t2);
			}

			
		}
	}


	//opt_where_clause -> ST_NULL_S  
	//where - - t
	private void do_opt_where_clause_2(){
		boolean bool=this.doCommon();
		if(bool){
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();
			if(list.size()==0){
				oper="where";
	            t1="-";

				t2="-";
				
				this.showResult(oper, t1, t2);
			}

			
		}
	}


	//opt_group_by_clause -> GROUP  BY  column_ref_commalist  
	//group_by t1 - t
	private void do_opt_group_by_clause_1(){
		boolean bool=this.doCommon();
		if(bool){
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();
			if(list.size()==1){
				oper="group_by";
	            t1=(String)list.get(0);

				t2="-";
				
				this.showResult(oper, t1, t2);
			}

			
		}
	}


	//opt_group_by_clause -> ST_NULL_S  
	//group_by - - t
	private void do_opt_group_by_clause_2(){
		boolean bool=this.doCommon();
		if(bool){
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();
			if(list.size()==0){
				oper="group_by";
	            t1="-";

				t2="-";
				
				this.showResult(oper, t1, t2);
			}

			
		}
	}


	//column_ref_commalist -> column_ref  column_ref_commalist1  
	//add t2 t1 t
	private void do_column_ref_commalist_1(){
		boolean bool=this.doCommon();
		if(bool){
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();	
			for(int i=0;i<list.size();i++){
				oper="COLUMN";
				t1=(String)list.get(i);
				t2="-";
				this.showResult(oper, t1, t2);
			}
			
		}
		
//		if(bool){
//			String oper="";
//			String t1="";	
//			String t2="";
//			List list=this.getResultList();			
//			if(list.size()==2){
//				oper="add";
//	            t1=(String)list.get(1);
//
//				t2=(String)list.get(0);
//				this.showResult(oper, t1, t2);
//			}
//
//			
//			
//			
//		}
		
	}


	//column_ref_commalist1 -> ST_NULL_S  
	private void do_column_ref_commalist1_1(){
		boolean bool=this.doCommon();
//		if(bool){
//			String oper="";
//			String t1="";	
//			String t2="";
//			List list=this.getResultList();
//			if(list.size()==0){
//				oper="new";
//	            t1="List"; 
//				t2="Column";		//List 的类型	
//				this.showResult(oper, t1, t2);
//			}
//
//		}
	}


	//column_ref_commalist1 -> ,  column_ref  column_ref_commalist1  
	private void do_column_ref_commalist1_2(){
		boolean bool=this.doCommon();
//		if(bool){
//			String oper="";
//			String t1="";	
//			String t2="";
//			List list=this.getResultList();
//			
//			if(list.size()==2){
//				oper="add";
//	            t1=(String)list.get(1);
//				t2=(String)list.get(0);
//				this.showResult(oper, t1, t2);
//			}
//
//		}
	}


	//opt_having_clause -> HAVING  search_condition  
	private void do_opt_having_clause_1(){
		boolean bool=this.doCommon();
		if(bool){
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();
			if(list.size()==1){
				oper="having";
	            t1=(String)list.get(0);

				t2="-";
				
				this.showResult(oper, t1, t2);
			}
			
		}
	}


	//opt_having_clause -> ST_NULL_S  
	private void do_opt_having_clause_2(){
		boolean bool=this.doCommon();
		if(bool){
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();
			if(list.size()==0){
				oper="having";
	            t1="-";

				t2="-";
				
				this.showResult(oper, t1, t2);
			}

			
		}
	}


	//COMPARISON -> =  
	private void do_COMPARISON_1(){
		boolean bool=this.doCommon();
	}


	//COMPARISON -> <>  
	private void do_COMPARISON_2(){
		boolean bool=this.doCommon();
	}


	//COMPARISON -> <  
	private void do_COMPARISON_3(){
		boolean bool=this.doCommon();
	}


	//COMPARISON -> >  
	private void do_COMPARISON_4(){
		boolean bool=this.doCommon();
	}


	//COMPARISON -> <=  
	private void do_COMPARISON_5(){
		boolean bool=this.doCommon();
	}


	//COMPARISON -> >=  
	private void do_COMPARISON_6(){
		boolean bool=this.doCommon();
	}



	//search_condition -> search_condition11  search_condition13  
	private void do_search_condition_1(){
		boolean bool=this.doCommon();
//		if(bool){
//			String oper="OR";
//			List list=this.getResultList();
//			int size=list.size();
//			String t1=(String)list.get(size-1);
//			String t2="";
//			list.remove(size-1);
//			size=list.size();
//			for(int i=0;i<size;i=i+2){
//				t2=(String)list.get(i);
//				this.showResult(oper, t1, t2);
//				t1=this.syntax.getLeft().getSyntaxResult();
//			}
//		}
		if(bool){
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();
			
			if(list.size()>=3){
				t1=(String)list.get(0);
				list.remove(0);
				Iterator iter=list.iterator();
				while(iter.hasNext()){
					oper=(String)iter.next();
					t2=(String)iter.next();
					this.showResult(oper, t1, t2);
					t1=this.syntax.getLeft().getSyntaxResult();
				}
				
			}
		}
	}
	
	//search_condition13 -> OR  search_condition11  search_condition13  
	private void do_search_condition13_1(){
		boolean bool=this.doCommon();
		
	}

	//search_condition13 -> ST_NULL_S  
	private void do_search_condition13_2(){
		boolean bool=this.doCommon();
//		if(bool){
//			String oper="";
//			String t1="";	
//			String t2="";
//			List list=this.getResultList();
//			if(list.size()==0){
//				oper="new";
//	            t1="List"; 
//				t2="OR_CONDITION";		//List 的类型	
//				this.showResult(oper, t1, t2);
//			}
//
//		}
	}


	//search_condition14 -> AND  search_condition12  search_condition14  
	private void do_search_condition14_1(){
		boolean bool=this.doCommon();
//		if(bool){
//			String oper="add";
//			List list=this.getResultList();
//			int size=list.size();
//			String t1=(String)list.get(size-1);
//			String t2="";
//			list.remove(size-1);
//			size=list.size();
//			for(int i=0;i<size;i=i+2){
//				t2=(String)list.get(i);
//				this.showResult(oper, t1, t2);
//				t1=this.syntax.getLeft().getSyntaxResult();
//			}
//		}
	}


	//search_condition14 -> ST_NULL_S  
	private void do_search_condition14_2(){
		boolean bool=this.doCommon();
//		if(bool){
//			String oper="";
//			String t1="";	
//			String t2="";
//			List list=this.getResultList();
//			if(list.size()==0){
//				oper="new";
//	            t1="List"; 
//				t2="AND_CONDITION";		//List 的类型	
//				this.showResult(oper, t1, t2);
//			}
//
//		}
	}


	//search_condition11 -> search_condition12  search_condition14  
	private void do_search_condition11_1(){
		boolean bool=this.doCommon();
		if(bool){
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();
			if(list.size()>=3){
				t1=(String)list.get(0);
				list.remove(0);
				Iterator iter=list.iterator();
				while(iter.hasNext()){
					oper=(String)iter.next();
					t2=(String)iter.next();
					this.showResult(oper, t1, t2);
					t1=this.syntax.getLeft().getSyntaxResult();
				}
				
			}
		}
//		if(bool){
//			String oper="add";
//			List list=this.getResultList();
//			int size=list.size();
//			String t1=(String)list.get(size-1);
//			String t2="";
//			list.remove(size-1);
//			size=list.size();
//			for(int i=0;i<size;i=i+2){
//				t2=(String)list.get(i);
//				this.showResult(oper, t1, t2);
//				t1=this.syntax.getLeft().getSyntaxResult();
//			}
//		}
	}


	//search_condition12 -> NOT  search_condition15  
	//NOT t1 - t2
	private void do_search_condition12_1(){
		boolean bool=this.doCommon();
		if(bool){
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();
			
			if(list.size()==2){
				oper=(String)list.get(0);
				t1=(String)list.get(1);
				t2="-";
				this.showResult(oper, t1, t2);
			}
			
			
			
		}
	}


	//search_condition12 -> search_condition15  
	private void do_search_condition12_2(){
		boolean bool=this.doCommon();
	}


	//search_condition15 -> scalar_exp  search_condition1  
	private void do_search_condition15_1(){
		boolean bool=this.doCommon();
		if(bool){
			String oper="";
			String t1="";	
			String t2="";
			String t3="";
			List list=this.getResultList();
			
			/*
	search_condition -> scalar_exp COMPARISON scalar_exp                3
	search_condition -> scalar_exp NOT BETWEEN scalar_exp AND scalar_exp 6
	search_condition -> scalar_exp BETWEEN scalar_exp AND scalar_exp    5
	search_condition -> scalar_exp NOT LIKE atom opt_escape      5
	search_condition -> scalar_exp LIKE atom opt_escape    4
	search_condition -> scalar_exp IS NOT NULL     4
	search_condition -> scalar_exp IS NULL          3
	search_condition -> scalar_exp NOT IN ( query_spec )   4
	search_condition -> scalar_exp IN ( query_spec )       3
	search_condition -> scalar_exp NOT IN ( atom_commalist )  4
	search_condition -> scalar_exp IN ( atom_commalist )   3
	search_condition -> scalar_exp COMPARISON any_all_some ( query_spec ) 4
			*/
			if(list.size()>=3){
				boolean isCOMPARISON=false;
				boolean isNOT_BETWEEN=false;
				boolean isBETWEEN=false;
				boolean isNOT_LIKE=false;
				boolean isLIKE=false;
				boolean isIS_NOT_NULL=false;
				boolean isIS_NULL=false;
				
				boolean isNOT_IN=false;
				boolean isIN=false;
				
				boolean isCOMPARISONany_all_some=false;
				
	            String s1=(String)list.get(1);
	           
	            if(s1.equals("NOT")){
	            	String s2=(String)list.get(2);
	            	if(s2.equals("BETWEEN")){
	            		isNOT_BETWEEN=true;
		            }else if(s2.equals("LIKE")){
		            	isNOT_LIKE=true;
		            }else if(s2.equals("IN")){
		            	isNOT_IN=true;
		            }
	            }else if(s1.equals("BETWEEN")){
	            	isBETWEEN=true;
	            }else if(s1.equals("LIKE")){
	            	isLIKE=true;
	            }else if(s1.equals("IS")){
	            	if(list.size()==3){
	            		isIS_NULL=true;
	            	}else if(list.size()==4){
	            		isIS_NOT_NULL=true;
	            	}
	            }else if(s1.equals("IN")){
	            	isIN=true;
	            }else{
	            	if(list.size()==3){
	            		isCOMPARISON=true;
	            	}else if(list.size()==4){
	            		isCOMPARISONany_all_some=true;
	            	}
	            }
	            
	            if(isCOMPARISON){
	            	oper=(String)list.get(1);
	            	t1=(String)list.get(0);
	            	t2=(String)list.get(2);
	            	this.showResult(oper, t1, t2);
	            }else if(isNOT_BETWEEN){
	            	oper="NOT_BETWEEN";
	            	t1=(String)list.get(0);
	            	t2=(String)list.get(3);
	            	t3=(String)list.get(5);
	            	this.showFiveResult(oper, t1, t2, t3);	
	            }else if(isBETWEEN){
	            	oper="BETWEEN";
	            	t1=(String)list.get(0);
	            	t2=(String)list.get(2);
	            	t3=(String)list.get(4);
	            	this.showFiveResult(oper, t1, t2, t3);	
	            }else if(isNOT_LIKE){
	            	oper="NOT_LIKE";
	            	t1=(String)list.get(0);
	            	t2=(String)list.get(3);
	            	if(list.size()==4){
	            		t3="-";
	            	}else if(list.size()==5){
	            		t3=(String)list.get(4);
	            	}
	            	
	            	this.showFiveResult(oper, t1, t2, t3);	
	            }else if(isLIKE){
	            	//System.out.println("likeSize:..."+list.size());
	            	oper="LIKE";
	            	t1=(String)list.get(0);
	            	t2=(String)list.get(2);
	            	if(list.size()==3){
	            		t3="-";
	            	}else if(list.size()==4){
	            		t3=(String)list.get(3);
	            	}	            	
	            	this.showFiveResult(oper, t1, t2, t3);	
	            }else if(isIS_NOT_NULL){
	            	oper="IS_NOT_NULL";
	            	t1=(String)list.get(0);
	            	t2="-";	            	
	            	this.showResult(oper, t1, t2);	
	            }else if(isIS_NULL){
	            	oper="IS_NULL";
	            	t1=(String)list.get(0);
	            	t2="-";	            	
	            	this.showResult(oper, t1, t2);	
	            }else if(isNOT_IN){
	            	oper="NOT_IN";
	            	t1=(String)list.get(0);
	            	t2=(String)list.get(3);;	            	
	            	this.showResult(oper, t1, t2);	
	            }else if(isIN){
	            	oper="IN";
	            	t1=(String)list.get(0);
	            	t2=(String)list.get(2);;	            	
	            	this.showResult(oper, t1, t2);	
	            }else if(isCOMPARISONany_all_some){
	            	oper=(String)list.get(1)+(String)list.get(2);
	            	t1=(String)list.get(0);
	            	t2=(String)list.get(3);
	            	this.showResult(oper, t1, t2);
	            }
	            
			}			

		}
	}


	//search_condition15 -> search_condition9  
	private void do_search_condition15_2(){
		boolean bool=this.doCommon();
	}


	//search_condition9 -> EXISTS  (  subquery  )  
	private void do_search_condition9_1(){
		boolean bool=this.doCommon();
		if(bool){
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();
			if(list.size()==2){
				oper=(String)list.get(0); 
				t1=(String)list.get(1);
				t2="-";
				this.showResult(oper, t1, t2);
			}
		}
	}


	//search_condition1 -> COMPARISON  search_condition8  
	private void do_search_condition1_1(){
		boolean bool=this.doCommon();
	}


	//search_condition1 -> NOT  search_condition7  
	private void do_search_condition1_2(){
		boolean bool=this.doCommon();
	}


	//search_condition1 -> search_condition2  
	private void do_search_condition1_3(){
		boolean bool=this.doCommon();
	}


	//search_condition1 -> search_condition3  
	private void do_search_condition1_4(){
		boolean bool=this.doCommon();
	}


	//search_condition1 -> search_condition4  
	private void do_search_condition1_5(){
		boolean bool=this.doCommon();
	}


	//search_condition1 -> IS  search_condition6  
	private void do_search_condition1_6(){
		boolean bool=this.doCommon();
	}


	//search_condition2 -> BETWEEN  scalar_exp  AND  scalar_exp  
	private void do_search_condition2_1(){
		boolean bool=this.doCommon();
	}


	//search_condition3 -> LIKE  atom  opt_escape  
	private void do_search_condition3_1(){
		boolean bool=this.doCommon();
	}


	//search_condition4 -> IN  (  search_condition5  
	private void do_search_condition4_1(){
		boolean bool=this.doCommon();

	}


	//search_condition5 -> subquery  )  
	private void do_search_condition5_1(){
		boolean bool=this.doCommon();
	}


	//search_condition5 -> atom_commalist  )  
	private void do_search_condition5_2(){
		boolean bool=this.doCommon();
	}


	//search_condition6 -> NOT  NULL  
	private void do_search_condition6_1(){
		boolean bool=this.doCommon();
	}


	//search_condition6 -> NULL  
	private void do_search_condition6_2(){
		boolean bool=this.doCommon();
	}


	//search_condition7 -> search_condition2  
	private void do_search_condition7_1(){
		boolean bool=this.doCommon();
	}


	//search_condition7 -> search_condition3  
	private void do_search_condition7_2(){
		boolean bool=this.doCommon();
	}


	//search_condition7 -> search_condition4  
	private void do_search_condition7_3(){
		boolean bool=this.doCommon();
	}


	//search_condition8 -> scalar_exp  
	private void do_search_condition8_1(){
		boolean bool=this.doCommon();
	}


	//search_condition8 -> any_all_some  (  subquery  )  
	private void do_search_condition8_2(){
		boolean bool=this.doCommon();
	}


	//opt_escape -> ESCAPE  atom  
	private void do_opt_escape_1(){
		boolean bool=this.doCommon();
//		if(bool){
//			String oper="";
//			String t1="";	
//			String t2="";
//			List list=this.getResultList();
//			if(list.size()==2){
//				oper=(String)list.get(0); 
//				t1=(String)list.get(1);
//				t2="-";
//				this.showResult(oper, t1, t2);
//			}
//		}
	}


	//opt_escape -> ST_NULL_S  
	private void do_opt_escape_2(){
		boolean bool=this.doCommon();
	}


	//atom_commalist -> atom  atom_commalist1  
	private void do_atom_commalist_1(){
		boolean bool=this.doCommon();
		if(bool){
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();
			
			if(list.size()==2){
				oper="add";
	            t1=(String)list.get(1);
				t2=(String)list.get(0);
				this.showResult(oper, t1, t2);
			}

		}
	}


	//atom_commalist1 -> ,  atom  atom_commalist1  
	private void do_atom_commalist1_1(){
		boolean bool=this.doCommon();
		if(bool){
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();
			
			if(list.size()==2){
				oper="add";
	            t1=(String)list.get(1);
				t2=(String)list.get(0);
				this.showResult(oper, t1, t2);
			}

		}
	}


	//atom_commalist1 -> ST_NULL_S  
	private void do_atom_commalist1_2(){
		boolean bool=this.doCommon();
		if(bool){
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();
			if(list.size()==0){
				oper="new";
	            t1="List";
				t2="String";		//List 的类型	
				this.showResult(oper, t1, t2);
			}

		}
	}


	//any_all_some -> ANY  
	private void do_any_all_some_1(){
		boolean bool=this.doCommon();
	}


	//any_all_some -> ALL  
	private void do_any_all_some_2(){
		boolean bool=this.doCommon();
	}


	//any_all_some -> SOME  
	private void do_any_all_some_3(){
		boolean bool=this.doCommon();
	}


	
	
	//subquery -> SELECT  select_result  table_exp  
	private void do_subquery_1(){
		boolean bool=this.doCommon();
		if(bool){
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();
			if(list.size()>=1){
				this.syntax.getLeft().setSyntaxResult((String)list.get(0));
				oper="ResultSetTemp";
				t1=(String)list.get(0);
				t2="-";
				this.showResult(oper, t1, t2);
				//this.queryList.add(this.syntax);  //把查询添加到查询list中
			}

		}
	}


	//opt_order_by_clause -> ORDER  BY  ordering_spec_commalist  
	private void do_opt_order_by_clause_1(){
		boolean bool=this.doCommon();
		if(bool){
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();
			if(list.size()>0){
				oper="order_by";
	            t1=(String)list.get(list.size()-1);

				t2="-";
				
				this.showResult(oper, t1, t2);
			}

			
		}
	}


	//opt_order_by_clause -> ST_NULL_S  
	private void do_opt_order_by_clause_2(){
		boolean bool=this.doCommon();
		if(bool){
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();
			if(list.size()==0){
				oper="order_by";
	            t1="-";

				t2="-";
				
				this.showResult(oper, t1, t2);
			}

			
		}
	}


	//ordering_spec_commalist -> ordering_spec  ordering_spec_commalist1  
	private void do_ordering_spec_commalist_1(){
		boolean bool=this.doCommon();
//		if(bool){
//			String oper="";
//			String t1="";	
//			String t2="";
//			List list=this.getResultList();
//			
//			if(list.size()==2){
//				oper="add";
//	            t1=(String)list.get(1);
//				t2=(String)list.get(0);
//				this.showResult(oper, t1, t2);
//			}
//
//		}
//		if(bool){
//			String oper="";
//			String t1="";	
//			String t2="";
//			List list=this.getResultList();
//			if(list.size()>=2){
//				int size=list.size();
//				t1=(String)list.get(size-1);
//				list.remove(size-1);
//				Iterator iter=list.iterator();	
//				while(iter.hasNext()){
//					oper="add";
//					t2=(String)iter.next();
//					this.showResult(oper, t1, t2);
//					t1=this.syntax.getLeft().getSyntaxResult();
//				}
//				
//			}
//		}
	}


	//ordering_spec_commalist1 -> ST_NULL_S  
	private void do_ordering_spec_commalist1_1(){
		boolean bool=this.doCommon();
//		if(bool){
//			String oper="";
//			String t1="";	
//			String t2="";
//			List list=this.getResultList();
//			if(list.size()==0){
//				oper="new";
//	            t1="List";
//				t2="OrderBy";		//List 的类型	
//				this.showResult(oper, t1, t2);
//			}
//
//		}
	}


	//ordering_spec_commalist1 -> ,  ordering_spec  ordering_spec_commalist1  
	private void do_ordering_spec_commalist1_2(){
		boolean bool=this.doCommon();
//		if(bool){
//			String oper="";
//			String t1="";	
//			String t2="";
//			List list=this.getResultList();
//			
//			if(list.size()==2){
//				oper="add";
//	            t1=(String)list.get(1);
//				t2=(String)list.get(0);
//				this.showResult(oper, t1, t2);
//			}
//
//		}
	}


	//ordering_spec -> column_ref  opt_asc_desc  
	private void do_ordering_spec_1(){
		boolean bool=this.doCommon();
		if(bool){
			String oper="";
			String t1="";	
			String t2="";
			List list=this.getResultList();
			if(list.size()==1){
				oper="COLUMN";
				t1=(String)list.get(0);
				t2="ASC";
				this.showResult(oper, t1, t2);
			}
			if(list.size()==2){
				oper="COLUMN";
	            t1=(String)list.get(0);
				t2=(String)list.get(1);
				this.showResult(oper, t1, t2);
			}

		}
	}


	//opt_asc_desc -> ASC  
	private void do_opt_asc_desc_1(){
		boolean bool=this.doCommon();
	}



	//opt_asc_desc -> DESC  
	private void do_opt_asc_desc_2(){
		boolean bool=this.doCommon();
	}

	//opt_asc_desc -> ST_NULL_S  
	private void do_opt_asc_desc_3(){
		boolean bool=this.doCommon();
	}
	

	
	//获取result的每一个token 
	private List getResultList(){
		String result=this.syntax.getLeft().getSyntaxResult();
		StringTokenizer tokenizer=new StringTokenizer(result," ");//" "为分隔符
		List temp=new ArrayList();
		while(tokenizer.hasMoreTokens()){
			String sTemp=(String)tokenizer.nextToken();
			temp.add(sTemp);
		}
		return temp;
	}
	//显示结果  4个的
	private void showResult(String oper,String t1,String t2){
		try {
			String ti="t"+ST_APP.ti;
			ST_APP.ti++;
			List list=new ArrayList();
			list.add(oper.toUpperCase());
			list.add(t1.toUpperCase());
			list.add(t2.toUpperCase());
			list.add(ti.toUpperCase());
			AnalyzeSyntax.middleCodeList.add(list);
//			fileWriter.write(oper.toUpperCase()+"     ");
//			fileWriter.write(t1.toUpperCase()+"      ");
//			fileWriter.write(t2.toUpperCase()+"      ");
//			fileWriter.write(ti.toUpperCase()+"      ");
//			fileWriter.newLine();
			this.syntax.getLeft().setSyntaxResult(ti);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//显示结果 5个
	private void showFiveResult(String oper,String t1,String t2,String t3){
		try {
			
			String ti="t"+ST_APP.ti;
			ST_APP.ti++;
			List list=new ArrayList();
			list.add(oper.toUpperCase());
			list.add(t1.toUpperCase());
			list.add(t2.toUpperCase());
			list.add(t3.toUpperCase());
			list.add(ti.toUpperCase());
			AnalyzeSyntax.middleCodeList.add(list);
//			fileWriter.write(oper.toUpperCase()+"     ");
//			fileWriter.write(t1.toUpperCase()+"      ");
//			fileWriter.write(t2.toUpperCase()+"      ");
//			fileWriter.write(t3.toUpperCase()+"      ");
//			fileWriter.write(ti.toUpperCase()+"      ");
//			fileWriter.newLine();
			
			this.syntax.getLeft().setSyntaxResult(ti);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void showTree(Syntax syntax){
		int i=syntax.getRight().size();
		if(i==0){
			if(syntax.getLeft().getType()!=ST_TYPE.ST_NULL_VT){
				this.selColumnName+=syntax.getLeft().getName();
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
		
		String testStr = ST_APP.testSql;
		//testStr = "select a+1 from c ";
		System.out.println("testStr:"+testStr);
       TokenAnalyze ta = new TokenAnalyze(testStr);
	   List tokens=ta.getTokens();
	   SyntaxGrenerator sa=new SyntaxGrenerator(ST_APP.DBMS_SQL_SELECT_EXP,tokens,ST_APP.ST_query_spec);
	   
	   Syntax syntax=sa.getSyntaxTree();  //得到语法树
	   
	   System.out.println(".........");
	   sa.showTree(syntax);
	   System.out.println();
	   AnalyzeSyntax aes=new AnalyzeSyntax();
	   Query query=new Query(syntax);
	   
	   List list=query.getQueryList();
	   for(int i=list.size()-1;i>=0;i--){
		   Syntax s=(Syntax)list.get(i);
		   aes.greneratorMiddleCode(s);
	   }
	   

	}
	

}
