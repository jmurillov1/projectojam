package ec.edu.ups.coopjam.view;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import ec.edu.ups.coopjam.business.GestionUsuarioLocal;
import ec.edu.ups.coopjam.model.Cliente;
import ec.edu.ups.coopjam.model.CuentaDeAhorro;
import ec.edu.ups.coopjam.model.Transaccion;

@ManagedBean
@ViewScoped
public class CrearCliente {
	@Inject
	private GestionUsuarioLocal gestionUsuarios;  
	private Cliente cliente;    
	private String numeroCuenta;
	private String saldoCuenta;  
	private CuentaDeAhorro cuentaDeAhorro;   
	private List<Cliente> lstClientes;
	
	@PostConstruct
	private void iniciar() {  
		cliente = new Cliente();  
		cuentaDeAhorro = new CuentaDeAhorro();  
	}
	public GestionUsuarioLocal getGestionUsuarios() {
		return gestionUsuarios;
	}
	public void setGestionUsuarios(GestionUsuarioLocal gestionUsuarios) {
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
	
	public List<Cliente> obtenerClientes(){  
		try {
			List<Cliente> clis = gestionUsuarios.listaClientes();  
			System.out.println(clis.size());
			return gestionUsuarios.listaClientes(); 
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
}
