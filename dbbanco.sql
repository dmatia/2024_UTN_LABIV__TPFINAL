CREATE DATABASE dbbanco;

USE dbbanco;


--- CREACION TABLAS:

CREATE TABLE tipoUsuario (
    TipoUsuarioID_TUsu INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    tipoDeUsuarios VARCHAR(45) NOT NULL
);

CREATE TABLE usuario (
    UsuarioID_Usu INT NOT NULL PRIMARY KEY,
    TipoUsuarioID_Usu INT NOT NULL,
    Usuario_Usu VARCHAR(45) NOT NULL UNIQUE,
    Contrasena_Usu VARCHAR(45) NOT NULL,
    Estado_Usu TINYINT NOT NULL DEFAULT '1',
    FOREIGN KEY (TipoUsuarioID_Usu) REFERENCES tipoUsuario(TipoUsuarioID_TUsu)
);

CREATE TABLE clientes (
    ClienteID_Cli INT NOT NULL PRIMARY KEY,
    UsuarioID_Cli INT NOT NULL,
    DNI VARCHAR(20) NOT NULL UNIQUE,
    CUIL VARCHAR(20) NOT NULL UNIQUE,
    Nombre VARCHAR(45) NOT NULL,
    Apellido VARCHAR(45) NOT NULL,
    Sexo ENUM('Femenino','Masculino') NOT NULL,
    Nacionalidad VARCHAR(45) NOT NULL,
    FechaDeNacimiento DATE NOT NULL, 
    Direccion VARCHAR(45) NOT NULL,
    Localidad VARCHAR(45) NOT NULL,
    Provincia VARCHAR(45) NOT NULL,
    CorreoElectronico VARCHAR(45) NOT NULL UNIQUE,
    Telefono VARCHAR(45) NOT NULL,
    Estado_Cli TINYINT NOT NULL DEFAULT '1',
    FOREIGN KEY (UsuarioID_Cli) REFERENCES usuario(UsuarioID_Usu)
);

CREATE TABLE tipoCuenta (
    TipoCuentaID_TCue INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    TipoDeCuenta VARCHAR(45) NOT NULL
);

CREATE TABLE cuenta (
    NumeroCuenta_Cue INT NOT NULL PRIMARY KEY,
    ClienteID_Cue INT NOT NULL,
    TipoDeCuenta_Cue INT NOT NULL,
    FechaCreacion DATE NOT NULL,
    CBU_Cue VARCHAR(45) NOT NULL UNIQUE,
    Saldo DECIMAL(20,2) NOT NULL DEFAULT '10000.00',
    Estado_Cue TINYINT NOT NULL DEFAULT '1',
    FOREIGN KEY (ClienteID_Cue) REFERENCES clientes(ClienteID_Cli),
    FOREIGN KEY (TipoDeCuenta_Cue) REFERENCES tipoCuenta(TipoCuentaID_TCue)
);

CREATE TABLE tipoMovimientos (
    TipoMovimientosID_TMov INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    TipoMovimientos VARCHAR(45) NOT NULL
);

CREATE TABLE movimientos (
    MovimientosID_Mov INT NOT NULL,
    NumeroCuenta_Mov INT NOT NULL,
    TipoMovimiento_Mov INT NOT NULL,
    Fecha_Mov DATE NOT NULL,
    CBU_Mov VARCHAR(45) NOT NULL,
    Detalle_Mov VARCHAR(45) NOT NULL,
    Importe_Mov DECIMAL(20,2),
    PRIMARY KEY (MovimientosID_Mov, NumeroCuenta_Mov),
    FOREIGN KEY (NumeroCuenta_Mov) REFERENCES cuenta(NumeroCuenta_Cue),
    FOREIGN KEY (TipoMovimiento_Mov) REFERENCES tipoMovimientos(TipoMovimientosID_TMov)
);

CREATE TABLE intereses (
    InteresesID_Int INT NOT NULL PRIMARY KEY,
    Cuotas_Int INT NOT NULL,
    Porcentaje_Int DECIMAL(5,2)
);

