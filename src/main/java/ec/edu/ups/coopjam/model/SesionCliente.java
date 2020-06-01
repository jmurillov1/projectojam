package ec.edu.ups.coopjam.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class SesionCliente implements Serializable{ 
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int codigoSesion;  
	private String estado;   
	private Date fechaSesion; 
	@OneToOne
	@JoinColumn(name="cedula_cliente")
	private Cliente cliente; 
	
	public int getCodigoSesion() {
		return codigoSesion;
	}
	public void setCodigoSesion(int codigoSesion) {
		this.codigoSesion = codigoSesion;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	} 
	
	public Date getFechaSesion() {
		return fechaSesion;
	}
	public void setFechaSesion(Date fechaSesion) {
		this.fechaSesion = fechaSesion;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	} 

	
	
	
}
