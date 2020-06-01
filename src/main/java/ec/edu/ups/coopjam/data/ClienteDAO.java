package ec.edu.ups.coopjam.data;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.edu.ups.coopjam.model.Cliente;

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

}
