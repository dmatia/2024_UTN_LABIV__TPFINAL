package daoImplement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import com.mysql.cj.jdbc.CallableStatement;
import java.sql.CallableStatement;
import daoImplement.ConexionDB;
import dao.iDaoUsuario;
import entidad.TipoUsuario;
import entidad.Usuario;

public class DaoUsuario implements iDaoUsuario{
	
	public static String OBTENER_X_CREDENCIALES = "SELECT u.UsuarioID_Usu, u.TipoUsuarioID_Usu, tu.tipoDeUsuarios, u.Usuario_Usu, u.Contrasena_Usu, u.Estado_Usu "
            + "FROM usuario u "
            + "JOIN tipoUsuario tu ON u.TipoUsuarioID_Usu = tu.TipoUsuarioID_TUsu "
            + "WHERE u.Usuario_Usu = ? AND u.Contrasena_Usu = ?";


	@Override
	public boolean modificar(Usuario usuarioModificado) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Usuario> leerTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Usuario> obtenerUno(int dni) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insertar(Usuario nuevoUsuario) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int validarUsuario(String usuario, String contrasenia) {

	    Connection cn = ConexionDB.getConexion().getSQLConexion();
	    // valido si la conexion no es nula
	    if (cn == null) {
	        System.out.println("no se pudo conectar a la base de datos");
	        return 0;
	    }
	    try {
	        CallableStatement cst = (CallableStatement) cn.prepareCall("CALL spLogeoUsuario(?, ?)");
	        cst.setString(1, usuario);
	        cst.setString(2, contrasenia);
	        ResultSet rs = cst.executeQuery(); 
	        if (rs.next()) { 
	        	// si encuentra coincidencia devuelve 1
	            return 1;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // si hay error de conexion a db
	        System.out.print("No se pudo conectar a la base");
	    }
	    // si no hay coincidencia devuelve 0
	    return 0;
	}


	@Override
	public Usuario obtenerUnoPorCredenciales(String usuario, String contrasenia) {
	    System.out.println("Estoy en obtenerUnoPorCredenciales!");
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    Connection conexion = null;
	    
	    try {
	        conexion = ConexionDB.getConexion().getSQLConexion();
	        ps = conexion.prepareStatement(OBTENER_X_CREDENCIALES);
	        ps.setString(1, usuario);
	        ps.setString(2, contrasenia);
	        rs = ps.executeQuery();
	        
	        if (rs.next()) {
	        	// armo el usuario
	            int usuarioID = rs.getInt("UsuarioID_Usu");
	            int tipoUsuario = rs.getInt("TipoUsuarioID_Usu");
	            String tipoUsuarioNombre =rs.getString("tipoDeUsuarios");
	            String usuarioNombre = rs.getString("Usuario_Usu");
	            String password = rs.getString("Contrasena_Usu");
	            boolean estado = rs.getBoolean("Estado_Usu");
	            TipoUsuario tu = new TipoUsuario();
	            tu.setId(tipoUsuario);
	            tu.setTipoUsuario(tipoUsuarioNombre);
	            
	            return new Usuario(usuarioID, tu, usuarioNombre, password, estado);
	        }
	        
	        return null;
	        
	    } catch (SQLException ex) {
	    	System.out.println("Error sql");
	        ex.printStackTrace();
	        return null;
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

	@Override
	public int cambiarContraUsuario(int usuario, String contrasenia) {
		Connection cn = ConexionDB.getConexion().getSQLConexion();
		if (cn == null) {
		    System.out.println("no se pudo conectar a la base de datos");
		    return 0;
		}
		CallableStatement cst = null;
		try {
		    cn.setAutoCommit(false);
		    cst = cn.prepareCall("CALL spModificarUsuario(?, ?)");
		    cst.setInt(1, usuario);
		    cst.setString(2, contrasenia);
		    
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
	public int bajaUsuario(int id) {
		Connection cn = ConexionDB.getConexion().getSQLConexion();
		if (cn == null) {
		    System.out.println("no se pudo conectar a la base de datos");
		    return 0;
		}
		CallableStatement cst = null;
		try {
		    cn.setAutoCommit(false);
		    cst = cn.prepareCall("CALL spBajaLogicaUsuario(?)");
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
	
	//PRUEBA:
	@Override
	public int modificarEstadoATrue(int idUsu) {
		Connection cn = ConexionDB.getConexion().getSQLConexion();
	    if (cn == null) {
	        System.out.println("No se pudo conectar a la base de datos");
	        return 0;
	    }
	    PreparedStatement pst = null;
	    try {
	        cn.setAutoCommit(false);
	        String query = "UPDATE usuario SET Estado_Usu = 1 WHERE UsuarioID_Usu = ?";
	        pst = cn.prepareStatement(query);
	        pst.setInt(1, idUsu);
	        
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
	
}
