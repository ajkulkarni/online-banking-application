<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="employeeHeader.jsp"%>
<link rel="stylesheet" href="<c:url value="/resources/css/adminLogs.css" />"> <!-- Resource style -->
<div class="content-wrapper">
	<div class="col-md-12" id="page-content">
		<h3>Logging</h3>
		<form action="/CSE545-SS/adminhome" method="post" class="form-horizontal">
		<div class="panel-group">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">
						<a data-toggle="collapse" href="#collapse1">Filters</a>
					</h3>
				</div>
				<div id="collapse1" class="panel-collapse collapse">
					<div class="panel-body">
						<div class="search-group">
							<div class="col-lg-12">
								<label for="acno" class="col-lg-2 control-label">A/C No.</label>
								<input type="text" class="text-input" id="searchTermExternal"
									placeholder="Emp Name, Emp A/C No. etc.,">
							</div>
							<br>
							<div class="col-lg-12">
								<label for="acholdername" class="col-lg-2 control-label">A/C
									Holder Name</label> <input type="text" class="text-input"
									id="searchTermExternal"
									placeholder="Emp Name, Emp A/C No. etc.,">
							</div>
							<br>
							<div class="col-lg-12">
								<label for="startdate" class="col-lg-2 control-label">Start Date</label> <input type="date" class="date-input"
									id="searchTermExternal">
							</div>
							<br>
							<br>
							<div class="col-lg-12">
								<label for="enddate" class="col-lg-2 control-label">End Date</label> <input type="date" class="date-input"
									id="searchTermExternal"> <button type="submit" class="btn btn-primary">Search</button>
							</div>
							
						</div>
					</div>
				</div>
			</div>
		</div>
		</form>
		<div class="panel panel-success">
			<div class="panel-heading">
				<h3 class="panel-title">Interal User Log Search</h3>
			</div>
			<br>
			<div class="panel panel-primary">
				<div class="panel-body no-padding">
					<table id="content-table">
						<thead>
							<tr>
								<th class="active">User Id</th>
								<th class="active">Activity</th>
								<th class="active">Details</th>
								<th class="active">Date</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${request_list}" var="request">
								<tr>
									<td>${request.id}</td>
									<td>${request.request_type}</td>
									<td>${request.current_value}</td>
									<td>${request.requested_value}</td>
							</tr>
							</c:forEach>	
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="panel panel-success">
			<div class="panel-heading">
				<h3 class="panel-title">External User Log Search</h3>
			</div>

			<div class="panel-body">Log Content</div>
		</div>
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