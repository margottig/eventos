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
<title>Eventos</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/main.css">
<!-- change to match your file/naming structure -->
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/app.js"></script>
<!-- change to match your file/naming structure -->
</head>
<body>

	<div class="container">
		<h1>Welcome, ${ usuario.nombre }</h1>
		<div class="text-end">
			<a href="/logout">Logout</a>
		</div>
		<hr />
		<h3>Here are some events in your state:</h3>
		<table class="table table-hover">
			<thead>
				<tr>
					<th>Nombre</th>
					<th>Fecha</th>
					<th>Ciudad</th>
					<th>Estado</th>
					<th>Host</th>
					<th>Action/Status</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ estadoUsuario }" var="evento">
					<tr>
						<td><a href="/events/${ evento.id }">${ evento.nombreEvento }</a></td>
						<td><fmt:formatDate value="${evento.fechaEvento}"
								pattern="MMMM dd, yyyy" var="fechaEvento" /> <c:out
								value="${fechaEvento}"></c:out></td>

						<td>${ evento.ciudad }</td>
						<td>${ evento.estado }</td>
						<td>${ evento.organizador.nombre }</td>
						<td><c:choose>
								<c:when test="${ evento.organizador.id == usuario.id }">
									<a href="/events/${ evento.id }/edit">Edit</a> |
									<form class="delete-form" action="/events/${ evento.id }"
										method="post">
										<input type="hidden" name="_method" value="delete" />
										<button>Borrar</button>
									</form>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${ evento.asistentes.contains(usuario) }">Unirse 
											<a href="/event/${ evento.id }/${usuario.id }/cancel"> Cancelar</a>
										</c:when>
										<c:otherwise>
											Cancelar <a href="/event/${ evento.id }/${usuario.id }/unirse">Unirse</a>
										</c:otherwise>

									</c:choose>
								</c:otherwise>
							</c:choose></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<hr />

		<h3>Here are some of the events in other states:</h3>
		<table class="table table-hover">
			<thead>
				<tr>
					<th>Nombre</th>
					<th>Fecha</th>
					<th>Ciudad</th>
					<th>Estado</th>
					<th>Host</th>
					<th>Action/Status</th>

				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ estadoNoUsuario }" var="eventoOtroEstado">
					<tr>
						<td><a href="/events/${ eventoOtroEstado.id }">${ eventoOtroEstado.nombreEvento }</a></td>
						<td><fmt:formatDate value="${eventoOtroEstado.fechaEvento}"
								pattern="MMMM dd, yyyy" var="fechaEvento" /> <c:out
								value="${fechaEvento}"></c:out></td>

						<td>${ eventoOtroEstado.ciudad }</td>
						<td>${ eventoOtroEstado.estado }</td>
						<td>${ eventoOtroEstado.organizador.nombre }</td>
						<td><c:choose>
								<c:when test="${ eventoOtroEstado.organizador.id == usuario.id }">
									<a href="/events/${ eventoOtroEstado.id }/edit">Edit</a> |
									<form class="delete-form" action="/events/${ eventoOtroEstado.id }"
										method="post">
										<input type="hidden" name="_method" value="delete" />
										<button>Borrar</button>
									</form>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${ eventoOtroEstado.asistentes.contains(usuario) }">Unirse 
											<a href="/event/${ eventoOtroEstado.id }/${usuario.id }/cancel"> Cancelar</a>
										</c:when>
										<c:otherwise>
											Cancelar <a href="/event/${ eventoOtroEstado.id }/${usuario.id }/unirse">Unirse</a>
										</c:otherwise>

									</c:choose>
								</c:otherwise>
							</c:choose></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<hr class="text-primary">


		<div class="col-6 mt-2 mb-5">
			<h2>Plan an Event:</h2>
			<form:form action="/new/event" method="post" modelAttribute="event">
				<form:hidden value="${ usuario.id }" path="organizador" />
				<div class="form-group">
					<form:label path="nombreEvento">Name</form:label>
					<br>
					<form:errors class="text-danger" path="nombreEvento"></form:errors>
					<form:input class="form-control" path="nombreEvento"></form:input>
				</div>
				<div class="form-group">
					<form:label path="fechaEvento">Date</form:label>
					<br>
					<form:errors class="text-danger" path="fechaEvento"></form:errors>
					<form:input class="form-control" type="date" value="${ now }"
						path="fechaEvento"></form:input>
				</div>
				<div class="form-group">
					<form:label path="ciudad">City</form:label>
					<br>
					<form:errors class="text-danger" path="ciudad" />
					<form:input class="form-control" path="ciudad" />
				</div>
				<div class="form-group">
					<form:label path="estado">State</form:label>
					<br>
					<form:errors class="text-danger" path="estado" />
					<form:select class="form-control" path="estado">
						<c:forEach items="${ estados }" var="estado">
							<option value="${ estado }">${ estado }</option>
						</c:forEach>
					</form:select>
				</div>
				<button>Crear Evento</button>
			</form:form>
		</div>

	</div>



</body>
</html>
