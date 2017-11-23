package com.example.demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Cliente;
import com.example.demo.entity.Producto;
import com.example.demo.jdbc.ClienteDAOImpl;

@Service("clienteService")
public class ClienteServiceImplement implements ClienteService {
	@Autowired
	ClienteDAOImpl clienteDao;
	
	@Override
	public List<Cliente> findAllClientes() { 		
		// TODO Auto-generated method stub		
		return clienteDao.findAllClientes();
	}

	@Override
	public Cliente findById(Long id) {
		// TODO Auto-generated method stub
		return clienteDao.findById(id);
	}
	
	@Override
	public boolean isClienteExist(Cliente cliente) {
		// TODO Auto-generated method stub
		return findById(cliente.getIdcliente())!=null;
	}

	@Override
	public boolean saveCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		return clienteDao.saveCliente(cliente);
	}

	@Override
	public boolean updateCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		return clienteDao.updateCliente(cliente);
	}

	@Override
	public boolean deleteCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		return clienteDao.deleteCliente(cliente);
	}
}
