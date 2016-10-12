<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="customerHeader.jsp" %>

	<div class="content-wrapper">
		<div class="col-md-12" id="page-content">
			<h1>Accounts Statement</h1>
			
			<div class="panel panel-warning">
				<div class="panel-heading">
					<h3 class="panel-title">Account Info</h3>
				</div>
				<div class="panel-body">
					<div class="row">
					<div class="col-sm-3">Account Balance:</div>
					<div class="col-sm-3">${account_bal}</div>
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

</body>
</html>