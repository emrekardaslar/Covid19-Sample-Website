<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script>
	function AutoHideMessages() {
		$(".alert").delay(5000).slideUp(200, function() {
			$(this).remove();
		});		
	}
	
	$(document).ready(function() {        
		AutoHideMessages();
	});
</script>
<%
if(session.getAttribute("error")!=null) {
%>
	<div class="alert alert-danger alert-dismissable">
		<%= session.getAttribute("error") %>
		<button type="button" class="close" data-dismiss="alert" aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
	</div>

<%
session.setAttribute("error",null);
}
%>

<%
if(session.getAttribute("success")!=null) {
%>


<div class="alert alert-success alert-dismissable">
	<%= session.getAttribute("success") %>
	<button type="button" class="close" data-dismiss="alert" aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
</div>

<%
session.setAttribute("success",null);
}
%>
