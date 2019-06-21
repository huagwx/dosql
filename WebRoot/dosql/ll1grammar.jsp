<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="../css.css" type="text/css" rel="stylesheet"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>SELECT---LL1文法</title>
</head>
<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<div align='center'>
<p><a href='<%=(String)request.getContextPath() %>/dosql/search.jsp' target='_parent'><h2>返回SELECT查询首页</h2></a></p>
<table border='1' align="center">
<tr><td align='center'><h1>SELECT---LL1文法</h1><u>query_spec<small>&nbsp;&nbsp;<b>为文法的开始符号</b></small></u></td></tr>
<tr><td>&nbsp;&nbsp;  query_spec&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; SELECT&nbsp;&nbsp; &nbsp;  select_result&nbsp;&nbsp; &nbsp;  table_exp&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  select_result&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; opt_all_distinct&nbsp;&nbsp; &nbsp;  scalar_exp_commalist&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  opt_all_distinct&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ALL&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  opt_all_distinct&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; DISTINCT&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  opt_all_distinct&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ST_NULL_S&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  parameter_ref&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ST_NULL_S&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  parameter_ref&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; IDENT&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  parameter_ref&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; AS&nbsp;&nbsp; &nbsp;  IDENT&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  all_table_allcolumn&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; *&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  scalar_exp_one&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; scalar_exp&nbsp;&nbsp; &nbsp;  parameter_ref&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  scalar_exp_one&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; all_table_allcolumn&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  scalar_exp_commalist&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; scalar_exp_one&nbsp;&nbsp; &nbsp;  scalar_exp_commalist1&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  scalar_exp_commalist1&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ,&nbsp;&nbsp; &nbsp;  scalar_exp_one&nbsp;&nbsp; &nbsp;  scalar_exp_commalist1&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  scalar_exp_commalist1&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ST_NULL_S&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  OPERATOR&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; +&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  OPERATOR&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; -&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  OPERATOR1&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; *&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  OPERATOR1&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; /&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  atom&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; '&nbsp;&nbsp; &nbsp;  STRINGCONST&nbsp;&nbsp; &nbsp;  '&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  atom&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; "&nbsp;&nbsp; &nbsp;  STRINGCONST&nbsp;&nbsp; &nbsp;  "&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  atom&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; DIGITALCONST&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  column_ref&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; IDENT&nbsp;&nbsp; &nbsp;  column_ref1&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  column_ref1&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ST_NULL_S&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  column_ref1&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; .&nbsp;&nbsp; &nbsp;  column_ref2&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  column_ref2&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; *&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  column_ref2&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; IDENT&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  scalar_exp4&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; *&nbsp;&nbsp; &nbsp;  )&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  scalar_exp4&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; column_ref&nbsp;&nbsp; &nbsp;  )&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  scalar_exp7&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ST_NULL_S&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  scalar_exp7&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; OPERATOR&nbsp;&nbsp; &nbsp;  scalar_exp5&nbsp;&nbsp; &nbsp;  scalar_exp7&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  scalar_exp&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; scalar_exp5&nbsp;&nbsp; &nbsp;  scalar_exp7&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  scalar_exp8&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; OPERATOR1&nbsp;&nbsp; &nbsp;  scalar_exp6&nbsp;&nbsp; &nbsp;  scalar_exp8&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  scalar_exp8&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ST_NULL_S&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  scalar_exp5&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; scalar_exp6&nbsp;&nbsp; &nbsp;  scalar_exp8&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  scalar_exp6&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; +&nbsp;&nbsp; &nbsp;  scalar_exp9&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  scalar_exp6&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; -&nbsp;&nbsp; &nbsp;  scalar_exp9&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  scalar_exp6&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; scalar_exp9&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  scalar_exp9&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; (&nbsp;&nbsp; &nbsp;  scalar_exp&nbsp;&nbsp; &nbsp;  )&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  scalar_exp9&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; atom&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  scalar_exp9&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; column_ref&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  scalar_exp9&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; FUNC&nbsp;&nbsp; &nbsp;  (&nbsp;&nbsp; &nbsp;  func_exp&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  func_exp&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; opt_all_distinct&nbsp;&nbsp; &nbsp;  scalar_exp4&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  FUNC&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; COUNT&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  FUNC&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; SUM&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  FUNC&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; AVG&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  FUNC&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; MAX&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  FUNC&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; MIN&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  table_exp&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; from_clause&nbsp;&nbsp; &nbsp;  opt_where_clause&nbsp;&nbsp; &nbsp;  opt_group_by_clause&nbsp;&nbsp; &nbsp;  opt_having_clause&nbsp;&nbsp; &nbsp;  opt_order_by_clause&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  from_clause&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; FROM&nbsp;&nbsp; &nbsp;  table_ref_commalist&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  table_ref_one&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; IDENT&nbsp;&nbsp; &nbsp;  parameter_ref&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  table_ref_commalist&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; table_ref_one&nbsp;&nbsp; &nbsp;  table_ref_commalist1&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  table_ref_commalist1&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ST_NULL_S&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  table_ref_commalist1&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ,&nbsp;&nbsp; &nbsp;  table_ref_one&nbsp;&nbsp; &nbsp;  table_ref_commalist1&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  opt_where_clause&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; WHERE&nbsp;&nbsp; &nbsp;  search_condition&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  opt_where_clause&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ST_NULL_S&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  opt_group_by_clause&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; GROUP&nbsp;&nbsp; &nbsp;  BY&nbsp;&nbsp; &nbsp;  column_ref_commalist&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  opt_group_by_clause&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ST_NULL_S&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  column_ref_commalist&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; column_ref&nbsp;&nbsp; &nbsp;  column_ref_commalist1&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  column_ref_commalist1&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ST_NULL_S&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  column_ref_commalist1&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ,&nbsp;&nbsp; &nbsp;  column_ref&nbsp;&nbsp; &nbsp;  column_ref_commalist1&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  opt_having_clause&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; HAVING&nbsp;&nbsp; &nbsp;  search_condition&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  opt_having_clause&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ST_NULL_S&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  COMPARISON&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; =&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  COMPARISON&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; <>&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  COMPARISON&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; <&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  COMPARISON&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; >&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  COMPARISON&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; <=&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  COMPARISON&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; >=&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  search_condition&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; search_condition11&nbsp;&nbsp; &nbsp;  search_condition13&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  search_condition13&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; OR&nbsp;&nbsp; &nbsp;  search_condition11&nbsp;&nbsp; &nbsp;  search_condition13&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  search_condition13&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ST_NULL_S&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  search_condition14&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; AND&nbsp;&nbsp; &nbsp;  search_condition12&nbsp;&nbsp; &nbsp;  search_condition14&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  search_condition14&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ST_NULL_S&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  search_condition11&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; search_condition12&nbsp;&nbsp; &nbsp;  search_condition14&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  search_condition12&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; NOT&nbsp;&nbsp; &nbsp;  search_condition15&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  search_condition12&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; search_condition15&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  search_condition15&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; scalar_exp&nbsp;&nbsp; &nbsp;  search_condition1&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  search_condition15&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; search_condition9&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  search_condition9&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; EXISTS&nbsp;&nbsp; &nbsp;  (&nbsp;&nbsp; &nbsp;  subquery&nbsp;&nbsp; &nbsp;  )&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  search_condition1&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; COMPARISON&nbsp;&nbsp; &nbsp;  search_condition8&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  search_condition1&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; NOT&nbsp;&nbsp; &nbsp;  search_condition7&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  search_condition1&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; search_condition2&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  search_condition1&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; search_condition3&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  search_condition1&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; search_condition4&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  search_condition1&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; IS&nbsp;&nbsp; &nbsp;  search_condition6&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  search_condition2&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; BETWEEN&nbsp;&nbsp; &nbsp;  scalar_exp&nbsp;&nbsp; &nbsp;  AND&nbsp;&nbsp; &nbsp;  scalar_exp&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  search_condition3&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; LIKE&nbsp;&nbsp; &nbsp;  atom&nbsp;&nbsp; &nbsp;  opt_escape&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  search_condition4&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; IN&nbsp;&nbsp; &nbsp;  (&nbsp;&nbsp; &nbsp;  search_condition5&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  search_condition5&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; subquery&nbsp;&nbsp; &nbsp;  )&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  search_condition5&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; atom_commalist&nbsp;&nbsp; &nbsp;  )&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  search_condition6&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; NOT&nbsp;&nbsp; &nbsp;  NULL&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  search_condition6&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; NULL&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  search_condition7&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; search_condition2&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  search_condition7&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; search_condition3&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  search_condition7&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; search_condition4&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  search_condition8&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; scalar_exp&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  search_condition8&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; any_all_some&nbsp;&nbsp; &nbsp;  (&nbsp;&nbsp; &nbsp;  subquery&nbsp;&nbsp; &nbsp;  )&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  opt_escape&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ESCAPE&nbsp;&nbsp; &nbsp;  atom&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  opt_escape&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ST_NULL_S&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  atom_commalist&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; atom&nbsp;&nbsp; &nbsp;  atom_commalist1&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  atom_commalist1&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ,&nbsp;&nbsp; &nbsp;  atom&nbsp;&nbsp; &nbsp;  atom_commalist1&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  atom_commalist1&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ST_NULL_S&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  any_all_some&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ANY&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  any_all_some&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ALL&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  any_all_some&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; SOME&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  subquery&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; SELECT&nbsp;&nbsp; &nbsp;  select_result&nbsp;&nbsp; &nbsp;  table_exp&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  opt_order_by_clause&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ORDER&nbsp;&nbsp; &nbsp;  BY&nbsp;&nbsp; &nbsp;  ordering_spec_commalist&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  opt_order_by_clause&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ST_NULL_S&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  ordering_spec_commalist&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ordering_spec&nbsp;&nbsp; &nbsp;  ordering_spec_commalist1&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  ordering_spec_commalist1&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ST_NULL_S&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  ordering_spec_commalist1&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ,&nbsp;&nbsp; &nbsp;  ordering_spec&nbsp;&nbsp; &nbsp;  ordering_spec_commalist1&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  ordering_spec&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; column_ref&nbsp;&nbsp; &nbsp;  opt_asc_desc&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  opt_asc_desc&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ASC&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  opt_asc_desc&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; DESC&nbsp;&nbsp; &nbsp;  </td></tr>
<tr><td>&nbsp;&nbsp;  opt_asc_desc&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ST_NULL_S&nbsp;&nbsp; &nbsp;  </td></tr>

</table>
</div>
</body>
</html>