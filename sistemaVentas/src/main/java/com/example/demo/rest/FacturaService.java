package com.example.demo.rest;

import java.util.List;

import com.example.demo.entity.FacturaVO;

public interface FacturaService {

	List<FacturaVO> findAllFacturas();

	FacturaVO findById(Long id);

	boolean isFacturaExist(FacturaVO facturavo);

	boolean crearFactura(FacturaVO facturavo);

	boolean updateFactura(FacturaVO currentFactura);

}
