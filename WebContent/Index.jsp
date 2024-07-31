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
                    <h1 class="text-center">Bienvenido</h1>
                    <form method="post" action="ServletLogin">
                        <div class="form-group row">
                            <label for="user" class="col-sm-3 col-form-label">Usuario:</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="usuario" name="usuario" required>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="password" class="col-sm-3 col-form-label">Contrase&ntilde;a:</label>
                            <div class="col-sm-9">
                                <input type="password" class="form-control" id="contrasenia" name="contrasenia" required>
                            </div>
                        </div>
                        <div class="form-group row mt-4">
                            <div class="col-sm-12 text-right">
                                <input type="submit" class="btn btn-primary" name="btnLogin" value="Ingresar">
<!--                                 <p>Ingresar con usuario: admin</p> -->
<!--                                 <p>Ingresar con contraseña: admin</p> -->
                            </div>
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
