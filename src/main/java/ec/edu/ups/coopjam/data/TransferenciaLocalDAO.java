package ec.edu.ups.coopjam.data;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.edu.ups.coopjam.model.TransfereciaLocal;

@Stateless
public class TransferenciaLocalDAO {
	@PersistenceContext(name = "coopjamPersistenceUnit")
	private EntityManager em;

	public void insert(TransfereciaLocal t) {
		em.persist(t);
	}

	public void update(TransfereciaLocal t) {
		em.merge(t);
	}

	public TransfereciaLocal read(int codigoTra) {
		return em.find(TransfereciaLocal.class, codigoTra);
	}

	public void delete(int codigoTra) {
		TransfereciaLocal c = read(codigoTra);
		em.remove(c);
	}

	public List<TransfereciaLocal> getTransfereciaLocals() {
		String jpql = "SELECT t FROM TransfereciaLocal t ";

		Query q = em.createQuery(jpql, TransfereciaLocal.class);
		return q.getResultList();
	}

}
