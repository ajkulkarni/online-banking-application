<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="customerHeader.jsp"%>
<div class="content-wrapper">

	<div class="col-md-12" id="transferConfirmation">
		<c:if test="${success ==true }">
			<div class="container" id="transferSuccess" class="col-lg-12">
				<div class="alert alert-dismissible alert-success col-lg-6">
					<strong>Your payment was successful!</strong>
				</div>
			</div>


			<div class="panel panel-success" id="transferSuccessPanel">
				<div class="panel-heading">
					<h3 class="panel-title">Transfer Information</h3>
				</div>
				<div class="panel-body">
					<form class="form-horizontal">
						<fieldset>
							<div class="form-group">

								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
								<div class="col-lg-12">
									<p class="col-lg-2">
										<b>Transfer To : </b>
									</p>
									<p>${payee_info}</p>
								</div>
							</div>
							<div class="form-group">
								<div class="col-lg-12">
									<p class="col-lg-2">
										<b>Transfer From : </b>
									</p>
									<p>${payer_info}</p>
								</div>
							</div>
							<div class="form-group">
								<div class="col-lg-12">
									<p class="col-lg-2">
										<b>Amount : </b>
									</p>
									<p>${Amount}</p>
								</div>
							</div>
						</fieldset>
					</form>

				</div>


			</div>

			<div class="container" class="col-lg-12">
				<form class="form-horizontal" action="transferfunds" method='POST'>
					<fieldset>
						<div class="form-group">
							<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
							<div class="col-lg-5 input-group">
								<button type="submit" class="btn btn-primary">Make
									Another Payment</button>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
		</c:if>
		<c:if test="${ success == false }">
			<div class="container col-lg-12" id="transferRejected">
				<div class="alert alert-dismissible alert-warning">
					<h4 id="transferRejectMsg">
						<strong>Sorry! Your payment was rejected.</strong> <strong>${error_msg}</strong>
					</h4>
				</div>
			</div>
			<div class="container col-lg-12">
				<form class="form-horizontal" action="transferfunds" method='POST'>
					<fieldset>
						<div class="form-group">
							<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
							<div class="col-lg-5 input-group">
								<button type="submit" class="btn btn-primary">Make
									Another Payment</button>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
		</c:if>
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