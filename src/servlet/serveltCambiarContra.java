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
import negocio.UsuarioNegocio;
import negocioImplement.UsuarioNegocioImplement;

/**
 * Servlet implementation class serveltCambiarContra
 */
@WebServlet("/serveltCambiarContra")
public class serveltCambiarContra extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public serveltCambiarContra() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean contraAux = false;
		int validado = 0;
		
		if(request.getParameter("btnConfirmar")!=null) {
			HttpSession sesion = request.getSession();
		    Cliente cliente = (Cliente) sesion.getAttribute("clienteUsu");
		    int usuarioCli = cliente.getIdUsuario().getId();
			String contra = request.getParameter("contra").toString();
			String contraRep = request.getParameter("contraRep").toString();
			if(contra.equals(contraRep)) {
				contraAux = true;
				request.setAttribute("contraAux", true);
			} else {
				contraAux = false;
				request.setAttribute("contraAux", false);
			}
			if(contraAux) {
				UsuarioNegocio usuarioDao = new UsuarioNegocioImplement();
				validado = usuarioDao.cambiarContraUsuario(usuarioCli, contra);
			}
			if(validado == 1) {
				request.setAttribute("validado", true);
			} else {
				request.setAttribute("validado", false);
			}
			RequestDispatcher rDispatcher = request.getRequestDispatcher("/ModificarContra.jsp");
			rDispatcher.forward(request, response);
		}
	}

}
