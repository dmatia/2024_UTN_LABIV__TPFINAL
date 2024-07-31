package negocio;

import java.util.ArrayList;
import java.util.List;

import entidad.Cliente;
import entidad.Usuario;

public interface ClienteNegocio {
	
		public List<Cliente> leerTodosListarClientes();
		
		public List<Cliente> listadoClientesPorEstado(int estado);
		
		public ArrayList<Cliente> buscarClientes(String busquedaCliente);
		public int insertar(Cliente cliente, Usuario usuario);
		public Cliente obtenerUnClientePorID(int idCli);
		public Cliente obtenerUnCliente(int id);
		public int obtenerUnClientePorUsuario(Usuario usu);
		public int bajaCliente(int id);
		public Cliente buscarUnClientePorParametros(String busquedaCliente);
		public int modificarEstadoATrue(int idCli);
		public boolean actualizarCliente(Cliente cliente);
}