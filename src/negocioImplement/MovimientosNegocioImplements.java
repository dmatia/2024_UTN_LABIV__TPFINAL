package negocioImplement;

import java.util.ArrayList;
import java.util.List;

import dao.iDaoMovimientos;
import daoImplement.DaoCliente;
import daoImplement.DaoMovimientos;
import entidad.Movimientos;
import negocio.MovimientosNegocio;

public class MovimientosNegocioImplements implements MovimientosNegocio{

	private iDaoMovimientos daoMovimientos;

	public MovimientosNegocioImplements() {
        this.daoMovimientos = new DaoMovimientos();
    }
	
	@Override
	public ArrayList<Movimientos> obtenerTodosMovimientosPorTipo(int tipoMovimiento) {
		return daoMovimientos.obtenerTodosMovimientosPorTipo(tipoMovimiento) ;
	}
	
	@Override
	public List<Movimientos> leerTodosClienteImporteMayorOIgual(int idCliente, double importe) {
		return daoMovimientos.leerTodosClienteImporteMayorOIgual(idCliente, importe);
	}
	
	@Override
	public List<Movimientos> leerTodosAdminImporteMayorOIgual(double importe) {
		return daoMovimientos.leerTodosAdminImporteMayorOIgual(importe);
	}
	@Override
	public List<Movimientos> leerTodos() {
		return daoMovimientos.leerTodos();
	}

	@Override
	public ArrayList<Movimientos> obtenerMovimientosDeUnCliente(int id) {
		return daoMovimientos.obtenerMovimientosDeUnCliente(id);
	}

	@Override
	public ArrayList<Movimientos> obtenerMovimientosPorTipo(int idCliente, int tipoMovimiento) {
		return daoMovimientos.obtenerMovimientosPorTipo(idCliente,tipoMovimiento);
	}

	@Override
	public ArrayList<Movimientos> obtenerMovimientosPorCuentaId(int numeroCuenta_Cue) {
		System.out.println("Estoy en obtener Movimientos Por Cuenta Id - MovimientosNegocioImplements");
		return daoMovimientos.obtenerMovimientosPorCuentaId(numeroCuenta_Cue);
	}

	@Override
	public boolean agregarMovimiento(Movimientos nuevoMovimiento) {
		System.out.println("Estoy en obtener AgregarMovimiento - MovimientosNegocioImplements");
		return daoMovimientos.agregarMovimiento(nuevoMovimiento);
	}

	@Override
	public int agregarMovimientoTransferencia(Movimientos nuevoMovimiento) {
		return daoMovimientos.agregarMovimientoTransferencia(nuevoMovimiento);
	}

	@Override
	public int obtenerUltimoId() {
		return daoMovimientos.obtenerUltimoId();
	}

}
