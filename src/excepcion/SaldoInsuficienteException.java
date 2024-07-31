package excepcion;

public class SaldoInsuficienteException extends Exception{
	
	public SaldoInsuficienteException() {
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "Ud. no tiene saldo suficiente para realizar la transferencia.";
	}
	
}
