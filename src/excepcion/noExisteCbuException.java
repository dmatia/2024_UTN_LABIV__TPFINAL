package excepcion;

public class noExisteCbuException extends Exception{
	
	public noExisteCbuException() {
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "No existe el CBU ingresado.";
	}
	
}
