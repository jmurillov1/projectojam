package ec.edu.ups.coopjam.business;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.management.remote.NotificationResult;
import javax.persistence.NoResultException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

import au.com.bytecode.opencsv.CSVWriter;
import dnl.utils.text.table.TextTable;
import ec.edu.ups.coopjam.data.ClienteDAO;
import ec.edu.ups.coopjam.data.CreditoDAO;
import ec.edu.ups.coopjam.data.CuentaDeAhorroDAO;
import ec.edu.ups.coopjam.data.DetalleCreditoDAO;
import ec.edu.ups.coopjam.data.EmpleadoDAO;
import ec.edu.ups.coopjam.data.SesionClienteDAO;
import ec.edu.ups.coopjam.data.SolicitudDeCreditoDAO;
import ec.edu.ups.coopjam.data.TransaccionDAO;
import ec.edu.ups.coopjam.data.TransferenciaLocalDAO;
import ec.edu.ups.coopjam.model.Cliente;
import ec.edu.ups.coopjam.model.Credito;
import ec.edu.ups.coopjam.model.CuentaDeAhorro;
import ec.edu.ups.coopjam.model.DetalleCredito;
import ec.edu.ups.coopjam.model.Empleado;
import ec.edu.ups.coopjam.model.SesionCliente;
import ec.edu.ups.coopjam.model.SolicitudDeCredito;
import ec.edu.ups.coopjam.model.Transaccion;
import ec.edu.ups.coopjam.model.TransfereciaLocal;

/**
 * Esta clase me permite hacer diferentes validaciones o metodos necesarios
 * antes de poder realizar las diferentes funciones basicas en la base de datos
 * 
 * @author ALEX
 * @version 1.0
 */
@Stateless
public class GestionUsuarios implements GestionUsuarioLocal {
	@Inject
	private ClienteDAO clienteDAO;
	@Inject
	private CuentaDeAhorroDAO cuentaDeAhorroDAO;
	@Inject
	private SesionClienteDAO sesionClienteDAO;
	@Inject
	private TransaccionDAO transaccionDAO;
	@Inject
	private EmpleadoDAO empleadoDAO;
	@Inject
	private TransferenciaLocalDAO transferenciaLocalDAO;
	@Inject
	private SolicitudDeCreditoDAO solicitudDeCreditoDAO;
	@Inject
	private CreditoDAO creditoDAO;
	@Inject
	private DetalleCreditoDAO detalleCreditoDAO;

	/**
	 * Metodo que permite la validacion de una cedula correcta
	 * 
	 * @param ced Cedula que se valida
	 * @return Es correcta o no la cedula
	 */
	/*
	 * public boolean verificarCedula(String ced) { int longitud = 0; char digitoN;
	 * int ultimo = 0; int suma = 0; int digitoVerificador = 0; longitud =
	 * ced.length();
	 * 
	 * for (int i = 0; i < longitud - 1; i++) { digitoN = ced.charAt(i); int
	 * digitoAscii = (int) ced.charAt(i); int resultado = 0;
	 * 
	 * switch (digitoAscii) { case 48: digitoN = 0; break; case 49: digitoN = 1;
	 * break; case 50: digitoN = 2; break; case 51: digitoN = 3; break; case 52:
	 * digitoN = 4; break; case 53: digitoN = 5; break; case 54: digitoN = 6; break;
	 * case 55: digitoN = 7; break; case 56: digitoN = 8; break; case 57: digitoN =
	 * 9; break; } if (i % 2 == 0) { resultado = digitoN * 2; if (resultado >= 10) {
	 * resultado -= 9;
	 * 
	 * } } else { resultado = digitoN * 1; } suma += resultado; } digitoVerificador
	 * = (((suma / 10) + 1) * 10) - suma; if (digitoVerificador == 10) {
	 * digitoVerificador = 0; } ultimo = (int) ced.charAt(9);
	 * 
	 * switch (ultimo) { case 48: ultimo = 0; break; case 49: ultimo = 1; break;
	 * case 50: ultimo = 2; break; case 51: ultimo = 3; break; case 52: ultimo = 4;
	 * break; case 53: ultimo = 5; break; case 54: ultimo = 6; break; case 55:
	 * ultimo = 7; break; case 56: ultimo = 8; break; case 57: ultimo = 9; break; }
	 * if (ultimo == digitoVerificador) { return true;
	 * 
	 * } else { return false; } }
	 */

	/**
	 * Metodo que permite generar una numero de cuenta automatico
	 * 
	 * @return Numero de cuenta generado
	 */
	public String generarNumeroDeCuenta() {
		int numeroInicio = 4040;
		List<CuentaDeAhorro> listaCuentas = listaCuentaDeAhorros();
		int numero = listaCuentas.size() + 1;
		String resultado = String.format("%08d", numero);
		String resultadoFinal = String.valueOf(numeroInicio) + resultado;
		return resultadoFinal;
	}

