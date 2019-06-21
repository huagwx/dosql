<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.stone.dosql.struts.util.*" %> 
<%@ page import="java.util.*" %>
<%@ page import="com.stone.dosql.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<link href="<%=(String)request.getContextPath() %>/css.css" type="text/css" rel="stylesheet"/>
	  <title>dosql首页</title>
	</head>

<body>

<%	
   List dbList =new ArrayList();
   try{
   	String DBMS_URL=(String)request.getRealPath("/")+JspConst.dbsSetUrl;
	DbName dbName=new DbName(DBMS_URL);
    dbList=dbName.getDbList();
    }catch(Exception e){
    	response.sendRedirect("./err.jsp");
    }
    
    String selDbName="";
    try{
    	selDbName=(String)session.getAttribute("dbName");
    }catch(Exception e){
    	selDbName="";
    }
    String selectString="";
    try{
    	selectString=(String)session.getAttribute("selectString");
    }catch(Exception e){
    	selectString="";
    }

 %>
<table width="1000" border="0" cellpadding="0" cellspacing="0">
  <!--DWLayoutTable-->
  <tr>
    <td width="1000" height="87" valign="top">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" onclick='""'>
      <!--DWLayoutTable-->
      <tr>
        <td width="1000" height="33" align="center"><h1>基于解释方式的SQL数据查询翻译----分析器</h1></td>
      </tr>
	  <tr>
        <td width="1000" onclick='null'>
		<html:form action="/search">
			<b>数据库 :</b> <html:select  property="dbname" value="<%=selDbName %>">
					<%
					 int size=dbList.size();					 
					 for(int i=0;i<size;i++){
					 String str=(String)dbList.get(i);					 
					 %>
					 <html:option key="<%=str %>" value="<%=str %>"  />					 
					 <%
					 }
					 %>						
			        </html:select><html:errors property="dbname"/> &nbsp;&nbsp;&nbsp;&nbsp;
			         <a href='<%=(String)request.getContextPath() %>/dosql/ll1grammar.jsp' target='_parent'>SELECT--LL1文法</a>&nbsp;&nbsp;&nbsp;&nbsp;
			         <a href='<%=(String)request.getContextPath() %>/dosql/ll1forecasttable.jsp' target='_parent'>SELECT--LL1预测分析表</a>&nbsp;&nbsp;&nbsp;&nbsp;
			         <a href='<%=(String)request.getContextPath() %>/dosql/er_student.jsp' target='_parent'>数据库STUDENT的ER关系图</a><br/>
			       
		    <html:textarea rows="7" cols="120" property="selectString" value="<%=selectString %>"/><html:errors property="selectString"/><br/>
			<html:submit>提交</html:submit>&nbsp;&nbsp;<input type="reset" value='返回'/>
			
		</html:form>
		
        </td>
      </tr>
      <%
        try{
        JspNavigator navigator=(JspNavigator)session.getAttribute("navigator");
      	out.print("<tr><td width='1000' align='center'>"+navigator.getNavigatorString()+"</td></tr>");
        }catch(Exception e){
        
        
        }
      
       %>
    </table>   
    </td>
  </tr>
  <tr>
    <td height="481" valign="top">
    <table width="100%" height="419" border="0" cellpadding="0" cellspacing="0">
      <!--DWLayoutTable-->
      <tr>
        <td width="1000" height="450">
		     <iframe id="main" src="<%=(String)request.getContextPath() %>/dosql/show.jsp" name="main" scrolling="auto" width="98%" height="95%">                  
			  </iframe>
	    </td>
        </tr>
    </table>
    </td>
  </tr>
</table>
</body>
</html>
