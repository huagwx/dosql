<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.stone.dosql.struts.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="<%=(String)request.getContextPath() %>/css.css" type="text/css" rel="stylesheet"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>dosql结果信息显示</title>
</head>
<body>
 <%
    
    try{
     JspResult jspResult=(JspResult)session.getAttribute("jspResult");
     String curSelectType=jspResult.getJspResultShowType();
   
%>
<table width="100%" height="100%" >

 	<tr>
 		<td width="303" align="left" valign="top">
 		<%
 		   out.print(jspResult.getJspSearchTime().getSearchTimeShow());
 		 %>
 		</td>
 		<td width="644" align="left" valign="top">
 		<%
 		   if(curSelectType.equals(JspConst.ShowResulstSet)){
 		    out.print(jspResult.getJspSelectResult().getResultSetShow());
 		   }else if(curSelectType.equals(JspConst.ShowTokens)){
 		   out.print(jspResult.getJspTokensFrame().getTokensShow());
 		   }else if(curSelectType.equals(JspConst.ShowMiddleCodes)){
 		   out.print(jspResult.getJspMiddleCode().getMiddlecodeShow());
 		   }
 		 %>
 		</td>
 	</tr>
 </table>
 <%
     }catch(Exception e){
    	
    }
  
 %>
</body>
</html>