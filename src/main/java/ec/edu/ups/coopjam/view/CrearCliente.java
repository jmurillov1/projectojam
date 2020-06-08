package ec.edu.ups.coopjam.view;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import ec.edu.ups.coopjam.business.GestionUsuarios;
import ec.edu.ups.coopjam.model.Cliente;
import ec.edu.ups.coopjam.model.CuentaDeAhorro;
import ec.edu.ups.coopjam.model.Transaccion;

@ManagedBean
@SessionScoped
public class CrearCliente {
	@Inject
	private GestionUsuarios gestionUsuarios;  
	private Cliente cliente;    
	private String numeroCuenta;
	private String saldoCuenta;  
	private CuentaDeAhorro cuentaDeAhorro;  
	
	@PostConstruct
	private void iniciar() {  
		cliente = new Cliente();  
	}
	public GestionUsuarios getGestionUsuarios() {
		return gestionUsuarios;
	}
	public void setGestionUsuarios(GestionUsuarios gestionUsuarios) {
		this.gestionUsuarios = gestionUsuarios;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}  
	public String getSaldoCuenta() {
		return saldoCuenta;
	}
	public void setSaldoCuenta(String saldoCuenta) {
		this.saldoCuenta = saldoCuenta;
	}
	public String getNumeroCuenta() {
		return numeroCuenta;
	}
	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}
	public CuentaDeAhorro getCuentaDeAhorro() {
		return cuentaDeAhorro;
	}
	public void setCuentaDeAhorro(CuentaDeAhorro cuentaDeAhorro) {
		this.cuentaDeAhorro = cuentaDeAhorro;
	}
	public String crearCliente() {
		try {
			gestionUsuarios.guardarCliente(cliente);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	} 
	
	public String validarCedula() {
		if (cliente.getCedula() != null) {
			Cliente cli = gestionUsuarios.buscarCliente(cliente.getCedula());
			if(cli!=null) {  
				return "Este cliente ya se encuentra registrado";
			} 
			try {
				boolean verificar = gestionUsuarios.validadorDeCedula(cliente.getCedula()); 
				if(verificar) { 
					return "Cedula Valida";
				}else if(verificar==false) { 
					return "Cedula Incorrecta";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}  
		return " ";
		
	} 
	
	public String generarNumeroCuenta() {
		this.numeroCuenta = gestionUsuarios.generarNumeroDeCuenta(); 
		return numeroCuenta;
	}  
	
	
	public String crearCuenta() {  
		try {  
			cuentaDeAhorro.setNumeroCuentaDeAhorro(numeroCuenta); 
			cuentaDeAhorro.setFechaDeRegistro(new Date());
			cuentaDeAhorro.setCliente(cliente);  
			cuentaDeAhorro.setSaldoCuentaDeAhorro(Double.parseDouble(saldoCuenta));
			gestionUsuarios.guardarCuentaDeAhorros(cuentaDeAhorro); 
			Transaccion transaccion = new Transaccion();  
			transaccion.setFecha(new Date()); 
			transaccion.setMonto(cuentaDeAhorro.getSaldoCuentaDeAhorro()); 
			transaccion.setTipo("deposito");
			transaccion.setCliente(cliente); 
			gestionUsuarios.guardarTransaccion(transaccion);  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "CrearCliente";
	} 	
	
}