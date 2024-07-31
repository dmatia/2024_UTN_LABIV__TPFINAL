package daoImplement;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.iDaoPrestamo;
import entidad.Cliente;
import entidad.Cuentas;
import entidad.Intereses;
import entidad.Prestamos;
import entidad.TipoCuenta;
import entidad.Usuario;

public class DaoPrestamo implements iDaoPrestamo{
	
	public static String INSERTAR = "INSERT INTO Prestamos (PrestamoID_Pres, ClienteAsignado_Pres, CuentaDelCliente_Pres, ImporteSoli_Pres, InteresesID_Pres, FechaPedidoPrestamo_Pres, ImporteCuota_Pres, ImporteTotal_Pres, PlazoPagoMeses, EstadoSolicitud_Pres, Estado_Pres ) "
									+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
	public static String OBTENER_ID = "SELECT PrestamoID_Pres FROM prestamos ORDER BY PrestamoID_Pres DESC LIMIT 1";
	
	public List<Prestamos> obtenerPrestamosCompletos() {
	    Connection cn = ConexionDB.getConexion().getSQLConexion();
	    if (cn == null) {
	        System.out.println("No se pudo conectar a la base de datos en agregar cta cliente - DAO CUENTAS");
	        return null;
	    }

	    List<Prestamos> listaPrestamos = new ArrayList<>(); 

	    try {
	        cn.setAutoCommit(false);
	        CallableStatement cst = cn.prepareCall("CALL spObtenerPrestamosCompletos()");

	        ResultSet rs = cst.executeQuery();

	        while (rs.next()) {
	            Prestamos prestamo = new Prestamos();
	            Cliente cliente = new Cliente();
	            Cuentas cuenta = new Cuentas();
	            Intereses interes = new Intereses();

	            // Setear los campos del objeto Prestamo
	            prestamo.setPrestamoID(rs.getInt("PrestamoID_Pres"));
	            prestamo.setImporteSoli(rs.getDouble("ImporteSoli_Pres"));
	            prestamo.setFechaPedidoPrestamo(rs.getDate("FechaPedidoPrestamo_Pres"));
	            prestamo.setImporteCuota(rs.getDouble("ImporteCuota_Pres"));
	            prestamo.setImporteTotal(rs.getDouble("ImporteTotal_Pres"));
	            prestamo.setPlazoPagoMeses(rs.getInt("PlazoPagoMeses"));
	            prestamo.setEstadoSolicitud(rs.getString("EstadoSolicitud_Pres"));
	            prestamo.setEstado_Pres(rs.getBoolean("Estado_Pres"));

	            Usuario usu = new Usuario();
	            usu.setId(rs.getInt("UsuarioID_Cli"));

	            // Setear los campos del objeto Cliente
	            cliente.setIdCliente(rs.getInt("ClienteID_Cli"));
	            cliente.setIdUsuario(usu);
	            cliente.setDNI(rs.getString("DNI"));
	            cliente.setCUIL(rs.getString("CUIL"));
	            cliente.setNombre(rs.getString("Nombre"));
	            cliente.setApellido(rs.getString("Apellido"));
	            cliente.setSexo(rs.getString("Sexo"));
	            cliente.setNacionalidad(rs.getString("Nacionalidad"));
	            cliente.setFechaNacimiento(rs.getDate("FechaDeNacimiento"));
	            cliente.setDireccion(rs.getString("Direccion"));
	            cliente.setLocalidad(rs.getString("Localidad"));
	            cliente.setProvincia(rs.getString("Provincia"));
	            cliente.setCorreoElectronico(rs.getString("CorreoElectronico"));
	            cliente.setTelefono(rs.getString("Telefono"));
	            cliente.setEstado_Cli(rs.getBoolean("Estado_Cli"));

	            // Setear los campos del objeto Cuenta
	            cuenta.setNumeroCuenta_Cue(rs.getInt("NumeroCuenta_Cue"));
	            cuenta.setClienteID_Cue(rs.getInt("ClienteID_Cue"));
	            TipoCuenta tCuenta = new TipoCuenta();
	            tCuenta.setIdTipoCuenta(rs.getInt("TipoDeCuenta_Cue"));
	            cuenta.setTipoCuenta(tCuenta);
	            cuenta.setFechaCreacion(rs.getDate("FechaCreacion"));
	            cuenta.setCBU(rs.getString("CBU_Cue"));
	            cuenta.setSaldo(rs.getDouble("Saldo"));
	            cuenta.setEstado(rs.getBoolean("Estado_Cue"));

	            // Setear los campos del objeto Interes
	            interes.setInteresesID(rs.getInt("InteresesID_Int"));
	            interes.setCuotas(rs.getInt("Cuotas_Int"));
	            interes.setPorcentajes(rs.getDouble("Porcentaje_Int"));

	            // Añadir el cliente, cuenta e interes al objeto Prestamo
	            prestamo.setClienteAsignado(cliente);
	            prestamo.setCuentaDelCliente(cuenta);
	            prestamo.setIntereses(interes);

	            // Añadir el objeto Prestamo a la lista
	            listaPrestamos.add(prestamo);
	        }

	        cn.commit();
	        rs.close();
	        cst.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        if (cn != null) {
	            try {
	                cn.rollback();
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	    } finally {
	        if (cn != null) {
	            try {
	                cn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    return listaPrestamos;
	}
	
	public List<Prestamos> obtenerPrestamosPendientes(List<Prestamos> listaPrestamosCompletos) {
	    List<Prestamos> listaPrestamosPendientes = new ArrayList<>();

	    for (Prestamos prestamo : listaPrestamosCompletos) {
	        if ("Pendiente".equals(prestamo.getEstadoSolicitud())) {
	            listaPrestamosPendientes.add(prestamo);
	        }
	    }

	    return listaPrestamosPendientes;
	}
	
	public List<Prestamos> leerTodosListarPrestamos() {
		System.out.println("LEER TODOS");
        List<Prestamos> lista = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection cn = ConexionDB.getConexion().getSQLConexion();
        if (cn == null) {
            System.out.println("No se pudo conectar a la base de datos");
            return null;
        }

        String query = "SELECT PrestamoID_Pres, ClienteAsignado_Pres, CuentaDelCliente_Pres, InteresesID_Pres, " +
                       "FechaPedidoPrestamo_Pres, ImporteCuota_Pres, ImporteTotal_Pres, PlazoPagoMeses, EstadoSolicitud_Pres " +
                       "FROM prestamos";

        try (PreparedStatement ps = cn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Prestamos prestamo = new Prestamos();
                prestamo.setPrestamoID(rs.getInt("PrestamoID_Pres"));
                
                // Aquí necesitarás una lógica adicional para obtener los objetos Cliente, Cuentas e Intereses
                Cliente cliente = obtenerClientePorId(rs.getInt("ClienteAsignado_Pres"));
                Cuentas cuenta = obtenerCuentaPorId(rs.getInt("CuentaDelCliente_Pres"));
                Intereses intereses = obtenerInteresesPorId(rs.getInt("InteresesID_Pres"));

                prestamo.setClienteAsignado(cliente);
                prestamo.setCuentaDelCliente(cuenta);
                prestamo.setIntereses(intereses);

                prestamo.setFechaPedidoPrestamo(rs.getDate("FechaPedidoPrestamo_Pres"));
                prestamo.setImporteCuota(rs.getDouble("ImporteCuota_Pres"));
                prestamo.setImporteTotal(rs.getDouble("ImporteTotal_Pres"));
                prestamo.setPlazoPagoMeses(rs.getInt("PlazoPagoMeses"));
                prestamo.setEstadoSolicitud(rs.getString("EstadoSolicitud_Pres"));
                
                lista.add(prestamo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null && !cn.isClosed()) {
                    cn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return lista;
    }

	public Cliente obtenerClientePorId(int idCliente) {
		System.out.println("OBTENERPORID");
        Cliente cliente = null;
        String query = "SELECT ClienteID_Cli, UsuarioID_Cli, DNI, CUIL, Nombre, Apellido, Sexo, Nacionalidad, FechaDeNacimiento, Direccion, Localidad, Provincia, CorreoElectronico, Telefono, Estado_Cli FROM clientes WHERE ClienteID_Cli = ?";

        try (Connection cn = ConexionDB.getConexion().getSQLConexion();
             PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    Usuario usuario = new Usuario(); // Asumiendo que Usuario tiene un constructor vacío
                    usuario.setId(rs.getInt("UsuarioID_Cli"));
                    cliente.setIdUsuario(usuario);

                    cliente.setIdCliente(rs.getInt("ClienteID_Cli"));
                    cliente.setDNI(rs.getString("DNI"));
                    cliente.setCUIL(rs.getString("CUIL"));
                    cliente.setNombre(rs.getString("Nombre"));
                    cliente.setApellido(rs.getString("Apellido"));
                    cliente.setSexo(rs.getString("Sexo"));
                    cliente.setNacionalidad(rs.getString("Nacionalidad"));
                    cliente.setFechaNacimiento(rs.getDate("FechaDeNacimiento"));
                    cliente.setDireccion(rs.getString("Direccion"));
                    cliente.setLocalidad(rs.getString("Localidad"));
                    cliente.setProvincia(rs.getString("Provincia"));
                    cliente.setCorreoElectronico(rs.getString("CorreoElectronico"));
                    cliente.setTelefono(rs.getString("Telefono"));
                    cliente.setEstado_Cli(rs.getBoolean("Estado_Cli"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cliente;
    }
	
	public TipoCuenta obtenerTipoCuentaPorId(int idTipoCuenta) {
		System.out.println("OBTENERTIPOCUENTA");
	    TipoCuenta tipoCuenta = null;
	    String query = "SELECT idTipoCuenta, tipoCuentaDescripcion FROM tipo_cuenta WHERE idTipoCuenta = ?"; // Ajusta el nombre de la tabla y columnas según sea necesario

	    try (Connection cn = ConexionDB.getConexion().getSQLConexion();
	         PreparedStatement ps = cn.prepareStatement(query)) {
	        ps.setInt(1, idTipoCuenta);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                tipoCuenta = new TipoCuenta();
	                tipoCuenta.setIdTipoCuenta(rs.getInt("idTipoCuenta"));
	                tipoCuenta.setTipoCuentaDescripcion(rs.getString("tipoCuentaDescripcion"));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return tipoCuenta;
	}

	 public Cuentas obtenerCuentaPorId(int idCuenta) {
		 	System.out.println("OBTENERCUENTAID");
	        Cuentas cuenta = null;
	        String query = "SELECT NumeroCuenta_Cue, ClienteID_Cue, TipoDeCuenta_Cue, FechaCreacion, CBU_Cue, Saldo, Estado_Cue FROM cuenta WHERE NumeroCuenta_Cue = ?";

	        try (Connection cn = ConexionDB.getConexion().getSQLConexion();
	             PreparedStatement ps = cn.prepareStatement(query)) {
	            ps.setInt(1, idCuenta);
	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    cuenta = new Cuentas();
	                    cuenta.setNumeroCuenta_Cue(rs.getInt("NumeroCuenta_Cue"));
	                    
	                    // Aquí necesitarás una lógica adicional para obtener el objeto Cliente
	                    Cliente cliente = obtenerClientePorId(rs.getInt("ClienteID_Cue"));
	                    cuenta.setClienteID_Cue(cliente.getIdCliente());
	                    
	                    int tipoCuentaId = rs.getInt("TipoDeCuenta_Cue");
	                    TipoCuenta tipoCuenta = obtenerTipoCuentaPorId(tipoCuentaId);
	                    cuenta.setTipoCuenta(tipoCuenta);
	                    
	                    cuenta.setFechaCreacion(rs.getDate("FechaCreacion"));
	                    cuenta.setCBU(rs.getString("CBU_Cue"));
	                    cuenta.setSaldo(rs.getDouble("Saldo"));
	                    cuenta.setEstado(rs.getBoolean("Estado_Cue"));
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return cuenta;
	    }

	 public Intereses obtenerInteresesPorId(int idIntereses) {
	        Intereses intereses = null;
	        String query = "SELECT InteresesID_Int, Cuotas_Int, Porcentaje_Int FROM intereses WHERE InteresesID_Int = ?";

	        try (Connection cn = ConexionDB.getConexion().getSQLConexion();
	             PreparedStatement ps = cn.prepareStatement(query)) {
	            ps.setInt(1, idIntereses);
	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    intereses = new Intereses();
	                    intereses.setInteresesID(rs.getInt("InteresesID_Int"));
	                    intereses.setCuotas(rs.getInt("Cuotas_Int"));
	                    intereses.setPorcentajes(rs.getDouble("Porcentaje_Int"));
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return intereses;
	    }

	@Override
	public int insertar(Prestamos prestamo) {
		PreparedStatement ps = null;
	    Connection conexion = null;

	    try {
	        conexion = ConexionDB.getConexion().getSQLConexion();
	        conexion.setAutoCommit(false);

	        ps = conexion.prepareStatement(INSERTAR);
	        ps.setInt(1, prestamo.getPrestamoID());
	        ps.setInt(2, prestamo.getClienteAsignado().getIdCliente());
	        ps.setInt(3, prestamo.getCuentaDelCliente().getNumeroCuenta_Cue());
	        ps.setDouble(4, prestamo.getImporteSoli());
	        ps.setInt(5, prestamo.getIntereses().getInteresesID());
	        ps.setDate(6, prestamo.getFechaPedidoPrestamo());
	        ps.setDouble(7, prestamo.getImporteCuota());
	        ps.setDouble(8, prestamo.getImporteTotal());
	        ps.setInt(9, prestamo.getPlazoPagoMeses());
	        ps.setString(10, prestamo.getEstadoSolicitud());
	        ps.setBoolean(11, prestamo.isEstado_Pres());

	        int filasAfectadas = ps.executeUpdate();
	        
	        if (filasAfectadas > 0) {
	            conexion.commit();
	            return 1;
	        } else {
	            conexion.rollback();
	        }

	        return 0;

	    } catch (SQLException ex) {
	        System.out.println("Error sql: " + ex.getMessage());
	        ex.printStackTrace();
	        try {
	            if (conexion != null) {
	                conexion.rollback();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return 0;
	    } finally {
	        try {
	            if (ps != null) ps.close();
	            if (conexion != null) conexion.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}


	@Override
	public int obtenerUltimoId() {
		PreparedStatement ps = null;
	    ResultSet rs = null;
	    Connection conexion = null;
	    
	    try {
	        conexion = ConexionDB.getConexion().getSQLConexion();
	        ps = conexion.prepareStatement(OBTENER_ID);
	        rs = ps.executeQuery();
	        
	        if (rs.next()) {
	        	int idPrestamo = rs.getInt("PrestamoID_Pres");
	        	return idPrestamo + 1;
	        }
	        
	        return 0;
	        
	    } catch (SQLException ex) {
	    	System.out.println("Error sql");
	        ex.printStackTrace();
	        return 0;
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (conexion != null) conexion.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	public boolean actualizarPrestamo(Prestamos prestamo) {
        Connection conexion = ConexionDB.getConexion().getSQLConexion();
        String query = "UPDATE prestamos SET EstadoSolicitud_Pres = ? WHERE PrestamoID_Pres = ?";
        PreparedStatement statement;

        try {
            statement = conexion.prepareStatement(query);
            statement.setString(1, prestamo.getEstadoSolicitud());
            statement.setInt(2, prestamo.getPrestamoID());

            if (statement.executeUpdate() > 0) {
                conexion.commit();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conexion.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        return false;
    }

	public ArrayList<Prestamos> obtenerPrestamosPorClienteId(int clienteID) {
	    Connection cn = ConexionDB.getConexion().getSQLConexion();
	    if (cn == null) {
	        System.out.println("No se pudo conectar a la base de datos en obtener prestamos por cliente - DAO PRESTAMOS");
	        return null;
	    }
	    try {
	        ArrayList<Prestamos> listaPrestamos = new ArrayList<>();
	        cn.setAutoCommit(false);
	        CallableStatement cst = cn.prepareCall("CALL spObtenerPrestamosPorCliente(?)");
	        cst.setInt(1, clienteID);

	        ResultSet rs = cst.executeQuery();

	        while (rs.next()) {
	            Prestamos prestamo = new Prestamos();
	            Cliente cliente = new Cliente();
	            Cuentas cuenta = new Cuentas();
	            Intereses interes = new Intereses();
	            TipoCuenta tipoCuenta = new TipoCuenta();

	            prestamo.setPrestamoID(rs.getInt("PrestamoID_Pres"));
	            prestamo.setImporteSoli(rs.getDouble("ImporteSoli_Pres"));
	            prestamo.setFechaPedidoPrestamo(rs.getDate("FechaPedidoPrestamo_Pres"));
	            prestamo.setImporteCuota(rs.getDouble("ImporteCuota_Pres"));
	            prestamo.setImporteTotal(rs.getDouble("ImporteTotal_Pres"));
	            prestamo.setPlazoPagoMeses(rs.getInt("PlazoPagoMeses"));
	            prestamo.setEstadoSolicitud(rs.getString("EstadoSolicitud_Pres"));
	            prestamo.setEstado_Pres(rs.getBoolean("Estado_Pres"));

	            cliente.setIdCliente(rs.getInt("ClienteID_Cli"));
	            cliente.setDNI(rs.getString("DNI"));
	            cliente.setCUIL(rs.getString("CUIL"));
	            cliente.setNombre(rs.getString("Nombre"));
	            cliente.setApellido(rs.getString("Apellido"));
	            cliente.setSexo(rs.getString("Sexo"));
	            cliente.setNacionalidad(rs.getString("Nacionalidad"));
	            cliente.setFechaNacimiento(rs.getDate("FechaDeNacimiento"));
	            cliente.setDireccion(rs.getString("Direccion"));
	            cliente.setLocalidad(rs.getString("Localidad"));
	            cliente.setProvincia(rs.getString("Provincia"));
	            cliente.setCorreoElectronico(rs.getString("CorreoElectronico"));
	            cliente.setTelefono(rs.getString("Telefono"));
	            cliente.setEstado_Cli(rs.getBoolean("Estado_Cli"));

	            cuenta.setNumeroCuenta_Cue(rs.getInt("NumeroCuenta_Cue"));
	            cuenta.setClienteID_Cue(rs.getInt("ClienteID_Cue"));
	            cuenta.setFechaCreacion(rs.getDate("FechaCreacion"));
	            cuenta.setCBU(rs.getString("CBU_Cue"));
	            cuenta.setSaldo(rs.getDouble("Saldo"));
	            cuenta.setEstado(rs.getBoolean("Estado_Cue"));

	            tipoCuenta.setIdTipoCuenta(rs.getInt("TipoCuentaID_TCue"));
	            tipoCuenta.setTipoCuentaDescripcion(rs.getString("TipoDeCuenta"));

	            interes.setInteresesID(rs.getInt("InteresesID_Int"));
	            interes.setCuotas(rs.getInt("Cuotas_Int"));
	            interes.setPorcentajes(rs.getDouble("Porcentaje_Int"));

	            prestamo.setClienteAsignado(cliente);
	            prestamo.setCuentaDelCliente(cuenta);
	            cuenta.setTipoCuenta(tipoCuenta);
	            prestamo.setIntereses(interes);

	            listaPrestamos.add(prestamo);
	        }
	        return listaPrestamos;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	public Prestamos obtenerPrestamoPorID(int idPrestamo) {
	    Prestamos prestamo = null;
	    String query = "SELECT PrestamoID_Pres, ClienteAsignado_Pres, CuentaDelCliente_Pres, ImporteSoli_Pres, " +
	                   "InteresesID_Pres, FechaPedidoPrestamo_Pres, ImporteCuota_Pres, ImporteTotal_Pres, " +
	                   "PlazoPagoMeses, EstadoSolicitud_Pres, Estado_Pres " +
	                   "FROM prestamos WHERE PrestamoID_Pres = ?";

	    try (Connection cn = ConexionDB.getConexion().getSQLConexion();
	         PreparedStatement ps = cn.prepareStatement(query)) {
	        ps.setInt(1, idPrestamo);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                prestamo = new Prestamos();
	                prestamo.setPrestamoID(rs.getInt("PrestamoID_Pres"));
	                prestamo.setImporteSoli(rs.getDouble("ImporteSoli_Pres"));
	                prestamo.setFechaPedidoPrestamo(rs.getDate("FechaPedidoPrestamo_Pres"));
	                prestamo.setImporteCuota(rs.getDouble("ImporteCuota_Pres"));
	                prestamo.setImporteTotal(rs.getDouble("ImporteTotal_Pres"));
	                prestamo.setPlazoPagoMeses(rs.getInt("PlazoPagoMeses"));
	                prestamo.setEstadoSolicitud(rs.getString("EstadoSolicitud_Pres"));
	                prestamo.setEstado_Pres(rs.getBoolean("Estado_Pres"));

	                Cliente cliente = obtenerClientePorId(rs.getInt("ClienteAsignado_Pres"));
	                Cuentas cuenta = obtenerCuentaPorId(rs.getInt("CuentaDelCliente_Pres"));
	                Intereses intereses = obtenerInteresesPorId(rs.getInt("InteresesID_Pres"));

	                prestamo.setClienteAsignado(cliente);
	                prestamo.setCuentaDelCliente(cuenta);
	                prestamo.setIntereses(intereses);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return prestamo;
	}

}