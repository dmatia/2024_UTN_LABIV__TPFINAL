package daoImplement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    public static ConexionDB instancia;
    private Connection connection;

    //arreglo de conexion:
    
    private ConexionDB() {
        try {
        	Class.forName("com.mysql.jdbc.Driver");
        	this.connection = DriverManager.getConnection(
        		    "jdbc:mysql://localhost:3306/dbbanco?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true", 
        		    "root", 
        		    "1234"
        		);
            this.connection.setAutoCommit(false);
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el controlador JDBC: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error de conexión con la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    public static ConexionDB getConexion() {
        if (instancia == null) {
            instancia = new ConexionDB();
        }
        return instancia;
    }

    public Connection getSQLConexion() {
    	 try {
             if (this.connection == null || this.connection.isClosed()) {
                 this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbbanco", "root", "1234");
                 this.connection.setAutoCommit(false);
             }
         } catch (SQLException e) {
             System.out.println("Error reabriendo la conexión: " + e.getMessage());
             e.printStackTrace();
         }
         return this.connection;
    }

    public void cerrarConexion() {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                this.connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        instancia = null;
    }
}
