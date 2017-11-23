package com.example.demo.rest;

import java.util.List;

import com.example.demo.entity.Cliente;

public interface ClienteService {

	List<Cliente> findAllClientes();

	Cliente findById(Long id);

	boolean isClienteExist(Cliente clientes);


	boolean saveCliente(Cliente cliente);

	boolean updateCliente(Cliente currentClient);

	boolean deleteCliente(Cliente currentCliente);

}
