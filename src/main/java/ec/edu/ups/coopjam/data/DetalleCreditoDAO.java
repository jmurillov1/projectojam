package ec.edu.ups.coopjam.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import ec.edu.ups.coopjam.model.DetalleCredito;

public class DetalleCreditoDAO {
	
	@PersistenceContext(name = "coopjamPersistenceUnit") 
	private EntityManager em;
	
	public void insert(DetalleCredito s) {
		em.persist(s);
	}
	
	public void update(DetalleCredito s) {
		em.merge(s);
	} 
	
	public DetalleCredito read(int codigoCredito) {
		return em.find(DetalleCredito.class, codigoCredito);
	} 
	
	public void delete(int codigoCredito) {
		DetalleCredito c = read(codigoCredito);
		em.remove(c);
	}
	
	public List<DetalleCredito> getDetallesCreditos() {
		String jpql = "SELECT s FROM DetalleCredito s ";

		Query q = em.createQuery(jpql, DetalleCredito.class);
		return q.getResultList();
	}

}
