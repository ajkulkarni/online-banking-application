<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="customerHeader.jsp"%>

<div class="content-wrapper">
	<div class="col-md-12" id="page-content">
		

		<table class="table table-striped">
			<thead>
				<tr>
					<th>#</th>
					<th>Request</th>
					<th></th>
					<th></th>

				</tr>
			</thead>
			<tbody>

<!-- Remove the first row when backend is ready -->
				<tr>
					<th scope="row">1</th>
					<td>Email Change</td>
					<td><div class="radio">
							<label><input type="radio" name="optradio">Approve</label>
						</div></td>
					<td><div class="radio">
							<label><input type="radio" name="optradio">Decline</label>
						</div></td>

				</tr>

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
		
		
		<table class="table table-striped">
			<thead>
				<tr>
					<th>#</th>
					<th>Notifications</th>
		

				</tr>
			</thead>
			<tbody>

<!-- Remove the first row when backend is ready -->
				<tr>
					<th scope="row">1</th>
					<td>Account details was updated</td>
				

				</tr>

				<c:forEach var="trans" items="${transations}" varStatus="loop">

					<tr>
						<th scope="row">${loop.index + 1}</th>
						<td>${trans.description}</td>

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