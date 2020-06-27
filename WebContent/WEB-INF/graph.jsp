<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

   
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>Graph</title>
		<link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<link href="static/css/common.css" rel="stylesheet">
		<script src="static/jquery/jquery.min.js"></script>
		<script src="static/bootstrap/js/bootstrap.min.js"></script>
		<script src="static/chartjs/chart.min.js"></script>
		

<script>
var days = new Array();
var totalMortality = new Array();
var totalCases = new Array();
var totalRecovered = new Array();
<c:forEach items="${covidDailyList}" var="covidDaily">
	days.push('${covidDaily.getCdate()}');
	totalMortality.push('${covidDaily.getMortality()}');
	totalCases.push('${covidDaily.getCases()}');
	totalRecovered.push('${covidDaily.getRecovered()}');
</c:forEach>
		
		var config = {
			type: 'line',
			data: {
				labels: days,
				datasets: [
					{
						label: 'Total Mortality',
						fill: false,
						backgroundColor: 'rgb(255, 99, 132)',
						borderColor: 'rgb(255, 99, 132)',
						data: totalMortality,
					}, 
					{
						label: 'Total Cases',
						fill: false,
						backgroundColor: 'rgb(54, 162, 235)',
						borderColor:  'rgb(54, 162, 235)',
						data: totalCases,
					},
					{
						label: 'Total Recovered',
						fill: false,
						backgroundColor: 'rgb(75, 192, 100)',
						borderColor: 'rgb(75, 192, 100)',
						data: totalRecovered,
					},
				]
			},
			options: {
				responsive: true,
				title: {
					display: true,
					text: ''
				},
				tooltips: {
					mode: 'index',
					intersect: false,
				},
				hover: {
					mode: 'nearest',
					intersect: true
				},
				scales: {
					xAxes: [{
						display: true,
						scaleLabel: {
							display: true,
							labelString: 'Days'
						}
					}],
					yAxes: [{
						display: true,
						scaleLabel: {
							display: true,
							labelString: 'Total Mortality / Total Cases / Total Recovered'
						}
					}]
				}
			}
		};

		window.onload = function() {
			var ctx = document.getElementById('canvas').getContext('2d');
			window.myLine = new Chart(ctx, config);
		};

		
</script>
		
		
	</head>
	<body>
		<jsp:include page= "menu.jsp"/>			
		<jsp:include page = "messages.jsp"/>
		<form action="GraphServlet" method="post">
			<div class="panel panel-default">
			    <div class="panel-heading">
			    	<span class="panel-title" >Daily Graph by Country</span> 
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
					</div>
			    </div>
				<div class="panel-body" style="width:75%;">
					<canvas id="canvas"></canvas>
				</div>
			</div>
		</form>
	</body>
</html>