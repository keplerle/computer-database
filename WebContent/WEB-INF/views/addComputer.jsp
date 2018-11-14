<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>
<title><spring:message code="title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="./css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="./css/font-awesome.css" rel="stylesheet" media="screen">
<link href="./css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"><spring:message code="banner" /></a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1><spring:message code="addComputer" /></h1>
					<form id="addForm" action="add" method="POST">
						<fieldset>
							<div class="form-group">
								<label for="computerName"><spring:message code="computerName" /></label> <input
									type="text" class="form-control" id="computerName"
									name="computerName" placeholder="Computer name">
							</div>
							<div class="form-group">
								<label for="introduced"><spring:message code="introducedDate" /></label> <input
									type="date" class="form-control" id="introduced"
									name="introduced" placeholder="Introduced date">
							</div>
							<div class="form-group">
								<label for="discontinued"><spring:message code="discontinuedDate" /></label> <input
									type="date" class="form-control" id="discontinued"
									name="discontinued" placeholder="Discontinued date">
							</div>
							<div class="form-group">
								<label for="companyId"><spring:message code="company" /></label> <select
									class="form-control" name="companyId" id="companyId">
									<option value="0">--</option>
									<c:forEach var="company" items="${companies}">
										<option value="${company.id }"><c:out
												value="${company.name }" /></option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="<spring:message code="add" />" class="btn btn-primary">
							or <a href="dashboard" class="btn btn-default"><spring:message code="cancel" /></a>
						</div>
					</form>
					<div><c:out value="${internError}" /></div>
				</div>
			</div>
		</div>
	</section>
	<script src="./js/jquery.min.js"></script>
	<script src="./js/jquery.validate.min.js"></script>
	<script src="./js/validator.js"></script>
</body>
</html>