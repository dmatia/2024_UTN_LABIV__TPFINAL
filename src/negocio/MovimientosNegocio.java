package negocio;

import java.util.ArrayList;
import java.util.List;

import entidad.Movimientos;

public interface MovimientosNegocio {
	public List<Movimientos> leerTodos();
	public ArrayList<Movimientos> obtenerMovimientosDeUnCliente(int id); //Para vista de AdminVerMovimientos.jsp
	public ArrayList<Movimientos> obtenerMovimientosPorTipo(int idCliente, int tipoMovimiento);
	public ArrayList<Movimientos> obtenerMovimientosPorCuentaId(int numeroCuenta_Cue);
	public boolean agregarMovimiento(Movimientos nuevoMovimiento);
	public int agregarMovimientoTransferencia(Movimientos nuevoMovimiento);
	public int obtenerUltimoId();

	public List<Movimientos> leerTodosAdminImporteMayorOIgual(double importe);
	public ArrayList<Movimientos> obtenerTodosMovimientosPorTipo(int tipoMovimiento);
	
	public List<Movimientos> leerTodosClienteImporteMayorOIgual(int idCliente, double importe);
}