<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Main Page</title>


	<link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	
	<script src="static/jquery/jquery.min.js"></script>
	<script src="static/bootstrap/js/bootstrap.min.js"></script>
	
</head>
	<body>
	<%
	//allow access only if session exists
	String user = null;
	
	if(session.getAttribute("user") == null) {
		response.sendRedirect("LoginServlet");
	}
	else {
		user = (String) session.getAttribute("user");
	}
	%>
	
	<jsp:include page= "menu.jsp"/>
	<br>
	<h3>Hi <%=user %>, welcome to Covid19Dashboard.</h3>
	
	
	</body>
</html>


