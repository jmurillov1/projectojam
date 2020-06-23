package ec.edu.ups.coopjam.data;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.edu.ups.coopjam.model.SolicitudDeCredito;

@Stateless
public class SolicitudDeCreditoDAO {
	
	@PersistenceContext(name = "coopjamPersistenceUnit") 
	private EntityManager em;
	
	public void insert(SolicitudDeCredito s) {
		em.persist(s);
	}
	
	public void update(SolicitudDeCredito s) {
		em.merge(s);
	} 
	
	public SolicitudDeCredito read(int codigoCredito) {
		return em.find(SolicitudDeCredito.class, codigoCredito);
	} 
	
	public void delete(int codigoCredito) {
		SolicitudDeCredito c = read(codigoCredito);
		em.remove(c);
	}
	
	public List<SolicitudDeCredito> getSolicitudDeCreditos() {
		String jpql = "SELECT s FROM SolicitudDeCredito s ";

		Query q = em.createQuery(jpql, SolicitudDeCredito.class);
		return q.getResultList();
	} 
}
