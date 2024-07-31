package daoImplement;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.iDaoReportes;

public class DaoReportes implements iDaoReportes {
//    private Connection connection;
//
//    public DaoReportes(Connection connection) {
//        this.connection = connection;
//    }

    // Consulta SQL para obtener la cantidad de préstamos aprobados
    private static final String OBTENER_CANTIDAD_PRESTAMOS = 
        "SELECT COUNT(*) AS cantidadPrestamos FROM prestamos WHERE ClienteAsignado_Pres = ? AND EstadoSolicitud_Pres = 'Aprobado'";

    // Consulta SQL para obtener el total de transferencias enviadas
    private static final String OBTENER_TOTAL_TRANSFERENCIAS = 
    		 "SELECT SUM(Importe_Mov) AS totalTransferencias " +
    				    "FROM movimientos " +
    				    "WHERE NumeroCuenta_Mov IN (SELECT NumeroCuenta_Cue FROM cuenta WHERE ClienteID_Cue = ?) " +
    				    "AND TipoMovimiento_Mov = 4 " +
    				    "AND Detalle_Mov = 'Transferencia Enviada'";

    // Consulta SQL para obtener el nombre del cliente
    private static final String OBTENER_NOMBRE_CLIENTE = 
        "SELECT Nombre FROM clientes WHERE ClienteID_Cli = ?";

    // Consulta SQL para obtener el importe total de todos los préstamos del cliente
    private static final String OBTENER_IMPORTE_TOTAL_PRESTAMOS = 
        "SELECT SUM(ImporteTotal_Pres) AS totalImportePrestamos FROM prestamos WHERE ClienteAsignado_Pres = ? AND EstadoSolicitud_Pres = 'Aprobado'";

    // Consulta SQL para obtener las cuotas pagadas del cliente
    private static final String OBTENER_CUOTAS_PAGADAS = 
        "SELECT SUM(ImporteCuota_PDP) AS totalCuotasPagadas FROM pagoDelPrestamo WHERE PrestamoID_PDP IN (SELECT PrestamoID_Pres FROM prestamos WHERE ClienteAsignado_Pres = ? AND EstadoSolicitud_Pres = 'Aprobado')";

    //Consulta SQL para obtener saldo entre fechas
//    private static final String OBTENER_SALDO_ENTRE_FECHAS = 
//    	    "SELECT " +
//    	    "SUM(CASE WHEN m.TipoMovimiento_Mov = 1 THEN m.Importe_Mov ELSE -m.Importe_Mov END) AS saldoEntreFechas " +
//    	    "FROM " +
//    	    "movimientos m " +
//    	    "JOIN cuenta c ON m.NumeroCuenta_Mov = c.NumeroCuenta_Cue " +
//    	    "JOIN clientes cli ON c.ClienteID_Cue = cli.ClienteID_Cli " +
//    	    "WHERE " +
//    	    "cli.ClienteID_Cli = ? " +
//    	    "AND m.Fecha_Mov BETWEEN ? AND ?";  
    
    private static final String OBTENER_TOTAL_GASTADO_ENTRE_FECHAS = 
    	    "SELECT " +
    	    "SUM(m.Importe_Mov) AS totalGastado " +
    	    "FROM " +
    	    "movimientos m " +
    	    "JOIN cuenta c ON m.NumeroCuenta_Mov = c.NumeroCuenta_Cue " +
    	    "JOIN clientes cli ON c.ClienteID_Cue = cli.ClienteID_Cli " +
    	    "WHERE " +
    	    "cli.ClienteID_Cli = ? " +
    	    "AND m.TipoMovimiento_Mov IN (3, 4) " +
    	    "AND m.Detalle_Mov = 'Transferencia Enviada' " +
    	    "AND m.Fecha_Mov BETWEEN ? AND ?";
    
