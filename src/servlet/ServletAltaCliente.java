package servlet;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import daoImplement.DaoCliente;
import entidad.Cliente;
import entidad.TipoUsuario;
import entidad.Usuario;
import negocio.ClienteNegocio;
import negocioImplement.ClienteNegocioImplement;

/**
 * Servlet implementation class ServletAltaCliente
 */
@WebServlet("/ServletAltaCliente")
public class ServletAltaCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAltaCliente() {
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
	    ClienteNegocio cdao = new ClienteNegocioImplement();
	    boolean contraAux = false;

	    if (request.getParameter("btnRegistro") != null) {
	    	String contra = request.getParameter("contrasenia").toString();
			String contraRep = request.getParameter("contraRep").toString();
			
			
			if(contra.equals(contraRep)) {
				contraAux = true;
				request.setAttribute("contraAux", true);
			} else {
				contraAux = false;
				request.setAttribute("contraAux", false);
				request.setAttribute("error", "Las contraseñas no coinciden.");
			}
			
	    	
	    	if(contraAux){
		        try {
		            TipoUsuario tu = new TipoUsuario();
		            tu.setId(2);
		            tu.setTipoUsuario("Cliente");
	
		            Usuario u = new Usuario();
		            u.setTipoUsuario(tu);
		            u.setUsuario(request.getParameter("usuario"));
		            u.setContrasenia(request.getParameter("contrasenia"));
	
		            Cliente c = new Cliente();
		            c.setIdUsuario(u);
		            c.setNombre(request.getParameter("nombre"));
		            c.setApellido(request.getParameter("apellido"));
		            c.setDNI(request.getParameter("dni"));
		            c.setCUIL(request.getParameter("cuil"));
		            c.setSexo(request.getParameter("genero"));
	
		            String fechaStr = request.getParameter("fechaNac");
		            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		            Date fecha = dateFormat.parse(fechaStr);
		            java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());
		            c.setFechaNacimiento(fechaSQL);
	
		            c.setNacionalidad(request.getParameter("nacionalidad"));
		            c.setDireccion(request.getParameter("direccion"));
		            c.setProvincia(request.getParameter("provincia"));
		            c.setLocalidad(request.getParameter("localidad"));
		            c.setCorreoElectronico(request.getParameter("email"));
		            c.setTelefono(request.getParameter("telefono"));
	
		            int resultado = cdao.insertar(c, u);		       
		            if (resultado == 1) {
		                request.setAttribute("mensaje", "Usuario registrado exitosamente");
		            } else {
		                request.setAttribute("error", "Error al registrar el usuario");
		            }
	
		        } catch (ParseException | java.text.ParseException e) {
		            e.printStackTrace();
		            request.setAttribute("error", "Error en el formato de la fecha de nacimiento");
		        }
	    	}
		        
		        RequestDispatcher rd = request.getRequestDispatcher("/AltaUsuario.jsp");
		        rd.forward(request, response);
		    }
	    }
	    
	}
