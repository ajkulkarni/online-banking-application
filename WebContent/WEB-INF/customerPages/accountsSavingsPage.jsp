<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="customerHeader.jsp" %>
	<div class="content-wrapper">
		<div class="col-md-12" id="page-content">
			<h1>Savings Account</h1>
			<div class="panel panel-warning">
				<div class="panel-heading">
					<h3 class="panel-title">ACCOUNT INFORMATION</h3>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-sm-3"><h4><b>Account Balance:</b></h4></div>
						<div class="col-sm-3">${account_bal}</div>
        				<select class="form-control" id="select" style="max-width:30%;">
          					<option>Last month</option>
          					<option>Last 3 months</option>
          					<option>Last 6 months</option>
        				</select>
        			</div>
					<hr>
					<h4><center>Transaction History</center></h4>
					<hr>
					<div class="row">
						<table class="table table-fixed">
          					<thead>
            					<tr>
              						<th class="col-xs-2">#</th>
              						<th class="col-xs-4">Description</th>
              						<th class="col-xs-2">Payee</th>
              						<th class="col-xs-2">Amount</th>          
             						<th class="col-xs-2">Date</th>
            					</tr>
          					</thead>
          
<!--  SAMPLE TABLE VALUES  
          
          	<tbody>
			<tr>
              <td class="col-xs-2">1</td><td class="col-xs-6">aaa</td><td class="col-xs-2">23</td><td class="col-xs-2">23</td>
            </tr>
            <tr>
              <td class="col-xs-2">2</td><td class="col-xs-6">bbb</td><td class="col-xs-2">44</td><td class="col-xs-2">23</td>
            </tr>
            <tr>
              <td class="col-xs-2">3</td><td class="col-xs-6">ccc</td><td class="col-xs-2">86</td><td class="col-xs-2">23</td>
            </tr>
            <tr>
              <td class="col-xs-2">4</td><td class="col-xs-6">ddd</td><td class="col-xs-2">23</td><td class="col-xs-2">23</td>
            </tr>
            <tr>
              <td class="col-xs-2">5</td><td class="col-xs-6">eee</td><td class="col-xs-2">44</td><td class="col-xs-2">23</td>
            </tr>
            <tr>
              <td class="col-xs-2">6</td><td class="col-xs-6">fff</td><td class="col-xs-2">44</td><td class="col-xs-2">23</td>
            </tr>
            <tr>
              <td class="col-xs-2">7</td><td class="col-xs-6">ggg</td><td class="col-xs-2">44</td><td class="col-xs-2">23</td>
            </tr>
             <tr>
              <td class="col-xs-2">7</td><td class="col-xs-6">ggg</td><td class="col-xs-2">44</td><td class="col-xs-2">23</td>
            </tr>
             <tr>
              <td class="col-xs-2">7</td><td class="col-xs-6">ggg</td><td class="col-xs-2">44</td><td class="col-xs-2">23</td>
            </tr>
             <tr>
              <td class="col-xs-2">7</td><td class="col-xs-6">ggg</td><td class="col-xs-2">44</td><td class="col-xs-2">23</td>
            </tr>
             <tr>
              <td class="col-xs-2">7</td><td class="col-xs-6">ggg</td><td class="col-xs-2">44</td><td class="col-xs-2">23</td>
            </tr>
             <tr>
              <td class="col-xs-2">7</td><td class="col-xs-6">ggg</td><td class="col-xs-2">44</td><td class="col-xs-2">23</td>
            </tr>
          	</tbody>
            
            -->
            
    					<c:forEach var="trans" items="${sAccount.transactionList}" varStatus="loop">
							<tr>
								<th scope="row">${loop.index + 1}</th>
								<td>${trans.description}</td>
								<td>${trans.payee}</td>
								<td>${trans.amount}</td>
								<td>${trans.date}</td>
							</tr>
						</c:forEach>
        			</table>
       				 </div>
				</div>
				
			</div>
			<button type="button" class="btn btn-primary">Download statements</button>
			
			
			
			
		</div>
	</div> <!-- .content-wrapper -->
	
</main> 

<script type="text/javascript">
	$(document).ready(function() {
		sideNavigationSettings();
	});
</script>

</body>
</html>