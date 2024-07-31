<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entidad.Cliente" %>
<%@ page import="entidad.Usuario"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Listado de clientes</title>

<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="./css/ListadoClientes.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>

<script type="text/javascript">
$(document).ready(function (){
	var table = $('#tablaListadoClientes').DataTable({
        "dom": '<"top"l>rt<"bottom"ip><"clear">' 
    });

    $('input[name="busquedaCliente"]').on('keyup', function () {
        table.search(this.value).draw();
    });
});

function openModal(event, clienteId, estadoCliente) {
    event.preventDefault();
    
    var modal = document.getElementById("myModal");
    modal.setAttribute("data-cliente-id", clienteId);
    modal.setAttribute("data-estado-cliente", estadoCliente);

    modal.style.display = "block";
}

function closeModal() {
    var modal = document.getElementById("myModal");
    modal.style.display = "none";
}

function confirmSubmit() {
    var modal = document.getElementById("myModal");
    var clienteId = modal.getAttribute("data-cliente-id");
    var estadoCliente = modal.getAttribute("data-estado-cliente");

    var form = document.createElement("form");
    form.method = "get";
    form.action = "ServletCliente";

    var inputClienteId = document.createElement("input");
    inputClienteId.type = "hidden";
    inputClienteId.name = "IdCambiarEstado";
    inputClienteId.value = clienteId;
    form.appendChild(inputClienteId);

    var inputEstadoCliente = document.createElement("input");
    inputEstadoCliente.type = "hidden";
    inputEstadoCliente.name = "EstadoCliente";
    inputEstadoCliente.value = estadoCliente;
    form.appendChild(inputEstadoCliente);

    document.body.appendChild(form);
    form.submit();
}

window.onload = function() {
	closeModal();
};
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
	
<h1 class="text-center display-1 w-100 text-uppercase">LISTADO CLIENTES</h1>

<%
    ArrayList<Cliente> listaClientes = null;
    if(request.getAttribute("listaC") != null){
        listaClientes = (ArrayList<Cliente>) request.getAttribute("listaC");

    if (!listaClientes.isEmpty()) {
%>

<div class="d-flex justify-content-around"> 

<form method="get" action="ServletCliente" class="text-center">
    <span class="text-uppercase">Busqueda de cliente</span>
    <input type="text" name="busquedaCliente">
    <input type="submit" value="Buscar" class="btn btn-primary">
</form>

<form method="get" action="ServletCliente">
<span class="text-uppercase mr-2">Filtrar por Estado</span>
	<select name="estadoCliente" class="text-uppercase mr-2">
	<option value="0" >Activo y No-activo</option>
	<option value="1" >Activo</option>
	<option value="2" >No-activo</option>
	</select>
	<button type="submit" name="btnFiltrarPorEstado" class="btn btn-primary">Filtrar</button>
</form>

<form method="get" action="ServletCliente">
    <button type="submit" name="btnBorrarFiltros" class="btn btn-danger">Borrar Filtros</button>
</form>
</div>







<table border="1" class="table-sm mt-2 text-uppercase" id="tablaListadoClientes">
<thead>
<tr>
    <th>Nombre</th>
    <th>DNI</th>
    <th>Correo electr&oacute;nico</th>
    <th>Cuentas</th>
    <th>Movimientos</th>
    <th>Informaci&oacute;n</th>
    <th>Modificar</th>
    <th>Estado</th>
</tr>
</thead>


<%

for (Cliente cliente : listaClientes) { 
%>
<tbody>
<tr>
        <td class="text-left"><%= cliente.getApellido().trim() + ", " +  cliente.getNombre().trim()  %></td>
        <td class="text-right"><%= cliente.getDNI() %></td>
        <td><%= cliente.getCorreoElectronico() %></td>
        <td><a class="btn btn-primary btn-sm" href="ServletCuentas?idCliente=<%=cliente.getIdCliente()%>">Ver Cuentas</a></td>
        <td><a class="btn btn-primary btn-sm"  href="ServletMovimientos?idCliente=<%=cliente.getIdCliente()%>">Ver Movimientos</a></td>
        <td><a class="btn btn-primary btn-sm"  href="ServletCliente?MasInfo=<%=cliente.getIdCliente()%>">Más info.</a></td>
        <td><a class="btn btn-primary btn-sm"  href="ServletCliente?ModClient=<%=cliente.getIdCliente()%>">Modificar</a></td>
        <td>
        <%
        	if(cliente.isEstado_Cli()==true){
        		%>
        		 <a class="btn btn-danger btn-sm"  href="#" onclick="openModal(event, <%= cliente.getIdCliente() %>, <%= cliente.isEstado_Cli() %>)">INACTIVAR</a>
        			<% 
        	}else{
        		%>
        		 <a class="btn btn-success btn-sm"  href="#" onclick="openModal(event, <%= cliente.getIdCliente() %>, <%= cliente.isEstado_Cli() %>)">ACTIVAR</a>
        		<% 
        	}
        
        
        %>
         <br>
   		</td>
</tr>
</tbody>
<% 				} %>
</table>


<div id="myModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal()">&times;</span>
        <p>¿Está seguro de que desea modificar el estado del cliente?</p>
        <button onclick="confirmSubmit()">Confirmar</button>
        <button onclick="closeModal()">Cancelar</button>
    </div>
</div>
<% 
    }else{
	%>
		
		<h1> No hay usuarios registrados.</h1>
	
	<% 
}

            }else{
            	
            	%>
                
                <h1> No hay usuario en sesi&oacute;n</h1>
                <a class="" href="Index.jsp">Iniciar sesi&oacute;n</a>
                
                
                <%
            
            
            }
				}
				
            
            %>
            
</body>
</html>
