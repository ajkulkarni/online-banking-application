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
	<script src="<c:url value="/resources/js/jquery.menu-aim.js" />"></script>
	<script src="<c:url value="/resources/js/main.js" />"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
	<meta name="HandheldFriendly" content="true" />
	<title>New Customer Registration Form</title>
	<link href="resources/css/register.css" rel="stylesheet" type="text/css" />
	<link type="text/css" rel="stylesheet" href="resources/css/mainregister.css" />
	<link type="text/css" media="print" rel="stylesheet" href="resources/css/style1.css" />
	<style type="text/css">
	    .form-label-left{
	        width:150px !important;
	    }
	    .form-line{
	        padding-top:12px;
	        padding-bottom:12px;
	    }
	    .form-label-right{
	        width:150px !important;
	    }
	    body, html{
	        margin:0;
	        padding:0;
	        background:false;
	    }
	
	    .form-all{
	        margin:0px auto;
	        padding-top:0px;
	        width:690px;
	        color:#555 !important;
	        font-family:"Lucida Grande", "Lucida Sans Unicode", "Lucida Sans", Verdana, sans-serif;
	        font-size:14px;
	    }
	</style>
	
	<style type="text/css" id="form-designer-style">
	    /* Injected CSS Code */
	.form-matrix-values {padding-left: 4px;padding-right: 4px;}/* 1st Column */.form-matrix-row-headers +.form-matrix-values .form-textbox {width: 200px; padding-left: 4px;padding-right: 4px;}/* 2nd Column */.form-matrix-row-headers +.form-matrix-values +.form-matrix-values .form-textbox {width: 250px; padding-left:}/* 3rd Column */
	.form-matrix-row-headers +.form-matrix-values +.form-matrix-values +.form-matrix-values .form-textbox{width: 120px;}
	    /* Injected CSS Code */
	</style>
	
	<link type="text/css" rel="stylesheet" href="resources/css/apple.css"/>
	<script src="resources/js/form.js" type="text/javascript"></script>
	<script src="resources/js/form1.js" type="text/javascript"></script>
	<script type="text/javascript">
	   JotForm.setConditions([{"action":[{"field":"9","visibility":"Show"}],"link":"Any","terms":[{"field":"8","operator":"equals","value":"Other (Please specify...)"}],"type":"field"}]);
	   JotForm.init(function(){
	      JotForm.calendarMonths = ["January","February","March","April","May","June","July","August","September","October","November","December"];
	      JotForm.calendarDays = ["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"];
	      JotForm.calendarOther = {"today":"Today"};
	      JotForm.setCalendar("7", true, {"days":{"monday":true,"tuesday":true,"wednesday":true,"thursday":true,"friday":true,"saturday":true,"sunday":true},"future":true,"past":true,"custom":false,"ranges":false,"start":"January 01, 1898","end":"January 01, 1998"});
	      setTimeout(function() {
	          $('input_6').hint('ex: email@asu.edu');
	       }, 20);
		JotForm.clearFieldOnHide="disable";
		JotForm.onSubmissionError="jumpToFirstError";
	   });
	</script>
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
	
	<script>
		
		$(document).ready(function(){
		$('#submit').click(function(){
		var firstName=$('#firstName').val();
		var lastName=$('#lastName').val();
		var userEmail=$('#userEmail').val();
		var city = $('#city').val(); 
		var password = $('#password').val();
		var userSsn = $('#userSsn').val();
		var filter = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
		if(!filter.test(userEmail))
		{
			$("#userEmail").focus();
		    $("#errorBox").html("Please Enter a Valid Email Address");
		return false;
		}
		filter = /^[A-z]+$/;
		
		if(!filter.test(firstName))
		{
			$("#firstName").focus();
		    $("#errorBox").html("Name can contain only alphabets");
		return false;
		}
		else if(!filter.test(lastName))
		{
			$("#lastName").focus();
		    $("#errorBox").html("Name can contain only alphabets");
		return false;
		}
		console.log(password);
		filter = /^[A-z]+$/;
		if(!filter.test(city))
		{
			console.log("hahah");
			$("#city").focus();
		    $("#errorBox").html("Please Enter a Valid City");
		return false;
		}	
		
		var strongRegex = /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{6,16}$/;
		console.log(userSsn);
		if(!strongRegex.test(password))
		{
			console.log(password);
			$("#password").focus();
		    $("#errorBox").html("Please Enter a Valid Password");
		return false;
		}	
		var filter1 =  /^[0-9]{3}\-?[0-9]{2}\-?[0-9]{4}$/; 
		if(!filter1.test(userSsn))
		{
			console.log(userSsn);
			$("#userSsn").focus();
		    $("#errorBox").html("Please Enter a Valid SSN");
		return false;
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
				
			</div>
								<form class="jotform-form" action="newreg" method="post" name="form_62818724164157" id="62818724164157" accept-charset="utf-8">
			  <input type="hidden" name="formID" value="62818724164157" />
			  <div class="form-all">
			    <ul class="form-section page-section">
			      <li id="cid_1" class="form-input-wide" data-type="control_head">
			        <div class="form-header-group">
			          <div class="header-text httal htvam">
			            <h2 id="header_1" class="form-header">
			              Registration Form
			            </h2>
			          </div>
			        </div>
			      </li>
			      <div id="errorBox"></div>
			      <li class="form-line jf-required" data-type="control_fullname" id="id_3">
			        <label class="form-label form-label-left" id="label_3" for="input_3">
			          Full Name
			          <span class="form-required">
			            *
			          </span>
			        </label>
			        <div id="cid_3" class="form-input jf-required">
			          <span class="form-sub-label-container" style="vertical-align: top">
			            <input class="form-textbox validate[required]" type="text" size="10" name="firstName" id="firstName" required/>
			            <label class="form-sub-label" for="first_3" id="sublabel_first" style="min-height: 13px;"> First Name </label>
			          </span>
			          <span class="form-sub-label-container" style="vertical-align: top">
			            <input class="form-textbox validate[required]" type="text" size="15" name="lastName" id="lastName" required/>
			            <label class="form-sub-label" for="last_3" id="sublabel_last" style="min-height: 13px;"> Last Name </label>
			          </span>
			        </div>
			      </li>
			      <li class="form-line jf-required" data-type="control_address" id="id_4">
			        <label class="form-label form-label-left" id="label_4" for="input_4">
			          Address
			          <span class="form-required">
			            *
			          </span>
			        </label>
			        <div id="cid_4" class="form-input jf-required">
			          <table summary="" undefined class="form-address-table" border="0" cellpadding="0" cellspacing="0">
			            <tr>
			              <td colspan="2">
			                <span class="form-sub-label-container" style="vertical-align: top">
			                  <input class="form-textbox validate[required] form-address-line" type="text" name="street" id="input_4_addr_line1" required/>
			                  <label class="form-sub-label" for="input_4_addr_line1" id="sublabel_4_addr_line1" style="min-height: 13px;"> Street Address </label>
			                </span>
			              </td>
			            </tr>
			            <tr>
			              <td colspan="2">
			                <span class="form-sub-label-container" style="vertical-align: top">
			                  <input class="form-textbox form-address-line" type="text" name="house" id="input_4_addr_line2" size="46" />
			                  <label class="form-sub-label" for="input_4_addr_line2" id="sublabel_4_addr_line2" style="min-height: 13px;"> Street Address Line 2 </label>
			                </span>
			              </td>
			            </tr>
			            <tr>
			              <td width="50%">
			                <span class="form-sub-label-container" style="vertical-align: top">
			                  <input class="form-textbox validate[required] form-address-city" type="text" name="city" id="city" size="21" required/>
			                  <label class="form-sub-label" for="input_4_city" id="sublabel_4_city" style="min-height: 13px;"> City </label>
			                </span>
			              </td>
			              <td>
			                <span class="form-sub-label-container" style="vertical-align: top">
			                  <select class="form-dropdown validate[required] form-address-state" name="state" id="state">
			                    <option value="" selected> Please Select </option>
			                    <option value="Alabama"> Alabama </option>
			                    <option value="Alaska"> Alaska </option>
			                    <option value="Arizona"> Arizona </option>
			                    <option value="Arkansas"> Arkansas </option>
			                    <option value="California"> California </option>
			                    <option value="Colorado"> Colorado </option>
			                    <option value="Connecticut"> Connecticut </option>
			                    <option value="Delaware"> Delaware </option>
			                    <option value="District of Columbia"> District of Columbia </option>
			                    <option value="Florida"> Florida </option>
			                    <option value="Georgia"> Georgia </option>
			                    <option value="Hawaii"> Hawaii </option>
			                    <option value="Idaho"> Idaho </option>
			                    <option value="Illinois"> Illinois </option>
			                    <option value="Indiana"> Indiana </option>
			                    <option value="Iowa"> Iowa </option>
			                    <option value="Kansas"> Kansas </option>
			                    <option value="Kentucky"> Kentucky </option>
			                    <option value="Louisiana"> Louisiana </option>
			                    <option value="Maine"> Maine </option>
			                    <option value="Maryland"> Maryland </option>
			                    <option value="Massachusetts"> Massachusetts </option>
			                    <option value="Michigan"> Michigan </option>
			                    <option value="Minnesota"> Minnesota </option>
			                    <option value="Mississippi"> Mississippi </option>
			                    <option value="Missouri"> Missouri </option>
			                    <option value="Montana"> Montana </option>
			                    <option value="Nebraska"> Nebraska </option>
			                    <option value="Nevada"> Nevada </option>
			                    <option value="New Hampshire"> New Hampshire </option>
			                    <option value="New Jersey"> New Jersey </option>
			                    <option value="New Mexico"> New Mexico </option>
			                    <option value="New York"> New York </option>
			                    <option value="North Carolina"> North Carolina </option>
			                    <option value="North Dakota"> North Dakota </option>
			                    <option value="Ohio"> Ohio </option>
			                    <option value="Oklahoma"> Oklahoma </option>
			                    <option value="Oregon"> Oregon </option>
			                    <option value="Pennsylvania"> Pennsylvania </option>
			                    <option value="Rhode Island"> Rhode Island </option>
			                    <option value="South Carolina"> South Carolina </option>
			                    <option value="South Dakota"> South Dakota </option>
			                    <option value="Tennessee"> Tennessee </option>
			                    <option value="Texas"> Texas </option>
			                    <option value="Utah"> Utah </option>
			                    <option value="Vermont"> Vermont </option>
			                    <option value="Virginia"> Virginia </option>
			                    <option value="Washington"> Washington </option>
			                    <option value="West Virginia"> West Virginia </option>
			                    <option value="Wisconsin"> Wisconsin </option>
			                    <option value="Wyoming"> Wyoming </option>
			                  </select>
			                  <label class="form-sub-label" for="input_4_state" id="sublabel_4_state" style="min-height: 13px;"> State </label>
			                </span>
			              </td>
			            </tr>
			            <tr>
			              <td width="50%">
			                <span class="form-sub-label-container" style="vertical-align: top">
			                  <input class="form-textbox validate[required] form-address-postal" type="text" name="pincode" id="input_4_postal" size="10" required/>
			                  <label class="form-sub-label" for="input_4_postal" id="sublabel_4_postal" style="min-height: 13px;"> Zip Code </label>
			                </span>
			              </td>
			              <td>
			                <span class="form-sub-label-container" style="vertical-align: top">
			                  <select class="form-dropdown validate[required] form-address-country" defaultcountry="United States" name="country" id="input_4_country">
			                    <option value="" selected> Please Select </option>
			                    <option selected="selected" value="United States"> United States </option>
			                    <option value="Afghanistan"> Afghanistan </option>
			                    <option value="Albania"> Albania </option>
			                    <option value="Algeria"> Algeria </option>
			                    <option value="American Samoa"> American Samoa </option>
			                    <option value="Andorra"> Andorra </option>
			                    <option value="Angola"> Angola </option>
			                    <option value="Anguilla"> Anguilla </option>
			                    <option value="Antigua and Barbuda"> Antigua and Barbuda </option>
			                    <option value="Argentina"> Argentina </option>
			                    <option value="Armenia"> Armenia </option>
			                    <option value="Aruba"> Aruba </option>
			                    <option value="Australia"> Australia </option>
			                    <option value="Austria"> Austria </option>
			                    <option value="Azerbaijan"> Azerbaijan </option>
			                    <option value="The Bahamas"> The Bahamas </option>
			                    <option value="Bahrain"> Bahrain </option>
			                    <option value="Bangladesh"> Bangladesh </option>
			                    <option value="Barbados"> Barbados </option>
			                    <option value="Belarus"> Belarus </option>
			                    <option value="Belgium"> Belgium </option>
			                    <option value="Belize"> Belize </option>
			                    <option value="Benin"> Benin </option>
			                    <option value="Bermuda"> Bermuda </option>
			                    <option value="Bhutan"> Bhutan </option>
			                    <option value="Bolivia"> Bolivia </option>
			                    <option value="Bosnia and Herzegovina"> Bosnia and Herzegovina </option>
			                    <option value="Botswana"> Botswana </option>
			                    <option value="Brazil"> Brazil </option>
			                    <option value="Brunei"> Brunei </option>
			                    <option value="Bulgaria"> Bulgaria </option>
			                    <option value="Burkina Faso"> Burkina Faso </option>
			                    <option value="Burundi"> Burundi </option>
			                    <option value="Cambodia"> Cambodia </option>
			                    <option value="Cameroon"> Cameroon </option>
			                    <option value="Canada"> Canada </option>
			                    <option value="Cape Verde"> Cape Verde </option>
			                    <option value="Cayman Islands"> Cayman Islands </option>
			                    <option value="Central African Republic"> Central African Republic </option>
			                    <option value="Chad"> Chad </option>
			                    <option value="Chile"> Chile </option>
			                    <option value="China"> China </option>
			                    <option value="Christmas Island"> Christmas Island </option>
			                    <option value="Cocos (Keeling) Islands"> Cocos (Keeling) Islands </option>
			                    <option value="Colombia"> Colombia </option>
			                    <option value="Comoros"> Comoros </option>
			                    <option value="Congo"> Congo </option>
			                    <option value="Cook Islands"> Cook Islands </option>
			                    <option value="Costa Rica"> Costa Rica </option>
			                    <option value="Cote d'Ivoire"> Cote d'Ivoire </option>
			                    <option value="Croatia"> Croatia </option>
			                    <option value="Cuba"> Cuba </option>
			                    <option value="Cyprus"> Cyprus </option>
			                    <option value="Czech Republic"> Czech Republic </option>
			                    <option value="Democratic Republic of the Congo"> Democratic Republic of the Congo </option>
			                    <option value="Denmark"> Denmark </option>
			                    <option value="Djibouti"> Djibouti </option>
			                    <option value="Dominica"> Dominica </option>
			                    <option value="Dominican Republic"> Dominican Republic </option>
			                    <option value="Ecuador"> Ecuador </option>
			                    <option value="Egypt"> Egypt </option>
			                    <option value="El Salvador"> El Salvador </option>
			                    <option value="Equatorial Guinea"> Equatorial Guinea </option>
			                    <option value="Eritrea"> Eritrea </option>
			                    <option value="Estonia"> Estonia </option>
			                    <option value="Ethiopia"> Ethiopia </option>
			                    <option value="Falkland Islands"> Falkland Islands </option>
			                    <option value="Faroe Islands"> Faroe Islands </option>
			                    <option value="Fiji"> Fiji </option>
			                    <option value="Finland"> Finland </option>
			                    <option value="France"> France </option>
			                    <option value="French Polynesia"> French Polynesia </option>
			                    <option value="Gabon"> Gabon </option>
			                    <option value="The Gambia"> The Gambia </option>
			                    <option value="Georgia"> Georgia </option>
			                    <option value="Germany"> Germany </option>
			                    <option value="Ghana"> Ghana </option>
			                    <option value="Gibraltar"> Gibraltar </option>
			                    <option value="Greece"> Greece </option>
			                    <option value="Greenland"> Greenland </option>
			                    <option value="Grenada"> Grenada </option>
			                    <option value="Guadeloupe"> Guadeloupe </option>
			                    <option value="Guam"> Guam </option>
			                    <option value="Guatemala"> Guatemala </option>
			                    <option value="Guernsey"> Guernsey </option>
			                    <option value="Guinea"> Guinea </option>
			                    <option value="Guinea-Bissau"> Guinea-Bissau </option>
			                    <option value="Guyana"> Guyana </option>
			                    <option value="Haiti"> Haiti </option>
			                    <option value="Honduras"> Honduras </option>
			                    <option value="Hong Kong"> Hong Kong </option>
			                    <option value="Hungary"> Hungary </option>
			                    <option value="Iceland"> Iceland </option>
			                    <option value="India"> India </option>
			                    <option value="Indonesia"> Indonesia </option>
			                    <option value="Iran"> Iran </option>
			                    <option value="Iraq"> Iraq </option>
			                    <option value="Ireland"> Ireland </option>
			                    <option value="Israel"> Israel </option>
			                    <option value="Italy"> Italy </option>
			                    <option value="Jamaica"> Jamaica </option>
			                    <option value="Japan"> Japan </option>
			                    <option value="Jersey"> Jersey </option>
			                    <option value="Jordan"> Jordan </option>
			                    <option value="Kazakhstan"> Kazakhstan </option>
			                    <option value="Kenya"> Kenya </option>
			                    <option value="Kiribati"> Kiribati </option>
			                    <option value="North Korea"> North Korea </option>
			                    <option value="South Korea"> South Korea </option>
			                    <option value="Kosovo"> Kosovo </option>
			                    <option value="Kuwait"> Kuwait </option>
			                    <option value="Kyrgyzstan"> Kyrgyzstan </option>
			                    <option value="Laos"> Laos </option>
			                    <option value="Latvia"> Latvia </option>
			                    <option value="Lebanon"> Lebanon </option>
			                    <option value="Lesotho"> Lesotho </option>
			                    <option value="Liberia"> Liberia </option>
			                    <option value="Libya"> Libya </option>
			                    <option value="Liechtenstein"> Liechtenstein </option>
			                    <option value="Lithuania"> Lithuania </option>
			                    <option value="Luxembourg"> Luxembourg </option>
			                    <option value="Macau"> Macau </option>
			                    <option value="Macedonia"> Macedonia </option>
			                    <option value="Madagascar"> Madagascar </option>
			                    <option value="Malawi"> Malawi </option>
			                    <option value="Malaysia"> Malaysia </option>
			                    <option value="Maldives"> Maldives </option>
			                    <option value="Mali"> Mali </option>
			                    <option value="Malta"> Malta </option>
			                    <option value="Marshall Islands"> Marshall Islands </option>
			                    <option value="Martinique"> Martinique </option>
			                    <option value="Mauritania"> Mauritania </option>
			                    <option value="Mauritius"> Mauritius </option>
			                    <option value="Mayotte"> Mayotte </option>
			                    <option value="Mexico"> Mexico </option>
			                    <option value="Micronesia"> Micronesia </option>
			                    <option value="Moldova"> Moldova </option>
			                    <option value="Monaco"> Monaco </option>
			                    <option value="Mongolia"> Mongolia </option>
			                    <option value="Montenegro"> Montenegro </option>
			                    <option value="Montserrat"> Montserrat </option>
			                    <option value="Morocco"> Morocco </option>
			                    <option value="Mozambique"> Mozambique </option>
			                    <option value="Myanmar"> Myanmar </option>
			                    <option value="Nagorno-Karabakh"> Nagorno-Karabakh </option>
			                    <option value="Namibia"> Namibia </option>
			                    <option value="Nauru"> Nauru </option>
			                    <option value="Nepal"> Nepal </option>
			                    <option value="Netherlands"> Netherlands </option>
			                    <option value="Netherlands Antilles"> Netherlands Antilles </option>
			                    <option value="New Caledonia"> New Caledonia </option>
			                    <option value="New Zealand"> New Zealand </option>
			                    <option value="Nicaragua"> Nicaragua </option>
			                    <option value="Niger"> Niger </option>
			                    <option value="Nigeria"> Nigeria </option>
			                    <option value="Niue"> Niue </option>
			                    <option value="Norfolk Island"> Norfolk Island </option>
			                    <option value="Turkish Republic of Northern Cyprus"> Turkish Republic of Northern Cyprus </option>
			                    <option value="Northern Mariana"> Northern Mariana </option>
			                    <option value="Norway"> Norway </option>
			                    <option value="Oman"> Oman </option>
			                    <option value="Pakistan"> Pakistan </option>
			                    <option value="Palau"> Palau </option>
			                    <option value="Palestine"> Palestine </option>
			                    <option value="Panama"> Panama </option>
			                    <option value="Papua New Guinea"> Papua New Guinea </option>
			                    <option value="Paraguay"> Paraguay </option>
			                    <option value="Peru"> Peru </option>
			                    <option value="Philippines"> Philippines </option>
			                    <option value="Pitcairn Islands"> Pitcairn Islands </option>
			                    <option value="Poland"> Poland </option>
			                    <option value="Portugal"> Portugal </option>
			                    <option value="Puerto Rico"> Puerto Rico </option>
			                    <option value="Qatar"> Qatar </option>
			                    <option value="Republic of the Congo"> Republic of the Congo </option>
			                    <option value="Romania"> Romania </option>
			                    <option value="Russia"> Russia </option>
			                    <option value="Rwanda"> Rwanda </option>
			                    <option value="Saint Barthelemy"> Saint Barthelemy </option>
			                    <option value="Saint Helena"> Saint Helena </option>
			                    <option value="Saint Kitts and Nevis"> Saint Kitts and Nevis </option>
			                    <option value="Saint Lucia"> Saint Lucia </option>
			                    <option value="Saint Martin"> Saint Martin </option>
			                    <option value="Saint Pierre and Miquelon"> Saint Pierre and Miquelon </option>
			                    <option value="Saint Vincent and the Grenadines"> Saint Vincent and the Grenadines </option>
			                    <option value="Samoa"> Samoa </option>
			                    <option value="San Marino"> San Marino </option>
			                    <option value="Sao Tome and Principe"> Sao Tome and Principe </option>
			                    <option value="Saudi Arabia"> Saudi Arabia </option>
			                    <option value="Senegal"> Senegal </option>
			                    <option value="Serbia"> Serbia </option>
			                    <option value="Seychelles"> Seychelles </option>
			                    <option value="Sierra Leone"> Sierra Leone </option>
			                    <option value="Singapore"> Singapore </option>
			                    <option value="Slovakia"> Slovakia </option>
			                    <option value="Slovenia"> Slovenia </option>
			                    <option value="Solomon Islands"> Solomon Islands </option>
			                    <option value="Somalia"> Somalia </option>
			                    <option value="Somaliland"> Somaliland </option>
			                    <option value="South Africa"> South Africa </option>
			                    <option value="South Ossetia"> South Ossetia </option>
			                    <option value="South Sudan"> South Sudan </option>
			                    <option value="Spain"> Spain </option>
			                    <option value="Sri Lanka"> Sri Lanka </option>
			                    <option value="Sudan"> Sudan </option>
			                    <option value="Suriname"> Suriname </option>
			                    <option value="Svalbard"> Svalbard </option>
			                    <option value="Swaziland"> Swaziland </option>
			                    <option value="Sweden"> Sweden </option>
			                    <option value="Switzerland"> Switzerland </option>
			                    <option value="Syria"> Syria </option>
			                    <option value="Taiwan"> Taiwan </option>
			                    <option value="Tajikistan"> Tajikistan </option>
			                    <option value="Tanzania"> Tanzania </option>
			                    <option value="Thailand"> Thailand </option>
			                    <option value="Timor-Leste"> Timor-Leste </option>
			                    <option value="Togo"> Togo </option>
			                    <option value="Tokelau"> Tokelau </option>
			                    <option value="Tonga"> Tonga </option>
			                    <option value="Transnistria Pridnestrovie"> Transnistria Pridnestrovie </option>
			                    <option value="Trinidad and Tobago"> Trinidad and Tobago </option>
			                    <option value="Tristan da Cunha"> Tristan da Cunha </option>
			                    <option value="Tunisia"> Tunisia </option>
			                    <option value="Turkey"> Turkey </option>
			                    <option value="Turkmenistan"> Turkmenistan </option>
			                    <option value="Turks and Caicos Islands"> Turks and Caicos Islands </option>
			                    <option value="Tuvalu"> Tuvalu </option>
			                    <option value="Uganda"> Uganda </option>
			                    <option value="Ukraine"> Ukraine </option>
			                    <option value="United Arab Emirates"> United Arab Emirates </option>
			                    <option value="United Kingdom"> United Kingdom </option>
			                    <option value="Uruguay"> Uruguay </option>
			                    <option value="Uzbekistan"> Uzbekistan </option>
			                    <option value="Vanuatu"> Vanuatu </option>
			                    <option value="Vatican City"> Vatican City </option>
			                    <option value="Venezuela"> Venezuela </option>
			                    <option value="Vietnam"> Vietnam </option>
			                    <option value="British Virgin Islands"> British Virgin Islands </option>
			                    <option value="Isle of Man"> Isle of Man </option>
			                    <option value="US Virgin Islands"> US Virgin Islands </option>
			                    <option value="Wallis and Futuna"> Wallis and Futuna </option>
			                    <option value="Western Sahara"> Western Sahara </option>
			                    <option value="Yemen"> Yemen </option>
			                    <option value="Zambia"> Zambia </option>
			                    <option value="Zimbabwe"> Zimbabwe </option>
			                    <option value="other"> Other </option>
			                  </select>
			                  <label class="form-sub-label" for="input_4_country" id="sublabel_4_country" style="min-height: 13px;"> Country </label>
			                </span>
			              </td>
			            </tr>
			          </table>
			        </div>
			      </li>
			      <li class="form-line jf-required" data-type="control_datetime" id="id_7">
			        <label class="form-label form-label-left" id="label_7" for="input_7">
			          DOB
			          <span class="form-required">
			            *
			          </span>
			        </label>
			        <div id="cid_7" class="form-input jf-required">
			          <span class="form-sub-label-container" style="vertical-align: top">
			          	<input type="date" required>
			           
			        </div>
			      </li>
			      <li class="form-line jf-required" data-type="control_phone" id="id_5">
			        <label class="form-label form-label-left" id="label_5" for="input_5">
			          Phone Number
			          <span class="form-required" required>
			            *
			          </span>
			        </label>
			        <div id="cid_5" class="form-input jf-required">
			          <span class="form-sub-label-container" style="vertical-align: top">
			            <input class="form-textbox validate[required]" type="tel" name="userPhonecode" id="input_5_country" size="6" required>
			            <span class="phone-separate">
			              &nbsp;-
			            </span>
			            <label class="form-sub-label" for="input_5_country" id="sublabel_country" style="min-height: 13px;"> Country Code </label>
			          </span>
			          <span class="form-sub-label-container" style="vertical-align: top">
			            <input class="form-textbox validate[required]" type="tel" name="userAreacode" id="input_5_area" size="3" required>
			            <span class="phone-separate">
			              &nbsp;-
			            </span>
			            <label class="form-sub-label" for="input_5_area" id="sublabel_area" style="min-height: 13px;"> Area Code </label>
			          </span>
			          <span class="form-sub-label-container" style="vertical-align: top">
			            <input class="form-textbox validate[required]" type="tel" name="userPhonenumber" id="input_5_phone" size="8" required>
			            <label class="form-sub-label" for="input_5_phone" id="sublabel_phone" style="min-height: 13px;"> Phone Number </label>
			          </span>
			        </div>
			      </li>
			      <li class="form-line jf-required" data-type="control_email" id="id_6">
			        <label class="form-label form-label-left" id="label_6" for="input_6">
			          E-mail
			          <span class="form-required">
			            *
			          </span>
			        </label>
			        <div id="cid_6" class="form-input jf-required">
			          <input type="email" class=" form-textbox validate[required, Email]" id="userEmail" name="userEmail" size="20" value="" required/>
			        </div>
			      </li>
			      <li class="form-line jf-required" data-type="control_textbox" id="id_10">
			        <label class="form-label form-label-left" id="label_10" for="input_10">
			          SSN
			          <span class="form-required">
			            *
			          </span>
			        </label>
			        <div id="cid_10" class="form-input jf-required">
			          <input type="text" class=" form-textbox validate[required, AlphaNumeric]" data-type="input-textbox" id="userSsn" name="userSsn" size="20" value="" required/>
			        </div>
			      </li>
			      <li class="form-line jf-required" data-type="control_textbox" id="id_11">
			        <label class="form-label form-label-left" id="label_11" for="input_11">
			          Password
			          <span class="form-required">
			            *
			          </span>
			        </label>
			        <div id="cid_11" class="form-input jf-required">
			          <input type="password" class=" form-textbox validate[required, AlphaNumeric]" data-type="input-textbox" id="password" name="userPassword" size="20" value="" required/>
			        </div>
			      </li>
			      <div class="g-recaptcha" data-sitekey="6LcMeggUAAAAAPjZlkFO3kTfHhSqJ-qo3nQivY2S"></div>
			      
			        <div id="cid_2" class="form-input-wide">
			          <div style="text-align:center" class="form-buttons-wrapper">
			            <button id="submit" type="submit" class="form-submit-button form-submit-button-simple_green_apple">
			              Submit
			            </button>
			            &nbsp;
			            <button id="input_reset_2" type="reset" class="form-submit-reset form-submit-button-simple_green_apple">
			              Reset Form
			            </button>
			          </div>
			        </div>
			      
			     
			      
			      	<script src='https://www.google.com/recaptcha/api.js'></script>	
			     
			    </ul>
			  </div>
			  	
			  <input type="hidden" id="simple_spc" name="simple_spc" value="62818724164157" />
			  <script type="text/javascript">
			  document.getElementById("si" + "mple" + "_spc").value = "62818724164157-62818724164157";
			  </script>
			  <input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
					
			  
			</form>
			
		
		</div> <!-- .content-wrapper -->
	
	</main> <!-- .cd-main-content -->
</body>
</html>