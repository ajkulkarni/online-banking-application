<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="employeeHeader.jsp" %>

	<div class="content-wrapper">
		<div class="col-md-12" id="page-content">
			<h4>Transactions</h4>
			
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Filters</h3>
				</div>
				<div class="panel-body">
					<div class="col-lg-7">
						<form class="form-horizontal" action="transactionreject" method='POST' onSubmit="return checkInputOr()">
							<label for="requestID" class="col-lg-2 control-label">Transaction ID : </label>
							<div class="col-lg-10">
		       					<input type="text" class="form-control" name="transactionID" placeholder="Transaction ID">
		      				</div>
		      				<br><br>
		      				<label class="col-lg-2 control-label">OR</label>
		      				<br><br>
		      				<label for="userID" class="col-lg-2 control-label">Account No : </label>
							<div class="col-lg-10">
		       					<input type="text" class="form-control" name="accNo" placeholder="Account Number">
		      				</div>
		      				<br><br>
		      				<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
						    <button type="submit" class="btn btn-primary">Submit</button>
						</form>
					</div>
				</div>
			</div>
			
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
							<c:forEach items="${pending_list}" var="transaction">
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
							<c:forEach items="${complete_list}" var="transaction">
								<tr>
									<td>${transaction.id}</td>
									<td>${transaction.payer_id}</td>
									<td>${transaction.payee_id}</td>
									<td>${transaction.amount}</td>
									<td>${transaction.status}</td>
									<td>${transaction.approver}</td>
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