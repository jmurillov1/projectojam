package ec.edu.ups.coopjam.business;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Properties;

import javax.ejb.Stateless;
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

import ec.edu.ups.coopjam.data.ClienteDAO;
import ec.edu.ups.coopjam.data.CuentaDeAhorroDAO;
import ec.edu.ups.coopjam.data.SesionClienteDAO;
import ec.edu.ups.coopjam.data.TransaccionDAO;
import ec.edu.ups.coopjam.model.Cliente;
import ec.edu.ups.coopjam.model.CuentaDeAhorro;
import ec.edu.ups.coopjam.model.SesionCliente;
import ec.edu.ups.coopjam.model.Transaccion;

@Stateless
public class GestionUsuarios {
	@Inject
	private ClienteDAO clienteDAO;
	@Inject
	private CuentaDeAhorroDAO cuentaDeAhorroDAO; 
	@Inject  
	private SesionClienteDAO sesionClienteDAO; 
	@Inject 
	private TransaccionDAO transaccionDAO;

	public boolean verificarCedula(String ced) {
		int longitud = 0;
		char digitoN;
		int ultimo = 0;
		int suma = 0;
		int digitoVerificador = 0;
		longitud = ced.length();

		for (int i = 0; i < longitud - 1; i++) {
			digitoN = ced.charAt(i);
			int digitoAscii = (int) ced.charAt(i);
			int resultado = 0;

			switch (digitoAscii) {
			case 48:
				digitoN = 0;
				break;
			case 49:
				digitoN = 1;
				break;
			case 50:
				digitoN = 2;
				break;
			case 51:
				digitoN = 3;
				break;
			case 52:
				digitoN = 4;
				break;
			case 53:
				digitoN = 5;
				break;
			case 54:
				digitoN = 6;
				break;
			case 55:
				digitoN = 7;
				break;
			case 56:
				digitoN = 8;
				break;
			case 57:
				digitoN = 9;
				break;
			}
			if (i % 2 == 0) {
				resultado = digitoN * 2;
				if (resultado >= 10) {
					resultado -= 9;

				}
			} else {
				resultado = digitoN * 1;
			}
			suma += resultado;
		}
		digitoVerificador = (((suma / 10) + 1) * 10) - suma;
		if (digitoVerificador == 10) {
			digitoVerificador = 0;
		}
		ultimo = (int) ced.charAt(9);

		switch (ultimo) {
		case 48:
			ultimo = 0;
			break;
		case 49:
			ultimo = 1;
			break;
		case 50:
			ultimo = 2;
			break;
		case 51:
			ultimo = 3;
			break;
		case 52:
			ultimo = 4;
			break;
		case 53:
			ultimo = 5;
			break;
		case 54:
			ultimo = 6;
			break;
		case 55:
			ultimo = 7;
			break;
		case 56:
			ultimo = 8;
			break;
		case 57:
			ultimo = 9;
			break;
		}
		if (ultimo == digitoVerificador) {
			return true;

		} else {
			return false;
		}
	}

	public String generarNumeroDeCuenta() {
		int numeroInicio = 4040;
		List<CuentaDeAhorro> listaCuentas = listaCuentaDeAhorros();
		int numero = listaCuentas.size() + 1;
		String resultado = String.format("%08d", numero);
		String resultadoFinal = String.valueOf(numeroInicio) + resultado;
		return resultadoFinal;
	}

