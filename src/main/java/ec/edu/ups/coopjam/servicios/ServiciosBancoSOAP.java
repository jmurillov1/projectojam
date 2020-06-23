package ec.edu.ups.coopjam.servicios;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;

import ec.edu.ups.coopjam.business.GestionUsuarioLocal;

@WebService
public class ServiciosBancoSOAP {

	@Inject 
	private GestionUsuarioLocal gestionUsuario; 
	
	@WebMethod 
	public String realizarTransaccion(String cuenta, double monto, String tipo) { 
		return null;
	}
	
	
}
