package negocioImplement;

import java.util.ArrayList;
import java.util.List;

import dao.iDaoCuentas;
import daoImplement.DaoCuentas;
import entidad.Cuentas;
import negocio.CuentasNegocio;


public class CuentasNegocioImplement implements CuentasNegocio {

	@Override
	public int modificarEstadoATrueCuenta(int idCuenta) {
		return daoCuentas.modificarEstadoATrueCuenta(idCuenta);
	}

	private iDaoCuentas daoCuentas;
	
	public CuentasNegocioImplement() {
        this.daoCuentas = new DaoCuentas();
    }
	
	@Override
	public int darBaja(int id) {
		return daoCuentas.darBaja(id);
	}

	@Override
	public List<Cuentas> leerTodos() {
		return daoCuentas.leerTodos();
	}

	@Override
	public ArrayList<Cuentas> obtenerCuentasDeUnCliente(int id) {
		return daoCuentas.obtenerCuentasDeUnCliente(id);
	}

	@Override
	public Cuentas obtenerCuentaPorNro(int nroCuenta) {
		return daoCuentas.obtenerCuentaPorNro(nroCuenta);
	}

	@Override
	public Cuentas agregarCuenta(int idCliente, int tipoCuenta) {
		return daoCuentas.agregarCuenta(idCliente, tipoCuenta);
	}

	@Override
	public Cuentas obtenerCuentaPorCBU(String CBU) {
		return daoCuentas.obtenerCuentaPorCBU(CBU);
	}

	@Override
	public int actualizarSaldoCuenta(int idCuenta, double saldo) {
		return daoCuentas.actualizarSaldoCuenta(idCuenta, saldo);
	}

	@Override
	public double obtenerSaldoCuenta(int idCuenta) {
		return daoCuentas.obtenerSaldoCuenta(idCuenta);
	}

}