package ec.edu.ups.coopjam.view;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import ec.edu.ups.coopjam.business.GestionUsuarios;
import ec.edu.ups.coopjam.model.Cliente;
import ec.edu.ups.coopjam.model.CuentaDeAhorro;

@ManagedBean
@ViewScoped
public class CajeroBean {
	@Inject
	private GestionUsuarios clienteON;

	private Double monto;

	private Cliente cliente;

	private boolean retiro;

	private boolean deposito;
	
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
			try {
				FacesContext contex = FacesContext.getCurrentInstance();
				contex.getExternalContext().redirect("PaginaCajero.xhtml");
			} catch (Exception e) {
			}
		}else if (retiro == true && monto <= clp.getSaldoCuentaDeAhorro()) {
			Double nvmonto2 =  clp.getSaldoCuentaDeAhorro() - monto;
			clp.setSaldoCuentaDeAhorro(nvmonto2);
			clienteON.actualizarCuentaDeAhorro(clp);
			try {
				FacesContext contex = FacesContext.getCurrentInstance();
				contex.getExternalContext().redirect("PaginaCajero.xhtml");
			} catch (Exception e) {
			}	
		}
		return "PaginaCajero";
	}

}
