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
import negocio.UsuarioNegocio;
import negocioImplement.ClienteNegocioImplement;
import negocioImplement.UsuarioNegocioImplement;


@WebServlet("/ServletLogin")
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ServletLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int validado = 0;
		// valido si el usuario existe
		if(request.getParameter("btnLogin")!=null){
			UsuarioNegocio usuarioDao = new UsuarioNegocioImplement();
			String usuario = request.getParameter("usuario").toString();
			String contrasenia = request.getParameter("contrasenia").toString();
			validado = usuarioDao.validarUsuario(usuario, contrasenia);
		}
		// si se valida, armo el usuario y veo qué tipo es, para ir al menu que corresponda y guardo sesion
		if(validado != 0) {
			UsuarioNegocio usuarioDao = new UsuarioNegocioImplement();
			String usuario = request.getParameter("usuario").toString();
			String contrasenia = request.getParameter("contrasenia").toString();
			Usuario usu = usuarioDao.obtenerUnoPorCredenciales(usuario, contrasenia);
			HttpSession Sesion = request.getSession();
			Sesion.setAttribute("usuario", usu);
			
			if(usu.getTipoUsuario().getId() == 1) {
			request.getRequestDispatcher("menuAdmin.jsp").forward(request, response);
			}
			if(usu.getTipoUsuario().getId() == 2) {
				ClienteNegocio clienteNegocio = new ClienteNegocioImplement();
				int idCliente = clienteNegocio.obtenerUnClientePorUsuario(usu);
				Sesion.setAttribute("idCliente", idCliente);
	            RequestDispatcher miDispacher = request.getRequestDispatcher("/ServletMenuCliente");
	            miDispacher.forward(request, response);
				}
		}
		else
		{
			request.setAttribute("Mensaje", "No se pudo validar sus datos. Intentelo nuevamente.");
			RequestDispatcher rd = request.getRequestDispatcher("/Index.jsp");
			rd.forward(request, response);
		}
		
	}
}













