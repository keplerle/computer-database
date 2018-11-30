<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
			<a class="navbar-brand" href="dashboard"><spring:message
					code="banner" /></a>
					<div class="btn-group btn-group-sm pull-right" role="group">
			<a href="?lang=en"><button type="button" class="btn btn-default"><spring:message code="english" /></button></a>
			<a href="?lang=fr"><button type="button" class="btn btn-default"><spring:message code="french" /></button></a>
			</div>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1><spring:message code="loginTitle" /></h1>
					<c:if test="${param.error != null}">
						<div>
							<spring:message code="errorLoginMessage" />
						</div>
					</c:if>
					<c:if test="${param.logout != null}">
						<div><spring:message code="logoutMessage" /></div>
					</c:if>

					<form:form id="userForm" action="user" method="POST"
						modelAttribute="userDto">
						<fieldset>
							<div class="form-group">
								<form:label for="username" path="username">
									<spring:message code="usernameLabel" />
								</form:label>
								<form:input type="text" name="username" class="form-control"
									id="username" path="username" />
							</div>
							<div class="form-group">
								<form:label for="password" path="saltedPassword">
									<spring:message code="passwordLabel" />
								</form:label>
								<form:input type="password" name="password" class="form-control"
									id="password" path="saltedPassword" />
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Submit" class="btn btn-primary">
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
	<script src="./js/jquery.min.js"></script>
	<script src="./js/jquery.validate.min.js"></script>
</body>
</html>