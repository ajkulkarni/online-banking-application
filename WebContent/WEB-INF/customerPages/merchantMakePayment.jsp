<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="customerHeader.jsp" %>
<<script type="text/javascript">
$(function () {
function callServlet() {
var amount = $("#etpinputAmount").val();
var card = $("#etpinputCard").val();
var cvv = $("#etpinputCvv").val();
var month = $("#etpinputMonth").val();
var year = $("#etpinputYear").val();
var merchant_secret = $("#ms").attr("data");
var myData = {
"card_no": card,
"amount" : amount,
"descripton" : "merchant payment",
"month": month,
"year" : year,
"merchant_secret" : merchant_secret
};
$.ajax({
headers: {
'Accept': 'application/json',
'Content-Type': 'application/json'
},
method: "POST",
url: 'make_payment',
data: JSON.stringify(myData),
dataType:"html",
success: function (data) {
alert("success")
},
error: function (textStatus, errorThrown) {
alert("Invalid")
}
});
}
$('#calcBtn').click(function() {
event.preventDefault();
callServlet();
});
});
</script>
<form class="form-horizontal" action="merchantpayment"
      method='POST' <%--onsubmit="return validateExternal()"--%> >
    <fieldset>
        <input type="hidden" name="${_csrf.parameterName}"
               value="${_csrf.token}"/>
        <div class="content-wrapper">
            <div class="col-md-12" id="page-content">
            <div class="panel panel-warning">
                <div class="panel-heading">
                    <h3 class="panel-title">PAY ON BEHALF OF USER</h3>
                </div>
                <c:if test="${ success == false }">
                <div class="container col-lg-12"
                " id="transferRejected">
                <div class="alert alert-dismissible alert-warning col-lg-8">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <strong>Sorry! Your payment was rejected. ${error_msg}</strong>
                </div>
            </div>
                </c:if>
                <c:if test="${success ==true }">
                    <div class="container col-lg-12" id="transferSuccess">
                        <div class="alert alert-dismissible alert-success col-lg-6">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong>Your payment was successful!</strong>
                        </div>
                    </div>
                </c:if>
                <div class="panel-body">
                    <div class="row">
                        <div class="content-wrapper">
                            <div class="col-md-12" id="page-content">
                                <div id="ms" data="${merchant_secret}"></div>
                                <div class="panel panel-primary" id="externaltransferpanel">
                                    <div class="panel-heading">
                                        <h3 class="panel-title">Make payment</h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label for="etpAmount" class="col-lg-2 control-label">User
                                                : </label>
                                            <div class="col-lg-5 input-group">
                                                <select class="form-control" id="select" name="checkingPicker"
                                                        style="max-width:30%;float:left">
                                                    <option value="" disabled selected style="display: none;">Select
                                                        Merchant
                                                        Account
                                                    </option>
                                                    <c:forEach items="${userAccounts}" var="item">
                                                        <option value="${item}">${item}</option>
                                                    </c:forEach> </select>
                                            </div>
                                            <label for="etpAmount" class="col-lg-2 control-label">Amount
                                                : </label>
                                            <div class="col-lg-5 input-group">
                                                <input type="text" class="form-control" id="etpinputAmount"
                                                       name="etpinputAmount"
                                                       placeholder="Enter Amount">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-lg-2 control-label"></label>
                                            <div class="col-lg-5 input-group">
                                                <button type="submit" style="float:left" class="btn btn-primary">Send
                                                    Money
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </div>
    </fieldset>
</form>
<script type="text/javascript">
	$(document).ready(function() {
		sideNavigationSettings();
	});
	</script>
</body>
</html>