package com.stone.dosql.codegenerator.impl;

public class RuleType {
	protected String query_spec_1 = "query_spec ->  SELECT  select_result  table_exp";
	protected String select_result_1 = "select_result ->  opt_all_distinct  scalar_exp_commalist";
	protected String opt_all_distinct_1 = "opt_all_distinct ->  ALL";
	protected String opt_all_distinct_2 = "opt_all_distinct ->  DISTINCT";
	protected String opt_all_distinct_3 = "opt_all_distinct ->  ST_NULL_S";
	protected String parameter_ref_1 = "parameter_ref ->  ST_NULL_S";
	protected String parameter_ref_2 = "parameter_ref ->  IDENT";
	protected String parameter_ref_3 = "parameter_ref ->  AS  IDENT";
	protected String all_table_allcolumn_1 = "all_table_allcolumn ->  *";
	protected String scalar_exp_one_1 = "scalar_exp_one ->  scalar_exp  parameter_ref";
	protected String scalar_exp_one_2 = "scalar_exp_one ->  all_table_allcolumn";
	protected String scalar_exp_commalist_1 = "scalar_exp_commalist ->  scalar_exp_one  scalar_exp_commalist1";
	protected String scalar_exp_commalist1_1 = "scalar_exp_commalist1 ->  ,  scalar_exp_one  scalar_exp_commalist1";
	protected String scalar_exp_commalist1_2 = "scalar_exp_commalist1 ->  ST_NULL_S";
	protected String OPERATOR_1 = "OPERATOR ->  +";
	protected String OPERATOR_2 = "OPERATOR ->  -";
	protected String OPERATOR1_1 = "OPERATOR1 ->  *";
	protected String OPERATOR1_2 = "OPERATOR1 ->  /";
	protected String atom_1 = "atom ->  '  STRINGCONST  '";
	protected String atom_2 = "atom ->  \"  STRINGCONST  \"";
	protected String atom_3 = "atom ->  DIGITALCONST";
	protected String column_ref_1 = "column_ref ->  IDENT  column_ref1";
	protected String column_ref1_1 = "column_ref1 ->  ST_NULL_S";
	protected String column_ref1_2 = "column_ref1 ->  .  column_ref2";
	protected String column_ref2_1 = "column_ref2 ->  *";
	protected String column_ref2_2 = "column_ref2 ->  IDENT";
	protected String scalar_exp4_1 = "scalar_exp4 ->  *  )";
	protected String scalar_exp4_2 = "scalar_exp4 ->  column_ref  )";
	protected String scalar_exp7_1 = "scalar_exp7 ->  ST_NULL_S";
	protected String scalar_exp7_2 = "scalar_exp7 ->  OPERATOR  scalar_exp5  scalar_exp7";
	protected String scalar_exp_1 = "scalar_exp ->  scalar_exp5  scalar_exp7";
	protected String scalar_exp8_1 = "scalar_exp8 ->  OPERATOR1  scalar_exp6  scalar_exp8";
	protected String scalar_exp8_2 = "scalar_exp8 ->  ST_NULL_S";
	protected String scalar_exp5_1 = "scalar_exp5 ->  scalar_exp6  scalar_exp8";
	protected String scalar_exp6_1 = "scalar_exp6 ->  +  scalar_exp9";
	protected String scalar_exp6_2 = "scalar_exp6 ->  -  scalar_exp9";
	protected String scalar_exp6_3 = "scalar_exp6 ->  scalar_exp9";
	protected String scalar_exp9_1 = "scalar_exp9 ->  (  scalar_exp  )";
	protected String scalar_exp9_2 = "scalar_exp9 ->  atom";
	protected String scalar_exp9_3 = "scalar_exp9 ->  column_ref";
	protected String scalar_exp9_4 = "scalar_exp9 ->  FUNC  (  func_exp";
	protected String func_exp_1 = "func_exp ->  opt_all_distinct  scalar_exp4";
	protected String FUNC_1 = "FUNC ->  COUNT";
	protected String FUNC_2 = "FUNC ->  SUM";
	protected String FUNC_3 = "FUNC ->  AVG";
	protected String FUNC_4 = "FUNC ->  MAX";
	protected String FUNC_5 = "FUNC ->  MIN";
	protected String table_exp_1 = "table_exp ->  from_clause  opt_where_clause  opt_group_by_clause  opt_having_clause  opt_order_by_clause";
	protected String from_clause_1 = "from_clause ->  FROM  table_ref_commalist";
	protected String table_ref_one_1 = "table_ref_one ->  IDENT  parameter_ref";
	protected String table_ref_commalist_1 = "table_ref_commalist ->  table_ref_one  table_ref_commalist1";
	protected String table_ref_commalist1_1 = "table_ref_commalist1 ->  ST_NULL_S";
	protected String table_ref_commalist1_2 = "table_ref_commalist1 ->  ,  table_ref_one  table_ref_commalist1";
	protected String opt_where_clause_1 = "opt_where_clause ->  WHERE  search_condition";
	protected String opt_where_clause_2 = "opt_where_clause ->  ST_NULL_S";
	protected String opt_group_by_clause_1 = "opt_group_by_clause ->  GROUP  BY  column_ref_commalist";
	protected String opt_group_by_clause_2 = "opt_group_by_clause ->  ST_NULL_S";
	protected String column_ref_commalist_1 = "column_ref_commalist ->  column_ref  column_ref_commalist1";
	protected String column_ref_commalist1_1 = "column_ref_commalist1 ->  ST_NULL_S";
	protected String column_ref_commalist1_2 = "column_ref_commalist1 ->  ,  column_ref  column_ref_commalist1";
	protected String opt_having_clause_1 = "opt_having_clause ->  HAVING  search_condition";
	protected String opt_having_clause_2 = "opt_having_clause ->  ST_NULL_S";
	protected String COMPARISON_1 = "COMPARISON ->  =";
	protected String COMPARISON_2 = "COMPARISON ->  <>";
	protected String COMPARISON_3 = "COMPARISON ->  <";
	protected String COMPARISON_4 = "COMPARISON ->  >";
	protected String COMPARISON_5 = "COMPARISON ->  <=";
	protected String COMPARISON_6 = "COMPARISON ->  >=";
	protected String search_condition_1 = "search_condition ->  search_condition11  search_condition13";
	protected String search_condition13_1 = "search_condition13 ->  OR  search_condition11  search_condition13";
	protected String search_condition13_2 = "search_condition13 ->  ST_NULL_S";
	protected String search_condition14_1 = "search_condition14 ->  AND  search_condition12  search_condition14";
	protected String search_condition14_2 = "search_condition14 ->  ST_NULL_S";
	protected String search_condition11_1 = "search_condition11 ->  search_condition12  search_condition14";
	protected String search_condition12_1 = "search_condition12 ->  NOT  search_condition15";
	protected String search_condition12_2 = "search_condition12 ->  search_condition15";
	protected String search_condition15_1 = "search_condition15 ->  scalar_exp  search_condition1";
	protected String search_condition15_2 = "search_condition15 ->  search_condition9";
	protected String search_condition9_1 = "search_condition9 ->  EXISTS  (  subquery  )";
	protected String search_condition1_1 = "search_condition1 ->  COMPARISON  search_condition8";
	protected String search_condition1_2 = "search_condition1 ->  NOT  search_condition7";
	protected String search_condition1_3 = "search_condition1 ->  search_condition2";
	protected String search_condition1_4 = "search_condition1 ->  search_condition3";
	protected String search_condition1_5 = "search_condition1 ->  search_condition4";
	protected String search_condition1_6 = "search_condition1 ->  IS  search_condition6";
	protected String search_condition2_1 = "search_condition2 ->  BETWEEN  scalar_exp  AND  scalar_exp";
	protected String search_condition3_1 = "search_condition3 ->  LIKE  atom  opt_escape";
	protected String search_condition4_1 = "search_condition4 ->  IN  (  search_condition5";
	protected String search_condition5_1 = "search_condition5 ->  subquery  )";
	protected String search_condition5_2 = "search_condition5 ->  atom_commalist  )";
	protected String search_condition6_1 = "search_condition6 ->  NOT  NULL";
	protected String search_condition6_2 = "search_condition6 ->  NULL";
	protected String search_condition7_1 = "search_condition7 ->  search_condition2";
	protected String search_condition7_2 = "search_condition7 ->  search_condition3";
	protected String search_condition7_3 = "search_condition7 ->  search_condition4";
	protected String search_condition8_1 = "search_condition8 ->  scalar_exp";
	protected String search_condition8_2 = "search_condition8 ->  any_all_some  (  subquery  )";
	protected String opt_escape_1 = "opt_escape ->  ESCAPE  atom";
	protected String opt_escape_2 = "opt_escape ->  ST_NULL_S";
	protected String atom_commalist_1 = "atom_commalist ->  atom  atom_commalist1";
	protected String atom_commalist1_1 = "atom_commalist1 ->  ,  atom  atom_commalist1";
	protected String atom_commalist1_2 = "atom_commalist1 ->  ST_NULL_S";
	protected String any_all_some_1 = "any_all_some ->  ANY";
	protected String any_all_some_2 = "any_all_some ->  ALL";
	protected String any_all_some_3 = "any_all_some ->  SOME";
	protected String subquery_1 = "subquery ->  SELECT  select_result  table_exp";
	protected String opt_order_by_clause_1 = "opt_order_by_clause ->  ORDER  BY  ordering_spec_commalist";
	protected String opt_order_by_clause_2 = "opt_order_by_clause ->  ST_NULL_S";
	protected String ordering_spec_commalist_1 = "ordering_spec_commalist ->  ordering_spec  ordering_spec_commalist1";
	protected String ordering_spec_commalist1_1 = "ordering_spec_commalist1 ->  ST_NULL_S";
	protected String ordering_spec_commalist1_2 = "ordering_spec_commalist1 ->  ,  ordering_spec  ordering_spec_commalist1";
	protected String ordering_spec_1 = "ordering_spec ->  column_ref  opt_asc_desc";
	protected String opt_asc_desc_1 = "opt_asc_desc ->  ASC";
	protected String opt_asc_desc_2 = "opt_asc_desc ->  DESC";
	protected String opt_asc_desc_3 = "opt_asc_desc ->  ST_NULL_S";
	public RuleType() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public String getQuery_spec_1() {
		return query_spec_1;
	}

