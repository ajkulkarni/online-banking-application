<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="customerHeader.jsp"%>

<script>
	function validateEmailPhone() {

		console.log("valdate");

		var amount = document.getElementById("eptpinputAmount").value;
		console.log(amount);

		var mode = $("#eptpModeOfTransfer").val();
		var modeValue = $("#eptpinputMode").val();
		var payer = $("#eptpselectPayerAccount").val();

		var validInput = true;
		//validate Amount
		if (isNaN(amount) || amount < 0) {
			/* document.getElementById("etpinputAmount").placeholder="Enter Numeric value only";
			 */$("#eptpinputAmount").val('');
			$("#eptpinputAmount").attr("placeholder", "Enter valid Amount");
			console.log("error");
			validInput = false;
		}

		if (mode == "Select Mode of Transfer") {
			$("#eptpModeOfTransfer").val('Select Mode of Transfer');
			validInput = false;
		}

		if (modeValue == "") {
			$("#eptpinputMode").val('');
			$("#eptpinputMode")
					.attr("placeholder", "Enter Payee's Email/Phone");
			validInput = false;
		}

		if (payer == "Select Account") {
			$("#eptpselectPayerAccount").val('Select Account');
			validInput = false;
		}

		return validInput;
	}

	function validateExternal() {
		var amount = document.getElementById("etpinputAmount").value;
		var payer = $("#etpselectPayerAccount").val();
		var payee = $("#etpselectPayeeAccount").val();

		var validInput = true;
		if (isNaN(amount) || amount < 0) {
			$("#etpinputAmount").val('');
			$("#etpinputAmount").attr("placeholder", "Enter valid Amount");
			console.log("error");
			validInput = false;
		}

		if (payer == "Select Account") {
			$("#etpselectPayerAccount").val('Select Account');
			validInput = false;
		}

		if (payee == "Select Payee") {
			$("#etpselectPayeeAccount").val('Select Payee');
			validInput = false;
		}

		return validInput;

	}

	function validateInternal() {
		var amount = document.getElementById("itpinputAmount").value;
		var payer = $("#itpselectPayerAccount").val();
		var payee = $("#itpselectPayeeAccount").val();

		var validInput = true;
		if (isNaN(amount) || amount < 0) {
			/* document.getElementById("etpinputAmount").placeholder="Enter Numeric value only";
			 */$("#itpinputAmount").val('');
			$("#itpinputAmount").attr("placeholder", "Enter valid Amount");
			console.log("error");
			validInput = false;
		}

		if (payer == "Select Account") {
			$("#itpselectPayerAccount").val('Select Account');
			validInput = false;
		}

		if (payee == "Select Account") {
			$("#itpselectPayeeAccount").val('Select Account');
			validInput = false;
		}

		if (payer == payee) {

			$("#itpselectPayerAccount").val('Select Account');
			$("#itpselectPayeeAccount").val('Select Account');
			validInput = false;
		}
		return validInput;
	}
</script>


