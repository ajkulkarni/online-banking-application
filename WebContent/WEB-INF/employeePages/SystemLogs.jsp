<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="employeeHeader.jsp" %>

	<div class="content-wrapper">
		<div class="col-md-12" id="page-content">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Filters</h3>
				</div>
				<div class="panel-body">
					<div class="col-lg-7">
						<form class="form-horizontal" action="searchlogs" method='POST' onSubmit="return checkInputOr()">
		      				<label for="userid" class="col-lg-3 control-label">User ID : </label>
							<div class="col-lg-9 form-margin">
		       					<input type="text" class="form-control" name="userid" placeholder="User ID">
		      				</div>
		      				<label for="type" class="col-lg-3 control-label">User Type : </label>
		      				<div class="col-lg-9 form-margin">
     								<select class="form-control" name="type" required>
     									<option value="">Select Type</option>
        								<option value="internal">Employee</option>
        								<option value="external">customer</option>
     								</select>
     							</div>
		      				<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
		      				<br><br>
						    <button type="submit" class="btn btn-primary">Submit</button>
						</form>
					</div>
				</div>
			</div>
				<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Employee Logs</h3>
				</div>
				<div class="panel-body no-padding" style="overflow-y: scroll; max-height:400px;">
					<table id="content-table">
						<thead>
							<tr>
								<th class="active">Employee ID</th>
								<th class="active">Activity</th>
								<th class="active">Details</th>
								<th class="active">Time Stamp</th>
							</tr>
						</thead>
						<tbody>
						<c:choose>
                        		<c:when test="${empty internal_log}">
                        			<tr>
                                    	<td colspan="2">No Logs</td>
                                	</tr>
                        		</c:when>
                        		<c:otherwise>
                        			<c:forEach items="${internal_log}" var="log">
										<tr>
											<td style="text-align:center">${log.userid}</td>
											<td style="text-align:center">${log.activity}</td>
											<td style="text-align:center">${log.details}</td>
											<td style="text-align:center">${log.timestamp}</td>
										</tr>
									</c:forEach>
                        		</c:otherwise>
                        	</c:choose>	
						</tbody>
					</table>
				</div>
			</div>
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Customer Logs</h3>
				</div>
				<div class="panel-body no-padding" style="overflow-y: scroll; max-height:400px;">
					<table id="content-table">
						<thead>
							<tr>
								<th class="active">Employee ID</th>
								<th class="active">Activity</th>
								<th class="active">Details</th>
								<th class="active">Time Stamp</th>
							</tr>
						</thead>
						<tbody>
						<c:choose>
                        		<c:when test="${empty external_log}">
                        			<tr>
                                    	<td colspan="2">No Logs</td>
                                	</tr>
                        		</c:when>
                        		<c:otherwise>
                        			<c:forEach items="${external_log}" var="log">
										<tr>
											<td style="text-align:center">${log.userid}</td>
											<td style="text-align:center">${log.activity}</td>
											<td style="text-align:center">${log.details}</td>
											<td style="text-align:center">${log.timestamp}</td>
										</tr>
									</c:forEach>
                        		</c:otherwise>
                        	</c:choose>
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