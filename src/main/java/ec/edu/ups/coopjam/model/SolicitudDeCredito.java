package ec.edu.ups.coopjam.model;

import java.io.File;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import org.primefaces.model.file.UploadedFile;

@Entity
public class SolicitudDeCredito {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codigoCredito; 
	@OneToOne
	@JoinColumn(name="cedula_cliente")
	private Cliente clienteCredito;  
	@OneToOne
	@JoinColumn(name="cedula_garante")
	private Cliente garanteCredito; 
	private String propositoCredito; 
	private double montoCredito; 
	private String mesesCredito;
	private String tiempoEmpleo; 
	private String estadoCivilSexo; 
	private double avaluoDeVivienda;  
	private String activo; 
	private String tipoVivienda; 
	private String tipoEmpleo; 
	private String trabajadorExtranjero; 
	private String estadoCredito;   
	@Lob 
	@Column(length=16777216)
    private byte[] arCedula; 
	@Lob 
	@Column(length=16777216)
    private byte[] arPlanillaServicios; 
	@Lob 
	@Column(length=16777216)
    private byte[] arRolDePagos;  
	private String historialCredito; 
	private String saldoCuenta;  
	private double tasaPago; 
	private String garanteEstado;  
	private int añosCliente; 
	private int cantidadCreditos; 
	private String tipoCliente;
	
	
	
	public int getCodigoCredito() {
		return codigoCredito;
	}
	public void setCodigoCredito(int codigoCredito) {
		this.codigoCredito = codigoCredito;
	}
	public Cliente getClienteCredito() {
		return clienteCredito;
	}
	public void setClienteCredito(Cliente clienteCredito) {
		this.clienteCredito = clienteCredito;
	}
	public String getPropositoCredito() {
		return propositoCredito;
	}
	public void setPropositoCredito(String propositoCredito) {
		this.propositoCredito = propositoCredito;
	}
	public double getMontoCredito() {
		return montoCredito;
	}
	public void setMontoCredito(double montoCredito) {
		this.montoCredito = montoCredito;
	} 
	
	public String getMesesCredito() {
		return mesesCredito;
	}
	public void setMesesCredito(String mesesCredito) {
		this.mesesCredito = mesesCredito;
	}
	public String getTiempoEmpleo() {
		return tiempoEmpleo;
	}
	public void setTiempoEmpleo(String tiempoEmpleo) {
		this.tiempoEmpleo = tiempoEmpleo;
	}
	public String getEstadoCivilSexo() {
		return estadoCivilSexo;
	}
	public void setEstadoCivilSexo(String estadoCivilSexo) {
		this.estadoCivilSexo = estadoCivilSexo;
	}
	public double getAvaluoDeVivienda() {
		return avaluoDeVivienda;
	}
	public void setAvaluoDeVivienda(double avaluoDeVivienda) {
		this.avaluoDeVivienda = avaluoDeVivienda;
	}
	public String getActivo() {
		return activo;
	}
	public void setActivo(String activo) {
		this.activo = activo;
	}
	public String getTipoVivienda() {
		return tipoVivienda;
	}
	public void setTipoVivienda(String tipoVivienda) {
		this.tipoVivienda = tipoVivienda;
	}
	public String getTipoEmpleo() {
		return tipoEmpleo;
	}
	public void setTipoEmpleo(String tipoEmpleo) {
		this.tipoEmpleo = tipoEmpleo;
	}
	public String getTrabajadorExtranjero() {
		return trabajadorExtranjero;
	}
	public void setTrabajadorExtranjero(String trabajadorExtranjero) {
		this.trabajadorExtranjero = trabajadorExtranjero;
	}
	public String getEstadoCredito() {
		return estadoCredito;
	}
	public void setEstadoCredito(String estadoCredito) {
		this.estadoCredito = estadoCredito;
	}
	public byte[] getArCedula() {
		return arCedula;
	}
	public void setArCedula(byte[] arCedula) {
		this.arCedula = arCedula;
	}
	public byte[] getArPlanillaServicios() {
		return arPlanillaServicios;
	}
	public void setArPlanillaServicios(byte[] arPlanillaServicios) {
		this.arPlanillaServicios = arPlanillaServicios;
	}
	public byte[] getArRolDePagos() {
		return arRolDePagos;
	}
	public void setArRolDePagos(byte[] arRolDePagos) {
		this.arRolDePagos = arRolDePagos;
	}
	public Cliente getGaranteCredito() {
		return garanteCredito;
	}
	public void setGaranteCredito(Cliente garanteCredito) {
		this.garanteCredito = garanteCredito;
	}
	public String getHistorialCredito() {
		return historialCredito;
	}
	public void setHistorialCredito(String historialCredito) {
		this.historialCredito = historialCredito;
	}
	
	public String getSaldoCuenta() {
		return saldoCuenta;
	}
	public void setSaldoCuenta(String saldoCuenta) {
		this.saldoCuenta = saldoCuenta;
	}
	public double getTasaPago() {
		return tasaPago;
	}
	public void setTasaPago(double tasaPago) {
		this.tasaPago = tasaPago;
	}
	public int getAñosCliente() {
		return añosCliente;
	}
	public void setAñosCliente(int añosCliente) {
		this.añosCliente = añosCliente;
	}
	public int getCantidadCreditos() {
		return cantidadCreditos;
	}
	public void setCantidadCreditos(int cantidadCreditos) {
		this.cantidadCreditos = cantidadCreditos;
	}
	public String getGaranteEstado() {
		return garanteEstado;
	}
	public void setGaranteEstado(String garanteEstado) {
		this.garanteEstado = garanteEstado;
	}
	public String getTipoCliente() {
		return tipoCliente;
	}
	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}
	@Override
	public String toString() {
		return "SolicitudDeCredito [codigoCredito=" + codigoCredito + ", clienteCredito=" + clienteCredito
				+ ", garanteCredito=" + garanteCredito + ", propositoCredito=" + propositoCredito + ", montoCredito="
				+ montoCredito + ", mesesCredito=" + mesesCredito + ", tiempoEmpleo=" + tiempoEmpleo
				+ ", estadoCivilSexo=" + estadoCivilSexo + ", avaluoDeVivienda=" + avaluoDeVivienda + ", activo="
				+ activo + ", tipoVivienda=" + tipoVivienda + ", tipoEmpleo=" + tipoEmpleo + ", trabajadorExtranjero="
				+ trabajadorExtranjero + ", estadoCredito=" + estadoCredito + ", arCedula=" + Arrays.toString(arCedula)
				+ ", arPlanillaServicios=" + Arrays.toString(arPlanillaServicios) + ", arRolDePagos="
				+ Arrays.toString(arRolDePagos) + ", historialCredito=" + historialCredito + ", saldoCuenta="
				+ saldoCuenta + ", tasaPago=" + tasaPago + ", garanteEstado=" + garanteEstado + ", añosCliente="
				+ añosCliente + ", cantidadCreditos=" + cantidadCreditos + ", tipoCliente=" + tipoCliente + "]";
	}  
	
	
	
	
	
	
	
	
	
	
	
}
