<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<nav class="navbar navbar-default navbar-dark bg-dark ">
  <a class="navbar-brand" href="MainServlet">Home</a>
  
  
  <a class="navbar-brand" href="CovidDailyServlet">Covid Daily</a>
  <a class="navbar-brand" href="StatisticsServlet">Statistics</a>
  <a class="navbar-brand" href="GraphServlet">Graph</a>
<%
if(session.getAttribute("isAdmin") != null){
    boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
    if (isAdmin) {
%>
  <a class="navbar-brand" href="UserServlet">Users</a>
  <a class="navbar-brand" href="DBMServlet">Database Management</a>
<%
	}
}
%>  

<p class="navbar-brand mr-auto"></p>
 
<form action="LogoutServlet" method="post">
 <ul class="navbar-nav ml-auto">
    <li class="nav-item active">
    	<button class="btn btn-primary mr-auto" type="submit" value="Logout">Logout</button>
    </li>
 </ul>
 </form>
</nav>