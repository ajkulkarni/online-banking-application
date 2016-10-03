<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="customerHeader.jsp" %>

	<div class="content-wrapper">
		<div class="col-md-12" id="page-content">
			<h1>Customer DashBoard</h1>
			<div class="panel panel-warning">
				<div class="panel-heading">
					<h3 class="panel-title">Panel warning</h3>
				</div>
				<div class="panel-body">
					Panel content
				</div>
			</div>
			<div class="panel panel-success">
				<div class="panel-heading">
					<h3 class="panel-title">Panel success</h3>
				</div>
				<div class="panel-body">
					Panel content
				</div>
			</div>
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Panel success</h3>
				</div>
				<div class="panel-body">
					Panel content
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