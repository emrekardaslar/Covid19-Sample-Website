<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Database Management</title>
	
	<link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<script src="static/jquery/jquery.min.js"></script>
	<script src="static/bootstrap/js/bootstrap.min.js"></script>
</head>
	<body>
		<jsp:include page= "menu.jsp"/>	
		<jsp:include page = "messages.jsp"/>
		
		<form action="DBMServlet" method="post">
			<h2>Database Management</h2>
			<input type="submit" class="btn btn-success" name="option" value="Create Database">
			<input type="submit" class="btn btn-danger" name="option" value="Drop Database"/>	
			<input type="submit" class="btn btn-danger" name="option" value="Reset Database"/>	
		</form>
	</body>
</html>