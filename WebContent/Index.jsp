<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>¡Bienvenido!</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="./css/Index.css">
</head>
<body>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-12">
                <div>
                <img class="login-logo" alt="banco-logo" src="assets/banco-logo-verde.png">
                    <h1 class="text-center text-uppercase">Bienvenido</h1>
                            <form method="post" action="ServletLogin" class="text-center">
                <div class="form-group">
                    <label for="usuario">Usuario</label>
                    <input type="text" class="form-control mx-auto" id="usuario" name="usuario" required>
                </div>
                
                <div class="form-group">
                    <label for="contrasenia">Contraseña</label>
                    <input type="password" class="form-control mx-auto" id="contrasenia" name="contrasenia" required>
                </div>
                
                <div class="form-group mt-4">
                    <input type="submit" class="btn btn-primary w-100" name="btnLogin" value="Ingresar">
                </div>
            </form>
                    <% if (request.getAttribute("Mensaje") != null) { %>
        			<div class="alert alert-danger" role="alert">
            		<%= request.getAttribute("Mensaje") %>
        			</div>
    				<% } %>
                </div>
            </div>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
