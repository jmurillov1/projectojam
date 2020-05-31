package ec.edu.ups.coopjam.data;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.edu.ups.coopjam.model.Empleado;


@Stateless
public class EmpleadoDAO {
	
	@PersistenceContext(name = "coopjamPersistenceUnit") 
	private EntityManager con;
	
	public boolean insertarEmpleado(Empleado emleado) {
		con.persist(emleado);
		return true;
	}
	
	
	public Empleado obtenerEmpleado(String id) {
		 return con.find(Empleado.class, id);
	}
	
	
	
	public boolean editar_Empleado(Empleado factura) {
		con.merge(factura);
		return true;
	}
	
	public List<Empleado> obtener() {
		String jpl = "select p from Empleado p";
		Query q = con.createQuery(jpl, Empleado.class);
		return q.getResultList();
	
	}
	
	
	public void eliminarEmpleado(Empleado per) {
		Empleado p = obtenerEmpleado(per.getCedula());
		con.remove(p);

    }
	
	public Empleado obtenerUsuario(String usuario,String contra) throws Exception {
		try {
			String jpl = "select p from Empleado p Where p.usuario =:usu AND p.contrasena =:contr";
			Query q = con.createQuery(jpl, Empleado.class);
			q.setParameter("usu", usuario);
			q.setParameter("contr", contra);
			return (Empleado)q.getSingleResult();
			
		} catch (NoResultException e) {
			//System.out.println(e.getMessage());
			 throw new Exception("Credenciaales Inocorrectas"); 
		}
		//return null;
	}


}
