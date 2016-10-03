<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="creditHeader.jsp" %>

	<div class="content-wrapper">
		<div class="col-md-12" id="page-content">
			<h1>Credit Card Statement</h1>
			
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