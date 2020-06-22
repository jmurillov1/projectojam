package ec.edu.ups.coopjam.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import ec.edu.ups.coopjam.business.GestionUsuarios;
import ec.edu.ups.coopjam.model.Cliente;
import ec.edu.ups.coopjam.model.CuentaDeAhorro;
import ec.edu.ups.coopjam.model.SesionCliente;
import ec.edu.ups.coopjam.model.Transaccion;

/**
 * Esta clase implementa la logica que se utilizara en las diferentes interfaces
 * para poder utilizar las entidades o clases
 * 
 * @author ALEX
 * @version 1.0
 */
@ManagedBean
@SessionScoped
public class ClientesBean {
	// Atributos de la clase
	@Inject
	private GestionUsuarios gestionUsuarios;
	private Cliente cliente;
	private String numeroCuenta;
	private CuentaDeAhorro cuentaDeAhorro;
	private CuentaDeAhorro buscarCuentaDeAhorro;
	private String cedulaParametro;
	private Transaccion transaccion;
	private List<Cliente> lstClientes;
	private List<SesionCliente> lstSesionesCliente;
	private List<Transaccion> lstTransacciones;
	private String saldoCuenta;
	private Date fechaInicio;
	private Date fechaFinal;
	private String tipoTransaccion;  
	private String fechasInvalidas;

	/**
	 * Metodo que permite inicializar atributos y metodos al momento que se llama a
	 * esta clase
	 */
	@PostConstruct
	private void iniciar() {
		listarClientes(); 
		tipoTransaccion = "Todos";
		System.out.println(lstClientes.size());
		cuentaDeAhorro = new CuentaDeAhorro();
		cliente = new Cliente();
	}

	/**
	 * Metodo que permite obtener el atributo cliente
	 * 
	 * @return Atributo cliente de la clase
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * Metodo que permite asignar un valor al atributo cliente
	 * 
	 * @param cliente Variable asiganda al atributo cliente de la clase
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	/**
	 * Metodo que permte obtener el atrbuto gestionUsuarios de la clase
	 * 
	 * @return Atributo gestionUsuarios de la clase
	 */
	public GestionUsuarios getGestionUsuarios() {
		return gestionUsuarios;
	}

	/**
	 * Metodo que permite asignar un valor al atributo gestionUsuarios de la clase
	 * 
	 * @param gestionUsuarios Variable asiganda al atributo gestionUsuarios de la
	 *                        clase
	 */
	public void setGestionUsuarios(GestionUsuarios gestionUsuarios) {
		this.gestionUsuarios = gestionUsuarios;
	}

	/**
	 * Metodo que permite obtener el atributo numeroCuenta de la clase
	 * 
	 * @return Atributo numeroCuenta de la clase
	 */
	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	/**
	 * Metodo que permite asignar un valor al atributo numeroCuenta de la clase
	 * 
	 * @param numeroCuenta Variable asiganda al atributo numeroCuenta de la clase
	 */
	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	/**
	 * Metodo que permite obtener el atributo cuentaDeAhorro de la clase
	 * 
	 * @return Atributo cuentaDeAhorro de la clase
	 */
	public CuentaDeAhorro getCuentaDeAhorro() {
		return cuentaDeAhorro;
	}

	/**
	 * Metodo que permite asignar un valor al atributo cuentaDeAhorro de la clase
	 * 
	 * @param cuentaDeAhorro Variable asignada al atributo cuentaDeAhorro de la
	 *                       clase
	 */
	public void setCuentaDeAhorro(CuentaDeAhorro cuentaDeAhorro) {
		this.cuentaDeAhorro = cuentaDeAhorro;
	}

	/**
	 * Metodo que permite obtener el atributo buscarCuentaDeAhorro de la clase
	 * 
	 * @return Atributo buscarCuentaDeAhorro de la clase
	 */
	public CuentaDeAhorro getBuscarCuentaDeAhorro() {
		return buscarCuentaDeAhorro;
	}

