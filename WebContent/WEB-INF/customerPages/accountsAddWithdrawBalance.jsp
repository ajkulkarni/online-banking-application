<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="customerHeader.jsp" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.11.2/moment.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>
<script src="<c:url value="/resources/js/transferfunds.js" />"></script>

    <div class="content-wrapper">
        <div class="col-md-12" id="page-content">
            <div class="container" id="chooseTransferMethodContainer" style="display:none;">
              
              
                <form class="form-horizontal">
               
                  <fieldset>
                    <div class="form-group">
                    <div class="col-lg-12">
                      <legend>Add/Withdraw Balance</legend>
                      <a href="#" class="btn btn-success btn-lg" id="extTfrBtn">Add amount</a>
                      <a href="#" class="btn btn-success btn-lg" id="intTfrBtn">Withdraw amount</a>
                    </div>
                    </div>
                  </fieldset>
                </form>
              
            </div>        
        
            <div class="panel panel-primary" id="externaltransferpanel" style="display:none;">
              <div class="panel-heading">
                <h3 class="panel-title">Add Balance</h3>
              </div>
              <div class="panel-body">
                  
                    <div class="form-group">
                    <form class="form-horizontal" name="addMoneyForm" action="addMoney" method='POST' onSubmit="return validateForm()">           
                    <label for="etpselectPayee" class="col-lg-2 control-label">Select Account: </label>
                      <div class="col-lg-5 input-group">
                        <select name="accountType" class="form-control" id="etpselectPayee" >
                          <option value="CHECKING">Checking Account</option>
                          <option value="SAVINGS">Savings Account</option>
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
            
            <div class="panel panel-primary" id="internaltransferpanel" style="display:none;">
              <div class="panel-heading">
                <h3 class="panel-title">Withdraw amount</h3>
              </div>
              <div class="panel-body">
                    <div class="form-group">
                    <form class="form-horizontal" name="withdrawMoneyForm" action="withdrawMoney" method='POST' onSubmit="return checkInputOr()">         
                    <label for="etpselectPayee" class="col-lg-2 control-label">Select Account: </label>
                      <div class="col-lg-5 input-group">
                        <select name="accountType" class="form-control" id="etpselectPayee" >
                          <option value="CHECKING">Checking Account</option>
                          <option value="SAVINGS">Savings Account</option>
                        </select>     
                      </div>
                      <div>
                    <label for="accountType" style="padding-left:130px; padding-top:20px; padding-bottom:20px">Checking Account Balance: </label>
                                                                    ${ checkingAccount.balance }
                    </div>
                    <div>
                   <label for="accountType" style="padding-left:130px; padding-top:20px; padding-bottom:20px">Saving Account Balance: </label>
                     ${ savingsAccount.balance }
                   </div>
                      <label for="etpinputAmount" class="col-lg-2 control-label">Amount : </label>
                      <div class="col-lg-5 input-group">
                        <input type="text" name="amount" class="form-control" id="amount" placeholder="Enter Amount">
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
        </main>
        
<script type="text/javascript">
    $(document).ready(function() {
        sideNavigationSettings();
    });
</script>
</body>
</html>
    