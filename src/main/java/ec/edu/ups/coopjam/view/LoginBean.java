package ec.edu.ups.coopjam.view;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;


import ec.edu.ups.coopjam.business.GestionUsuarios;
import ec.edu.ups.coopjam.model.Cliente;
import ec.edu.ups.coopjam.model.CuentaDeAhorro;
import ec.edu.ups.coopjam.model.Empleado;
/**
 * Clase de tipo Bean para el manejo de JSF y archivos xhtml
 * @author Malki Yupanki
 * @version: 1.0
 */
@ManagedBean
@SessionScoped
public class LoginBean {
	@Inject
	private GestionUsuarios empleadoON;

	private String usuario;

	private String contrasena;

	
	@PostConstruct
	public void init() {
	}

	public GestionUsuarios getEmpleadoON() {
		return empleadoON;
	}

	public void setEmpleadoON(GestionUsuarios empleadoON) {
		this.empleadoON = empleadoON;
	}
	
	/**
	 * Metodo para obtener el usuario
	 * @return El usuario o valor digitado en la pagina xhtml
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * Metodo para asignar un usuario
	 * @param usuario El parametro usuario me permite asignar valor a mi variable usuario.
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	/**
	 * Metodo para obtener la contraseña
	 * @return La contraseña que se ingresa en la pagina xhtml
	 */
	public String getContrasena() {
		return contrasena;
	}
	
	/**
	 * Metodo para asignar una contraseña 
	 * @param contrasena El parametro contraseña me permite asignar un valor a mi variable contrasena
	 */
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	/**
	 * Metodo para validar rol de Empleado
	 * @return La pagina de acuerdo al rol del empleado
	 */
	public String validarUsuario() {
		Empleado emp;
		try {
			emp = empleadoON.usuario(usuario, contrasena);
			if (emp != null && emp.getRol().equalsIgnoreCase("Cajero")) {
				try {
					FacesContext contex = FacesContext.getCurrentInstance();
					contex.getExternalContext().redirect("PaginaCajero.xhtml");
				} catch (Exception e) {
				}
			} else if(emp != null && emp.getRol().equalsIgnoreCase("JefeCredito")) {
				try {
					FacesContext contex = FacesContext.getCurrentInstance();
					contex.getExternalContext().redirect("PaginaJefeCredito.xhtml");
				} catch (Exception e) {
				}
			}else if(emp != null && emp.getRol().equalsIgnoreCase("Admin")) {
				try {
					FacesContext contex = FacesContext.getCurrentInstance();
					contex.getExternalContext().redirect("Admin.xhtml");
				} catch (Exception e) {
				}	
			}
		} catch (Exception e) {
			return "InicioUsuarios";
		}
		return null;
	}
	

}
