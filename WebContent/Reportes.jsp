<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ page import="javax.servlet.http.HttpSession" %>
	<%@ page import="java.math.BigDecimal" %>
<%@ page import="entidad.Usuario" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registrar Cliente</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="./css/Reportes.css">
</head>
<body>
			<%	
			HttpSession sesionUsuario = request.getSession();
			Usuario usuario = (Usuario) sesionUsuario.getAttribute("usuario");
	
				if (usuario != null) {
					String usuarioNombre = usuario.getUsuario();
			%>
	<div class="container bg-white">
	
			<div class="encabezado">

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
	
					<h1 class="display-1 text-uppercase text-center">Reportes</h1>
					<form action="ServletReporte" method="post">
						<div class="form-div formReportes">
								
		<label for="startDate">Fecha de Inicio:</label>
        <input type="date" id="startDate" name="startDate" required><br><br>
        <label for="endDate">Fecha de Fin:</label>
        <input type="date" id="endDate" name="endDate" required><br><br>
        <label for="IDCliente">ID Cliente:</label>
        <input type="text" id="IDCliente" name="IDCliente" required><br><br>
        <input type="submit" value="Generar Reporte">
        <%-- Agrega este código para depurar --%>
<%
    BigDecimal totalTransferencias = (BigDecimal) request.getAttribute("totalTransferencias");
    System.out.println("Total de Transferencias recibido en el JSP: " + totalTransferencias);
%>
	<h2>Resultados del Reporte:</h2>
	<p>Nombre del Cliente: ${nombreCliente}</p>
    <p>Cantidad de Préstamos aprobados: ${cantidadPrestamos}</p>
    <p>Total de Transferencias: ${totalTransferencias}</p>
    <p>Importe Restante del Préstamo: ${importeRestantePrestamo}</p>
    <%
    BigDecimal saldoEntreFechas = (BigDecimal) request.getAttribute("saldoEntreFechas");
    if (saldoEntreFechas != null) {
%>
    <p>Saldo gastado Entre Fechas: <%= saldoEntreFechas %></p>
<%
    } else {
%>
    <p>Saldo gastado Entre Fechas: No disponible</p>
<%
    }
%>
    <%
    BigDecimal saldoRecibidoEntreFechas = (BigDecimal) request.getAttribute("saldoRecibidoEntreFechas");
    if (saldoEntreFechas != null) {
%>
    <p>Saldo recibido Entre Fechas: <%= saldoRecibidoEntreFechas %></p>
<%
    } else {
%>
    <p>Saldo recibido Entre Fechas: No disponible</p>
<%
    }
%>
				</div>
								
							</div>
					</form>

				<%} %>


	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>