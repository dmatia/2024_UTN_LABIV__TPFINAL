package entidad;
import java.sql.Date;

public class Prestamos {

	private int PrestamoID;
    private Cliente ClienteAsignado;
    private Cuentas CuentaDelCliente;
    private double ImporteSoli;
    private Intereses Intereses;
    private Date FechaPedidoPrestamo;
    private double ImporteCuota;
    private double ImporteTotal;
    private int PlazoPagoMeses;
    private String EstadoSolicitud;
    private boolean Estado_Pres;
    
    
    public Prestamos() {
    	
    }
    

	public Prestamos(int prestamoID, Cliente clienteAsignado, Cuentas cuentaDelCliente, double ImporteSoli, Intereses Intereses,
			Date fechaPedidoPrestamo, double importeCuota, double importeTotal, int plazoPagoMeses,
			String estadoSolicitud, boolean estado_Pres) {
		super();
		this.PrestamoID = prestamoID;
		this.ClienteAsignado = clienteAsignado;
		this.CuentaDelCliente = cuentaDelCliente;
		this.ImporteSoli = ImporteSoli;
		this.Intereses = Intereses;
		this.FechaPedidoPrestamo = fechaPedidoPrestamo;
		this.ImporteCuota = importeCuota;
		this.ImporteTotal = importeTotal;
		this.PlazoPagoMeses = plazoPagoMeses;
		this.EstadoSolicitud = estadoSolicitud;
		this.Estado_Pres = estado_Pres;
	}
   
	
    
	public int getPrestamoID() {
		return PrestamoID;
	}
	public void setPrestamoID(int prestamoID) {
		PrestamoID = prestamoID;
	}
	
	public Cliente getClienteAsignado() {
		return ClienteAsignado;
	}


	public void setClienteAsignado(Cliente clienteAsignado) {
		ClienteAsignado = clienteAsignado;
	}


	public Cuentas getCuentaDelCliente() {
		return CuentaDelCliente;
	}


	public void setCuentaDelCliente(Cuentas cuentaDelCliente) {
		CuentaDelCliente = cuentaDelCliente;
	}


	public Date getFechaPedidoPrestamo() {
		return FechaPedidoPrestamo;
	}
	public void setFechaPedidoPrestamo(Date fechaPedidoPrestamo) {
		FechaPedidoPrestamo = fechaPedidoPrestamo;
	}
	public double getImporteCuota() {
		return ImporteCuota;
	}
	public void setImporteCuota(double importeCuota) {
		ImporteCuota = importeCuota;
	}
	public double getImporteTotal() {
		return ImporteTotal;
	}
	public void setImporteTotal(double importeTotal) {
		ImporteTotal = importeTotal;
	}
	public int getPlazoPagoMeses() {
		return PlazoPagoMeses;
	}
	public void setPlazoPagoMeses(int plazoPagoMeses) {
		PlazoPagoMeses = plazoPagoMeses;
	}
	public String getEstadoSolicitud() {
		return EstadoSolicitud;
	}
	public void setEstadoSolicitud(String estadoSolicitud) {
		EstadoSolicitud = estadoSolicitud;
	}
	public boolean isEstado_Pres() {
		return Estado_Pres;
	}
	public void setEstado_Pres(boolean estado_Pres) {
		Estado_Pres = estado_Pres;
	}
	public Intereses getIntereses() {
		return Intereses;
	}
	public void setIntereses(Intereses intereses) {
		Intereses = intereses;
	}
	public double getImporteSoli() {
		return ImporteSoli;
	}
	public void setImporteSoli(double importeSoli) {
		ImporteSoli = importeSoli;
	}
	@Override
	public String toString() {
		return "Prestamos [PrestamoID=" + PrestamoID + ", ClienteAsignado=" + ClienteAsignado + ", CuentaDelCliente="
				+ CuentaDelCliente + ", InteresesID=" + Intereses + ", FechaPedidoPrestamo=" + FechaPedidoPrestamo
				+ ", ImporteCuota=" + ImporteCuota + ", ImporteTotal=" + ImporteTotal + ", PlazoPagoMeses="
				+ PlazoPagoMeses + ", EstadoSolicitud=" + EstadoSolicitud + ", Estado_Pres=" + Estado_Pres + "]";
	}
	
}

