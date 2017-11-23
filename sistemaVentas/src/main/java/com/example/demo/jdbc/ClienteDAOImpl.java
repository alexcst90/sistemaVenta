package com.example.demo.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.entity.Cliente;
import com.example.demo.entity.Producto;
import com.example.demo.rest.ClienteService;
import com.example.demo.util.CustomErrorType;

@Component
public class ClienteDAOImpl {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	ClienteService clienteService;
	
	RowMapper<Cliente> mapper = new RowMapper<Cliente>() {
		public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException{
			
			Cliente client = new Cliente();
			client.setIdcliente(rs.getLong("idcliente"));
			client.setNombre(rs.getString("nombre"));
			client.setApellido(rs.getString("apellido"));
			client.setTelefono(rs.getString("telefono"));
			
			return client;
		}		
	};
	

	public List<Cliente> findAllClientes() {
		List<Cliente> client = jdbcTemplate.query("SELECT * from cliente", mapper);	    
		  return client;
	}


	public Cliente findById(Long id) {
		try {
			return jdbcTemplate.queryForObject("select * from cliente where idcliente = ?", new Object[] { id}, mapper);
		}catch(Exception e) {
			return null;
		}
	}
	
	
	public boolean saveCliente(Cliente cliente) {
		try {
			final String sql ="insert into cliente (idcliente, nombre, apellido, telefono) values(?,?,?,?)";
			  KeyHolder holder = new GeneratedKeyHolder();
		      jdbcTemplate.update((connection) ->{	        
		                    PreparedStatement ps =  connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		                    
		                    ps.setLong(1, cliente.getIdcliente());
		                    ps.setString(2, cliente.getNombre());
		                    ps.setString(3, cliente.getApellido());
		                    ps.setString(4, cliente.getTelefono());		                    
		                   
		                    return ps;		
		      },holder);
		      
		     
		      return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean updateCliente(Cliente cliente) {
		try {
			final String sql = "update cliente set nombre = ?, apellido = ?, telefono = ? where idcliente = ?";
			int i = jdbcTemplate.update(sql, new Object[] {cliente.getNombre(),cliente.getApellido(), cliente.getTelefono(), cliente.getIdcliente()});
			if(i > 0) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}		
	}
	public boolean deleteCliente(Cliente cliente) {
		try {
			final String sql ="DELETE FROM cliente where idcliente = ?";
			int i = jdbcTemplate.update(sql, new Object[] { cliente.getIdcliente()});
			if(i>0) {
				return true;
			}else {
				return false;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
}
