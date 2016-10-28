<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="employeeHeader.jsp" %>

	<div class="content-wrapper">
		<div class="col-md-12" id="page-content">
			<h4>Login Management</h4>
			
			
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Blocked Login Accounts</h3>
				</div>
				<div class="panel-body no-padding" style="overflow-y: scroll; max-height:400px;">
					<table id="content-table">
						<thead>
							<tr>
								<th class="active">Username</th>
								<th class="active">Action</th>
							</tr>
						</thead>
						<tbody>
						<c:choose>
                        		<c:when test="${empty login_list}">
                        			<tr>
                                    	<td colspan="2">No Blocked Accounts</td>
                                	</tr>
                        		</c:when>
                        		<c:otherwise>
                        			<c:forEach items="${login_list}" var="login">
										<tr>
											<td>${login.username}</td>
											<td>
											<form action = "unblocklogin" method = "post">
		                                  		<input type="hidden" name="username" value="${login.username}">
		                                  		<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
		                                  		<button type="submit" class="btn btn-xs btn-primary">Unblock</button>
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
			
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Blocked OTP Accounts</h3>
				</div>
				<div class="panel-body no-padding" style="overflow-y: scroll; max-height:400px;">
					<table id="content-table">
						<thead>
							<tr>
								<th class="active">Username</th>
								<th class="active">Action</th>
							</tr>
						</thead>
						<tbody>
						<c:choose>
                        		<c:when test="${empty otp_list}">
                        			<tr>
                                    	<td colspan="2">No Blocked Accounts</td>
                                	</tr>
                        		</c:when>
                        		<c:otherwise>
                        			<c:forEach items="${otp_list}" var="otp">
										<tr>
											<td>${otp.username}</td>
											<td>
											<form action = "unblockotp" method = "post">
		                                  		<input type="hidden" name="username" value="${otp.username}">
		                                  		<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
		                                  		<button type="submit" class="btn btn-xs btn-primary">Unblock</button>
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
	</div> <!-- .content-wrapper -->
	
</main> 

<script type="text/javascript">
	$(document).ready(function() {
		sideNavigationSettings();
	});
	</script>

</body>
</html>