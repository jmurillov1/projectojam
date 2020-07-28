package ec.edu.ups.coopjam.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;

import ec.edu.ups.coopjam.business.GestionUsuarioLocal;
import ec.edu.ups.coopjam.business.GestionUsuarios;
import ec.edu.ups.coopjam.model.Cliente;
import ec.edu.ups.coopjam.model.Credito;
import ec.edu.ups.coopjam.model.CuentaDeAhorro;
import ec.edu.ups.coopjam.model.DetalleCredito;
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
	private GestionUsuarioLocal clienteON;

	private Double monto;

	private Cliente cliente;

	private List<Transaccion> listaTra;

	private boolean editable;
	
	private boolean editable2;

	private String tipoTransaccion;
	
	private List<Credito> credito;
	
	private String cedulaAux;
	
	private int codigoAux;
	
	private int codigoAux2;
	
	private int codigoAux3;
	
	private Transaccion transaccionAux;
	
	private PieChartModel pieModel;
	
	private boolean grafica;

	@PostConstruct
	public void init() {
		createPieModel();
		transaccionAux = new Transaccion();
		cliente = new Cliente();
		grafica = false;
	}

	public GestionUsuarioLocal getClienteON() {
		return clienteON;
	}

	public void setClienteON(GestionUsuarioLocal clienteON) {
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
	
	

	public List<Credito> getCredito() {
		return credito;
	}

	public void setCredito(List<Credito> credito) {
		this.credito = credito;
	}
	
	

	public String getCedulaAux() {
		return cedulaAux;
	}

	public void setCedulaAux(String cedulaAux) {
		this.cedulaAux = cedulaAux;
	}
	
	

	public boolean isEditable2() {
		return editable2;
	}

	public void setEditable2(boolean editable2) {
		this.editable2 = editable2;
	}
	
	

	public int getCodigoAux() {
		return codigoAux;
	}

	public void setCodigoAux(int codigoAux) {
		this.codigoAux = codigoAux;
	}
	
	

	public Transaccion getTransaccionAux() {
		return transaccionAux;
	}

	public void setTransaccionAux(Transaccion transaccionAux) {
		this.transaccionAux = transaccionAux;
	}
	
	
	
	

	public PieChartModel getPieModel() {
		return pieModel;
	}

	public void setPieModel(PieChartModel pieModel) {
		this.pieModel = pieModel;
	}

	public int getCodigoAux2() {
		return codigoAux2;
	}

	public void setCodigoAux2(int codigoAux2) {
		this.codigoAux2 = codigoAux2;
	}

	public int getCodigoAux3() {
		return codigoAux3;
	}

	public void setCodigoAux3(int codigoAux3) {
		this.codigoAux3 = codigoAux3;
	}
	
	

	public boolean isGrafica() {
		return grafica;
	}

	public void setGrafica(boolean grafica) {
		this.grafica = grafica;
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
			t.setSaldoCuenta(nvmonto);
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
			t2.setSaldoCuenta(nvmonto2);
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
			editable2 = false;
		}
		return null;
	}
	
	public List<Credito> cargarCreditos() {
		System.out.println("Cargar Creditos ----- ");
		List<Credito> lis = clienteON.listarCreditosCedula(cedulaAux);
		List<Credito> lisAux = new ArrayList<Credito>();
		System.out.println(lis.size());
		for(Credito credito: lis) {
			if (credito.getEstado().equals("Pendiente")) {
				lisAux.add(credito);
			}
		}
		if (lisAux  != null) {
			return lisAux;
		}
		return null;
	}
	 public void activar() {
		 System.out.println(cedulaAux);
		 editable = true;
		 editable2 = false;
		 //cedulaAux = "";
	 }
	 
	 public void cambioVar(int cod) {
		 codigoAux = cod;
		 editable = false;
		 editable2 = true;
		 
	 }
	 
	 public List<DetalleCredito> verDealles(){
		 List<DetalleCredito> list = clienteON.verCredito(codigoAux).getDetalles();
		 List<DetalleCredito> list2 = new ArrayList<DetalleCredito>();
		 for(DetalleCredito credito:list) {
			 if (!credito.getEstado().equals("Pagado")) {
				 list2.add(credito);
			}
		 }
		 
		 return list2;
	 }
	 
	 public void guardar() {
		 System.out.println("PAGOOOOOOOOOOOOOO");
		 System.out.println(codigoAux+"****"+transaccionAux.getMonto()+"********"+transaccionAux.getTipo()+"********"+codigoAux2+"***************"+cedulaAux);
		 List<DetalleCredito> listt = clienteON.verCredito(codigoAux).getDetalles();
		 if (transaccionAux.getTipo().equals("pagoC")) {
			for(DetalleCredito credito: listt) {
				if (transaccionAux.getMonto() >= credito.getSaldo()  && credito.getCodigoDetalle() == codigoAux2) {
					transaccionAux.setTipo("PagoCredito");
					transaccionAux.setFecha(new Date());
					transaccionAux.setSaldoCuenta(credito.getMonto());;
					credito.setEstado("Pagado");
					credito.setSaldo(0.00);
					clienteON.actualizarDetalle(credito);
				}
			}
			
		}else if (transaccionAux.getTipo().equals("pagoAb")) {
			for(DetalleCredito credito: listt) {
				if (transaccionAux.getMonto() <= credito.getSaldo() && credito.getCodigoDetalle() == codigoAux2) {
					transaccionAux.setTipo("PagoCredito");
					transaccionAux.setFecha(new Date());
					transaccionAux.setSaldoCuenta(credito.getMonto());
					//credito.setEstado("PagoAbono");
					double valor = credito.getSaldo() - transaccionAux.getMonto();
					credito.setSaldo(valor);
					if (valor > 0) {
						credito.setEstado("PagoAbono");
						credito.setSaldo(valor);
						clienteON.actualizarDetalle(credito);
					}else if(valor <= 0) {
						credito.setEstado("Pagado");
						credito.setSaldo(0);
						clienteON.actualizarDetalle(credito);
					}
					
					
				}
			}
			
		}else if(transaccionAux.getTipo().equals("pagoA")) {
			for(DetalleCredito credito: listt) {
				if (transaccionAux.getMonto() <= credito.getSaldo() && credito.getCodigoDetalle() == codigoAux2) {
					transaccionAux.setTipo("PagoCredito");
					transaccionAux.setFecha(new Date());
					transaccionAux.setSaldoCuenta(credito.getMonto());
					credito.setEstado("PagoAbono");
					double valor = credito.getSaldo() - transaccionAux.getMonto();
					credito.setSaldo(valor);
					clienteON.actualizarDetalle(credito);
					
					
				}else if (transaccionAux.getMonto() >= credito.getSaldo() && credito.getCodigoDetalle() == codigoAux2) {
					transaccionAux.setTipo("PagoCredito");
					transaccionAux.setFecha(new Date());
					transaccionAux.setSaldoCuenta(credito.getMonto());
					credito.setEstado("Pagado");
					credito.setSaldo(0.00);
					clienteON.actualizarDetalle(credito);
					
					
				}
			}
		}
		 
		 
		 
		 Cliente cliente = clienteON.buscarCliente(cedulaAux);
		 transaccionAux.setCliente(cliente);
		 try {
			clienteON.guardarTransaccion(transaccionAux);
			editable = false;
		    transaccionAux = new Transaccion();
		    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 //verfico
		 List<DetalleCredito> lisComprobar = clienteON.verCredito(codigoAux).getDetalles();
		 int cont = 0;
		 for(DetalleCredito credito: listt) {
			 if (credito.getEstado().equals("Pagado")) {
				cont += 1;
			}
		 }
		 System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooo");
		 System.out.println(cont);
		 System.out.println(" ooooooooooooooooooooooooooooooooooooooooooooo");
		 if (cont == listt.size()) {
			 System.out.println(lisComprobar.size()+" FINAL CREDITOOOOOOOOOOOOOOOO FINNNNNNNNNNNNNNN ");
			 Credito nv = clienteON.verCredito(codigoAux);
			 nv.setEstado("Pagado");
			 clienteON.actualizarCredito(nv);
		}
	 }
	 
	 private void createPieModel() {
		 String m = clienteON.getDatos();
		 String[] parts = m.split(";");
		 String part1 = parts[0]; 
		 String part2 = parts[1];
		 System.out.println(parts[0]+"*******"+parts[1]);
		 
		 System.out.println("******************************");
		 System.out.println("ENTRO graficaaaaaaaaaaaaa");
		 
	        pieModel = new PieChartModel();
	        ChartData data = new ChartData();
	         
	        PieChartDataSet dataSet = new PieChartDataSet();
	        List<Number> values = new ArrayList<>();
	        
	        for (int i = 0; i < parts.length; i++) {
	        	values.add(Integer.parseInt(parts[i]));
			}
	       /* values.add(300);
	        values.add(50);
	        values.add(100);*/
	        dataSet.setData(values);
	         
	        List<String> bgColors = new ArrayList<>();
	        bgColors.add("rgb(255, 99, 132)");
	        bgColors.add("rgb(54, 162, 235)");
	      //  bgColors.add("rgb(255, 205, 86)");
	        dataSet.setBackgroundColor(bgColors);
	         
	        data.addChartDataSet(dataSet);
	        List<String> labels = new ArrayList<>();
	        labels.add("Clientes Buenos");
	        labels.add("Clientes Malos");
	      //  labels.add("Yellow");
	        data.setLabels(labels);
	         
	        pieModel.setData(data);
	    }
	 
	 
	 public void cambioGrafica(String m) {
		 System.out.println("METODO cambiografica"+"****"+m);
		 if (m.equals("A")) {
			createPieModel();
			 grafica = true;
			
		}
		 System.out.println(grafica);
		 System.out.println(editable);
	 }
	 

}
