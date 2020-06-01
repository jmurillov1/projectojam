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

	private boolean retiro;

	private boolean deposito;
	
	private List<Transaccion> listaTra;
	
	private boolean editable;
	
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

	public boolean isRetiro() {
		return retiro;
	}

	public void setRetiro(boolean retiro) {
		this.retiro = retiro;
	}

	public boolean isDeposito() {
		return deposito;
	}

	public void setDeposito(boolean deposito) {
		this.deposito = deposito;
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

	public String valCedula() {
		System.out.println("*-------*"+cliente.getCedula());
		if (cliente.getCedula() != null) {
			Cliente usuarioRegistrado = clienteON.buscarCliente(cliente.getCedula());
			//cliente = usuarioRegistrado;
			if (usuarioRegistrado != null) {
				System.out.println("Registrado");
				String l =(String) (usuarioRegistrado.getNombre() + "    " + usuarioRegistrado.getApellido());
				return l;
			}
		}
		return " ";
	}
	
	public String valMonto() {
		System.out.println("*-------*"+cliente.getCedula());
		if (cliente.getCedula() != null) {
			Cliente usuarioRegistrado = clienteON.buscarCliente(cliente.getCedula());
			//cliente = usuarioRegistrado;
			if (usuarioRegistrado != null) {
				System.out.println("Registrado");
				CuentaDeAhorro cl = clienteON.buscarCuentaDeAhorroCliente(cliente.getCedula());
				String l = String.valueOf(cl.getSaldoCuentaDeAhorro());
				System.out.println("------MONTO--+++++++++"+retiro+cl.getSaldoCuentaDeAhorro() + monto);
				if (retiro == true && cl.getSaldoCuentaDeAhorro() < monto) {
					String ms = "La cuenta no cuenta con el saldo suficiente, Su saldo es: "+cl.getSaldoCuentaDeAhorro();
					return ms;
				}else {
					return l;
				}		
				
			}
		}
		return " ";
	}
	
	public String registrar() {
		CuentaDeAhorro clp = clienteON.buscarCuentaDeAhorroCliente(cliente.getCedula());
		if (deposito) {
			Double nvmonto =  clp.getSaldoCuentaDeAhorro() + monto;
			clp.setSaldoCuentaDeAhorro(nvmonto);
			clienteON.actualizarCuentaDeAhorro(clp);
			Transaccion t = new Transaccion();
			t.setCliente(clp.getCliente());
			t.setMonto(monto);
			t.setFecha(new Date());
			try {
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
		}else if (retiro == true && monto <= clp.getSaldoCuentaDeAhorro()) {
			Double nvmonto2 =  clp.getSaldoCuentaDeAhorro() - monto;
			clp.setSaldoCuentaDeAhorro(nvmonto2);
			clienteON.actualizarCuentaDeAhorro(clp);
			Transaccion t2 = new Transaccion();
			t2.setCliente(clp.getCliente());
			t2.setMonto(monto);
			t2.setFecha(new Date());
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
