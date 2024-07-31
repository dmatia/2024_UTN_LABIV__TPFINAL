package entidad;

public class TipoCuenta {
	private int idTipoCuenta;
	private String tipoCuentaDescripcion;
	
	public TipoCuenta() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TipoCuenta(int idTipoCuenta, String tipoCuentaDescripcion) {
		super();
		this.idTipoCuenta = idTipoCuenta;
		this.tipoCuentaDescripcion = tipoCuentaDescripcion;
	}

	public int getIdTipoCuenta() {
		return idTipoCuenta;
	}

	public void setIdTipoCuenta(int idTipoCuenta) {
		this.idTipoCuenta = idTipoCuenta;
	}

	public String getTipoCuentaDescripcion() {
		return tipoCuentaDescripcion;
	}

	public void setTipoCuentaDescripcion(String tipoCuentaDescripcion) {
		this.tipoCuentaDescripcion = tipoCuentaDescripcion;
	}

	@Override
	public String toString() {
		return "TipoCuenta [idTipoCuenta=" + idTipoCuenta + ", tipoCuentaDescripcion=" + tipoCuentaDescripcion + "]";
	}
	
}
