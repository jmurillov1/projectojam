package ec.edu.ups.coopjam.view;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import ec.edu.ups.coopjam.model.Cliente;
import ec.edu.ups.coopjam.model.Empleado;

@ManagedBean
@ViewScoped
public class VerificarSesionBean implements Serializable {

	public void verificarSession() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			Cliente cliente = (Cliente) context.getExternalContext().getSessionMap().get("cliente");

			if (cliente == null) {
				context.getExternalContext().redirect("InicioClientes.xhtml");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void verificarSessionAdmin(){
	        try {
	            FacesContext context = FacesContext.getCurrentInstance();
				Empleado  empleado= (Empleado) context.getExternalContext().getSessionMap().get("empleado");
	            if (empleado == null) {
	                context.getExternalContext().redirect("InicioUsuarios.xhtml");
	            }
	            
	        } catch (Exception e) {
	            System.out.println(e.getMessage());

	        } 
	}
}
