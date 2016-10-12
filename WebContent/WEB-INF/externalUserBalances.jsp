<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>External user dashboard</title>
</head>
<body>
<table border="1">
		<tr>
			<th>Savings Account</th>
			<th>Checkings Account</th>
		</tr>
			<tr>
				<td>${balances.get(0)}</td>
				<td>${balances.get(1)}</td>
			</tr>
	</table>

</body>
</html>