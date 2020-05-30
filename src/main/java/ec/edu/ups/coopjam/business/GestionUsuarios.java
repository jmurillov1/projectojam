package ec.edu.ups.coopjam.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ec.edu.ups.coopjam.data.ClienteDAO;
import ec.edu.ups.coopjam.model.Cliente;

@Stateless
public class GestionUsuarios { 
	@Inject 
	private ClienteDAO clienteDAO;  
	
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
	
	public void guardarCliente(Cliente c) throws Exception {

		if (verificarCedula(c.getCedula())) {
			try {
				Cliente cliente = clienteDAO.read(c.getCedula());
				if (cliente != null) {
					clienteDAO.update(c);
				} else {
					clienteDAO.insert(c);
				}
				System.out.println("Insertado");
			} catch (Exception e) {
				throw new Exception(e);
			}
		} else {
			throw new Exception("Cedula es incorrecta");
		}

	} 
	
	public Cliente buscarCliente(String cedulaCliente)throws Exception {
		Cliente cliente = clienteDAO.read(cedulaCliente);
		if (cliente == null) {
			throw new Exception("Esta cedula no se encuentra registrada en la base");
		}

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
	
	

}
