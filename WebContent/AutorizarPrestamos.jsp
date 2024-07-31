<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entidad.Prestamos" %>
<%@ page import="entidad.Cuentas" %>
<%@ page import="entidad.Intereses" %>
<%@ page import="servlet.ServletPrestamos" %>
<%@ page import="entidad.Usuario"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Autorizar Préstamos</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="./css/AutorizarPrestamos.css">
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
	
<h1 class="text-center display-1 w-100 text-uppercase">AUTORIZAR PR&Eacute;STAMOS</h1>
            
            <% 
                String mensaje = (String) request.getAttribute("mensaje");
                if (mensaje != null) {
            %>
            <div class="alert alert-info" role="alert">
                <%= mensaje %>
            </div>
            <% 
 }
                
                List<Prestamos> listaPrestamos = (List<Prestamos>)sesionUsuario.getAttribute("listaPrestamos");
                if (listaPrestamos != null && !listaPrestamos.isEmpty()) {
                	
                	
            %>
            
            <table class="table table-bordered tablaPrestamos">
                <thead>
                    <tr>
                        <th>ID Préstamo</th>
                        <th>DNI Cliente</th>
                        <th>Importe Solicitado</th>
                        <th>Intereses</th>
                        <th>Importe cuota</th>
                        <th>Importe Total</th>
                        <th>Plazo</th>
                        <th>Depósito Destino</th>
                        <th>Acción</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                            for (Prestamos prestamo : listaPrestamos) {
        	
                    %>
                    <tr>
                        <form action="ServletPrestamos" method="post" class="formularioPrestamo">
                            <td><%= prestamo.getPrestamoID() %></td>
                            <td><%= prestamo.getClienteAsignado().getDNI() %></td>
                            <td><%= prestamo.getImporteSoli() %></td>
                            <td><%= prestamo.getIntereses().getPorcentajes() %>%</td>
                            <td><%= prestamo.getImporteCuota() %></td>
                            <td><%= prestamo.getImporteTotal() %></td>
                            <td><%= prestamo.getPlazoPagoMeses() %> MESES</td>
                            <td><%= prestamo.getCuentaDelCliente().getNumeroCuenta_Cue() %></td>
                            <td>
                                <input type="hidden" name="prestamoID" value="<%= prestamo.getPrestamoID() %>">
                                <button type="submit" name="action" value="Aprobar" class="btn btn-success" onclick="return confirm('¿Está seguro que desea aprobar este préstamo?');">Aprobar</button>
                                <button type="submit" name="action" value="Rechazar" class="btn btn-danger" onclick="return confirm('¿Está seguro que desea rechazar este préstamo?');">Rechazar</button>
                            </td>
                        </form>
                    </tr>
                    <% 
                            }
                        }else{
                        	%>
                        	
                        	<h1 class="text-center"> No hay pr&eacute;stamos pendientes de autorización </h1>
                        	
                        	<%
                        }
                    %>
                </tbody>
            </table>
            <%
            }else{
            
            %>
            
            <h1> No hay usuario en sesi&oacute;n</h1>
            <a class="" href="Index.jsp">Iniciar sesi&oacute;n</a>
            
            
            <%
            }
            
            %>
            
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>