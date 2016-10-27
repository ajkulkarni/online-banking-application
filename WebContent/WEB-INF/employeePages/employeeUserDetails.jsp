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
			<c:if test="${role != 'ROLE_ADMIN' }">
			<a href="#modifyaccount" class="btn btn-primary btn-sm" style="margin-bottom:20px;" data-toggle="modal">Modify Account</a>
			<a href="#changepassword" class="btn btn-primary btn-sm" style="margin-left: 15px;margin-bottom:20px;" data-toggle="modal">Change Password</a>
			</c:if>
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">User Details</h3>
				</div>
				<div class="panel-body no-padding">
					<table id="content-table">
						<tbody>
							<tr>
								<td class="active" style="width:30%">User ID</td>
								<td>${user.id}</td>
							</tr>
							<tr>
								<td class="active">Name</td>
								<td>${user.name}</td>
							</tr>
							<tr>
								<td class="active">Designation</td>
								<td>${user.designation}</td>
							</tr>
							<tr>
								<td class="active">Email</td>
								<td>${user.email}</td>
							</tr>
							<tr>
								<td class="active">Phone</td>
								<td>${user.phone}</td>
							</tr>	
							<tr>
								<td class="active">Address</td>
								<td>${user.address}</td>
							</tr>
						</tbody>
					</table>
				</div>					
			</div>
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
							<td>${user.ssn}</td>
							<td>${user.date_of_birth}</td>
						</tr>	
						</tbody>
					</table>
				</div>					
			</div>
			<c:if test="${role != 'ROLE_ADMIN' }">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Your Requests History</h3>
				</div>
				<div class="panel-body no-padding">
					<table id="content-table">
						<thead>
							<tr>
								<th class="active">Request ID</th>
								<th class="active">Type</th>
								<th class="active">Current Value</th>
								<th class="active">Requested Value</th>
								<th class="active">Status</th>
								<th class="active">Approver</th>
							</tr>
						</thead>
						<tbody>
						<c:choose>
                        		<c:when test="${empty request_list}">
                        			<tr>
                                    	<td colspan="2">No Request</td>
                                	</tr>
                        		</c:when>
                        		<c:otherwise>
                        			<c:forEach items="${request_list}" var="request">
										<tr>
											<td style="text-align:center">${request.id}</td>
											<td style="text-align:center">${request.request_type}</td>
											<td style="text-align:center">${request.current_value}</td>
											<td style="text-align:center">${request.requested_value}</td>
											<td style="text-align:center">${request.status}</td>
											<c:if test="${request.status == 'Pending'}">
												<td style="text-align:center"></td>
											</c:if>
											<c:if test="${request.status != 'Pending'}">
												<td style="text-align:center">${request.approver}</td>
											</c:if>
										</tr>
									</c:forEach>
                        		</c:otherwise>
                        	</c:choose>	
						</tbody>
					</table>
				</div>
			</div>
			</c:if>
		</div>
		<div class="modal" id="modifyaccount">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-body no-padding">
		        <div class="panel panel-success no-margin">
				  <div class="panel-heading">
				    <h3 class="panel-title">Edit Account</h3>
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
       							<input type="hidden" name="userID" value="${userID}">	
       							<input type="hidden" name="userType" value="internal">				
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
		<div class="modal" id="changepassword">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-body no-padding">
		        <div class="panel panel-success no-margin">
				  <div class="panel-heading">
				    <h3 class="panel-title">Change Password</h3>
				  </div>
				  <div class="panel-body">
				    <form class="form-horizontal" action="changepassword" method="POST">
				    	<fieldset>
				    		<div class="form-group">
       							<label for="oldpassword" class="col-lg-3 control-label">Old Password</label>
      							<div class="col-lg-9 form-margin">
        							<input type="password" class="form-control" name="oldpassword" placeholder="Old Password" required>
      							</div>
      							<label for="newpassword" class="col-lg-3 control-label">New Password</label>
      							<div class="col-lg-9 form-margin">
        							<input type="password" class="form-control" name="newpassword" placeholder="New Password" required>
      							</div>
      							<label for="confirmpassword" class="col-lg-3 control-label">Confirm</label>
      							<div class="col-lg-9 form-margin">
        							<input type="password" class="form-control" name="confirmpassword" placeholder="Confirm Password" required>
      							</div>
       							<input type="hidden" name="userID" value="${userID}">					
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