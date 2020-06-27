<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
	<title>Login</title>
	
	<link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="static/css/login.css" />
	<script src="static/jquery/jquery.min.js"></script>
	<script src="static/bootstrap/js/bootstrap.min.js"></script>

</head>
<body>


<jsp:include page = "messages.jsp"/>

	<div class="login-form">
		<form method="POST" action="LoginServlet">
			<img src="static/img/corona.png" class="avatar">
			<h2>Covid System</h2>
			<h2>Login</h2>
			<p>Username: <input type="text" name="user"  placeholder="Username"></p>
			<p>Password: <input type="password" name="pwd" placeholder="Password"></p>
			<br>
			<input type="submit" class="btn" value="Login">
			<br><br><br>
			<a href="RegisterServlet">Register</a>
		</form>
	</div>
</body>
</html>