package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidad.Cuentas;
import entidad.Movimientos;
import entidad.PagoPrestamo;
import entidad.TipoMovimientos;
import negocio.CuentasNegocio;
import negocio.MovimientosNegocio;
import negocio.PagoPrestamoNegocio;
import negocioImplement.CuentasNegocioImplement;
import negocioImplement.MovimientosNegocioImplements;
import negocioImplement.PagoPrestamoNegocioImplement;

/**
 * Servlet implementation class ServletPagoPrestamo
 */
@WebServlet("/ServletPagoPrestamo")
public class ServletPagoPrestamo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletPagoPrestamo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("ESTOY EN EL SERVLET PAGO PRESTAMOS");

		HttpSession session = request.getSession();

		Enumeration<String> attributeNames = session.getAttributeNames();

		System.out.println("Contenido de la sesi n:");

		while (attributeNames.hasMoreElements()) {
			String attributeName = attributeNames.nextElement();
			Object attributeValue = session.getAttribute(attributeName);

			System.out.println(attributeName + ": " + attributeValue);
		}

		// en sesion tengo el id cliente
		// cuentascliente
		// lista prestamos

		if (request.getParameter("btnPagarPagoPrestamo") != null) {
			System.out.println("ESTOY EN EL BOTON BTNPAGARPAGOPRESTAMO");
			if (validarOpciones(request, session)) {
				if (validarSaldo(request, session)) {
					int nroPago = Integer.parseInt(request.getParameter("cuotaSeleccionada"));
					ArrayList<PagoPrestamo> pagosPrestamo = ((ArrayList<PagoPrestamo>) session
							.getAttribute("listaPagos"));
					PagoPrestamo pagoActualizado = new PagoPrestamo();
					PagoPrestamoNegocio pagoPrestamoNegocio = new PagoPrestamoNegocioImplement();
					for (PagoPrestamo pp : pagosPrestamo) {
						if (pp.getPagoPrestamoID() == nroPago) {
							pagoActualizado = pp;
							LocalDate fechaDeHoy = LocalDate.now();
							java.sql.Date fechaDePago = java.sql.Date.valueOf(fechaDeHoy);
							pp.setFechaDePago(fechaDePago);
							pp.setEstadoPago("Pagado");
							pagoActualizado.setFechaDePago(fechaDePago);
							pagoActualizado.setEstadoPago("Pagado");
							System.out.println(pagoActualizado.toString());
							if (pagoPrestamoNegocio.modificarPagoPrestamo(pagoActualizado)) {
								if (registrarMovimiento(request, session)) {
									if(actualizarSaldoCuenta(request, session)) {
												request.setAttribute("mensajePagoPrestamo",
												"El pago se realiz  correctamente.");
									
									}else {
										request.setAttribute("mensajePagoPrestamo",
												"No se pudo actualizar el saldo de la cuenta. La cuota fue saldada y el movimiento registrado.");
									}
								} else {
									request.setAttribute("mensajePagoPrestamo",
											"No se pudo registrar el movimiento de pago. La cuota fue saldada");
								}
							} else {
								request.setAttribute("mensajePagoPrestamo",
										"No se pudo procesar el pago. Intentelo nuevamente. [ERROR BASE DATOS]");
							}
						}
					}
					session.setAttribute("listaPagos", pagosPrestamo);

				} else {
					request.setAttribute("mensajePagoPrestamo",
							"La cuenta seleccionada no posee saldo suficiente para realizar este pago.");
				}

			} else {
				request.setAttribute("mensajePagoPrestamo", "Primero debe elegir una cuota y una cuenta a debitar.");
			}

			RequestDispatcher miDispacher = request.getRequestDispatcher("PrestamosYCuotas.jsp");
			miDispacher.forward(request, response);
			return;
		}

	}

	protected boolean validarOpciones(HttpServletRequest request, HttpSession sesionUsuario) {
		System.out.println("ESTOY EN VALIDAR OPCIONS");

		String cuotaSeleccionada = request.getParameter("cuotaSeleccionada");
		String cuentaSeleccionada = request.getParameter("cuentaSeleccionada");
		if (cuotaSeleccionada == null || cuotaSeleccionada.equals("SELECCIONE CUOTA A PAGAR")
				|| cuentaSeleccionada == null || cuentaSeleccionada.equals("SELECCIONE CUENTA A DEBITAR")) {
			System.out.println("ESTOY DEVOLVIENDO FALSE EN VALIDAR OPCIONES");
			return false;

		}
		return true;
	}

	protected boolean validarSaldo(HttpServletRequest request, HttpSession sesionUsuario) {
		int nroPago = Integer.parseInt(request.getParameter("cuotaSeleccionada"));
		int nroCuenta = Integer.parseInt(request.getParameter("cuentaSeleccionada"));

		ArrayList<PagoPrestamo> pagosPrestamo = ((ArrayList<PagoPrestamo>) sesionUsuario.getAttribute("listaPagos"));
		ArrayList<Cuentas> cuentasUsuario = ((ArrayList<Cuentas>) sesionUsuario.getAttribute("cuentasCliente"));
		for (Cuentas c : cuentasUsuario) {
			if (c.getNumeroCuenta_Cue() == nroCuenta) {
				for (PagoPrestamo pp : pagosPrestamo) {
					if (pp.getPagoPrestamoID() == nroPago) {
						if (c.getSaldo() >= pp.getImporteCuota()) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	protected boolean registrarMovimiento(HttpServletRequest request, HttpSession sesionUsuario) {
		int nroPago = Integer.parseInt(request.getParameter("cuotaSeleccionada"));
		int nroCuenta = Integer.parseInt(request.getParameter("cuentaSeleccionada"));

		TipoMovimientos tm = new TipoMovimientos(3, "Pago Prestamo");
		LocalDate fechaDeHoy = LocalDate.now();
		java.sql.Date fechaDePago = java.sql.Date.valueOf(fechaDeHoy);
		Movimientos movimiento = new Movimientos();
		ArrayList<PagoPrestamo> pagosPrestamo = ((ArrayList<PagoPrestamo>) sesionUsuario.getAttribute("listaPagos"));

		for (PagoPrestamo pp : pagosPrestamo) {
			if (pp.getPagoPrestamoID() == nroPago) {
				movimiento.setMovimientosID(0);
				movimiento.setNumeroCuenta(nroCuenta);
				movimiento.setTipoMovimientos(tm);
				movimiento.setCBUMov("ENTIDAD BANCARIA");
				movimiento.setFechaMov(fechaDePago);
				movimiento.setDetalleMov("Pago Prestamo");
				movimiento.setImporte_Mov(pp.getImporteCuota());
			}
		}

		MovimientosNegocio movimientosNegocio = new MovimientosNegocioImplements();

		if (movimientosNegocio.agregarMovimiento(movimiento)) {
			System.out.println("Movimiento de pago prestamo agregado");
			return true;
		} else {
			System.out.println("Movimiento de pago prestamo no agregado");
			return false;
		}
	}

	protected boolean actualizarSaldoCuenta(HttpServletRequest request, HttpSession sesionUsuario) {
		int nroPago = Integer.parseInt(request.getParameter("cuotaSeleccionada"));
		int nroCuenta = Integer.parseInt(request.getParameter("cuentaSeleccionada"));

		ArrayList<PagoPrestamo> pagosPrestamo = ((ArrayList<PagoPrestamo>) sesionUsuario.getAttribute("listaPagos"));
		ArrayList<Cuentas> cuentasUsuario = ((ArrayList<Cuentas>) sesionUsuario.getAttribute("cuentasCliente"));
		for (Cuentas c : cuentasUsuario) {
			if (c.getNumeroCuenta_Cue() == nroCuenta) {
				for (PagoPrestamo pp : pagosPrestamo) {
					if (pp.getPagoPrestamoID() == nroPago) {
							CuentasNegocio cuentasNegocio = new CuentasNegocioImplement();
							if(cuentasNegocio.actualizarSaldoCuenta(c.getNumeroCuenta_Cue(), (c.getSaldo() - pp.getImporteCuota())) ==1){
								c.setSaldo((c.getSaldo() - pp.getImporteCuota()));
								System.out.println("CUENTA ACTUALIZADA EN DB");
							}else {
								return false;
							}
						}
					}
				}
		}
		
		sesionUsuario.setAttribute("cuentasCliente", cuentasUsuario);
		
		return true;
	}
}
