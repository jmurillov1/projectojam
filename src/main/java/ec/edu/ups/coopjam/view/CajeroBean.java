package ec.edu.ups.coopjam.view;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import ec.edu.ups.coopjam.business.GestionEmpleadosON;
import ec.edu.ups.coopjam.business.GestionUsuarios;
import ec.edu.ups.coopjam.model.Cliente;
import ec.edu.ups.coopjam.model.CuentaDeAhorro;
import ec.edu.ups.coopjam.model.Transaccion;

@ManagedBean
@ViewScoped
public class CajeroBean {
	@Inject
	private GestionUsuarios clienteON;

	@Inject
	private GestionEmpleadosON empleadoON;

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

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	public GestionEmpleadosON getEmpleadoON() {
		return empleadoON;
	}

	public void setEmpleadoON(GestionEmpleadosON empleadoON) {
		this.empleadoON = empleadoON;
	}

	public List<Transaccion> getListaTra() {
		return listaTra;
	}

	public void setListaTra(List<Transaccion> listaTra) {
		this.listaTra = listaTra;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String getTipoTransaccion() {
		return tipoTransaccion;
	}

	public void setTipoTransaccion(String tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

	public String valCedula() {
		System.out.println("*-------*" + cliente.getCedula());
		if (cliente.getCedula() != null) {
			try {
				boolean c = empleadoON.validadorDeCedula(cliente.getCedula());
				if (c) {
					Cliente usuarioRegistrado = clienteON.buscarCliente(cliente.getCedula());
					// cliente = usuarioRegistrado;
					if (usuarioRegistrado != null) {
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
				}else {
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

	public String valMonto() {
		System.out.println("*-------*" + cliente.getCedula());
		if (cliente.getCedula() != null) {
			Cliente usuarioRegistrado = clienteON.buscarCliente(cliente.getCedula());
			// cliente = usuarioRegistrado;
			if (usuarioRegistrado != null) {
				System.out.println("Registrado");
				CuentaDeAhorro cl = clienteON.buscarCuentaDeAhorroCliente(cliente.getCedula());
				String l = String.valueOf(cl.getSaldoCuentaDeAhorro());
				System.out.println("------MONTO--+++++++++"+ tipoTransaccion + cl.getSaldoCuentaDeAhorro() + monto);
				if (tipoTransaccion.equalsIgnoreCase("retiro") && cl.getSaldoCuentaDeAhorro() < monto) {
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
				empleadoON.guardarTransaccion(t);
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
				empleadoON.guardarTransaccion(t2);
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

	public String cargarTransacciones() {
		List<Transaccion> lis = empleadoON.listadeTransacciones(cliente.getCedula());
		if (lis != null) {
			listaTra = lis;
			editable = true;
		}
		return null;
	}
	

}
