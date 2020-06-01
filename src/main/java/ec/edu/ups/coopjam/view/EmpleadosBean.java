package ec.edu.ups.coopjam.view;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import ec.edu.ups.coopjam.business.GestionEmpleadosON;
import ec.edu.ups.coopjam.model.Empleado;

@ManagedBean
@ViewScoped
public class EmpleadosBean {

	@Inject
	private GestionEmpleadosON empleadoON;

	private Empleado empleado;

	private boolean cajero;

	private boolean jefeCredito;

	private boolean ced;
	
	private List<Empleado> listaEmpleados;	

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

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public boolean isCajero() {
		return cajero;
	}

	public void setCajero(boolean cajero) {
		this.cajero = cajero;
	}

	public boolean isJefeCredito() {
		return jefeCredito;
	}

	public void setJefeCredito(boolean jefeCredito) {
		this.jefeCredito = jefeCredito;
	}

	public boolean isCed() {
		return ced;
	}

	public void setCed(boolean ced) {
		this.ced = ced;
	}
	
	
	public List<Empleado> getListaEmpleados() {
		return listaEmpleados;
	}

	public void setListaEmpleados(List<Empleado> listaEmpleados) {
		this.listaEmpleados = listaEmpleados;
	}
	
	public String guardarDatos() {

		System.out.println(this.empleado.getCedula() + "   " + this.empleado.getNombre() + cajero + jefeCredito);

		try {
			if (cajero == true) {
				empleado.setRol("Cajero");
				empleadoON.guardarEmpleado(empleado);
			} else if (jefeCredito == true) {
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
	
	public void loadData() {
		listaEmpleados = empleadoON.listadoEmpleados();
	}
	

}
