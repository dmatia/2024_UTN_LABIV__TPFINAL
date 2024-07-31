package negocioImplement;

import java.util.ArrayList;
import java.util.List;

import dao.iDaoCliente;
import daoImplement.DaoCliente;
import entidad.Cliente;
import entidad.Usuario;
import negocio.ClienteNegocio;

public class ClienteNegocioImplement implements ClienteNegocio {
    
	private iDaoCliente daoCliente;

    public ClienteNegocioImplement() {
        this.daoCliente = new DaoCliente();
    }

	@Override
	public List<Cliente> leerTodosListarClientes() {
		return daoCliente.leerTodosListarClientes();
	}
	
	
	@Override
	public List<Cliente> listadoClientesPorEstado(int estado) {
    	return daoCliente.listadoClientesPorEstado(estado);
	}
	
	public ArrayList<Cliente> buscarClientes(String busquedaCliente){
		return daoCliente.buscarClientes(busquedaCliente);
	}
	
	@Override
	public int insertar(Cliente cliente, Usuario usuario){
		return daoCliente.insertar(cliente, usuario);
	}

	public Cliente obtenerUnClientePorID(int idCli) {
		return daoCliente.obtenerUnClientePorID(idCli);
	}
	
	@Override
	public Cliente obtenerUnCliente(int id) {
		return daoCliente.obtenerUnCliente(id);
	}

	@Override
	public int bajaCliente(int id) {
		return daoCliente.bajaCliente(id);
	}
	
	@Override
	public int modificarEstadoATrue(int idCli) {
		return daoCliente.modificarEstadoATrue(idCli);
	}

	@Override
	public int obtenerUnClientePorUsuario(Usuario usu) {
		return daoCliente.obtenerUnClientePorUsuario(usu);
	}
	
	@Override
	public Cliente buscarUnClientePorParametros(String busquedaCliente) {
		return daoCliente.obtenerUnClientePorParametros(busquedaCliente);
	}

	@Override
	public boolean actualizarCliente(Cliente cliente) {
		return daoCliente.actualizarCliente(cliente);
	}
}

