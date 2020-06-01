package ec.edu.ups.coopjam.view;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import ec.edu.ups.coopjam.business.GestionEmpleadosON;
import ec.edu.ups.coopjam.business.GestionUsuarios;
import ec.edu.ups.coopjam.model.Cliente;

@ManagedBean 
@SessionScoped
public class LoginClientesBean {
	@Inject 
	private GestionUsuarios gestionUsuarios;  
	private Cliente cliente;  
	private String usuario; 
	private String contraseña;
	
	@PostConstruct 
	public void init() {
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
	
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public 	String validarCliente() { 
		Cliente cliente = gestionUsuarios.buscarClienteUsuarioContraseña(usuario, contraseña);
		if(cliente != null) { 
			try {
				FacesContext contex = FacesContext.getCurrentInstance();
				contex.getExternalContext().redirect("PaginaPrincipalCliente.xhtml");
			} catch (Exception e) {
			}
		}
		
		return "InicioClientes";
	}
	
	
	
	
}
