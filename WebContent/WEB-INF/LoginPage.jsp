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
	
	<script>
		window.onload = function() {
		  var recaptcha = document.forms["myForm"]["g-recaptcha-response"];
		  recaptcha.required = true;
		  recaptcha.oninvalid = function(e) {
		    // do something
		    alert("Please complete the captcha");
		  }
		}	
	</script>
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
				<h3>Login Page</h3>
				  <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
      				<font color="red">
        				Your login attempt was not successful due to <br/><br/>
        			<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
        			<%-- <h2>${msg}</h2> --%>
      				</font>
    			  </c:if>
    			  <c:if test="${not empty exception_message}">
					<div class="alert alert-dismissible alert-danger">
  					<button type="button" class="close" data-dismiss="alert">&times;</button>
  					<strong>${exception_message}</strong>
					</div>
				</c:if>
				<form action="<c:url value='j_spring_security_check' />" method='POST'>
      				<label for="username" class="col-lg-2 control-label no-padding">Username</label>
      				<div class="col-lg-10 form-margin no-padding">
        				<input type="email" class="form-control no-padding" name="username" id="userEmail" placeholder="Username" required>
      				</div>
      				<label for="password" class="col-lg-2 control-label no-padding">Password</label>
      				<div class="col-lg-10 form-margin no-padding">
        				<input type="password" class="form-control no-padding" name="password" id="password" placeholder="Password" required>
      				</div>
     				<div class="col-lg-12 form-margin no-padding">
      				<div class="g-recaptcha" data-sitekey="6LecawoUAAAAAGxDiLpqTZ9CZSr5I0QAyWEuXWRW"></div>
      				</div>
      				<div class="col-lg-12 form-margin no-padding">
      				<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
				    <input type="submit" class="btn btn-primary" value="Login" class="btn" id="submit"/>
				    </div>
				    <br>
				    <br>
				</form>
				
				<script src='https://www.google.com/recaptcha/api.js'></script>
				<p>Forgot your password? <input type = "submit" class = "myButton" value = "Click Here!" onclick="location.href='forgotpassword';"></p>
			</div>
	
		</div> <!-- .content-wrapper -->
	
	</main> <!-- .cd-main-content -->
</body>
</html>
