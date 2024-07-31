package daoImplement;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.iDaoCliente;
import entidad.Cliente;
import entidad.TipoUsuario;
import entidad.Usuario;

public class DaoCliente implements iDaoCliente{
	
	public static String OBTENER_X_ID = "SELECT c.ClienteID_Cli, c.UsuarioID_Cli, u.TipoUsuarioID_Usu, u.Usuario_Usu, u.Contrasena_Usu, u.Estado_Usu, c.DNI, c.CUIL, c.Nombre, c.Apellido, c.Sexo, c.Nacionalidad, c.FechaDeNacimiento, c.Direccion, c.Localidad, c.Provincia, c.CorreoElectronico, c.Telefono, c.Estado_Cli " 
			+ "FROM clientes c " 
			+ "JOIN usuario u ON u.UsuarioID_Usu = c.UsuarioID_Cli " 
			+ "WHERE c.ClienteID_Cli = ?";
	
	public static String OBTENER_X_IDUSU = "SELECT c.ClienteID_Cli "
			+ "FROM clientes c JOIN usuario u ON u.UsuarioID_Usu = c.UsuarioID_Cli "
			+ "WHERE u.UsuarioID_Usu = ?";
	
	public static String OBTENERUNO_X_BUSQUEDA = "SELECT * FROM clientes WHERE DNI like ? OR CUIL like ? OR CorreoElectronico like ?";
	
	
	//Prueba para volver a tener el estado en true:
	@Override
	public int modificarEstadoATrue(int idCli) {
		Connection cn = ConexionDB.getConexion().getSQLConexion();
	    if (cn == null) {
	        System.out.println("No se pudo conectar a la base de datos");
	        return 0;
	    }
	    PreparedStatement pst = null;
	    try {
	        cn.setAutoCommit(false);
	        String query = "UPDATE clientes SET Estado_Cli = 1 WHERE ClienteID_Cli = ?";
	        pst = cn.prepareStatement(query);
	        pst.setInt(1, idCli);
	        
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
		
	//PRUEBA PARA FILTRAR POR ESTADO:
	@Override
	public List<Cliente> listadoClientesPorEstado(int estado) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<Cliente> lista = new ArrayList<Cliente>();
		Connection cn = ConexionDB.getConexion().getSQLConexion();
		if (cn == null) {
	        System.out.println("No se pudo conectar a la base de datos");
	        return null;
	    }
		Statement st = null;
	    ResultSet rs = null;

	    try {
	        st = cn.createStatement();
	        String sql = "";
	        
	        switch (estado) {
	            case 0:
	                sql = "SELECT ClienteID_Cli, Nombre, Apellido, DNI, CorreoElectronico, Telefono, Estado_Cli FROM clientes";
	                break;
	            case 1:
	                sql = "SELECT ClienteID_Cli, Nombre, Apellido, DNI, CorreoElectronico, Telefono, Estado_Cli FROM clientes WHERE Estado_Cli=1";
	                break;
	            case 2:
	                sql = "SELECT ClienteID_Cli, Nombre, Apellido, DNI, CorreoElectronico, Telefono, Estado_Cli FROM clientes WHERE Estado_Cli=0";
	                break;
	            default:
	                System.out.println("Estado no válido");
	                return null;
	        }

	        rs = st.executeQuery(sql);
	        
	        while (rs.next()) {
	            Cliente clienteRs = new Cliente();
	            clienteRs.setIdCliente(rs.getInt("ClienteID_Cli"));
	            clienteRs.setNombre(rs.getString("Nombre"));
	            clienteRs.setApellido(rs.getString("Apellido"));
	            clienteRs.setDNI(rs.getString("DNI"));
	            clienteRs.setCorreoElectronico(rs.getString("CorreoElectronico"));
	            clienteRs.setTelefono(rs.getString("Telefono"));
	            clienteRs.setEstado_Cli(rs.getBoolean("Estado_Cli"));
	            lista.add(clienteRs);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (st != null) st.close();
	            if (cn != null && !cn.isClosed()) cn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return lista;
	
	// TODO Auto-generated method stub
	//return null;
	}
	
	@Override
	public List<Cliente> leerTodosListarClientes() {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ArrayList<Cliente> lista = new ArrayList<Cliente>();
			Connection cn = ConexionDB.getConexion().getSQLConexion();
			if (cn == null) {
		        System.out.println("No se pudo conectar a la base de datos");
		        return null;
		    }
			Statement st = null;
		    ResultSet rs = null;
			try { st = cn.createStatement();
			        rs = st.executeQuery("SELECT ClienteID_Cli, Nombre, Apellido, DNI, CorreoElectronico, Telefono, Estado_Cli FROM clientes");
			        
			        while (rs.next()) {
			            Cliente clienteRs = new Cliente();
			            clienteRs.setIdCliente(rs.getInt("ClienteID_Cli"));
			            //String nombreCompleto = rs.getString("Nombre").trim() + " " + rs.getString("Apellido").trim();
			            //clienteRs.setNombre(nombreCompleto);
			            clienteRs.setNombre(rs.getString("Nombre"));
			            clienteRs.setApellido(rs.getString("Apellido"));
			            clienteRs.setDNI(rs.getString("DNI"));
			            clienteRs.setCorreoElectronico(rs.getString("CorreoElectronico"));
			            clienteRs.setTelefono(rs.getString("Telefono"));
			            clienteRs.setEstado_Cli(rs.getBoolean("Estado_Cli"));
			            lista.add(clienteRs);
			        }
			    } catch (SQLException e) {
			        e.printStackTrace();
			    } finally {
			        try {
			            if (rs != null) rs.close();
			            if (st != null) st.close();
			            if (cn != null && !cn.isClosed()) cn.close();
			        } catch (SQLException e) {
			            e.printStackTrace();
			        }
			    }
			    return lista;
		
		// TODO Auto-generated method stub
		//return null;
	}
	
	public ArrayList<Cliente> buscarClientes(String busquedaCliente){ //Para el filtro de busqueda de la vista ListadoClientes.jsp
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<Cliente> lista = new ArrayList<Cliente>();
		Connection cn = ConexionDB.getConexion().getSQLConexion();
		if (cn == null) {
	        System.out.println("No se pudo conectar a la base de datos");
	        return null;
	    }
		PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT ClienteID_Cli, Nombre, Apellido, DNI, CorreoElectronico, Telefono " +
                     "FROM clientes " +
                     "WHERE (ClienteID_Cli LIKE ? OR Nombre LIKE ? OR Apellido LIKE ? OR DNI LIKE ?) AND Estado_Cli=1";
        
        try {
            ps = cn.prepareStatement(sql);
            String clienteABuscar = "%" + busquedaCliente + "%";
            ps.setString(1, clienteABuscar); //id cliente
            ps.setString(2, clienteABuscar); //nombre
            ps.setString(3, clienteABuscar); //apellido
            ps.setString(4, clienteABuscar); //dni
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("ClienteID_Cli"));
                cliente.setNombre(rs.getString("Nombre"));
                cliente.setApellido(rs.getString("Apellido"));
                cliente.setDNI(rs.getString("DNI"));
                cliente.setCorreoElectronico(rs.getString("CorreoElectronico"));
                cliente.setTelefono(rs.getString("Telefono"));
                lista.add(cliente);
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
	public int insertar(Cliente cliente, Usuario usuario) {
	    Connection cn = null;
	    CallableStatement cst = null;

	    try {
	        cn = ConexionDB.getConexion().getSQLConexion();
	        cn.setAutoCommit(false);
	        cst = cn.prepareCall("CALL spAgregarUsuarioYCliente(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	        cst.setInt(1, usuario.getTipoUsuario().getId());
	        cst.setString(2, usuario.getUsuario());
	        cst.setString(3, usuario.getContrasenia());
	        cst.setString(4, cliente.getDNI());
	        cst.setString(5, cliente.getCUIL());
	        cst.setString(6, cliente.getNombre());
	        cst.setString(7, cliente.getApellido());
	        cst.setString(8, cliente.getSexo());
	        cst.setString(9, cliente.getNacionalidad());
	        cst.setDate(10, cliente.getFechaNacimiento());
	        cst.setString(11, cliente.getDireccion());
	        cst.setString(12, cliente.getLocalidad());
	        cst.setString(13, cliente.getProvincia());
	        cst.setString(14, cliente.getCorreoElectronico());
	        cst.setString(15, cliente.getTelefono());
	        cst.registerOutParameter(16, java.sql.Types.INTEGER);

	        cst.execute();
	        int resultado = cst.getInt(16);

	        cn.commit();

	        return resultado;
	    } catch (SQLException e) {
	        if (cn != null) {
	            try {
	                cn.rollback();
	            } catch (SQLException rollbackEx) {
	                rollbackEx.printStackTrace();
	            }
	        }
	        e.printStackTrace();
	        return 0;
	    } finally {
	        try {
	            if (cst != null) cst.close();
	            if (cn != null) cn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	@Override
	public Cliente obtenerUnClientePorID(int idCli) {
		
		PreparedStatement ps = null;
	    ResultSet rs = null;
	    Connection conexion = null;
	    
	    String sql = "SELECT c.ClienteID_Cli, c.UsuarioID_Cli, c.DNI, c.CUIL, c.Nombre, c.Apellido, c.Sexo, " +
                "c.Nacionalidad, c.FechaDeNacimiento, c.Direccion, c.Localidad, c.Provincia, " +
                "c.CorreoElectronico, c.Telefono " +
                "FROM clientes c " +
                "WHERE c.ClienteID_Cli = ?";
   
   try {
       conexion = ConexionDB.getConexion().getSQLConexion();
       ps = conexion.prepareStatement(sql);
       ps.setInt(1, idCli);
       rs = ps.executeQuery();
       
       if (rs.next()) {
           int idCliente = rs.getInt("ClienteID_Cli");
           int idUsuario = rs.getInt("UsuarioID_Cli");
           String DNI = rs.getString("DNI");
           String CUIL = rs.getString("CUIL");
           String nombre = rs.getString("Nombre");
           String apellido = rs.getString("Apellido");
           String sexo = rs.getString("Sexo");
           String nacionalidad = rs.getString("Nacionalidad");
           Date fechaNacimiento = rs.getDate("FechaDeNacimiento");
           String direccion = rs.getString("Direccion");
           String localidad = rs.getString("Localidad");
           String provincia = rs.getString("Provincia");
           String correoElectronico = rs.getString("CorreoElectronico");
           String telefono = rs.getString("Telefono");
           
           Usuario usuCli = new Usuario();
           usuCli.setId(idUsuario);
           
           return new Cliente(usuCli, idCliente, DNI, CUIL, nombre, apellido, sexo, nacionalidad, fechaNacimiento,
                              direccion, localidad, provincia, correoElectronico, telefono,true);
       }
	    } catch (SQLException ex) {
	        System.out.println("Error SQL al obtener cliente: " + ex.getMessage());
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
		return null;
	}
	
	@Override
	public Cliente obtenerUnCliente(int id) {
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    Connection conexion = null;
	    
	    try {
	        conexion = ConexionDB.getConexion().getSQLConexion();
	        ps = conexion.prepareStatement(OBTENER_X_ID);
	        ps.setInt(1, id);
	        rs = ps.executeQuery();
	        
	        if (rs.next()) {
	        	int idCliente = rs.getInt("ClienteID_Cli");
	        	int idUsuario = rs.getInt("UsuarioID_Cli");
	        	int tipoUsuario = rs.getInt("TipoUsuarioID_Usu");
	            String usuarioNombre = rs.getString("Usuario_Usu");
	            String password = rs.getString("Contrasena_Usu");
	            boolean estado_Usu = rs.getBoolean("Estado_Usu");
	        	String DNI = rs.getString("DNI");
	        	String CUIL = rs.getString("CUIL");
	        	String nombre = rs.getString("Nombre");
	        	String apellido = rs.getString("Apellido");
	        	String sexo = rs.getString("Sexo");
	        	String nacionalidad = rs.getString("Nacionalidad");
	        	Date fechaNacimiento = rs.getDate("FechaDeNacimiento");
	        	String direccion = rs.getString("Direccion");
	        	String localidad = rs.getString("Localidad");
	        	String provincia = rs.getString("Provincia");
	        	String correoElectronico = rs.getString("CorreoElectronico");
	        	String telefono = rs.getString("Telefono");
	        	boolean estado_Cli = rs.getBoolean("Estado_Cli");
	        	
	        	TipoUsuario tu = new TipoUsuario();
	            tu.setId(tipoUsuario);
	            tu.setTipoUsuario("Cliente");
	            
	            Usuario usuCli = new Usuario(idUsuario,tu,usuarioNombre,password,estado_Usu);
	            
	            return new Cliente(usuCli,idCliente,DNI,CUIL,nombre,apellido,sexo,nacionalidad,fechaNacimiento,direccion,localidad,provincia,correoElectronico,telefono,estado_Cli);
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
	public int bajaCliente(int id) {
		Connection cn = ConexionDB.getConexion().getSQLConexion();
		if (cn == null) {
		    System.out.println("no se pudo conectar a la base de datos");
		    return 0;
		}
		CallableStatement cst = null;
		try {
		    cn.setAutoCommit(false);
		    cst = cn.prepareCall("CALL spBajaLogicaCliente(?)");
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
	public int obtenerUnClientePorUsuario(Usuario usu) {
		PreparedStatement ps = null;
	    ResultSet rs = null;
	    Connection conexion = null;
	    int id = usu.getId();
	    
	    try {
	        conexion = ConexionDB.getConexion().getSQLConexion();
	        ps = conexion.prepareStatement(OBTENER_X_IDUSU);
	        ps.setInt(1, id);
	        rs = ps.executeQuery();
	        
	        if (rs.next()) {
	        	int idCliente = rs.getInt("ClienteID_Cli");
	            
	            return idCliente;
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



	public Cliente obtenerUnClientePorParametros(String busquedaCliente) {
	    Connection cn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    Cliente cliente = null;

	    try {
	        Class.forName("com.mysql.jdbc.Driver");
	        cn = ConexionDB.getConexion().getSQLConexion();

	        if (cn == null) {
	            System.out.println("No se pudo conectar a la base de datos desde obtenerUnClientePorParametros - Dao cliente");
	            return null;
	        }

	        ps = cn.prepareStatement(OBTENERUNO_X_BUSQUEDA);
	        ps.setString(1, busquedaCliente);
	        ps.setString(2, busquedaCliente);
	        ps.setString(3, busquedaCliente);


	        rs = ps.executeQuery();
	        if (rs.next()) {
	            cliente = new Cliente();
	            cliente.setIdCliente(rs.getInt("ClienteID_Cli"));
	            cliente.setNombre(rs.getString("Nombre"));
	            cliente.setApellido(rs.getString("Apellido"));
	            cliente.setDNI(rs.getString("DNI"));
	            cliente.setCorreoElectronico(rs.getString("CorreoElectronico"));
	            cliente.setTelefono(rs.getString("Telefono"));
	            cliente.setFechaNacimiento(rs.getDate("FechaDeNacimiento"));
	        }
	        else {
	        	return null;
	        }
	    } catch (ClassNotFoundException e) {
	        System.out.println(e);
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

	    return cliente;
	}

	@Override
	public boolean actualizarCliente(Cliente cliente) {
		Connection conexion = ConexionDB.getConexion().getSQLConexion();
        String query = "UPDATE clientes SET DNI = ?, CUIL = ?, Nombre = ?, Apellido = ?, Sexo = ?, Nacionalidad = ?, FechaDeNacimiento = ?, Direccion = ?, Localidad = ?, Provincia = ?, CorreoElectronico = ?, Telefono = ? WHERE ClienteID_Cli = ?";
        PreparedStatement statement;

        try {
            statement = conexion.prepareStatement(query);
            statement.setString(1, cliente.getDNI());
            statement.setString(2, cliente.getCUIL());
            statement.setString(3, cliente.getNombre());
            statement.setString(4, cliente.getApellido());
            statement.setString(5, cliente.getSexo());
            statement.setString(6, cliente.getNacionalidad());
            statement.setDate(7, cliente.getFechaNacimiento());
            statement.setString(8, cliente.getDireccion());
            statement.setString(9, cliente.getLocalidad());
            statement.setString(10, cliente.getProvincia());
            statement.setString(11, cliente.getCorreoElectronico());
            statement.setString(12, cliente.getTelefono());
            statement.setInt(13, cliente.getIdCliente());

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
