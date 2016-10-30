<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" class="no-js">
<head>
	<meta http-equiv="Content-Type" content="text/html" charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="shortcut icon" href="<c:url value="/resources/img/asu.png"/>">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	<link rel="stylesheet" href="<c:url value="/resources/css/main.css" />"> <!-- Resource style -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script src="<c:url value="/resources/js/jquery.menu-aim.js" />"></script>
	<script src="<c:url value="/resources/js/main.js" />"></script>
	<title>Home</title>
	<link href="resources/css/jquery-ui.css" rel="stylesheet">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8/jquery.js"></script>
	<script src="https://code.jquery.com/ui/1.9.0/jquery-ui.min.js"></script>

	<!-- keyboard widget css & script (required) -->
	<link href="resources/css/keyboard.css" rel="stylesheet">
	<script src="resources/js/jquery.keyboard.js"></script>

	<!-- keyboard extensions (optional) -->
	<script src="resources/js/jquery.mousewheel.js"></script>
	<script>
		$(function(){
			$('#password').keyboard();
		});
	</script>
	<script>
			
		$(document).ready(function(){
		$('#submit').click(function(){
		var userEmail=$('#userEmail').val();	
		var filter = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
		if(!filter.test(userEmail))
		{
			$("#userEmail").focus();
	    	$("#errorBox").html("Please Enter a Valid Email Address");
			return false;
		}
		
		});
	});
	</script>

}
</head>
<body>
	<header class="cd-main-header">
		<!--<a href="#0" class="cd-logo"><img src="img/cd-logo.svg" alt="Logo"></a>-->
		<a href="/CSE545-SS" style="padding-left: 20px"><img src="<c:url value="/resources/img/Sparky.png"/>"
						 style="vertical-align:top;"
						 width="35px" height="65px" alt="Sparky"></a>
		<a href="/CSE545-SS"><img class="text-logo" src="<c:url value="/resources/img/Picture1.png"/>"
													style="padding: 7px 0 7px 0"
													width="200px" height="65px" alt="Devil's Vault"></a>

	</header> <!-- .cd-main-header -->
	<main class="cd-main-content">
		<div class="content-wrapper" id="login-container">
			<div class="col-md-12" id="login-page">
				<%-- <c:if test="${not empty verifyemail}"> --%>
					<c:if test="${not empty message}">
					<div class="alert alert-dismissible alert-success">
		  				<button type="button" class="close" data-dismiss="alert">&times;</button>
		  				<strong>${message}</strong>
					</div>
					</c:if>
					
					<form action="verifyotp" method="POST">
						<div class="form-group">
		      				<label for="otp" class="col-lg-2 control-label">Enter OTP</label>
		      				<div class="col-lg-10 form-margin">
		        				<input type="text" class="form-control" id="otp" name="otp" placeholder="OTP">
		      				</div>
		      				<div class="form-group">
						      <div class="col-lg-10 col-lg-offset-2 form-margin">
						      <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
						        <button type="submit" class="btn btn-primary">Submit</button>
						      </div>
						    </div>
		    			</div>
					</form>
										
			</div>
	
		</div> <!-- .content-wrapper -->
	
	</main> <!-- .cd-main-content -->
</body>
</html>
