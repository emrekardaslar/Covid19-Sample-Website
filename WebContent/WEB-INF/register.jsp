<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Register</title>
	
	<link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<script src="static/jquery/jquery.min.js"></script>
	<script src="static/bootstrap/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="static/css/login.css" />
</head>
<body>

<jsp:include page = "messages.jsp"/>

<div class="login-form">
	<form action="RegisterServlet" method="post">
		<img src="static/img/corona.png" class="avatar">
		<h2>Covid System</h2>
		<h2>Register</h2>
		<p> Username: <input type="text" name="user" placeholder="Username"></p>
		<p> Password: <input type="password" name="pwd1" placeholder="Password"></p>
		<p> Password (Again): <input type="password" name="pwd2" placeholder="Password (Again)"></p>

		<input type="submit" value="Register">
		<br><br>
		<a href="MainServlet">Login</a>
	</form>
</div>
</body>
</html>