CREATE TABLE prestamos (
    PrestamoID_Pres INT NOT NULL PRIMARY KEY,
    ClienteAsignado_Pres INT NOT NULL,
    CuentaDelCliente_Pres INT NOT NULL,
    ImporteSoli_Pres DECIMAL(20,2) NOT NULL,
    InteresesID_Pres INT NOT NULL,
    FechaPedidoPrestamo_Pres DATE NOT NULL,
    ImporteCuota_Pres DECIMAL(20,2) NOT NULL,
    ImporteTotal_Pres DECIMAL(20,2) NOT NULL,
    PlazoPagoMeses INT NOT NULL,
    EstadoSolicitud_Pres ENUM('Pendiente','Aprobado','Rechazado') NOT NULL DEFAULT 'Pendiente',
    Estado_Pres TINYINT NOT NULL DEFAULT '0',
    FOREIGN KEY (ClienteAsignado_Pres) REFERENCES clientes(ClienteID_Cli),
    FOREIGN KEY (CuentaDelCliente_Pres) REFERENCES cuenta(NumeroCuenta_Cue),
    FOREIGN KEY (InteresesID_Pres) REFERENCES intereses(InteresesID_Int)
);

CREATE TABLE pagoDelPrestamo (
    PagoPrestamoID_PDP INT NOT NULL PRIMARY KEY,
    PrestamoID_PDP INT NOT NULL,
    CuentaDelCliente_PDP INT NOT NULL,
    FechaDePago_PDP DATE NULL,
    EstadoPrestamo_PDP ENUM('No Pagado','Pagado') NOT NULL DEFAULT 'No Pagado',
    ImporteCuota_PDP DECIMAL(20,2) NOT NULL,
    FechaDeVencimiento_PDP DATE NULL,
    FOREIGN KEY (PrestamoID_PDP) REFERENCES prestamos(PrestamoID_Pres),
    FOREIGN KEY (CuentaDelCliente_PDP) REFERENCES prestamos(CuentaDelCliente_Pres)
);


-- CARGA DE DATOS DE TIPOS: -------------------------------------------------------------

use dbbanco;
 
-- TIPO DE USUARIO:
insert into tipoUsuario (tipoDeUsuarios)
values
('Administrador'), 
('Cliente'); 

-- TIPO DE MOVIMIENTOS:
insert into tipoMovimientos (TipoMovimientos)
values
('Alta de cuenta'),
('Alta de un prestamo'),
('Pago de prestamo'),
('Transferencia');

-- TIPO DE CUENTA:
insert into tipoCuenta(TipoDeCuenta)
values
('Caja de ahorro'),
('Cuenta corriente');



-- PROCEDIENTOS ALMACENADOS: ---------------------------------------------------------------------

-- USUARIOS: ---------------------------------------

-- Para eliminar (baja logica):
use dbbanco;
DELIMITER $$
create procedure dbbanco.spBajaLogicaUsuario
(
IN idUsuario_BL INT
)
BEGIN
	IF EXISTS (SELECT 1 FROM usuario WHERE UsuarioID_Usu = idUsuario_BL) THEN
		UPDATE usuario SET Estado_Usu = 0 WHERE UsuarioID_Usu = idUsuario_BL;
    END IF;
END $$
DELIMITER ;

-- Para modificar la contrasena:
use dbbanco;
DELIMITER $$
create procedure dbbanco.spModificarUsuario
(
IN idUsuario_MU INT, 
IN contrasena_MU VARCHAR(45)
)
BEGIN
	IF EXISTS (SELECT 1 FROM usuario WHERE UsuarioID_Usu = idUsuario_MU) THEN
		UPDATE usuario SET Contrasena_Usu = contrasena_MU WHERE UsuarioID_Usu = idUsuario_MU;
    END IF;
END $$
DELIMITER ;


-- Para logearse:

use dbbanco;

