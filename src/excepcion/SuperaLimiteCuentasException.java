package excepcion;

public class SuperaLimiteCuentasException extends Exception {
	public SuperaLimiteCuentasException() {
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "LIMITE DE CUENTAS SUPERADO. No se ha agregado la cuenta.";
	}
}
