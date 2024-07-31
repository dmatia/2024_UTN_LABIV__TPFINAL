<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@page import="entidad.Cuentas"%>
<%@page import="entidad.Cliente"%>
<%@page import="entidad.Usuario"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="./css/ClienteTransferir.css">
<title>Realizar Transferencia</title>
<script>
function openModal(event) {
    event.preventDefault();

    var importeTrans = document.getElementById("numerosCtransf").value;
    var numerosCBU = document.getElementById("numerosCBU").value;
    var cuenta = document.getElementById("cuenta").value;

    if (importeTrans.trim() === "" || numerosCBU === "" || cuenta === "") {
        alert("Por favor, complete el formulario antes de realizar la transferencia.");
        return;
    }
    
    var modal = document.getElementById("myModal");
    modal.style.display = "block";
}

function closeModal() {
    var modal = document.getElementById("myModal");
    modal.style.display = "none";
}

function confirmSubmit() {
    var form = document.getElementById("transferenciaForm");
    var hiddenInput = document.createElement("input");
    hiddenInput.type = "hidden";
    hiddenInput.name = "btnConfirmarModal";
    hiddenInput.value = "confirmar";
    form.appendChild(hiddenInput);
    form.submit();
}
</script>
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

        <div>
            <h1 class="display-1 w-100 text-center text-uppercase">NUEVA TRANSFERENCIA</h1>
        </div>
        

 <div class="parteIzq">
		<div class="menu">
<form id="transferenciaForm" method="post" action="ServletTransferir" class="formulariotransferencia">

        <label>Seleccione una cuenta</label>
        <select id="cuenta" name="cuenta" required>
            <option value="">Seleccione una cuenta</option>
            <% 
            HttpSession sessionCuentas = request.getSession();
            ArrayList<Cuentas> cuentas = (ArrayList<Cuentas>) session.getAttribute("sessionCuentas");
            if (cuentas != null) {
                for (Cuentas cuenta : cuentas) {
            %>
                    <option value="<%= cuenta.getNumeroCuenta_Cue() %>">
                        <strong><%=cuenta.getTipoCuenta().getTipoCuentaDescripcion()%>
						[CBU:<%=cuenta.getCBU()%>]</strong><br>
                        <span>Saldo: <%= cuenta.getSaldo() %>$</span>
                    </option>
            <% 
                }
            } else {
            %>
                <option value="">No hay cuentas disponibles</option>
            <% 
            }
            %>
        </select>
        
            
            <label for="CBU">Ingrese el CBU del destinatario</label>

            <input id="numerosCBU" type="number" name="numerosCBU"></input>
         
            <label>Ingrese monto que desea transferir</label>
            <div class="card--modif cm1"></div>
            <input id="numerosCtransf" type="number" name="numerosCtransf" step="0.01"></input>
      
            <input type="submit" name="btnTransferir" id="btnTransferir" class="btn btn-primary btn-lg m-1" value="TRANSFERIR" onclick="openModal(event)">
            <input type="reset" name="btnCancelar" class="btn btn-danger btn-lg m-1" value="CANCELAR">
          </form>
		</div>

</div>
<%
}%>

<div id="myModal" class="modal">
  <div class="modal-content">
  	<h5 class="modal-title" id="confirmModalLabel">Confirmacion de Transferencia</h5>
    <span class="close" onclick="closeModal()">&times;</span>
    <p>¿Esta seguro de que desea realizar la transferencia?</p>
    <button name="btnConfirmarModal" onclick="confirmSubmit()">Confirmar</button>
    <button onclick="closeModal()">Cancelar</button>
  </div>
</div>

<% 
if (request.getAttribute("errorMensaje") != null) {
    String mensaje = (String) request.getAttribute("errorMensaje");
%>
    <script type="text/javascript">
        alert("<%= mensaje %>");
    </script>
<%
}
%>

</body>
</html>
