package daoImplement;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.iDaoPagoPrestamo;
import entidad.PagoPrestamo;


public class DaoPagoPrestamo implements iDaoPagoPrestamo{
	
	  public static String OBTENER_x_PRESTAMOID = "SELECT PagoPrestamoID_PDP as 'PagoPrestamoID', PrestamoID_PDP as 'PrestamoID', CuentaDelCliente_PDP as 'CuentaDelCliente', FechaDePago_PDP as 'FechaDePago', EstadoPrestamo_PDP as 'EstadoPago', ImporteCuota_PDP as 'ImporteCuota', FechaDeVencimiento_PDP as 'FechaVencimientoPago' FROM pagodelprestamo WHERE PrestamoID_PDP = ?";
	 
	  public static String UPDATE_PAGOPRESTAMO = "UPDATE pagodelprestamo SET PrestamoID_PDP = ?, CuentaDelCliente_PDP = ?, FechaDePago_PDP = ?, EstadoPrestamo_PDP = ?, ImporteCuota_PDP = ?, FechaDeVencimiento_PDP = ? WHERE PagoPrestamoID_PDP = ?";

	@Override
	public boolean insertarPagoPrestamo(PagoPrestamo pagoPrestamo) {
	    Connection connection = ConexionDB.getConexion().getSQLConexion();
	    boolean isInserted = false;
	    
	    String sql = "INSERT INTO pagodelprestamo (PagoPrestamoID_PDP, PrestamoID_PDP, CuentaDelCliente_PDP, EstadoPrestamo_PDP, ImporteCuota_PDP, FechaDeVencimiento_PDP, FechaDePago_PDP) VALUES (?, ?, ?, ?, ?, ?, ?)";
	    
	    try (PreparedStatement statement = connection.prepareStatement(sql)) {
	        int pagoPrestamoID = generarNuevoPagoPrestamoID();
	        pagoPrestamo.setPagoPrestamoID(pagoPrestamoID);

	        statement.setInt(1, pagoPrestamo.getPagoPrestamoID());
	        statement.setInt(2, pagoPrestamo.getPrestamoID());
	        statement.setInt(3, pagoPrestamo.getCuentaDelCliente());
	        statement.setString(4, pagoPrestamo.getEstadoPago());
	        statement.setDouble(5, pagoPrestamo.getImporteCuota());
	        statement.setDate(6, pagoPrestamo.getFechaVencimientoPago());
	        statement.setDate(7, pagoPrestamo.getFechaDePago() != null ? pagoPrestamo.getFechaDePago() : null);

	        if (statement.executeUpdate() > 0) {
	            connection.commit();
	            isInserted = true;
	        } else {
	            connection.rollback();
	        }
	    } catch (SQLException e) {
	        try {
	            connection.rollback();
	        } catch (SQLException rollbackEx) {
	            rollbackEx.printStackTrace();
	        }
	        e.printStackTrace();
	    } finally {
	        ConexionDB.getConexion().cerrarConexion();
	    }
	    return isInserted;
	}
	
	public int generarNuevoPagoPrestamoID() {
//	    Connection connection = ConexionDB.getConexion().getSQLConexion();
//	    int nuevoID = 1;
//
//	    String sql = "SELECT MAX(PagoPrestamoID_PDP) AS maxID FROM pagodelprestamo";
//	    
//	    try (Statement statement = (Statement) connection.createStatement();
//	         ResultSet resultSet = statement.executeQuery(sql)) {
//	        if (resultSet.next()) {
//	            nuevoID = resultSet.getInt("maxID") + 1;
//	        }
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	    } finally {
//	        ConexionDB.getConexion().cerrarConexion();
//	    }
//	    return nuevoID;
		return 1;
	}

