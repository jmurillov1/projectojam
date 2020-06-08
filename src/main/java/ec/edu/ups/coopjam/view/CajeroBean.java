package ec.edu.ups.coopjam.view;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import ec.edu.ups.coopjam.business.GestionUsuarios;
import ec.edu.ups.coopjam.model.Cliente;
import ec.edu.ups.coopjam.model.CuentaDeAhorro;
import ec.edu.ups.coopjam.model.Transaccion;

/**
 * Clase de tipo Bean para el manejo de JSF y archivos xhtml
 * 
 * @author Malki Yupanki
 * @version: 1.0
 */
@ManagedBean
@ViewScoped
public class CajeroBean {
	@Inject
	private GestionUsuarios clienteON;

	private Double monto;

	private Cliente cliente;

	private List<Transaccion> listaTra;

	private boolean editable;

	private String tipoTransaccion;

	@PostConstruct
	public void init() {
		cliente = new Cliente();
	}

	public GestionUsuarios getClienteON() {
		return clienteON;
	}

	public void setClienteON(GestionUsuarios clienteON) {
		this.clienteON = clienteON;
	}

	/**
	 * Metodo para obtener un Monto
	 * 
	 * @return Me devuelve un valor de una transaccion
	 */
	public Double getMonto() {
		return monto;
	}

	/**
	 * Metodo para asignar un valor al monto
	 * 
	 * @param monto El parametro moto me permite asignar un valor al monto
	 */
	public void setMonto(Double monto) {
		this.monto = monto;
	}

	/**
	 * Metodo para obtener un Cliente
	 * 
	 * @return Un cliente para se utilizado en la paguina
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * Metodo para asignar un cliente
	 * 
	 * @param cliente el parametro cliente me permite asignar un valor a mi variable
	 *                cliente
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	/**
	 * Metodo para obtener una lista de tipo transacciones
	 * 
	 * @return Una lista de Transacciones
	 */
	public List<Transaccion> getListaTra() {
		return listaTra;
	}

	/**
	 * Metodo para asignar valores a la lista
	 * 
	 * @param listaTra el parametro listaTra me permite asignar una lista de
	 *                 transacciones a mi variable local de Tipo Lista de
	 *                 Transacciones
	 */
	public void setListaTra(List<Transaccion> listaTra) {
		this.listaTra = listaTra;
	}

	/**
	 * Metodo para asignar un valor booleano
	 * 
	 * @return Si es TRUE o FALSE
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * Metodo para asignar un valor booleano
	 * 
	 * @param editable El parametro editable me permite cambiar a TRUE o FALSE el
	 *                 booleano
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	/**
	 * Metdo para obtener el tipo de transaccion que se va a realizar
	 * 
	 * @return El valor del tipo de transaccion si es Retiro o Deposito
	 */
	public String getTipoTransaccion() {
		return tipoTransaccion;
	}

