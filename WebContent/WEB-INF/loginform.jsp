<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" class="no-js">
<head>
	<script src="https://www.google.com/recaptcha/api.js"></script>
	<meta http-equiv="Content-Type" content="text/html" charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="shortcut icon" href="<c:url value="/resources/img/asu.png"/>">
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	<link rel="stylesheet" href="<c:url value="/resources/css/main.css" />"> <!-- Resource style -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script src="<c:url value="/resources/js/jquery.menu-aim.js" />"></script>
	<script src="<c:url value="/resources/js/main.js" />"></script>
	<link href='http://fonts.googleapis.com/css?family=Open+Sans:700,600' rel='stylesheet' type='text/css'>
	<link rel="stylesheet" href="/CSE545-SS/resources/css/style.css">
	<title>Home</title>
</head>
<body>

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
				
				<form:form action="new.html"  commandName="loginForm" method="post">
				
					<div class="box">
					<h1>Login Page</h1>
					<br>
					<br>
					<input path="userName" type="email" name="userName" value="email" onFocus="field_focus(this, 'email');" onblur="field_blur(this, 'email');" class="email" />
					  
					<input path="password" type="password" name="password" value="*****" onFocus="field_focus(this, 'email');" onblur="field_blur(this, 'email');" class="email" />
					<br>
					<br>
					<input type="radio" name="usertype" value="External User" required>External User<br>
					<input type="radio" name="usertype" value="Internal User" required>Internal User<br>

    				
					
					<br>
					<br>
					<div class="g-recaptcha" data-sitekey="6LcMeggUAAAAAPjZlkFO3kTfHhSqJ-qo3nQivY2S"></div>
					<br>
					<br>
					<tr>
						<td><input type="submit" value="Login"  class = "btn"/></td>
					</tr>
					<tr>
						</tr>
					<br>
					<br>
					
					
					
					  
					</div> <!-- End Box -->
				</form:form>
				<td><input type="submit" value="Sign In"  class = "btn" onclick="location.href='register.html';"/></td>
				
				<script src='https://www.google.com/recaptcha/api.js'></script>
				<p>Forgot your password? <input type = "submit" class = "myButton" value = "Click Here!" onclick="location.href='forgot.html';"></p>
				
  						
				<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js" type="text/javascript"></script>
    
        		<script src="/CSE545-SS/resources/js/index.js"></script>
			</div>
			
			
<% 

%>		
		</div> <!-- .content-wrapper -->
	
	</main> <!-- .cd-main-content -->
	<tr>
						
</body>
</html>