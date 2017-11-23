package com.example.demo.entity;


public class Producto {

	
	private Long idproducto;
	private String nombre;
	private Double precioLista;
	private String descripcion;
	private Integer stock;
	
	public Long getIdproducto() {
		return idproducto;
	}
	public void setIdProducto(Long idproducto) {
		this.idproducto = idproducto;		
	}
	
	public String getNombreProducto() {
		return nombre;
	}
	public void setNombreProducto(String nombre) {
		this.nombre = nombre;		
	}
	
	public Double getPrecioLista() {
		return precioLista;
	}
	public void setPrecioLista(Double precioLista) {
		this.precioLista = precioLista;		
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;		
	}
	
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;		
	}
	
}
