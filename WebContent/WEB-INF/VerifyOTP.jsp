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
 
 <link type="text/css" rel="stylesheet" href="https://cdn.jotfor.ms/css/styles/buttons/form-submit-button-simple_orange.css?3.3.15179"/>
 <script src="https://cdn.jotfor.ms/static/prototype.forms.js" type="text/javascript"></script>
 <script src="https://cdn.jotfor.ms/static/jotform.forms.js?3.3.15179" type="text/javascript"></script>
 <script type="text/javascript">
    
    $(document).ready(function(){
     
     $('#input_19').click(function(){
      $('#').hide();
      $('#').show();
      
     });
    });
 </script>
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
    
   </div>
   <h4 style="color:red">${msg}</h4>
   
  <form class="jotform-form" action="backtologin.html" method="post" name="form_62831368525157" id="62831368525157" accept-charset="utf-8">
    <input type="hidden" name="formID" value="62831368525157" />
    <div class="form-all">
      <ul class="form-section page-section">
        <li class="form-line jf-required" data-type="control_email" id="id_16">
          <label class="form-label form-label-top form-label-auto" id="label_16" for="input_16">
            Enter OTP
            <span class="form-required">
              *
            </span>
          </label>
          <div id="cid_16" class="form-input-wide jf-required">
            <input type="number" id="input_16" name="otp" size="6" value="" required/>
          </div>
        </li>
        <br>
       
          <div id="cid_19" class="form-input-wide">
            <div style="margin-left:156px" class="form-buttons-wrapper">
              <button id="input_19" type="submit">
                Verify OTP
              </button>
            </div>
          </div>
        
        
      </ul>
    </div>
  
    <input type="hidden" id="simple_spc" name="simple_spc" value="62831368525157" />
    <script type="text/javascript">
    document.getElementById("si" + "mple" + "_spc").value = "62831368525157-62831368525157";
    </script>
    <input type="hidden" name="${_csrf.parameterName}"
         value="${_csrf.token}" />
  </form>
       
     
  
  </div> <!-- .content-wrapper -->
 
 </main> <!-- .cd-main-content -->
</body>
</html>