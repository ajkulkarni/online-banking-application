<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="employeeHeader.jsp" %>

	<div class="content-wrapper">
		<div class="col-md-12" id="page-content">
			<c:if test="${not empty msg}">
			<div class="alert alert-dismissible alert-success">
  				<button type="button" class="close" data-dismiss="alert">&times;</button>
  				<strong>${msg}</strong>
			</div>
			</c:if>
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Customer Details</h3>
				</div>
				<div class="panel-body no-padding">
					<table id="content-table">
						<tbody>
							<tr>
								<td class="active" style="width:30%">Customer ID</td>
								<td>${extUserObj.id}</td>
							</tr>
							<tr>
								<td class="active">Name</td>
								<td>${extUserObj.name}</td>
							</tr>
							<tr>
								<td class="active">Email</td>
								<td>${extUserObj.email}</td>
							</tr>
							<tr>
								<td class="active">Phone</td>
								<td>${extUserObj.phone}</td>
							</tr>	
							<tr>
								<td class="active">Address</td>
								<td>${extUserObj.address}</td>
							</tr>
						</tbody>
					</table>
				</div>					
			</div>
			<c:if test="${role == 'ROLE_ADMIN' }">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">PII Information</h3>
				</div>
				<div class="panel-body no-padding">
					<table id="content-table">
						<thead>
							<tr>
								<th class="active">SSN</th>
								<th class="active">Date Of Birth</th>
							</tr>
						</thead>
						<tbody>
						<tr>
							<td>${extUserObj.ssn}</td>
							<td>${extUserObj.date_of_birth}</td>
						</tr>	
						</tbody>
					</table>
				</div>					
			</div>
			</c:if>
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Account Details</h3>
				</div>
				<div class="panel-body no-padding">
					<table id="content-table">
						<thead>
							<tr>
								<th class="active">Account Number</th>
								<th class="active">Account Type</th>
								<th class="active">Action</th>
							</tr>
						</thead>
						<tbody>
						<c:choose>
                        		<c:when test="${empty account_list}">
                        			<tr>
                                    	<td colspan="2">No Account Details</td>
                                	</tr>
                        		</c:when>
                        		<c:otherwise>
                        			<c:forEach items="${account_list}" var="row_value">
										<tr>
											<td style="text-align:center">${row_value.account_number}</td>
											<td style="text-align:center">${row_value.account_type}</td>
											<td style="text-align:center">
												<c:if test="${row_value.account_type == 'SAVINGS'}">
													<form action = "viewsavingsaccount" method = "post">
			                                  		<input type="hidden" name="extUserID" value="${extUserObj.id}">
			                                  		<input type="hidden" name="savingsPicker" value='last month'>
			                                  		<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
			                                  		<button type="submit" class="btn btn-xs btn-primary">Submit</button>
		                                 		</form>
												</c:if>
												<c:if test="${row_value.account_type == 'CHECKING'}">		
		                                 		<form action = "viewcheckingaccount" method = "post">
		                                  		<input type="hidden" name="extUserID" value="${extUserObj.id}">
		                                  		<input type="hidden" name="checkingPicker" value='last month'>
		                                  		<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
		                                  		<button type="submit" class="btn btn-xs btn-primary">Submit</button>
		                                 		</form>
		                                 		</c:if>		
											</td>
										</tr>
									</c:forEach>
                        		</c:otherwise>
                        	</c:choose>	
						</tbody>
					</table>
				</div>					
			</div>
			<c:if test="${role != 'ROLE_ADMIN'}">
				<a href="#modifyaccount" class="btn btn-primary btn-sm" data-toggle="modal">Modify Account</a>
				<form action = "deletecustomer" method = "post" style="float:right">
	                <input type="hidden" name="extUserID" value="${extUserObj.id}">
	                <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
	                <button type="submit" class="btn btn-sm btn-danger">Delete Account</button>
	            </form>
			</c:if>
			<c:if test="${role == 'ROLE_ADMIN' && userType == 'internal'}">
				<a href="#modifyaccount" class="btn btn-primary btn-sm" data-toggle="modal">Modify Account</a>
				<form action = "deleteemployee" method = "post" style="float:right">
	                <input type="hidden" name="UserID" value="${extUserObj.id}">
	                <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
	                <button type="submit" class="btn btn-sm btn-danger">Delete Account</button>
	            </form>
			</c:if>
		</div>
		<div class="modal" id="modifyaccount">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-body no-padding">
		        <div class="panel panel-success no-margin">
				  <div class="panel-heading">
				    <h3 class="panel-title">Edit Customer Details</h3>
				  </div>
				  <div class="panel-body">
				    <form class="form-horizontal" action="addrequest" method="POST">
				    	<fieldset>
				    		<div class="form-group">
				    			<label for="requestType" class="col-lg-3 control-label">Request Type</label>
			    				<div class="col-lg-9 form-margin">
       								<select class="form-control" name="requestType" required>
       									<option value="">Select Type</option>
          								<option value="phone">Phone Change</option>
          								<option value="address">Address Change</option>
       								</select>
       							</div>
       							<label for="newValue" class="col-lg-3 control-label">Enter</label>
      							<div class="col-lg-9">
        							<input type="text" class="form-control" name="newValue" placeholder="New Detail" required>
      							</div>
       							<input type="hidden" name="userID" value="${extUserObj.id}">	
       							<input type="hidden" name="userType" value="${userType}">				
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

</body>
</html>