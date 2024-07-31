package excepcion;

public class TransfMismaCuentaException extends Exception{
	
	public TransfMismaCuentaException() {
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "Error, el CBU de destino no puede ser igual al CBU de origen.";
	}

}