DELIMITER $$
CREATE PROCEDURE dbbanco.spLogeoUsuario
(
    IN usuario_LU VARCHAR(45),
    IN contrasena_LU VARCHAR(45)
)
BEGIN
    SELECT Usuario_Usu, Contrasena_Usu
    FROM usuario
    WHERE BINARY Usuario_Usu = BINARY usuario_LU  -- Comparación case-sensitive
      AND BINARY Contrasena_Usu = BINARY contrasena_LU 
      AND Estado_Usu = 1;
END $$
DELIMITER ;


-- Clientes: ---------------------------------------

-- Para dar baja logica:
use dbbanco;
DELIMITER $$
create procedure dbbanco.spBajaLogicaCliente
(
IN idCliente_BLC INT
)
BEGIN
	IF EXISTS (SELECT 1 FROM clientes WHERE ClienteID_Cli = idCliente_BLC) THEN
		UPDATE clientes SET Estado_Cli = 0 WHERE ClienteID_Cli = idCliente_BLC;
    END IF;
END $$
DELIMITER ;


-- Cuentas: ------------------------------------------

-- generar CBU
DELIMITER $$

CREATE PROCEDURE spGenerarCBU(OUT nuevoCBU VARCHAR(45))
BEGIN
    DECLARE valido INT DEFAULT 0;
    DECLARE cbu VARCHAR(22);
    DECLARE parte1 VARCHAR(11);
    DECLARE parte2 VARCHAR(11);

    WHILE valido = 0 DO
        -- Genera las dos partes del CBU
        SET parte1 = LPAD(CONVERT(FLOOR(1 + RAND() * 99999999999), CHAR), 11, '0');
        SET parte2 = LPAD(CONVERT(FLOOR(1 + RAND() * 99999999999), CHAR), 11, '0');
        -- Concatena las dos partes para formar el CBU de 22 caracteres
        SET cbu = CONCAT(parte1, parte2);

        -- Verifica si el CBU generado ya existe en la tabla cuenta
        IF NOT EXISTS (SELECT 1 FROM cuenta WHERE CBU_Cue = cbu) THEN
            SET valido = 1;
        END IF;
    END WHILE;

    SET nuevoCBU = cbu;
END $$

DELIMITER ;


-- Para agregar cuenta:
USE dbbanco;
DELIMITER $$

CREATE PROCEDURE spAgregarCuenta
(
    IN clienteid_ACue INT,
    IN tipocuentaid_ACue INT,
    OUT resultado INT
)
BEGIN
    DECLARE proximoIdCuenta INT;
    DECLARE cbu_ACue VARCHAR(45);
    DECLARE fechacreacion_ACue DATE;
    DECLARE saldo_ACue DECIMAL(20,2) DEFAULT 10000.00;
    
    SET fechacreacion_ACue = CURDATE();
    
    IF EXISTS (SELECT 1 FROM clientes WHERE ClienteID_Cli = clienteid_ACue) THEN
        IF EXISTS (SELECT 1 FROM tipoCuenta WHERE TipoCuentaID_TCue = tipocuentaid_ACue) THEN
            CALL spGenerarCBU(cbu_ACue);
            SELECT IFNULL(MAX(NumeroCuenta_Cue), 0) + 1 INTO proximoIdCuenta FROM cuenta;
            INSERT INTO cuenta (NumeroCuenta_Cue, ClienteID_Cue, TipoDeCuenta_Cue, FechaCreacion, CBU_Cue, Saldo)
            VALUES(proximoIdCuenta, clienteid_ACue, tipocuentaid_ACue, fechacreacion_ACue, cbu_ACue, saldo_ACue);
            SET resultado = proximoIdCuenta;

            -- Devolver todos los campos de la cuenta recién creada
            SELECT NumeroCuenta_Cue, ClienteID_Cue, TipoDeCuenta_Cue, FechaCreacion, CBU_Cue, Saldo
            FROM cuenta
            WHERE NumeroCuenta_Cue = proximoIdCuenta;
        ELSE
            SET resultado = -1;
        END IF;
    ELSE
        SET resultado = -1;
    END IF;
