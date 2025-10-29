<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="entidad.Cuentas"%>
<%@ page import="entidad.Usuario"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Menu de Cuentas</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link rel="stylesheet" href="./css/MenuCliente.css">
</head>
<body>
	<div class="container">

		<!-- DATOS DEL USUARIO LOGUEADO -->
		<div class="links">
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

		<!-- CONTENIDO DEL MENU PRINCIPAL -->
		<div id="Menu">
			<h1 class="display-1 w-100 text-center">MEN&Uacute; PRINCIPAL</h1>


			<div id="CuentasCliente">
				<%
					ArrayList<Cuentas> cuentasCliente = null;
					if (sesionUsuario.getAttribute("cuentasCliente") != null) {
						cuentasCliente = (ArrayList<Cuentas>) sesionUsuario.getAttribute("cuentasCliente");
						if (!cuentasCliente.isEmpty()) {
				%>
				<p class="text-left text-uppercase">Seleccione una cuenta para ver sus
					movimientos</p>

				<%
					
						for (Cuentas cuenta : cuentasCliente) {
				%>
				<form action="ServletMovimientos" method="POST">
					<input type="hidden" name="idCuenta"
						value="<%=cuenta.getNumeroCuenta_Cue()%>">
					<button type="submit" name="btnVerMovimientosCuenta"
						class="btn btn-primary btn-lg btn-block">
						<%=cuenta.getTipoCuenta().getTipoCuentaDescripcion()%>
						[CBU:<%=cuenta.getCBU()%>]
					</button>
				</form>

				<%
					}
				%>
			</div>


		</div>
		<p class="text-left pt-4 text-uppercase">Otras secciones</p>
		<div id="Secciones">
			<a name="btnMisMovimientos" class="btn btn-primary btnSeccion" href="ServletMovimientos?clienteIdCliente=<%= sesionUsuario.getAttribute("idCliente") %>">Mis Movimientos</a>
			<a name="btnMisPrestamos" class="btn btn-primary btnSeccion" href="prestamosCliente.jsp">Mis Prestamos</a> 
			<a name="btnRealizarTransferencia" class="btn btn-primary btnSeccion" href="ServletTransferir">Realizar transferencia</a>

<!-- 			<form action="ServletCuentas" method="POST"> -->
<!-- 				<input type="hidden" name="idCliente" value="IdUsuario"> <input -->
<!-- 					type="submit" name="btnSolicitarCuenta" -->
<!-- 					class="btn btn-primary  btnSeccion" value="Solicitar Nueva Cuenta"> -->
<!-- 			</form> -->
				<% 	} else {
				%>
				<p class="text-uppercase">No se encontraron cuentas para el cliente.</p>
				<%
					} 
					}
				%>
			<a href="ServletUsuarioCliente" class="btn btn-primary btnSeccion">Mi
				Perfil</a>
		</div>
	</div>
	
<!-- 	MODALES  -->
	<%
        if (request.getAttribute("estadoTrans") != null) {
            boolean validado = (boolean) request.getAttribute("estadoTrans");
            if (validado) {
    %>
    <div id="myModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <p>Transferencia realizada con exito.</p>
            <button id="continueButton">Continuar</button>
        </div>
    </div>
    <%
            } else {
    %>
    <div id="myModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <p>No se pudo realizar la transferencia.</p>
            <button id="continueButton">Continuar</button>
        </div>
    </div>
     <%
            }
           
        }
	// FIN MODALES
	
					} else {
					response.sendRedirect("Index.jsp");
				}
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
