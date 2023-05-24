<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!-- c:out ; c:forEach etc. -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Formatting (dates) -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>TITULO AQUI</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/main.css">
<!-- change to match your file/naming structure -->
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/app.js"></script>
<!-- change to match your file/naming structure -->
</head>
<body>
	<div class="container">
		<h2>${ event.name }</h2>
		<div class="event-details-side">
			<h4>
				<strong>Host:</strong> ${ event.planner.firstName }
			</h4>
			<h4>
				<strong>Date:</strong> ${ event.eventDate }
			</h4>
			<h4>
				<strong>Location:</strong> ${ event.city }, ${ event.state }
			</h4>
			<h4>
				<strong>People attending:</strong> ${ event.attendees.size() }
			</h4>
			<table class="table table-hover">
				<thead>
					<tr>
						<th>Name</th>
						<th>Location</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${ event.attendees }" var="user">
						<tr>
							<td>${ user.firstName }${ user.lastName }</td>
							<td>${ user.city },${ user.state }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="event-details-side">
			<h3>Message Wall</h3>
			<div class="messages">
				<c:forEach items="${ event.messages }" var="message">
					<p>${ message.author.firstName }says: ${ message.content }</p>
				</c:forEach>
			</div>
			<form action="/events/${ event.id }/comment" method="post">
				<div class="form-group">
					<label for="comment">Add Comment</label> <span>${ error }</span>
					<textarea name="comment" id="comment" class="form-control"></textarea>
					<button>Submit</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
