package dao;

import java.util.ArrayList;
import java.util.List;

import entidad.Cliente;
import entidad.Usuario;

public interface iDaoCliente {

    public List<Cliente> leerTodosListarClientes();
    //PRUEBA PARA FILTRAR POR ESTADO:
    public List<Cliente> listadoClientesPorEstado(int estado);
    
    public ArrayList<Cliente> buscarClientes(String busquedaCliente);
    public int insertar(Cliente cliente, Usuario usuario);
    public Cliente obtenerUnClientePorID(int idCli);
    public Cliente obtenerUnCliente(int id);
    public int obtenerUnClientePorUsuario(Usuario usu);
    public int bajaCliente(int id);
    public Cliente obtenerUnClientePorParametros(String busquedaCliente);
    public boolean actualizarCliente(Cliente cliente);
    
    //PRUEBA PARA DAR DE SETEAR ESTADO EN TRUE
    public int modificarEstadoATrue(int idCli);
}