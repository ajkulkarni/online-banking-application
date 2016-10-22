<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="employeeHeader.jsp" %>

	<div class="content-wrapper">
		<div class="col-md-12" id="page-content">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">User Details</h3>
				</div>
				<div class="panel-body no-padding">
					<table id="content-table">
						<tbody>
							<tr>
								<td class="active" style="width:30%">User ID</td>
								<td>${user.id}</td>
							</tr>
							<tr>
								<td class="active">Name</td>
								<td>${user.name}</td>
							</tr>
							<tr>
								<td class="active">Designation</td>
								<td>${user.designation}</td>
							</tr>
							<tr>
								<td class="active">Email</td>
								<td>${user.email}</td>
							</tr>
							<tr>
								<td class="active">Phone</td>
								<td>${user.phone}</td>
							</tr>	
							<tr>
								<td class="active">Address</td>
								<td>${user.address} ${user.city} ${user.state} ${user.country} ${user.pincode}</td>
							</tr>
						</tbody>
					</table>
				</div>					
			</div>
			<a href="#modifyaccount" style="padding-left: 5px;" data-toggle="modal">Modify Account</a>
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