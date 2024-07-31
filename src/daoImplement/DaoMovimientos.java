package daoImplement;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.iDaoMovimientos;
import entidad.Movimientos;
import entidad.TipoMovimientos;

public class DaoMovimientos implements iDaoMovimientos {

	@Override
	public List<Movimientos> leerTodosClienteImporteMayorOIgual(int idCliente, double importe) {
	    try {
	        Class.forName("com.mysql.jdbc.Driver");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    ArrayList<Movimientos> lista = new ArrayList<>();
	    Connection cn = ConexionDB.getConexion().getSQLConexion();
	    if (cn == null) {
	        System.out.println("No se pudo conectar a la base de datos");
//	        return null;
	    }

	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    String sql = "SELECT m.MovimientosID_Mov, " +
	                 "m.NumeroCuenta_Mov, " +
	                 "m.TipoMovimiento_Mov, " +
	                 "m.Fecha_Mov, " +
	                 "m.CBU_Mov, " +
	                 "m.Detalle_Mov, " +
	                 "m.Importe_Mov " +
	                 "FROM movimientos m " +
	                 "JOIN cuenta c ON m.NumeroCuenta_Mov = c.NumeroCuenta_Cue " +
	                 "WHERE c.ClienteID_Cue = ? AND m.Importe_Mov >= ?";

	    try {
	        ps = cn.prepareStatement(sql);
	        ps.setInt(1, idCliente);
	        ps.setDouble(2, importe);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            Movimientos movimiento = new Movimientos();
	            movimiento.setMovimientosID(rs.getInt("MovimientosID_Mov"));
	            movimiento.setNumeroCuenta(rs.getInt("NumeroCuenta_Mov"));

	            int tipoMovId = rs.getInt("TipoMovimiento_Mov");
	            TipoMovimientos tipoMovimiento = obtenerTipoMovimientoPorId(tipoMovId, cn);

	            movimiento.setTipoMovimientos(tipoMovimiento);

	            movimiento.setFechaMov(rs.getDate("Fecha_Mov"));
	            movimiento.setCBUMov(rs.getString("CBU_Mov"));
	            movimiento.setDetalleMov(rs.getString("Detalle_Mov"));
	            movimiento.setImporte_Mov(rs.getDouble("Importe_Mov"));

	            lista.add(movimiento);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (cn != null && !cn.isClosed()) cn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return lista;
	}

	@Override
	public List<Movimientos> leerTodosAdminImporteMayorOIgual(double importe) {
		 try {
		        Class.forName("com.mysql.jdbc.Driver");
		    } catch (ClassNotFoundException e) {
		        e.printStackTrace();
		    }

		    ArrayList<Movimientos> lista = new ArrayList<>();
		    Connection cn = ConexionDB.getConexion().getSQLConexion();
		    if (cn == null) {
		        System.out.println("No se pudo conectar a la base de datos");
		        return null;
		    }

		    PreparedStatement ps = null;
		    ResultSet rs = null;
		    String sql =  "SELECT m.MovimientosID_Mov, " + 
		             "m.NumeroCuenta_Mov, " + 
		             "m.TipoMovimiento_Mov, " + 
		             "m.Fecha_Mov, " + 
		             "m.CBU_Mov, " + 
		             "m.Detalle_Mov, " + 
		             "m.Importe_Mov " + 
		             "FROM movimientos m " + 
		             "WHERE m.Importe_Mov >= ?";

		    try {
		        ps = cn.prepareStatement(sql);
		        ps.setDouble(1, importe);
		        rs = ps.executeQuery();

		        while (rs.next()) {
		            Movimientos movimiento = new Movimientos();
		            movimiento.setMovimientosID(rs.getInt("MovimientosID_Mov"));
		            movimiento.setNumeroCuenta(rs.getInt("NumeroCuenta_Mov"));

		            int tipoMovId = rs.getInt("TipoMovimiento_Mov");
		            TipoMovimientos tipoMovimiento = obtenerTipoMovimientoPorId(tipoMovId, cn);

		            movimiento.setTipoMovimientos(tipoMovimiento);

		            movimiento.setFechaMov(rs.getDate("Fecha_Mov"));
		            movimiento.setCBUMov(rs.getString("CBU_Mov"));
		            movimiento.setDetalleMov(rs.getString("Detalle_Mov"));
		            movimiento.setImporte_Mov(rs.getDouble("Importe_Mov"));

		            lista.add(movimiento);
		        }

		        System.out.println("Movimientos encontrados: " + lista.size());
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (rs != null) rs.close();
		            if (ps != null) ps.close();
		            if (cn != null && !cn.isClosed()) cn.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		    return lista;
	}

	public static String OBTENER_X_NROCUENTA = "SELECT MovimientosID_Mov, NumeroCuenta_Mov, TipoMovimiento_Mov, tm.TipoMovimientos, Fecha_Mov, CBU_Mov, Detalle_Mov, Importe_Mov \r\n"
			+ "FROM movimientos mo left join tipomovimientos tm on mo.TipoMovimiento_Mov = tm.TipoMovimientosID_TMov\r\n"
			+ "right join cuenta cu on mo.NumeroCuenta_Mov = cu.NumeroCuenta_Cue where cu.NumeroCuenta_Cue =  ?";
	public static String INSERTAR = "INSERT INTO movimientos (MovimientosID_Mov, NumeroCuenta_Mov, TipoMovimiento_Mov, Fecha_Mov, CBU_Mov, Detalle_Mov, Importe_Mov) VALUES (?, ?, ?, ?, ?, ?, ?)";
	public static String OBTENER_ID = "SELECT MovimientosID_Mov FROM movimientos ORDER BY MovimientosID_Mov DESC LIMIT 1";
	
	@Override
	public List<Movimientos> leerTodos() {
	    try {
	        Class.forName("com.mysql.jdbc.Driver");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    ArrayList<Movimientos> lista = new ArrayList<>();
	    Connection cn = ConexionDB.getConexion().getSQLConexion();
	    if (cn == null) {
	        System.out.println("No se pudo conectar a la base de datos");
	        return null;
	    }

	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    String sql = "SELECT m.MovimientosID_Mov, " + 
	                 "m.NumeroCuenta_Mov, " + 
	                 "m.TipoMovimiento_Mov, " + 
	                 "m.Fecha_Mov, " + 
	                 "m.CBU_Mov, " + 
	                 "m.Detalle_Mov, " + 
	                 "m.Importe_Mov " + 
	                 "FROM movimientos m";

	    try {
	        ps = cn.prepareStatement(sql);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            Movimientos movimiento = new Movimientos();
	            movimiento.setMovimientosID(rs.getInt("MovimientosID_Mov"));
	            movimiento.setNumeroCuenta(rs.getInt("NumeroCuenta_Mov"));

	            int tipoMovId = rs.getInt("TipoMovimiento_Mov");
	            TipoMovimientos tipoMovimiento = obtenerTipoMovimientoPorId(tipoMovId, cn);

	            movimiento.setTipoMovimientos(tipoMovimiento);

	            movimiento.setFechaMov(rs.getDate("Fecha_Mov"));
	            movimiento.setCBUMov(rs.getString("CBU_Mov"));
	            movimiento.setDetalleMov(rs.getString("Detalle_Mov"));
	            movimiento.setImporte_Mov(rs.getDouble("Importe_Mov"));

	            lista.add(movimiento);
	        }

	        System.out.println("Movimientos encontrados: " + lista.size());
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (cn != null && !cn.isClosed()) cn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return lista;
	}

	@Override
	public ArrayList<Movimientos> obtenerMovimientosDeUnCliente(int id) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		ArrayList<Movimientos> lista = new ArrayList<>();
		Connection cn = ConexionDB.getConexion().getSQLConexion();
		if (cn == null) {
			System.out.println("No se pudo conectar a la base de datos");
			return null;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT m.MovimientosID_Mov, " + "m.NumeroCuenta_Mov, " + "m.TipoMovimiento_Mov, "
				+ "m.Fecha_Mov, " + "m.CBU_Mov, " + "m.Detalle_Mov, " + "m.Importe_Mov " + "FROM movimientos m "
				+ "JOIN cuenta c ON m.NumeroCuenta_Mov = c.NumeroCuenta_Cue " + "WHERE c.ClienteID_Cue = ?";

		try {
			ps = cn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			while (rs.next()) {
				Movimientos movimiento = new Movimientos();
				movimiento.setMovimientosID(rs.getInt("MovimientosID_Mov"));
				movimiento.setNumeroCuenta(rs.getInt("NumeroCuenta_Mov"));

				int tipoMovId = rs.getInt("TipoMovimiento_Mov");
				TipoMovimientos tipoMovimiento = obtenerTipoMovimientoPorId(tipoMovId, cn);

				movimiento.setTipoMovimientos(tipoMovimiento);

				movimiento.setFechaMov(rs.getDate("Fecha_Mov"));
				movimiento.setCBUMov(rs.getString("CBU_Mov"));
				movimiento.setDetalleMov(rs.getString("Detalle_Mov"));
				movimiento.setImporte_Mov(rs.getDouble("Importe_Mov"));

				lista.add(movimiento);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (cn != null && !cn.isClosed())
					cn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return lista;
	}

	private TipoMovimientos obtenerTipoMovimientoPorId(int id, Connection cn) {
		TipoMovimientos tipoMovimiento = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT TipoMovimientosID_TMov, TipoMovimientos FROM tipoMovimientos WHERE TipoMovimientosID_TMov = ?";

		try {
			ps = cn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {

				tipoMovimiento = new TipoMovimientos();
				tipoMovimiento.setTipoMovimientosID(rs.getInt("TipoMovimientosID_TMov"));
				tipoMovimiento.setTipoMovimientos(rs.getString("TipoMovimientos"));

			}
		} catch (SQLException e) {
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
		return tipoMovimiento;
	}

	public ArrayList<Movimientos> obtenerTodosMovimientosPorTipo(int tipoMovimiento) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		ArrayList<Movimientos> lista = new ArrayList<>();
		Connection cn = ConexionDB.getConexion().getSQLConexion();
		if (cn == null) {
			System.out.println("No se pudo conectar a la base de datos");
			return null;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT m.MovimientosID_Mov, m.NumeroCuenta_Mov, m.TipoMovimiento_Mov, "
				+ "m.Fecha_Mov, m.CBU_Mov, m.Detalle_Mov, m.Importe_Mov " + "FROM movimientos m "
				+ "WHERE m.TipoMovimiento_Mov = ?";

		try {
			ps = cn.prepareStatement(sql);
			ps.setInt(1, tipoMovimiento);
			rs = ps.executeQuery();

			while (rs.next()) {
				Movimientos movimiento = new Movimientos();
				movimiento.setMovimientosID(rs.getInt("MovimientosID_Mov"));
				movimiento.setNumeroCuenta(rs.getInt("NumeroCuenta_Mov"));

				int tipoMovId = rs.getInt("TipoMovimiento_Mov");
				TipoMovimientos tipoMovimient = obtenerTipoMovimientoPorId(tipoMovId, cn);

				movimiento.setTipoMovimientos(tipoMovimient);

				movimiento.setFechaMov(rs.getDate("Fecha_Mov"));
				movimiento.setCBUMov(rs.getString("CBU_Mov"));
				movimiento.setDetalleMov(rs.getString("Detalle_Mov"));
				movimiento.setImporte_Mov(rs.getDouble("Importe_Mov"));

				lista.add(movimiento);
			}
		} catch (SQLException e) {
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

		return lista;
	}
	
	
	public ArrayList<Movimientos> obtenerMovimientosPorTipo(int idCliente, int tipoMovimiento) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		ArrayList<Movimientos> lista = new ArrayList<>();
		Connection cn = ConexionDB.getConexion().getSQLConexion();
		if (cn == null) {
			System.out.println("No se pudo conectar a la base de datos");
			return null;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT m.MovimientosID_Mov, m.NumeroCuenta_Mov, m.TipoMovimiento_Mov, "
				+ "m.Fecha_Mov, m.CBU_Mov, m.Detalle_Mov, m.Importe_Mov " + "FROM movimientos m "
				+ "JOIN cuenta c ON m.NumeroCuenta_Mov = c.NumeroCuenta_Cue "
				+ "WHERE c.ClienteID_Cue = ? AND m.TipoMovimiento_Mov = ?";

		try {
			ps = cn.prepareStatement(sql);
			ps.setInt(1, idCliente);
			ps.setInt(2, tipoMovimiento);
			rs = ps.executeQuery();

			while (rs.next()) {
				Movimientos movimiento = new Movimientos();
				movimiento.setMovimientosID(rs.getInt("MovimientosID_Mov"));
				movimiento.setNumeroCuenta(rs.getInt("NumeroCuenta_Mov"));

				int tipoMovId = rs.getInt("TipoMovimiento_Mov");
				TipoMovimientos tipoMovimient = obtenerTipoMovimientoPorId(tipoMovId, cn);

				movimiento.setTipoMovimientos(tipoMovimient);

				movimiento.setFechaMov(rs.getDate("Fecha_Mov"));
				movimiento.setCBUMov(rs.getString("CBU_Mov"));
				movimiento.setDetalleMov(rs.getString("Detalle_Mov"));
				movimiento.setImporte_Mov(rs.getDouble("Importe_Mov"));

				lista.add(movimiento);
			}
		} catch (SQLException e) {
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

		return lista;
	}

	@Override
	public ArrayList<Movimientos> obtenerMovimientosPorCuentaId(int numeroCuenta_Cue) {
	    System.out.println("Estoy en obtenerMovimientosPorCuentaId - DAO MOVIMIENTOS");
	    Connection conexion = ConexionDB.getConexion().getSQLConexion();
	    if (conexion == null) {
	        System.err.println("La conexi n a la base de datos es nula.");
	        return null;
	    }
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    ArrayList<Movimientos> listaMovimientos = new ArrayList<>();
	    try {
	        ps = conexion.prepareStatement(OBTENER_X_NROCUENTA);
	        ps.setInt(1, numeroCuenta_Cue);
	        rs = ps.executeQuery();
	        while (rs.next()) {
	            Movimientos movimiento = new Movimientos();
	            TipoMovimientos tipoMovimiento = new TipoMovimientos();
	            movimiento.setMovimientosID(rs.getInt("MovimientosID_Mov"));
	            movimiento.setNumeroCuenta(rs.getInt("NumeroCuenta_Mov"));
	            tipoMovimiento.setTipoMovimientosID(rs.getInt("TipoMovimiento_Mov"));
	            tipoMovimiento.setTipoMovimientos(rs.getString("TipoMovimientos"));
	            movimiento.setTipoMovimientos(tipoMovimiento);
	            movimiento.setFechaMov(rs.getDate("Fecha_Mov"));
	            movimiento.setCBUMov(rs.getString("CBU_Mov"));
	            movimiento.setDetalleMov(rs.getString("Detalle_Mov"));
	            movimiento.setImporte_Mov(rs.getDouble("Importe_Mov"));
	            listaMovimientos.add(movimiento);
	            movimiento.toString();
	        }
	    } catch (SQLException e) {
	    	System.out.println(e);
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (conexion != null && !conexion.isClosed()) conexion.close();
	        } catch (SQLException e) {
	        	System.out.println(e);
	        	e.printStackTrace();
	        }
	    }
	    return listaMovimientos;
	}

	public boolean agregarMovimiento(Movimientos nuevoMovimiento) {
	    Connection cn = ConexionDB.getConexion().getSQLConexion();
	    if (cn == null) {
	        System.out.println("No se pudo conectar a la base de datos en agregar movimiento - DAO MOVIMIENTOS");
	        return false;
	    }
	    try {
	    	
	    	System.out.println(nuevoMovimiento.toString());
	    	
	        cn.setAutoCommit(false);
	        CallableStatement cst = cn.prepareCall("CALL spAgregarMovimientoTipo123(?, ?, ?, ?, ?, ?)");
	        cst.setInt(1, nuevoMovimiento.getNumeroCuenta());
	        cst.setInt(2, nuevoMovimiento.getTipoMovimientos().getTipoMovimientosID());
	        cst.setDate(3, nuevoMovimiento.getFechaMov());
	        cst.setString(4, nuevoMovimiento.getCBUMov());
	        cst.setString(5, nuevoMovimiento.getDetalleMov());
	        cst.setBigDecimal(6, BigDecimal.valueOf(nuevoMovimiento.getImporte_Mov()));

	        int affectedRows = cst.executeUpdate();

	        if (affectedRows > 0) {
	            cn.commit();
	            return true;
	        } else {
	            System.out.println("La operacion de agregar Movimiento fallo. - DAO MOVIMIENTOS");
	            cn.rollback();
	        }
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	        try {
	            if (cn != null) {
	                cn.rollback();
	            }
	        } catch (SQLException rollbackEx) {
	            rollbackEx.printStackTrace();
	        }
	        e.printStackTrace();
	        System.out.print("No se pudo conectar a la base desde agregarMov MOVIMIENTOS DAO");
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
	    return false;
	}


	@Override
	public int agregarMovimientoTransferencia(Movimientos nuevoMovimiento) {
		PreparedStatement ps = null;
	    Connection conexion = null;

	    try {
	        conexion = ConexionDB.getConexion().getSQLConexion();
	        conexion.setAutoCommit(false);

	        ps = conexion.prepareStatement(INSERTAR);
	        ps.setInt(1, nuevoMovimiento.getMovimientosID());
	        ps.setInt(2, nuevoMovimiento.getNumeroCuenta());
	        ps.setInt(3, nuevoMovimiento.getTipoMovimientos().getTipoMovimientosID());
	        ps.setDate(4, nuevoMovimiento.getFechaMov());
	        ps.setString(5, nuevoMovimiento.getCBUMov());
	        ps.setString(6, nuevoMovimiento.getDetalleMov());
	        ps.setDouble(7, nuevoMovimiento.getImporte_Mov());

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
	        	int idMovimiento = rs.getInt("MovimientosID_Mov");
	        	return idMovimiento + 1;
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

}
