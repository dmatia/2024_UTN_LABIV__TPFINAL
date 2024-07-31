package entidad;

import java.io.Serializable;
import java.sql.Date;

public class Cuentas implements Serializable {
    private static final long serialVersionUID = 1L;

	private int NumeroCuenta_Cue ;
    private int ClienteID_Cue ;
    private TipoCuenta tipoCuenta;
    private Date FechaCreacion;
    private String CBU;
    private double Saldo;
    private boolean Estado;
    
    public Cuentas() {
    	
    }
    
	public Cuentas(int numeroCuenta_Cue, int clienteID_Cue, TipoCuenta tipoCuenta, Date fechaCreacion, String cBU,
			double saldo, boolean estado) {
		super();
		this.NumeroCuenta_Cue = numeroCuenta_Cue;
		this.ClienteID_Cue = clienteID_Cue;
		this.tipoCuenta = tipoCuenta;
		this.FechaCreacion = fechaCreacion;
		this.CBU = cBU;
		this.Saldo = saldo;
		this.Estado = estado;
	}
    
    

    public int getNumeroCuenta_Cue() {
		return NumeroCuenta_Cue;
	}
	public void setNumeroCuenta_Cue(int numeroCuenta_Cue) {
		NumeroCuenta_Cue = numeroCuenta_Cue;
	}

	public int getClienteID_Cue() {
		return ClienteID_Cue;
	}

	public void setClienteID_Cue(int clienteID_Cue) {
		ClienteID_Cue = clienteID_Cue;
	}

	public TipoCuenta getTipoCuenta() {
		return this.tipoCuenta;
	}

	public void setTipoCuenta(TipoCuenta tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public Date getFechaCreacion() {
		return FechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		FechaCreacion = fechaCreacion;
	}
	public String getCBU() {
		return CBU;
	}
	public void setCBU(String cBU) {
		CBU = cBU;
	}
	public double getSaldo() {
		return Saldo;
	}
	public void setSaldo(double saldo) {
		Saldo = saldo;
	}
	public boolean isEstado() {
		return Estado;
	}
	public void setEstado(boolean estado) {
		Estado = estado;
	}

	@Override
	public String toString() {
		return "Cuentas [NumeroCuenta_Cue=" + NumeroCuenta_Cue + ", ClienteID_Cue=" + ClienteID_Cue + ", TipoDeCuenta="
				+ this.getTipoCuenta().getIdTipoCuenta() + " " + this.getTipoCuenta().getTipoCuentaDescripcion() + ", FechaCreacion=" + FechaCreacion + ", CBU=" + CBU + ", Saldo=" + Saldo + ", Estado="
				+ Estado + "]";
	}
	
}