<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	<script src = "http://code.jquery.com/jquery-2.1.1.min.js"></script>
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
			$('#confirmpassword').keyboard();
	});
	</script>
	<script>
		$(document).ready(function() {
			$("#saveForm").click(function() {
				rules: {
				password: {
					var strongRegex = ^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{6,16}$;
						var password = $('#password').val();
						if(!strongRegex.test(password))
						{
							return false;
					}
						
				},
					confirmpassword: {
						equalTo: "#password"
					}
				},
				messages: {
					password: {
						"Password does not agree with all the rules listed below. Please type the password according to the rules given below."
						
				},
					confirmpassword: {
						equalTo: "Passwords entered in the two fields do not match. Please re enter the passwords."
				}
			}
		});
	});
	</script>
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
				<h3>New Password</h3>
				
				<h4 style="color:red">${msg}</h4>
				<form id = "NewPasswordForm"action="newpassword" method='POST'>
				<div class="form_description">
   					<h2>Enter new password</h2>
  					<p>Change the Password</p>
  				</div>      
  				<ul>
   
     			<li id="li_1" >
  				<label class="description" for="element_1">Enter New Password  <color = "red"> * <color/></label>
  					<div>
   						<input id="password" name="password"  class="element text medium" type="password" maxlength="255" value="" required/> 
  					</div><p class="guidelines" id="guide_1">1) The password must be exactly 8 characters long.</p>
						<p class="guidelines" id="guide_1">2) It must contain at least one letter.</p>
						<p class="guidelines" id="guide_1">3) It must have at least one number.</p>
						<p class="guidelines" id="guide_1">4) It must have at at least one of the following special characters [!@#$%^&*].</small></p> 
  					</li>  <li id="li_2" >
  					<label class="description" for="element_2">Confirm Password <color = "red"> * <color/> </label>
  					<div>
   					<input id="confirmpassword" name="confirmpassword" class="element text medium" type="password" maxlength="255" value="" required/> 
  					</div> 
  					</li>
   					<br/>
   					
     				<li class="buttons">
       				<input type="hidden" name="form_id" value="1165710" />
       
   					<input id="saveForm" class="button_text" type="submit" name="submit" value="Submit" OnClick = 'validate()'/>
  					</li>
   					</ul>
   					<input type="hidden" name="${_csrf.parameterName}"
         				   value="${_csrf.token}" />
					
					</form>
					
				</div>
					
			</div> <!-- .content-wrapper -->
	
		</main> <!-- .cd-main-content -->
</body>
</html>