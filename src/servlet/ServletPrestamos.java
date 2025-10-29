package servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import daoImplement.DaoCuentas;
import daoImplement.DaoPrestamo;
import entidad.Cuentas;
import entidad.Movimientos;
import entidad.PagoPrestamo;
import entidad.Prestamos;
import entidad.TipoMovimientos;
import negocio.CuentasNegocio;
import negocio.MovimientosNegocio;
import negocio.PagoPrestamoNegocio;
import negocio.PrestamoNegocio;
import negocioImplement.CuentasNegocioImplement;
import negocioImplement.MovimientosNegocioImplements;
import negocioImplement.PagoPrestamoNegocioImplement;
import negocioImplement.PrestamoNegocioImplement;

@WebServlet("/ServletPrestamos")
public class ServletPrestamos extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrestamoNegocio PresNeg = new PrestamoNegocioImplement();
		List<Prestamos> listaPrestamos = PresNeg.obtenerPrestamosPendientes(PresNeg.obtenerPrestamosCompletos());

		HttpSession session = request.getSession();
		session.setAttribute("listaPrestamos", listaPrestamos);

		// request.setAttribute("listaPrestamos", listaPrestamos);

		RequestDispatcher dispatcher = request.getRequestDispatcher("AutorizarPrestamos.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Enumeration<String> attributeNames = session.getAttributeNames();

		System.out.println("Contenido de la sesión:");
		while (attributeNames.hasMoreElements()) {
			String attributeName = attributeNames.nextElement();
			Object attributeValue = session.getAttribute(attributeName);
			System.out.println(attributeName + ": " + attributeValue);
		}

		if (request.getParameter("VerPrestamos") != null) {
			int idCliente = (int) session.getAttribute("idCliente");
			PrestamoNegocio prestamoNegocio = new PrestamoNegocioImplement();
			ArrayList<Prestamos> listaPrestamos = (ArrayList<Prestamos>) prestamoNegocio
					.obtenerPrestamosPorClienteId(idCliente);
			session.setAttribute("listaPrestamosCliente", listaPrestamos);

			ArrayList<PagoPrestamo> listaPagos = new ArrayList<>();
			PagoPrestamoNegocio pagoPrestamoNegocio = new PagoPrestamoNegocioImplement();
			for (Prestamos p : listaPrestamos) {
				ArrayList<PagoPrestamo> listaPagosAuxiliar = pagoPrestamoNegocio
						.obtenerPagosPrestamoPorPrestamoId(p.getPrestamoID());
				for (PagoPrestamo pp : listaPagosAuxiliar) {
					System.out.println(pp.toString());
				}
				listaPagos.addAll(listaPagosAuxiliar);
			}

			session.setAttribute("listaPagos", listaPagos);
			RequestDispatcher dispatcher = request.getRequestDispatcher("PrestamosYCuotas.jsp");
			dispatcher.forward(request, response);
		}

		else if (request.getParameter("action") != null) {
			String mensaje = "";
			int prestamoID = Integer.parseInt(request.getParameter("prestamoID"));
			ArrayList<Prestamos> listaP = ((ArrayList<Prestamos>) session.getAttribute("listaPrestamos"));

			System.out.println("LISTA PAGOS: " + listaP.toString());

			PrestamoNegocio prestamoNegocio = new PrestamoNegocioImplement();
			if (request.getParameter("action").equals("Aprobar")) {
				System.out.println("ESTOY EN APROBAR");
				for (Prestamos p : listaP) {
					if (p.getPrestamoID() == prestamoID) {
						
						System.out.println("ESTOY EN EL PRESTAMO");
						
						p.setEstadoSolicitud("Aprobado");
						
						PagoPrestamoNegocio pagoPrestamoNegocio = new PagoPrestamoNegocioImplement();
						
						if ((pagoPrestamoNegocio.insertarPlanDePago(p.getPlazoPagoMeses(), p.getPrestamoID(),
								p.getCuentaDelCliente().getNumeroCuenta_Cue(), p.getImporteCuota())) > 0) {
							System.out.println("PLAN AGREGADO");


							MovimientosNegocio movNeg = new MovimientosNegocioImplements();
							TipoMovimientos tm = new TipoMovimientos(2, "Alta de un prestamo");
							int movId = movNeg.obtenerUltimoId();
							System.out.println("ULTIMO MOV ID: " +  movId);
							Movimientos movOrigen = new Movimientos(movId,
									p.getCuentaDelCliente().getNumeroCuenta_Cue(), tm, p.getFechaPedidoPrestamo(),
									p.getCuentaDelCliente().getCBU(), "Alta Prestamo", p.getImporteSoli());
							
							if (movNeg.agregarMovimiento(movOrigen)) {
								System.out.println("AGREGUE MOVI");
								mensaje += " Registro agregado a Movimientos.";
							} else {
								System.out.println("mOVIMIENTO NO AGREGADO");
								mensaje += " Registro no pudo ser agregado a Movimientos.";
							}
							prestamoNegocio.actualizarPrestamo(p);
							
							Cuentas cuentas = p.getCuentaDelCliente();
							
							CuentasNegocio cuentaNegocio = new CuentasNegocioImplement();
							cuentaNegocio.actualizarSaldoCuenta(cuentas.getNumeroCuenta_Cue(), (cuentas.getSaldo()+p.getImporteSoli()));
							
							
						} else {
							mensaje = "No se pudo aprobar el préstamo";
						}
					}
				}
			} else if (request.getParameter("action").equals("Rechazar")) {
				for (Prestamos p : listaP) {
					if (p.getPrestamoID() == prestamoID) {
						p.setEstadoSolicitud("Rechazado");
						if (prestamoNegocio.actualizarPrestamo(p)) {
							mensaje = "Préstamo rechazado correctamente.";
						}
					}
				}
			}

				session.setAttribute("listaPrestamos", listaP);

				request.setAttribute("mensaje", mensaje);
				RequestDispatcher dispatcher = request.getRequestDispatcher("AutorizarPrestamos.jsp");
				dispatcher.forward(request, response);
			

		}
	}
}

