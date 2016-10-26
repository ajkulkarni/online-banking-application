<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="employeeHeader.jsp" %>

	<div class="content-wrapper">
		<div class="col-md-12" id="page-content">
			<h3>Employee DashBoard</h3>
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Pending requests statistics</h3>
				</div>
				<div class="panel-body no-padding">
					<table id="content-table">
						<thead>
							<tr>
								<th class="active">Requests</th>
								<th class="active">Count</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${role == 'ROLE_MANAGER' || role == 'ROLE_ADMIN'}">
							<tr>
								<td>Internal User Requests</td>
								<td>${internal_count}</td>
							</tr>
							</c:if>
							<c:if test="${role != 'ROLE_ADMIN'}">
							<tr>
								<td>External User Requests</td>
								<td>${external_count}</td>
							</tr>
							<tr>
								<td>Transaction Requests</td>
								<td>${transaction_count}</td>
							</tr>
							</c:if>
						</tbody>
					</table>
				</div>
			</div>
			<c:if test="${role == 'ROLE_MANAGER' || role == 'ROLE_ADMIN'}">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title">Pending Internal Requests</h3>
					</div>
					<div class="panel-body no-padding">
						<table id="content-table">
							<thead>
								<tr>
									<th class="active">ID</th>
									<th class="active">Type</th>
									<th class="active">Current Value</th>
									<th class="active">Requested Value</th>
								</tr>
							</thead>
							<tbody>
							<c:choose>
                        		<c:when test="${empty internal_list}">
                        			<tr>
                                    	<td colspan="5">No Internal Request Pending</td>
                                	</tr>
                        		</c:when>
                        		<c:otherwise>
                        			<c:forEach items="${internal_list}" var="request">
										<tr>
											<td>${request.id}</td>
											<td>${request.request_type}</td>
											<td>${request.current_value}</td>
											<td>${request.requested_value}</td>
										</tr>
									</c:forEach>
                        		</c:otherwise>
							</c:choose>
							</tbody>
						</table>
					</div>
				</div>
			</c:if>
			<c:if test="${role != 'ROLE_ADMIN'}">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Pending External Request</h3>
				</div>
				<div class="panel-body no-padding">
					
					<table id="content-table">
						<thead>
							<tr>
								<th class="active">ID</th>
								<th class="active">Type</th>
								<th class="active">Current Value</th>
								<th class="active">Requested Value</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
                        		<c:when test="${empty external_list}">
                        			<tr>
                                    	<td colspan="5">No External Request Pending</td>
                                	</tr>
                        		</c:when>
                        		<c:otherwise>
                        			<c:forEach items="${external_list}" var="request">
										<tr>
											<td>${request.id}</td>
											<td>${request.request_type}</td>
											<td>${request.current_value}</td>
											<td>${request.requested_value}</td>
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
					<h3 class="panel-title">Pending Transactions</h3>
				</div>
				<div class="panel-body no-padding">
					
					<table id="content-table">
						<thead>
							<tr>
								<th class="active">ID</th>
								<th class="active">Sender Account</th>
								<th class="active">Receiver Account</th>
								<th class="active">Amount</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
                        		<c:when test="${empty transaction_list}">
                        			<tr>
                                    	<td colspan="4">No Transaction Pending</td>
                                	</tr>
                        		</c:when>
                        		<c:otherwise>
                        			<c:forEach items="${transaction_list}" var="transaction">
										<tr>
											<td>${transaction.id}</td>
											<td>${transaction.payer_id}</td>
											<td>${transaction.payee_id}</td>
											<td>${transaction.amount}</td>
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
	</div> <!-- .content-wrapper -->
	
</main> 

<script type="text/javascript">
	$(document).ready(function() {
		sideNavigationSettings();
	});
	</script>

</body>
</html>