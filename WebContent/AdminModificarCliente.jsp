<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ page import="entidad.Cliente"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Modificar Cliente</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="./css/AltaUsuario.css">
</head>
<body>
	<div class="container">
	
		<div class="encabezado">
			

			<ul class="nav justify-content-between">

				<li class="nav-item"><a class="nav-link" href="ServletCliente?Param=1">Volver</a></li>
			</ul>
		</div>
	
<h1 class="text-center display-1 w-100 text-uppercase">Modificar Cliente</h1>

		<% 
			String mensaje = (String) request.getAttribute("mensaje");
			String error = (String) request.getAttribute("error");
			if (mensaje != null) {
		%>
			<div class="alert alert-success alert-dismissible fade show" role="alert">
				<%= mensaje %>
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		<% 
			} else if (error != null) {
		%>
			<div class="alert alert-danger alert-dismissible fade show" role="alert">
				<%= error %>
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		<% 
			} 
		%>
					
		<%
	    HttpSession sesion = request.getSession();
	    Cliente cliente = (Cliente) sesion.getAttribute("clienteUsu");
		%>
					
					<form action="ServletModificarCliente" method="post">
						<div class="form-div formRegistro">
								<label for="">Usuario:</label>
								<%
								if (cliente != null && cliente.getIdUsuario() != null) {
								%>
								<label name="usuario" for=""><strong><%= cliente.getIdUsuario().getUsuario() %></strong></label>
								<%
							    } else {
								%>
								<label for=""><strong>No se encontró el cliente</strong></label>
								<%
							    }
								%> 
								
								<label for="">Nombre:</label> 
				<input name="nombre" type="text" value="<%= cliente.getNombre() %>" required>
								
				<label for="">Apellido:</label> 
				<input name="apellido" type="text" value="<%= cliente.getApellido() %>" required> 
								
				<label for="">DNI:</label>
				<input name="dni" type="number" value="<%= cliente.getDNI() %>" required>
								
				<label for="">CUIL:</label> 
				<input name="cuil" type="number" value="<%= cliente.getCUIL() %>" required>
									
				<label for="">Sexo:</label> 
				<select class="sexo" name="genero" id="genero" required>
					<option value="Masculino" <%= cliente.getSexo().equals("Masculino") ? "selected" : "" %>>Masculino</option>
					<option value="Femenino" <%= cliente.getSexo().equals("Femenino") ? "selected" : "" %>>Femenino</option>
				</select> 
								
				<label for="">Fecha de nacimiento:</label> 
				<input name="fechaNac" type="date" value="<%= cliente.getFechaNacimiento() %>" required>
								
				<label for="">Direcci&oacute;n:</label> 
				<input name="direccion" type="text" value="<%= cliente.getDireccion() %>" required>
								
				<label for="">Nacionalidad:</label> 
				<input name="nacionalidad" type="text" value="<%= cliente.getNacionalidad() %>" required> 
								
				<label for="">Provincia:</label> 
				<select name="provincia" id="provincia" required>
					<option value="BuenosAires" <%= cliente.getProvincia().equals("Buenos Aires") ? "selected" : "" %>>Buenos Aires</option>
					<option value="CiudadAutonomaDeBuenosAires" <%= cliente.getProvincia().equals("CiudadAutonomaDeBuenosAires") ? "selected" : "" %>>Ciudad Autonoma de Buenos Aires</option>
					<option value="Catamarca" <%= cliente.getProvincia().equals("Catamarca") ? "selected" : "" %>>Catamarca</option>
					<option value="Chaco" <%= cliente.getProvincia().equals("Chaco") ? "selected" : "" %>>Chaco</option>
					<option value="Chubut" <%= cliente.getProvincia().equals("Chubut") ? "selected" : "" %>>Chubut</option>
					<option value="Córdoba" <%= cliente.getProvincia().equals("Cordoba") ? "selected" : "" %>>Cordoba</option>
					<option value="Corrientes" <%= cliente.getProvincia().equals("Corrientes") ? "selected" : "" %>>Corrientes</option>
					<option value="Entre Ríos" <%= cliente.getProvincia().equals("Entre Ríos") ? "selected" : "" %>>Entre Ríos</option>
					<option value="Formosa" <%= cliente.getProvincia().equals("Formosa") ? "selected" : "" %>>Formosa</option>
					<option value="Jujuy" <%= cliente.getProvincia().equals("Jujuy") ? "selected" : "" %>>Jujuy</option>
					<option value="La Pampa" <%= cliente.getProvincia().equals("La Pampa") ? "selected" : "" %>>La Pampa</option>
					<option value="La Rioja" <%= cliente.getProvincia().equals("La Rioja") ? "selected" : "" %>>La Rioja</option>
					<option value="Mendoza" <%= cliente.getProvincia().equals("Mendoza") ? "selected" : "" %>>Mendoza</option>
					<option value="Misiones" <%= cliente.getProvincia().equals("Misiones") ? "selected" : "" %>>Misiones</option>
					<option value="Neuquén" <%= cliente.getProvincia().equals("Neuquén") ? "selected" : "" %>>Neuquén</option>
					<option value="Río Negro" <%= cliente.getProvincia().equals("Rio Negro") ? "selected" : "" %>>Rio Negro</option>
					<option value="Salta" <%= cliente.getProvincia().equals("Salta") ? "selected" : "" %>>Salta</option>
					<option value="San Juan" <%= cliente.getProvincia().equals("San Juan") ? "selected" : "" %>>San Juan</option>
					<option value="San Luis" <%= cliente.getProvincia().equals("San Luis") ? "selected" : "" %>>San Luis</option>
					<option value="Santa Cruz" <%= cliente.getProvincia().equals("Santa Cruz") ? "selected" : "" %>>Santa Cruz</option>
					<option value="Santa Fe" <%= cliente.getProvincia().equals("Santa Fe") ? "selected" : "" %>>Santa Fe</option>
					<option value="Santiago del Estero" <%= cliente.getProvincia().equals("Santiago del Estero") ? "selected" : "" %>>Santiago del Estero</option>
					<option value="Tierra del Fuego" <%= cliente.getProvincia().equals("Tierra del Fuego") ? "selected" : "" %>>Tierra del Fuego</option>
					<option value="Tucumán" <%= cliente.getProvincia().equals("Tucuman") ? "selected" : "" %>>Tucuman</option>
				</select> 
								
				<label for="">Localidad:</label> 
				<input name="localidad" type="text" value="<%= cliente.getLocalidad() %>" required>
							
				<label for="">Correo electr&oacute;nico:</label> 
				<input name="email" type="text" value="<%= cliente.getCorreoElectronico() %>" required>
									
				<label for="">Tel&eacute;fono:</label>
				<input name="telefono" type="number" value="<%= cliente.getTelefono() %>" required>
								
								<br>
								
								<div class="col-sm-12 text-right">
								<input type="submit" class="btn btn-primary btnRegistro" name="btnModificar" value="Modificar">
								</div>
								
							</div>
							<a class="botonContra" href="ServletCliente?ModContra=<%=cliente.getIdCliente()%>">Modificar contraseña</a>
					</form>
				</div>

	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>