	/**
	 * Metodo que permite generar un usuario aletorio
	 * 
	 * @param cedula   Cedula del usuario
	 * @param nombre   Nombre del usario
	 * @param apellido Apellido del usuario
	 * @return Usuario que se ha creado
	 */
	public String getUsuario(String cedula, String nombre, String apellido) {
		System.out.println(cedula);
		System.out.println(nombre);
		System.out.println(apellido);
		String ud = cedula.substring(cedula.length() - 1);
		String pln = nombre.substring(0, 1);
		int it = 0;
		for (int i = 0; i < apellido.length(); i++) {
			if (apellido.charAt(i) == 32) {
				it = i;
			}
		}
		String a = "";
		if (it == 0) {
			a = apellido.substring(0, apellido.length());
		} else {
			a = apellido.substring(0, it);
		}
		return pln.toLowerCase() + a.toLowerCase() + ud;
	}

	/**
	 * Metodo que permite la creacion de una contraseña aleatoria
	 * 
	 * @return Contraseña aleatoria
	 */
	public String getContraseña() {
		String simbolos = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefjhijklmnopqrstuvwxyz0123456789!#$%&()*+,-./:;<=>?@_";

		int tam = simbolos.length() - 1;
		System.out.println(tam);
		String clave = "";
		for (int i = 0; i < 10; i++) {
			int v = (int) Math.floor(Math.random() * tam + 1);
			clave += simbolos.charAt(v);
		}

		return clave;
	}

