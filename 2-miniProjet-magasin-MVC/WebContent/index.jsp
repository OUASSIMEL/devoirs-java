<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<fmt:setBundle basename="ma.ensa.Resources.messages"
	var="resourceBundle" />

<meta charset="ISO-8859-1">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
	integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l"
	crossorigin="anonymous">
<title><fmt:message key="acceuil" bundle="${resourceBundle}" /></title>
</head>

<body>
	<div class='container mt-4'>
		<div class='container mt-4'>

			<h3 class="my-3">
				<fmt:message key="bienvenue" bundle="${resourceBundle}" />
			</h3>
			<div class="my-2">
				<a href="identification"><fmt:message key="login"
						bundle="${resourceBundle}" /></a>
			</div>
			<div class="my-2">
				<a href="inscription"><fmt:message key="signup"
						bundle="${resourceBundle}" /></a>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp" />

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
		crossorigin="anonymous"></script>
</body>

</html>