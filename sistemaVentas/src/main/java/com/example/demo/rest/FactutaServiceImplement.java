package com.example.demo.rest;


import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.FacturaVO;
import com.example.demo.jdbc.FacturaDAOImpl;

@Service("facturaService")
public class FactutaServiceImplement implements FacturaService{

	@Autowired
	FacturaDAOImpl facturaDao;
	
	@Override
	public List<FacturaVO> findAllFacturas() {
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
	public FacturaVO findById(Long id) {
		// TODO Auto-generated method stub
		return facturaDao.findById(id);
	}

	@Override
	public boolean isFacturaExist(FacturaVO facturavo) {
		// TODO Auto-generated method stub
		return findById(facturavo.getIdfactura())!=null;
	}

	@Override
	public boolean crearFactura(FacturaVO facturavo) {
		// TODO Auto-generated method stub
		return facturaDao.crearFactura(facturavo);
	}

	@Override
	public boolean updateFactura(FacturaVO factura) {
		// TODO Auto-generated method stub
		return facturaDao.anularFactura(factura);
	}
	
}
