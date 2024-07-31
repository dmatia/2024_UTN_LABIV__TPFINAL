package servlet;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import daoImplement.DaoCuentas;
import entidad.Cliente;
import entidad.Cuentas;
import entidad.Movimientos;
import entidad.TipoMovimientos;
import entidad.Usuario;
import excepcion.SuperaLimiteCuentasException;
import negocio.ClienteNegocio;
import negocio.CuentasNegocio;
import negocio.MovimientosNegocio;
import negocio.UsuarioNegocio;
import negocioImplement.ClienteNegocioImplement;
import negocioImplement.CuentasNegocioImplement;
import negocioImplement.MovimientosNegocioImplements;
import negocioImplement.UsuarioNegocioImplement;

/**
 * Servlet implementation class ServletCuentas
 */
@WebServlet("/ServletCuentas")
public class ServletCuentas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCuentas() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DaoCuentas cdao = new DaoCuentas();
		
		String btnVolver = request.getParameter("btnVolver");

	    if (btnVolver != null) {
	    	response.sendRedirect(request.getContextPath() + "/ServletCliente");
	        return;
	    }
		
		
		ArrayList<Cuentas> lista = null; 
		String clienteIdParam = request.getParameter("idCliente");
		HttpSession sessionIdCliente = request.getSession(); // PARA GUARDAR EL ID DEL CLIENTE FILTRADO
	        
			if (clienteIdParam != null) {
	            int clienteId = Integer.parseInt(clienteIdParam);
	            lista = cdao.obtenerCuentasDeUnCliente(clienteId);
	            request.setAttribute("listaC", lista);
	            
	            sessionIdCliente.setAttribute("IdCliente", clienteId);
	            
	            RequestDispatcher rd = request.getRequestDispatcher("/AdminVerCuentas.jsp");
	            rd.forward(request, response);
	        } else {
	        	System.out.println("Entro en el else del Cuentas");
	        }
		
	        
	        String IdModificarEstado = request.getParameter("IdModificarEstado");
		    String EstadoCuenta = request.getParameter("EstadoCuenta");
	    	int IdCliente = (int) sessionIdCliente.getAttribute("IdCliente");
			
		    if(IdModificarEstado != null && EstadoCuenta != null) {
		    	int idCue= Integer.parseInt(IdModificarEstado);
		    	
		    	if("true".equals(EstadoCuenta)) {
		    		//dar de baja
		    		cdao.darBaja(idCue);
			    	
		    	}else {
		    		//dar de alta
		    		try {
			            Cliente.validarCantidadCtasCliente(IdCliente);
			    		cdao.modificarEstadoATrueCuenta(idCue);
		    		}catch (SuperaLimiteCuentasException e) {
			            request.setAttribute("Mensaje", e.getMessage());
			        }
		    	}
		    	
		    	
		    	response.sendRedirect(request.getContextPath() + "/ServletCuentas?idCliente=" + IdCliente);
		    	return;
		    	
	        }
	        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    HttpSession session = request.getSession();
	    Usuario usuario = (Usuario) session.getAttribute("usuario");
	    String btnSolicitar = request.getParameter("btnSolicitar");

	    if (btnSolicitar != null) {
	        String idClienteParam = request.getParameter("idCliente");
	        Integer idCliente = null;

	        if (idClienteParam != null && !idClienteParam.isEmpty()) {
	            try {
	                idCliente = Integer.parseInt(idClienteParam);
	            } catch (NumberFormatException e) {
	                RequestDispatcher rd = request.getRequestDispatcher("/AdminAltaCuenta.jsp");
	                rd.forward(request, response);
	                return;
	            }
	        }

	        if (idCliente == null) {
	            request.setAttribute("Mensaje", "ID de cliente no encontrado");
	            RequestDispatcher rd = request.getRequestDispatcher("/AdminAltaCuenta.jsp");
	            rd.forward(request, response);
	            return;
	        }

	        System.out.println("IdCliente = " + idCliente);
	        System.out.println("Estoy en el post del servlet cuentas - btnSolicitarCuenta");
	        System.out.println("IdTipocuenta: " + request.getParameter("idTipoCuenta"));

	        int idTipoCuenta = Integer.parseInt(request.getParameter("idTipoCuenta"));

	        try {
	            Cliente.validarCantidadCtasCliente(idCliente);
	            CuentasNegocio cuentasNegocio = new CuentasNegocioImplement();
	            Cuentas cuentaAgregada = cuentasNegocio.agregarCuenta(idCliente, idTipoCuenta);

	        	if( cuentaAgregada !=null) {	
					System.out.println("Cuenta agregada");
					request.setAttribute("Mensaje", "Operación Realizada con éxito!");
					MovimientosNegocio movimientosNegocio = new MovimientosNegocioImplements();
					TipoMovimientos tm = new TipoMovimientos(1, "Alta Cuenta");
					double saldo = cuentaAgregada.getSaldo();
					Movimientos movimientoAlta = new Movimientos(
							0,
							cuentaAgregada.getNumeroCuenta_Cue(),
							tm,
							cuentaAgregada.getFechaCreacion(),
							cuentaAgregada.getCBU(),
							"Alta Cuenta",
							saldo
							)
							;
							if(movimientosNegocio.agregarMovimiento(movimientoAlta)) {
									System.out.println("Movimiento agregado");
							}else {
								System.out.println("Movimiento no agregado");
							}
	            } else {
	                System.out.println("No se pudo agregar su cuenta!");
	                request.setAttribute("Mensaje", "No se pudo agregar la cuenta.");
	            }
	        } catch (SuperaLimiteCuentasException e) {
	            request.setAttribute("Mensaje", e.getMessage());
	        } finally {
	            System.out.println("Estoy en el finally");
	            RequestDispatcher rd = request.getRequestDispatcher("/AdminAltaCuenta.jsp");
	            rd.forward(request, response);
	        }
	    }
	}
}