END $$
DELIMITER ;


-- Para dar de baja logica:
use dbbanco;
DELIMITER $$
create procedure dbbanco.spBajaLogicaCuenta
(
IN numCuenta_BLCue INT
)
BEGIN
	IF EXISTS (SELECT 1 FROM cuenta WHERE NumeroCuenta_Cue = numCuenta_BLCue) THEN
		UPDATE cuenta SET Estado_Cue = 0 WHERE NumeroCuenta_Cue = numCuenta_BLCue;
    END IF;
END $$
DELIMITER ;

-- Para validar cantidad de cuentas menor igual a 3 de un cliente:
use dbbanco;
DELIMITER $$
create procedure dbbanco.spCantidadCuentasPorCliente
(
IN idcliente_CCC INT,
OUT totalCuentas_CCC INT
)
BEGIN
	SELECT COUNT(*) INTO totalCuentas_CCC FROM cuenta WHERE ClienteID_Cue = idcliente_CCC AND Estado_Cue <> 0;
END $$
DELIMITER ;



-- Movimientos: --------------------------------------------


-- Para agregar un movimiento (excepto el de transferencia):
use dbbanco;
DELIMITER $$
create procedure dbbanco.spAgregarMovimientoTipo123
(
IN numCuenta_AMT123 INT,
IN tipoMovimiento_AMT123 INT,
IN fechaMovimiento_AMT123 DATE,
IN cbu_AMT123 VARCHAR(45),
IN detalle_AMT123 VARCHAR(25),
IN importe_AMT123 DECIMAL(20,2)
)
BEGIN
	DECLARE proximoIDMov INT; 
	SELECT IFNULL(MAX(MovimientosID_Mov), 0) + 1 INTO proximoIDMov FROM movimientos;
	INSERT INTO movimientos (MovimientosID_Mov, NumeroCuenta_Mov, TipoMovimiento_Mov, Fecha_Mov, CBU_Mov, Detalle_Mov, Importe_Mov)
    VALUES (proximoIDMov, numCuenta_AMT123, tipoMovimiento_AMT123, fechaMovimiento_AMT123, cbu_AMT123, detalle_AMT123, importe_AMT123);
END $$
DELIMITER ;

-- Para agregar movimientos de tipo 4:
use dbbanco;
DELIMITER $$
create procedure dbbanco.spAgregarMovimientoTipo4
(
IN numCuentaSalida_AMT123 INT,
IN numCuentaEntrada_AMT123 INT,
IN tipoMovimiento_AMT123 INT,
IN fechaMovimiento_AMT123 DATE,
IN cbu_AMT123 VARCHAR(45),
IN detalle_AMT123 VARCHAR(25),
IN importe_AMT123 DECIMAL(20,2)
)
BEGIN
	DECLARE proximoIDMov INT; 
	SELECT IFNULL(MAX(MovimientosID_Mov), 0) + 1 INTO proximoIDMov FROM movimientos;
	-- Registro para quien hace la transefrencia:
    INSERT INTO movimientos (MovimientosID_Mov, NumeroCuenta_Mov, TipoMovimiento_Mov, Fecha_Mov, CBU_Mov, Detalle_Mov, Importe_Mov)
    VALUES (proximoIDMov,numCuentaSalida_AMT123,tipoMovimiento_AMT123,cbu_AMT123,detalle_AMT123,importe_AMT123);
	-- Registro para quien recibe la transefrencia:
	INSERT INTO movimientos (MovimientosID_Mov, NumeroCuenta_Mov, TipoMovimiento_Mov, Fecha_Mov, CBU_Mov, Detalle_Mov, Importe_Mov)
    VALUES (proximoIDMov,numCuentaEntrada_AMT123,tipoMovimiento_AMT123,cbu_AMT123,detalle_AMT123,importe_AMT123);

END $$
DELIMITER ;


-- Prestamos ----------------------------------------

