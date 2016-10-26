<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="customerHeader.jsp"%>

<div class="content-wrapper">
	<div class="col-md-12" id="page-content">

		<h1>Credit Card Payment</h1>

		<div class="row">

			<c:if test="${not empty paymentResult}" >
				<c:if test="${paymentResult == '1'}">
				<script type="text/javascript">
    			confirm('Transaction is successful');
				</script>
				</c:if>
				
				<c:if test="${paymentResult == '0'}">
				<script type="text/javascript">
    			alert('Transaction Failed');
				</script>
				</c:if>
			</c:if>
			
			<form class="form-horizontal" action="makePayement" method='POST'
				onSubmit="return checkInputOr()">
		<div class="panel panel-warning">
			<div class="panel-heading">
				<h4 class="panel-title">Account</h4>
			</div>

			<div class="panel-body">
				<div class="row">
					<div class="col-sm-3">Next Payment Date:</div>
					<div class="col-sm-3">${creditAccount.dueDateTimestamp}</div>
				</div>

				<div class="row">
					<div class="col-sm-3">Out Standing Balance:</div>
					<div class="col-sm-3">${creditAccount.availBalance}</div>
				</div>
			</div>
			</div>
				<div class="form-group">

					<label for="inputAmount" class="col-sm-3 control-label">Enter
						the amount to pay</label>
						

					<div class="input-group" class="col-sm-4">
						<div class="input-group-addon">$</div>
						
						<input type="text" class="form-control" id="inputAmountField"
							placeholder="Amount" name="inputAmountField">
							
						<div class="input-group-addon">.00</div>
					</div>
				</div>
				<fieldset disabled>
				<div class="form-group">
					<label for="labelAccount" class="col-sm-3 control-label">Account</label>
					<div class="input-group" class="col-sm-5">
						<input type="text" class="form-control" id="inputAmount"
							placeholder="Checking Account (Balance: )" required>
					</div>
				</div>
				</fieldset>
				<div class="form-group">
					<div class="col-sm-3">
						<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
						<button type="submit" class="btn btn-default">Pay</button>
					</div>
				</div>
			</form>
		</div>
	</div>

</div>
<!-- .content-wrapper -->



<script type="text/javascript">
	$(document).ready(function() {
		sideNavigationSettings();
	});
</script>

</body>
</html>