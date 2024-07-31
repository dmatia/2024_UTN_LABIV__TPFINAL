<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="entidad.Usuario" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="./css/MenuAdmin.css">
<title>Men&uacute; Principal</title>
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
    
    			<ul class="nav justify-content-end">
				<li class="nav-item"><a class="nav-link disabled" href="#">Bienvenido,
						<%=usuarioNombre%></a></li>

				<li class="nav-item"><a class="nav-link active"
					href="ServletLogout">Cerrar sesi&oacute;n</a></li>
			</ul>

</div>
<h1 class="display-1 text-uppercase text-center">Men&uacute; principal</h1>

   
            <a class="btn btn-primary btn-lg w-100 p-3 mt-1" href="AltaUsuario.jsp">Alta Clientes</a>
            <a class="btn btn-primary btn-lg w-100 p-3 mt-1" href="ServletCliente?Param=1">Listar Clientes</a>
            <a class="btn btn-primary btn-lg w-100 p-3 mt-1" href="ServletMovimientos?TodosMovimientos=1">Movimientos</a>
            <a class="btn btn-primary btn-lg w-100 p-3 mt-1" href="AdminAltaCuenta.jsp">Agregar Cuenta a Cliente</a>
            <a class="btn btn-primary btn-lg w-100 p-3 mt-1" href="Reportes.jsp">Reportes</a>
            <a class="btn btn-primary btn-lg w-100 p-3 mt-1" href="ServletPrestamos">Autorizar Prestamos</a>

</div>
<% }else {
    response.sendRedirect("Index.jsp"); // Redirigir a la página de inicio de sesión si no hay usuario en la sesión
} %>
</body>
</html>