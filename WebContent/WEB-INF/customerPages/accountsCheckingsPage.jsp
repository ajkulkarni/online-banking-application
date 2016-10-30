<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="customerHeader.jsp" %>
<script src="<c:url value="/resources/js/jspdf.js" />"></script>
<script src="<c:url value="/resources/js/jspdf.table.js" />"></script>
    <div class="content-wrapper">
        <div class="col-md-12" id="page-content">
		<div><p id="heading" style="font-size:30px; text-align:center">Checking Account</p></div>            
            <div class="panel panel-warning">
                <div class="panel-heading">
                    <h3 class="panel-title">ACCOUNT INFORMATION</h3>
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
                    
                    <form class="form-horizontal" action="CheckingAccount" method='POST' onSubmit="return checkInputOr()">
                    
                    <div class="row">
                        <select class="form-control" id="select" name="checkingPicker" style="max-width:30%;float:right">
                            <option>Last month</option>
                            <option>Last 3 months</option>
                            <option>Last 6 months</option>
                        </select>
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
            
            <button type="button" onclick="generatePDF(${checkingAccount.account_number},${checkingAccount.balance})" class="btn btn-primary">Download statements</button></div>
            
            
            
            
        </div>
    </div> <!-- .content-wrapper -->
    
<script>
function generatePDF(num,bal) {    
    function generatePDF() {
          var pdfsize = 'a4';
          var pdf = new jsPDF('l', 'pt', pdfsize);
          var res = pdf.autoTableHtmlToJson(document.getElementById("trans-table"));
          var today = new Date();
          var dd = today.getDate();
          var mm = today.getMonth()+1; 
          var yyyy = today.getFullYear();
          if(dd<10) {
              dd='0'+dd
          } 
          if(mm<10) {
              mm='0'+mm
          } 
          today = mm+'/'+dd+'/'+yyyy;
          
          
          var header = function(data) {
            pdf.setFontSize(18);
            pdf.setTextColor(40);
            pdf.setFontStyle('normal');
            pdf.text(("Transaction Summary for checking account\t\t\t\t\tDate:"+today+"\n"+"Account Number:  "+num+"\n"+"Account Balance:  $"+bal), data.settings.margin.left, 50);
          }
          pdf.autoTable(res.columns, res.data, {
            beforePageContent: header,
            startY: 100,
            drawHeaderRow: function(row, data) {
              row.height = 46;
            },
        
            drawRow: function(row, data) {
              if (row.index === -1) return false;
            },
            margin: {
              top: 100
            },
            styles: {
              overflow: 'linebreak',
              fontSize: 10,
              tableWidth: 'auto',
              columnWidth: 'auto',
            },
            columnStyles: {
              1: {
                columnWidth: 'auto'
              }
            },
          });
        
          pdf.save("Transaction_Summary_Checking_"+num+"_"+today+".pdf");
        };
        generatePDF();
    }
</script>
<script type="text/javascript">
    $(document).ready(function() {
        sideNavigationSettings();
    });
</script>
</body>
</html>