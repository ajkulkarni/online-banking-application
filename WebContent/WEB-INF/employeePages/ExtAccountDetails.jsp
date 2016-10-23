<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="employeeHeader.jsp" %>

	<div class="content-wrapper">
		<div class="col-md-12" id="page-content">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Customer Details</h3>
				</div>
				<div class="panel-body no-padding">
					<table id="content-table">
						<tbody>
							<tr>
								<td class="active" style="width:30%">Customer ID</td>
								<td>${extUserObj.id}</td>
							</tr>
							<tr>
								<td class="active">Name</td>
								<td>${extUserObj.name}</td>
							</tr>
							<tr>
								<td class="active">Email</td>
								<td>${extUserObj.email}</td>
							</tr>
							<tr>
								<td class="active">Phone</td>
								<td>${extUserObj.phone}</td>
							</tr>	
							<tr>
								<td class="active">Address</td>
								<td>${extUserObj.address} ${extUserObj.city} ${extUserObj.state} ${extUserObj.country} ${extUserObj.pincode}</td>
							</tr>
						</tbody>
					</table>
				</div>					
			</div>
			<a href="#modifyaccount" class="btn btn-primary btn-sm" style="padding-left: 5px;" data-toggle="modal">Modify Account</a>
			<form action = "deletecustomer" method = "post" style="float:right">
                <input type="hidden" name="extUserID" value="${extUserObj.id}">
                <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
                <button type="submit" class="btn btn-sm btn-danger">Delete Account</button>
            </form>
		</div>
		<div class="modal" id="modifyaccount">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-body no-padding">
		        <div class="panel panel-success no-margin">
				  <div class="panel-heading">
				    <h3 class="panel-title">Edit User Details</h3>
				  </div>
				  <div class="panel-body">
				    <form class="form-horizontal">
				    	<fieldset>
				    		<div class="form-group">
				    			<label for="requestType" class="col-lg-2 control-label">Email</label>
			    				<div class="col-lg-10">
       								<select class="form-control" name="requestType">
          								<option>Phone Change</option>
          								<option>Email Change</option>
          								<option>Address Change</option>
       								</select>
       							</div>
				    		</div>
				    	</fieldset>
				    </form>
				  </div>
				</div>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		        <button type="submit" class="btn btn-primary">Save changes</button>
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