	public String getSelect_result_1() {
		return select_result_1;
	}

	public String getOpt_all_distinct_1() {
		return opt_all_distinct_1;
	}

	public String getOpt_all_distinct_2() {
		return opt_all_distinct_2;
	}

	public String getOpt_all_distinct_3() {
		return opt_all_distinct_3;
	}

	public String getParameter_ref_1() {
		return parameter_ref_1;
	}

	public String getParameter_ref_2() {
		return parameter_ref_2;
	}

	public String getParameter_ref_3() {
		return parameter_ref_3;
	}

	public String getAll_table_allcolumn_1() {
		return all_table_allcolumn_1;
	}

	public String getScalar_exp_one_1() {
		return scalar_exp_one_1;
	}

	public String getScalar_exp_one_2() {
		return scalar_exp_one_2;
	}

	public String getScalar_exp_commalist_1() {
		return scalar_exp_commalist_1;
	}

	public String getScalar_exp_commalist1_1() {
		return scalar_exp_commalist1_1;
	}

	public String getScalar_exp_commalist1_2() {
		return scalar_exp_commalist1_2;
	}

	public String getOPERATOR_1() {
		return OPERATOR_1;
	}

	public String getOPERATOR_2() {
		return OPERATOR_2;
	}

	public String getOPERATOR1_1() {
		return OPERATOR1_1;
	}

