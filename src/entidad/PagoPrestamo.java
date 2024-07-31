package entidad;
import java.sql.Date;
public class PagoPrestamo {
	
	private int PagoPrestamoID;
    private int PrestamoID;
    private int CuentaDelCliente; // cuenta desde la que se debitó el pago
    private String EstadoPago; // enum para ver si la cuota está paga o no
    private double ImporteCuota; 
    private Date FechaVencimientoPago; // fecha vencimiento del pago (cada pago corresponde a un mes)
    private Date FechaDePago; // fecha en la que se abonó el pago
    
    public PagoPrestamo() {}

	public PagoPrestamo(int pagoPrestamoID, int prestamoID, int cuentaDelCliente, String estadoPago,
			double importeCuota, Date fechaVencimientoPago, Date fechaDePago) {
		super();
		PagoPrestamoID = pagoPrestamoID;
		PrestamoID = prestamoID;
		CuentaDelCliente = cuentaDelCliente;
		EstadoPago = estadoPago;
		ImporteCuota = importeCuota;
		FechaVencimientoPago = fechaVencimientoPago;
		FechaDePago = fechaDePago;
	}

	public int getPagoPrestamoID() {
		return PagoPrestamoID;
	}

	public void setPagoPrestamoID(int pagoPrestamoID) {
		PagoPrestamoID = pagoPrestamoID;
	}

	public int getPrestamoID() {
		return PrestamoID;
	}

	public void setPrestamoID(int prestamoID) {
		PrestamoID = prestamoID;
	}

	public int getCuentaDelCliente() {
		return CuentaDelCliente;
	}

	public void setCuentaDelCliente(int cuentaDelCliente) {
		CuentaDelCliente = cuentaDelCliente;
	}

	public String getEstadoPago() {
		return EstadoPago;
	}

	public void setEstadoPago(String estadoPago) {
		EstadoPago = estadoPago;
	}

	public double getImporteCuota() {
		return ImporteCuota;
	}

	public void setImporteCuota(double importeCuota) {
		ImporteCuota = importeCuota;
	}

	public Date getFechaVencimientoPago() {
		return FechaVencimientoPago;
	}

	public void setFechaVencimientoPago(Date fechaVencimientoPago) {
		FechaVencimientoPago = fechaVencimientoPago;
	}

	public Date getFechaDePago() {
		return FechaDePago;
	}

	public void setFechaDePago(Date fechaDePago) {
		FechaDePago = fechaDePago;
	}

	@Override
	public String toString() {
		return "PagoPrestamo [PagoPrestamoID=" + PagoPrestamoID + ", PrestamoID=" + PrestamoID + ", CuentaDelCliente="
				+ CuentaDelCliente + ", EstadoPago=" + EstadoPago + ", ImporteCuota=" + ImporteCuota
				+ ", FechaVencimientoPago=" + FechaVencimientoPago + ", FechaDePago=" + FechaDePago + "]";
	}
    
	

}

