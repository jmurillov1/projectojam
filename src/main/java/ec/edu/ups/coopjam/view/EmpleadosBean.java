package ec.edu.ups.coopjam.view;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import ec.edu.ups.coopjam.business.GestionEmpleadosON;
import ec.edu.ups.coopjam.model.Empleado;
/**
 * Clase de tipo Bean para el manejo de JSF y archivos xhtml
 * @author Malki Yupanki
 * @version: 1.0
 */
@ManagedBean
@ViewScoped
public class EmpleadosBean {

	@Inject
	private GestionEmpleadosON empleadoON;

	private Empleado empleado;

	private boolean ced;
	
	private List<Empleado> listaEmpleados;
	
	private String tipoEmpleado;

	@PostConstruct
	public void init() {
		empleado = new Empleado();
		loadData();
	}

	public GestionEmpleadosON getEmpleadoON() {
		return empleadoON;
	}

	public void setEmpleadoON(GestionEmpleadosON empleadoON) {
		this.empleadoON = empleadoON;
	}
	
	/**
	 * Metodo para obtener un Empleado
	 * @return Un empleado para un registro en la Base de Datos
	 */
	public Empleado getEmpleado() {
		return empleado;
	}
	
	/**
	 * Metodo para asignar un Empleado
	 * @param empleado El parametro empleado me Permite asignar datos a un Empleado
	 */
	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
	
	/**
	 * Metodo para Obtener un Mensaje
	 * @return Si es TRUE o False
	 */
	public boolean isCed() {
		return ced;
	}
	
	/**
	 * Metodo para asignar un valor
	 * @param ced El parametro ced me permite asignar el volor booleano de TRUE o FALSE
	 */
	public void setCed(boolean ced) {
		this.ced = ced;
	}
	
	/**
	 * Metodo para obtener lista de Empleados
	 * @return Una lista de tipo Empleados
	 */
	public List<Empleado> getListaEmpleados() {
		return listaEmpleados;
	}
	
	/**
	 * Metodo para asignar una lista de Empleado
	 * @param listaEmpleados El parametro listaEmpleados me asigna los datos de los Empleados a mi lista
	 */
	public void setListaEmpleados(List<Empleado> listaEmpleados) {
		this.listaEmpleados = listaEmpleados;
	}
	
	
	/**
	 * Metodo para obtener un tipo de Empleado
	 * @return El Tipod de Empleado que se esta asignando en la pagina xhtml
	 */
	public String getTipoEmpleado() {
		return tipoEmpleado;
	}
	
	/**
	 * Metodo para asignar el tipo de Empleado
	 * @param tipoEmpleado El parametro tipoEmpleado me permite asignar el tipo de empleado seleccionado en la pagina xhtml
	 */
	public void setTipoEmpleado(String tipoEmpleado) {
		this.tipoEmpleado = tipoEmpleado;
	}
	
	/**
	 * Metodo para guardar datos del Empleado
	 * @return La paguina con la lista de los Empleados registrados
	 */
	public String guardarDatos() {

		System.out.println(this.empleado.getCedula() + "   " + this.empleado.getNombre() + tipoEmpleado);

		try {
			if (tipoEmpleado.equalsIgnoreCase("cajero")) {
				empleado.setRol("Cajero");
				empleadoON.guardarEmpleado(empleado);
			} else if (tipoEmpleado.equalsIgnoreCase("jefeCredito")) {
				empleado.setRol("JefeCredito");
				empleadoON.guardarEmpleado(empleado);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Lista-Empleados";

	}
	
	/**
	 * Metodo para validar un Empleado
	 * @return Mensaje si el Empleado esta registrado en la Base de Datos
	 */
	public String valCedula() {
		System.out.println("*-------*"+empleado.getCedula());
		if (empleado.getCedula() != null) {
			Empleado usuarioRegistrado = empleadoON.usuarioRegistrado(empleado.getCedula());
			if (usuarioRegistrado != null) {
				System.out.println("Registrado");
				return "Empleado REGISTRADO";
			}
			try {
				ced = empleadoON.validadorDeCedula(empleado.getCedula());
				System.out.println(ced);
				if (ced) {
					return "Cedula Valida";
				}else if(ced == false) {
					return "Cedula Incorrecta";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return " ";	
	}
	
	/**
	 * Metodo para asignar la lista de Empleados registrados en la Base de Datos
	 */
	public void loadData() {
		listaEmpleados = empleadoON.listadoEmpleados();
	}
	

}
