<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="entidad.Cuentas"%>
<%@ page import="entidad.Usuario"%>
<%@ page import="entidad.Movimientos"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Movimientos De Cuenta</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="./css/ClienteMovimientosCuenta.css">

<!-- DATA TABLES -->
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>

</head>
<body>
    <%
        HttpSession sesionUsuario = request.getSession();
        Usuario usuario = (Usuario) sesionUsuario.getAttribute("usuario");
        Cuentas cuenta = (Cuentas) sesionUsuario.getAttribute("cuenta");
        ArrayList<Movimientos> listaMovimientos = (ArrayList<Movimientos>) sesionUsuario.getAttribute("listaMovimientos");
        if (cuenta != null) {
            System.out.println(cuenta.toString());
            double saldoC = cuenta.getSaldo();
            NumberFormat currencyFormatterC = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));
            String saldoFormateadoC = currencyFormatterC.format(saldoC);
    %>
    <div class="container">
        <div class="encabezado">
 <%
                if (usuario != null) {
                    String usuarioNombre = usuario.getUsuario();
            %>
            
                    <ul class="nav justify-content-between">
  <li class="nav-item">
    <a class="nav-link active" href="MenuCliente.jsp">Home</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="ServletTransferir">Transferencias</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="prestamosCliente.jsp">Pr&eacute;stamos</a>
  </li>
    <li class="nav-item">
    <a class="nav-link" href="ServletUsuarioCliente" >Mi perfil</a>
  </li>
  <li class="nav-item">
                <a href="PerfilCliente.jsp" class="nav-link disabled d-block text-right">Bienvenido, <%=usuarioNombre%></a>
  </li>
   <li class="nav-item">
                <a class="nav-link" href="ServletLogout">Cerrar sesi&oacute;n</a>
  </li>
</ul>
<%
                } else {
                    response.sendRedirect("ServletCuentas.jsp");
                }
            %>

        </div>
        <h1 class="display-2 w-100 text-center">DETALLE <%= cuenta.getTipoCuenta().getTipoCuentaDescripcion().toUpperCase() %></h1>
		<p class="h2 text-right bg-secondary text-white"> SALDO DISPONIBLE: <%=saldoFormateadoC%></p>
        <div class="row justify-content-center">
            <div class="col-md-12">
                <table id="myTable" class="display">
                    <thead>
                    <tr>
<!--                         <th>ID MOV.</th> -->
                        <th>CUENTA ORIGEN</th>
                        <th>TIPO MOVIMIENTO</th>
                        <th>FECHA</th>
                        <th>DESTINO</th>
                        <th>DETALLE</th>
                        <th>IMPORTE</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        if (listaMovimientos != null && !listaMovimientos.isEmpty()) {
                            for (Movimientos movimiento : listaMovimientos) {
                            	
                            	double saldoM = movimiento.getImporte_Mov();
                                NumberFormat currencyFormatterM = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));
                                String saldoFormateadoM = currencyFormatterC.format(saldoM);
                    %>
                    <tr>
<%--                    <td><%=movimiento.getMovimientosID()%></td> --%>
                        <td><%=cuenta.getTipoCuenta().getTipoCuentaDescripcion()%></td>
                        <td><%=movimiento.getTipoMovimientos().getTipoMovimientos()%></td>
                        <td><%=movimiento.getFechaMov()%></td>
                        <td><%=movimiento.getCBUMov()%></td>
                        <td><%=movimiento.getDetalleMov()%></td>
                        <td
                         <% if(movimiento.getTipoMovimientos().getTipoMovimientosID() == 3 || movimiento.getDetalleMov().equals("Transferencia Enviada")){
                         %>class="saldoNegativo"<%} %> >
                        <%= saldoFormateadoM %>
                       </td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="7">No hay movimientos para mostrar.</td>
                    </tr>
                    <%
                        }
                    %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <%
        } else {
    %>
    <h1>CUENTA NO ENCONTRADA</h1>
    <a href="index.jsp">Volver al index</a>
    <%
            System.out.println("Cuenta no encontrada.");
        }
    %>

    <script type="text/javascript">
    $(document).ready(function() {
        $('#myTable').DataTable({
            "paging": true,
            "searching": true,
            "info": true
        });
    });
    </script>
</body>
</html>
