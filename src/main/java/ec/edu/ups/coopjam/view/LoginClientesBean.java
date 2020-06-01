package ec.edu.ups.coopjam.view;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import ec.edu.ups.coopjam.business.GestionEmpleadosON;
import ec.edu.ups.coopjam.business.GestionUsuarios;
import ec.edu.ups.coopjam.model.Cliente;
import ec.edu.ups.coopjam.model.SesionCliente;

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
		List<Cliente> lstClis = gestionUsuarios.listaClientes();  
		System.out.println("PASO LA LISTAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		for(Cliente c : lstClis) {  
			System.out.println("ENTROOOOOOOOOOOO EN EL FORRRRRR");
			if(c.getUsuario().equalsIgnoreCase(usuario) && c.getClave().equalsIgnoreCase(contraseña)) { 
				System.out.println("ENTROOOOOOOOOOOO EN EL IFFFFFFFFFFFFFF CORRECTO");
				SesionCliente sesionCliente = new SesionCliente();
				sesionCliente.setCliente(c);  
				sesionCliente.setFechaSesion(new Date());
				sesionCliente.setEstado("Correcto");  
				gestionUsuarios.guardarSesion(sesionCliente);
				try {
					FacesContext contex = FacesContext.getCurrentInstance();
					contex.getExternalContext().redirect("PaginaPrincipalCliente.xhtml?faces-redirect=true&cedula="+c.getCedula());
				} catch (Exception e) {
				}
			}else if(c.getUsuario().equalsIgnoreCase(usuario)) {  
				System.out.println("ENTROOOOOOOOOOOO EN EL IFFFFFFFFFFFFFF MAL"); 
				SesionCliente sesionCliente2 = new SesionCliente();
				sesionCliente2.setCliente(c);  
				sesionCliente2.setFechaSesion(new Date());
				sesionCliente2.setEstado("Incorrecto");  
				gestionUsuarios.guardarSesion(sesionCliente2);
				return "InicioClientes";
			}
		} 
		return "InicioClientes";
	}
	
	
	
	
}
