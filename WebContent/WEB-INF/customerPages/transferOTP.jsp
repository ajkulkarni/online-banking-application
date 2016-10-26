<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>

<%@include file="customerHeader.jsp"%>

<div class="content-wrapper">
           <div class="col-md-12">
           	<c:if test="${not empty error_msg}">
			<div class="alert alert-dismissible alert-success">
  				<button type="button" class="close" data-dismiss="alert">&times;</button>
  				<strong>${error_msg}</strong>
			</div>
			</c:if>
               <div class="panel panel-primary" id="externaltransferpanel">
                   <div class="panel-heading">
                       <h3 class="panel-title">Transfer Money - OTP Verification</h3>
                   </div>
                   <div class="panel-body">
                       <form class="form-horizontal" action="verifyTransactionOTP"
                           method='POST'>
                           <fieldset>
                               <div class="form-group">
                                   <label for="optdata" class="col-lg-2 control-label">Enter OTP : </label>
                                   <div class="col-lg-5 input-group">
                                       <input type="text" class="form-control" name="otpdata" placeholder="Enter OTP" required>
                                   </div>
                                   <br><br>
                                   <div class="form-group">
                                       <label class="col-lg-2 control-label"></label>
                                       <div class="col-lg-5 input-group">
                                       <input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
                                           <button type="submit" class="btn btn-primary">Submit</button>
                                       </div>
                                   </div>    
                               </div>
                           </fieldset>
                       </form>
                   </div>
               </div>
           </div>
       </div>
<!-- .content-wrapper -->

<script type="text/javascript">
   $(document).ready(function() {
       sideNavigationSettings();
   });
</script>

</body>
</html>