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
import entidad.Movimientos;
import entidad.TipoMovimientos;
import entidad.Usuario;
import excepcion.SaldoInsuficienteException;
import excepcion.TransfMismaCuentaException;
import excepcion.noExisteCbuException;
import negocio.ClienteNegocio;
import negocio.CuentasNegocio;
import negocio.MovimientosNegocio;
import negocioImplement.ClienteNegocioImplement;
import negocioImplement.CuentasNegocioImplement;
import negocioImplement.MovimientosNegocioImplements;

/**
 * Servlet implementation class ServletTransferir
 */
@WebServlet("/ServletTransferir")
public class ServletTransferir extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletTransferir() {
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
				HttpSession session = request.getSession();
			    session.setAttribute("sessionCuentas", cuentas);
			    request.setAttribute("cuentas", cuentas);
			}
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/Transferir.jsp");
	    rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CuentasNegocio cuenNeg = new CuentasNegocioImplement();
		MovimientosNegocio movNeg = new MovimientosNegocioImplements();

		if (request.getParameter("btnConfirmarModal") != null) {
		    Cuentas cuen = new Cuentas();
		    Cuentas cuenDestino = new Cuentas();

		    String idCuentaStr = request.getParameter("cuenta");
		    int idCuenta = Integer.parseInt(idCuentaStr);
		    cuen = cuenNeg.obtenerCuentaPorNro(idCuenta);
		    String destinoCBU = request.getParameter("numerosCBU");
		    cuenDestino = cuenNeg.obtenerCuentaPorCBU(destinoCBU);
		    String importeTransferirStr = request.getParameter("numerosCtransf");
		    double importeTransferir = Double.parseDouble(importeTransferirStr);
		    double saldoCuenta = cuen.getSaldo();

		    try {
		        if (cuen.getCBU().equals(destinoCBU)) {
		            throw new TransfMismaCuentaException();
		        }
		        
		        if (importeTransferir > saldoCuenta) {
		            throw new SaldoInsuficienteException();
		        }
		        
		        if (cuenDestino == null) {
		            throw new noExisteCbuException();
		        }
		        
		        TipoMovimientos tm = new TipoMovimientos(4, "Transferencia");

		        double saldoCuenOrigen = cuenNeg.obtenerSaldoCuenta(cuen.getNumeroCuenta_Cue()) - importeTransferir;
		        double saldoCuenDestino = cuenNeg.obtenerSaldoCuenta(cuenDestino.getNumeroCuenta_Cue()) + importeTransferir;
		        
		        int varTransOrigen = cuenNeg.actualizarSaldoCuenta(cuen.getNumeroCuenta_Cue(), saldoCuenOrigen);
		        int varTransDestino = cuenNeg.actualizarSaldoCuenta(cuenDestino.getNumeroCuenta_Cue(), saldoCuenDestino);
		        
		        boolean varSaldo = false;
		        if(varTransOrigen == 1 && varTransDestino == 1) {
		        	varSaldo = true;
		        }

		        java.util.Date fechaActual = new java.util.Date();
		        java.sql.Date sqlFechaActual = new java.sql.Date(fechaActual.getTime());

		        int movId = movNeg.obtenerUltimoId();

		        Movimientos movOrigen = new Movimientos(movId, cuen.getNumeroCuenta_Cue(), tm, sqlFechaActual, cuenDestino.getCBU(), "Transferencia Enviada", importeTransferir);
		        Movimientos movDestino = new Movimientos(movId, cuenDestino.getNumeroCuenta_Cue(), tm, sqlFechaActual, cuen.getCBU(), "Transferencia Recibida", importeTransferir);

		        int varMovOrigen = movNeg.agregarMovimientoTransferencia(movOrigen);
		        int varMovDestino = movNeg.agregarMovimientoTransferencia(movDestino);
		        
		        boolean varMov = false;
		        if(varMovOrigen == 1 && varMovDestino == 1) {
		        	varMov = true;
		        }
		        
		        if(varSaldo == true && varMov == true) {
		        	request.setAttribute("estadoTrans", true);
		        	RequestDispatcher rd = request.getRequestDispatcher("/MenuCliente.jsp");
		    		rd.forward(request, response);
		        }
		        else {
		        	request.setAttribute("estadoTrans", false);
		        	RequestDispatcher rd = request.getRequestDispatcher("/MenuCliente.jsp");
		    		rd.forward(request, response);
		        }
		        
		    } catch (noExisteCbuException | TransfMismaCuentaException | SaldoInsuficienteException e) {
		        request.setAttribute("errorMensaje", e.getMessage());
		        request.setAttribute("estadoTrans", false);
		        RequestDispatcher rd = request.getRequestDispatcher("/Transferir.jsp");
				rd.forward(request, response);
		    }
		}

		//RequestDispatcher rd = request.getRequestDispatcher("/MenuCliente.jsp");
		//rd.forward(request, response);

	}

}
