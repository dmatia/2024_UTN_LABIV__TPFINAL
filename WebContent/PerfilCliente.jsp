<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="entidad.Cliente" %>
<%@ page import="entidad.Usuario" %>
<%@ page import="entidad.Cuentas"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Mi Perfil</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="./css/PerfilCliente.css">
</head>
<body>
<% 
    Cliente cliente = (Cliente) request.getAttribute("cliente");
	int idCliente = cliente.getIdCliente();
		HttpSession sesionUsuario = request.getSession();
		Usuario usuario = (Usuario) sesionUsuario.getAttribute("usuario");
	%>
	<div class="container">
				<div class="encabezado">
			<%
				if (usuario != null) {
					String usuarioNombre = usuario.getUsuario();
			%>
			

			<ul class="nav justify-content-between">
			
				<li class="nav-item"><a class="nav-link active"
					href="MenuCliente.jsp">Home</a></li>
					
					<%
					ArrayList<Cuentas> cuentasCliente = null;
					if (sesionUsuario.getAttribute("cuentasCliente") != null) {
						cuentasCliente = (ArrayList<Cuentas>) sesionUsuario.getAttribute("cuentasCliente");
				
					 for(Cuentas c : cuentasCliente){
						 System.out.println(c.toString());
					 }
						
						if (!cuentasCliente.isEmpty()) {
				%>
				<li class="nav-item"><a class="nav-link" href="ServletMovimientos?clienteIdCliente=<%= sesionUsuario.getAttribute("idCliente") %>">Mis Movimientos</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="ServletTransferir">Transferencias</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="prestamosCliente.jsp">Pr&eacute;stamos</a>
				</li>
				<% }} %>
				<li class="nav-item"><a class="nav-link" href="ServletUsuarioCliente">Mi perfil</a>
				</li>
				<a href="PerfilCliente.jsp" class="nav-link disabled">Bienvenido,
					<%=usuarioNombre%></a>

				<li class="nav-item"><a class="nav-link" href="ServletLogout">Cerrar
						sesi&oacute;n</a></li>
			</ul>
		</div>


            <h1 class="display-1 w-100 text-center text-uppercase">Mis datos</h1>
        
        
    <div id="DatosCliente">
    <p class="DatosSubtitulo">Usuario</p>
    <br>
    <p>Usuario: <span> <%= cliente.getIdUsuario().getUsuario() %></span></p>
        <br>
    <p class="DatosSubtitulo">Datos Personales</p>
        <br>
    <p>Nombre <span> <%= cliente.getNombre() %></span></p>
    <p>Apellido <span> <%= cliente.getApellido() %></span></p>
    <p>DNI <span> <%= cliente.getDNI() %></span></p>
    <p>CUIL <span> <%= cliente.getCUIL() %></span></p>
    <p>Fecha Nac. <span><%= cliente.getFechaNacimiento() %></span></p>
    <p>Nacionalidad <span> <%= cliente.getNacionalidad() %></span></p>
    <p>Sexo <span> <%= cliente.getSexo() %></span></p>
    <p>Direccion <span> <%= cliente.getDireccion() %></span></p>
    <p>Localidad <span> <%= cliente.getLocalidad() %></span></p>
    <p>Provincia <span> <%= cliente.getProvincia() %></span></p>
    <p class="DatosSubtitulo">Datos de contacto</p>
        <br>
    <p>Correo electr&oacute;nico <span> <%= cliente.getCorreoElectronico() %></span></p>
    <p>Tel&eacute;fono <span> <%= cliente.getTelefono() %></span></p>
    </div>
    
    <a class="btn btn-primary text-center mt-4 btnVolver" href="MenuCliente.jsp" >Volver al menú principal</a>
  	<br>
  
    <% 
    } else {
    %>
    <p>No se encontraron datos del cliente.</p>
    <% 
    }
    %>
</div>
</body>
</html>