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
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>
						<spring:message code="addComputer" />
					</h1>
					<form:form id="addForm" action="add" method="POST"
						modelAttribute="computerDto">
						<fieldset>
							<div class="form-group">
								<form:label for="computerName" path="name">
									<spring:message code="computerName" />
								</form:label>
								<form:input type="text" name="computerName" class="form-control"
									id="computerName" path="name" placeholder="Computer name" />
							</div>
							<div class="form-group">
								<form:label for="introduced" path="introduced">
									<spring:message code="introducedDate" />
								</form:label>
								<form:input type="date" class="form-control"
									id="introduced" name="introduced" placeholder="Introduced date"
									path="introduced" />
							</div>
							<div class="form-group">
								<form:label for="discontinued" path="discontinued">
									<spring:message code="discontinuedDate" />
								</form:label>
								<form:input type="date" class="form-control"
									id="discontinued" name="discontinued"
									placeholder="Discontinued date" path="discontinued" />
							</div>
							<div class="form-group">
								<form:label for="companyId" path="companyId">
									<spring:message code="company" />
								</form:label>
								<form:select class="form-control" name="companyId"
									id="companyId" path="companyId">
									<form:option value="0">--</form:option>
									<c:forEach var="company" items="${companies}">
										<form:option value="${company.id }"><c:out
												value="${company.name }" /></form:option>
									</c:forEach>
								</form:select>

							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="<spring:message code="add" />"
								class="btn btn-primary"> <spring:message code="or" /> <a href="dashboard"
								class="btn btn-default"><spring:message code="cancel" /></a>
						</div>
					</form:form>
					<div>
						<c:out value="${internError}" />
					</div>
				</div>
			</div>
		</div>
	</section>
	<script src="./js/jquery.min.js"></script>
	<script src="./js/jquery.validate.min.js"></script>
	<script src="./js/validator.js"></script>
</body>
</html>