package com.example.demo.rest;

import java.util.List;

import com.example.demo.entity.DetalleFactura;
import com.example.demo.entity.Factura;
import com.example.demo.entity.FacturaVO;

public interface FacturaService {

	List<Factura> findAllFacturas();

	Factura findById(Long id);

	boolean isFacturaExist(Factura factura);

	boolean crearFactura(Factura factura);

	boolean updateFactura(Factura currentFactura);


}
