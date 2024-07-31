package entidad;

import java.sql.Date;

import daoImplement.DaoCuentas;
import excepcion.SuperaLimiteCuentasException;

public class Cliente {
	private Usuario idUsuario;
	private int idCliente;
	private String DNI;
	private String CUIL;
	private String nombre;
	private String apellido;
	private String sexo;
	private String nacionalidad;
	private Date fechaNacimiento;
	private String direccion;
	private String localidad;
	private String provincia;
	private String correoElectronico;
	private String telefono;
	private boolean estado_Cli;
	
	public Cliente() {
	}
	
	public Cliente(Usuario idUsu,int idCli,String DNI,String CUIL,String nombre,String apellido,String sexo,String nacionalidad,Date fechaNacimiento,String direccion,String localidad,String provincia,String correoElectronico,String telefono,boolean estado_Cli) {
		super();
		this.idUsuario = idUsu;
        this.idCliente = idCli;
        this.DNI = DNI;
        this.CUIL = CUIL;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.localidad = localidad;
        this.provincia = provincia;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.estado_Cli = estado_Cli;
		
	}
	
	public Usuario getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Usuario idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public String getDNI() {
		return DNI;
	}
	public void setDNI(String dNI) {
		DNI = dNI;
	}
	public String getCUIL() {
		return CUIL;
	}
	public void setCUIL(String cUIL) {
		CUIL = cUIL;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getNacionalidad() {
		return nacionalidad;
	}
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public boolean isEstado_Cli() {
		return estado_Cli;
	}
	public void setEstado_Cli(boolean estado_Cli) {
		this.estado_Cli = estado_Cli;
	}

	@Override
	public String toString() {
		return "Cliente [idUsuario=" + idUsuario + ", idCliente=" + idCliente + ", DNI=" + DNI + ", CUIL=" + CUIL
				+ ", nombre=" + nombre + ", apellido=" + apellido + ", sexo=" + sexo + ", nacionalidad=" + nacionalidad
				+ ", fechaNacimiento=" + fechaNacimiento + ", direccion=" + direccion + ", localidad=" + localidad
				+ ", provincia=" + provincia + ", correoElectronico=" + correoElectronico + ", telefono=" + telefono
				+ ", estado_Cli=" + estado_Cli + "]";
	}
	
	public static boolean validarCantidadCtasCliente(int IdCliente) throws SuperaLimiteCuentasException{
		DaoCuentas daoCuentas = new DaoCuentas();
		System.out.println("Cantidad cuentas: " + daoCuentas.validarCantidadCuentasCliente(IdCliente));
		if(daoCuentas.validarCantidadCuentasCliente(IdCliente) > 2) {
			SuperaLimiteCuentasException exc1 = new SuperaLimiteCuentasException();
			throw exc1;
		}
		System.out.println("Devuelvo true");
		return true;
	}
	
	
	
	
}
