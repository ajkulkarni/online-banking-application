<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="customerHeader.jsp" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.11.2/moment.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>
<!--<script src="<c:url value="/resources/js/transferfunds.js" />"></script>-->

    <div class="content-wrapper">
                <c:if test="${not empty errorMessage}">
                <div class="container col-lg-12" " id="transferRejected">
                <div class="alert alert-dismissible alert-warning col-lg-8">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <strong>${errorMessage}</strong>
                </div>
            </div>
            </c:if>
        <div class="col-md-12" id="page-content">
       <div class="panel panel-primary" id="externaltransferpanel">
              <div class="panel-heading">
                <h3 class="panel-title">Add Balance</h3>
              </div>
              <div class="panel-body">                  
                    <div class="form-group">
                    <form class="form-horizontal" name="addMoneyForm" action="addMoney" method='POST' onSubmit="return validateForm()">           
                    <label for="etpselectPayee2" class="col-lg-2 control-label">Select Account: </label>
                      <div class="col-lg-5 input-group">
                        <select name="accountType" class="form-control" id="etpselectPayee" required>
						   <option selected="selected" value="CHECKING">Checking Account</option>
                          <option  value="SAVINGS">Savings Account</option>
                        </select>
                      </div>
                      <div>
                    <label for="accountType" style="padding-left:130px; padding-top:20px; padding-bottom:20px">Checking Account Balance: </label>
                        ${checkingAccount.balance}
                      </div>
                      <div>
                        <label for="accountType" style="padding-left:130px; padding-top:20px; padding-bottom:20px">Savings Account Balance: </label>
                                       ${savingsAccount.balance}
                        
                      </div>
                      <label for="etpinputAmount" class="col-lg-2 control-label">Amount : </label>
                      <div class="col-lg-5 input-group">
                        <input type="text" name="amount" class="form-control" id="inputAmount" placeholder="Enter Amount">
                      </div> 
                      <label class="col-lg-2 control-label"></label>
                      <div class="col-lg-5 input-group">
                      <input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
                        <button type="submit" class="btn btn-primary">Submit</button>
                      </div>
                        </form>   
                      </div>
                    </div>
              </div>
            </div>
        </div>
        
<script type="text/javascript">
    $(document).ready(function() {
        sideNavigationSettings();
    });
</script>
</body>
</html>
    