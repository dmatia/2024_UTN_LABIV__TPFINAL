package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidad.Cuentas;
import entidad.Usuario;
import negocio.CuentasNegocio;
import negocioImplement.CuentasNegocioImplement;

/**
 * Servlet implementation class ServletMenuCliente
 */
@WebServlet("/ServletMenuCliente")
public class ServletMenuCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletMenuCliente() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        System.out.println("Estoy en el do post de servlet menu cliente");
	        HttpSession session = request.getSession();
	        Usuario usuario = (Usuario) session.getAttribute("usuario");
	        Integer idCliente = (Integer) session.getAttribute("idCliente");
	        String actualizarCuentasParam = request.getParameter("actualizarCuentas");
	        if (usuario != null && idCliente != null || ("true".equals(actualizarCuentasParam)) ) {
	        	System.out.println("Estoy en cargar cuentas");
	            cargarCuentasUsuario(request, response, usuario, idCliente);
	        } else {

	            System.out.println("Usuario o IdCliente no encontrados en la sesi n.");
	        }
	    }

	    private void cargarCuentasUsuario(HttpServletRequest request, HttpServletResponse response,
	         Usuario usuario, int idCliente) throws ServletException, IOException {
	        HttpSession session = request.getSession();
	        CuentasNegocio cuentasNegocio = new CuentasNegocioImplement();
	        ArrayList<Cuentas> cuentasCliente = null;
	        cuentasCliente = cuentasNegocio.obtenerCuentasDeUnCliente(idCliente);
	        session.setAttribute("cuentasCliente", cuentasCliente);
	        RequestDispatcher miDispatcher = request.getRequestDispatcher("/MenuCliente.jsp");
	        miDispatcher.forward(request, response);
	    }
	}
