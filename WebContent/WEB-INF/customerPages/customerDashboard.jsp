<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="customerHeader.jsp"%>

<div class="content-wrapper">
	<div class="col-md-12" id="page-content">
		<!-- <h1>Customer DashBoard</h1> -->
		<div class="panel panel-warning">
			<div class="panel-heading">
				<h3 class="panel-title">Checking Account</h3>
			</div>
			<div class="panel-body">
			
			<div class="row">
					<div class="col-sm-3">Checking Account Number:</div>
					<div class="col-sm-3">${cAccount.accountNumber}</div>
				</div>
				
				<div class="row">
					<div class="col-sm-3">Current Balance:</div>
					<div class="col-sm-3">${CheckingAccBal}</div>
					
					<hr>
					<center><b>Last 5 transactions</b></center>
					<hr>
				</div>
				<br>
				<table class="table table-striped">
					<thead>
						<tr>
							<th>#</th>
							<th>Description</th>
							<th>Payee</th>
							<th>Amount</th>
							<th>Date</th>
						</tr>
					</thead>
					<tbody>


						<c:forEach var="trans" items="${cAccount.transactionList}" varStatus="loop">

							<tr>
								<th scope="row">${loop.index + 1}</th>
								<td>${trans.description}</td>
								<td>${trans.payee}</td>
								<td>${trans.amount}</td>
								<td>${trans.date}</td>
							</tr>
						</c:forEach>


					</tbody>
				</table>
				
			</div>
		</div>
		<div class="panel panel-success">
			<div class="panel-heading">
				<h3 class="panel-title">Saving Account</h3>
			</div>
			<div class="panel-body">
			<div class="row">
					<div class="col-sm-3">Saving Account Number:</div>
					<div class="col-sm-3">${sAccount.accountNumber}</div>
				</div>
				<div class="row">
					<div class="col-sm-3">Current Balance:</div>
					<div class="col-sm-3">${SavingsAccBal}</div>
										<hr>
					<center><b>Last 5 transactions</b></center>
					<hr>
				</div>
				
				<table class="table table-striped">
					<thead>
						<tr>
							<th>#</th>
							<th>Description</th>
							<th>Payee</th>
							<th>Amount</th>
							<th>Date</th>
						</tr>
					</thead>
					<tbody>


						<c:forEach var="trans" items="${sAccount.transactionList}" varStatus="loop">

							<tr>
								<th scope="row">${loop.index + 1}</th>
								<td>${trans.description}</td>
								<td>${trans.payee}</td>
								<td>${trans.amount}</td>
								<td>${trans.date}</td>
							</tr>
						</c:forEach>


					</tbody>
				</table>
				
			</div>
		</div>
		
		
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Credit card</h3>
			</div>
			<div class="panel-body">
			
			<div class="row">
					<div class="col-sm-3">Credit Card Account Number:</div>
					<div class="col-sm-3">${ccAccount.accountNumber}</div>
				</div>
				<div class="row">
					<div class="col-sm-3">Current Balance:</div>
					<div class="col-sm-3">${min_payment_due}</div>
										<hr>
					<center><b>Last 5 transactions</b></center>
					<hr>
				</div>

				<table class="table table-striped">
					<thead>
						<tr>
							<th>#</th>
							<th>Description</th>
							<th>Payee</th>
							<th>Amount</th>
							<th>Date</th>
						</tr>
					</thead>
					<tbody>


						<c:forEach var="trans" items="${ccAccount.transactionList}" varStatus="loop">

							<tr>
								<th scope="row">${loop.index + 1}</th>
								<td>${trans.description}</td>
								<td>${trans.payee}</td>
								<td>${trans.amount}</td>
								<td>${trans.date}</td>
							</tr>
						</c:forEach>


					</tbody>
				</table>

			</div>
		</div>
	</div>
</div>
<!-- .content-wrapper -->

</main>

<script type="text/javascript">
	$(document).ready(function() {
		sideNavigationSettings();
	});
</script>

</body>
</html>