	@Override
	public ArrayList<PagoPrestamo> obtenerPagosPrestamoPorPrestamoId(int idPrestamo) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		ArrayList<PagoPrestamo> listaPagoPrestamo = new ArrayList<>();
		Connection cn = ConexionDB.getConexion().getSQLConexion();
		if (cn == null) {
			System.out.println("No se pudo conectar a la base de datos desde obtenerPrestamosPorPrestamoID - DAO PAGO PRESTAMO");
			return null;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = cn.prepareStatement(OBTENER_x_PRESTAMOID);
			ps.setInt(1, idPrestamo);
			rs = ps.executeQuery();

			while (rs.next()) {
				PagoPrestamo pagoPrestamo = new PagoPrestamo();
				pagoPrestamo.setPagoPrestamoID(rs.getInt("PagoPrestamoID"));
				pagoPrestamo.setPrestamoID(rs.getInt("PrestamoID"));
				pagoPrestamo.setCuentaDelCliente(rs.getInt("CuentaDelCliente"));
				pagoPrestamo.setFechaDePago(rs.getDate("FechaDePago"));
				pagoPrestamo.setEstadoPago(rs.getString("EstadoPago"));
				pagoPrestamo.setImporteCuota(rs.getDouble("ImporteCuota"));
				pagoPrestamo.setFechaVencimientoPago(rs.getDate("FechaVencimientoPago"));
				listaPagoPrestamo.add(pagoPrestamo);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return listaPagoPrestamo;
	}

	@Override
	public boolean modificarPagoPrestamo(PagoPrestamo pagoPrestamo) {
		Connection cn = ConexionDB.getConexion().getSQLConexion();
	    if (cn == null) {
	        System.out.println("No se pudo conectar a la base de datos desde modificarPagoPrestamo");
	        return false;
	    }
	    PreparedStatement pst = null;
	    try {
	        cn.setAutoCommit(false);
	        pst = cn.prepareStatement(UPDATE_PAGOPRESTAMO);
	        pst.setInt(1,pagoPrestamo.getPrestamoID());
	        pst.setInt(2, pagoPrestamo.getCuentaDelCliente());
	        pst.setDate(3, pagoPrestamo.getFechaDePago());
	        pst.setString(4, pagoPrestamo.getEstadoPago());
	        pst.setDouble(5, pagoPrestamo.getImporteCuota());
	        pst.setDate(6, pagoPrestamo.getFechaVencimientoPago());
	        pst.setInt(7, pagoPrestamo.getPagoPrestamoID());
	        int filas = pst.executeUpdate();
	        if (filas > 0) {
	            cn.commit();
	            return true;
	        } else {
	            cn.rollback();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.print("No se pudo conectar a la base en update pago prestamo DAO");
	        try {
	            if (cn != null) {
	                cn.rollback();
	            }
	        } catch (SQLException ex) {
	        	System.out.println(ex.getMessage());
	           // ex.printStackTrace();
	        }
	    } finally {
	        try {
	            if (pst != null) {
	                pst.close();
	            }
	            if (cn != null) {
	                cn.close();
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }
	    return false;
	}

	@Override
	public int insertarPlanDePago(int cantCuotas, int idPrestamo, int idCuenta, Double importeCuota) {
	    System.out.println("ESTOY EN INSERTAR PLAN DE PAGO");

	    Connection cn = ConexionDB.getConexion().getSQLConexion();
	    if (cn == null) {
	        System.out.println("no se pudo conectar a la base de datos en insertarPlanPago");
	        return 0;
	    }
	    CallableStatement cst = null;
	    ResultSet rs = null;
	    int filasAfectadas = 0;
	    try {
	        cn.setAutoCommit(false);
	        cst = cn.prepareCall("CALL spAgregarPagosPrestamo(?, ?, ?, ?)");
	        cst.setInt(1, cantCuotas);
	        cst.setInt(2, idPrestamo);
	        cst.setInt(3, idCuenta);
	        cst.setDouble(4, importeCuota);

	        boolean hasResultSet = cst.execute();
	        if (hasResultSet) {
	            rs = cst.getResultSet();
	            if (rs.next()) {
	                filasAfectadas = rs.getInt("FilasAfectadas");
	            }
	        }

	        if (filasAfectadas > 0) {
	            System.out.println("FILAS MAYOR A CERO: " + filasAfectadas);
	            cn.commit();
	        } else {
	            System.out.println("FILAS MENOS A 0 - FILAS: " + filasAfectadas);
	            cn.rollback();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.print("No se pudo conectar a la base en insertar plan pagos -dao");
	        try {
	            if (cn != null) {
	                cn.rollback();
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (cst != null) {
	                cst.close();
	            }
	            if (cn != null) {
	                cn.close();
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }
	    return filasAfectadas;
	}


	
}
