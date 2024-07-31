package negocioImplement;
import java.util.ArrayList;
import dao.iDaoPagoPrestamo;
import daoImplement.DaoPagoPrestamo;
import entidad.PagoPrestamo;
import negocio.PagoPrestamoNegocio;

public class PagoPrestamoNegocioImplement implements PagoPrestamoNegocio
{
	private iDaoPagoPrestamo daoPagoPrestamo;
	
	public PagoPrestamoNegocioImplement() {
		this.daoPagoPrestamo = new DaoPagoPrestamo();
	}

	@Override
	public int insertarPlanDePago(int cantCuotas, int idPrestamo, int idCuenta, Double importeCuota) {
		return this.daoPagoPrestamo.insertarPlanDePago( cantCuotas, idPrestamo, idCuenta, importeCuota);
	}

	@Override
	public ArrayList<PagoPrestamo> obtenerPagosPrestamoPorPrestamoId(int idPrestamo){
		return this.daoPagoPrestamo.obtenerPagosPrestamoPorPrestamoId(idPrestamo);
	}

	@Override
	public boolean modificarPagoPrestamo(PagoPrestamo pagoPrestamo) {
		return this.daoPagoPrestamo.modificarPagoPrestamo(pagoPrestamo);
	}

	@Override
	public boolean insertarPagoPrestamo(PagoPrestamo pagoPrestamo) {
		return this.daoPagoPrestamo.insertarPagoPrestamo(pagoPrestamo);
	}

	@Override
	public int generarNuevoPagoPrestamoID() {
		return this.daoPagoPrestamo.generarNuevoPagoPrestamoID();
		
	}

}