	public String getOPERATOR1_2() {
		return OPERATOR1_2;
	}

	public String getAtom_1() {
		return atom_1;
	}

	public String getAtom_2() {
		return atom_2;
	}

	public String getColumn_ref_1() {
		return column_ref_1;
	}

	public String getColumn_ref1_1() {
		return column_ref1_1;
	}

	public String getColumn_ref1_2() {
		return column_ref1_2;
	}

	public String getColumn_ref2_1() {
		return column_ref2_1;
	}

	public String getColumn_ref2_2() {
		return column_ref2_2;
	}

	public String getScalar_exp4_1() {
		return scalar_exp4_1;
	}

	public String getScalar_exp4_2() {
		return scalar_exp4_2;
	}

	public String getScalar_exp7_1() {
		return scalar_exp7_1;
	}

	public String getScalar_exp7_2() {
		return scalar_exp7_2;
	}

	public String getScalar_exp_1() {
		return scalar_exp_1;
	}

	public String getScalar_exp8_1() {
		return scalar_exp8_1;
	}

	public String getScalar_exp8_2() {
		return scalar_exp8_2;
	}


	public String getScalar_exp6_1() {
		return scalar_exp6_1;
	}

	public String getScalar_exp6_2() {
		return scalar_exp6_2;
	}

