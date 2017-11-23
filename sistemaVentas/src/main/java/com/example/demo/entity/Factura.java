package com.example.demo.entity;

public class Factura {
	private Long idfactura;
	private Integer numero;
	private Double total;
	private Integer idcliente;
	private DetalleFactura detalleFa;
	
	
	
	public Long getIdfactura() {
		return idfactura;
	}
	public void setIdfactura(Long idfactura) {
		this.idfactura = idfactura;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Integer getIdcliente() {
		return idcliente;
	}
	public void setIdcliente(Integer idcliente) {
		this.idcliente = idcliente;
	}
	
	public DetalleFactura getDetalleFactura() {
		return detalleFa;
	}
	public void setDetalleFactura(DetalleFactura detalleFa) {
		this.detalleFa = detalleFa;
	}
	
}
