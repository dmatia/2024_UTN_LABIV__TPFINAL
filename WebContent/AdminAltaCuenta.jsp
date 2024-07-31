<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="entidad.Cuentas"%>
<%@ page import="entidad.Usuario"%>
<%@ page import="entidad.Cliente"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin | Alta cuenta</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="./css/AltaCuenta.css">

<!-- DATA TABLES -->
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" charset="utf8"
	src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>

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

			<!-- 			CONTENIDO DE LA VISTA -->
			<div id="cuerpoClienteSolicitarCuenta">

				<h1 class="display-1 w-100">ALTA CUENTA</h1>

				<%
					if (request.getAttribute("MensajeAltaCuenta") != null) {
							String mensaje = (String) request.getAttribute("MensajeAltaCuenta");
				%>
				<form id="formRedirigir" action="ServletCliente" method="POST">
					<input type="hidden" name="alertMensaje" value="true">
				</form>

				<script type="text/javascript">
    			alert("<%=mensaje%>");
					document.getElementById("formRedirigir").submit();
				</script>

				<%
					}else if (request.getAttribute("Mensaje") != null) {
						String mensaje = (String) request.getAttribute("Mensaje");
						%>
			<form id="formRedirigir" action="ServletCliente" method="POST">
				<input type="hidden" name="alertMensaje" value="true">
			</form>

			<script type="text/javascript">
			alert("<%=mensaje%>");
				document.getElementById("formRedirigir").submit();
			</script>
						
				<%
				} else if (request.getAttribute("ClienteEncontrado") != null) {
							Cliente cliente = (Cliente) request.getAttribute("ClienteEncontrado");
				%>
				<label style="font-size: 2em; font-weight: 100;" for="txtBusqueda">Cliente
					encontrado: </label>
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>Nombre</th>
							<th>Apellido</th>
							<th>DNI</th>
							<th>Email</th>
							<th>Fecha de Nacimiento</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><%=cliente.getNombre()%></td>
							<td><%=cliente.getApellido()%></td>
							<td><%=cliente.getDNI()%></td>
							<td><%=cliente.getCorreoElectronico()%></td>
							<td><%=cliente.getFechaNacimiento()%></td>
						</tr>
					</tbody>
				</table>

				<form action="ServletCliente" method="POST">
					<button id="btnValidarUsuarioEncontrado"
						class="btn btn-primary btn-lg">Aceptar</button>
					<button type="submit" name="btnLimpiarBusqueda"
						class="btn btn-primary btn-lg">Limpiar Busqueda</button>

				</form>

				<form id="formCuentas" action="ServletCuentas" method="POST" style="display: none;">
    <input type="hidden" name="idCliente" value="<%=cliente.getIdCliente()%>"> 
    <label for="cuentaSelect">Selecci&oacute;n tipo de cuenta</label>
    <select class="w-100" id="cuentaSelect" name="idTipoCuenta" disabled>
        <option value="1">Caja Ahorro</option>
        <option value="2">Cuenta Corriente</option>
    </select>
    <button type="submit" name="btnSolicitar" class="btn btn-primary btn-lg btn-block">Dar alta cuenta</button>
</form>

				<%
					} else {
				%>
				<form action="ServletCliente" method="POST">
					<label for="txtBusqueda">B&uacute;squeda cliente</label> <input
						class="d-block  w-100 " type="text" id="txtBusqueda"
						name="txtBusqueda"
						placeholder="Ingrese DNI, CUIL O Email del cliente" value="">
					<button type="submit" name="btnBuscarCliente"
						class="btn btn-primary btn-lg btn-block">Buscar cliente</button>
				</form>

				<%
					}
				%>


				<script>
					document
							.getElementById('btnValidarUsuarioEncontrado')
							.addEventListener(
									'click',
									function() {
										event.preventDefault();
										document.getElementById('formCuentas').style.display = 'block';
										document.getElementById('cuentaSelect').disabled = false;
										document.getElementById('formBusquedaCliente').style.display = 'none';
									});
				</script>


			</div>
			<%
				} else {
			%>
			<h1>No hay usuario</h1>
			<%
				}
			%>

	</div>

</body>
</html>