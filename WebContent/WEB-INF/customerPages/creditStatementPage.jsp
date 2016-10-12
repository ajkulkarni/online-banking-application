<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="customerHeader.jsp"%>

<div class="content-wrapper">
	<div class="col-md-12" id="page-content">
		<h1>Credit Card Statement</h1>

		<table class="table table-striped">
			<thead>
				<tr>
					<th>#</th>
					<th>Description</th>
					<th>Payee</th>
					<th>Amount</th>
					<th>Date</th>
				</tr>
			</thead>
			<tbody>


				<c:forEach var="trans" items="${transations}" varStatus="loop">

					<tr>
						<th scope="row">${loop.index + 1}</th>
						<td>${trans.description}</td>
						<td>${trans.payee}</td>
						<td>${trans.amount}</td>
						<td>${trans.date}</td>
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
</html>