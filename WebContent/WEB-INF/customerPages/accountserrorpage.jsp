<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="customerHeader.jsp" %>
<script src="<c:url value="/resources/js/jspdf.js" />"></script>
<script src="<c:url value="/resources/js/jspdf.table.js" />"></script>

	<div class="content-wrapper">
	<div>${errorMessage}</div>
	    <div class="col-sm-6"><a href="home"><button type="button" class="btn btn-primary" style="float:right">Go back to Dashboard</button></a></div>
	
	</div>
<script type="text/javascript">
	$(document).ready(function() {
		sideNavigationSettings();
	});
</script>

</body>
</html>