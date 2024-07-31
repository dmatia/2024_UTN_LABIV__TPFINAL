package daoImplement;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.iDaoCuentas;
import entidad.Cuentas;
import entidad.TipoCuenta;

public class DaoCuentas implements iDaoCuentas {

	public static String OBTENER_X_NROCUENTA = "SELECT NumeroCuenta_Cue, ClienteID_Cue, TipoDeCuenta_Cue, FechaCreacion, CBU_Cue, Saldo, Estado_Cue FROM cuenta WHERE NumeroCuenta_Cue = ?";
    public static String OBTENER_TIPOCUENTA = "SELECT TipoCuentaID_TCue, TipoDeCuenta FROM tipocuenta WHERE TipoCuentaID_TCue = ?";
    public static String OBTENER_X_CBU = "SELECT NumeroCuenta_Cue, ClienteID_Cue, TipoDeCuenta_Cue, FechaCreacion, CBU_Cue, Saldo, Estado_Cue FROM cuenta WHERE CBU_Cue = ?";
    public static String OBTENER_SALDO = "SELECT Saldo FROM cuenta WHERE NumeroCuenta_Cue = ?";
    
    @Override
	public int modificarEstadoATrueCuenta(int idCuenta) {
    	Connection cn = ConexionDB.getConexion().getSQLConexion();
	    if (cn == null) {
	        System.out.println("No se pudo conectar a la base de datos");
	        return 0;
	    }
	    PreparedStatement pst = null;
	    try {
	        cn.setAutoCommit(false);
	        String query = "UPDATE cuenta SET Estado_Cue = 1 WHERE NumeroCuenta_Cue = ?";
	        pst = cn.prepareStatement(query);
	        pst.setInt(1, idCuenta);
	        
	        int filas = pst.executeUpdate();
	        if (filas > 0) {
	            cn.commit();
	            return 1;
	        } else {
	            cn.rollback();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.print("No se pudo conectar a la base");
	        try {
	            if (cn != null) {
	                cn.rollback();
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
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
	    return 0;
	}
    
    @Override
    public int darBaja(int id) {
    	Connection cn = ConexionDB.getConexion().getSQLConexion();
		if (cn == null) {
		    System.out.println("no se pudo conectar a la base de datos");
		    return 0;
		}
		CallableStatement cst = null;
		try {
		    cn.setAutoCommit(false);
		    cst = cn.prepareCall("CALL spBajaLogicaCuenta(?)");
		    cst.setInt(1, id);
		    
		    int filas = cst.executeUpdate();
		    if (filas > 0) {
		        cn.commit();
		        return 1;
		    } else {
		        cn.rollback();
		    }
		} catch (SQLException e) {
		    e.printStackTrace();
		    System.out.print("No se pudo conectar a la base");
		    try {
		        if (cn != null) {
		            cn.rollback();
		        }
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		    }
		} finally {
		    try {
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
		return 0;
    }

    @Override
    public List<Cuentas> leerTodos() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<Cuentas> obtenerCuentasDeUnCliente(int id) {
        ArrayList<Cuentas> lista = new ArrayList<>();
        String sql = "SELECT NumeroCuenta_Cue, ClienteID_Cue, TipoDeCuenta_Cue, FechaCreacion, CBU_Cue, Saldo, Estado_Cue FROM cuenta WHERE ClienteID_Cue = ?";

        try (Connection cn = ConexionDB.getConexion().getSQLConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id); // id cliente

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cuentas cuenta = new Cuentas();
                    cuenta.setNumeroCuenta_Cue(rs.getInt("NumeroCuenta_Cue"));
                    cuenta.setClienteID_Cue(rs.getInt("ClienteID_Cue"));
                    TipoCuenta tipoCuenta = obtenerTipoCuentaPorID(rs.getInt("TipoDeCuenta_Cue"), cn);
                    cuenta.setTipoCuenta(tipoCuenta);
                    cuenta.setFechaCreacion(rs.getDate("FechaCreacion"));
                    cuenta.setCBU(rs.getString("CBU_Cue"));
                    cuenta.setSaldo(rs.getDouble("Saldo"));
                    cuenta.setEstado(rs.getBoolean("Estado_Cue"));
                    lista.add(cuenta);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Cuentas obtenerCuentaPorNro(int nroCuenta) {
        System.out.println("Estoy en obtenerCuentaPorNro (Dao)");
        try (Connection conexion = ConexionDB.getConexion().getSQLConexion();
             PreparedStatement ps = conexion.prepareStatement(OBTENER_X_NROCUENTA)) {

            ps.setInt(1, nroCuenta);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int numeroCuenta_Cue = rs.getInt("NumeroCuenta_Cue");
                    int clienteID_Cue = rs.getInt("ClienteID_Cue");
                    TipoCuenta tipoCuenta = obtenerTipoCuentaPorID(rs.getInt("TipoDeCuenta_Cue"), conexion);
                    Date fechaCreacion = rs.getDate("FechaCreacion");
                    String cbu_Cue = rs.getString("CBU_Cue");
                    Double saldo = rs.getDouble("Saldo");
                    boolean estado = rs.getBoolean("Estado_Cue");
                    return new Cuentas(numeroCuenta_Cue, clienteID_Cue, tipoCuenta, fechaCreacion, cbu_Cue, saldo, estado);
                }
            }

        } catch (SQLException ex) {
            System.out.println("Error sql al traer la cuenta buscada por ID");
            ex.printStackTrace();
        }
        return null;
    }

    public TipoCuenta obtenerTipoCuentaPorID(int tipoCuentaId, Connection conexion) {
        System.out.println("Estoy en obtenerTipoCuentaPorID - DAOCUENTAS");
        try (PreparedStatement ps = conexion.prepareStatement(OBTENER_TIPOCUENTA)) {

            ps.setInt(1, tipoCuentaId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idTipoCuenta = rs.getInt("TipoCuentaID_TCue");
                    String tipoCuentaDescripcion = rs.getString("TipoDeCuenta");
                    return new TipoCuenta(idTipoCuenta, tipoCuentaDescripcion);
                }
            }

        } catch (SQLException ex) {
            System.out.println("Error sql al traer el tipo de cuenta buscado por ID");
            ex.printStackTrace();
        }
        return null;
    }
    

	public int validarCantidadCuentasCliente(int idCliente) {
	    Connection cn = ConexionDB.getConexion().getSQLConexion();
	    if (cn == null) {
	        System.out.println("no se pudo conectar a la base de datos en validar cant. cuentas cliente - DAO CUENTAS");
	        return 0;
	    }
	    try {
	    	 CallableStatement cst = cn.prepareCall("{CALL spCantidadCuentasPorCliente(?, ?)}");
	         cst.setInt(1, idCliente);
	         cst.registerOutParameter(2, java.sql.Types.INTEGER);
	         

	         cst.execute();

	         int totalCuentas = cst.getInt(2);
	         return totalCuentas;
	     } catch (SQLException e) {
	         e.printStackTrace();
	         System.out.print("No se pudo conectar a la base");
	     }
	     return -1;
	 }

	
	public Cuentas agregarCuenta(int idCliente, int tipoCuenta) {
	    Connection cn = ConexionDB.getConexion().getSQLConexion();
	    if (cn == null) {
	        System.out.println("No se pudo conectar a la base de datos en agregar cta cliente - DAO CUENTAS");
	        return null;
	    }
	    Cuentas cuenta = null;
	    try {
	        cn.setAutoCommit(false);
	        CallableStatement cst = cn.prepareCall("{CALL spAgregarCuenta(?, ?, ?)}");
	        cst.setInt(1, idCliente);
	        cst.setInt(2, tipoCuenta);
	        cst.registerOutParameter(3, java.sql.Types.INTEGER);
	        
	        ResultSet rs = cst.executeQuery();
	        
	        if (rs.next()) {
	            cuenta = new Cuentas();
	            cuenta.setNumeroCuenta_Cue(rs.getInt("NumeroCuenta_Cue"));
	            cuenta.setClienteID_Cue(rs.getInt("ClienteID_Cue"));
	            int idTipoCuenta = rs.getInt("TipoDeCuenta_Cue");
	            TipoCuenta tipoC = obtenerTipoCuentaPorID(idTipoCuenta, cn);
	            cuenta.setTipoCuenta(tipoC);
	            cuenta.setFechaCreacion(rs.getDate("FechaCreacion"));
	            cuenta.setCBU(rs.getString("CBU_Cue"));
	            cuenta.setSaldo(rs.getBigDecimal("Saldo").doubleValue());
	            
	            System.out.println("Cuenta agregada - NumeroCuenta_Cue: " + cuenta.getNumeroCuenta_Cue());
	            cn.commit();
	        } else {
	            System.out.println("La operaci n fall .");
	            cn.rollback();
	        }
	    } catch (SQLException e) {
	        try {
	            if (cn != null) {
	                cn.rollback();
	            }
	        } catch (SQLException rollbackEx) {
	            rollbackEx.printStackTrace();
	        }
	        e.printStackTrace();
	        System.out.print("No se pudo conectar a la base desde agregarCuenta CUENTAS DAO");
	    } finally {
	        try {
	            if (cn != null) {
	                cn.setAutoCommit(true);
	                cn.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return cuenta;
	}

	@Override
	public Cuentas obtenerCuentaPorCBU(String CBU) {
		System.out.println("Estoy en obtenerCuentaPorCBU (Dao)");
        try (Connection conexion = ConexionDB.getConexion().getSQLConexion();
             PreparedStatement ps = conexion.prepareStatement(OBTENER_X_CBU)) {

            ps.setString(1, CBU);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int numeroCuenta_Cue = rs.getInt("NumeroCuenta_Cue");
                    int clienteID_Cue = rs.getInt("ClienteID_Cue");
                    TipoCuenta tipoCuenta = obtenerTipoCuentaPorID(rs.getInt("TipoDeCuenta_Cue"), conexion);
                    Date fechaCreacion = rs.getDate("FechaCreacion");
                    String cbu_Cue = rs.getString("CBU_Cue");
                    Double saldo = rs.getDouble("Saldo");
                    boolean estado = rs.getBoolean("Estado_Cue");
                    return new Cuentas(numeroCuenta_Cue, clienteID_Cue, tipoCuenta, fechaCreacion, cbu_Cue, saldo, estado);
                }
            }

        } catch (SQLException ex) {
            System.out.println("Error sql al traer la cuenta buscada por CBU");
            ex.printStackTrace();
        }
        return null;
	}

	@Override
	public int actualizarSaldoCuenta(int idCuenta, double saldo) {
		Connection cn = ConexionDB.getConexion().getSQLConexion();
	    if (cn == null) {
	        System.out.println("No se pudo conectar a la base de datos");
	        return 0;
	    }
	    PreparedStatement pst = null;
	    try {
	        cn.setAutoCommit(false);
	        String query = "UPDATE cuenta SET Saldo = ? WHERE NumeroCuenta_Cue = ?";
	        pst = cn.prepareStatement(query);
	        pst.setDouble(1, saldo);
	        pst.setInt(2, idCuenta);
	        
	        int filas = pst.executeUpdate();
	        if (filas > 0) {
	            cn.commit();
	            return 1;
	        } else {
	            cn.rollback();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.print("No se pudo conectar a la base");
	        try {
	            if (cn != null) {
	                cn.rollback();
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
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
	    return 0;
	}

	@Override
	public double obtenerSaldoCuenta(int idCuenta) {
		PreparedStatement ps = null;
	    ResultSet rs = null;
	    Connection conexion = null;
	    
	    try {
	        conexion = ConexionDB.getConexion().getSQLConexion();
	        ps = conexion.prepareStatement(OBTENER_SALDO);
	        ps.setInt(1, idCuenta);
	        rs = ps.executeQuery();
	        
	        if (rs.next()) {
	        	double saldo = rs.getDouble("Saldo");
	        	return saldo;
	        }
	        
	        return -1;
	        
	    } catch (SQLException ex) {
	    	System.out.println("Error sql");
	        ex.printStackTrace();
	        return -1;
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
	public boolean actualizarSaldo(Cuentas cuenta) {
        Connection conexion = ConexionDB.getConexion().getSQLConexion();
        String query = "UPDATE cuenta SET Saldo = ? WHERE NumeroCuenta_Cue = ?";
        PreparedStatement statement;

        try {
            statement = conexion.prepareStatement(query);
            statement.setDouble(1, cuenta.getSaldo());
            statement.setInt(2, cuenta.getNumeroCuenta_Cue());

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
}
