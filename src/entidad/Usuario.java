package entidad;

public class Usuario {

	private int id;
	private TipoUsuario tipoUsuario;
	private String usuario;
	private String contrasenia;
	private boolean estado;
	
	public Usuario() {
	}

	public Usuario(int id, TipoUsuario tipoUsuario, String usuario, String contrasenia, boolean estado) {
		super();
		this.id = id;
		this.tipoUsuario = tipoUsuario;
		this.usuario = usuario;
		this.contrasenia = contrasenia;
		this.estado = estado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Usuario - id:" + id + ", tipoUsuario: " + tipoUsuario + ", usuario: " + usuario + ", contraseña: "
				+ contrasenia + ", estado: " + estado + "]";
	}
	
	
	
	
	
	
	
}
