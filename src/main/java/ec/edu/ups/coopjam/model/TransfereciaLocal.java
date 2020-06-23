package ec.edu.ups.coopjam.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
public class TransfereciaLocal implements Serializable{
	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codigoTransferenciaLocal; 
	private double monto; 
	@JoinColumn(name="numero_cuenta")
	private CuentaDeAhorro cuentaDeAhorroOrigen;   
	@JoinColumn(name="numero_cuenta")
	private CuentaDeAhorro cuentaDeAhorroDestino; 
	
	public int getCodigoTransferenciaLocal() {
		return codigoTransferenciaLocal;
	}
	public void setCodigoTransferenciaLocal(int codigoTransferenciaLocal) {
		this.codigoTransferenciaLocal = codigoTransferenciaLocal;
	}
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	public CuentaDeAhorro getCuentaDeAhorroOrigen() {
		return cuentaDeAhorroOrigen;
	}
	public void setCuentaDeAhorroOrigen(CuentaDeAhorro cuentaDeAhorroOrigen) {
		this.cuentaDeAhorroOrigen = cuentaDeAhorroOrigen;
	}
	public CuentaDeAhorro getCuentaDeAhorroDestino() {
		return cuentaDeAhorroDestino;
	}
	public void setCuentaDeAhorroDestino(CuentaDeAhorro cuentaDeAhorroDestino) {
		this.cuentaDeAhorroDestino = cuentaDeAhorroDestino;
	}
	
	
	
	
	
}
