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
import com.example.demo.entity.Producto;
import com.example.demo.rest.ClienteService;
import com.example.demo.util.CustomErrorType;


@RestController
@RequestMapping("/cliente")
public class ClienteController {
	
	public static final Logger logger = LoggerFactory.getLogger(ClienteController.class);
	
	@Autowired
	ClienteService clienteService;
	
	
	@RequestMapping(value="/clientes/", method = RequestMethod.GET)
	public ResponseEntity<List<Cliente>> listCliente(@RequestHeader String usuario, @RequestHeader String password){
		final String uri = "http://localhost:8002/usuarios/login?usuario="+usuario+"&password="+password;
		RestTemplate restTemplate = new RestTemplate();
		String result = null;
		
		try {
			 result = restTemplate.getForObject(uri, String.class);
		}catch(Exception e) {
			e.printStackTrace();
			if(e instanceof HttpClientErrorException) {
				HttpClientErrorException ex = (HttpClientErrorException) e;
				if( ex.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
					return new ResponseEntity<List<Cliente>>(HttpStatus.FORBIDDEN);
				}
			}
			return new ResponseEntity<List<Cliente>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
			List<Cliente> cliente = clienteService.findAllClientes();
			if(cliente.isEmpty()) {
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}		
			return new ResponseEntity<List<Cliente>>(cliente, HttpStatus.OK);
		
	}
	
	//cliente por ID
	@RequestMapping(value="/clientes/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getClientes(@PathVariable("id") Long id, @RequestHeader String usuario, @RequestHeader String password){
		
		final String uri = "http://localhost:8002/usuarios/login?usuario="+usuario+"&password="+password;
		RestTemplate restTemplate = new RestTemplate();
		String result = null;
		
		try {
			 result = restTemplate.getForObject(uri, String.class);
		}catch(Exception e) {
			e.printStackTrace();
			if(e instanceof HttpClientErrorException) {
				HttpClientErrorException ex = (HttpClientErrorException) e;
				if( ex.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
					return new ResponseEntity<Cliente>(HttpStatus.FORBIDDEN);
				}
			}
			return new ResponseEntity<Cliente>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
			logger.info("mostrando Clientes con el id {} ", id);
			
			Cliente client = clienteService.findById(id);	
			if(client == null) {
				logger.error("Cliente con el id {} no encontrado.", id);
				return new ResponseEntity(new CustomErrorType("Cliente con el id " + id + " no encontrado"), HttpStatus.NOT_FOUND);			
			}
			
			return new ResponseEntity<Cliente>(client, HttpStatus.OK);
		
		
	}
	
	
	//Insertar Producto
		@RequestMapping(value="/clientes/", method = RequestMethod.POST)
		public ResponseEntity<?> crearClientes(@RequestBody Cliente cliente, UriComponentsBuilder ucBuilder,  @RequestHeader String usuario, @RequestHeader String password){
			
			final String uri = "http://localhost:8002/usuarios/login?usuario="+usuario+"&password="+password;
			RestTemplate restTemplate = new RestTemplate();
			String result = null;
			
			try {
				 result = restTemplate.getForObject(uri, String.class);
			}catch(Exception e) {
				e.printStackTrace();
				if(e instanceof HttpClientErrorException) {
					HttpClientErrorException ex = (HttpClientErrorException) e;
					if( ex.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
						return new ResponseEntity<Cliente>(HttpStatus.FORBIDDEN);
					}
				}
				return new ResponseEntity<Cliente>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
				logger.info("Insertando producto: {}", cliente);
				if(clienteService.isClienteExist(cliente)){
					logger.error("No se puede crear. Producto con el nombre {} ya existe", cliente.getNombre());
					return new ResponseEntity(new CustomErrorType("No se puede crear. Producto con el nombre {}" + cliente.getNombre() + "ya existe."), HttpStatus.CONFLICT);
				}
				clienteService.saveCliente(cliente);
				HttpHeaders headers = new HttpHeaders();
		        headers.setLocation(ucBuilder.path("/cliente/clientes/{id}").
		        		buildAndExpand(cliente.getIdcliente()).toUri());
		        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
		}
		
		//Actualizar producto
				@RequestMapping(value="/clientes/{id}", method = RequestMethod.PUT)
				public ResponseEntity<?> updateCliente(@PathVariable("id") long id, @RequestBody Cliente cliente,  @RequestHeader String usuario, @RequestHeader String password) {
					final String uri = "http://localhost:8002/usuarios/login?usuario="+usuario+"&password="+password;
					RestTemplate restTemplate = new RestTemplate();
					String result = null;
					
					try {
						 result = restTemplate.getForObject(uri, String.class);
					}catch(Exception e) {
						e.printStackTrace();
						if(e instanceof HttpClientErrorException) {
							HttpClientErrorException ex = (HttpClientErrorException) e;
							if( ex.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
								return new ResponseEntity<Cliente>(HttpStatus.FORBIDDEN);
							}
						}
						return new ResponseEntity<Cliente>(HttpStatus.INTERNAL_SERVER_ERROR);
					}					
						logger.info("Updating cliente with id {}", id);
				        Cliente currentClient = clienteService.findById(id);
				        if (currentClient == null) {
				            logger.error("imposible actualizar. Cliente with id {} not found.", id);
				            return new ResponseEntity(new CustomErrorType("Unable to update. Cliente with id " + id + " not found."),HttpStatus.NOT_FOUND);
				        }
				        currentClient.setNombre(cliente.getNombre());
				        currentClient.setApellido(cliente.getApellido());
				        currentClient.setTelefono(cliente.getTelefono());
				      
				        clienteService.updateCliente(currentClient);
				        return new ResponseEntity<Cliente>(currentClient, HttpStatus.OK);				
				}
				//Eliminar Cliente
				@RequestMapping(value="/clientes/{id}", method = RequestMethod.DELETE)
				public ResponseEntity<?> deleteCliente(@PathVariable("id") long id, @RequestHeader String usuario, @RequestHeader String password){
					
					final String uri = "http://localhost:8002/usuarios/login?usuario=&password="+password;
					RestTemplate restTemplate = new RestTemplate();
					String result = null;
					
					try {
						 result = restTemplate.getForObject(uri, String.class);
					}catch(Exception e) {
						e.printStackTrace();
						if(e instanceof HttpClientErrorException) {
							HttpClientErrorException ex = (HttpClientErrorException) e;
							if( ex.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
								return new ResponseEntity<Cliente>(HttpStatus.FORBIDDEN);
							}
						}
						return new ResponseEntity<Cliente>(HttpStatus.INTERNAL_SERVER_ERROR);
					}
						logger.info("Deleting Cliente with id {}", id);
						Cliente currentCliente = clienteService.findById(id);
						if(currentCliente == null) {
							logger.error("imposible eliminar. cliente with id {} not found.", id);
				            return new ResponseEntity(new CustomErrorType("Imposible eliminar. cliente with id " + id + " not found."),HttpStatus.NOT_FOUND);
						}
						clienteService.deleteCliente(currentCliente);
						return new ResponseEntity<Cliente>( HttpStatus.OK);					
				}
}
