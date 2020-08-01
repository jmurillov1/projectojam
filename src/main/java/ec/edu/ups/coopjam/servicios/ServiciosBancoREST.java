package ec.edu.ups.coopjam.servicios;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ec.edu.ups.coopjam.business.GestionUsuarioLocal;

@Path("/banco")
public class ServiciosBancoREST {
	
	@Inject 
	private GestionUsuarioLocal on;  
	
	
	@POST
	@Path("/login")
	@Produces("application/json;charset=utf-8")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Respuesta login(@FormParam("username") String username, @FormParam("password") String password) {
		System.out.println(username + " : " + password);
		Respuesta r = new Respuesta();
		try {
			//Prueba prueba = contactoON.login(username, password);
			/*if(prueba != null) {
				System.out.println(prueba.toString());
				r.setCodigo(1);
				r.setDescripcion("Exitoso");
				r.setPrueba(prueba);
			}else {
				r.setCodigo(2);
				r.setDescripcion("No existe el cliente");
			}*/
		} catch (Exception e) {
			r.setCodigo(2);
			r.setDescripcion("Error: " + e.getMessage());
		}
		return r;
	}
	
	
	
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