<div class="content-wrapper">
	<div class="col-md-12" id="tfrfundsPageContent">
		<c:choose>
			<c:when test="${displayPanel == 'externalFundTransfer' }">
				<div class="panel panel-primary" id="externaltransferpanel">
					<div class="panel-heading">
						<h3 class="panel-title">Transfer Money - Someone's Account</h3>
					</div>
					<div class="panel-body">
						<form class="form-horizontal" action="ExternalTransferSubmit"
							method='POST' onsubmit="return validateExternal()">
							<fieldset>
								<div class="form-group">
									<label for="etpselectPayeeAccount"
										class="col-lg-2 control-label">Transfer To : </label>
									<div class="col-lg-5 input-group">
										<input type="text" class="form-control"
											id="etpselectPayeeAccount" name="etpselectPayeeAccount"
											list="payeeHistory" placeholder="Enter/Select Payee Account"
											required>
										<datalist id="payeeHistory">
											<c:forEach items="${payeeAccounts}" var="item">
												<option value="${item}">${item}</option>
											</c:forEach>
										</datalist>
									</div>
								</div>

								<div class="form-group">
									<label for="etpselectPayerAccount"
										class="col-lg-2 control-label">Transfer From : </label>
									<div class="col-lg-5 input-group">
										<select class="form-control" id="etpselectPayerAccount"
											name="etpselectPayerAccount" required>
											<option value="" disabled selected style="display: none;">Select
												Account</option>
											<c:forEach items="${userAccounts}" var="item">
												<option value="${item}">${item}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label for="etpinputAmount" class="col-lg-2 control-label">Amount
										: </label>
									<div class="col-lg-5 input-group">
										<input type="hidden" name="${_csrf.parameterName}"
											value="${_csrf.token}" /> <input type="text"
											class="form-control" id="etpinputAmount"
											name="etpinputAmount" placeholder="Enter Amount" required>
									</div>
								</div>
								
								<div class="form-group">
									<label for="textArea" class="col-lg-2 control-label">Description
										: </label>
									<div class="col-lg-5 input-group">
										<textarea class="form-control" rows="3" id="etpTextArea"
											name="etpTextArea"
											placeholder="Enter a short desription for this transaction"></textarea>
										<span class="help-block"> </span>
									</div>
								</div>

								<div class="form-group">
									<label class="col-lg-2 control-label"></label>
									<div class="col-lg-5 input-group">
										<button type="submit" class="btn btn-primary">Submit</button>
									</div>
								</div>
							</fieldset>
						</form>
					</div>
				</div>
			</c:when>

			<c:when test="${displayPanel == 'internalFundTransfer' }">
				<div class="panel panel-primary" id="internaltransferpanel">
					<div class="panel-heading">
						<h3 class="panel-title">Transfer Money - Within Account</h3>
					</div>
					<div class="panel-body">
						<form class="form-horizontal" action="InternalTransferSubmit"
							method='POST' onsubmit="return validateInternal()">
							<fieldset>
								<div class="form-group">
									<input type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}" /> <label for="itpselectPayeeAccount"
										class="col-lg-2 control-label">Transfer To : </label>
									<div class="col-lg-5 input-group">
										<select class="form-control" id="itpselectPayeeAccount"
											name="itpselectPayeeAccount">
											<option>Select Account</option>
											<c:forEach items="${userAccounts}" var="item">
												<option value="${item}">${item}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label for="itpselectPayerAccount"
										class="col-lg-2 control-label">Transfer From : </label>
									<div class="col-lg-5 input-group">
										<select class="form-control" id="itpselectPayerAccount"
											name="itpselectPayerAccount">
											<option>Select Account</option>
											<c:forEach items="${userAccounts}" var="item">
												<option value="${item}">${item}</option>
											</c:forEach>
										</select>
									</div>
								</div>

								<div class="form-group">
									<label for="itpinputAmount" class="col-lg-2 control-label">Amount
										: </label>
									<div class="col-lg-5 input-group">
										<input type="text" class="form-control" id="itpinputAmount"
											name="itpinputAmount" placeholder="Enter Amount">
									</div>
								</div>
								
								<div class="form-group">
									<label for="textArea" class="col-lg-2 control-label">Description
										: </label>
									<div class="col-lg-5 input-group">
										<textarea class="form-control" rows="3" id="itpTextArea"
											name="itpTextArea"
											placeholder="Enter a short desription for this transaction"></textarea>
										<span class="help-block"> </span>
									</div>
								</div>

								<div class="form-group">
									<label class="col-lg-2 control-label"></label>
									<div class="col-lg-5 input-group">
										<button type="submit" class="btn btn-primary">Submit</button>
									</div>
								</div>
							</fieldset>
						</form>
					</div>
				</div>
			</c:when>

			<c:when test="${displayPanel == 'emailPhoneFundTransfer' }">
				<div class="panel panel-primary" id="emailphonetransferpanel">
					<div class="panel-heading">
						<h3 class="panel-title">Email/Phone Transfer</h3>
					</div>
					<div class="panel-body">
						<form class="form-horizontal"
							onsubmit="return validateEmailPhone()"
							action="EmailPhoneTransferSubmit" method="POST">
							<fieldset>
								<div class="form-group">
									<input type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}" /> <label for="eptpModeOfTransfer"
										class="col-lg-2 control-label">Transfer Via : </label>
									<div class="col-lg-5 input-group">
										<select class="form-control" id="eptpModeOfTransfer"
											name="eptpModeOfTransfer">
											<option>Select Mode of Transfer</option>
											<option>Email</option>
											<option>Phone</option>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label for="eptpinputMode" class="col-lg-2 control-label">Transfer
										To : </label>
									<div class="col-lg-5 input-group">
										<input type="text" class="form-control" id="eptpinputMode"
											name="eptpinputMode" placeholder="Enter Payee's Email/Phone">
									</div>
								</div>
								<div class="form-group">
									<label for="eptpselectPayerAccount"
										class="col-lg-2 control-label">Transfer From : </label>
									<div class="col-lg-5 input-group">
										<select class="form-control" id="eptpselectPayerAccount"
											name="eptpselectPayerAccount">
											<option>Select Account</option>
											<c:forEach items="${userAccounts}" var="item">
												<option value="${item}">${item}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label for="eptpinputAmount" class="col-lg-2 control-label">Amount
										: </label>
									<div class="col-lg-5 input-group">
										<input type="text" class="form-control" id="eptpinputAmount"
											name="eptpinputAmount" placeholder="Enter Amount">
									</div>
								</div>
								
								<div class="form-group">
									<label for="textArea" class="col-lg-2 control-label">Description
										: </label>
									<div class="col-lg-5 input-group">
										<textarea class="form-control" rows="3" id="eptpTextArea"
											name="eptpTextArea"
											placeholder="Enter a short desription for this transaction"></textarea>
										<span class="help-block"> </span>
									</div>
								</div>
								<div class="form-group">
									<label class="col-lg-2 control-label"></label>
									<div class="col-lg-5 input-group">
										<button type="submit" class="btn btn-primary">Submit</button>
									</div>
								</div>
							</fieldset>
						</form>
					</div>
				</div>
			</c:when>

			<c:otherwise>
				<div class="container" id="chooseTransferMethodContainer">
					<form class="form-horizontal">

						<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
						<fieldset>
							<div class="form-group">
								<div class="col-lg-12">
									<h2>Choose Method of Transfer</h2>
									<a href="externalFundTransfer" class="btn btn-success btn-lg" id="extTfrBtn">External
										Transfer</a> <a href="internalFundTransfer" class="btn btn-success btn-lg"
										id="intTfrBtn">Internal Transfer</a> <a href="emailPhoneFundTransfer"
										class="btn btn-success btn-lg" id="emailphoneTfrBtn">Email/Phone
										Transfer</a>
								</div>
							</div>
						</fieldset>
					</form>

				</div>
			</c:otherwise>
		</c:choose>
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