<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="customerHeader.jsp"%>
<div class="content-wrapper">
    <div class="col-md-12" id="page-content">
        <h3>Credit Card DashBoard</h3>
        <div class="panel panel-warning">
            <div class="panel-heading">
                <h4 class="panel-title">Account Info</h4>
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
                <div class="row">
                    <div class="col-sm-3">Credit Limit:</div>
                    <div class="col-sm-3">${creditAccount.creditLimit}</div>
                </div>
                <div class="row">
                    <div class="col-sm-3">Current Due Amount:</div>
                    <div class="col-sm-3">${creditAccount.currentDueAmount}</div>
                </div>
                <div class="row">
                    <div class="col-sm-3">Cycle Date:</div>
                    <div class="col-sm-3">${creditAccount.cycleDate}</div>
                </div>
                <div class="row">
                    <div class="col-sm-3">APR:</div>
                    <div class="col-sm-3">${creditAccount.apr}</div>
                </div>
            </div>
        </div>
        <a class="btn btn-primary bnt-lg pull-right" href="creditPayment"
            role="button">Make payment</a>
        <h3>Credit Card Statement</h3>
        <form class="form-horizontal" action="getTransactions" method='POST'
            onSubmit="return checkInputOr()">
            <select class="form-control" name="monthPicker">
                <c:forEach var="month" items="${months}" varStatus="loop">
                <option>${month}</option>
                    <%-- <c:if test=${month}.equals(${electedMonth})>
                        <option selected="selected">${month}</option>
                    </c:if>
                    <c:otherwise>
                        <option>${month}</option>
                    </c:otherwise> --%>
                </c:forEach>
            </select>
            <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Description</th>
                    <th>Payee</th>
                    <th>Payer</th>
                    <th>Amount</th>
                    <th>Date</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="trans" items="${transations}" varStatus="loop">
                    <tr>
                        <th scope="row">${loop.index + 1}</th>
                        <td>${trans.description}</td>
                        <td>${trans.payee_id}</td>
                        <td>${trans.payer_id}
                        </th>
                        <td>${trans.amount}</td>
                        <td>${trans.timestamp_updated}</td>
                        <td>${trans.pendingStrg}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
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