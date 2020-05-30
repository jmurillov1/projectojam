package ec.edu.ups.coopjam.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import ec.edu.ups.coopjam.business.GestionUsuarios;
import ec.edu.ups.coopjam.model.Cliente;

@ManagedBean 
@ViewScoped
public class ClientesBean { 
	
	@Inject 
	private GestionUsuarios gestionUsuarios;
	private Cliente cliente;

	
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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
	
	
	
	
	

}