	/**
	 * Metodo que permite asignar un valor al atributo buscarCuentaDeAhorro de la
	 * clase
	 * 
	 * @param buscarCuentaDeAhorro Variable asignada al atributo
	 *                             buscarCuentaDeAhorro de la clase
	 */
	public void setBuscarCuentaDeAhorro(CuentaDeAhorro buscarCuentaDeAhorro) {
		this.buscarCuentaDeAhorro = buscarCuentaDeAhorro;
	}

	/**
	 * Metodo que permite obtener el atributo cedulaParametro de la clase
	 * 
	 * @return Atributo cedulaParametro de la clase
	 */
	public String getCedulaParametro() {
		return cedulaParametro;
	}
 

	public String getFechasInvalidas() {
		return fechasInvalidas;
	}

	public void setFechasInvalidas(String fechasInvalidas) {
		this.fechasInvalidas = fechasInvalidas;
	}

	/**
	 * Metodo que permite asignar un valor al atributo cedulaParametro de la clase y
	 * a su vez buscar la cuenta de ahorros y transaccion de un cliente que tenga la
	 * cedula asignada al metodo
	 * 
	 * @param cedulaParametro Variable asignada al atributo cedulaParametro de la
	 *                        clase
	 */
	public void setCedulaParametro(String cedulaParametro) {
		this.cedulaParametro = cedulaParametro;
		if (cedulaParametro != null) {
			try {
				buscarCuentaDeAhorro = gestionUsuarios.buscarCuentaDeAhorroCliente(cedulaParametro);
				List<Transaccion> lista = gestionUsuarios.listadeTransacciones(cedulaParametro);
				transaccion = lista.get(lista.size() - 1);
				ultimosDias();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/***
	 * Metodo que permite obtener el atributo transaccion de la clase
	 * 
	 * @return Atributo transaccion de la clase
	 */
	public Transaccion getTransaccion() {
		return transaccion;
	}

	/**
	 * Metodo que permite asignar un valor al atributo transaccion de la clase
	 * 
	 * @param transaccion Variable asignada al atributo transaccion de la clase
	 */
	public void setTransaccion(Transaccion transaccion) {
		this.transaccion = transaccion;
	}

	/**
	 * Metodo que permite obtener el atributo de tipo lista lstClientes de la clase
	 * 
	 * @return Atributo de tipo lista lstClientes de la clase
	 */
	public List<Cliente> getLstClientes() {
		return lstClientes;
	}

	/**
	 * Metodo que permite asignar un valor al atributo de tipo lista lstClientes de
	 * la clase
	 * 
	 * @param lstClientes Variable asignada al atributo de tipo lista lstClientes de
	 *                    la clase
	 */
	public void setLstClientes(List<Cliente> lstClientes) {
		this.lstClientes = lstClientes;
	}

	/**
	 * Metodo que permite obtener el atributo de tipo lista lstSesionesClientes de
	 * la clase
	 * 
	 * @return Atributo de tipo lista lstSesionesClientes de la clase
	 */
	public List<SesionCliente> getLstSesionesCliente() {
		return lstSesionesCliente;
	}

	/**
	 * Metodo que permite asignar un valor al atributo de tipo lista
	 * lstSesionesClientes de la clase
	 * 
	 * @param lstSesionesCliente Variable asignada al atributo de tipo lista
	 *                           lstSesionesClientes de la clase
	 */
	public void setLstSesionesCliente(List<SesionCliente> lstSesionesCliente) {
		this.lstSesionesCliente = lstSesionesCliente;
	}

	public List<Transaccion> getLstTransacciones() {
		return lstTransacciones;
	}

	public void setLstTransacciones(List<Transaccion> lstTransacciones) {
		this.lstTransacciones = lstTransacciones;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public String getTipoTransaccion() {
		return tipoTransaccion;
	}

	public void setTipoTransaccion(String tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

	/**
	 * Metodo que permite crear un cliente
	 * 
	 * @return Nulo
	 */
	public String crearCliente() {
		try {
			gestionUsuarios.guardarCliente(cliente);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Metodo que permite validar la cedula de un cliente
	 * 
	 * @return Mensaje de confirmacion
	 */
	public String validarCedula() {
		if (cliente.getCedula() != null) {
			Cliente cli = gestionUsuarios.buscarCliente(cliente.getCedula());
			if (cli != null) {
				return "Este cliente ya se encuentra registrado";
			}
			try {
				boolean verificar = gestionUsuarios.validadorDeCedula(cliente.getCedula());
				if (verificar) {
					return "Cedula Valida";
				} else if (verificar == false) {
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
	 * Metodo que permite generar el numero de cuenta
	 * 
	 * @return Numero de cuenta que se ha generado
	 */
	public String generarNumeroCuenta() {
		this.numeroCuenta = gestionUsuarios.generarNumeroDeCuenta();
		return numeroCuenta;
	}

	public String getSaldoCuenta() {
		return saldoCuenta;
	}

	public void setSaldoCuenta(String saldoCuenta) {
		this.saldoCuenta = saldoCuenta;
	}

	/**
	 * Metodo que permite crear la cuenta, cliente y a su vez una transaccion
	 * 
	 * @return Pagina que se redirige
	 */
	public String crearCuenta() {
		try {
			cuentaDeAhorro.setNumeroCuentaDeAhorro(numeroCuenta);
			cuentaDeAhorro.setFechaDeRegistro(new Date());
			cuentaDeAhorro.setCliente(cliente);
			cuentaDeAhorro.setSaldoCuentaDeAhorro(Double.parseDouble(saldoCuenta));
			gestionUsuarios.guardarCuentaDeAhorros(cuentaDeAhorro);
			Transaccion transaccion = new Transaccion();
			transaccion.setFecha(new Date());
			transaccion.setMonto(cuentaDeAhorro.getSaldoCuentaDeAhorro());
			transaccion.setTipo("deposito");
			transaccion.setCliente(cliente); 
			transaccion.setSaldoCuenta(Double.parseDouble(saldoCuenta));
			gestionUsuarios.guardarTransaccion(transaccion);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "CrearCliente";
	}

	/**
	 * Metodo que me permite obtener una lista de los clientes y asignarlo a la
	 * variable lstClientes de la clase
	 */
	public void listarClientes() {
		lstClientes = gestionUsuarios.listaClientes();
	}

	/**
	 * Metodo que permite cambiar el formato a una fecha
	 * 
	 * @param fecha Fecha a la que se cambia el formato
	 * @return Fecha cambiada el formato
	 */
	public String obtenerFecha(Date fecha) {
		DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return hourdateFormat.format(fecha);
	}

	/**
	 * Metodo que permite obtener las sesiones de una cliente
	 * 
	 * @param cedula Cedula del cliente
	 * @return Lista de sesiones que tiene el cliente
	 */
	public List<SesionCliente> cargarSesiones(String cedula) {
		System.out.println(cedula);
		List<SesionCliente> lis = gestionUsuarios.obtenerSesionesCliente(cedulaParametro);
		if (lis != null) {
			lstSesionesCliente = lis;
			return lstSesionesCliente;
		}
		return null;
	}

	public String consultarTransacciones() {
		return "ConsultaTransacciones";
	}

	public void validarFechas() throws Exception {
		if (this.fechaInicio != null && this.fechaFinal != null) {
			/*
			 * System.out.println(fechaInicio.getClass()); DateFormat hourdateFormat = new
			 * SimpleDateFormat("dd/MM/yyyy"); String d =
			 * hourdateFormat.format(fechaInicio);
			 * System.out.println(buscarCuentaDeAhorro.getNumeroCuentaDeAhorro());
			 * System.out.println(d +"***"+fechaFinal);
			 */
			DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String inicioF = hourdateFormat.format(fechaInicio);
			String finalF = hourdateFormat.format(fechaFinal);
			List<Transaccion> listaTrans = gestionUsuarios.obtenerTransaccionesFechaHora(cedulaParametro, inicioF,
					finalF);
			lstTransacciones = listaTrans;
			System.out.println("H" + lstTransacciones.size());
			System.out.println(cedulaParametro);
			System.out.println(new Date());
		}
	}

	public void obtenerTransaccionesInicioFinal() {
		/*
		 * lstTransacciones =
		 * gestionUsuarios.obtenerTransaccionesFechaHora(buscarCuentaDeAhorro.getCliente
		 * ().getCedula(), fechaInicio, fechaFinal);
		 */
		System.out.println("Este es el tipo de transaccion : " + tipoTransaccion);

	}

	public void ultimosDias() {
		Calendar c = Calendar.getInstance();
		fechaFinal = c.getTime();
		c.add(Calendar.DATE, -30);
		fechaInicio = c.getTime();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String inicioF = hourdateFormat.format(fechaInicio);
		String finalF = hourdateFormat.format(fechaFinal);
		List<Transaccion> listaTrans = gestionUsuarios.obtenerTransaccionesFechaHora(cedulaParametro, inicioF, finalF);
		lstTransacciones = listaTrans;
		System.out.println(lstTransacciones.size());
		System.out.println(cedulaParametro);
	}

	public void validarFechas2() throws Exception { 
		System.out.println(tipoTransaccion); 
		
		if (this.fechaInicio != null && this.fechaFinal != null) { 
			
			if(errorFechas() == null) {  
				fechasInvalidas = errorFechas(); 
				DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String inicioF = hourdateFormat.format(fechaInicio);
				String finalF = hourdateFormat.format(fechaFinal);
				List<Transaccion> listaTrans = gestionUsuarios.obtenerTransaccionesFechaHora(cedulaParametro, inicioF,
						finalF);
				
				if(tipoTransaccion!=null) { 
					if (tipoTransaccion.equals("Todos")) {
						lstTransacciones = listaTrans;
					} else if (tipoTransaccion.equals("Depositos")) { 
						lstTransacciones = new ArrayList<Transaccion>();
						for (Transaccion transaccion : listaTrans) {
							if (transaccion.getTipo().equals("deposito")) { 
								lstTransacciones.add(transaccion);
							}
						}
					} else { 
						lstTransacciones = new ArrayList<Transaccion>();
						for (Transaccion transaccion : listaTrans) {
							if (transaccion.getTipo().equals("retiro")) {
								lstTransacciones.add(transaccion);
							}
						}
					}
				}
			}else { 
				fechasInvalidas = errorFechas(); 
				lstTransacciones.removeAll(lstTransacciones);
			}
			
			/*
			 * System.out.println("H"+lstTransacciones.size());
			 * System.out.println(cedulaParametro); System.out.println(new Date());
			 */ 
		}   
		
		System.out.println("LISTA DE TRANSACCION SIZE :   " + lstTransacciones.size());
	}  
	
	
	public String errorFechas() {   
		/*System.out.println(fechaInicio.after(fechaFinal));;
		System.out.println("Fecha inicio: "+this.fechaInicio); 
		System.out.println("Fecha final: "+this.fechaFinal); 
		if(fechaInicio.after(fechaFinal)) {   
			System.out.println("ENTROOOOO VALIDACION");
			fechasInvalidas = false; 
			return "No se puede consultar entre estas fechas";  
		}else { 
			fechasInvalidas = true;
		} 
		return "si";*/ 
        Date fechaInicioDate = this.fechaInicio;  //String a date
        Date fechaFinDate = this.fechaFinal;  //String a date

        System.out.println("Inicial: "+fechaInicioDate);
        System.out.println("Final: "+fechaFinDate);
        //comprueba si es que inicio esta después que fecha actual       
        if (fechaInicioDate.after(fechaFinDate)) {
           return "Fecha inicio mayor";
        }
        return null;
	}
	
	
}