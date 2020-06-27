<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
   
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>Users</title>
		<link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<link href="static/css/common.css" rel="stylesheet">
		<script src="static/jquery/jquery.min.js"></script>
		<script src="static/bootstrap/js/bootstrap.min.js"></script>
	</head>
	<body>
		<jsp:include page= "menu.jsp"/>			
		<jsp:include page = "messages.jsp"/>
		<form action="UserServlet" method="post">
			<div class="panel panel-default">
			    <div class="panel-heading">
			    	<span class="panel-title" >Users</span> 
			    	<div class="float-right panel-actions">
				    	<input type="search" name="search_text" value="${search_text}" placeholder="Search" >
						<input type="submit" class="btn btn-primary ml-auto" value="Search" name="search_btn">
						<a class="btn btn-success " href="CreateUserServlet">Create New User</a>
					</div>
			    </div>
				<div class="panel-body">
					<table class="table table-striped">
						<thead class="thead-ligth">
						    <tr>
						        <th>Name</th>
						       <!-- <th>Password</th>	 --> 
						        <th>Is Admin</th>
						        <th>Operations</th>
						    </tr>  		       
					    </thead>
						<c:forEach var="user" items= "${userList}">
						        <tr>  
						            <td> ${user.getName()} </td>
						          <!--<td> ${user.getPwd()} </td>  -->  
						            <td> 
										<c:choose>
											<c:when test="${user.getIsAdmin()==1}">
											Yes
											</c:when>
											<c:otherwise>
											No
											</c:otherwise>
										</c:choose>
									</td>
						            
						            <td>
						            	<a class="btn btn-danger " href="DeleteUserServlet?name=${user.getName()}">Delete</a>	             
										<a class="btn btn-primary " href="EditUserServlet?name=${user.getName()}">Edit</a>
									</td>				                        
						        </tr>
						</c:forEach>		
					</table>
				</div>
			</div>
		</form>
	</body>
</html>