    public double obtenerTotalTransferencias(Date startDate, Date endDate) throws SQLException {
        String query = "SELECT SUM(Importe_Mov) AS TotalTransfers " +
                       "FROM movimientos " +
                       "WHERE Fecha_Mov BETWEEN ? AND ? " +
                       "AND Detalle_Mov = 'Transferencia Enviada'";
        
        Connection connection = ConexionDB.getConexion().getSQLConexion();
	    if (connection == null) {
	        System.out.println("No se pudo conectar a la base de datos");
	        return 0;
	    }
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, startDate);
            preparedStatement.setDate(2, endDate);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("TotalTransfers");
                } else {
                    return 0.0;
                }
            }
        }
    }

    
    
    @Override
    public int obtenerCantidadPrestamos(int clienteId) throws SQLException {
    	Connection connection = ConexionDB.getConexion().getSQLConexion();
	    if (connection == null) {
	        System.out.println("No se pudo conectar a la base de datos");
	        return 0;
	    }
    	
    	try (PreparedStatement stmt = connection.prepareStatement(OBTENER_CANTIDAD_PRESTAMOS)) {
            stmt.setInt(1, clienteId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("cantidadPrestamos");
                }
            }
        }
        return 0;
    }

    @Override
    public BigDecimal obtenerTotalTransferencias(int clienteId) throws SQLException {
    	Connection connection = ConexionDB.getConexion().getSQLConexion();
	    if (connection == null) {
	        System.out.println("No se pudo conectar a la base de datos");
	        return null;
	    }
    	try (PreparedStatement stmt = connection.prepareStatement(OBTENER_TOTAL_TRANSFERENCIAS)) {
            stmt.setInt(1, clienteId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("totalTransferencias");
                }
            }
        }
        return BigDecimal.ZERO;
    }

    @Override
    public String obtenerNombreCliente(int clienteId) throws SQLException {
    	Connection connection = ConexionDB.getConexion().getSQLConexion();
	    if (connection == null) {
	        System.out.println("No se pudo conectar a la base de datos");
	        return null;
	    }
    	try (PreparedStatement stmt = connection.prepareStatement(OBTENER_NOMBRE_CLIENTE)) {
            stmt.setInt(1, clienteId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Nombre");
                }
            }
        }
        return null;
    }

    @Override
    public BigDecimal obtenerImporteRestantePrestamo(int clienteId) throws SQLException {
        BigDecimal totalImportePrestamos = BigDecimal.ZERO;
        BigDecimal totalCuotasPagadas = BigDecimal.ZERO;

        Connection connection = ConexionDB.getConexion().getSQLConexion();
	    if (connection == null) {
	        System.out.println("No se pudo conectar a la base de datos");
	        return null;
	    }
        try (PreparedStatement stmtTotal = connection.prepareStatement(OBTENER_IMPORTE_TOTAL_PRESTAMOS)) {
            stmtTotal.setInt(1, clienteId);
            try (ResultSet rsTotal = stmtTotal.executeQuery()) {
                if (rsTotal.next() && rsTotal.getBigDecimal("totalImportePrestamos") != null) {
                    totalImportePrestamos = rsTotal.getBigDecimal("totalImportePrestamos");
                }
            }
        }

        try (PreparedStatement stmtCuotas = connection.prepareStatement(OBTENER_CUOTAS_PAGADAS)) {
            stmtCuotas.setInt(1, clienteId);
            try (ResultSet rsCuotas = stmtCuotas.executeQuery()) {
                if (rsCuotas.next() && rsCuotas.getBigDecimal("totalCuotasPagadas") != null) {
                    totalCuotasPagadas = rsCuotas.getBigDecimal("totalCuotasPagadas");
                }
            }
        }

        BigDecimal importeRestante = totalImportePrestamos.subtract(totalCuotasPagadas);
        return importeRestante.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : importeRestante;
    }
    
    //PARA SABER CUÁNTO GASTO ENTRE LAS FECHAS INGRESADAS
    public BigDecimal obtenerSaldoEntreFechas(int clienteId, Date fechaInicio, Date fechaFin) throws SQLException {
    	BigDecimal saldoEntreFechas = BigDecimal.ZERO;
        Connection connection = ConexionDB.getConexion().getSQLConexion();

        if (connection == null) {
            System.out.println("No se pudo conectar a la base de datos");
            return null; 
        }

        String sql = "SELECT " +
                "SUM(m.Importe_Mov) AS totalGastado " +
                "FROM movimientos m " +
                "JOIN cuenta c ON m.NumeroCuenta_Mov = c.NumeroCuenta_Cue " +
                "JOIN clientes cli ON c.ClienteID_Cue = cli.ClienteID_Cli " +
                "WHERE cli.ClienteID_Cli = ? " +
                "AND ((m.TipoMovimiento_Mov = 3) OR (m.TipoMovimiento_Mov = 4 AND m.Detalle_Mov = 'Transferencia Enviada')) " +
                "AND m.Fecha_Mov BETWEEN ? AND ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            stmt.setDate(2, new java.sql.Date(fechaInicio.getTime())); 
            stmt.setDate(3, new java.sql.Date(fechaFin.getTime())); 
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    saldoEntreFechas = rs.getBigDecimal("totalGastado");
                    if (saldoEntreFechas == null) {
                        saldoEntreFechas = BigDecimal.ZERO;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el saldo entre fechas: " + e.getMessage());
            throw e; 
        } catch (NullPointerException e) {
            System.err.println("NullPointerException en obtenerSaldoEntreFechas: " + e.getMessage());
            throw e; 
        } finally {
        	System.err.println("Salio por el finally en obtenerSaldoEntreFechas");
        }

        return saldoEntreFechas;
    }
    
    
    public BigDecimal obtenerSaldoRecibidoEntreFechas(int clienteId, Date fechaInicio, Date fechaFin) throws SQLException {
    	BigDecimal saldoRecibidoEntreFechas = BigDecimal.ZERO;
        Connection connection = ConexionDB.getConexion().getSQLConexion();

        if (connection == null) {
            System.out.println("No se pudo conectar a la base de datos");
            return null; 
        }

        String sql = "SELECT " +
                "SUM(m.Importe_Mov) AS totalRecibido " +
                "FROM movimientos m " +
                "JOIN cuenta c ON m.NumeroCuenta_Mov = c.NumeroCuenta_Cue " +
                "JOIN clientes cli ON c.ClienteID_Cue = cli.ClienteID_Cli " +
                "WHERE cli.ClienteID_Cli = ? " +
                "AND ((m.TipoMovimiento_Mov = 1) OR (m.TipoMovimiento_Mov = 4 AND m.Detalle_Mov = 'Transferencia Recibida')) " +
                "AND m.Fecha_Mov BETWEEN ? AND ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            stmt.setDate(2, new java.sql.Date(fechaInicio.getTime())); 
            stmt.setDate(3, new java.sql.Date(fechaFin.getTime())); 
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    saldoRecibidoEntreFechas = rs.getBigDecimal("totalRecibido");
                    if (saldoRecibidoEntreFechas == null) {
                    	saldoRecibidoEntreFechas = BigDecimal.ZERO;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el saldo entre fechas: " + e.getMessage());
            throw e; 
        } catch (NullPointerException e) {
            System.err.println("NullPointerException en obtenerSaldoRecibidoEntreFechas: " + e.getMessage());
            throw e; 
        } finally {
        	System.err.println("Salio por el finally en obtenerSaldoRecibidoEntreFechas");
        }

        return saldoRecibidoEntreFechas;
    }
    
    
    
}