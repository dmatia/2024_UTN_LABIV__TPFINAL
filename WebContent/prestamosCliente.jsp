<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="entidad.Usuario"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- <link rel="stylesheet" href="./css/styleAdmin.css"> -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="./css/prestamosCliente.css">

<title>Prestamos</title>
</head>
<body>
	<%
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
				<li class="nav-item"><a class="nav-link" href="ServletMovimientos?clienteIdCliente=<%= sesionUsuario.getAttribute("idCliente") %>">Mis Movimientos</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="ServletTransferir">Transferencias</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="prestamosCliente.jsp">Pr&eacute;stamos</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="ServletUsuarioCliente">Mi perfil</a>
				</li>
				<a href="PerfilCliente.jsp" class="nav-link disabled">Bienvenido,
					<%=usuarioNombre%></a>

				<li class="nav-item"><a class="nav-link" href="ServletLogout">Cerrar
						sesi&oacute;n</a></li>
			</ul>
		</div>

<!--  ACA EMPIEZA LO ANTERIOR -->
<div class="prestamosClienteContenido">
<h1 class="display-1 w-100 text-center">MEN&Uacute;  PR&Eacute;STAMOS</h1>

   
			<div class="menuPrestamosCliente">
			
				<a class="btn btn-primary"
					href="ServletSolicitarPrestamo">Solicitar Pr&eacute;stamo</a>
					
				<form action="ServletPrestamos" method="POST">
					<input type="hidden" name="VerPrestamos" value="true"> 
					<input  type="submit" class="btn btn-primary w-100 h-100 mt-2" id="verPrestamos"
						value="Ver prestamos y cuotas">
				</form>

			</div>
    <%
        if (request.getAttribute("estado") != null) {
            boolean validado = (boolean) request.getAttribute("estado");
            if (validado) {
    %>
    <div id="myModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <p>Prestamo solicitado con exito.</p>
            <button id="continueButton">Continuar</button>
        </div>
    </div>
    <%
            } else {
    %>
    <div id="myModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <p>No se pudo solicitar el prestamo.</p>
            <button id="continueButton">Continuar</button>
        </div>
    </div>
    
    </div>
    <%
            }
        }
				}
				else{
					 %>
					<h1> No hay usuario logueado</h1>
				<% }
    %>
    
    <script>
        var modal = document.getElementById("myModal");
        var btn = document.getElementById("continueButton");
        var span = document.getElementsByClassName("close")[0];

        modal.style.display = "block";

        span.onclick = function() {
            modal.style.display = "none";
        }

        btn.onclick = function() {
            modal.style.display = "none";
        }

        window.onclick = function(event) {
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }
    </script>
</body>
</html>
