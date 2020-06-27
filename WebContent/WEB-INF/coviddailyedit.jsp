<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>Edit Covid Daily</title>
	
		<link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<link href="static/css/common.css" rel="stylesheet">
		<script src="static/jquery/jquery.min.js"></script>
		<script src="static/bootstrap/js/bootstrap.min.js"></script>
	</head>
	<body>	
	<jsp:include page= "menu.jsp"/>	
		<jsp:include page = "messages.jsp"/>
		
		<div class="container border covid-form">
			<form action="EditCovidDailyServlet" method="post">
				<h2>Edit Covid Daily Data</h2>
				<input type="hidden" name="country_id" value="${cidToEdit}">
				<input type="hidden" name="country_date" value="${dateToEdit}">
				
				<div class="form-group">
					<label for="iddate">Date:</label>
					<input type="text" class="form-control" id="iddate" name="cdate" value="${cdate}" placeholder="YYYY-MM-DD">
				</div>
				
				<div class="form-group">
					<label for="idcountry">Country:</label>
					 <select class="form-control" id="idcountry" name="cid">
						<c:forEach var="country" items= "${countryList}">
							<option value="${country.getId()}" 
								<c:if test="${country.getId()==cid}">selected</c:if>
							>${country.getName()}</option>
						</c:forEach>
					</select>
				</div>
				
				<div class="form-group">
					<label for="idtests">Tests:</label>
					<input type="text" class="form-control" id="idtests" name="tests" value="${tests}">
				</div>
				
				<div class="form-group">
					<label for="idcases">Cases:</label>
					<input type="text" class="form-control" id="idcases" name="cases" value="${cases}">
				</div>
				
				<div class="form-group">
					<label for="idmortality">Mortality:</label>
					<input type="text" class="form-control" id="idmortality" name="mortality" value="${mortality}">
				</div>
				
				<div class="form-group">
					<label for="idrecovered">Recovered:</label>
					<input type="text" class="form-control" id="idrecovered" name="recovered" value="${recovered}">
				</div>

				<input type="submit" class="btn btn-primary" name="option" value="Save">
				<input type="submit" class="btn btn-secondary" name="option" value="Cancel"/>	
			</form>
		</div>
	</body>
</html>