package daoImplement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.iDaoIntereses;
import entidad.Intereses;

public class DaoIntereses implements iDaoIntereses{
	
	public static String OBTENER_X_ID = "SELECT InteresesID_Int, Cuotas_Int, Porcentaje_Int FROM intereses WHERE InteresesID_Int = ?";

	@Override
	public Intereses obternerInteres(int id) {
		PreparedStatement ps = null;
	    ResultSet rs = null;
	    Connection conexion = null;
	    
	    try {
	        conexion = ConexionDB.getConexion().getSQLConexion();
	        ps = conexion.prepareStatement(OBTENER_X_ID);
	        ps.setInt(1, id);
	        rs = ps.executeQuery();
	        
	        if (rs.next()) {
	        	int idInteres = rs.getInt("InteresesID_Int");
	        	int cuotaInteres = rs.getInt("Cuotas_Int");
	        	double porcentaje = rs.getInt("Porcentaje_Int");
	        	
	        	return new Intereses(idInteres,cuotaInteres,porcentaje);
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

}
