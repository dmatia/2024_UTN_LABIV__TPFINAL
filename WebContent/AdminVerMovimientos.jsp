<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entidad.Movimientos" %>
<%@ page import="entidad.Usuario" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ver movimientos del cliente</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="./css/ListadoClientes.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
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
		
		<h1 class="display-4 text-center text-uppercase">Movimientos del cliente</h1>

<%

ArrayList<Movimientos> listaMovimientos = null;

if (request.getAttribute("listaM") != null) {
	listaMovimientos = (ArrayList<Movimientos>) request.getAttribute("listaM");
    if (!listaMovimientos.isEmpty()) {



%>
<div class="d-flex justify-content-around"> 
<span class="text-uppercase">Filtrar por </span>

<form method="get" action="ServletMovimientos">
    <input type="hidden" name="idCliente" value="<%= request.getParameter("idCliente") %>">
    <button class="btn btn-primary btn-sm" type="submit" name="tipoMovimiento" value="1">Alta de cuenta</button>
</form>
<form method="get" action="ServletMovimientos">
    <input type="hidden" name="idCliente" value="<%= request.getParameter("idCliente") %>">
    <button class="btn btn-primary btn-sm" type="submit" name="tipoMovimiento" value="2">Alta de préstamo</button>
</form>
<form method="get" action="ServletMovimientos">
    <input type="hidden" name="idCliente" value="<%= request.getParameter("idCliente") %>">
    <button class="btn btn-primary btn-sm" type="submit" name="tipoMovimiento" value="3">Pago de préstamo</button>
</form>
<form method="get" action="ServletMovimientos">
    <input type="hidden" name="idCliente" value="<%= request.getParameter("idCliente") %>">
    <button class="btn btn-primary btn-sm" type="submit" name="tipoMovimiento" value="4">Transferencias</button>
</form>
<form method="get" action="ServletMovimientos">
    <input type="hidden" name="idCliente" value="<%= request.getParameter("idCliente") %>">
    <button class="btn btn-danger btn-sm" type="submit" name="btnBorrarFiltros" value="5">Borrar Filtros</button>
</form>

</div>


<div class="claseColumna">
<table id="tablaMovimientos" class="display text-uppercase" >
<thead>
<tr>
	<th>ID. MOV.</th>
	<th>ID. CTA. SALIDA</th>
	<th>Tipo de movimiento</th>
	<th>Fecha</th> 
	<th>CBU DESTINO</th> 
	<th>Detalle</th> 
	<th>Importe</th> 
	
</tr>
</thead>


<%


        for (Movimientos movimiento : listaMovimientos) {
%>
<tbody>
<tr>
    <td><%= movimiento.getMovimientosID() %></td>
    <td><%= movimiento.getNumeroCuenta() %></td>
    <td><%= movimiento.getTipoMovimientos() %></td>
    <td><%= movimiento.getFechaMov() %></td>
    <td><%= movimiento.getCBUMov() %></td>
    <td><%= movimiento.getDetalleMov() %></td>
    <td><%= movimiento.getImporte_Mov() %></td>
</tr>

</tbody>

<% 
        } 
%>
</table>

</div>
  <%  }else{
    	%>

    	<p class="h2 text-center text-uppercase">El cliente no tiene movimientos registrados.</p>
    	
<%
}
}
%>

<form method="get" action="ServletMovimientos" class="text-center">
    <button class="btn btn-primary text-center" type="submit" name="btnVolver" value="1">Volver al listado</button>
</form>
<%
				}
else{ %>
<h1> No hay usuario en sesi&oacute;n</h1>
<a class="h2" href="Index.jsp">Iniciar sesi&oacute;n</a>
<% 
}%>
</div>

</body>
</html>
