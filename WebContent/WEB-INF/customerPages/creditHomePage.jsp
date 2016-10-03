<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="creditHeader.jsp" %>

	<div class="content-wrapper">
		<div class="col-md-12" id="page-content">
			<h1>Credit Card DashBoard</h1>
			
			<div class="panel panel-warning">
				<div class="panel-heading">
					<h3 class="panel-title">Payment Info</h3>
				</div>
				<div class="panel-body">
					<div class="row">
					<div class="col-sm-3">Min Payment Due:</div>
					<div class="col-sm-3">${min_payment_due}</div>
				</div>
				
				<div class="row">
					<div class="col-sm-3">Next Payment Date:</div>
					<div class="col-sm-3">${next_payment_date}</div>
				</div>
				
				<div class="row">
					<div class="col-sm-3">Last Payment Amount:</div>
					<div class="col-sm-3">${last_payment_amount}</div>
				</div>
				
				<div class="row">
					<div class="col-sm-3">Last Payment Date:</div>
					<div class="col-sm-3">${last_payment_date}</div>
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
					<div class="col-sm-3">${out_standing_balance}</div>
				</div>
				
				<div class="row">
					<div class="col-sm-3">Pending Charges:</div>
					<div class="col-sm-3">${pending_charges}</div>
				</div>
				
				<div class="row">
					<div class="col-sm-3">Total Credit Limit:</div>
					<div class="col-sm-3">${total_credit_limit}</div>
				</div>
				
				<div class="row">
					<div class="col-sm-3">Available Credit:</div>
					<div class="col-sm-3">${avail_credit}</div>
				</div>
				
				<div class="row">
					<div class="col-sm-3">Billing Cycle Date:</div>
					<div class="col-sm-3">${billing_cycle_date}</div>
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
					<div class="col-sm-3">${cash_advance_balance}</div>
				</div>
				
				
				<div class="row">
					<div class="col-sm-3">Cash Advance Limit:</div>
					<div class="col-sm-3">${cash_advance_limit}</div>
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
					<div class="col-sm-3">${apr_now}</div>
				</div>
				<div class="row">
					<div class="col-sm-3">Cash APR:</div>
					<div class="col-sm-3">${cash_apr}</div>
				</div>
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