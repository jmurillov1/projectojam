package ec.edu.ups.coopjam.servicios;


import ec.edu.ups.coopjam.model.Cliente;

public class Respuesta {
	
	private int codigo;
	private String descripcion;
	private Cliente cliente; 
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	
	
}
