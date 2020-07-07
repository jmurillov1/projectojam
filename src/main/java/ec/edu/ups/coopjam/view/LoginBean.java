package ec.edu.ups.coopjam.view;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import ec.edu.ups.coopjam.business.GestionUsuarioLocal;
import ec.edu.ups.coopjam.business.GestionUsuarios;
import ec.edu.ups.coopjam.model.Cliente;
import ec.edu.ups.coopjam.model.Credito;
import ec.edu.ups.coopjam.model.CuentaDeAhorro;
import ec.edu.ups.coopjam.model.DetalleCredito;
import ec.edu.ups.coopjam.model.Empleado;
import ec.edu.ups.coopjam.model.SolicitudDeCredito;
/**
 * Clase de tipo Bean para el manejo de JSF y archivos xhtml
 * @author Malki Yupanki
 * @version: 1.0
 */
@ManagedBean
@SessionScoped
public class LoginBean {
	@Inject
	private GestionUsuarioLocal empleadoON;

	private String usuario;

	private String contrasena;
	
	private List<SolicitudDeCredito> solicitudes;

	private SolicitudDeCredito solicitudDeCredito;
	
	private boolean editable = false;
	
	private boolean editabledos = false;
	
	private String motivo;
	
	private Empleado empleado;

	
	@PostConstruct
	public void init() {
		empleado = new Empleado();
		loadDataSol();
	}

	public GestionUsuarioLocal getEmpleadoON() {
		return empleadoON;
	}

	public void setEmpleadoON(GestionUsuarioLocal empleadoON) {
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
	
	
	
	public List<SolicitudDeCredito> getSolicitudes() {
		return solicitudes;
	}

	public void setSolicitudes(List<SolicitudDeCredito> solicitudes) {
		this.solicitudes = solicitudes;
	}

	public SolicitudDeCredito getSolicitudDeCredito() {
		return solicitudDeCredito;
	}

	public void setSolicitudDeCredito(SolicitudDeCredito solicitudDeCredito) {
		this.solicitudDeCredito = solicitudDeCredito;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean isEditabledos() {
		return editabledos;
	}

	public void setEditabledos(boolean editabledos) {
		this.editabledos = editabledos;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	/**
	 * Metodo para validar rol de Empleado
	 * @return La pagina de acuerdo al rol del empleado
	 */
	public String validarUsuario() {
		Empleado emp;
		try {
			emp = empleadoON.usuario(usuario, contrasena);
			System.out.println("***************"+emp.getNombre());
			empleado = emp;
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
	
	public void loadDataSol() {
		solicitudes = empleadoON.listadoSolicitudDeCreditos();
	}
	
	public String cargarSol(int cod) {
		editable = true;
		System.out.println("**********/****/--"+cod+editable);
		
		for (SolicitudDeCredito sol : solicitudes) {
			if (sol.getCodigoCredito() == cod) {
				solicitudDeCredito = sol;
			}
		}
		return null;
	}
	public String aprobar(int cod) {
		System.out.println("//////-/////////-/////"+empleado.getNombre());
		for (SolicitudDeCredito sol : solicitudes) {
			if (sol.getCodigoCredito() == cod && sol.getEstadoCredito().equalsIgnoreCase("Solicitando") ) {
				
				Credito credito = new Credito();
				credito.setFechaRegistro(new Date());
				credito.setInteres(12);
				credito.setMonto(sol.getMontoCredito());
				credito.setJefeC(empleado);
				credito.setEstado("Pendiente");
				credito.setSolicitud(sol);
				List<DetalleCredito> li = empleadoON.crearTablaAmortizacion(Integer.parseInt(sol.getMesesCredito()), sol.getMontoCredito(), 12.00);
				System.out.println(li.toString());
				credito.setDetalles(li);
				empleadoON.guardarCredito(credito);
				empleadoON.aprobarCredito(credito, sol.getClienteCredito());
				System.out.println(credito);
				solicitudDeCredito.setEstadoCredito("Aprobado");
				empleadoON.actualizarSolicitudCredito(solicitudDeCredito);
				CuentaDeAhorro ccv = empleadoON.buscarCuentaDeAhorroCliente(sol.getClienteCredito().getCedula());
				ccv.setSaldoCuentaDeAhorro(ccv.getSaldoCuentaDeAhorro()+sol.getMontoCredito());
				empleadoON.actualizarCuentaDeAhorro(ccv);
				solicitudDeCredito = new SolicitudDeCredito();
				editable = false;
				editabledos = false;
				
			}
		}
		
		
		return "PaginaJefeCredito";
	}
	public String rechazar() {
		solicitudDeCredito.setEstadoCredito("Rechazado");
		
		empleadoON.actualizarSolicitudCredito(solicitudDeCredito);
		System.out.println(motivo);
		//System.out.println(solicitudDeCredito.getCodigoCredito());
		solicitudDeCredito = new SolicitudDeCredito();
		
		editable = false;
		editabledos = false;
		return "PaginaJefeCredito";
	}
	
	public void cambio() {
		editable = false;
		editabledos = true;
	}
	
	public void ver(String tipo) throws IOException {
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

		File file = File.createTempFile("archivoTemp", ".pdf");
		try (FileOutputStream fos = new FileOutputStream(file)) {
			if (tipo.equalsIgnoreCase("cedula")) {
				fos.write(solicitudDeCredito.getArCedula());
			}else if (tipo.equalsIgnoreCase("planilla")) {
				fos.write(solicitudDeCredito.getArPlanillaServicios());
			}else if (tipo.equalsIgnoreCase("rol")) {
				fos.write(solicitudDeCredito.getArRolDePagos());
			}
			
		}
		BufferedInputStream input = null;
		BufferedOutputStream output = null;

		try {
			// Open file.
			input = new BufferedInputStream(new FileInputStream(file), 10240);

			// Init servlet response.
			response.reset();
			response.setHeader("Content-Type", "application/pdf");
			response.setHeader("Content-Length", String.valueOf(file.length()));
			response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
			output = new BufferedOutputStream(response.getOutputStream(), 10240);

			// Write file contents to response.
			byte[] buffer = new byte [10240];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}

			// Finalize task.
			output.flush();
		} finally {

		}

		// Inform JSF that it doesn't need to handle response.
		// This is very important, otherwise you will get the following exception in the
		// logs:
		// java.lang.IllegalStateException: Cannot forward after response has been
		// committed.
		facesContext.responseComplete();
	}

}
