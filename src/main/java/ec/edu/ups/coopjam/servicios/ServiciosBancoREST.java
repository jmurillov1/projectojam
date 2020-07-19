package ec.edu.ups.coopjam.servicios;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import ec.edu.ups.coopjam.business.GestionUsuarioLocal;

@Path("/banco")
public class ServiciosBancoREST {
	
	@Inject 
	private GestionUsuarioLocal on;  
	
	@POST
	@Path("/transaccion")
	@Produces("application/json")  
	@Consumes("application/json")
	public String realizarTransaccionBancaria(TransaccionRest transaccionRest) { 
		return on.realizarTransaccion(transaccionRest.getCuenta(),transaccionRest.getMonto(), transaccionRest.getTipo());
	} 
	
	@POST
	@Path("/transferencia")
	@Produces("application/json")  
	@Consumes("application/json") 
	public String realizarTransferencia(TransferenciaRest transferenciaRest) { 
		return on.realizarTransferencia(transferenciaRest.getCedula(), transferenciaRest.getCuentaDeAhorro(), transferenciaRest.getMonto());
	}	
	
}
