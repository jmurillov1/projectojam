package ec.edu.ups.coopjam.view;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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

	private boolean pasar;
	
	private Empleado cliente;
	
	@PostConstruct
	public void init() {
		cliente = new Empleado();
	}

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

	public boolean isPasar() {
		return pasar;
	}

	public void setPasar(boolean pasar) {
		this.pasar = pasar;
	}
	
	public Empleado getCliente() {
		return cliente;
	}

	public void setCliente(Empleado cliente) {
		this.cliente = cliente;
	}

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
	
	
	public String clienteBuscar() {
		if (cliente.getCedula() != null) {
			Empleado em = empleadoON.usuarioRegistrado(cliente.getCedula());
			String l = em.getNombre() +"     "+ em.getApellido();
			return l;
		}
		return " ";
	}
	
	public String valCedula() {
		System.out.println("*-------*"+cliente.getCedula());
		if (cliente.getCedula() != null) {
			Empleado usuarioRegistrado = empleadoON.usuarioRegistrado(cliente.getCedula());
			//cliente = usuarioRegistrado;
			if (usuarioRegistrado != null) {
				System.out.println("Registrado");
				String l =(String) (usuarioRegistrado.getNombre() + "    " + usuarioRegistrado.getApellido());
				return l;
			}
			try {
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return " ";	
	}

}
