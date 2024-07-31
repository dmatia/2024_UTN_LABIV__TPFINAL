<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entidad.Movimientos" %>
<%@ page import="entidad.Cliente" %>
<%@ page import="entidad.Usuario" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ver movimientos del cliente</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
<link rel="stylesheet" href="./css/ClienteTodosMovimientos.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>

<script type="text/javascript">
$(document).ready(function (){
	var table = $('#tablaMovimientos').DataTable({
		"dom": '<"top"l>rt<"bottom"ip><"clear">',
        "paging": true,
        "lengthChange": true,
        "searching": false,
        "ordering": true,
        "info": true,
        "autoWidth": false
    });

});
</script>

</head>
<body>

<% 
		HttpSession sesionUsuario = request.getSession();
		Usuario usuario = (Usuario) sesionUsuario.getAttribute("usuario");
	%>
	<div class="container pb-2">
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


            <h1 class="display-1 w-100 text-center text-uppercase">Mis Movimientos</h1>
  
<form method="get" action="ServletMovimientos" class="text-center">
    <span class="text-uppercase">Filtrar por mayor o igual </span>
    <input type="text" name="clienteImporteMayorOIgual">
    <input type="hidden" name="clienteIdCliente" value="<%= request.getParameter("clienteIdCliente") %>">
    <input type="submit" value="Buscar" class="btn btn-primary">
</form>

<div class="filtrosMovimiento text-center pt-2">
<span class="text-uppercase">Aplicar Filtros</span>
<form method="get" action="ServletMovimientos">
    <input type="hidden" name="clienteIdCliente" value="<%= request.getParameter("clienteIdCliente") %>">
    <button type="submit" name="tipoMovimientoCli" value="1" class="btn btn-success">Alta de cuenta</button>
</form>
<form method="get" action="ServletMovimientos">
    <input type="hidden" name="clienteIdCliente" value="<%= request.getParameter("clienteIdCliente") %>">
    <button type="submit" name="tipoMovimientoCli" value="2" class="btn btn-success">Alta de préstamo</button>
</form>
<form method="get" action="ServletMovimientos">
    <input type="hidden" name="clienteIdCliente" value="<%= request.getParameter("clienteIdCliente") %>">
    <button type="submit" name="tipoMovimientoCli" value="3" class="btn btn-success">Pago de préstamo</button>
</form>
<form method="get" action="ServletMovimientos">
    <input type="hidden" name="clienteIdCliente" value="<%= request.getParameter("clienteIdCliente") %>">
    <button type="submit" name="tipoMovimientoCli" value="4" class="btn btn-success">Transferencias</button>
</form>
<form method="get" action="ServletMovimientos">
    <input type="hidden" name="clienteIdCliente" value="<%= request.getParameter("clienteIdCliente") %>">
    <button type="submit" name="btnBorrarFiltrosCliente" value="5" class="btn btn-danger">Borrar Filtros</button>
</form>
</div>

<table border="1" id="tablaMovimientos" class="display">
<thead>
<tr>
	<th>ID Movimiento</th>
	<th>Num. de cuenta</th>
	<th>Tipo de movimiento</th>
	<th>Fecha</th> 
	<th>CBU</th> 
	<th>Detalle</th> 
	<th>Importe</th> 
	
</tr>
</thead>

<tbody>
<%
    ArrayList<Movimientos> listaMovimientos = null;
    if (request.getAttribute("clienteListaTodosMovimientos") != null) {
    	listaMovimientos = (ArrayList<Movimientos>) request.getAttribute("clienteListaTodosMovimientos");
    }

    if (listaMovimientos != null) {
        for (Movimientos movimiento : listaMovimientos) {
%>
<tr>
    <td><%= movimiento.getMovimientosID() %></td>
    <td><%= movimiento.getNumeroCuenta() %></td>
    <td><%= movimiento.getTipoMovimientos() %></td>
    <td><%= movimiento.getFechaMov() %></td>
    <td><%= movimiento.getCBUMov() %></td>
    <td><%= movimiento.getDetalleMov() %></td>
    <td><%= movimiento.getImporte_Mov() %></td>
</tr>
<% 
        } 
    }
%>
</tbody>

</table>


          </div>
<% } %>


</body>
</html>
