package dao;

import java.util.ArrayList;
import java.util.List;

import entidad.Cuentas;

public interface iDaoCuentas {
    public int darBaja(int id);
    public List<Cuentas> leerTodos();
    public ArrayList<Cuentas> obtenerCuentasDeUnCliente(int id); //Para vista de AdminVerCuenta.jsp
    public Cuentas obtenerCuentaPorNro(int nroCuenta);
    public Cuentas obtenerCuentaPorCBU(String CBU);
    public int validarCantidadCuentasCliente(int idCliente);
    public Cuentas agregarCuenta(int idCliente, int tipoCuenta);
    public int modificarEstadoATrueCuenta(int idCuenta);
    public double obtenerSaldoCuenta(int idCuenta);
    public int actualizarSaldoCuenta(int idCuenta, double saldo);
}