-- Dar alta de prestamo:
use dbbanco;
DELIMITER $$
create procedure dbbanco.spDarAltaPrestamo
(
IN idPrestamo_DAP INT
)
BEGIN
	IF EXISTS (SELECT 1 FROM prestamos WHERE PrestamoID_Pres = idPrestamo_DAP) THEN
		UPDATE prestamos SET Estado_Pres = 1,  EstadoSolicitud_Pres = 'Aprobado'
			WHERE PrestamoID_Pres = idPrestamo_DAP;
    END IF;
END $$
DELIMITER ;

-- Rechazo de prestamo:
use dbbanco;
DELIMITER $$
create procedure dbbanco.spRechazoPrestamo
(
IN idPrestamo_DAP INT
)
BEGIN
	IF EXISTS (SELECT 1 FROM prestamos WHERE PrestamoID_Pres = idPrestamo_DAP) THEN
		UPDATE prestamos SET EstadoSolicitud_Pres = 'Rechazado' WHERE PrestamoID_Pres = idPrestamo_DAP;
    END IF;
END $$
DELIMITER ;

-- OBTENER PRESTAMOS DE UN CLIENTE X CLIENTE ID
DELIMITER //
CREATE PROCEDURE spObtenerPrestamosPorCliente(IN p_ClienteID INT)
BEGIN
    SELECT 
        pre.PrestamoID_Pres, 
        pre.ClienteAsignado_Pres, 
        pre.CuentaDelCliente_Pres, 
        pre.ImporteSoli_Pres, 
        pre.InteresesID_Pres, 
        pre.FechaPedidoPrestamo_Pres, 
        pre.ImporteCuota_Pres, 
        pre.ImporteTotal_Pres, 
        pre.PlazoPagoMeses, 
        pre.EstadoSolicitud_Pres, 
        pre.Estado_Pres,
        cli.ClienteID_Cli, 
        cli.UsuarioID_Cli, 
        cli.DNI, 
        cli.CUIL, 
        cli.Nombre, 
        cli.Apellido, 
        cli.Sexo, 
        cli.Nacionalidad, 
        cli.FechaDeNacimiento, 
        cli.Direccion, 
        cli.Localidad, 
        cli.Provincia, 
        cli.CorreoElectronico, 
        cli.Telefono, 
        cli.Estado_Cli,
        tc.TipoCuentaID_TCue, 
        tc.TipoDeCuenta,
        cue.NumeroCuenta_Cue, 
        cue.ClienteID_Cue, 
        cue.TipoDeCuenta_Cue, 
        cue.FechaCreacion, 
        cue.CBU_Cue, 
        cue.Saldo, 
        cue.Estado_Cue,
        inte.InteresesID_Int, 
        inte.Cuotas_Int, 
        inte.Porcentaje_Int
    FROM prestamos pre 
    LEFT JOIN clientes cli ON pre.ClienteAsignado_Pres = cli.ClienteID_Cli
    LEFT JOIN cuenta cue ON pre.CuentaDelCliente_Pres = cue.NumeroCuenta_Cue
    LEFT JOIN intereses inte ON pre.InteresesID_Pres = inte.InteresesID_Int
    LEFT JOIN tipocuenta tc ON cue.TipoDeCuenta_Cue = tc.TipoCuentaID_TCue
    WHERE pre.ClienteAsignado_Pres = p_ClienteID;
END //
DELIMITER ;


