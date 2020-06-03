package ec.edu.ups.coopjam.business;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import ec.edu.ups.coopjam.data.EmpleadoDAO;
import ec.edu.ups.coopjam.data.TransaccionDAO;
import ec.edu.ups.coopjam.model.Empleado;
import ec.edu.ups.coopjam.model.Transaccion;

@Stateless
public class GestionEmpleadosON {

	@Inject
	EmpleadoDAO empleadoDAO;
	
	@Inject
	TransaccionDAO transaccionDAO;
	
	/**
	 * Metodo para validacion
	 * @param cedula
	 * @return
	 * @throws Exception
	 */
	public boolean validadorDeCedula(String cedula) throws Exception {
		System.out.println(cedula + "    En Metodo ");
		boolean cedulaCorrecta = false;
		try {
			if (cedula.length() == 10) // ConstantesApp.LongitudCedula
			{
				int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
				if (tercerDigito < 6) {
					int[] coefValCedula = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };
					int verificador = Integer.parseInt(cedula.substring(9, 10));
					int suma = 0;
					int digito = 0;
					for (int i = 0; i < (cedula.length() - 1); i++) {
						digito = Integer.parseInt(cedula.substring(i, i + 1)) * coefValCedula[i];
						suma += ((digito % 10) + (digito / 10));
					}
					if ((suma % 10 == 0) && (suma % 10 == verificador)) {
						cedulaCorrecta = true;
					} else if ((10 - (suma % 10)) == verificador) {
						cedulaCorrecta = true;
					} else {
						cedulaCorrecta = false;
					}
				} else {
					cedulaCorrecta = false;
				}
			} else {
				cedulaCorrecta = false;
			}
		} catch (NumberFormatException nfe) {
			cedulaCorrecta = false;
		} catch (Exception err) {
			cedulaCorrecta = false;
			throw new Exception("Error cedula");
		}
		if (!cedulaCorrecta) {
			return cedulaCorrecta;
			//throw new Exception("Cedula Incorrecta");
			
		}
		return cedulaCorrecta;
	}
	
	/**
	 * Metodo para guardar Empleado
	 * @param empleado El parametro empleado me permite registrarlo en la Base de Datos un Empleado
	 * @throws SQLException Excepcion para un fallo de ingreso en la base de datos
	 * @throws Exception Excepcion de registro en la base de datos
	 */
	public void guardarEmpleado(Empleado empleado) throws SQLException, Exception {

		if (!validadorDeCedula(empleado.getCedula())) {
			throw new Exception("Cedula Incorrecta");
		} else {

			try {
				empleadoDAO.insertarEmpleado(empleado);
			} catch (Exception e) {
				throw new Exception(e.toString());
			}
		}
	}
	
	
	/**
	 * Metodo para obtener un Empleado
	 * @param cedula El parametro cedula me permite obtener un Empleado que contenga la cedual igual al parametro
	 * @return Un Empleado registrado en la Base de Datos
	 */
	public Empleado usuarioRegistrado(String cedula) {
		return empleadoDAO.obtenerEmpleado(cedula);
	}
	
	/**
	 * Metodo para obtener una Lista de Empleados
	 * @return La lita con todos los empleado registrados en la Institucion
	 */
	public List<Empleado> listadoEmpleados(){
		return empleadoDAO.obtener();
	}
	
	/**
	 * Metodo para obtener un Empleado
	 * @param usuario El parametro usuario me permite obtener un Empleado que contenga el usuario pasado como parametro
	 * @param contra El parametro contra permite obtener un Empleado que contenga el usuario pasado como parametro
	 * @return Un Empleado con los usuario y contraseña de acuerdo a los parametros
	 * @throws Exception Excepcion cuando no se obtiene ningun usuario
	 */
	public Empleado usuario(String usuario,String contra) throws Exception {
		try {
			Empleado em = empleadoDAO.obtenerUsuario(usuario, contra);
			if (em != null) {
				return em;
			}
		} catch (NoResultException e) {
			throw new Exception("Credenciales Incorrectas");
		}
		return null;
		
	}
	
	
	/**
	 * Metodo para obtener una Lista de Transacciones
	 * @param cedula El parametro cedula me permite obtener la lista de transacciones de acuedo al parametro
	 * @return Lista de Transacciones que realizo un Cliente de acuerdo al parametro
	 */
	public List<Transaccion> listadeTransacciones(String cedula){
		try {
			return transaccionDAO.getListaTransacciones(cedula);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * Metodo para guardad una Transaccion
	 * @param t El parametro t me permite registrar una Transaccion de acuerdo al parametro
	 * @throws Exception Excepcion para un fallo en el registro de la Transaccion
	 */
	public void guardarTransaccion(Transaccion t) throws Exception {
		
		try {
			transaccionDAO.insert(t);
		} catch (Exception e) {
			throw new Exception(e.toString());
		}
	}
	
	
	

}
