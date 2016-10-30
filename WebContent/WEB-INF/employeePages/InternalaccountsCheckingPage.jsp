<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="employeeHeader.jsp" %>
<script src="<c:url value="/resources/js/jspdf.js" />"></script>
<script src="<c:url value="/resources/js/jspdf.table.js" />"></script>
    <div class="content-wrapper">
        <div class="col-md-12" id="page-content">
		<div><p id="heading" style="font-size:30px; text-align:center">Checking Account</p></div>            
            <div class="panel panel-warning">
                <div class="panel-heading">
                    <h3 class="panel-title">Account Information</h3>
                </div>
                <div class="panel-body">
                                    <div class="row">
                        <div class="col-sm-2"><b>Account Number:</b></div>
                        <div class="col-sm-3">${checkingAccount.account_number}</div>
                                            
                    </div>
                    <div class="row">
                        <div class="col-sm-2"><b>Account Balance:</b></div>
                        <div class="col-sm-3">${checkingAccount.balance}</div>
                    </div>
                    <hr>
                    <h4><center>Transaction History</center></h4>
                    
                    <form class="form-horizontal" action="viewcheckingaccount" method='POST' onSubmit="return checkInputOr()">
                    
                    <div class="row">
                        <select class="form-control" id="select" name="checkingPicker" style="max-width:30%;float:right">
                            <option>Last month</option>
                            <option>Last 3 months</option>
                            <option>Last 6 months</option>
                        </select>
                        <input type="hidden" name="extUserID"
									value="${customer.id}" />
                        <input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
                        <button type="submit" style="float:right" class="btn btn-primary">Submit</button>
                                        </div>
                    
                                            </form>
                                            <hr>
                    
                    <div class="row" id="table-scroll" style="height:300px; overflow:scroll">
                        <table class="table table-striped" id="trans-table">
                            <thead>
                                <tr>
                                    <th class="col-xs-1">#</th>
                                    <th class="col-xs-4">Description</th>
                                    
                                    <th class="col-xs-1">Transaction Type</th>                                    
                                    <th class="col-xs-1">Payee</th>
                                    <th class="col-xs-2">Amount</th>
                                    <th class="col-xs-2">Status</th>       
                                    <th class="col-xs-3">Date</th>    
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="trans" items="${TransactionLines}" varStatus="loop">
                                    <tr>
                                        <th scope="row">${loop.index + 1}</th>
                                        <td>${trans.description}</td>
                                        <c:set var="account_number" scope="session" value="${checkingAccount.account_number}"/>
                                        <c:set var="description" scope="session" value="${trans.description}"/>                                     
                                        <c:set var="payee_id" scope="session" value="${trans.payee_id}"/>
                                        <c:set var="payer_id" scope="session" value="${trans.payer_id}"/>
                                        <c:set var="depo_desc" scope="session" value="Deposit money to account"/>
                                        <c:set var="with_desc" scope="session" value="Withdrawal of money from account"/> 
                                                                               
                                    	<c:choose>
                                    		<c:when test="${payer_id!=payee_id }">
                                    			<c:choose>
  													<c:when test="${account_number==payee_id}">
  														<td>CREDIT</td>
  													</c:when>
  													<c:otherwise>
  														<td>DEBIT</td>
  													</c:otherwise>
  												</c:choose>
  											</c:when>
  											<c:otherwise>
  												<c:choose>
  													<c:when test="${description==depo_desc}">
  														<td>CREDIT</td>
													</c:when>
													<c:otherwise>
														<td>DEBIT</td>  
													</c:otherwise>
												</c:choose>														
  											</c:otherwise>
										</c:choose>
                                        <td>${trans.payee_id}</td>
                                        <td>${trans.amount}</td>
                                        <td>${trans.status}</td>
                                        <td>${trans.timestamp_updated}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                
            </div>
            <div>
            
            
          </div>
          </div>
          </main>  
            
            
        </div>
<script type="text/javascript">
    $(document).ready(function() {
        sideNavigationSettings();
    });
</script>
</body>
</html>