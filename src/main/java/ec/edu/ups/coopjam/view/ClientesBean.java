package ec.edu.ups.coopjam.view;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import ec.edu.ups.coopjam.business.GestionUsuarios;
import ec.edu.ups.coopjam.model.Cliente;
import ec.edu.ups.coopjam.model.CuentaDeAhorro;

@ManagedBean
@ViewScoped
public class ClientesBean {

	@Inject
	private GestionUsuarios gestionUsuarios;
	private Cliente cliente;  
	private String numeroCuenta;
	private CuentaDeAhorro cuentaDeAhorro; 
	

	@PostConstruct
	private void iniciar() { 
		cuentaDeAhorro = new CuentaDeAhorro();  
		cliente = new Cliente();
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	public GestionUsuarios getGestionUsuarios() {
		return gestionUsuarios;
	}

	public void setGestionUsuarios(GestionUsuarios gestionUsuarios) {
		this.gestionUsuarios = gestionUsuarios;
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
			boolean verificar = gestionUsuarios.verificarCedula(cliente.getCedula());
			if(verificar) { 
				return "Cedula Valida";
			}else if(verificar==false) { 
				return "Cedula Incorrecta";
			}
		}  
		return " ";
		
	}

	public String generarNumeroCuenta() {
		this.numeroCuenta = gestionUsuarios.generarNumeroDeCuenta(); 
		return numeroCuenta;
	} 
	
	public String crearCuenta() {  
		cuentaDeAhorro.setNumeroCuentaDeAhorro(numeroCuenta); 
		cuentaDeAhorro.setFechaDeRegistro(new Date());
		cuentaDeAhorro.setCliente(cliente); 
		try {
			gestionUsuarios.guardarCuentaDeAhorros(cuentaDeAhorro);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	} 
	
	public String obtenerCuenta() {  
		CuentaDeAhorro cuenta = gestionUsuarios.buscarCuentaDeAhorroCliente("0105011399"); 
		System.out.println(cuenta.getNumeroCuentaDeAhorro());  
		System.out.println(cuenta.getSaldoCuentaDeAhorro()); 
		return null;
	}

}
