<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@include file="customerHeader.jsp" %>

   <div class="content-wrapper">
        <div class="col-md-12" id="page-content">

<form class="form-horizontal" action="authorizemerchants"
      method='POST' <%--onsubmit="return validateExternal()"--%> >
    <fieldset>
        <input type="hidden" name="${_csrf.parameterName}"
               value="${_csrf.token}"/>
        <div class="content-wrapper">
            <div class="col-md-12" id="page-content">
                <c:if test="${ success == false }">
                <div class="container col-lg-12" " id="transferRejected">
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
                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <h3 class="panel-title">MERCHANT INFORMATION</h3>
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-sm-2"><b><h4>Merchant List</h4></b></div>
                            <select class="form-control" id="select" name="checkingPicker"
                                    style="max-width:30%;float:left">
                                <option value="" disabled selected style="display: none;">Select Merchant
                                    Account
                                </option>
                                <c:forEach items="${merchantAccounts}" var="item">
                                    <option value="${item}">${item}</option>
                                </c:forEach> </select>
                            <button type="submit" style="float:left" class="btn btn-primary">Add</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </fieldset>
</form>
</div>

<div class="content-wrapper">
    <div class="col-md-12" id="page-content">
        <div class="panel panel-warning">
            <div class="panel-heading">
            </div>
            <div class="panel-body">
                <div class="row">
                    <table class="table table-striped" id="merchant-table">
                        <thead>
                        <tr>
                            <th class="col-xs-2">Merchant Name</th>
                            <th class="col-xs-2">Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${existingMerchants}" var="item">
                            <option value="${item}">
                                <tr>
                                    <td>${item}</td>
                                    <td>
                                        <form class="form-horizontal" action="authorizemerchants"
                                              method='POST' <%--onsubmit="return validateExternal()"--%> >
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                                <%--               <input id="${item}" type="submit" name="submit" value="${item}"  style="float:left" class="btn btn-primary">--%>


                                            <button id="${item}" value="${item}" name="removeMerchant" type="submit"
                                                    style="float:left" class="btn btn-primary">Revoke
                                            </button>
                                    </td>
                                        <%--                                    <button id="${item}" value="${item}"      name="removeMerchant" type="submit" style="float:left" class="btn btn-primary">Revoke</button></td>--%>
                                </tr>
                            </option>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</main>

<!-- .content-wrapper -->
<script type="text/javascript">
    $(document).ready(function () {
        sideNavigationSettings();
    });
</script>

</body>
</html>