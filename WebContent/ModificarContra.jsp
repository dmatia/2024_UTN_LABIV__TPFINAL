<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="entidad.Cliente" %>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>Modificar Contraseña</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	<link rel="stylesheet" href="./css/ModificarContra.css">
</head>
<body>
	<div class="links">
	  <a href="AdminModificarCliente.jsp">Volver</a>
	</div>
	<div class="menuContra">
    <h1>Modificar Contraseña</h1>
    <%
    HttpSession sesion = request.getSession();
    Cliente cliente = (Cliente) sesion.getAttribute("clienteUsu");
    if (cliente != null && cliente.getIdUsuario() != null) {
	%>
    <p>Nombre de usuario: <strong><%= cliente.getIdUsuario().getUsuario() %></strong></p>
	<%
    } else {
	%>
    <p>Error: No se encontró el cliente en la sesión o el cliente no tiene un IdUsuario.</p>
	<%
    }
	%>
    <form action="serveltCambiarContra" method=post>
        <label for="contra">Nueva Contraseña:</label>
        <input type="password" id="contra" name="contra" required>
        <br>
        <label for="contraRep">Repita la Contraseña:</label>
        <input type="password" id="contraRep" name="contraRep" required>
        <br>
        <input class="btnConfirmar" type="submit" name="btnConfirmar" value="Confirmar">
        <%
    	if (request.getAttribute("contraAux") != null) {
        boolean contraAux = (Boolean) request.getAttribute("contraAux");
        
        if (!contraAux) {
		%>
            <p>Las contraseñas no son iguales.</p>
		<%
        		}
    		}	
		%>
		
		<%
        if (request.getAttribute("validado") != null) {
            boolean validado = (boolean) request.getAttribute("validado");
            if (validado) {
        %>
        <p>Contraseña modificada con exito.</p>
        <%
            } else {
        %>
        <p>No se pudo modificar la contraseña.</p>
        <%
            	}
        	}
    	%>
    </form>
    </div>
</body>
</html>