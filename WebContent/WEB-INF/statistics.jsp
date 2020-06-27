<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
   
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>Statistics</title>
		<link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<link href="static/css/common.css" rel="stylesheet">
		<script src="static/jquery/jquery.min.js"></script>
		<script src="static/bootstrap/js/bootstrap.min.js"></script>
	</head>
	<body>
		<jsp:include page= "menu.jsp"/>			
		<jsp:include page = "messages.jsp"/>
			<div class="panel panel-default">
			    <div class="panel-heading">
			    	<span class="panel-title" >Global Statistics</span> 
			    </div>
				<!-- Global Statistics Table-->
				<div class="panel-body">
					<table class="table table-striped">
						<thead class="thead-ligth">
						    <tr>
						        <th>Total Tests</th>
						        <th>Total Cases</th>
						        <th>Total Mortality</th>
						        <th>Total Recovered</th>
						    </tr>  		       
					    </thead>
					        <tr>  
					            <td>${globalStatistics.getTotalTests()}</td>
					            <td>${globalStatistics.getTotalCases()}</td>
					            <td>${globalStatistics.getTotalMortality()}</td>
					            <td>${globalStatistics.getTotalRecovered()}</td>
					        </tr>
					</table>
				</div>
			</div>

		<form action="StatisticsServlet" method="post">
			<div class="panel panel-default">
			    <div class="panel-heading">
			    	<span class="panel-title" >Country Statistics</span> 
			    	<div class="float-right panel-actions">
				    	<input type="search" name="search_text" value="${search_text}" placeholder="Search" >
						<input type="submit" class="btn btn-primary ml-auto" value="Search" name="search_btn">
					</div>
			    </div>
				<!-- CountryStatistics Table-->
				<div class="panel-body">
					<table class="table table-striped">
						<thead class="thead-ligth">
						    <tr>
						    	<th>Country</th>
						        <th>Total Tests</th>
						        <th>Total Cases</th>
						        <th>Total Mortality</th>
						        <th>Total Recovered</th>
						    </tr>  		       
					    </thead>
						<c:forEach var="country" items= "${countryList}">
					        <tr>  
					            <td>${country.getName()}</td>
					            <td>${country.getTotalTests()}</td>
					            <td>${country.getTotalCases()}</td>
					            <td>${country.getTotalMortality()}</td>
								<td>${country.getTotalRecovered()}</td>
					        </tr>
						</c:forEach>		
					</table>
				</div>

			</div>
		</form>
	</body>
</html> 
