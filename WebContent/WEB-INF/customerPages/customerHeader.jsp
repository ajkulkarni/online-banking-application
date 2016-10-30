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
</head>
<body>
	<header class="cd-main-header">
		<!--<a href="#0" class="cd-logo"><img src="img/cd-logo.svg" alt="Logo"></a>-->
		<a href="home" style="padding-left: 20px"><img src="<c:url value="/resources/img/Sparky.png"/>"
						 style="vertical-align:top;"
						 width="35px" height="65px" alt="Sparky"></a>
		<a href="home"><img class="text-logo" src="<c:url value="/resources/img/Picture1.png"/>"
													style="padding: 7px 0 7px 0"
													width="200px" height="65px" alt="Devil's Vault"></a>

		<a href="#0" class="cd-nav-trigger"><span></span></a>

		<nav class="cd-nav">
			<ul class="cd-top-nav">
				<li class="has-children account">
					<a href="#0">
						<i style="padding-right: 10px" class="fa fa-user" aria-hidden="true"></i>${username}</a>
					<ul>

						<li><a href="userdetails">My Account</a></li>
						<li><a href="<c:url value="/logout" />">Logout</a></li>
					</ul>
				</li>
			</ul>
		</nav>
	</header> <!-- .cd-main-header -->
	<main class="cd-main-content">
		<nav class="cd-side-nav is-fixed">
			<ul>
			<li>
					<a href="home">Home</a>
				</li>
				<li class="has-children">
					<a href="#0">Accounts</a>
					
					<ul>
						<li><a href="/CSE545-SS/customer/CheckingAccount?checkingPicker='last month'">Checking</a></li>
						<li><a href="/CSE545-SS/customer/SavingsAccount?savingsPicker='last month'">Savings</a></li>
						<li><a href="credithome">Credit Card</a></li>
					</ul>
				</li>
				<li>
					<a href="pendingrequest">Request</a>
				</li>

				<li class="has-children">
					<a href="transferfunds">Transfer Money</a>
					
					<ul>
						<li><a href="emailPhoneFundTransfer">Email/Telephone</a></li>
						<li><a href="internalFundTransfer">Within Account</a></li>
						<li><a href="externalFundTransfer">Someone's Account</a></li>
					</ul>
				</li>
				<c:if test="${role == 'ROLE_CUSTOMER' }">
					<li>
						<a href="authorizemerchants">Merchant Panel</a>
					</li>
				</c:if>
				<c:if test="${role == 'ROLE_MERCHANT' }">
					<li>
						<a href="merchantpayment">Merchant Panel</a>
					</li>
				</c:if>
				<li><a href="/CSE545-SS/customer/addMoneyOption">Add Money</a></li>
				<li><a href="/CSE545-SS/customer/withdrawMoneyOption">Withdraw Money</a></li>
			</ul>
		</nav>