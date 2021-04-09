<%@ page import="java.io.*,java.util.*,ma.ensa.Models.Article"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<fmt:setLocale value="<%=request.getLocale()%>" />

<fmt:setBundle basename="ma.ensa.Resources.messages"
	var="resourceBundle" />
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
<title><fmt:message key="catalogue" bundle="${resourceBundle}" /></title>
</head>

<body>
	<div class='container mt-4'>
		<div>
			<a class="h6" href="acceuil">&larr;<fmt:message key="acceuil"
					bundle="${resourceBundle}" /></a><br>
		</div>
		<c:if test="${not empty erreur}">
			<div class="alert alert-danger alert-dismissible fade show"
				role="alert">
				<strong> <fmt:message key="panierVide"
						bundle="${resourceBundle}" />
				</strong>
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		</c:if>

		<h2>
			<fmt:message key="catalogue" bundle="${resourceBundle}" />
		</h2>

		<table style="width: 80%" class="table table-bordered">
			<tr>
				<th scope="col"><fmt:message key="designation"
						bundle="${resourceBundle}" /></th>
				<th scope="col"><fmt:message key="categorie"
						bundle="${resourceBundle}" /></th>
				<th scope="col"><fmt:message key="photo"
						bundle="${resourceBundle}" /></th>
				<th scope="col"><fmt:message key="prix"
						bundle="${resourceBundle}" /></th>
				<th scope="col"><fmt:message key="commander"
						bundle="${resourceBundle}" /></th>
			</tr>
			<c:forEach items="${requestScope.catalogue}" var="article">
				<tr>
					<th scope="row"><a href="infos?code=${article.code}">
							${article.designation} </a></th>
					<td>${article.categorie}</td>
					<td class="tdimg"><img class="img" src="${article.photo}"
						alt="${article.designation}" width="30%"></img></td>
					<td><fmt:formatNumber value="${article.prix}" type="currency" /></td>
					<td><a href="achat?code=${article.code}"><fmt:message
								key="ajouterPanier" bundle="${resourceBundle}" /></a></td>
				</tr>
			</c:forEach>
		</table>
		<a href="achat" class="btn btn-primary active" role="button"
			aria-pressed="true"><fmt:message key="visualiserPanier"
				bundle="${resourceBundle}" /></a>
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