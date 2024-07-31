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
<link rel="stylesheet" href="./css/SolicitarPrestamo.css">
<title>Solicitar Prestamo</title>
<script>
function actualizarImporte() {
    var cuotas = document.getElementById("cuotas").value;
    var labelImporte = document.getElementById("importe");
    var importeSoli = document.getElementById("importeSoli").value;
    importeSoli = parseFloat(importeSoli); 
    if (isNaN(importeSoli)) {
        labelImporte.textContent = "Importe a pagar: ";
        return;
    }
    var importeTotal = 0;

    switch (cuotas) {
        case "1":
            importeTotal = importeSoli * 1.10;
            break;
        case "2":
            importeTotal = importeSoli * 1.30;
            break;
        case "3":
            importeTotal = importeSoli * 1.60;
            break;
        case "4":
            importeTotal = importeSoli * 2;
            break;
        default:
            importeTotal = 0;
            break;
    }

    labelImporte.textContent = "Importe a pagar: " + importeTotal.toFixed(2) + "$";
}

function openModal(event) {
    event.preventDefault();

    var importe = document.getElementById("importeSoli").value;
    var cuotas = document.getElementById("cuotas").value;
    var cuenta = document.getElementById("cuenta").value;

    if (importe.trim() === "" || cuotas === "" || cuenta === "") {
        alert("Por favor, complete el formulario antes de solicitar el préstamo.");
        return;
    }
    
    var importeSoli = parseFloat(importe);
    var importeTotal = 0;
    var cuotasNum = 0;

    switch (cuotas) {
        case "1":
            importeTotal = importeSoli * 1.10;
            cuotasNum = 1
            break;
        case "2":
            importeTotal = importeSoli * 1.30;
            cuotasNum = 3
            break;
        case "3":
            importeTotal = importeSoli * 1.60;
            cuotasNum = 6
            break;
        case "4":
            importeTotal = importeSoli * 2;
            cuotasNum = 12
            break;
        default:
            importeTotal = 0;
            break;
    }

    var montoPorCuota = (importeTotal / cuotasNum).toFixed(2);
    var cuotasTexto = document.getElementById("cuotas").selectedOptions[0].text;

    document.getElementById("costoSolicitadoModal").textContent = "Costo solicitado: " + importeSoli.toFixed(2) + "$";
    document.getElementById("cuotasModal").textContent = cuotasTexto;
    document.getElementById("montoCuotasModal").textContent = "Monto por cuota: " + montoPorCuota + "$";
    document.getElementById("costoFinalModal").textContent = "Costo final: " + importeTotal.toFixed(2) + "$";

    var modal = document.getElementById("myModal");
    modal.style.display = "block";
}

function closeModal() {
    var modal = document.getElementById("myModal");
    modal.style.display = "none";
}

function confirmSubmit() {
    var form = document.getElementById("prestamoForm");
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
            <h1 class="display-1 w-100 text-center text-uppercase">SOLICITAR PR&Eacute;STAMO</h1>
        </div>
        




<div id="cuerpoSolicitarPrestamo">
    <form id="prestamoForm" method="post" action="ServletSolicitarPrestamo" class="formularioPrestamo">
    
    <div id="myModal" class="modal">
  <div class="modal-content">
  	<h5 class="modal-title" id="confirmModalLabel">Confirmacion de Prestamo</h5>
  	<p id="costoSolicitadoModal"></p>
    <p id="cuotasModal"></p>
    <p id="montoCuotasModal"></p>
    <p id="costoFinalModal"></p>
    <span class="close" onclick="closeModal()">&times;</span>
    <p>¿Esta seguro de que desea solicitar el prestamo?</p>
    <button name="btnConfirmarModal" onclick="confirmSubmit()">Confirmar</button>
    <button onclick="closeModal()">Cancelar</button>
  </div>
</div>
    
    
    
        <label for="importeSoli">Importe a solicitar</label>
        <input id="importeSoli" type="number" onchange="actualizarImporte()" name="montoIngresado" required>
        
        <label for="cuotas">Cantidad de cuotas</label>        
        <select id="cuotas" name="cuotas" required onchange="actualizarImporte()">
            <option value="">Seleccione las cuotas</option>
            <option value="1">1 Cuota - 10% interes</option>
            <option value="2">3 Cuotas - 30% interes</option>
            <option value="3">6 Cuotas - 60% interes</option>
            <option value="4">12 Cuotas - 100% interes</option>
        </select>

        <label>Seleccione una cuenta</label>
        <select id="cuenta" name="cuenta" required>
            <option value="">Seleccione una cuenta</option>
            <% 
            ArrayList<Cuentas> cuentas = (ArrayList<Cuentas>) request.getAttribute("cuentas");
            if (cuentas != null) {
                for (Cuentas cuenta : cuentas) {
            %>
                    <option value="<%= cuenta.getNumeroCuenta_Cue() %>">
                        <strong>Cuenta N°<%= cuenta.getNumeroCuenta_Cue() %></strong>
                        <span>Saldo actual: <%= cuenta.getSaldo() %>$</span>
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
        
        <label id="importe" for="importeF">Importe a pagar:</label>
         
        <input type="reset" name="btnCancelar" class="btn btn-danger btn-lg m-1" value="CANCELAR" onclick="document.getElementById('importe').textContent = 'Importe a pagar: '">
        <input type="submit" name="btnConfirmar" class="btn btn-primary btn-lg m-1" value="SOLICITAR" onclick="openModal(event)">
  </form>
</div>
<% } %>
</body>
</html>