	public String getScalar_exp6_3() {
		return scalar_exp6_3;
	}

	public String getScalar_exp9_1() {
		return scalar_exp9_1;
	}

	public String getScalar_exp9_2() {
		return scalar_exp9_2;
	}

	public String getScalar_exp9_3() {
		return scalar_exp9_3;
	}

	public String getScalar_exp9_4() {
		return scalar_exp9_4;
	}


	public String getFunc_exp_1() {
		return func_exp_1;
	}

	public String getFUNC_1() {
		return FUNC_1;
	}

	public String getFUNC_2() {
		return FUNC_2;
	}

	public String getFUNC_3() {
		return FUNC_3;
	}

	public String getFUNC_4() {
		return FUNC_4;
	}

	public String getFUNC_5() {
		return FUNC_5;
	}

	public String getTable_exp_1() {
		return table_exp_1;
	}

	public String getFrom_clause_1() {
		return from_clause_1;
	}

	public String getTable_ref_one_1() {
		return table_ref_one_1;
	}

	public String getTable_ref_commalist_1() {
		return table_ref_commalist_1;
	}

	public String getTable_ref_commalist1_1() {
		return table_ref_commalist1_1;
	}

	public String getTable_ref_commalist1_2() {
		return table_ref_commalist1_2;
	}

	public String getOpt_where_clause_1() {
		return opt_where_clause_1;
	}

	public String getOpt_where_clause_2() {
		return opt_where_clause_2;
	}

	public String getOpt_group_by_clause_1() {
		return opt_group_by_clause_1;
	}

	public String getOpt_group_by_clause_2() {
		return opt_group_by_clause_2;
	}

	public String getColumn_ref_commalist_1() {
		return column_ref_commalist_1;
	}

	public String getColumn_ref_commalist1_1() {
		return column_ref_commalist1_1;
	}

	public String getColumn_ref_commalist1_2() {
		return column_ref_commalist1_2;
	}

	public String getOpt_having_clause_1() {
		return opt_having_clause_1;
	}

	public String getOpt_having_clause_2() {
		return opt_having_clause_2;
	}

	public String getCOMPARISON_1() {
		return COMPARISON_1;
	}

	public String getCOMPARISON_2() {
		return COMPARISON_2;
	}

	public String getCOMPARISON_3() {
		return COMPARISON_3;
	}

	public String getCOMPARISON_4() {
		return COMPARISON_4;
	}