// } else {
//
// Prestamos prestamo = (Prestamos)session.getAttribute("prestamoID");
//
// System.out.println("prestamo = " + prestamo.toString());
//
// // int prestamoID = Integer.parseInt(request.getParameter("prestamoID"));
// int nroCuenta = Integer.parseInt(request.getParameter("NumeroCuenta"));
// int cantMeses = Integer.parseInt(request.getParameter("CantMeses"));
// double montoCuota = Double.parseDouble(request.getParameter("ImporteCuota"));
// String action = request.getParameter("action");
//
// MovimientosNegocio movNeg = new MovimientosNegocioImplements();
// PrestamoNegocio prestamoNegocio = new PrestamoNegocioImplement();
// CuentasNegocio cuentaNegocio = new CuentasNegocioImplement();
// PagoPrestamoNegocio pagoPrestamoNegocio = new PagoPrestamoNegocioImplement();
//
//// Prestamos prestamo = prestamoNegocio.obtenerPrestamoPorID(prestamoID);
// String mensaje = "";
//
// if (prestamo != null) {
// if (action != null) {
// if (action.equals("Aprobar")) {
// prestamo.setEstadoSolicitud("Aprobado");
// Cuentas cuenta = cuentaNegocio.obtenerCuentaPorNro(nroCuenta);
// cuenta.setSaldo(cuenta.getSaldo() + prestamo.getImporteSoli());
// cuentaNegocio.actualizarSaldoCuenta(nroCuenta, cuenta.getSaldo());
//
// // if (pagoPrestamoNegocio.insertarPlanDePago(cantMeses, prestamoID,
// nroCuenta, montoCuota) > 0) {
//
// // System.out.println("PLAN AGREGADO");
//
// // }
// // Crear los pagos mensuales
//// Date fechaActual = prestamo.getFechaPedidoPrestamo();
//// for (int i = 0; i < cantMeses; i++) {
//// PagoPrestamo pago = new PagoPrestamo();
//// pago.setPrestamoID(prestamoID);
//// pago.setCuentaDelCliente(nroCuenta);
//// pago.setEstadoPago("No Pagado");
//// pago.setImporteCuota(montoCuota);
//// fechaActual = Date.valueOf(fechaActual.toLocalDate().plusMonths(1)); //
// Incrementar un mes
//// pago.setFechaVencimientoPago(fechaActual);
//// pago.setFechaDePago(null); // La fecha de pago es nula inicialmente
////
//// // Generar nuevo ID para el pago
//// int nuevoPagoID = pagoPrestamoNegocio.generarNuevoPagoPrestamoID();
//// pago.setPagoPrestamoID(nuevoPagoID);
////
//// if (pagoPrestamoNegocio.insertarPagoPrestamo(pago)) {
//// mensaje += " Pago " + (i + 1) + " agregado correctamente.";
//// } else {
//// mensaje += " No se pudo agregar el pago " + (i + 1) + ".";
//// } }
//
//
// mensaje += " Préstamo aprobado correctamente.";
//
// // Registrar movimiento
// TipoMovimientos tm = new TipoMovimientos(2, "Alta de un prestamo");
// int movId = movNeg.obtenerUltimoId();
// Movimientos movOrigen = new Movimientos(movId, cuenta.getNumeroCuenta_Cue(),
// tm, prestamo.getFechaPedidoPrestamo(), cuenta.getCBU(), "Alta Prestamo",
// prestamo.getImporteSoli());
// if (movNeg.agregarMovimiento(movOrigen)) {
// mensaje += " Registro agregado a Movimientos.";
// } else {
// mensaje += " Registro no pudo ser agregado a Movimientos.";
// }
// } else if (action.equals("Rechazar")) {
// prestamo.setEstadoSolicitud("Rechazado");
// mensaje = "Préstamo rechazado correctamente.";
// }
//
// if (!prestamoNegocio.actualizarPrestamo(prestamo)) {
// mensaje = "No se pudo actualizar el estado del préstamo.";
// }
// } else {
// mensaje = "Acción no válida.";
// }
// } else {
// // mensaje = "No se encontró el préstamo con ID: " + prestamoID;
// }
//
// request.setAttribute("mensaje", mensaje);
//
// // Redirigir después de procesar la solicitud
// doGet(request, response); // Para actualizar la lista de préstamos y mostrar
// el mensaje
// }
