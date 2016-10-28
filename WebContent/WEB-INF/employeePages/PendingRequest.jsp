<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="employeeHeader.jsp" %>

	<div class="content-wrapper">
		<div class="col-md-12" id="page-content">
			<c:if test="${not empty error_msg}">
			<div class="alert alert-dismissible alert-success">
  				<button type="button" class="close" data-dismiss="alert">&times;</button>
  				<strong>${error_msg}</strong>
			</div>
			</c:if>
			<h3>Pending Request Management</h3>
			<c:if test="${ role != 'ROLE_REGULAR'}">
				<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Internal Requests</h3>
				</div>
				<div class="panel-body no-padding" style="overflow-y: scroll; max-height:400px;">
					<table id="content-table">
						<thead>
							<tr>
								<th class="active">Request ID</th>
								<th class="active">Employee ID</th>
								<th class="active">Type</th>
								<th class="active">Current Value</th>
								<th class="active">Requested Value</th>
								<th class="active">Action</th>
							</tr>
						</thead>
						<tbody>
						<c:choose>
                        		<c:when test="${empty internal_list}">
                        			<tr>
                                    	<td colspan="2">No Pending Request</td>
                                	</tr>
                        		</c:when>
                        		<c:otherwise>
                        			<c:forEach items="${internal_list}" var="request">
										<tr>
											<td style="text-align:center">${request.id}</td>
											<td style="text-align:center">${request.requesterid}</td>
											<td style="text-align:center">${request.request_type}</td>
											<td style="text-align:center">${request.current_value}</td>
											<td style="text-align:center">${request.requested_value}</td>
											<td style="text-align:center">
											<form action = "processrequestinternal" method = "post">
		                                  		<input type="hidden" name="requestID" value="${request.id}">
		                                  		<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
		                                  		<select name="action" required>
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
			</c:if>
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">External Requests</h3>
				</div>
				<div class="panel-body no-padding" style="overflow-y: scroll; max-height:400px;">
					<table id="content-table">
						<thead>
							<tr>
								<th class="active">Request ID</th>
								<th class="active">Customer ID</th>
								<th class="active">Type</th>
								<th class="active">Current Value</th>
								<th class="active">Requested Value</th>
								<th class="active">Action</th>
							</tr>
						</thead>
						<tbody>
						<c:choose>
                        		<c:when test="${empty external_list}">
                        			<tr>
                                    	<td colspan="2">No Pending Request</td>
                                	</tr>
                        		</c:when>
                        		<c:otherwise>
                        			<c:forEach items="${external_list}" var="request">
										<tr>
											<td style="text-align:center">${request.id}</td>
											<td style="text-align:center">${request.requesterid}</td>
											<td style="text-align:center">${request.request_type}</td>
											<td style="text-align:center">${request.current_value}</td>
											<td style="text-align:center">${request.requested_value}</td>
											<td style="text-align:center">
											<form action = "processrequestexternal" method = "post">
		                                  		<input type="hidden" name="requestID" value="${request.id}">
		                                  		<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
		                                  		<select name="action" required>
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