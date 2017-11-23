package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.entity.Cliente;
import com.example.demo.entity.FacturaVO;
import com.example.demo.entity.Producto;
import com.example.demo.rest.FacturaService;
import com.example.demo.util.CustomErrorType;

@RestController
@RequestMapping("/factura")
public class FacturaController {

	public static final Logger logger = LoggerFactory.getLogger(FacturaController.class);
	
	@Autowired
	FacturaService facturaService;
	
	@RequestMapping(value="/facturas/", method = RequestMethod.GET)
	public ResponseEntity<List<FacturaVO>> listFacturas(@RequestHeader String usuario, @RequestHeader String password){
		
		final String uri = "http://localhost:8002/usuarios/login?usuario="+usuario+"&password="+password;
		RestTemplate restTemplate = new RestTemplate();
		String result = "";
		
		try {
			 result = restTemplate.getForObject(uri, String.class);
		}catch(Exception e) {
			e.printStackTrace();
			if(e instanceof HttpClientErrorException) {
				HttpClientErrorException ex = (HttpClientErrorException) e;
				if(ex.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
					return new ResponseEntity<List<FacturaVO>>(HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			return new ResponseEntity<List<FacturaVO>>( HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		List<FacturaVO> facturavo = facturaService.findAllFacturas();
		if(facturavo.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}		
		return new ResponseEntity<List<FacturaVO>>(facturavo, HttpStatus.OK);		
	}

	
	@RequestMapping(value = "/facturas/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getClientes(@PathVariable("id") Long id, @RequestHeader String usuario, @RequestHeader String password){
		final String uri = "http://localhost:8002/usuarios/login?usuario="+usuario+"&password="+password;
		RestTemplate restTemplate = new RestTemplate();
		String result = null;
		
		try {
			result = restTemplate.getForObject(uri, String.class);
		}catch(Exception e) {
			e.printStackTrace();
		}
		if(result.equals("Exitoso")) {
			logger.info("Mostrando Factura con el id {}", id);
			FacturaVO facturavo = facturaService.findById(id);
			if(facturavo == null) {
				logger.error("Factura con el ID {} no existe", id);
				return new ResponseEntity(new CustomErrorType("Factura con el ID " + id + " no encontrada"), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<FacturaVO>(facturavo, HttpStatus.OK);
		}else {
			return new ResponseEntity<FacturaVO>( HttpStatus.FORBIDDEN);
		}
	}
	@RequestMapping(value = "/facturas/", method = RequestMethod.POST)
	public ResponseEntity<?> crearVenta(@RequestBody FacturaVO facturavo, UriComponentsBuilder ucBuilder, 
								@RequestHeader String usuario, 	
								@RequestHeader String password
								){
		final String uri = "http://localhost:8002/usuarios/login?usuario="+usuario+"&password="+password;
		RestTemplate restTemplate = new RestTemplate();
		String result = null;
		
		try {
			result = restTemplate.getForObject(uri, String.class);
		}catch(Exception e) {
			e.printStackTrace();
		}
		if(result.equals("Exitoso")) {
			logger.info("Insertando factura: {}", facturavo);
			if(facturaService.isFacturaExist(facturavo)){
				logger.error("No se puede crear. Factura ya existe");
				return new ResponseEntity(new CustomErrorType("No se puede crear. Factura ya existe."), HttpStatus.CONFLICT);
			}else {
				facturaService.crearFactura(facturavo);
				HttpHeaders headers = new HttpHeaders();
		        headers.setLocation(ucBuilder.path("/factura/facturas/{id}").
		        		buildAndExpand(facturavo.getIdfactura()).toUri());
		        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
			}
		}
		return null;
		
	}
	
	//Anular Factura
	@RequestMapping(value="/facturas/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateFacturaVO(@PathVariable("id") long id, @RequestBody FacturaVO facturavo, @RequestHeader String usuario, @RequestHeader String password){
		final String uri = "http://localhost:8002/usuarios/login?usuario="+usuario+"&password="+password;
		RestTemplate restTemplate = new RestTemplate();
		String result = null;
		
		try {
			result = restTemplate.getForObject(uri, String.class);
		}catch(Exception e) {
			e.printStackTrace();
			if(e instanceof HttpClientErrorException){
				HttpClientErrorException ex = (HttpClientErrorException) e;
				if(ex.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
					return new ResponseEntity<FacturaVO>(HttpStatus.FORBIDDEN);
				}
		}
		return new ResponseEntity<List<Producto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.info("Anulando factura con el id {}", id);
		FacturaVO currentFactura = facturaService.findById(id);
		if(currentFactura == null) {
			logger.error("Imposible anular factura porque no existe");
			return new ResponseEntity<Object>(new CustomErrorType("Unable to upate. factura with id " + id + " not found."),HttpStatus.NOT_FOUND);
		}
		facturaService.updateFactura(currentFactura);
		return new ResponseEntity<Object>(new CustomErrorType("Factura con el id " + id + " anulada"),HttpStatus.OK);
	}
}
