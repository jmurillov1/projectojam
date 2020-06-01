package ec.edu.ups.coopjam.data;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.edu.ups.coopjam.model.Cliente;
import ec.edu.ups.coopjam.model.Empleado;

@Stateless
public class ClienteDAO {
	
	@PersistenceContext(name = "coopjamPersistenceUnit") 
	private EntityManager em;

	public void insert(Cliente c) {
		em.persist(c);
	}

	public void update(Cliente c) {
		em.merge(c);
	}

	public Cliente read(String cedulaCliente) {
		return em.find(Cliente.class, cedulaCliente);
	}

	public void delete(String cedulaCliente) {
		Cliente c = read(cedulaCliente);
		em.remove(c);
	}

	public List<Cliente> getClientes() {
		String jpql = "SELECT c FROM Cliente c ";

		Query q = em.createQuery(jpql, Cliente.class);
		return q.getResultList();
	} 
	
	public Cliente obtenerClienteUsuarioContraseña(String usuario,String contra) throws Exception {
		try {
			String jpl = "select c from Cliente c Where c.usuario =:usu AND c.clave =:contr";
			Query q = em.createQuery(jpl, Cliente.class);
			q.setParameter("usu", usuario);
			q.setParameter("contr", contra);
			return (Cliente)q.getSingleResult();
			
		} catch (NoResultException e) {
			//System.out.println(e.getMessage());
			 throw new Exception("Credenciaales Inocorrectas"); 
		}
		//return null;
	}
}
