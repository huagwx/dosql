<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.stone.dosql.codegenerator.impl.*" %>

<html>
<body>
<table>
<tr>
<td>时间</td>

<td>
 <% out.print((String)request.getAttribute("result")); %>

</td>
</tr>
</table>

</body>

</html>