	/**
	 * Asignar el Tipo de Transaccion
	 * 
	 * @param tipoTransaccion El parametro tipoTransaccion me permite ver que tipo
	 *                        de Transaccion se esta realizando.
	 */
	public void setTipoTransaccion(String tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

	/**
	 * Metodo para validar la cedula de Un Cliente
	 * 
	 * @return Me devuelve un Mensaje si la ceudla es correcta, incorreta o si el
	 *         cliente no esta registrado
	 */
	public String valCedula() {
		System.out.println("*-------*" + cliente.getCedula());
		if (cliente.getCedula() != null) {
			try {
				boolean c = clienteON.validadorDeCedula(cliente.getCedula());
				if (c) {
					Cliente usuarioRegistrado = clienteON.buscarCliente(cliente.getCedula());

					// cliente = usuarioRegistrado;
					if (usuarioRegistrado != null) {
						CuentaDeAhorro cuen = clienteON.buscarCuentaDeAhorroCliente(usuarioRegistrado.getCedula());
						System.out.println("Registrado");
						String l = (String) (usuarioRegistrado.getNombre() + "    " + usuarioRegistrado.getApellido());
						editable = false;
						cargarTransacciones();
						return l;
					} else {
						String kl = "Cliente No registrado en el sistema";
						editable = false;
						cargarTransacciones();
						return kl;
					}
				} else {
					String ml = "Cedula Incorrecta";
					editable = false;
					cargarTransacciones();
					return ml;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return " ";
	}

	public String numCuenta() {
		
		try {
			boolean c;
			c = clienteON.validadorDeCedula(cliente.getCedula());
			if (c) {
				Cliente usuarioRegistrado = clienteON.buscarCliente(cliente.getCedula());
				// cliente = usuarioRegistrado;
				if (usuarioRegistrado != null) {
					CuentaDeAhorro cuen = clienteON.buscarCuentaDeAhorroCliente(usuarioRegistrado.getCedula());
					System.out.println("Si entra a la cuenta");
					return cuen.getNumeroCuentaDeAhorro();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("No entro");
		return null;

	}

	/**
	 * Metodo para validar el Saldo de la Cuenta de Ahorro
	 * 
	 * @return El saldo disponible en la cuenta de ahorro de acuerdo a la
	 *         transaccion que se esta realizando
	 */
	public String valMonto() {
		System.out.println("*-------*" + cliente.getCedula());
		if (cliente.getCedula() != null) {
			Cliente usuarioRegistrado = clienteON.buscarCliente(cliente.getCedula());
			// cliente = usuarioRegistrado;
			if (usuarioRegistrado != null) {
				System.out.println("Registrado");
				CuentaDeAhorro cl = clienteON.buscarCuentaDeAhorroCliente(cliente.getCedula());
				String l = String.valueOf(cl.getSaldoCuentaDeAhorro());
				System.out.println("------MONTO--+++++++++" + tipoTransaccion + cl.getSaldoCuentaDeAhorro() + monto);
				if (tipoTransaccion.equalsIgnoreCase("retiro") && monto == null) {
					return l;
				} else if (tipoTransaccion.equalsIgnoreCase("retiro") && cl.getSaldoCuentaDeAhorro() < monto) {
					String ms = "La cuenta no cuenta con el saldo suficiente, Su saldo es: "
							+ cl.getSaldoCuentaDeAhorro();
					return ms;

				} else {
					return l;
				}

			}
		}
		return " ";
	}

	/**
	 * Metodo para regitrar la transaccion
	 * 
	 * @return Me devuelve a la Pagina del cajero para realizar una nueva
	 *         transaccion
	 */
	public String registrar() {
		CuentaDeAhorro clp = clienteON.buscarCuentaDeAhorroCliente(cliente.getCedula());
		if (tipoTransaccion.equalsIgnoreCase("deposito")) {
			Double nvmonto = clp.getSaldoCuentaDeAhorro() + monto;
			clp.setSaldoCuentaDeAhorro(nvmonto);
			clienteON.actualizarCuentaDeAhorro(clp);
			Transaccion t = new Transaccion();
			t.setCliente(clp.getCliente());
			t.setMonto(monto);
			t.setFecha(new Date());
			t.setTipo("deposito");
			try {
				// editable = false;
				clienteON.guardarTransaccion(t);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.getMessage();
			}
			try {
				FacesContext contex = FacesContext.getCurrentInstance();
				contex.getExternalContext().redirect("PaginaCajero.xhtml");
			} catch (Exception e) {
			}
		} else if (tipoTransaccion.equalsIgnoreCase("retiro") && monto <= clp.getSaldoCuentaDeAhorro()) {
			Double nvmonto2 = clp.getSaldoCuentaDeAhorro() - monto;
			clp.setSaldoCuentaDeAhorro(nvmonto2);
			clienteON.actualizarCuentaDeAhorro(clp);
			Transaccion t2 = new Transaccion();
			t2.setCliente(clp.getCliente());
			t2.setMonto(monto);
			t2.setFecha(new Date());
			t2.setTipo("retiro");
			try {
				clienteON.guardarTransaccion(t2);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.getMessage();
			}
			try {
				FacesContext contex = FacesContext.getCurrentInstance();
				contex.getExternalContext().redirect("PaginaCajero.xhtml");
			} catch (Exception e) {
			}
		}
		return "PaginaCajero";
	}

	/**
	 * Metodo para Cargar las Transacciones
	 * 
	 * @return Una lista de transacciones del cliente que va a realizar un Deposito
	 *         o Retiro
	 */
	public String cargarTransacciones() {
		List<Transaccion> lis = clienteON.listadeTransacciones(cliente.getCedula());
		if (lis != null) {
			listaTra = lis;
			editable = true;
		}
		return null;
	}

}
