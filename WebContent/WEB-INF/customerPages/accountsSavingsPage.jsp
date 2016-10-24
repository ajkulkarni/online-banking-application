<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="customerHeader.jsp" %>
<script src="<c:url value="/resources/js/jspdf.js" />"></script>
<script src="<c:url value="/resources/js/jspdf.table.js" />"></script>
	<div class="content-wrapper">
		<div class="col-md-12" id="page-content">
			<h1>Savings Account</h1>
			<div class="panel panel-warning">
				<div class="panel-heading">
					<h3 class="panel-title">ACCOUNT INFORMATION</h3>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-sm-2"><b>Account Balance:</b></div>
						<div class="col-sm-3">${SavingsAccBal}</div>
						<form class="form-horizontal" action="getsavingTransactions" method='POST' onSubmit="return checkInputOr()">
        				<select class="form-control" name="savingsPicker" id="select" style="max-width:30%;float:right">
          					<option>Last month</option>
          					<option>Last 3 months</option>
          					<option>Last 6 months</option>
        				</select>
        				<button type="submit" style="float:right" class="btn btn-primary">Submit</button>
        				</form>
        			</div>
					<hr>
					<h4><center>Transaction History</center></h4>
					<hr>
					<div class="row">
						<table class="table table-fixed" id="trans-table">
          					<thead>
            					<tr>
              						<th class="col-xs-2">#</th>
              						<th class="col-xs-4">Description</th>
              						<th class="col-xs-3">Payee</th>
              						<th class="col-xs-3">Amount</th>         
              						<th class="col-xs-3">Date</th>      
            					</tr>
          					</thead>
            				<tbody>
    							<c:forEach var="trans" items="${TransactionLines}" varStatus="loop">
									<tr>
										<th scope="row">${loop.index + 1}</th>
										<td>${trans.description}</td>
										<td>${trans.payee_id}</td>
										<td>${trans.amount}</td>
										<td>${trans.timestamp_updated}</td>
										
									</tr>
								</c:forEach>
							</tbody>
        				</table>
       				</div>
				</div>
				
			</div>
			<button type="button" onclick="generatePDF()" class="btn btn-primary">Download statements</button>
			
			
			
			
		</div>
	</div> <!-- .content-wrapper -->
	
<script>
	    function generatePDF() {    
	        var doc = new jsPDF('p', 'pt');
	        var elem = document.getElementById("trans-table");
	        var res = doc.autoTableHtmlToJson(elem);
	        doc.autoTable(res.columns, res.data);
	        doc.save("table.pdf");
	    }
</script>

<script type="text/javascript">
	$(document).ready(function() {
		sideNavigationSettings();
	});
</script>

</body>
</html>