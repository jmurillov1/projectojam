package ec.edu.ups.coopjam.data;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.edu.ups.coopjam.model.CuentaDeAhorro;
import ec.edu.ups.coopjam.model.SesionCliente;

@Stateless
public class SesionClienteDAO {
	@PersistenceContext(name = "coopjamPersistenceUnit") 
	private EntityManager em;

	public void insert(SesionCliente s) {
		em.persist(s);
	}

	public void update(SesionCliente s) {
		em.merge(s);
	}

	public SesionCliente read(int codigoSesion) {
		return em.find(SesionCliente.class, codigoSesion);
	}

	public void delete(int codigoSesion) {
		SesionCliente c = read(codigoSesion);
		em.remove(c);
	}

	public List<SesionCliente> getSesionClientes() {
		String jpql = "SELECT s FROM SesionCliente s ";

		Query q = em.createQuery(jpql, SesionCliente.class);
		return q.getResultList();
	}  
	
	public List<SesionCliente> obtenerSesionCliente(String cedulaCliente) { 
		String jpql = "SELECT s FROM SesionCliente s WHERE s.cliente.cedula = :cedulaCliente";
		Query q = em.createQuery(jpql, SesionCliente.class);  
		q.setParameter("cedulaCliente",cedulaCliente);
		return q.getResultList();
	}
}
