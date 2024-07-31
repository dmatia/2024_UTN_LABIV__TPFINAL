<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Modificar Clientes</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="./css/ModificarClientes.css">
</head>
<body>
	<div class="container">
		<a href="menuAdmin.jsp" class="d-block text-right">HOME</a>
		<div class="row justify-content-center">
			<h1 class="text-center">Modificar Clientes</h1>
			<form method="post" class="formularioModificarClientes">
				<table class="tablaClientes" border="1">
					<thead>
						<tr>
							<th>Nombre</th>
							<th>Apellido</th>
							<th>DNI</th>
							<th>CUIL</th>
							<th>Sexo</th>
							<th>Nac.</th>
							<th>Fecha Nac.</th>
							<th>Direcci&oacute;n</th>
							<th>Localidad</th>
							<th>Provincia</th>
							<th>Email</th>
							<th>Telefono</th>
							<th></th>
							<th></th>
						</tr>
					</thead>
					<tbody>

						<tr>
							<td>Juan</td>
							<td>López</td>
							<td>39411214</td>
							<td>27394112141</td>
							<td>M</td>
							<td>ARG</td>
							<td>31/01/1990</td>
							<td>Beltran 123</td>
							<td>CABA</td>
							<td>BS. AS.</td>
							<td>jlopez@mail.com</td>
							<td>1132541512</td>
							<td><button>MODIFICAR</button></td>
							<td><button>BAJA</button></td>
							
						</tr>

					</tbody>
				</table>
				<br> 
				<input class="btn btn-primary btnFinalizar" type="button"
					value="Finalizar">
			</form>
		</div>
	</div>

	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>