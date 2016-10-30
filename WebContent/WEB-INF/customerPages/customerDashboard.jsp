<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="customerHeader.jsp"%>

<div class="content-wrapper">
	<div class="col-md-12" id="page-content">
		<!-- <h1>Customer DashBoard</h1>--> 
		<div class="panel panel-warning">
			<div class="panel-heading">
				<h3 class="panel-title">Checking Account</h3>
			</div>
			<div class="panel-body">
			
			<div class="row">
					<div class="col-sm-3">Checking Account Number:</div>
					<div class="col-sm-3">${checkingAccount.account_number}</div>
				</div>
				
				<div class="row">
					<div class="col-sm-3">Current Balance:</div>
					<div class="col-sm-3">${checkingAccount.balance}</div>
					<hr>
					<center><b>Last 5 transactions</b></center>
					<hr>
				</div>
				<br>
				<table class="table table-striped">
					<thead>
						<tr>
                                    <th class="col-xs-1">#</th>
                                    <th class="col-xs-4">Description</th>
                                    <th class="col-xs-1">Transaction Type</th>
                                    <th class="col-xs-1">Payee</th>
                                    <th class="col-xs-2">Amount</th>
                                    <th class="col-xs-2">Status</th>       
                                    <th class="col-xs-3">Date</th>    
						</tr>
					</thead>
					<tbody>


						<c:forEach var="trans" items="${TransactionLinesCH}" varStatus="loop">

							<tr>
								<th scope="row">${loop.index + 1}</th>
								<td>${trans.description}</td>
								<c:set var="account_number" scope="session" value="${checkingAccount.account_number}"/>
                                        <c:set var="description" scope="session" value="${trans.description}"/>                                     
                                        <c:set var="payee_id" scope="session" value="${trans.payee_id}"/>
                                        <c:set var="payer_id" scope="session" value="${trans.payer_id}"/>
                                        <c:set var="depo_desc" scope="session" value="Deposit money to account"/>
                                        <c:set var="with_desc" scope="session" value="Withdrawal of money from account"/> 
                                                                               
                                    	<c:choose>
                                    		<c:when test="${payer_id!=payee_id }">
                                    			<c:choose>
  													<c:when test="${account_number==payee_id}">
  														<td>CREDIT</td>
  													</c:when>
  													<c:otherwise>
  														<td>DEBIT</td>
  													</c:otherwise>
  												</c:choose>
  											</c:when>
  											<c:otherwise>
  												<c:choose>
  													<c:when test="${description==depo_desc}">
  														<td>CREDIT</td>
													</c:when>
													<c:otherwise>
														<td>DEBIT</td>  
													</c:otherwise>
												</c:choose>														
  											</c:otherwise>
										</c:choose>
                                    
								<td>${trans.payee_id}</td>
								<td>${trans.amount}</td>
								<td>${trans.status}</td>
								<td>${trans.timestamp_updated}</td>
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
					<div class="col-sm-3">${savingsAccount.account_number}</div>
				</div>
				<div class="row">
					<div class="col-sm-3">Current Balance:</div>
					<div class="col-sm-3">${savingsAccount.balance}</div>
										<hr>
					<center><b>Last 5 transactions</b></center>
					<hr>
				</div>
				
				<table class="table table-striped">
					<thead>
						<tr>
                                    <th class="col-xs-1">#</th>
                                    <th class="col-xs-4">Description</th>
                                    <th class="col-xs-1">Transaction Type</th>
                                    <th class="col-xs-1">Payee</th>
                                    <th class="col-xs-2">Amount</th>
                                    <th class="col-xs-2">Status</th>       
                                    <th class="col-xs-3">Date</th>    
						</tr>
					</thead>
					<tbody>


						<c:forEach var="trans" items="${TransactionLinesSV}" varStatus="loop">

							<tr>
								<th scope="row">${loop.index + 1}</th>
								<td>${trans.description}</td>
								<c:set var="account_number" scope="session" value="${checkingAccount.account_number}"/>
                                        <c:set var="description" scope="session" value="${trans.description}"/>                                     
                                        <c:set var="payee_id" scope="session" value="${trans.payee_id}"/>
                                        <c:set var="payer_id" scope="session" value="${trans.payer_id}"/>
                                        <c:set var="depo_desc" scope="session" value="Deposit money to account"/>
                                        <c:set var="with_desc" scope="session" value="Withdrawal of money from account"/> 
                                                                               
                                    	<c:choose>
                                    		<c:when test="${payer_id!=payee_id }">
                                    			<c:choose>
  													<c:when test="${account_number==payee_id}">
  														<td>CREDIT</td>
  													</c:when>
  													<c:otherwise>
  														<td>DEBIT</td>
  													</c:otherwise>
  												</c:choose>
  											</c:when>
  											<c:otherwise>
  												<c:choose>
  													<c:when test="${description==depo_desc}">
  														<td>CREDIT</td>
													</c:when>
													<c:otherwise>
														<td>DEBIT</td>  
													</c:otherwise>
												</c:choose>														
  											</c:otherwise>
										</c:choose>
                                    
								<td>${trans.payee_id}</td>
								<td>${trans.amount}</td>
								<td>${trans.status}</td>
								<td>${trans.timestamp_updated}</td>
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
					<div class="col-sm-3">${creditCardAccount.account_number}</div>
				</div>
				<div class="row">
					<div class="col-sm-3">Current Balance:</div>
					<div class="col-sm-3">${creditCardAccount.balance}</div>
										<hr>
					<center><b>Last 5 transactions</b></center>
					<hr>
				</div>

				<table class="table table-striped">
					<thead>
						<tr>
                                    <th class="col-xs-1">#</th>
                                    <th class="col-xs-4">Description</th>
                                    <th class="col-xs-1">Transaction Type</th>
                                    <th class="col-xs-1">Payee</th>
                                    <th class="col-xs-2">Amount</th>
                                    <th class="col-xs-2">Status</th>       
                                    <th class="col-xs-3">Date</th>    
						</tr>
					</thead>
					<tbody>


						<c:forEach var="trans" items="${TransactionLinesCC}" varStatus="loop">

							<tr>
								<th scope="row">${loop.index + 1}</th>
								<td>${trans.description}</td>
								<c:set var="account_number" scope="session" value="${checkingAccount.account_number}"/>
                                        <c:set var="description" scope="session" value="${trans.description}"/>                                     
                                        <c:set var="payee_id" scope="session" value="${trans.payee_id}"/>
                                        <c:set var="payer_id" scope="session" value="${trans.payer_id}"/>
                                        <c:set var="depo_desc" scope="session" value="Deposit money to account"/>
                                        <c:set var="with_desc" scope="session" value="Withdrawal of money from account"/> 
                                                                               
                                    	<c:choose>
                                    		<c:when test="${payer_id!=payee_id }">
                                    			<c:choose>
  													<c:when test="${account_number==payee_id}">
  														<td>CREDIT</td>
  													</c:when>
  													<c:otherwise>
  														<td>DEBIT</td>
  													</c:otherwise>
  												</c:choose>
  											</c:when>
  											<c:otherwise>
  												<c:choose>
  													<c:when test="${description==depo_desc}">
  														<td>CREDIT</td>
													</c:when>
													<c:otherwise>
														<td>DEBIT</td>  
													</c:otherwise>
												</c:choose>														
  											</c:otherwise>
										</c:choose>
                                    
								<td>${trans.payee_id}</td>
								<td>${trans.amount}</td>
								<td>${trans.status}</td>
								<td>${trans.timestamp_updated}</td>
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