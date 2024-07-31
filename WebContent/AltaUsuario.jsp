<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="entidad.Usuario"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registrar Cliente</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="./css/AltaUsuario.css">
<script>
function calcularEdad(fechaNacimiento) {
    var hoy = new Date();
    var nacimiento = new Date(fechaNacimiento);
    var edad = hoy.getFullYear() - nacimiento.getFullYear();
    var diferenciaMeses = hoy.getMonth() - nacimiento.getMonth();
    if (diferenciaMeses < 0 || (diferenciaMeses === 0 && hoy.getDate() < nacimiento.getDate())) {
        edad--;
    }
    return edad;
}

function confirmarRegistro() {
    var fechaNac = document.getElementById('fechaNac').value;
    if (fechaNac === "") {
        alert('Por favor ingrese la fecha de nacimiento.');
        return false;
    }

    var edad = calcularEdad(fechaNac);
    if (edad < 18) {
        alert('No se puede registrar un cliente menor de 18 años.');
        return false;
    }

    return confirm('¿Está seguro que desea registrar al cliente?');
}
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
                <li class="nav-item"><a class="nav-link" href="#">Movimientos</a>
                </li>
                <li class="nav-item"><a class="nav-link" href="AdminAltaCuenta.jsp">Alta Cuenta</a>
                </li>
                <li class="nav-item"><a class="nav-link" href="#">Reportes</a>
                </li>
                <li class="nav-item"><a class="nav-link" href="#">Pr&eacute;stamos</a>
                </li>
                <a href="PerfilCliente.jsp" class="nav-link disabled">Bienvenido,
                    <%=usuarioNombre%></a>

                <li class="nav-item"><a class="nav-link" href="ServletLogout">Cerrar
                        sesi&oacute;n</a></li>
            </ul>
        </div>
    
<h1 class="text-center display-1 w-100 text-uppercase">Registrar Cliente</h1>
                    
        <% 
            String mensaje = (String) request.getAttribute("mensaje");
            String error = (String) request.getAttribute("error");
            if (mensaje != null) {
        %>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <%= mensaje %>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        <% 
            } else if (error != null) {
        %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <%= error %>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        <% 
            } 
        %>
                    
                    <form action="ServletAltaCliente" method="post" onsubmit="return confirmarRegistro()">
                        <div class="form-div formRegistro">
                                <label for="">Usuario:</label> 
                                <input name="usuario" type="text" required>
                                
                                <label for="">Contrase&ntilde;a:</label> 
                                <input id="contrasenia" name="contrasenia" type="password" required>
                                
                                <label for="">Nombre:</label> 
                                <input name="nombre" type="text" required>
                                
                                <label for="contraRep">Repita la Contraseña:</label>
                                <input type="password" id="contraRep" name="contraRep" required> 
                                
                                <label for="">Apellido:</label> 
                                <input name="apellido" type="text" required> 
                                
                                <label for="">DNI:</label>
                                <input name="dni" type="number" required>
                                
                                <label for="">CUIL:</label> 
                                <input name="cuil" type="number" required>
                                    
                                <label for="">Sexo:</label> 
                                <select class="sexo" name="genero" id="genero" required>
                                    <option value="" selected disabled hidden>Seleccione Sexo</option>
                                    <option value="Masculino">Masculino</option>
                                    <option value="Femenino">Femenino</option>
                                </select> 
                                
                                <label for="">Fecha de nacimiento:</label> 
                                <input name="fechaNac" type="date" id="fechaNac" required>
                                
                                <label for="">Direcci&oacute;n:</label> 
                                <input name="direccion" type="text" required>
                                
                                <label for="">Nacionalidad:</label> 
                                <input name="nacionalidad" type="text" required> 
                                
                                <label for="">Provincia:</label> 
                                <select name="provincia" id="provincia" required>
                                    <option value="" selected disabled hidden>Seleccione Provincia</option>
                                    <option value="BuenosAires">Buenos Aires</option>
                                    <option value="CiudadAutonomaDeBuenosAires">Ciudad Autonoma de Buenos Aires</option>
                                    <option value="Catamarca">Catamarca</option>
                                    <option value="Chaco">Chaco</option>
                                    <option value="Chubut">Chubut</option>
                                    <option value="Córdoba">Córdoba</option>
                                    <option value="Corrientes">Corrientes</option>
                                    <option value="Entre Ríos">Entre Ríos</option>
                                    <option value="Formosa">Formosa</option>
                                    <option value="Jujuy">Jujuy</option>
                                    <option value="La Pampa">La Pampa</option>
                                    <option value="La Rioja">La Rioja</option>
                                    <option value="Mendoza">Mendoza</option>
                                    <option value="Misiones">Misiones</option>
                                    <option value="Neuquén">Neuquén</option>
                                    <option value="Río Negro">Río Negro</option>
                                    <option value="Salta">Salta</option>
                                    <option value="San Juan">San Juan</option>
                                    <option value="San Luis">San Luis</option>
                                    <option value="Santa Cruz">Santa Cruz</option>
                                    <option value="Santa Fe">Santa Fe</option>
                                    <option value="Santiago del Estero">Santiago del Estero</option>
                                    <option value="Tierra del Fuego">Tierra del Fuego</option>
                                    <option value="Tucumán">Tucumán</option>
                                </select> 
                                
                                <label for="">Localidad:</label> 
                                <input name="localidad" type="text" required>
                            
                                <label for="">Correo electr&oacute;nico:</label> 
                                <input name="email" type="text" required>
                                    
                                <label for="">Tel&eacute;fono:</label>
                                <input name="telefono" type="number" required>
                                
                                <br>
                                
                                <div class="col-sm-12 text-right">
                                <input type="submit" class="btn btn-primary btnRegistro" name="btnRegistro" value="Registrar">
                                </div>
                                
                            </div>
                    </form>
                </div>
                <%
            }else{
            
            %>
            
            <h1> No hay usuario en sesi&oacute;n</h1>
            <a class="" href="Index.jsp">Iniciar sesi&oacute;n</a>
            
            
            <%
            }
            
            %>
            

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script
        src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script
        src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>