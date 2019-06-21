<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>出错信息提示</title>
</head>
<body>
  <%
  try{
    String err=(String)request.getAttribute("err");
    //out.print(err);
     err= err.replaceAll("'","单引号");
     err=err.replaceAll("\"","双引号");

   %>
   <script language="javascript">
     alert('<%=err %>')
     window.history.back(-1);
    </script>
    <%
    }catch(Exception e){
    	out.print("出错");
    }
     %>
</body>
</html>