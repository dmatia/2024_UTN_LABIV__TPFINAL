package negocioImplement;

import java.util.ArrayList;
import java.util.List;

import dao.iDaoUsuario;
import daoImplement.DaoUsuario;
import entidad.Usuario;
import negocio.UsuarioNegocio;

public class UsuarioNegocioImplement implements UsuarioNegocio {
    
	private iDaoUsuario daoUsuario;

    public UsuarioNegocioImplement() {
        this.daoUsuario = new DaoUsuario();
    }
    
    @Override
    public boolean modificar(Usuario usuarioModificado) {
        return daoUsuario.modificar(usuarioModificado);
    }

    @Override
    public List<Usuario> leerTodos() {
        return daoUsuario.leerTodos();
    }

    @Override
    public ArrayList<Usuario> obtenerUno(int id) {
        return daoUsuario.obtenerUno(id);
    }

    @Override
    public boolean insertar(Usuario nuevoUsuario) {
        return daoUsuario.insertar(nuevoUsuario);
    }

    @Override
    public int validarUsuario(String usuario, String contrasenia) {
        return daoUsuario.validarUsuario(usuario, contrasenia);
    }

    @Override
    public Usuario obtenerUnoPorCredenciales(String usuario, String contrasenia) {
        return daoUsuario.obtenerUnoPorCredenciales(usuario, contrasenia);
    }

	@Override
	public int cambiarContraUsuario(int usuario, String contrasenia) {
		return daoUsuario.cambiarContraUsuario(usuario, contrasenia);
	}

	@Override
	public int bajaUsuario(int id) {
		return daoUsuario.bajaUsuario(id);
	}
	
	 @Override
	public int modificarEstadoATrue(int idUsu) {
		 return daoUsuario.modificarEstadoATrue(idUsu);
	}
	
}
