package dao;
import java.util.ArrayList;

import entidad.PagoPrestamo;

public interface iDaoPagoPrestamo {
	public int insertarPlanDePago(int cantCuotas, int idPrestamo, int idCuenta, Double importeCuota);
	public ArrayList<PagoPrestamo> obtenerPagosPrestamoPorPrestamoId(int idPrestamo);
	public boolean modificarPagoPrestamo(PagoPrestamo pagoPrestamo);
	boolean insertarPagoPrestamo(PagoPrestamo pagoPrestamo);
	public int generarNuevoPagoPrestamoID();
}

