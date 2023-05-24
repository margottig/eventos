<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!-- c:out ; c:forEach etc. -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Formatting (dates) -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true"%>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html >
<head>
<meta charset="ISO-8859-1">
<title>Editar Evento</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/main.css">
<!-- change to match your file/naming structure -->
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/app.js"></script>
<!-- change to match your file/naming structure -->
</head>
<body>

	<h2>Editar Evento:</h2>
	<form:form action="/${evento.id}" method="post" modelAttribute="event">
		<input type="hidden" name="_method" value="put">
		<form:hidden value="${ usuario.id }" path="organizador" />
		<div class="form-group">
			<form:label path="nombreEvento">Name</form:label>
			<br>
			<form:errors class="text-danger" path="nombreEvento"></form:errors>
			<form:input value="${evento.nombreEvento }" class="form-control"
				path="nombreEvento"></form:input>
		</div>
		<div class="form-group">
			<form:label path="fechaEvento">Date</form:label>
			<br>
			<form:errors class="text-danger" path="fechaEvento"></form:errors>
			<form:input class="form-control" type="date"
				value="${evento.fechaEvento }" path="fechaEvento"></form:input>
		</div>
		<div class="form-group">
			<form:label path="ciudad">City</form:label>
			<br>
			<form:errors class="text-danger" path="ciudad" />
			<form:input class="form-control" path="ciudad"
				value="${evento.ciudad }" />
		</div>
		<div class="form-group">
			<form:label path="estado">State</form:label>
			<br>
			<form:errors class="text-danger" path="estado" />
			<form:select class="form-control" path="estado">
				<option value="${ evento.estado }">${ evento.estado }</option>
				<c:forEach items="${ estados }" var="estado">
					<option value="${ estado }">${ estado }</option>
				</c:forEach>
<%-- 				<c:forEach items="${ estados }" var="estado"> --%>
<%-- 					<c:choose> --%>
<%-- 						<c:when test="${estado.equals(evento.estado)}"> --%>
<%-- 							<option value="${ estado }">${ estado }</option> --%>
<%-- 						</c:when> --%>
<%-- 						<c:otherwise> --%>
<%-- 							<option value="${ estado }">${ estado }</option> --%>
<%-- 						</c:otherwise> --%>
<%-- 					</c:choose> --%>
<%-- 				</c:forEach> --%>
			</form:select>
		</div>
		<button>Editar Evento</button>
	</form:form>
	</div>

</body>
</html>
