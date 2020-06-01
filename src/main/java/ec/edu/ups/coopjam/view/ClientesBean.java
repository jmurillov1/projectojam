package ec.edu.ups.coopjam.view;

import java.util.Date;
import java.util.List;

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
	private CuentaDeAhorro buscarCuentaDeAhorro;
	private String cedulaParametro;
	private List<Cliente> lstClientes;
	

	@PostConstruct
	private void iniciar() {  
		listarClientes();
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
	
	
	
	public CuentaDeAhorro getBuscarCuentaDeAhorro() {
		return buscarCuentaDeAhorro;
	}

	public void setBuscarCuentaDeAhorro(CuentaDeAhorro buscarCuentaDeAhorro) {
		this.buscarCuentaDeAhorro = buscarCuentaDeAhorro;
	}

	public String getCedulaParametro() {
		return cedulaParametro;
	}

	public void setCedulaParametro(String cedulaParametro) {
		this.cedulaParametro = cedulaParametro; 
		if(cedulaParametro!=null) { 
			try { 
				buscarCuentaDeAhorro = gestionUsuarios.buscarCuentaDeAhorroCliente(cedulaParametro); 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}

	public List<Cliente> getLstClientes() {
		return lstClientes;
	}

	public void setLstClientes(List<Cliente> lstClientes) {
		this.lstClientes = lstClientes;
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
		return "CrearCliente";
	} 	
	
	public void listarClientes() {
		lstClientes = gestionUsuarios.listaClientes();
	}
}
