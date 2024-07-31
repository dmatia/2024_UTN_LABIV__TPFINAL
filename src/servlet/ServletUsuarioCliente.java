package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidad.Cliente;
import entidad.Usuario;
import negocio.ClienteNegocio;
import negocioImplement.ClienteNegocioImplement;

/**
 * Servlet implementation class ServletUsuarioCliente
 */
@WebServlet("/ServletUsuarioCliente")
public class ServletUsuarioCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletUsuarioCliente() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sesion = request.getSession();
		Usuario usuario = (Usuario) sesion.getAttribute("usuario");
		
		if(usuario != null) {
			Cliente cliente = new Cliente();
			ClienteNegocio cn = new ClienteNegocioImplement();
			
			int idCli = cn.obtenerUnClientePorUsuario(usuario);
			cliente = cn.obtenerUnCliente(idCli);
			
			if(cliente != null) {
				request.setAttribute("cliente", cliente);
			}
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/PerfilCliente.jsp");
	    rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
