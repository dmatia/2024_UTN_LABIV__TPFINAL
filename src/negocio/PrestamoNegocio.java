package negocio;

import java.util.ArrayList;
import java.util.List;

import entidad.Prestamos;

public interface PrestamoNegocio {
	
	public int insertar(Prestamos prestamo);
	public int obtenerUltimoId();
	public ArrayList<Prestamos> obtenerPrestamosPorClienteId(int clienteID);
	public List<Prestamos> obtenerPrestamosCompletos();
	public List<Prestamos> obtenerPrestamosPendientes(List<Prestamos> listaPrestamosCompletos);
	public Prestamos obtenerPrestamoPorID(int idPrestamo);
	public boolean actualizarPrestamo(Prestamos prestamo);
}
