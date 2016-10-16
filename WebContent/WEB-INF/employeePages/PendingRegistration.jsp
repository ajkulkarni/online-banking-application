<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="employeeHeader.jsp" %>

	<div class="content-wrapper">
		<div class="col-md-12" id="page-content">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Pending User Registration</h3>
				</div>
				<div class="panel-body no-padding">
					<table id="content-table">
						<thead>
							<tr>
								<th class="active">Email</th>
								<th class="active">Name</th>
								<th class="active">Address</th>
								<th class="active">Phone</th>
								<th class="active">Creation Time</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${register_list}" var="register">
								<tr>
									<td>${register.userEmail}</td>
									<td>${register.firstName} ${register.lastName}</td>
									<td>${register.street} ${register.house} ${register.city} ${register.state} ${register.country}</td>
									<td>${register.userPhoneNumber}</td>
									<td>${register.timestamp_created}</td>
							</tr>
							</c:forEach>	
						</tbody>
					</table>
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