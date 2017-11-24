package com.example.demo.entity;

import java.util.List;

public class Factura {
	private Long idfactura;
	private Integer numero;
	private Double total;
	private Long idcliente;
	private String estado;
	private List<DetalleFactura> detalleFactura;
	
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
	public Long getIdcliente() {
		return idcliente;
	}
	public void setIdcliente(Long idcliente) {
		this.idcliente = idcliente;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public List<DetalleFactura> getDetalleFactura() {
		return detalleFactura;
	}
	public void setDetalleFactura(List<DetalleFactura> detalleFactura) {
		this.detalleFactura = detalleFactura;
	}
}
