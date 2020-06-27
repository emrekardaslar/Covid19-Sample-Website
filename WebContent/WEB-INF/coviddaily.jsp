<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
if(session.getAttribute("isAdmin") != null){
    boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
%>
  
   
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>Covid Daily</title>
		<link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<link href="static/css/common.css" rel="stylesheet">
		<script src="static/jquery/jquery.min.js"></script>
		<script src="static/bootstrap/js/bootstrap.min.js"></script>
	</head>
	<body>
		<jsp:include page= "menu.jsp"/>			
		<jsp:include page = "messages.jsp"/>
		<form action="CovidDailyServlet" method="post">
			<div class="panel panel-default">
			    <div class="panel-heading">
			    	<span class="panel-title" >Covid Daily</span> 
			    	<div class="float-right panel-actions">
						<input type="search" name="search_date1" style="width: 240px;" value="${search_date1}" placeholder="Start Date (YYYY-MM-DD)" >
				    	<input type="search" name="search_date2" style="width: 240px;" value="${search_date2}" placeholder="End Date (YYYY-MM-DD)" >
				    	
					    <select name="cid">
							<c:forEach var="country" items= "${countryList}">
								<option value="${country.getId()}" 
									<c:if test="${country.getId()==cid}">selected</c:if>
								>${country.getName()}</option>
		
							</c:forEach>
						</select>
						<input type="submit" class="btn btn-primary ml-auto" value="Search" name="search_btn">
<%
	if (isAdmin) {
%>  
						<a class="btn btn-success " href="CreateCovidDailyServlet">Create New Record</a>
<%
	}
%>  
					</div>
			    </div>
				<div class="panel-body">
					<table class="table table-striped">
						<thead class="thead-ligth">
						    <tr>
						        <th>Date</th>
						        <th>Country</th>
						        <th>Tests</th>
						        <th>Cases</th>
						        <th>Mortality</th>
						        <th>Recovered</th>
						    </tr>  		       
					    </thead>
						<c:forEach var="covidDaily" items= "${covidDailyList}">
						        <tr>  
						            <td>${covidDaily.getCdate()}</td>
						            <td>${covidDaily.getCname()}</td>
						            <td>${covidDaily.getTests()}</td>
						            <td>${covidDaily.getCases()}</td>
						            <td>${covidDaily.getMortality()}</td>
						            <td>${covidDaily.getRecovered()}</td>
						            
						            <td>
<%
	if (isAdmin) {
%>  
						            	<a class="btn btn-danger " href="DeleteCovidDailyServlet?cid=${covidDaily.getCid()}&cdate=${covidDaily.getCdate()}">Delete</a>	             
										<a class="btn btn-primary " href="EditCovidDailyServlet?cid=${covidDaily.getCid()}&cdate=${covidDaily.getCdate()}">Edit</a>
<%
	}
%> 
									</td>				                        
						        </tr>
						</c:forEach>		
					</table>
				</div>
			</div>
		</form>
	</body>
</html>

<%
}
%>  
