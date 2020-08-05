package ec.edu.ups.coopjam.data;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.edu.ups.coopjam.model.TransferenciaExterna;

@Stateless
public class TransferenciaExternaDAO {
	@PersistenceContext(name = "coopjamPersistenceUnit")
	private EntityManager em;

	public void insert(TransferenciaExterna t) {
		em.persist(t);
	}

	public void update(TransferenciaExterna t) {
		em.merge(t);
	}

	public TransferenciaExterna read(int codigoTransferenciaExterna) {
		return em.find(TransferenciaExterna.class, codigoTransferenciaExterna);
	}

	public void delete(int codigoTransferenciaExterna) {
		TransferenciaExterna t = read(codigoTransferenciaExterna);
		em.remove(t);
	}

	public List<TransferenciaExterna> getListaTransferenciasExterna() throws Exception {
		String jpql = "SELECT t FROM TransferenciaExterna";
		Query q = em.createQuery(jpql, TransferenciaExterna.class);
		return q.getResultList();

	}
}
