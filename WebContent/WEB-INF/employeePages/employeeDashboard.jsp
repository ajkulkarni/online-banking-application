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
							<tr>
								<td>Account Status Requests</td>
								<td></td>
							</tr>
							<tr>
								<td>Internal User Requests</td>
								<td></td>
							</tr>
							<tr>
								<td>External User Requests</td>
								<td>${request_count}</td>
							</tr>
							<tr>
								<td>Transaction Requests</td>
								<td>${transaction_count}</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<%-- <div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Pending Account Registrations</h3>
				</div>
				<div class="panel-body no-padding">
					<table id="content-table">
						<thead>
							<tr>
								<th class="active">Email</th>
								<th class="active">Name</th>
								<th class="active">Address</th>
								<th class="active">Phone Number</th>
								<th class="active">Date</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${user_list}" var="user">
								<tr>
									<td>${user.userEmail}</td>
									<td>${user.firstName} ${user.lastName}</td>
									<td>${user.street} ${user.house} ${user.city} ${user.state} ${user.country}</td>
									<td>${user.userPhonenumber}</td>
									<td>${user.timestamp_created}</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div> --%>
			<%-- <div class="panel panel-primary">
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
								<th class="active">Creation Time</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${internal_list}" var="request">
								<tr>
									<td>${request.id}</td>
									<td>${request.request_type}</td>
									<td>${request.current_value}</td>
									<td>${request.requested_value}</td>
									<td>${request.timestamp_created}</td>
							</tr>
							</c:forEach>	
						</tbody>
					</table>
				</div>
			</div> --%>
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
								<th class="active">Creation Time</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${external_list}" var="request">
								<tr>
									<td>${request.id}</td>
									<td>${request.request_type}</td>
									<td>${request.current_value}</td>
									<td>${request.requested_value}</td>
									<td>${request.timestamp_created}</td>
							</tr>
							</c:forEach>	
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
							<c:forEach items="${transaction_list}" var="transaction">
								<tr>
									<td>${transaction.id}</td>
									<td>${transaction.payer_id}</td>
									<td>${transaction.payee_id}</td>
									<td>${transaction.amount}</td>
							</tr>
							</c:forEach>
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