	/**
	 * Metodo que permite el envio de un correo
	 * 
	 * @param destinatario Destinario que se envia el correo
	 * @param asunto       Asunto del correo
	 * @param cuerpo       Cuerpo del correo
	 */
	public void enviarCorreo(String destinatario, String asunto, String cuerpo) {
		Properties propiedad = new Properties();
		propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
		propiedad.setProperty("mail.smtp.starttls.enable", "true");
		propiedad.setProperty("mail.smtp.port", "587");

		Session sesion = Session.getDefaultInstance(propiedad);
		String correoEnvia = "cooperativajam@gmail.com";
		String contrasena = "ZJRIcfjy1719";

		MimeMessage mail = new MimeMessage(sesion);
		try {
			mail.setFrom("Cooperativa JAM <" + correoEnvia + ">");
			mail.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
			mail.setSubject(asunto);
			mail.setText(cuerpo);

			Transport transportar = sesion.getTransport("smtp");
			transportar.connect(correoEnvia, contrasena);
			transportar.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
		} catch (AddressException ex) {
			System.out.println(ex.getMessage());
		} catch (MessagingException ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * Metodo que permite cambiar el formato de la fecha
	 * 
	 * @return Fecha con nuevo formato
	 */
	public String fecha() {
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return hourdateFormat.format(date);
	}

	/**
	 * Metodo que permite cambiar el formato de la fecha
	 * 
	 * @param fecha Fecha que se cambiara el formato
	 * @return
	 */
	public String obtenerFecha(Date fecha) {
		DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return hourdateFormat.format(fecha);
	}

	/**
	 * Metodo que me permite guardar el cliente en la base de datos
	 * 
	 * @param c Cliente que se guarda en la base de datos
	 */
	public void guardarCliente(Cliente c) {
		clienteDAO.insert(c);

	}

	/**
	 * Metodo que permite la busqueda de un cliente
	 * 
	 * @param cedulaCliente Cedula del cliente que se busca
	 * @return Cliente obtenido de la busqueda
	 */
	public Cliente buscarCliente(String cedulaCliente) {
		Cliente cliente = clienteDAO.read(cedulaCliente);
		return cliente;
	}

	/**
	 * Metodo que permite la busqueda del cliente en base a su usuario y contraseña
	 * 
	 * @param usuario    Usuario del cliente
	 * @param contraseña Contraseña del cliente
	 * @return Cliente obtenido de la busqueda
	 */
	public Cliente buscarClienteUsuarioContraseña(String usuario, String contraseña) {
		try {
			return clienteDAO.obtenerClienteUsuarioContraseña(usuario, contraseña);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Metodo que permite eliminar un cliente
	 * 
	 * @param cedulaCliente Cedula del cliente que se elimina
	 */
	public void eliminarCliente(String cedulaCliente) {
		clienteDAO.delete(cedulaCliente);
	}

	/**
	 * Metodo que permite actualizar un cliente
	 * 
	 * @param cliente Cliente que se actualiza
	 */
	public void actualizarCliente(Cliente cliente) {
		clienteDAO.update(cliente);
	}

	/**
	 * Metodo que permite listar los clientes
	 * 
	 * @return Lista de todos los clientes
	 */
	public List<Cliente> listaClientes() {
		List<Cliente> clientes = clienteDAO.getClientes();
		return clientes;
	}

	/**
	 * Metodo que permite guardar una cuenta de ahorro
	 * 
	 * @param c Cuenta de ahorro que se guarda
	 */
	public void guardarCuentaDeAhorros(CuentaDeAhorro c) {
		Cliente cliente = clienteDAO.read(c.getCliente().getCedula());
		if (cliente == null) {
			Cliente cli = c.getCliente();
			String usuario = getUsuario(cli.getCedula(), cli.getNombre(), cli.getApellido());
			String contraseña = getContraseña();
			cli.setUsuario(usuario);
			cli.setClave(contraseña);
			c.setCliente(cli);
			String destinatario = cli.getCorreo(); // A quien le quieres escribir.

			String asunto = "CREACION DE USUARIO";
			String cuerpo = "JAMVirtual                                               SISTEMA TRANSACCIONAL\n"
					+ "------------------------------------------------------------------------------\n"
					+ "              Estimado(a): " + cli.getNombre().toUpperCase() + " "
					+ cli.getApellido().toUpperCase() + "\n"
					+ "------------------------------------------------------------------------------\n"
					+ "COOPERATIVA JAM le informa que el usuario ha sido habilitado exitosamente.    \n"
					+ "                                                                              \n"
					+ "                       Su usuario es : " + usuario + "                          \n"
					+ "                   	Su clave de acceso es:   " + contraseña + "               \n"
					+ "                       Fecha: " + fecha() + "                                     \n"
					+ "                                                                              \n"
					+ "------------------------------------------------------------------------------\n";
			enviarCorreo(destinatario, asunto, cuerpo);
			cuentaDeAhorroDAO.insert(c);
		}

	}

	/**
	 * Metodo que permite buscar una cuenta de ahorros
	 * 
	 * @param numeroCuentaDeAhorro Numero de la cuenta de ahorros que se desea
	 *                             buscar
	 * @return Cuenta de ahorros que se obtiende de la busqueda
	 */
	public CuentaDeAhorro buscarCuentaDeAhorro(String numeroCuentaDeAhorro) {
		CuentaDeAhorro cuentaDeAhorro = cuentaDeAhorroDAO.read(numeroCuentaDeAhorro);
		return cuentaDeAhorro;
	}

	/**
	 * Metodo que me permite buscar una cuenta de ahorros
	 * 
	 * @param cedulaCliente Cedula del cliente de la cuenta de ahorros
	 * @return Cuenta de ahorro obtenida de la busqueda
	 */
	public CuentaDeAhorro buscarCuentaDeAhorroCliente(String cedulaCliente) {
		CuentaDeAhorro cuentaDeAhorro = cuentaDeAhorroDAO.getCuentaCedulaCliente(cedulaCliente);
		return cuentaDeAhorro;

	}

	/**
	 * Metodo que me permite eliminar una cuenta de ahorros
	 * 
	 * @param numeroCuentaDeAhorro Numero de la cuenta de ahorros que se desea
	 *                             eliminar
	 */
	public void eliminarCuentaDeAhorro(String numeroCuentaDeAhorro) {
		cuentaDeAhorroDAO.delete(numeroCuentaDeAhorro);
	}

	/**
	 * Metodo que permite actualizar una cuenta de ahorros
	 * 
	 * @param cuentaDeAhorro Cuenta de Ahorros que se desea actualizar
	 */
	public void actualizarCuentaDeAhorro(CuentaDeAhorro cuentaDeAhorro) {
		cuentaDeAhorroDAO.update(cuentaDeAhorro);
	}

	/**
	 * Metodo que me permite listar las cuentas de ahorros
	 * 
	 * @return Lista de todas las cuentas de ahorros
	 */
	public List<CuentaDeAhorro> listaCuentaDeAhorros() {
		List<CuentaDeAhorro> clientes = cuentaDeAhorroDAO.getCuentaDeAhorros();
		return clientes;
	}

	/**
	 * Metodo que permite guardar la sesion y enviar un correo al usuario que se le
	 * ha asignado esa sesion
	 * 
	 * @param sesionCliente Sesion que se guarda
	 */
	public void guardarSesion(SesionCliente sesionCliente) {
		Cliente cli = sesionCliente.getCliente();
		String destinatario = cli.getCorreo();
		if (sesionCliente.getEstado().equalsIgnoreCase("Incorrecto")) {
			// A quien le quieres escribir.

			String asunto = "INICIO DE SESION FALLIDA";
			String cuerpo = "JAMVirtual SISTEMA TRANSACCIONAL\n"
					+ "------------------------------------------------------------------------------\n"
					+ "              Estimado(a): " + cli.getNombre().toUpperCase() + " "
					+ cli.getApellido().toUpperCase() + "\n"
					+ "------------------------------------------------------------------------------\n"
					+ "COOPERATIVA JAM le informa que el acceso a su cuenta ha sido fallida.    \n"
					+ "                       Fecha: " + obtenerFecha(sesionCliente.getFechaSesion())
					+ "                                     \n"
					+ "                                                                              \n"
					+ "------------------------------------------------------------------------------\n";
			enviarCorreo(destinatario, asunto, cuerpo);
		} else {
			// A quien le quieres escribir.

			String asunto = "INICIO DE SESION CORRECTA";
			String cuerpo = "JAMVirtual SISTEMA TRANSACCIONAL\n"
					+ "------------------------------------------------------------------------------\n"
					+ "              Estimado(a): " + cli.getNombre().toUpperCase() + " "
					+ cli.getApellido().toUpperCase() + "\n"
					+ "------------------------------------------------------------------------------\n"
					+ "COOPERATIVA JAM le informa que el acceso a su cuenta ha sido correcta.    \n"
					+ "                       Fecha: " + obtenerFecha(sesionCliente.getFechaSesion())
					+ "                                     \n"
					+ "                                                                              \n"
					+ "------------------------------------------------------------------------------\n";
			enviarCorreo(destinatario, asunto, cuerpo);

		}

		sesionClienteDAO.insert(sesionCliente);

	}

	/**
	 * Metodo que permite buscar una Sesion
	 * 
	 * @param codigoSesionCliente Codigo de la sesion que se desea buscar
	 * @return Sesion obtenida de la busqueda
	 */

	public SesionCliente buscarSesionCliente(int codigoSesionCliente) {
		return sesionClienteDAO.read(codigoSesionCliente);
	}

	/**
	 * Metodo que permite obtener las sesiones de un cliente
	 * 
	 * @param cedulaCliente Cedula del cliente que tiene la sesion que se desea
	 *                      buscar
	 * @return Lista de sesiones de un cliente
	 */
	public List<SesionCliente> obtenerSesionesCliente(String cedulaCliente) {
		try {
			return sesionClienteDAO.obtenerSesionCliente(cedulaCliente);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Metodo para validacion
	 * 
	 * @param cedula El parmetro cedula sirve para la validacion de la una cedula
	 *               Ecuatoriana
	 * @return Si la cedula esta correcta o incorrecta en una variable booleana TRUE
	 *         o FALSE
	 * @throws Exception
	 */
	public boolean validadorDeCedula(String cedula) throws Exception {
		System.out.println(cedula + "    En Metodo ");
		boolean cedulaCorrecta = false;
		try {
			if (cedula.length() == 10) // ConstantesApp.LongitudCedula
			{
				int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
				if (tercerDigito < 6) {
					int[] coefValCedula = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };
					int verificador = Integer.parseInt(cedula.substring(9, 10));
					int suma = 0;
					int digito = 0;
					for (int i = 0; i < (cedula.length() - 1); i++) {
						digito = Integer.parseInt(cedula.substring(i, i + 1)) * coefValCedula[i];
						suma += ((digito % 10) + (digito / 10));
					}
					if ((suma % 10 == 0) && (suma % 10 == verificador)) {
						cedulaCorrecta = true;
					} else if ((10 - (suma % 10)) == verificador) {
						cedulaCorrecta = true;
					} else {
						cedulaCorrecta = false;
					}
				} else {
					cedulaCorrecta = false;
				}
			} else {
				cedulaCorrecta = false;
			}
		} catch (NumberFormatException nfe) {
			cedulaCorrecta = false;
		} catch (Exception err) {
			cedulaCorrecta = false;
			throw new Exception("Error cedula");
		}
		if (!cedulaCorrecta) {
			return cedulaCorrecta;
			// throw new Exception("Cedula Incorrecta");

		}
		return cedulaCorrecta;
	}

	/**
	 * Metodo para guardar Empleado
	 * 
	 * @param empleado El parametro empleado me permite registrarlo en la Base de
	 *                 Datos un Empleado
	 * @throws SQLException Excepcion para un fallo de ingreso en la base de datos
	 * @throws Exception    Excepcion de registro en la base de datos
	 */
	public void guardarEmpleado(Empleado empleado) throws SQLException, Exception {

		if (!validadorDeCedula(empleado.getCedula())) {
			throw new Exception("Cedula Incorrecta");
		} else {

			try {
				empleadoDAO.insertarEmpleado(empleado);
			} catch (Exception e) {
				throw new Exception(e.toString());
			}
		}
	}

	/**
	 * Metodo para obtener un Empleado
	 * 
	 * @param cedula El parametro cedula me permite obtener un Empleado que contenga
	 *               la cedual igual al parametro
	 * @return Un Empleado registrado en la Base de Datos
	 */
	public Empleado usuarioRegistrado(String cedula) {
		return empleadoDAO.obtenerEmpleado(cedula);
	}

	/**
	 * Metodo para obtener una Lista de Empleados
	 * 
	 * @return La lita con todos los empleado registrados en la Institucion
	 */
	public List<Empleado> listadoEmpleados() {
		return empleadoDAO.obtener();
	}

	/**
	 * Metodo para obtener un Empleado
	 * 
	 * @param usuario El parametro usuario me permite obtener un Empleado que
	 *                contenga el usuario pasado como parametro
	 * @param contra  El parametro contra permite obtener un Empleado que contenga
	 *                el usuario pasado como parametro
	 * @return Un Empleado con los usuario y contraseña de acuerdo a los parametros
	 * @throws Exception Excepcion cuando no se obtiene ningun usuario
	 */
	public Empleado usuario(String usuario, String contra) throws Exception {
		try {
			Empleado em = empleadoDAO.obtenerUsuario(usuario, contra);
			if (em != null) {
				return em;
			}
		} catch (NoResultException e) {
			throw new Exception("Credenciales Incorrectas");
		}
		return null;

	}

	/**
	 * Metodo para obtener una Lista de Transacciones
	 * 
	 * @param cedula El parametro cedula me permite obtener la lista de
	 *               transacciones de acuedo al parametro
	 * @return Lista de Transacciones que realizo un Cliente de acuerdo al parametro
	 */
	public List<Transaccion> listadeTransacciones(String cedula) {
		try {
			return transaccionDAO.getListaTransacciones(cedula);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Metodo para guardad una Transaccion
	 * 
	 * @param t El parametro t me permite registrar una Transaccion de acuerdo al
	 *          parametro
	 * @throws Exception Excepcion para un fallo en el registro de la Transaccion
	 */
	public void guardarTransaccion(Transaccion t) throws Exception {

		try {
			transaccionDAO.insert(t);
		} catch (Exception e) {
			throw new Exception(e.toString());
		}
	}

	public List<Transaccion> obtenerTransaccionesFechaHora(String cedula, String fechaI, String fechaF) {
		String fechaInicio = fechaI + " 00:00:00.000000";
		System.out.println(fechaInicio);
		String fechaFinal = fechaF + " 23:59:59.000000";
		System.out.println(fechaFinal);
		try {
			return transaccionDAO.getListaTransaccionesFechas(cedula, fechaInicio, fechaFinal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String realizarTransaccion(String cuenta, double monto, String tipoTransaccion) {
		CuentaDeAhorro clp = cuentaDeAhorroDAO.read(cuenta);
		if (clp != null) {
			System.out.println(clp.getNumeroCuentaDeAhorro());
			if (tipoTransaccion.equalsIgnoreCase("deposito")) {
				Double nvmonto = clp.getSaldoCuentaDeAhorro() + monto;
				clp.setSaldoCuentaDeAhorro(nvmonto);
				actualizarCuentaDeAhorro(clp);
				Transaccion t = new Transaccion();
				t.setCliente(clp.getCliente());
				t.setMonto(monto);
				t.setFecha(new Date());
				t.setTipo("deposito");
				t.setSaldoCuenta(nvmonto);
				try {
					// editable = false;
					guardarTransaccion(t);
					return "Hecho";
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.getMessage();
				}
			} else if (tipoTransaccion.equalsIgnoreCase("retiro") && monto <= clp.getSaldoCuentaDeAhorro()) {
				Double nvmonto2 = clp.getSaldoCuentaDeAhorro() - monto;
				clp.setSaldoCuentaDeAhorro(nvmonto2);
				actualizarCuentaDeAhorro(clp);
				Transaccion t2 = new Transaccion();
				t2.setCliente(clp.getCliente());
				t2.setMonto(monto);
				t2.setFecha(new Date());
				t2.setTipo("retiro");
				t2.setSaldoCuenta(nvmonto2);
				try {
					guardarTransaccion(t2);
					return "Hecho";
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.getMessage();
				}
			} else {
				return "Monto exedido";
			}
		} else {
			return "Cuenta Inexistente";
		}
		return "Fallido";
	}

	public String realizarTransferencia(String cedula, String cuentaAhorro2, double monto) {
		System.out.println(cedula);
		System.out.println(cuentaAhorro2);
		System.out.println(monto);
		CuentaDeAhorro cuentaAhorro = cuentaDeAhorroDAO.getCuentaCedulaCliente(cedula);
		CuentaDeAhorro cuentaAhorroTransferir = cuentaDeAhorroDAO.read(cuentaAhorro2);
		if (cuentaAhorro.getSaldoCuentaDeAhorro() >= monto) {
			cuentaAhorro.setSaldoCuentaDeAhorro(cuentaAhorro.getSaldoCuentaDeAhorro() - monto);
			actualizarCuentaDeAhorro(cuentaAhorro);
			cuentaAhorroTransferir.setSaldoCuentaDeAhorro(cuentaAhorroTransferir.getSaldoCuentaDeAhorro() + monto);
			actualizarCuentaDeAhorro(cuentaAhorroTransferir);
			TransfereciaLocal transfereciaLocal = new TransfereciaLocal();
			transfereciaLocal.setCliente(cuentaAhorro.getCliente());
			transfereciaLocal.setCuentaDeAhorroDestino(cuentaAhorroTransferir);
			transfereciaLocal.setMonto(monto);
			guardarTransferenciaLocal(transfereciaLocal);
			return "Transferencia Satisfactoria";
		} else {
			return "Monto exedido";
		}
	}

	public void guardarTransferenciaLocal(TransfereciaLocal transfereciaLocal) {
		transferenciaLocalDAO.insert(transfereciaLocal);
	}

	public void guardarSolicitudCredito(SolicitudDeCredito solicituDeCredito) {
		solicituDeCredito.setHistorialCredito(historialCredito(solicituDeCredito));
		solicituDeCredito.setSaldoCuenta(saldoCuenta(solicituDeCredito));
		solicituDeCredito.setGaranteEstado(garanteCreditos(solicituDeCredito));
		solicituDeCredito.setAñosCliente(obtenerEdad(solicituDeCredito.getClienteCredito().getFechaNacimiento()));
		solicituDeCredito.setCantidadCreditos(numeroCreditos(solicituDeCredito));

		String credito = "{\"credito\":\"" + solicituDeCredito.getClienteCredito().getCedula() + ";"
				+ String.valueOf(solicituDeCredito.getMesesCredito()) + ";" + solicituDeCredito.getHistorialCredito()
				+ ";" + obtenerCodigo(solicituDeCredito.getPropositoCredito()) + ";"
				+ String.valueOf(solicituDeCredito.getMontoCredito()) + ";" + solicituDeCredito.getSaldoCuenta() + ";"
				+ obtenerCodigo(solicituDeCredito.getTiempoEmpleo()) + ";"
				+ String.valueOf(solicituDeCredito.getTasaPago()) + ";"
				+ obtenerCodigo(solicituDeCredito.getEstadoCivilSexo()) + ";" + solicituDeCredito.getGaranteEstado()
				+ ";" + String.valueOf(solicituDeCredito.getAvaluoDeVivienda()) + ";"
				+ obtenerCodigo(solicituDeCredito.getActivo()) + ";"
				+ String.valueOf(solicituDeCredito.getAñosCliente()) + ";"
				+ obtenerCodigo(solicituDeCredito.getTipoVivienda()) + ";"
				+ String.valueOf(solicituDeCredito.getCantidadCreditos()) + ";"
				+ obtenerCodigo(solicituDeCredito.getTipoEmpleo()) + ";"
				+ obtenerCodigo(solicituDeCredito.getTrabajadorExtranjero()) + ";0\"}";
		System.out.println(credito);
		String res = enviarEntidad(credito);
		System.out.println(res);
		try {
			solicituDeCredito.setTipoCliente(
					String.valueOf(obtenerTipoCliente(solicituDeCredito.getClienteCredito().getCedula())));
		} catch (ForbiddenException | InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		solicitudDeCreditoDAO.insert(solicituDeCredito);
	}

	public void actualizarSolicitudCredito(SolicitudDeCredito solicitudDeCredito) {
		solicitudDeCreditoDAO.update(solicitudDeCredito);
	}

	public List<SolicitudDeCredito> listadoSolicitudDeCreditos() {
		return solicitudDeCreditoDAO.getSolicitudDeCreditos();
	}

	public Cliente obtenerClienteCuentaAhorro(String numeroCuenta) {
		CuentaDeAhorro cuentaDeAhorro = cuentaDeAhorroDAO.read(numeroCuenta);
		return cuentaDeAhorro.getCliente();
	}

	public byte[] toByteArray(InputStream in) throws IOException {

		ByteArrayOutputStream os = new ByteArrayOutputStream();

		byte[] buffer = new byte[1024];
		int len;

		// read bytes from the input stream and store them in buffer
		while ((len = in.read(buffer)) != -1) {
			// write bytes from the buffer into output stream
			os.write(buffer, 0, len);
		}

		return os.toByteArray();
	}

	public static void guardarCSV(SolicitudDeCredito solicitudDeCredito) {

	}

	public void guardarCredito(Credito credito) {
		creditoDAO.insert(credito);
	}

	public void actualizarCredito(Credito credito) {
		creditoDAO.update(credito);
	}

	public List<Credito> listarCreditos() {
		List<Credito> cred = creditoDAO.getCreditos();
		return cred;
	}

	public List<DetalleCredito> crearTablaAmortizacion(int cuotas, double monto, double interes) {
		List<DetalleCredito> listaDet = new ArrayList<>();
		// double monto =12000;
		// double interes = 5;

		Date fecha = new Date();
		List<Date> fechas = new ArrayList<>();
		double vcuota = monto / cuotas;
		double icuota = monto * (interes / 100);
		System.out.println("Fecha " + "| Cuota " + "| Capital " + "| Interes " + "| Saldo");
		for (int i = 0; i < cuotas; i++) {
			DetalleCredito detalle = new DetalleCredito();
			detalle.setEstado("Pendiente");
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(fecha); // Configuramos la fecha que se recibe
			calendar1.add(Calendar.MONTH, 1);
			fecha = calendar1.getTime();// numero de horas a añadir, o restar en caso de horas<0
			fechas.add(fecha);
			DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy");
			monto -= vcuota;
			System.out.println(hourdateFormat.format(fecha) + " | " + (vcuota + icuota) + " | " + icuota + " | "
					+ vcuota + " | " + (monto));
			detalle.setNumeroCuota(i);
			detalle.setFechaPago(fecha);
			detalle.setInteres(icuota);
			detalle.setSaldo(vcuota + icuota);
			detalle.setMonto(monto);
			detalle.setCuota(vcuota);
			listaDet.add(detalle);
		}
		return listaDet;
	}

	public String obtenerFecha2(Date fecha) {
		DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return hourdateFormat.format(fecha);
	}

	public void aprobarCredito(Credito credito, Cliente cliente) {
		String destinatario = cliente.getCorreo();
		String asunto = "APROBACIÓN DE CREDITO";
		String cuerpo = "JAMVirtual SISTEMA TRANSACCIONAL\n"
				+ "------------------------------------------------------------------------------\n"
				+ "              Estimado(a): " + cliente.getNombre().toUpperCase() + " "
				+ cliente.getApellido().toUpperCase() + "\n"
				+ "------------------------------------------------------------------------------\n"
				+ "COOPERATIVA JAM le informa que la solicitud para su credito ha sido aprobada. \n"
				+ "                       Fecha: " + obtenerFecha(credito.getFechaRegistro())
				+ "                                     \n"
				+ "                           TABLA DE AMORTIZACIÓN                              \n"
				+ generarTabla(credito.getDetalles()) + "\n"
				+ "                                                                              \n";
		enviarCorreo(destinatario, asunto, cuerpo);
//			} 
	}

	public String generarTabla(List<DetalleCredito> dcreditos) {
		String[] columnas = { "# Cuota", "Fecha", "Cuota", "Capital", "Interes", "Saldo" };
		int nfilas = dcreditos.size();
		String[][] mTablaAmor = new String[nfilas][6];
		int cont = 0;
		for (DetalleCredito dc : dcreditos) {
			mTablaAmor[cont][0] = String.valueOf(dc.getNumeroCuota());
			mTablaAmor[cont][1] = obtenerFecha2(dc.getFechaPago());
			mTablaAmor[cont][2] = String.valueOf(dc.getSaldo());
			mTablaAmor[cont][3] = String.valueOf(dc.getCuota());
			mTablaAmor[cont][4] = String.valueOf(dc.getInteres());
			mTablaAmor[cont][5] = String.valueOf(dc.getMonto());
			cont++;
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		new TextTable(columnas, mTablaAmor).printTable(new PrintStream(outputStream), 0);
		String output = null;
		try {
			output = outputStream.toString("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return output;
	}

	public String historialCredito(SolicitudDeCredito solicitudCredito) {
		List<Credito> lstCreditos = creditoDAO.getCreditos();
		List<Credito> lstAprobados = new ArrayList<Credito>();
		boolean confirmar = false;
		boolean confirmar2 = false;

		if (lstCreditos.size() == 0) {
			confirmar = true;
			return "A30";
		} else {
			for (Credito credito : lstCreditos) {
				if (credito.getSolicitud().getClienteCredito().getCedula()
						.equals(solicitudCredito.getClienteCredito().getCedula())) {
					lstAprobados.add(credito);
				}
			}
		}

		for (Credito crd : lstAprobados) {
			if (crd.getEstado().equalsIgnoreCase("Pagado")) {
				for (DetalleCredito detalleCredito : crd.getDetalles()) {
					if (detalleCredito.getEstado().equalsIgnoreCase("Retraso")) {
						confirmar = true;
						return "A33";
					}
				}
			}
		}

		if (!confirmar) {
			return "A31";
		}

		for (Credito crd : lstAprobados) {
			if (crd.getEstado().equalsIgnoreCase("Pagado")) {
				for (DetalleCredito detalleCredito : crd.getDetalles()) {
					if (detalleCredito.getEstado().equalsIgnoreCase("Pagado")) {
						confirmar2 = true;
					}
				}
			}
		}

		if (confirmar2) {
			return "A32";
		}
		return null;
	}

	public int obtenerEdad(Date fechaNacimiento) {
		Calendar a = Calendar.getInstance();
		Calendar b = Calendar.getInstance();
		a.setTime(fechaNacimiento);
		b.setTime(new Date());
		int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
		if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH)
				|| (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
			diff--;
		}
		return diff;
	}

	public String saldoCuenta(SolicitudDeCredito solicitudDeCredito) {
		CuentaDeAhorro cuentaDeAhorro = cuentaDeAhorroDAO
				.getCuentaCedulaCliente(solicitudDeCredito.getClienteCredito().getCedula());
		if (cuentaDeAhorro != null) {
			double saldo = cuentaDeAhorro.getSaldoCuentaDeAhorro();
			if (saldo < 500) {
				return "A61";
			} else if (saldo >= 500 && saldo < 1000) {
				return "A62";
			} else if (saldo >= 1000 && saldo < 1500) {
				return "A63";
			} else if (saldo >= 1500) {
				return "A64";
			}
		}
		return "A65";
	}

	public String garanteCreditos(SolicitudDeCredito solicitudDeCredito) {
		List<SolicitudDeCredito> lstSolicitudes = solicitudDeCreditoDAO.getSolicitudDeCreditos();
		boolean confirmar = false;
		for (SolicitudDeCredito solCredito : lstSolicitudes) {
			if (solCredito.getGaranteCredito().getCedula()
					.equalsIgnoreCase(solicitudDeCredito.getGaranteCredito().getCedula())
					&& solCredito.getEstadoCredito().equalsIgnoreCase("Solicitando")) {
				confirmar = true;
				return "A102";
			} else if (solCredito.getGaranteCredito().getCedula()
					.equalsIgnoreCase(solicitudDeCredito.getGaranteCredito().getCedula())
					&& solCredito.getEstadoCredito().equalsIgnoreCase("Aprobado")) {
				confirmar = true;
				return "A103";
			}
		}

		if (!confirmar) {
			return "A101";
		}
		return null;
	}

	public int numeroCreditos(SolicitudDeCredito solicitudDeCredito) {
		List<Credito> lstCreditos = creditoDAO.getCreditos();
		int contador = 0;
		for (Credito credito : lstCreditos) {
			if (credito.getSolicitud().getClienteCredito().getCedula()
					.equalsIgnoreCase(solicitudDeCredito.getClienteCredito().getCedula())) {
				contador++;
			}
		}
		return contador;
	}

	public void agregarCSV(String[] dato) throws IOException {

		String archCSV = "/home/jmurillo/bin/django/proyectoanalisisdatos-master/apiAnalisis/Datasets/DatasetBanco/3.DatasetBanco.csv";
		CSVWriter writer = new CSVWriter(new FileWriter(archCSV, true), ';', CSVWriter.NO_QUOTE_CHARACTER,
				CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
		writer.writeNext(dato);

		writer.close();
	}

	public String obtenerCodigo(String palabra) {
		switch (palabra) {
		case "inmuebles":
			return "A40";
		case "automovil":
			return "A41";
		case "muebles / equipamiento":
			return "A42";
		case "tecnología":
			return "A43";
		case "electrodomesticos":
			return "A44";
		case "reparaciones":
			return "A45";
		case "educacion":
			return "A46";
		case "vacaciones":
			return "A47";
		case "capacitacion":
			return "A48";
		case "negocios":
			return "A49";
		case "otros":
			return "A410";
		case "desempleado":
			return "A71";
		case "menos de 1 año":
			return "A72";
		case "entre 1 y 4 años":
			return "A73";
		case "entre 4 y 7 años":
			return "A74";
		case "mas de  7 años":
			return "A75";
		case "masculino: divorciado/separado":
			return "A91";
		case "femenino: dirvorciada/separada/casada":
			return "A92";
		case "masculino: soltero":
			return "A93";
		case "masculino: casado/viudo":
			return "A94";
		case " femenino: soltera":
			return "A95";
		case "Bienes inmuebles":
			return "A121";
		case "Seguro de vida y plan de construcción":
			return "A122";
		case "automovil u otro":
			return "A123";
		case "desconocido / sin propiedad":
			return "A124";
		case "gratis":
			return "A151";
		case "alquiler":
			return "A152";
		case "propio":
			return "A153";
		case "sin empleo":
			return "A171";
		case "jubilado":
			return "A172";
		case "empleado":
			return "A173";
		case "autonomo":
			return "A174";
		case "si":
			return "A201";
		case "no":
			return "A202";
		default:
			break;
		}
		return null;

	}

	public int obtenerTipoCliente(String tr) throws ForbiddenException, InterruptedException, ExecutionException {

		Form form = new Form();
		form.param("Dni", tr);
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://35.238.98.31:8000/apiAnalisis/predecir/");
		System.out.println(target.getUri());
		Future<String> response = target.request(MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.TEXT_PLAIN)
				.buildPost(Entity.form(form)).submit(String.class);
		// client.close();
		return Integer.parseInt(response.get());
	}

	public String enviarEntidad(String credito) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://35.238.98.31:8000/apiAnalisis/enviarSolicitud/");
		String res = target.request().post(Entity.json(credito), String.class);
		client.close();
		return res;
	}

	public String getDatos() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://35.238.98.31:8000/apiAnalisis/verDiagrama/");
		String res = target.request().get(String.class);
		client.close();
		return res;
	}
	
	public List<Credito> listarCreditosCedula(String cedula) {
		List<Credito> cred = creditoDAO.getCreditos();
		List<Credito> credLista = new ArrayList<Credito>();
		for(Credito credito: cred){
			System.out.println("********************************************************");
			System.out.println(credito.getSolicitud().getClienteCredito().getCedula());
			System.out.println("/////////");
			System.out.println(cedula);
			System.out.println("********************************************************");
			if (credito.getSolicitud().getClienteCredito().getCedula().equals(cedula)) {
				credLista.add(credito);
			}
		}
		
		return credLista;
	}
	
	public Credito verCredito(int codigo) {
		Credito cred = creditoDAO.read(codigo);
		return cred;
	}

	public void actualizarDetalle(DetalleCredito credito) {
		detalleCreditoDAO.update(credito);
	}
}
