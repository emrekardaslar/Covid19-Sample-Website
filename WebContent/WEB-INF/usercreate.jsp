<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Create User</title>
	
	<link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="static/css/common.css" rel="stylesheet">
	<script src="static/jquery/jquery.min.js"></script>
	<script src="static/bootstrap/js/bootstrap.min.js"></script>
</head>
	<body>
		<jsp:include page= "menu.jsp"/>	
		<jsp:include page = "messages.jsp"/>
		
		<div class="container border covid-form">
			<form action="CreateUserServlet" method="post">
				<h2>Create User</h2>
				<div class="form-group">
				 <label for="iduser">Username:</label>
				 <input type="text" class="form-control" id="iduser" name="user" value="${user}" placeholder="Username">
				</div>
				
				<div class="form-group">
				<label for="idpwd">Password:</label>
				<input type="password" class="form-control" id="idpwd" name="pwd1" placeholder="Password">
				</div>
				
				
				<div class="form-group">
				<label for="idpwd2">Password (Again):</label>
				<input type="password" class="form-control" id="idpwd2" name="pwd2" placeholder="Password (Again)">
				</div>
				
				<div class="form-group form-check">
				    <input type="checkbox" class="form-check-input" id="idis_admin" name="is_admin" <c:if test="${is_admin==1}">checked=checked</c:if> value="1">
				    <label class="form-check-label" for="idis_admin">Is Administrator?</label>
				</div>
				
				<input type="submit" class="btn btn-success" name="option" value="Create">
				<input type="submit" class="btn btn-secondary" name="option" value="Cancel"/>	
			</form>
		</div>
	</body>
</html>