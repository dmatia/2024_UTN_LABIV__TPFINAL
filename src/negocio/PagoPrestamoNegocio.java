package negocio;

import java.util.ArrayList;

import entidad.PagoPrestamo;

public interface PagoPrestamoNegocio {
	public int insertarPlanDePago(int cantCuotas, int idPrestamo, int idCuenta, Double importeCuota);
	public ArrayList<PagoPrestamo> obtenerPagosPrestamoPorPrestamoId(int idPrestamo);
	public boolean modificarPagoPrestamo(PagoPrestamo pagoPrestamo);
	public boolean insertarPagoPrestamo(PagoPrestamo pagoPrestamo);
	public int generarNuevoPagoPrestamoID();
}