-- OBTENER TODOS LOS PRESTAMOS
DELIMITER //
CREATE PROCEDURE spObtenerPrestamosCompletos()
BEGIN
    SELECT 
        pre.PrestamoID_Pres, 
        pre.ClienteAsignado_Pres, 
        pre.CuentaDelCliente_Pres, 
        pre.ImporteSoli_Pres, 
        pre.InteresesID_Pres, 
        pre.FechaPedidoPrestamo_Pres, 
        pre.ImporteCuota_Pres, 
        pre.ImporteTotal_Pres, 
        pre.PlazoPagoMeses, 
        pre.EstadoSolicitud_Pres, 
        pre.Estado_Pres,
        cli.ClienteID_Cli, 
        cli.UsuarioID_Cli, 
        cli.DNI, 
        cli.CUIL, 
        cli.Nombre, 
        cli.Apellido, 
        cli.Sexo, 
        cli.Nacionalidad, 
        cli.FechaDeNacimiento, 
        cli.Direccion, 
        cli.Localidad, 
        cli.Provincia, 
        cli.CorreoElectronico, 
        cli.Telefono, 
        cli.Estado_Cli,
        cue.NumeroCuenta_Cue, 
        cue.ClienteID_Cue, 
        cue.TipoDeCuenta_Cue, 
        cue.FechaCreacion, 
        cue.CBU_Cue, 
        cue.Saldo, 
        cue.Estado_Cue,
        inte.InteresesID_Int, 
        inte.Cuotas_Int, 
        inte.Porcentaje_Int
    FROM 
        prestamos pre 
        LEFT JOIN clientes cli ON pre.ClienteAsignado_Pres = cli.ClienteID_Cli
        LEFT JOIN cuenta cue ON pre.CuentaDelCliente_Pres = cue.NumeroCuenta_Cue
        LEFT JOIN intereses inte ON pre.InteresesID_Pres = inte.InteresesID_Int;
END //

DELIMITER ;



USE dbbanco;
DELIMITER $$

CREATE PROCEDURE spAgregarPagosPrestamo(
    IN CantidadDeCuotas INT,
    IN PrestamoID_PDP INT,
    IN CuentaDelCliente_PDP INT,
    IN ImporteCuota_PDP DECIMAL(20,2)
)
BEGIN
    DECLARE proximoID_PagoPres INT;
    DECLARE fechaVencimiento DATE;
    DECLARE i INT DEFAULT 1;

    -- Inicializar el ID del próximo pago
    SELECT IFNULL(MAX(PagoPrestamoID_PDP), 0) + 1 INTO proximoID_PagoPres FROM pagoDelPrestamo;

    -- Inicializar la fecha de vencimiento
    SET fechaVencimiento = DATE_ADD(CURDATE(), INTERVAL 1 MONTH);

    -- Loop para insertar las cuotas
    WHILE i <= CantidadDeCuotas DO
        INSERT INTO pagoDelPrestamo(
            PagoPrestamoID_PDP,
            PrestamoID_PDP,
            CuentaDelCliente_PDP,
            FechaDePago_PDP,
            EstadoPrestamo_PDP,
            ImporteCuota_PDP,
            FechaDeVencimiento_PDP
        ) VALUES (
            proximoID_PagoPres,
            PrestamoID_PDP,
            CuentaDelCliente_PDP,
            NULL,
            'No Pagado',
            ImporteCuota_PDP,
            fechaVencimiento
        );
        
        -- Incrementar el ID del próximo pago
        SET proximoID_PagoPres = proximoID_PagoPres + 1;
        
        -- Calcular la próxima fecha de vencimiento
        SET fechaVencimiento = DATE_ADD(fechaVencimiento, INTERVAL 1 MONTH);
        
        -- Incrementar el contador del loop
        SET i = i + 1;
    END WHILE;

    -- Devolver la cantidad de filas afectadas
    SELECT CantidadDeCuotas AS FilasAfectadas;
    
END$$

DELIMITER ;




-- Pago del prestamo ------------------------------------

