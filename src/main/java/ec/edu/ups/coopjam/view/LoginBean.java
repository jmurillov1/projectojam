package ec.edu.ups.coopjam.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import ec.edu.ups.coopjam.business.GestionEmpleadosON;
import ec.edu.ups.coopjam.model.Empleado;

@ManagedBean
@SessionScoped
public class LoginBean {
	@Inject
	private GestionEmpleadosON empleadoON;

	private String usuario;

	private String contrasena;
	
	
	public GestionEmpleadosON getEmpleadoON() {
		return empleadoON;
	}


	public void setEmpleadoON(GestionEmpleadosON empleadoON) {
		this.empleadoON = empleadoON;
	}


	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	public String getContrasena() {
		return contrasena;
	}


	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}



	public String validarUsuario() {
		Empleado emp = empleadoON.usuario(usuario, contrasena);
		if (emp != null) {
			return "PaginaCajero";
		}else{
			return "Usuario Incorrecto";
		}
	}

}
