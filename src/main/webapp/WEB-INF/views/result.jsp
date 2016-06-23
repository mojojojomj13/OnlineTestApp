<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Online Exam Health Check</title>
</head>
<body>
	<h1>
		<u>Online Exam App</u>
	</h1>
	<c:choose>
		<c:when test="${status eq 'success'}">
			<h2 style="color: green">${message}</h2>
		</c:when>
		<c:otherwise>
			<h2 style="color: red">${message}</h2>
		</c:otherwise>
	</c:choose>
</body>
</html>