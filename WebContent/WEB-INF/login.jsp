<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" class="no-js">
<head>
	<meta http-equiv="Content-Type" content="text/html" charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="shortcut icon" href="<c:url value="/resources/img/asu.png"/>">
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	<link rel="stylesheet" href="<c:url value="/resources/css/main.css" />"> <!-- Resource style -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script src="<c:url value="/resources/js/jquery.menu-aim.js" />"></script>
	<script src="<c:url value="/resources/js/main.js" />"></script>
	<title>Home</title>
	<link href="resources/css/jquery-ui.css" rel="stylesheet">
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8/jquery.js"></script>
	<script src="http://code.jquery.com/ui/1.9.0/jquery-ui.min.js"></script>

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
</head>
<body onload='document.loginForm.username.focus();'>
	<header class="cd-main-header">
		<!--<a href="#0" class="cd-logo"><img src="img/cd-logo.svg" alt="Logo"></a>-->
		<a href="/cs445-ss" style="padding-left: 20px"><img src="<c:url value="/resources/img/Sparky.png"/>"
						 style="vertical-align:top;"
						 width="35px" height="65px" alt="Sparky"></a>
		<a href="/cs445-ss"><img class="text-logo" src="<c:url value="/resources/img/Picture1.png"/>"
													style="padding: 7px 0 7px 0"
													width="200px" height="65px" alt="Devil's Vault"></a>

	</header> <!-- .cd-main-header -->
	<main class="cd-main-content">
		<div class="content-wrapper" id="login-container">
			<div class="col-md-12" id="page-content">
				<h1>Login Page</h1>
				
						<div id="login-box">

											
							<c:if test="${not empty error}">
								<div class="error">${error}</div>
							</c:if>
							<c:if test="${not empty msg}">
								<div class="msg">${msg}</div>
							</c:if>
					
							<form name='loginForm'
								action="login" method='POST'>
					
								<table>
								
									
									<input path="username" type="email" name="username" value=""  class="email" required />
									  
									<input id = "password" path="password" type="password" name="password" value="*****" required/>
									<br>
									<br>
									<input type="radio" name="usertype" value="External User" required>External User<br>
									<input type="radio" name="usertype" value="Internal User" required>Internal User<br>
				
				    				
									
									
									<br>
									<div class="g-recaptcha" data-sitekey="6LcMeggUAAAAAPjZlkFO3kTfHhSqJ-qo3nQivY2S"></div>
									
									
									<tr>
										<td><input type="submit" value="Login"  class = "btn"/></td>
									</tr>
									
									<br>
									<br>
									
								</table>
					
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
					
							</form>
							<td><input type="submit" value="Sign In"  class = "btn" onclick="location.href='register.html';"/></td>
				
							<script src='https://www.google.com/recaptcha/api.js'></script>
							<p>Forgot your password? <input type = "submit" class = "myButton" value = "Click Here!" onclick="location.href='forgot.html';"></p>
				
						</div>
			</div>
							
					
		
		</div> <!-- .content-wrapper -->
	
	</main> <!-- .cd-main-content -->
</body>
</html>