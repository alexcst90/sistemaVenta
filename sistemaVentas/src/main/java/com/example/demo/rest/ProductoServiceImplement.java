package com.example.demo.rest;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.demo.entity.Producto;
import com.example.demo.jdbc.ProductoDAOImpl;

@Service("productoService")
public class ProductoServiceImplement implements ProductoService {
	
	@Autowired 
	ProductoDAOImpl productDao;
	
	@Override
	public List<Producto> findAllProducto() { 		
		// TODO Auto-generated method stub
		
		return productDao.findAllProducto();
	}
	
	public Producto findById(Long id){
		return productDao.findById(id); 
	}

	@Override
	public boolean isProductoExist(Producto producto) {
		// TODO Auto-generated method stub
		return findById(producto.getIdproducto())!=null;
	}

	@Override
	public boolean saveProduct(Producto producto) {
		// TODO Auto-generated method stub
		
		return productDao.saveProduct(producto);		
	}

	@Override
	public boolean updateProducto(Producto producto) {
		// TODO Auto-generated method stub
		return productDao.updateProducto(producto);
	}

	@Override
	public boolean deleteProducto(Producto producto) {
		// TODO Auto-generated method stub
		return productDao.deleteProducto(producto);
	}
	
	
}