	public String getCOMPARISON_5() {
		return COMPARISON_5;
	}

	public String getCOMPARISON_6() {
		return COMPARISON_6;
	}

	public String getSearch_condition_1() {
		return search_condition_1;
	}

	public String getSearch_condition13_1() {
		return search_condition13_1;
	}

	public String getSearch_condition13_2() {
		return search_condition13_2;
	}

	public String getSearch_condition14_1() {
		return search_condition14_1;
	}

	public String getSearch_condition14_2() {
		return search_condition14_2;
	}

	public String getSearch_condition11_1() {
		return search_condition11_1;
	}

	public String getSearch_condition12_1() {
		return search_condition12_1;
	}

	public String getSearch_condition12_2() {
		return search_condition12_2;
	}

	public String getSearch_condition15_1() {
		return search_condition15_1;
	}

	public String getSearch_condition15_2() {
		return search_condition15_2;
	}

	public String getSearch_condition9_1() {
		return search_condition9_1;
	}

	public String getSearch_condition1_1() {
		return search_condition1_1;
	}

	public String getSearch_condition1_2() {
		return search_condition1_2;
	}

	public String getSearch_condition1_3() {
		return search_condition1_3;
	}

	public String getSearch_condition1_4() {
		return search_condition1_4;
	}

	public String getSearch_condition1_5() {
		return search_condition1_5;
	}

	public String getSearch_condition1_6() {
		return search_condition1_6;
	}

	public String getSearch_condition2_1() {
		return search_condition2_1;
	}

	public String getSearch_condition3_1() {
		return search_condition3_1;
	}

	public String getSearch_condition4_1() {
		return search_condition4_1;
	}

	public String getSearch_condition5_1() {
		return search_condition5_1;
	}

	public String getSearch_condition5_2() {
		return search_condition5_2;
	}

	public String getSearch_condition6_1() {
		return search_condition6_1;
	}

	public String getSearch_condition6_2() {
		return search_condition6_2;
	}

	public String getSearch_condition7_1() {
		return search_condition7_1;
	}

	public String getSearch_condition7_2() {
		return search_condition7_2;
	}

	public String getSearch_condition7_3() {
		return search_condition7_3;
	}

	public String getSearch_condition8_1() {
		return search_condition8_1;
	}

	public String getSearch_condition8_2() {
		return search_condition8_2;
	}

	public String getOpt_escape_1() {
		return opt_escape_1;
	}

	public String getOpt_escape_2() {
		return opt_escape_2;
	}

	public String getAtom_commalist_1() {
		return atom_commalist_1;
	}

	public String getAtom_commalist1_1() {
		return atom_commalist1_1;
	}

	public String getAtom_commalist1_2() {
		return atom_commalist1_2;
	}

	public String getAny_all_some_1() {
		return any_all_some_1;
	}

	public String getAny_all_some_2() {
		return any_all_some_2;
	}

	public String getAny_all_some_3() {
		return any_all_some_3;
	}

	public String getSubquery_1() {
		return subquery_1;
	}

	public String getOpt_order_by_clause_1() {
		return opt_order_by_clause_1;
	}

	public String getOpt_order_by_clause_2() {
		return opt_order_by_clause_2;
	}

	public String getOrdering_spec_commalist_1() {
		return ordering_spec_commalist_1;
	}

	public String getOrdering_spec_commalist1_1() {
		return ordering_spec_commalist1_1;
	}

	public String getOrdering_spec_commalist1_2() {
		return ordering_spec_commalist1_2;
	}

	public String getOrdering_spec_1() {
		return ordering_spec_1;
	}

	public String getOpt_asc_desc_1() {
		return opt_asc_desc_1;
	}

	public String getOpt_asc_desc_2() {
		return opt_asc_desc_2;
	}

	public String getOpt_asc_desc_3() {
		return opt_asc_desc_3;
	}

}
