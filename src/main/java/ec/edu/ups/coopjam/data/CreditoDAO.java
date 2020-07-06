package ec.edu.ups.coopjam.data;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.edu.ups.coopjam.model.Credito;


@Stateless
public class CreditoDAO {
	
	@PersistenceContext(name = "coopjamPersistenceUnit") 
	private EntityManager em;
	
	public void insert(Credito s) {
		em.persist(s);
	}
	
	public void update(Credito s) {
		em.merge(s);
	} 
	
	public Credito read(int codigoCredito) {
		return em.find(Credito.class, codigoCredito);
	} 
	
	public void delete(int codigoCredito) {
		Credito c = read(codigoCredito);
		em.remove(c);
	}
	
	public List<Credito> getCreditos() {
		String jpql = "SELECT s FROM Credito s ";

		Query q = em.createQuery(jpql, Credito.class);
		return q.getResultList();
	}

}
