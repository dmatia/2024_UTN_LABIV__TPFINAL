<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entidad.Movimientos" %>
<%@ page import="entidad.Cliente" %>
<%@ page import="entidad.Usuario"%>
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
		//"dom": '<"top"l>rt<"bottom"ip><"clear">',
        "paging": true,
        "lengthChange": true,
        "searching": true,
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
	
<h1 class="text-center display-1 w-100 text-uppercase">Historial Movimientos</h1>

<%
    ArrayList<Movimientos> listaMovimientos = null;
    if (request.getAttribute("listaTodosMovimientos") != null) {
    	listaMovimientos = (ArrayList<Movimientos>) request.getAttribute("listaTodosMovimientos");
    if (!listaMovimientos.isEmpty()) {

    	%>
    	
    	<form method="get" action="ServletMovimientos" class="text-center">
    <span class="text-uppercase">Filtrar por mayor o igual</span>
    <input type="text" name="adminImporteMayorOIgual">
    <input type="submit" value="Buscar" class="btn btn-primary">
</form>


<div class="filtrosMovimiento text-center pt-2 pb-2">
<span class="text-uppercase">Filtrar por</span>
<form method="get" action="ServletMovimientos">
    <button type="submit" name="tipoMovimientoTodosMovimientos" value="1" class="btn btn-success">Alta de cuenta</button>
</form>
<form method="get" action="ServletMovimientos">
    <button type="submit" name="tipoMovimientoTodosMovimientos" value="2" class="btn btn-success">Alta de préstamo</button>
</form>
<form method="get" action="ServletMovimientos">
    <button type="submit" name="tipoMovimientoTodosMovimientos" value="3" class="btn btn-success">Pago de préstamo</button>
</form>
<form method="get" action="ServletMovimientos">
    <button type="submit" name="tipoMovimientoTodosMovimientos" value="4" class="btn btn-success">Transferencias</button>
</form>
<form method="get" action="ServletMovimientos">
    <button type="submit" name="btnBorrarFiltrosAdminTodosMovimientos" value="TodosMovimientos=1" class="btn btn-danger">Borrar Filtros</button>
</form>
</div>

<table border="1" id="tablaMovimientos" class="display mt-2">
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
<%
    }else{
    	%>
    	
    	<h1> No hay registros </h1>
    	
    	<% 
    }
    }else{
    	
    	%>
    	
    	<h1> No hay registros. </h1>
    	
    	
    	<% 
    	
    }
				}else{
	            	
	            	%>
	                
	                <h1> No hay usuario en sesi&oacute;n</h1>
	                <a class="" href="Index.jsp">Iniciar sesi&oacute;n</a>
	                
	                
	                <%
	            
	            
	            }
					
					
	            
	            %>


</body>
</html>
