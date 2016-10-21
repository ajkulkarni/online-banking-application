<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="customerHeader.jsp"%>

<div class="content-wrapper">
	<div class="col-md-12" id="page-content">
		<h1>Credit Card Payment</h1>
		<div class="row">
			

			<form class="form-horizontal">
				<div class="form-group">
					<label for="inputEmail3" class="col-sm-3 control-label">Enter the amount to pay</label>
					<label class="sr-only" for="exampleInputAmount">Amount (in dollars)</label>
					<div class="input-group">
						<div class="input-group-addon">$</div>
						<input type="text" class="form-control" id="inputAmount"
							placeholder="Amount">
						<div class="input-group-addon">.00</div>
					</div>
				</div>
				<div class="form-group">
					<label for="inputPassword3" class="col-sm-3 control-label">Select the account</label>
					<div class="col-sm-7">
						<input type="text" class="form-control" id="inputPassword3"
							placeholder="Password">
					</div>
				</div>
				
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" class="btn btn-default">Pay</button>
					</div>
				</div>
			</form>
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