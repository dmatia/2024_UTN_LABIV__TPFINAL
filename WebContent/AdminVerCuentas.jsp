<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entidad.Cuentas" %>
<%@ page import="entidad.Usuario"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ver cuentas del cliente</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="./css/AutorizarPrestamos.css">
<script>

function openModal(event, cuentaId, estadoCuenta) {
    event.preventDefault();
    
    var modal = document.getElementById("myModal");
    modal.setAttribute("data-cuenta-id", cuentaId);
    modal.setAttribute("data-estado-cuenta", estadoCuenta);

    modal.style.display = "block";
}

function closeModal() {
    var modal = document.getElementById("myModal");
    modal.style.display = "none";
}

function confirmSubmit() {
    var modal = document.getElementById("myModal");
    var cuentaId = modal.getAttribute("data-cuenta-id");
    var estadoCuenta = modal.getAttribute("data-estado-cuenta");

    var form = document.createElement("form");
    form.method = "get";
    form.action = "ServletCuentas";

    var inputCuentaId = document.createElement("input");
    inputCuentaId.type = "hidden";
    inputCuentaId.name = "IdModificarEstado";
    inputCuentaId.value = cuentaId;
    form.appendChild(inputCuentaId);

    var inputEstadoCuenta = document.createElement("input");
    inputEstadoCuenta.type = "hidden";
    inputEstadoCuenta.name = "EstadoCuenta";
    inputEstadoCuenta.value = estadoCuenta;
    form.appendChild(inputEstadoCuenta);

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
	
<h1 class="text-center display-4 w-100 text-uppercase ">Cuentas del cliente</h1>
<table class="table w-100 text-center text-uppercase justify-content-between">
<tr>
    <th>ID</th> 
    <th>Tipo de cuenta</th> 
    <th>CBU</th> 
    <th>Saldo</th> 
    <th>Fecha alta</th>  
    <th>Estado</th>
    <th></th>
</tr>

<%
    ArrayList<Cuentas> listaCuentas = null;
    if (request.getAttribute("listaC") != null) {
        listaCuentas = (ArrayList<Cuentas>) request.getAttribute("listaC");
    }

    if (listaCuentas != null) {
        for (Cuentas cuenta : listaCuentas) {
%>
<tr>
    <td><%= cuenta.getNumeroCuenta_Cue() %></td>
    <td><%= cuenta.getTipoCuenta().getTipoCuentaDescripcion() %></td>
    <td><%= cuenta.getCBU() %></td>
    <td><%= cuenta.getSaldo() %></td>
    <td><%= cuenta.getFechaCreacion() %></td>
    <td>
    <% if (cuenta.isEstado()){
    	%>
    	ACTIVA
    <%  } else{ %>
    	INACTIVA
    <% } %>
    </td>
    <td>
    <% if (cuenta.isEstado()){
    	%>
 <a href="#" class="btn btn-danger btn-sm" onclick="openModal(event, <%= cuenta.getNumeroCuenta_Cue()  %>, <%= cuenta.isEstado() %>)">INACTIVAR</a>
	
    <%  } else{ %>
    			<a href="#" class="btn btn-success btn-sm"  onclick="openModal(event, <%= cuenta.getNumeroCuenta_Cue()  %>, <%= cuenta.isEstado() %>)">ACTIVAR</a>
	
    <% } %>
   </td>
</tr>
<% 
        } 
    }
%>

</table>

       
<form method="get" action="ServletCuentas" class="text-center text-uppercase mt-4">
    <button type="submit" name="btnVolver" value="1" class="btn btn-primary">Volver al listado</button>
</form>

<div id="myModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal()">&times;</span>
        <p>¿Está seguro de que desea modificar el estado de la cuenta?</p>
        <button onclick="confirmSubmit()">Confirmar</button>
        <button onclick="closeModal()">Cancelar</button>
    </div>
</div>
<%
}else{ %>
	<h1> No hay usuario en sesi&oacute;n</h1>
	<a class="h2" href="Index.jsp">Iniciar sesi&oacute;n</a>
	<% 
}
				%>
</body>
</html>
