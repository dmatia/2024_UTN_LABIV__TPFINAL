<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="entidad.Cliente" %>
<%@ page import="entidad.Usuario" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Datos del cliente</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="./css/AdminVerInfoCliente.css">
</head>
<body>

<div class="container">
		<div class="encabezado">
			<%	
			HttpSession sesionUsuario = request.getSession();
			Usuario usuario = (Usuario) sesionUsuario.getAttribute("usuario");
	
				if (usuario != null) {
					String usuarioNombre = usuario.getUsuario();
			%>

			<ul class="nav justify-content-between">
				<li class="nav-item"><a class="nav-link active"
					href="menuAdmin.jsp">Home</a></li>
				<li class="nav-item"><a class="nav-link" href="AltaUsuario.jsp">Alta Cliente</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="ServletCliente?Param=1">Listar Clientes</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="ServletMovimientos?TodosMovimientos=1">Movimientos</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="AdminAltaCuenta.jsp">Alta Cuenta</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="Reportes.jsp">Reportes</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="ServletPrestamos">Pr&eacute;stamos</a>
				</li>
				<a href="PerfilCliente.jsp" class="nav-link disabled">Bienvenido,
					<%=usuarioNombre%></a>

				<li class="nav-item"><a class="nav-link" href="ServletLogout">Cerrar
						sesi&oacute;n</a></li>
			</ul>
		</div>
	
<h1 class="text-center display-4 w-100 text-uppercase ">INFO DEL CLIENTE</h1>

<% 
    Cliente cliente = (Cliente) request.getAttribute("cliente");
    
    if (cliente != null) {
    %>
    <div class=" text-uppercase contenedorInfo">
    
    <div class="infoUser  bg-dark"><p class="h2 text-uppercase text-white">Informaci&oacute;n de usuario</p></div>
    <div class="idCliente"><label>ID Cliente: </label><span id="idCliente"><%= cliente.getIdCliente() %></span><br></div>
    <div class="idUsuario"><label>ID Usuario: </label><span id="idUsuario"><%= cliente.getIdUsuario().getId() %></span><br></div>
    <div class="infoPerson  bg-dark"><p class="h2 text-uppercase text-white">INFO PERSONAL </p></div>
    <div class="dni"><label>DNI: </label><span id="dni">  <%= cliente.getDNI() %></span><br></div>
    <div class="cuil"><label>CUIL: </label><span id="cuil"> <%= cliente.getCUIL() %></span><br></div>
    <div class="nombreapellido "><label>Nombre: </label><span id="nombre"><span id="apellido"> <%= cliente.getApellido() %></span>, <%= cliente.getNombre() %></span> </div>
    <div class="sexo"><label>Sexo: </label><span id="sexo"> <%= cliente.getSexo() %></span><br></div>
    <div class="nacionalidad"><label>Nac: </label><span id="nacionalidad"> <%= cliente.getNacionalidad() %></span><br></div>
    <div class="fechanac"><label>Fecha Nac: </label><span id="fechaNacimiento"> <%= cliente.getFechaNacimiento() %></span><br></div>
    <div class="direccion"><label>Dirección: </label><span id="direccion"> <%= cliente.getDireccion() %></span><br></div>
    <div class="localidad"><label>Localidad: </label><span id="localidad"> <%= cliente.getLocalidad() %></span><br></div>
    <div class="provincia"><label>Provincia: </label><span id="provincia"> <%= cliente.getProvincia() %></span><br></div>
    <div class="email"><label>Email: </label><span id="correoElectronico"> <%= cliente.getCorreoElectronico() %></span></div>
    <div class="telefono"><label>Teléfono: </label><span id="telefono"> <%= cliente.getTelefono() %></span><br></div>

</div>
   
    <% 
    } else {
    %>
    <p>No se encontraron datos del cliente.</p>
    <% 
    }
				}
    %>
    
    <a href="ServletCliente?Param=1" class="btn btn-primary text-center mt-4">Volver</a>
</div>
</body>
</html>