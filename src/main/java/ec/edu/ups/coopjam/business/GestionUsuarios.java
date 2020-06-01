package ec.edu.ups.coopjam.business;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ec.edu.ups.coopjam.data.ClienteDAO;
import ec.edu.ups.coopjam.data.CuentaDeAhorroDAO;
import ec.edu.ups.coopjam.model.Cliente;
import ec.edu.ups.coopjam.model.CuentaDeAhorro;

@Stateless
public class GestionUsuarios { 
	@Inject 
	private ClienteDAO clienteDAO;  
	@Inject  
	private CuentaDeAhorroDAO cuentaDeAhorroDAO;
	
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
	    int numeroInicio= 4040;  
		List<CuentaDeAhorro> listaCuentas = listaCuentaDeAhorros(); 
		int numero = listaCuentas.size()+1;
		String resultado = String.format("%08d",numero);  
		String resultadoFinal = String.valueOf(numeroInicio) + resultado;
		return resultadoFinal;
	} 
	
	public static String getUsuario(String cedula, String nombre, String apellido) {
        String ud = cedula.substring(cedula.length() - 1);
        String pln = nombre.substring(0, 1);
        int it = 0;
        for (int i = 0; i < apellido.length(); i++) {
            if (apellido.charAt(i) == 32) {
                it = i;
            }
        }
        String a = apellido.substring(0, it);
        return pln.toLowerCase() + a.toLowerCase() + ud;
    }

    public static String getContraseña() {
        String simbolos = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefjhijklmnopqrstuvwxyz0123456789!#$%&()*+,-./:;<=>?@_";

        int tam = simbolos.length();
        String clave = "";
        for (int i = 0; i < 10; i++) {
            int v = (int) Math.floor(Math.random() * tam + 1);
            clave += simbolos.charAt(v);
        }
        return clave;
    }
	
	public void guardarCliente(Cliente c)  { 
		clienteDAO.insert(c);

	} 
	
	public Cliente buscarCliente(String cedulaCliente) {
		Cliente cliente = clienteDAO.read(cedulaCliente);
		return cliente;
	}  
	 
	public void eliminarCliente(String cedulaCliente) { 
		clienteDAO.delete(cedulaCliente);
	}
	public void actualizarCliente(Cliente cliente) {  
		clienteDAO.update(cliente);
	} 
	
	public List<Cliente> listaClientes(){ 
		List<Cliente> clientes = clienteDAO.getClientes(); 
		return clientes;
	}  
	
	public void guardarCuentaDeAhorros(CuentaDeAhorro c) throws Exception { 
		Cliente cli = c.getCliente();  
		cli.setUsuario(getUsuario(cli.getCedula(), cli.getNombre(), cli.getApellido())); 
		cli.setClave(getContraseña()); 
		c.setCliente(cli);
		cuentaDeAhorroDAO.insert(c);
	} 
	
	public CuentaDeAhorro buscarCuentaDeAhorro (String numeroCuentaDeAhorro) {
		CuentaDeAhorro cuentaDeAhorro = cuentaDeAhorroDAO.read(numeroCuentaDeAhorro);
		return cuentaDeAhorro;
	}   
	
	public CuentaDeAhorro buscarCuentaDeAhorroCliente (String cedulaCliente) {
		CuentaDeAhorro cuentaDeAhorro = cuentaDeAhorroDAO.getCuentaCedulaCliente(cedulaCliente);
		return cuentaDeAhorro;
	} 
	 
	public void eliminarCuentaDeAhorro(String numeroCuentaDeAhorro) { 
		cuentaDeAhorroDAO.delete(numeroCuentaDeAhorro);
	}
	public void actualizarCuentaDeAhorro(CuentaDeAhorro cuentaDeAhorro) {  
		cuentaDeAhorroDAO.update(cuentaDeAhorro);
	} 
	
	public List<CuentaDeAhorro> listaCuentaDeAhorros(){ 
		List<CuentaDeAhorro> clientes = cuentaDeAhorroDAO.getCuentaDeAhorros(); 
		return clientes;
	} 
	
	

}
