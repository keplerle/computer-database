
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="./css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="./css/font-awesome.css" rel="stylesheet" media="screen">
<link href="./css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application - Computer
				Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">

				<c:out value="${ counter }">0</c:out>
				Computers found
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="dashboard" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" value='${search}' placeholder="Search name" /> <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="add">Add
						Computer</a> 
						   <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Delete</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th>Computer name</th>
						<th>Introduced date</th>
						<!-- Table header for Discontinued Date -->
						<th>Discontinued date</th>
						<!-- Table header for Company -->
						<th>Company</th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${computers}"  var="computer">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computer.getId()}"></td>
							<td><a href="edit?computerId=${computer.getId()}" onclick=""><c:out value="${computer.getName()}"/></a></td>
							<td><c:out value="${computer.getIntroduced()}"/></td>
							<td><c:out value="${computer.getDiscontinued()}"/></td>
							<td><c:out value="${computer.getCompanyName()}"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center"></div>
			<ul class="pagination">
				<li><a href="?page=${pageIndex-1}&size=${pageSize}&search=${search}" aria-label="Previous"> <span
						aria-hidden="true">&laquo;</span>
				</a></li>
				<li><a href="?page=1&size=${pageSize}&search=${search}">1</a></li>
				<li><a href="?page=2&size=${pageSize}&search=${search}">2</a></li>
				<li><a href="?page=3&size=${pageSize}&search=${search}">3</a></li>
				<li><a href="?page=4&size=${pageSize}&search=${search}">4</a></li>
				<li><a href="?page=5&size=${pageSize}&search=${search}">5</a></li>
				<li><a href="?page=${pageIndex+1}&size=${pageSize}&search=${search}" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>
		
		<div class="btn-group btn-group-sm pull-right" role="group">
			<a href="?page=1&size=10&search=${search}"><button type="button" class="btn btn-default">10</button></a>
			<a href="?page=1&size=50&search=${search}"><button type="button" class="btn btn-default">50</button></a>
			<a href="?page=1&size=100&search=${search}"><button type="button" class="btn btn-default">100</button></a>
		</div>

	</footer>
	<script src="./js/jquery.min.js"></script>
	<script src="./js/bootstrap.min.js"></script>
	<script src="./js/dashboard.js"></script>

</body>
</html>