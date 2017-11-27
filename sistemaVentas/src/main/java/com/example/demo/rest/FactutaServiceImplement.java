package com.example.demo.rest;


import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Factura;
import com.example.demo.jdbc.FacturaDAOImpl;

@Service("facturaService")
public class FactutaServiceImplement implements FacturaService{

	@Autowired
	FacturaDAOImpl facturaDao;
	
	@Override
	public List<Factura> findAllFacturas() {
		// TODO Auto-generated method stub
		try {
			return facturaDao.findAllFacturas();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Factura findById(Long id) {
		// TODO Auto-generated method stub
		return facturaDao.findById(id);
	}

	public boolean isFacturaExist(Factura factura) {
		// TODO Auto-generated method stub
		return findById(factura.getIdfactura())!=null;
	}

	@Override
	public boolean crearFactura(Factura factura) {
		// TODO Auto-generated method stub
		return facturaDao.crearFactura(factura);
	}

	@Override
	public boolean updateFactura(Factura factura) {
		// TODO Auto-generated method stub
		return facturaDao.anularFactura(factura);
	}
}
