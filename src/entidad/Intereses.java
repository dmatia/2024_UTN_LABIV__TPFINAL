package entidad;

public class Intereses {
	private int InteresesID;
    private int Cuotas;
    private double Porcentajes;
    
    public Intereses() {
    	
    }
    
	public Intereses(int InteresesID, int Cuotas, double Porcentajes) {
		super();
		this.InteresesID = InteresesID;
		this.Cuotas = Cuotas;
		this.Porcentajes = Porcentajes;
	}

	public int getInteresesID() {
		return InteresesID;
	}

	public void setInteresesID(int interesesID) {
		InteresesID = interesesID;
	}

	public int getCuotas() {
		return Cuotas;
	}

	public void setCuotas(int cuotas) {
		Cuotas = cuotas;
	}

	public double getPorcentajes() {
		return Porcentajes;
	}

	public void setPorcentajes(double porcentajes) {
		Porcentajes = porcentajes;
	}
}
