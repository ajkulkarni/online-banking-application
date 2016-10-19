<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="customerHeader.jsp"%>

<div class="content-wrapper">
	<div class="col-md-12" id="transferConfirmation">
		<c:if test="${success ==true }">
			<div class="container" id="transferSuccess">
				<div class="alert alert-dismissible alert-success">
					<strong>Your payment was successful!</strong>
				</div>
				<div class="panel panel-success" id="transferSuccessPanel">
					<div class="panel-heading">
						<h3 class="panel-title">Transfer Information</h3>
						<div class="form-group">
							<label for="tfrPayeeAccount" class="col-lg-2 control-label">Transfer
								To : </label> <label id="tfrPayeeAccount" class="col-lg-5 control-label">${payee_info}</label>
						</div>
						<div class="form-group">
							<label for="tfrAmount" class="col-lg-2 control-label">Amount
								: </label> <label id="tfrAmount" class="col-lg-5 control-label">${Amount}
							</label>
						</div>
					</div>
					<div class="panel-body">Panel content</div>
				</div>

			</div>
		</c:if>
		<c:if test="${ success == false }">
			<div class="container" id="transferRejected">
				<div class="alert alert-dismissible alert-warning">
					<h2 id="transferRejectMsg">
						<strong>Sorry! Your payment was rejected.</strong> <strong>${error_msg}</strong>
					</h2>
				</div>
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