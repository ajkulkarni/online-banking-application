<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="employeeHeader.jsp" %>
    <div class="content-wrapper">
        <div class="col-md-12" id="page-content">
		<c:if test="${ success == false }">
			<div class="container col-lg-12" " id="transferRejected">
				<div class="alert alert-dismissible alert-warning col-lg-8">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<strong>${error_msg}</strong>
				</div>
			</div>
		</c:if>
		<c:if test="${success ==true }">
			<div class="container col-lg-12" id="transferSuccess">
				<div class="alert alert-dismissible alert-success col-lg-6">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<strong>${error_msg}</strong>
				</div>
			</div>
		</c:if>
            <h3>Accounts Transactions</h3>
            <a href="#newRequest" data-toggle="modal" class="btn btn-sm btn-primary">Add Transaction</a>
            <br><br>
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">Transaction List</h3>
                </div>
                <div class="panel-body no-padding">
                    <table id="content-table">
                        <thead>
                            <tr>
								<th class="active">Transaction ID</th>
								<th class="active">Sender</th>
								<th class="active">Receiver</th>
								<th class="active">Amount</th>
								<th class="active">Action</th>
							</tr>
                        </thead>
                        <tbody>
                        	<c:choose>
                        		<c:when test="${empty transactionList}">
                        			<tr>
                                    	<td colspan="5">No Pending Transaction</td>
                                	</tr>
                        		</c:when>
                        		<c:otherwise>
                        			<c:forEach items="${transactionList}" var="transaction">
                                		<tr>
                                    		<td style="text-align:center">${transaction.id}</td>
											<td style="text-align:center">${transaction.payer_id}</td>
											<td style="text-align:center">${transaction.payee_id}</td>
											<td style="text-align:center">${transaction.amount}</td>
											<td style="text-align:center">
												<form action = "processAccTransaction" method = "post">
		                                    		<input type="hidden" name="transactionID" value="${transaction.id}">
		                                    		<input type="hidden" name="extUserID" value="${extUserID}">
		                                    		<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
		                                    		<select id="requestType" name="requestType" required>
				       									<option value="">Select Type</option>
				          								<option value="approve">Approve</option>
				          								<option value="reject">Reject</option>
				       								</select>
		                                    		<button type="submit" class="btn btn-xs btn-primary">Submit</button>
		                                   		</form>
											</td>
                                		</tr>
                            		</c:forEach>
                        		</c:otherwise>
                        	</c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
                <div class="modal" id="newRequest">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-body no-padding">
		        <div class="panel panel-success no-margin">
				  <div class="panel-heading">
				    <h3 class="panel-title">Add New Transaction</h3>
				  </div>
				  <div class="panel-body">
				    <form class="form-horizontal" action="newaccTransaction" method="POST">
				    	<fieldset>
				    		<div class="form-group">
				    			<label for="accountNo" class="col-lg-3 control-label">Account</label>
			    				<div class="col-lg-9 form-margin">
       								<select class="form-control" id="accountNo" name="accountNo" required>
       									<c:forEach items="${userAccounts}" var="item">
													<option value="${item}">${item}</option>
												</c:forEach>
       								</select>
       							</div>
       							<label for="receiverAccount" class="col-lg-3 control-label">Receiver Account</label>
			    				<div class="col-lg-9 form-margin">
		      							<input type="text" class="form-control" id="receiverAccount" name="receiverAccount" placeholder="Receiver Account Number" required>
		      					</div>
		      					<label for="amount" class="col-lg-3 control-label">Amount</label>
			    				<div class="col-lg-9">
		      							<input type="text" class="form-control" id="amount" name="amount" placeholder="amount" required>
		      					</div>
       							<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
       							<input type="hidden" name="extUserID" value="${extUserID}">
       							<div class="col-lg-10 col-lg-offset-2" style="margin-top:15px;">
	       							<button style="float:right;" type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			        				<button style="float:right;margin-right:15px;" type="submit" class="btn btn-primary">Submit</button>
      							</div>
				    		</div>
				    	</fieldset>
				    </form>
				  </div>
				</div>
		      </div>
		    </div>
		  </div>
		</div>
        
    </div> <!-- .content-wrapper -->
    
</main> 
<script type="text/javascript">
    $(document).ready(function() {
        sideNavigationSettings();
    });
</script>
<script src="<c:url value="/resources/js/custom.js" />"></script>
</body>
</html>
