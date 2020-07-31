package ec.edu.ups.coopjam.view;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import ec.edu.ups.coopjam.business.GestionUsuarioLocal;

@Startup
@Singleton
@ManagedBean
@ViewScoped
public class MetodoAutomatico {


	    @Inject
	    private GestionUsuarioLocal gestionUsuario;

	    private Logger logger = Logger.getLogger(MetodoAutomatico.class.getName());

	    public static final String hora = "*";
	    public static final String minuto = "*";

	    @PostConstruct
	    public void init() {
	        try {

	            //automatico();
	        } catch (Exception e) {
	            System.out.println("ERROR EN EL SCHEDULE");
	        }

	    }

	    //@Schedule(hour = "18", minute = "0", second = "0", persistent = false)
	    @Schedule(hour = hora, minute = minuto, second = "*", persistent = false)
	    public void execute() {
	        try {
	            System.out.println("EJECUNTANDO EL METDO");
	            gestionUsuario.registrarCuotaVencida();
	        } catch (Exception ex) {
	            Logger.getLogger(MetodoAutomatico.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }
//	    public String guardar() thro


}
