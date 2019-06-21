<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.stone.dosql.util.*" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>基于解释方式的SQL数据查询翻译</title>
</head>

<body>

<%
	
	List dbList=new ArrayList();
	//String DBMS_URL=request.getRealPath("/");
	//DBMS_URL=DBMS_URL.substring(0,DBMS_URL.length()-1)+"\\WebRoot\\stone\\dosql\\date\\";
	//DBMS_URL.replaceAll("\","//");
	//DBMS_URL.replaceAll("\")
//	String DBMS_URL="stone//dosql//data//";
//	System.out.println(DBMS_URL);
//    DbName dbName=new DbName(DBMS_URL);
//	dbList=dbName.getDbList();
		
//	System.out.println("dbSize:"+dbList.size());
	//session.setAttribute("dbList",dbList);
	
	
	
	
	
 %>

<table width="1000" border="0" cellpadding="0" cellspacing="0">
  <!--DWLayoutTable-->
  <tr>
    <td width="1000" height="87" valign="top">
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
      <!--DWLayoutTable-->
      <tr>
        <td width="1000" height="33" align="center"><h1>基于解释方式的SQL数据查询翻译----分析器</h1></td>
      </tr>
	  <tr>
        <td width="1000" >
        <form name="form1" target="main" action="<%=(String)request.getContextPath() %>/search.do">
        <textarea name="inputselect" rows="7" cols="120"></textarea>
         数据库:<select size="1" name="dbName">

        	 </select>
        <p>
        <input type="checkbox" name="selectpart" value="token">词法分析结果&nbsp;&nbsp;
        <input type="checkbox" name="selectpart" value="syntax">语法分析结果&nbsp;&nbsp;
        <input type="checkbox" name="selectpart" value="optsyntax">优化语法树结果&nbsp;&nbsp;
        <input type="checkbox" name="selectpart" value="middlecode">中间代码结果&nbsp;&nbsp;
        <input type="submit" value="查询" />&nbsp;&nbsp;<input type="reset" value="重置" />
        </form></td>
      </tr>
    </table>   
     </td>
  </tr>
  <tr>
    <td height="481" valign="top">
    <table width="100%" height="419" border="0" cellpadding="0" cellspacing="0">
      <!--DWLayoutTable-->
      <tr>
        <td width="1000" height="450"><iframe id="main" src="result.jsp" name="main" scrolling="auto" width="98%" height="95%"></iframe></td>
        </tr>
    </table>
    </td>
  </tr>
</table>
</body>
</html>
