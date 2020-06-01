package ec.edu.ups.coopjam.data;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.edu.ups.coopjam.model.Empleado;
import ec.edu.ups.coopjam.model.Transaccion;

@Stateless
public class TransaccionDAO {

	@PersistenceContext(name = "coopjamPersistenceUnit")
	private EntityManager em;

	public void insert(Transaccion s) {
		em.persist(s);
	}

	public void update(Transaccion s) {
		em.merge(s);
	}

	public Transaccion read(int codigoSesion) {
		return em.find(Transaccion.class, codigoSesion);
	}

	public void delete(int codigoSesion) {
		Transaccion c = read(codigoSesion);
		em.remove(c);
	}

	public List<Transaccion> getListaTransacciones(String cedula) throws Exception {
		try {
			String jpql = "SELECT s FROM SesionCliente s Where s.cliente.cedula =:ced";
			Query q = em.createQuery(jpql, Transaccion.class);
			q.setParameter("ced", cedula);
			return q.getResultList();
		} catch (NoResultException e) {
			// System.out.println(e.getMessage());
			throw new Exception("Credenciaales Inocorrectas");
		}

	}

}
