package servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidad.Cliente;
import negocio.ClienteNegocio;
import negocioImplement.ClienteNegocioImplement;

/**
 * Servlet implementation class ServletModificarCliente
 */
@WebServlet("/ServletModificarCliente")
public class ServletModificarCliente extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletModificarCliente() {
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
        
        ClienteNegocio cn = new ClienteNegocioImplement();  
        boolean varAux = false;
        
        HttpSession sesion = request.getSession();
        Cliente cliente = (Cliente) sesion.getAttribute("clienteUsu");
        
        if(cliente != null) {
            varAux = true;
        }
        
        if (request.getParameter("btnModificar") != null) {
            
            if(varAux) {
                
                cliente.setNombre(request.getParameter("nombre"));
                cliente.setApellido(request.getParameter("apellido"));
                cliente.setDNI(request.getParameter("dni"));
                cliente.setCUIL(request.getParameter("cuil"));
                cliente.setSexo(request.getParameter("genero"));

                String fechaStr = request.getParameter("fechaNac");
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date fecha = dateFormat.parse(fechaStr);
                    java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());
                    cliente.setFechaNacimiento(fechaSQL);
                } catch (ParseException e) {
                    e.printStackTrace();
                    request.setAttribute("error", "Error al convertir la fecha");
                    RequestDispatcher rd = request.getRequestDispatcher("/AdminModificarCliente.jsp");
                    rd.forward(request, response);
                    return; // Salir del método en caso de error
                }

                cliente.setNacionalidad(request.getParameter("nacionalidad"));
                cliente.setDireccion(request.getParameter("direccion"));
                cliente.setProvincia(request.getParameter("provincia"));
                cliente.setLocalidad(request.getParameter("localidad"));
                cliente.setCorreoElectronico(request.getParameter("email"));
                cliente.setTelefono(request.getParameter("telefono"));
                
                boolean varUpdate = cn.actualizarCliente(cliente);
                
                if (varUpdate) {
                    request.setAttribute("mensaje", "Cliente modificado exitosamente");
                    sesion.setAttribute("clienteUsu", cliente);
                } else {
                    request.setAttribute("error", "Error al modificar el cliente");
                }
            } else {
                request.setAttribute("error", "Error el cliente esta vacio");
            }
        }
        RequestDispatcher rd = request.getRequestDispatcher("/AdminModificarCliente.jsp");
        rd.forward(request, response);
    }

}
