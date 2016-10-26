<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="customerHeader.jsp" %>

	<div class="content-wrapper">
		<div class="col-md-12" id="page-content">
			<c:if test="${not empty error_msg}">
			<div class="alert alert-dismissible alert-success">
  				<button type="button" class="close" data-dismiss="alert">&times;</button>
  				<strong>${error_msg}</strong>
			</div>
			</c:if>
			<h3>Pending Request Management</h3>
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Your Requests</h3>
				</div>
				<div class="panel-body no-padding">
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