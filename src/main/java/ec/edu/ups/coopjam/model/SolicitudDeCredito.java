package ec.edu.ups.coopjam.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class SolicitudDeCredito {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codigoCredito; 
	@OneToOne
	@JoinColumn(name="cedula_cliente")
	private Cliente clienteCredito; 
	private String propositoCredito; 
	private double montoCredito; 
	private String tiempoEmpleo; 
	private String estadoCivil; 
	private double avaluoDeVivienda; 
	private String activo; 
	private String tipoVivienda; 
	private String tipoEmpleo; 
	private String trabajadorExtranjero; 
	private String estadoCredito;  
	private String cedulaCliente; 
	private String plantillaServicios;
	private String rolDePagos; 
	
	public int getCodigoCredito() {
		return codigoCredito;
	}
	public void setCodigoCredito(int codigoCredito) {
		this.codigoCredito = codigoCredito;
	}
	public Cliente getClienteCredito() {
		return clienteCredito;
	}
	public void setClienteCredito(Cliente clienteCredito) {
		this.clienteCredito = clienteCredito;
	}
	public String getPropositoCredito() {
		return propositoCredito;
	}
	public void setPropositoCredito(String propositoCredito) {
		this.propositoCredito = propositoCredito;
	}
	public double getMontoCredito() {
		return montoCredito;
	}
	public void setMontoCredito(double montoCredito) {
		this.montoCredito = montoCredito;
	}
	public String getTiempoEmpleo() {
		return tiempoEmpleo;
	}
	public void setTiempoEmpleo(String tiempoEmpleo) {
		this.tiempoEmpleo = tiempoEmpleo;
	}
	public String getEstadoCivil() {
		return estadoCivil;
	}
	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	public double getAvaluoDeVivienda() {
		return avaluoDeVivienda;
	}
	public void setAvaluoDeVivienda(double avaluoDeVivienda) {
		this.avaluoDeVivienda = avaluoDeVivienda;
	}
	public String getActivo() {
		return activo;
	}
	public void setActivo(String activo) {
		this.activo = activo;
	}
	public String getTipoVivienda() {
		return tipoVivienda;
	}
	public void setTipoVivienda(String tipoVivienda) {
		this.tipoVivienda = tipoVivienda;
	}
	public String getTipoEmpleo() {
		return tipoEmpleo;
	}
	public void setTipoEmpleo(String tipoEmpleo) {
		this.tipoEmpleo = tipoEmpleo;
	}
	public String getTrabajadorExtranjero() {
		return trabajadorExtranjero;
	}
	public void setTrabajadorExtranjero(String trabajadorExtranjero) {
		this.trabajadorExtranjero = trabajadorExtranjero;
	}
	public String getEstadoCredito() {
		return estadoCredito;
	}
	public void setEstadoCredito(String estadoCredito) {
		this.estadoCredito = estadoCredito;
	}
	public String getCedulaCliente() {
		return cedulaCliente;
	}
	public void setCedulaCliente(String cedulaCliente) {
		this.cedulaCliente = cedulaCliente;
	}
	public String getPlantillaServicios() {
		return plantillaServicios;
	}
	public void setPlantillaServicios(String plantillaServicios) {
		this.plantillaServicios = plantillaServicios;
	}
	public String getRolDePagos() {
		return rolDePagos;
	}
	public void setRolDePagos(String rolDePagos) {
		this.rolDePagos = rolDePagos;
	} 
	
	
	
	
	
	
}
