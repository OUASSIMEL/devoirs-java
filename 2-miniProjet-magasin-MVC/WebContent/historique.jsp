<%@ page
	import="java.io.*,java.util.*,ma.ensa.Models.Article"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
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
<style type="text/css">
.tdimg {
	width: 250px;
	text-align: center;
	vertical-align: middle;
}

.img {
	max-height: 100%;
	max-width: 100%
}
</style>
<title><fmt:message key="commandeHistorique"
		bundle="${resourceBundle}" /></title>
</head>

<body>
	<div class='container mt-4'>
		<div>
			<a class="h6" href="acceuil">&larr;<fmt:message key="acceuil"
					bundle="${resourceBundle}" /></a><br>
		</div>
		<h2>
			<fmt:message key="catalogue" bundle="${resourceBundle}" />
		</h2>
		<table style="width: 80%" class="table table-bordered">
			<tr>
				<th scope="col"><fmt:message key="codeCommande"
						bundle="${resourceBundle}" /></th>
				<th scope="col"><fmt:message key="date"
						bundle="${resourceBundle}" /></th>
				<th scope="col"><fmt:message key="nomClient"
						bundle="${resourceBundle}" /></th>
				<th scope="col"><fmt:message key="elements"
						bundle="${resourceBundle}" /></th>
			</tr>
			<c:forEach items="${requestScope.commandes}" var="commande">
				<tr>
					<th scope="row">${commande.code}</th>
					<td>${commande.date}</td>
					<td>${commande.client.nom} ${commande.client.prenom}</td>
					<td><ul>
							<c:forEach items="${commande.articles}" var="article">
								<li>${article.designation}-${article.prix}</li>
							</c:forEach>
						</ul></td>
				</tr>
			</c:forEach>
		</table>

		<a href="catalogue" class="btn btn-primary active" role="button"
			aria-pressed="true"> <fmt:message key="consulterCatalogue"
				bundle="${resourceBundle}" />
		</a>
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