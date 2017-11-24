package com.example.demo.entity;

public class DetalleFactura {
	private Long iddetalle;
	private Integer idfactura;
	private Integer precioVenta;
	private Integer idproducto;
	private Integer cantidad;
	
	public Long getIddetalle() {
		return iddetalle;
	}
	public void setIddetalle(Long iddetalle) {
		this.iddetalle = iddetalle;
	}
	public Integer getPrecioVenta() {
		return precioVenta;
	}
	public void setPrecioVenta(Integer precioVenta) {
		this.precioVenta = precioVenta;
	}
	public Integer getIdproducto() {
		return idproducto;
	}
	public void setIdproducto(Integer idproducto) {
		this.idproducto = idproducto;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public Integer getIdfactura() {
		return idfactura;
	}
	public void setIdfactura(Integer idfactura) {
		this.idfactura = idfactura;
	}
	
}
