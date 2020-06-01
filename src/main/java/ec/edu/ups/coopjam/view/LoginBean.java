package ec.edu.ups.coopjam.view;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import ec.edu.ups.coopjam.business.GestionEmpleadosON;
import ec.edu.ups.coopjam.business.GestionUsuarios;
import ec.edu.ups.coopjam.model.Cliente;
import ec.edu.ups.coopjam.model.CuentaDeAhorro;
import ec.edu.ups.coopjam.model.Empleado;

@ManagedBean
@SessionScoped
public class LoginBean {
	@Inject
	private GestionEmpleadosON empleadoON;
	
	@Inject
	private GestionUsuarios clienteON;

	private String usuario;

	private String contrasena;

	private Double monto;
	
	private Cliente cliente;
	
	@PostConstruct
	public void init() {
		cliente = new Cliente();
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
	
	
	
	public GestionUsuarios getClienteON() {
		return clienteON;
	}

	public void setClienteON(GestionUsuarios clienteON) {
		this.clienteON = clienteON;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
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
	
	
	public String valCedula() {
		System.out.println("*-------*"+cliente.getCedula());
		if (cliente.getCedula() != null) {
			Cliente usuarioRegistrado = clienteON.buscarCliente(cliente.getCedula());
			//cliente = usuarioRegistrado;
			if (usuarioRegistrado != null) {
				System.out.println("Registrado");
				String l =(String) (usuarioRegistrado.getNombre() + "    " + usuarioRegistrado.getApellido());
				return l;
			}
		}
		return " ";
	}
	
	public String valMonto() {
		System.out.println("*-------*"+cliente.getCedula());
		if (cliente.getCedula() != null) {
			Cliente usuarioRegistrado = clienteON.buscarCliente(cliente.getCedula());
			//cliente = usuarioRegistrado;
			if (usuarioRegistrado != null) {
				System.out.println("Registrado");
				//CuentaDeAhorro = clienteON.buscarCuentaDeAhorro()
				String l =(String) (usuarioRegistrado.getNombre() + "    " + usuarioRegistrado.getApellido());
				return l;
			}
		}
		return " ";
	}

}
