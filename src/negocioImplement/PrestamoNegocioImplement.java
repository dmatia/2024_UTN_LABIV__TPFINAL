package negocioImplement;

import java.util.ArrayList;
import java.util.List;

import dao.iDaoPrestamo;
import daoImplement.DaoPrestamo;
import entidad.Prestamos;
import negocio.PrestamoNegocio;

public class PrestamoNegocioImplement implements PrestamoNegocio{

	private iDaoPrestamo daoPrestamo;
	
	
	public PrestamoNegocioImplement() {
        this.daoPrestamo = new DaoPrestamo();
    }
	
	@Override
	public int insertar(Prestamos prestamo) {
		return daoPrestamo.insertar(prestamo);
	}

	@Override
	public int obtenerUltimoId() {
		return daoPrestamo.obtenerUltimoId();
	}

	@Override
	public ArrayList<Prestamos> obtenerPrestamosPorClienteId(int clienteID) {
		return daoPrestamo.obtenerPrestamosPorClienteId(clienteID);
	}

	public List<Prestamos> obtenerPrestamosCompletos() {
		return daoPrestamo.obtenerPrestamosCompletos();
	}

	@Override
	public List<Prestamos> obtenerPrestamosPendientes(List<Prestamos> listaPrestamosCompletos) {
		return daoPrestamo.obtenerPrestamosPendientes(listaPrestamosCompletos);
		
	}

	@Override
	public Prestamos obtenerPrestamoPorID(int idPrestamo) {
		return daoPrestamo.obtenerPrestamoPorID(idPrestamo);
	}

	@Override
	public boolean actualizarPrestamo(Prestamos prestamo) {
		return daoPrestamo.actualizarPrestamo(prestamo);
	}
	
	

}
