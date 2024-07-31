<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="entidad.Usuario"%>
<%@ page import="entidad.Cuentas"%>
<%@ page import="entidad.Prestamos"%>
<%@ page import="entidad.PagoPrestamo"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="java.util.Locale"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="./css/PrestamosYCuotas.css">
<title>Pr&eacute;stamos y cuotas</title>
</head>
<body>

	<%
		HttpSession sesionUsuario = request.getSession();
		Usuario usuario = (Usuario) sesionUsuario.getAttribute("usuario");
	%>
	<div class="container">
	
	
				<%
			if(request.getAttribute("mensajePagoPrestamo")!=null){
				%>

				 <script>
       			 alert('<%= request.getAttribute("mensajePagoPrestamo") %>');
    				 </script>
				
				<%
			} %>
	
	
	
		<%
			if (usuario != null) {
				String usuarioNombre = usuario.getUsuario();
				ArrayList<Cuentas> cuentasCliente = ((ArrayList<Cuentas>) sesionUsuario.getAttribute("cuentasCliente"));

		%>
		<div class="encabezado">
			<ul class="nav justify-content-between">
				<li class="nav-item"><a class="nav-link active"
					href="MenuCliente.jsp">Home</a></li>
				<li class="nav-item"><a class="nav-link"
					href="ServletMovimientos?clienteIdCliente=<%=sesionUsuario.getAttribute("idCliente")%>">Mis
						Movimientos</a></li>
				<li class="nav-item"><a class="nav-link"
					href="ServletTransferir">Transferencias</a></li>
				<li class="nav-item"><a class="nav-link"
					href="prestamosCliente.jsp">Pr&eacute;stamos</a></li>
				<li class="nav-item"><a class="nav-link"
					href="ServletUsuarioCliente">Mi perfil</a></li>
				<a href="PerfilCliente.jsp" class="nav-link disabled">Bienvenido,
					<%=usuarioNombre%></a>

				<li class="nav-item"><a class="nav-link" href="ServletLogout">Cerrar
						sesi&oacute;n</a></li>
			</ul>
		</div>

		<div class="prestamosClienteContenido">
			<h1 class="display-2 w-100 text-center">PR&Eacute;STAMOS Y
				CUOTAS</h1>

			<%
				if (sesionUsuario.getAttribute("listaPrestamosCliente") != null) {
						ArrayList<Prestamos> listaPrestamos = (ArrayList<Prestamos>) sesionUsuario
								.getAttribute("listaPrestamosCliente");
						if (!listaPrestamos.isEmpty()) {
							for (Prestamos p : listaPrestamos) {
								if(p.getEstadoSolicitud().equals("Aprobado")){
								NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));
								String saldoImporteSolicitado = currencyFormatter.format(p.getImporteSoli());
								String saldoImporteTotal = currencyFormatter.format(p.getImporteTotal());
			%>
			<form method="POST" action="ServletPagoPrestamo"
				class="formularioPagarPrestamo">
				<table id="detallePrestamo" class="table table-sm">
					<tr>
						<th>PR&Eacute;STAMO</th>
						<th>PLAZO</th>
						<th>FECHA SOLICITUD</th>
						<th>IMPORTE SOLICITADO</th>
						<th>INTERESES</th>
						<th>IMPORTE TOTAL</th>
						<th>CUENTA DESTINO</th>
						<th>ESTADO</th>
					</tr>
					<tr>
						<td># <%=p.getPrestamoID()%></td>
						<td><%=p.getPlazoPagoMeses()%> MESES</td>
						<td><%=p.getFechaPedidoPrestamo()%></td>
						<td><%=saldoImporteSolicitado%></td>
						<td><%=p.getIntereses().getPorcentajes()%>%</td>
						<td><%=saldoImporteTotal%></td>
						<td><%=p.getCuentaDelCliente().getTipoCuenta().getTipoCuentaDescripcion()%></td>
						<td><%=p.getEstadoSolicitud()%></td>
					</tr>
				</table>

				<%
					if (sesionUsuario.getAttribute("listaPagos") != null) {
										ArrayList<PagoPrestamo> listaPagoPrestamo = (ArrayList<PagoPrestamo>) sesionUsuario
												.getAttribute("listaPagos");
										if (!listaPagoPrestamo.isEmpty()) {

				%>
				<label></label>
				<select id="selectCuota" name="cuotaSeleccionada" class="w-40">
					<option class="text-center">SELECCIONE CUOTA A PAGAR</option>
					<%
						for (int i = 0; i < listaPagoPrestamo.size(); i++) {
						
													PagoPrestamo pp = listaPagoPrestamo.get(i);
													
													System.out.println(pp.toString());
													if (pp.getPrestamoID() == p.getPrestamoID()) {
														String importeCuota = currencyFormatter.format(pp.getImporteCuota());
					%>
					<option value="<%=pp.getPagoPrestamoID()%>"
						<%if (pp.getEstadoPago().equals("Pagado")) {%> disabled <%}%>>
						CUOTA #<%=i + 1%> |
						<%=importeCuota%> |
						<%=pp.getEstadoPago()%> | EXP.
						<%=pp.getFechaVencimientoPago()%>
					</option>
					<%
						}
												}
					%>

				</select> 
				<label></label>
				<select id="selectCuenta" name="cuentaSeleccionada" class="w-40">
					<option>SELECCIONE CUENTA A DEBITAR</option>
					<%
						for (Cuentas c : cuentasCliente) {
													String saldoC = currencyFormatter.format(c.getSaldo());
					%>
					<option value="<%=c.getNumeroCuenta_Cue()%>">
						<%=c.getTipoCuenta().getTipoCuentaDescripcion()%> [Disponible
						<%=saldoC%> ]
					</option>
					<%
						}
					%>
				</select>
				<!--                 <label>Usted debe pagar: $ </label>[]<br>  -->
				<div class="d-block text-right">
					<input type="submit" value="Pagar" name="btnPagarPagoPrestamo"
						class="btn btn-primary text-uppercase"> <input
						type="reset" value="Limpiar" class="btn btn-danger text-uppercase">
				</div>
				<%
					}
										else{
											%>
											
											<h1> No tiene pr&eacute;stamos activos.</h1>
											
											<% 
										}
									}else{
										%>
										
										<h1> No se encontraron registros.</h1>
										
										<% 
									}
				%>
			</form>
			<%
				}
							}
						} else {
			%>
			<p class="display-4 text-center">Usted no tiene historial de
				pr&eacute;stamos</p>
			<a class="btn btn-primary btn-lg w-100" href="prestamosCliente.jsp">Volver
				al Men&uacute; Pr&eacute;stamos</a>
			<%
				}
					} else {
			%>
			<h1>Usted no tiene historial de prestamos</h1>
			<%
				}
			%>
		</div>
		<%
			} else {
		%>
		<h1>No hay usuario en sesi&oacute;n</h1>
		<a class="h2" href="Index.jsp"> Iniciar sesi&oacute; </a>
		<%
			}
		%>
	</div>
	
	

	
</body>
</html>







