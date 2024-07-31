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

import entidad.Cliente;
import entidad.Cuentas;
import entidad.Intereses;
import entidad.Prestamos;
import entidad.Usuario;
import negocio.ClienteNegocio;
import negocio.CuentasNegocio;
import negocio.InteresesNegocio;
import negocio.PrestamoNegocio;
import negocioImplement.ClienteNegocioImplement;
import negocioImplement.CuentasNegocioImplement;
import negocioImplement.InteresesNegocioImplement;
import negocioImplement.PrestamoNegocioImplement;

/**
 * Servlet implementation class ServletSolicitarPrestamo
 */
@WebServlet("/ServletSolicitarPrestamo")
public class ServletSolicitarPrestamo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletSolicitarPrestamo() {
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
			ClienteNegocio cn = new ClienteNegocioImplement();
			CuentasNegocio cuenNeg = new CuentasNegocioImplement();
			
			int idCli = cn.obtenerUnClientePorUsuario(usuario);
			ArrayList<Cuentas> cuentas = cuenNeg.obtenerCuentasDeUnCliente(idCli);
			
			if(cuentas != null) {
				request.setAttribute("cuentas", cuentas);
			}
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/solicitarPrestamo.jsp");
	    rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrestamoNegocio pn = new PrestamoNegocioImplement();
		CuentasNegocio cuenNeg = new CuentasNegocioImplement();
		InteresesNegocio in = new InteresesNegocioImplement();
		HttpSession sesion = request.getSession();
		Usuario usuario = (Usuario) sesion.getAttribute("usuario");
		
		if(usuario != null) {
			Cliente cliente = new Cliente();
			ClienteNegocio cn = new ClienteNegocioImplement();
			
			int idCli = cn.obtenerUnClientePorUsuario(usuario);
			cliente = cn.obtenerUnCliente(idCli);
			
			if(cliente != null) {
				HttpSession Sesion = request.getSession();
				Sesion.setAttribute("clientePrestamo", cliente);
			}
		}
		
		if (request.getParameter("btnConfirmarModal") != null) {
			HttpSession sesion2 = request.getSession();
			Cliente clientePrestamo = (Cliente) sesion2.getAttribute("clientePrestamo");
			
			if(clientePrestamo != null) {
				Prestamos prestamo = new Prestamos();
				Cuentas cuen = new Cuentas();
				Intereses inter = new Intereses();
				
				int idPrestamo = pn.obtenerUltimoId();
				String idCuentaStr = request.getParameter("cuenta");
				int idCuenta = Integer.parseInt(idCuentaStr);
				cuen = cuenNeg.obtenerCuentaPorNro(idCuenta);
				
				String importeSoliStr = request.getParameter("montoIngresado");
				System.out.println("ImportSoliStr: " + importeSoliStr);
				double importeSoli = Double.parseDouble(importeSoliStr);
				String interesesCuotas = request.getParameter("cuotas");
				System.out.println("INTERESES CUOTAS: " + interesesCuotas);
				
				inter = in.obternerInteres(Integer.parseInt(interesesCuotas));
				
				System.out.println(inter.toString());
				
				java.util.Date fechaActual = new java.util.Date();
				java.sql.Date sqlFechaActual = new java.sql.Date(fechaActual.getTime());
				
				System.out.println(inter.getPorcentajes());
				
				double porcentaje = (importeSoli * inter.getPorcentajes()) / 100;
				double importeTotal = importeSoli + porcentaje;
				double importeCuotas = importeTotal / inter.getCuotas();
				
				prestamo.setPrestamoID(idPrestamo);
				prestamo.setClienteAsignado(clientePrestamo);
				prestamo.setCuentaDelCliente(cuen);
				prestamo.setImporteSoli(importeSoli);
				prestamo.setIntereses(inter);
				prestamo.setFechaPedidoPrestamo(sqlFechaActual);
				prestamo.setImporteCuota(importeCuotas);
				prestamo.setImporteTotal(importeTotal);
				prestamo.setPlazoPagoMeses(inter.getCuotas());
				prestamo.setEstadoSolicitud("Pendiente");
				prestamo.setEstado_Pres(true);
				
				int estado = pn.insertar(prestamo);
				
				if(estado == 1) {
					request.setAttribute("estado", true);
				} else {
					request.setAttribute("estado", false);
				}
				
			}
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/prestamosCliente.jsp");
        rd.forward(request, response);
	}

}
