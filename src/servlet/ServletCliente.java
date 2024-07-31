package servlet;


import java.util.List;

import javax.servlet.RequestDispatcher;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import daoImplement.DaoCliente;
import entidad.Cliente;
import entidad.Usuario;
import negocio.ClienteNegocio;
import negocio.UsuarioNegocio;
import negocioImplement.ClienteNegocioImplement;
import negocioImplement.UsuarioNegocioImplement;

/**
 * Servlet implementation class ServletCliente
 */
@WebServlet("/ServletCliente")
public class ServletCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCliente() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ClienteNegocio cdao = new ClienteNegocioImplement();
	    List<Cliente> lista = null;
	    
	    String busquedaCliente = request.getParameter("busquedaCliente");
        String btnBorrarFiltros = request.getParameter("btnBorrarFiltros");

        if (busquedaCliente != null && !busquedaCliente.trim().isEmpty()) {
            lista = cdao.buscarClientes(busquedaCliente);
        } else if(btnBorrarFiltros != null){
            lista = cdao.leerTodosListarClientes();
        } else {
            lista = cdao.leerTodosListarClientes();
        }
        
        //PRUEBA FILTRADO POR ESTADO DEL CLIENTE:
        String estadoCliente = request.getParameter("estadoCliente");
        if(estadoCliente != null) {
        	int estado = Integer.parseInt(estadoCliente);
        	lista = cdao.listadoClientesPorEstado(estado);
        }
        
	    String verCuentas = request.getParameter("VerCuentas");
	    String verMovimientos = request.getParameter("VerMovimientos");
	    String masInfo = request.getParameter("MasInfo");
	    String modContra = request.getParameter("ModContra");
	    String ModClient = request.getParameter("ModClient");
	    String IdCambiarEstado = request.getParameter("IdCambiarEstado");
	    String EstadoCliente = request.getParameter("EstadoCliente");
	    
	    if (verCuentas != null) {
	        int idCli = Integer.parseInt(verCuentas);
	        response.sendRedirect("ServletCuenta?idCliente=" + idCli);
	        return;
	        
	    } else if(IdCambiarEstado != null && EstadoCliente != null) {
	    	int idCli = Integer.parseInt(IdCambiarEstado);
	    	ClienteNegocio clienteDao = new ClienteNegocioImplement();
	    	UsuarioNegocio usuarioDao = new UsuarioNegocioImplement();
	    	
	    	if("true".equals(EstadoCliente)) {
	    		//dar de baja
	    		clienteDao.bajaCliente(idCli);
		    	Cliente cliente = new Cliente();
		    	cliente = clienteDao.obtenerUnCliente(idCli);
		    	int idUsu = cliente.getIdUsuario().getId();
		    	usuarioDao.bajaUsuario(idUsu);
	    	}else {
	    		//dar de alta
		    	clienteDao.modificarEstadoATrue(idCli);
		    	Cliente cliente = new Cliente();
		    	cliente = clienteDao.obtenerUnCliente(idCli);
		    	int idUsu = cliente.getIdUsuario().getId();
		    	usuarioDao.modificarEstadoATrue(idUsu);
	    	}
	    	response.sendRedirect(request.getContextPath() + "/ServletCliente?Param=1");
	    	return;
	    	
        }else if(ModClient != null) {
        	int idCli = Integer.parseInt(ModClient);
        	Cliente cliente = new Cliente();
        	ClienteNegocio clienteDao = new ClienteNegocioImplement();
        	cliente = clienteDao.obtenerUnCliente(idCli);
        	HttpSession Sesion = request.getSession();
			Sesion.setAttribute("clienteUsu", cliente);
			RequestDispatcher rDispatcher = request.getRequestDispatcher("/AdminModificarCliente.jsp");
			rDispatcher.forward(request, response);
			return;
        }else if(modContra != null) {
        	int idCli = Integer.parseInt(modContra);
        	Cliente cliente = new Cliente();
        	ClienteNegocio clienteDao = new ClienteNegocioImplement();
        	cliente = clienteDao.obtenerUnCliente(idCli);
        	HttpSession Sesion = request.getSession();
			Sesion.setAttribute("clienteUsu", cliente);
			RequestDispatcher rDispatcher = request.getRequestDispatcher("/ModificarContra.jsp");
			rDispatcher.forward(request, response);
			return;
        } else if (verMovimientos != null) {
	        int idCli = Integer.parseInt(verMovimientos);
	        response.sendRedirect("ServletMovimientos?idCliente=" + idCli);
	        return;
	    } else if (masInfo != null) {
	    	int idCli = Integer.parseInt(masInfo);
	        Cliente clienteDatos = cdao.obtenerUnClientePorID(idCli);
	        if (clienteDatos != null) {
	            request.setAttribute("cliente", clienteDatos);
	            RequestDispatcher rd = request.getRequestDispatcher("/AdminVerInfoCliente.jsp");
	            rd.forward(request, response);
	             return;
	         } else {
	            System.out.println("No se encontraron datos para el cliente con ID: " + idCli);
	         }
	    }
	    
	    request.setAttribute("listaC", lista);
	    RequestDispatcher rd = request.getRequestDispatcher("/ListadoClientes.jsp");
	    rd.forward(request, response);
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");


		if (request.getParameter("btnBuscarCliente") != null) {
			String parametroBusqueda = (request.getParameter("txtBusqueda"));
			if (parametroBusqueda != null && !parametroBusqueda.trim().isEmpty()) {
				ClienteNegocio clienteNegocio = new ClienteNegocioImplement();
				Cliente clienteEncontrado = clienteNegocio.buscarUnClientePorParametros(parametroBusqueda);
				if (clienteEncontrado != null) {
					request.setAttribute("ClienteEncontrado", clienteEncontrado);
					RequestDispatcher rd = request.getRequestDispatcher("/AdminAltaCuenta.jsp");
					rd.forward(request, response);
				}else {
					request.setAttribute("MensajeAltaCuenta", "Su busqueda no tuvo resultados. Intentelo nuevamente.");
					RequestDispatcher rd = request.getRequestDispatcher("/AdminAltaCuenta.jsp");
					rd.forward(request, response);
				}
			}else {
					request.setAttribute("MensajeAltaCuenta", "Ingrese un dato");
					RequestDispatcher rd = request.getRequestDispatcher("/AdminAltaCuenta.jsp");
					rd.forward(request, response);
				}
			}
		
		if (request.getParameter("alertMensaje") != null) {
			request.setAttribute("txtBusqueda", null);
			request.setAttribute("ClienteEncontrado", null);
			RequestDispatcher rd = request.getRequestDispatcher("/AdminAltaCuenta.jsp");
			rd.forward(request, response);
		}		
		

		if (request.getParameter("btnLimpiarBusqueda") != null) {
			request.setAttribute("txtBusqueda", null);
			request.setAttribute("ClienteEncontrado", null);
			RequestDispatcher rd = request.getRequestDispatcher("/AdminAltaCuenta.jsp");
			rd.forward(request, response);
		}
	}

}

