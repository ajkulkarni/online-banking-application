<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="employeeHeader.jsp" %>

	<div class="content-wrapper">
		<div class="col-md-12" id="page-content">
			<h4>Transactions</h4>
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Pending Transaction Requests</h3>
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
							<%-- <c:forEach items="${request_list}" var="request">
								<tr>
									<td>${request.id}</td>
									<td>${request.request_type}</td>
									<td>${request.current_value}</td>
									<td>${request.requested_value}</td>
									<td>${request.status}</td>
									<td>${request.timestamp_created}</td>
									<td>${request.approver}</td>
							</tr>
							</c:forEach> --%>	
						</tbody>
					</table>
				</div>
			</div>
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Complete Transactions Requests</h3>
				</div>
				<div class="panel-body no-padding">
					<table id="content-table">
						<thead>
							<tr>
								<th class="active">ID</th>
								<th class="active">Sender Account</th>
								<th class="active">Receiver Account</th>
								<th class="active">Amount</th>
								<th class="active">Status</th>
								<th class="active">Approver</th>
							</tr>
						</thead>
						<tbody>
							<%-- <c:forEach items="${request_list}" var="request">
								<tr>
									<td>${request.id}</td>
									<td>${request.request_type}</td>
									<td>${request.current_value}</td>
									<td>${request.requested_value}</td>
									<td>${request.status}</td>
									<td>${request.timestamp_created}</td>
									<td>${request.approver}</td>
							</tr>
							</c:forEach> --%>	
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