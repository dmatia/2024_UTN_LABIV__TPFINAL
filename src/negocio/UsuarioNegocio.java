package negocio;
import java.util.ArrayList;
import java.util.List;

import entidad.Usuario;

public interface UsuarioNegocio {
	public boolean modificar(Usuario usuarioModificado);
	public List<Usuario> leerTodos();
	public ArrayList<Usuario> obtenerUno(int id);
	public Usuario obtenerUnoPorCredenciales(String usuario, String contrasenia);
	public boolean insertar(Usuario nuevoUsuario);
	public int validarUsuario(String usuario, String contrasenia);
	public int cambiarContraUsuario(int usuario, String contrasenia);
	public int bajaUsuario(int id);
	
	//PRUEBA SETEAR ESTADO EN TRUE
	public int modificarEstadoATrue(int idUsu);
}