	public static String getUsuario(String cedula, String nombre, String apellido) {
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

	public static String getContraseña() {
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

	public static void enviarCorreo(String destinatario, String asunto, String cuerpo) {
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

	public static String fecha() {
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return hourdateFormat.format(date);
	}

	public void guardarCliente(Cliente c) {
		clienteDAO.insert(c);

	}

	public Cliente buscarCliente(String cedulaCliente) {
		Cliente cliente = clienteDAO.read(cedulaCliente);
		return cliente;
	}

	public Cliente buscarClienteUsuarioContraseña(String usuario, String contraseña) {
		try {
			return clienteDAO.obtenerClienteUsuarioContraseña(usuario, contraseña);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void eliminarCliente(String cedulaCliente) {
		clienteDAO.delete(cedulaCliente);
	}

	public void actualizarCliente(Cliente cliente) {
		clienteDAO.update(cliente);
	}

	public List<Cliente> listaClientes() {
		List<Cliente> clientes = clienteDAO.getClientes();
		return clientes;
	}

	public void guardarCuentaDeAhorros(CuentaDeAhorro c) { 
		Cliente cliente = clienteDAO.read(c.getCliente().getCedula());  
		if(cliente == null) {  
			  Cliente cli = c.getCliente();  
			  String usuario =getUsuario(cli.getCedula(), cli.getNombre(), cli.getApellido()); String
			  contraseña = getContraseña(); cli.setUsuario(usuario);
			  cli.setClave(contraseña); c.setCliente(cli); String destinatario =
			  cli.getCorreo(); //A quien le quieres escribir.
			  
			  String asunto = "CREACION DE USUARIO"; String cuerpo =
			  "JAMVirtual                                               SISTEMA TRANSACCIONAL\n"
			  +
			  "------------------------------------------------------------------------------\n"
			  + "              Estimado(a): "+cli.getNombre().toUpperCase()+" "+cli.
			  getApellido().toUpperCase()+"\n" +
			  "------------------------------------------------------------------------------\n"
			  +
			  "COOPERATIVA JAM le informa que el usuario ha sido habilitado exitosamente.    \n"
			  +
			  "                                                                              \n"
			  + "                       Su usuario es : "+ usuario
			  +"                          \n" +
			  "                   	Su clave de acceso es:   "+
			  contraseña+"               \n" + "                       Fecha: "+fecha()
			  +"                                     \n" +
			  "                                                                              \n"
			  +
			  "------------------------------------------------------------------------------\n"
			  ;  
			  enviarCorreo(destinatario, asunto, cuerpo);  
			  cuentaDeAhorroDAO.insert(c); 
		}
		
	}
		 


	public CuentaDeAhorro buscarCuentaDeAhorro(String numeroCuentaDeAhorro) {
		CuentaDeAhorro cuentaDeAhorro = cuentaDeAhorroDAO.read(numeroCuentaDeAhorro);
		return cuentaDeAhorro;
	}

	public CuentaDeAhorro buscarCuentaDeAhorroCliente(String cedulaCliente) {
		CuentaDeAhorro cuentaDeAhorro = cuentaDeAhorroDAO.getCuentaCedulaCliente(cedulaCliente);
		return cuentaDeAhorro;

	}

	public void eliminarCuentaDeAhorro(String numeroCuentaDeAhorro) {
		cuentaDeAhorroDAO.delete(numeroCuentaDeAhorro);
	}

	public void actualizarCuentaDeAhorro(CuentaDeAhorro cuentaDeAhorro) {
		cuentaDeAhorroDAO.update(cuentaDeAhorro);
	}

	public List<CuentaDeAhorro> listaCuentaDeAhorros() {
		List<CuentaDeAhorro> clientes = cuentaDeAhorroDAO.getCuentaDeAhorros();
		return clientes;
	} 
	
	public void guardarSesion(SesionCliente sesionCliente) { 
			Cliente cli = sesionCliente.getCliente();  
			String destinatario =cli.getCorreo();
		if(sesionCliente.getEstado().equalsIgnoreCase("Incorrecto")) {
			  //A quien le quieres escribir.
			  
			  String asunto = "INICIO DE SESION FALLIDA";  
			  String cuerpo = "JAMVirtual SISTEMA TRANSACCIONAL\n"
			  +
			  "------------------------------------------------------------------------------\n"
			  + "              Estimado(a): "+cli.getNombre().toUpperCase()+" "+cli.
			  getApellido().toUpperCase()+"\n" +
			  "------------------------------------------------------------------------------\n"
			  +
			  "COOPERATIVA JAM le informa que el acceso a su cuenta ha sido fallida.    \n"
			  + "                       Fecha: "+sesionCliente.getFechaSesion()
			  +"                                     \n" +
			  "                                                                              \n"
			  +
			  "------------------------------------------------------------------------------\n"
			  ;  
			  enviarCorreo(destinatario, asunto, cuerpo); 
		}else  { 
			 //A quien le quieres escribir.
			  
			  String asunto = "INICIO DE SESION CORRECTA";  
			  String cuerpo = "JAMVirtual SISTEMA TRANSACCIONAL\n"
			  +
			  "------------------------------------------------------------------------------\n"
			  + "              Estimado(a): "+cli.getNombre().toUpperCase()+" "+cli.
			  getApellido().toUpperCase()+"\n" +
			  "------------------------------------------------------------------------------\n"
			  +
			  "COOPERATIVA JAM le informa que el acceso a su cuenta ha sido correcta.    \n"
			  + "                       Fecha: "+sesionCliente.getFechaSesion()
			  +"                                     \n" +
			  "                                                                              \n"
			  +
			  "------------------------------------------------------------------------------\n"
			  ;  
			  enviarCorreo(destinatario, asunto, cuerpo); 
			
		}
		
		sesionClienteDAO.insert(sesionCliente);

	}

	public SesionCliente buscarSesionCliente(int codigoSesionCliente) {
		return sesionClienteDAO.read(codigoSesionCliente);
	} 
	
	
	
	

}
