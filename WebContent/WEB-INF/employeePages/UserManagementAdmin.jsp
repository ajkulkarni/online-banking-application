<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="employeeHeader.jsp" %>
    <div class="content-wrapper">
        <div class="col-md-12" id="page-content">
        	<c:if test="${not empty message}">
			<div class="alert alert-dismissible alert-success">
  				<button type="button" class="close" data-dismiss="alert">&times;</button>
  				<strong>${message}</strong>
			</div>
			</c:if>
			<c:if test="${not empty error_msg}">
			<div class="alert alert-dismissible alert-success">
  				<button type="button" class="close" data-dismiss="alert">&times;</button>
  				<strong>${error_msg}</strong>
			</div>
			</c:if>
			<c:if test="${not empty error_message}">
			<div class="alert alert-dismissible alert-success">
  				<button type="button" class="close" data-dismiss="alert">&times;</button>
  				<strong>${error_message}</strong>
			</div>
			</c:if>
            <form action = "internalregistrationform" method = "post" style="float:right;">
	       		<input type="hidden" name="userType" value="external">
	       		<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
	       		<button type="submit" class="btn btn-sm btn-primary">New Registration</button>
            </form>
            <h3>Employee Search</h3>
            <form class="form-margin" action = "searchinternaluser" method = "post">
            	<div class="col-md-3" style="padding-left:0px">
            		<input class="form-control" type="text" name="employeeID" placeholder="Employee ID" required>
            	</div>
	       		<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
	       		<button type="submit" class="btn btn-sm btn-primary">Search Employee</button>
            </form>
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">Search Results</h3>
                </div>
                <div class="panel-body no-padding">
                    <table id="content-table">
                        <thead>
                            <tr>
                                <th class="active">Employee ID</th>
                                <th class="active">Employee Email</th>
                                <th class="active">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<tr>
                        		<c:choose>
	                        		<c:when test="${employeeObj.id == 0 || empty employeeObj}">
	                        			<tr>
	                                    	<td colspan="3">No Results</td>
	                                	</tr>
	                        		</c:when>
                        		<c:otherwise>
                                		<tr>
                                			<td style="text-align:center">${employeeObj.id}</td>
                                			<td style="text-align:center">${employeeObj.email}</td>
                                			<td style="text-align:center">
                                				<form action = "viewaccountdetails" method = "post" style="display:inline">
		                                    		<input type="hidden" name="extUserID" value="${employeeObj.id}">
		                                    		<input type="hidden" name="userType" value="internal">
		                                    		<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
		                                    		<button type="submit" class="btn btn-sm btn-primary">View Account</button>
		                                    	</form>
                                			</td>
                                		</tr>
                        		</c:otherwise>
                        	</c:choose>
                        		<td></td>
                        	</tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <br><br>	
            <h3>Customer Search</h3>
            <form class="form-margin" action = "searchexternaluser" method = "post">
            	<div class="col-md-3" style="padding-left:0px">
            		<input class="form-control" type="text" name="customerID" placeholder="Customer ID">
            	</div>
	       		<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
	       		<button type="submit" class="btn btn-sm btn-primary">Search Customer</button>
            </form>
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">Search Results</h3>
                </div>
                <div class="panel-body no-padding">
                    <table id="content-table">
                        <thead>
                            <tr>
                                <th class="active">Customer ID</th>
                                <th class="active">Customer Email</th>
                                <th class="active">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<tr>
                        		<c:choose>
	                        		<c:when test="${customerObj.id == 0 || empty customerObj}">
	                        			<tr>
	                                    	<td colspan="3">No Results</td>
	                                	</tr>
	                        		</c:when>
                        		<c:otherwise>
                                		<tr>
                                			<td style="text-align:center">${customerObj.id}</td>
                                			<td style="text-align:center">${customerObj.email}</td>
                                			<td style="text-align:center">
                                				<form action = "viewaccountdetails" method = "post" style="display:inline">
		                                    		<input type="hidden" name="extUserID" value="${customerObj.id}">
		                                    		<input type="hidden" name="userType" value="external">
		                                    		<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
		                                    		<button type="submit" class="btn btn-sm btn-primary">View Account</button>
		                                    	</form>
                                			</td>
                                		</tr>
                        		</c:otherwise>
                        	</c:choose>
                        		<td></td>
                        	</tr>
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
<script src="<c:url value="/resources/js/custom.js" />"></script>
</body>
</html>