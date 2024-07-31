package entidad;

import java.sql.Date;


public class Movimientos {


	private int MovimientosID;
	private int NumeroCuenta;
	   private TipoMovimientos TipoMovimientos;
	    private Date FechaMov;
	    private String CBUMov;
	    private String DetalleMov;
	    private double Importe_Mov;
	    
	    
	    public Movimientos() {
	    	
	    }
	    
		   public Movimientos(int movimientosID, int numeroCuenta, TipoMovimientos tipoMovimientos, Date fechaMov, String cBUMov,
					String detalleMov, double importe_Mov) {
				super();
				this.MovimientosID = movimientosID;
				this.NumeroCuenta = numeroCuenta;
				this.TipoMovimientos = tipoMovimientos;
				this.FechaMov = fechaMov;
				this.CBUMov = cBUMov;
				this.DetalleMov = detalleMov;
				this.Importe_Mov = importe_Mov;
			}
	    
	    
		   public int getMovimientosID() {
				return MovimientosID;
			}
			public void setMovimientosID(int movimientosID) {
				MovimientosID = movimientosID;
			}
			public int getNumeroCuenta() {
				return NumeroCuenta;
			}
			public void setNumeroCuenta(int numeroCuenta) {
				NumeroCuenta = numeroCuenta;
			}

			public TipoMovimientos getTipoMovimientos() {
				return TipoMovimientos;
			}
			public void setTipoMovimientos(TipoMovimientos tipoMovimientos) {
				this.TipoMovimientos = tipoMovimientos;
			}
			public Date getFechaMov() {
				return FechaMov;
			}
			public void setFechaMov(Date fechaMov) {
				FechaMov = fechaMov;
			}
			public String getCBUMov() {
				return CBUMov;
			}
			public void setCBUMov(String cBUMov) {
				CBUMov = cBUMov;
			}
			public String getDetalleMov() {
				return DetalleMov;
			}
			public void setDetalleMov(String detalleMov) {
				DetalleMov = detalleMov;
			}
			public double getImporte_Mov() {
				return Importe_Mov;
			}
			public void setImporte_Mov(double importe_Mov) {
				Importe_Mov = importe_Mov;
			}

			@Override
			public String toString() {
				return "Movimientos [MovimientosID=" + MovimientosID + ", NumeroCuenta=" + NumeroCuenta
						+ ", TipoMovimientos=" + TipoMovimientos + ", FechaMov=" + FechaMov + ", CBUMov=" + CBUMov
						+ ", DetalleMov=" + DetalleMov + ", Importe_Mov=" + Importe_Mov + "]";
			}
	    
}