-- Para agregar pago de prestamo:
use dbbanco;
DELIMITER $$
create procedure dbbanco.spAgregarPagoPrestamo
(
IN prestammoID_APP INT,
IN numCuenta_APP INT,
IN fechaPago_APP DATE,
IN importeCuota_APP DECIMAL(20,2)
)
BEGIN
	DECLARE proximoID_PagoPres INT;
    DECLARE cuotasRestantes INT;
    DECLARE importeRestante DECIMAL(20,2);
    
	IF EXISTS (SELECT 1 FROM prestamos WHERE PrestamoID_Pres = prestammoID_APP) THEN
		SELECT IFNULL(MAX(PagoPrestamoID_PDP), 0) + 1 INTO proximoID_PagoPres FROM pagoDelPrestamo;
        
        SELECT ImporteRestante_PDP, CuotasRestantes_PDP
		INTO importeRestante, cuotasRestantes
		FROM pagoDelPrestamo
		WHERE PrestamoID_PDP = prestammoID_APP
		ORDER BY PagoPrestamoID_PDP DESC
		LIMIT 1;
			
        SET cuotasRestantes = cuotasRestantes - 1;
		SET importeRestante = importeRestante - importeCuota_APP;
		
        INSERT INTO pagoDelPrestamo(PagoPrestamoID_PDP,PrestamoID_PDP,CuentaDelCliente_PDP,FechaDePago_PDP,ImporteCuota_PDP,ImporteRestante_PDP,CuotasRestantes_PDP)
        VALUES (proximoID_PagoPres,prestammoID_APP,numCuenta_APP,fechaPago_APP,importeCuota_APP,importeRestante,cuotasRestantes);
    END IF;

END $$
DELIMITER ;


-- Para agregar usuario y cliente ------------------------------------------------------------------
use dbbanco;
DELIMITER $$
CREATE PROCEDURE dbbanco.spAgregarUsuarioYCliente
(
-- Para usuarios:
    IN tipoUsuarioID_AU INT,
    IN usuario_AU VARCHAR(45),
    IN contrasena_AU VARCHAR(45),
-- Para clientes:
    IN DNI_AC VARCHAR(20),
    IN CUIL_AC VARCHAR(20),
    IN nombre_AC VARCHAR(45),
    IN apellido_AC VARCHAR(45),
    IN sexo_AC ENUM('Femenino','Masculino'),
    IN nacionalidad_AC VARCHAR(45),
    IN fechaNacimiento_AC DATE,
    IN direccion_AC VARCHAR(45),
    IN localidad_AC VARCHAR(45),
    IN provincia_AC VARCHAR(45),
    IN correoElec_AC VARCHAR(45),
    IN telefono_AC VARCHAR(45),
    OUT resultado INT
)
BEGIN
    DECLARE proximoIdUsu INT;
    DECLARE proximoIdCli INT;

    -- Para usuario:
    SELECT IFNULL(MAX(UsuarioID_Usu), 0) + 1 INTO proximoIdUsu FROM usuario;
    IF NOT EXISTS (SELECT 1 FROM usuario WHERE Usuario_Usu = usuario_AU) THEN
        INSERT INTO usuario (UsuarioID_Usu, TipoUsuarioID_Usu, Usuario_Usu, Contrasena_Usu)
        VALUES (proximoIdUsu, tipoUsuarioID_AU, usuario_AU, contrasena_AU);
    END IF;

    -- Para cliente
    SELECT IFNULL(MAX(ClienteID_Cli), 0) + 1 INTO proximoIdCli FROM clientes;
    INSERT INTO clientes (ClienteID_Cli, UsuarioID_Cli, DNI, CUIL, Nombre, Apellido, Sexo, Nacionalidad, FechaDeNacimiento, Direccion, Localidad, Provincia, CorreoElectronico, Telefono)
    VALUES (proximoIdCli, proximoIdUsu, DNI_AC, CUIL_AC, nombre_AC, apellido_AC, sexo_AC, nacionalidad_AC, fechaNacimiento_AC, direccion_AC, localidad_AC, provincia_AC, correoElec_AC, telefono_AC);

    SET resultado = 1;
END $$
DELIMITER ;


use dbbanco;

insert into usuario(UsuarioID_Usu, TipoUsuarioID_Usu, Usuario_Usu, Contrasena_Usu, Estado_Usu)
values (3, 1, 'admin', 'admin', true);

insert into Intereses (InteresesID_Int, Cuotas_Int, Porcentaje_Int) 
values(1, 1, '10.00'),
(2, 3, '30.00'),
(3, 6, '60.00'),
(4, 12, '100.00');
