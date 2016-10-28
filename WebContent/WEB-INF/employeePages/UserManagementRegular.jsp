<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="employeeHeader.jsp" %>
    <div class="content-wrapper">
        <div class="col-md-12" id="page-content">
            <h3>User Management</h3>
            <c:if test="${not empty message}">
			<div class="alert alert-dismissible alert-success">
  				<button type="button" class="close" data-dismiss="alert">&times;</button>
  				<strong>${message}</strong>
			</div>
			</c:if>
			<c:if test="${not empty error_msg}">
			<div class="alert alert-dismissible alert-success">
  				<button type="button" class="close" data-dismiss="alert">&times;</button>
  				<strong>${error_msg}</strong>
			</div>
			</c:if>
			<c:if test="${not empty error_message}">
			<div class="alert alert-dismissible alert-success">
  				<button type="button" class="close" data-dismiss="alert">&times;</button>
  				<strong>${error_message}</strong>
			</div>
			</c:if>
            <c:if test="${role == 'ROLE_REGULAR'}">
            <a href="#newRequest" data-toggle="modal" class="btn btn-sm btn-primary">New Request</a>
            <form action = "externalregistrationform" method = "post" id="newregisterButton" style="float:right; display:none">
	       		<input type="hidden" name="userType" value="external">
	       		<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
	       		<button type="submit" class="btn btn-sm btn-primary">New Registration</button>
            </form>
            </c:if>
            <br><br>
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">Pending Authorization</h3>
                </div>
                <div class="panel-body no-padding">
                    <table id="content-table">
                        <thead>
                            <tr>
                                <th class="active">External User</th>
                                <th class="active">Authorization Type</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:choose>
                        		<c:when test="${empty pendingList}">
                        			<tr>
                                    	<td colspan="2">No Pending Request</td>
                                	</tr>
                        		</c:when>
                        		<c:otherwise>
                        			<c:forEach items="${pendingList}" var="authorization">
                                		<tr>
                                			<c:choose>
			                                	<c:when test="${authorization.auth_Type == 'registration'}">
			                                    	<td style="text-align:center">N/A</td>
			                                    </c:when>
			                                    <c:otherwise>
			                                    	<td style="text-align:center">${authorization.external_userID}</td>
			                                    </c:otherwise>
		                                    </c:choose>
                                    		<td style="text-align:center">${authorization.auth_Type}</td>
                                		</tr>
                            		</c:forEach>
                        		</c:otherwise>
                        	</c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">Completed Authorization</h3>
                </div>
                <div class="panel-body no-padding">
                    <table id="content-table">
                        <thead>
                            <tr>                               
                                <th class="active">External User</th>
                                <th class="active">Authorization Type</th>
                                <th class="active">Option</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:choose>
                        		<c:when test="${empty completeList}">
                        			<tr>
                                    	<td colspan="2">No Authorization</td>
                                	</tr>
                        		</c:when>
                        		<c:otherwise>
                        			<c:forEach items="${completeList}" var="authorization">
		                                <tr>
		                                	<c:choose>
			                                	<c:when test="${authorization.auth_Type == 'registration'}">
			                                    	<td style="text-align:center">N/A</td>
			                                    </c:when>
			                                    <c:otherwise>
			                                    	<td style="text-align:center">${authorization.external_userID}</td>
			                                    </c:otherwise>
		                                    </c:choose>
		                                    <td style="text-align:center">${authorization.auth_Type}</td>
		                                    <c:if test="${authorization.auth_Type == 'transaction'}">
		                                    <td style="text-align:center">
		                                    	<form action = "viewtransaction" method = "post">
		                                    		<input type="hidden" name="extUserID" value="${authorization.external_userID}">
		                                    		<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
		                                    		<button type="submit" class="btn btn-sm btn-primary">View Transactions</button>
		                                    	</form>
		                                    </td>
		                                    </c:if>
		                                    <c:if test="${authorization.auth_Type == 'account'}">
		                                    	<td style="text-align:center">
			                                    	<form action = "viewaccountdetails" method = "post">
			                                   			<input type="hidden" name="userType" value="external">
			                                    		<input type="hidden" name="extUserID" value="${authorization.external_userID}">
			                                    		<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
			                                    		<button type="submit" class="btn btn-sm btn-primary">View Account</button>
			                                    	</form>
		                                    	</td>
		                                    </c:if>
		                                    <c:if test="${authorization.auth_Type == 'registration'}">
		                                    <script> EnableRegistration() </script>
		                                    <td style="text-align:center"></td>
		                                    </c:if>
		                                </tr>
                            		</c:forEach>
                        		</c:otherwise>
                        	</c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="modal" id="newRequest">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-body no-padding">
		        <div class="panel panel-success no-margin">
				  <div class="panel-heading">
				    <h3 class="panel-title">Add Authorization Request</h3>
				  </div>
				  <div class="panel-body">
				    <form class="form-horizontal" action="newauthorizationreq" method="POST">
				    	<fieldset>
				    		<div class="form-group">
				    			<label for="extUserID" class="col-lg-3 control-label">Customer ID</label>
			    				<div class="col-lg-9">
		      							<input type="text" class="form-control" id="extUserID" name="extUserID" placeholder="Customer ID" required>
		      					</div>
		      					<br>
		      					<br>
				    			<label for="requestType" class="col-lg-3 control-label">Request Type</label>
			    				<div class="col-lg-9">
       								<select class="form-control" id="requestType" name="requestType" onchange="disableCustomerID()" required>
       									<option value="">Select Type</option>
          								<option value="account">Account Details</option>
          								<option value="transaction">Transaction Access</option>
          								<option value="registration">New Registration</option>
       								</select>
       							</div>
       							<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
       							<div class="col-lg-10 col-lg-offset-2" style="margin-top:15px;">
	       							<button style="float:right;" type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			        				<button style="float:right;margin-right:15px;" type="submit" class="btn btn-primary">Submit</button>
      							</div>
				    		</div>
				    	</fieldset>
				    </form>
				  </div>
				</div>
		      </div>
		    </div>
		  </div>
		</div>
        
    </div> <!-- .content-wrapper -->
    
</main> 
<script type="text/javascript">
    $(document).ready(function() {
        sideNavigationSettings();
    });
</script>
<script src="<c:url value="/resources/js/custom.js" />"></script>
</body>
</html>