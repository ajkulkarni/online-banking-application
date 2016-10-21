<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="customerHeader.jsp" %>

	<div class="content-wrapper">
		<div class="col-md-12" id="page-content">
			<h1>Credit Card DashBoard</h1>
			
			<div class="panel panel-warning">
				<div class="panel-heading">
					<h3 class="panel-title">Payment Info</h3>
				</div>
				
			<div class="panel-body">
				<div class="row">
					<div class="col-sm-3">Next Payment Date:</div>
					<div class="col-sm-3">${creditAccount.dueDateTimestamp}</div>
				</div>
				
				<div class="row">
					<div class="col-sm-3">Last Payment Amount:</div>
					<div class="col-sm-3">${creditAccount.lastBillAmount}</div>
				</div>
				
				<div class="row">
					<div class="col-sm-3">Last Payment Date:</div>
					<div class="col-sm-3">NA</div>
				</div>
				
				</div>
			</div>
			
			<div class="panel panel-success">
				<div class="panel-heading">
					<h3 class="panel-title">Account Info</h3>
				</div>
			<div class="panel-body">
				
				<div class="row">
					<div class="col-sm-3">Out Standing Balance:</div>
					<div class="col-sm-3">${creditAccount.availBalance}</div>
				</div>
				
				<div class="row">
					<div class="col-sm-3">Pending Charges:</div>
					<div class="col-sm-3">NA</div>
				</div>
				
				<div class="row">
					<div class="col-sm-3">Total Credit Limit:</div>
					<div class="col-sm-3">NA</div>
				</div>
				
				<div class="row">
					<div class="col-sm-3">Available Credit:</div>
					<div class="col-sm-3">${creditAccount.availBalance}</div>
				</div>
				
				<div class="row">
					<div class="col-sm-3">Billing Cycle Date:</div>
					<div class="col-sm-3">NA</div>
				</div>
				
			</div>
			
		</div>
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Cash Advance</h3>
				</div>
				<div class="panel-body">
				<div class="row">
					<div class="col-sm-3">Cash Advance Balance:</div>
					<div class="col-sm-3">NA</div>
				</div>
				
				
				<div class="row">
					<div class="col-sm-3">Cash Advance Limit:</div>
					<div class="col-sm-3">NA</div>
				</div>
				
				</div>
			</div>
			
			<div class="panel panel-warning">
				<div class="panel-heading">
					<h3 class="panel-title">APR</h3>
				</div>
				<div class="panel-body">
				<div class="row">
					<div class="col-sm-3">Now:</div>
					<div class="col-sm-3">${creditAccount.apr}</div>
				</div>
				<div class="row">
					<div class="col-sm-3">Cash APR:</div>
					<div class="col-sm-3">${creditAccount.apr}</div>
				</div>
				</div>
			</div>
			<h1>Credit Card Statement</h1>

		<table class="table table-striped">
			<thead>
				<tr>
					<th>#</th>
					<th>Description</th>
					<th>Payee</th>
					<th>Payer</th>
					<th>Amount</th>
					<th>Date</th>
					<th>Transaction Status</th>
				</tr>
			</thead>
			<tbody>


				<c:forEach var="trans" items="${transations}" varStatus="loop">

					<tr>
						<th scope="row">${loop.index + 1}</th>
						<td>${trans.description}</td>
						<td>${trans.payee_id}</td>
						<td>${trans.payer_id}</th>
						<td>${trans.amount}</td>
						<td>${trans.timestamp_updated}</td>
						<td>${trans.pendingStrg}</td>
					</tr>
				</c:forEach>


			</tbody>
		</table>
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