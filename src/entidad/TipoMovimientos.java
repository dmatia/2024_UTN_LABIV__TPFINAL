package entidad;

public class TipoMovimientos {

	private int TipoMovimientosID;
    private String TipoMovimientos;
    
    public TipoMovimientos() {}
    
	public TipoMovimientos(int tipoMovimientosID, String tipoMovimientos) {
		super();
		this.TipoMovimientosID = tipoMovimientosID;
		this.TipoMovimientos = tipoMovimientos;
	}
    
	public int getTipoMovimientosID() {
		return TipoMovimientosID;
	}
	public void setTipoMovimientosID(int tipoMovimientosID) {
		TipoMovimientosID = tipoMovimientosID;
	}
	public String getTipoMovimientos() {
		return TipoMovimientos;
	}
	public void setTipoMovimientos(String tipoMovimientos) {
		TipoMovimientos = tipoMovimientos;
	}

	@Override
	public String toString() {
		return this.TipoMovimientos;
	}

}
