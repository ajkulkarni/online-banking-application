<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="employeeHeader.jsp" %>
    <div class="content-wrapper">
        <div class="col-md-12" id="page-content">
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
            <form action = "externalregistrationform" method = "post" style="float:right;">
	       		<input type="hidden" name="userType" value="external">
	       		<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
	       		<button type="submit" class="btn btn-sm btn-primary">New Registration</button>
            </form>
            <h3>User Management</h3>
            <form class="form-margin" action = "searchexternaluser" method = "post">
            	<div class="col-md-3" style="padding-left:0px;">
            		<input class="form-control" type="text" name="customerID" placeholder="Customer ID" required>
            	</div>
	       		<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
	       		<button type="submit" class="btn btn-sm btn-primary">Search Customer</button>
            </form>
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">Search Results</h3>
                </div>
                <div class="panel-body no-padding">
                    <table id="content-table">
                        <thead>
                            <tr>
                                <th class="active">Customer ID</th>
                                <th class="active">Customer Email</th>
                                <th class="active">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<tr>
                        		<c:choose>
	                        		<c:when test="${customerObj.id == 0 || empty customerObj}">
	                        			<tr>
	                                    	<td colspan="3">No Results</td>
	                                	</tr>
	                        		</c:when>
                        		<c:otherwise>
                                		<tr>
                                			<td style="text-align:center">${customerObj.id}</td>
                                			<td style="text-align:center">${customerObj.email}</td>
                                			<td style="text-align:center">
                                				<form action = "viewaccountdetails" method = "post" style="display:inline">
		                                    		<input type="hidden" name="extUserID" value="${customerObj.id}">
		                                    		<input type="hidden" name="userType" value="external">
		                                    		<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
		                                    		<button type="submit" class="btn btn-sm btn-primary">View Account</button>
		                                    	</form>
		                                    	<form action = "viewtransaction" method = "post" style="display:inline">
		                                    		<input type="hidden" name="extUserID" value="${customerObj.id}">
		                                    		<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
		                                    		<button type="submit" class="btn btn-sm btn-primary">View Transactions</button>
		                                    	</form>
                                			</td>
                                		</tr>
                        		</c:otherwise>
                        	</c:choose>
                        		<td></td>
                        	</tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <h3>Authorization Management</h3>
            
            <br>
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">Pending Authorization</h3>
                </div>
                <div class="panel-body no-padding">
                    <table id="content-table">
                        <thead>
                            <tr>
                            	<th class="active">Employee ID</th>
                                <th class="active">Customer ID</th>
                                <th class="active">Authorization Type</th>
                                <th class="active">Action</th>
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
                                			<td style="text-align:center">${authorization.internal_userID}</td>
                                			<c:choose>
			                                	<c:when test="${authorization.auth_Type == 'registration'}">
			                                    	<td style="text-align:center">N/A</td>
			                                    </c:when>
			                                    <c:otherwise>
			                                    	<td style="text-align:center">${authorization.external_userID}</td>
			                                    </c:otherwise>
		                                    </c:choose>
                                    		<td style="text-align:center">${authorization.auth_Type}</td>
                                    		<td style="text-align:center">
                                    			<form action = "processauthorization" method = "post">
		                                    		<input type="hidden" name="transactionID" value="${authorization.auth_id}">
		                                    		<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
		                                    		<select id="requestType" name="requestType" required>
				       									<option value="">Select Type</option>
				          								<option value="approve">Approve</option>
				          								<option value="reject">Reject</option>
				       								</select>
		                                    		<button type="submit" class="btn btn-xs btn-primary">Submit</button>
		                                   		</form>
                                    		</td>
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
                            	<th class="active">Employee ID</th>
                                <th class="active">Customer ID</th>                               
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
		                                	<td style="text-align:center">${authorization.internal_userID}</td>
		                                	<c:choose>
			                                	<c:when test="${authorization.auth_Type == 'registration'}">
			                                    	<td style="text-align:center">N/A</td>
			                                    </c:when>
			                                    <c:otherwise>
			                                    	<td style="text-align:center">${authorization.external_userID}</td>
			                                    </c:otherwise>
		                                    </c:choose>
		                                    <td style="text-align:center">${authorization.auth_Type}</td>
		                                    <td style="text-align:center">
		                                    	<form action = "revokeauthorization" method = "post">
		                                    		<input type="hidden" name="authID" value="${authorization.auth_id}">
		                                    		<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
		                                    		<button type="submit" class="btn btn-sm btn-danger">Revoke Authorization</button>
		                                    	</form>
		                                    </td>
		                                </tr>
                            		</c:forEach>
                        		</c:otherwise>
                        	</c:choose>
                        </tbody>
                    </table>
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