package com.example.demo.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.example.demo.entity.DetalleFactura;
import com.example.demo.entity.Factura;
import com.example.demo.rest.FacturaService;

@Component
public class FacturaDAOImpl {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	FacturaService facturaService;
	
	RowMapper<Factura> mapper = new RowMapper<Factura>() {
		public Factura mapRow(ResultSet rs, int rowNum) throws SQLException{
			
			Factura factura = new Factura();
			
			
			factura.setIdfactura(rs.getLong("idfactura"));
			factura.setNumero(rs.getInt("numero"));
			factura.setTotal(rs.getDouble("total"));
			factura.setIdcliente(rs.getLong("idcliente"));
			
			return factura;
		}		
	};
	
	public List<Factura> findAllFacturas() throws SQLException{
		//List<FacturaVO> factvo = jdbcTemplate.query("select  fa.idfactura, fa.numero,df.idproducto, df.cantidad, df.precioventa, fa.total,fa.estado,cl.idcliente, cl.nombre,cl.apellido from Factura as fa join DetalleFactura as df on fa.idfactura = df.idfactura join cliente as cl on fa.idcliente = cl.idcliente;", mapper);
		List<Factura> factura = jdbcTemplate.query("select * from Factrua", mapper);
		return factura;
	}
	
	
	public Factura findById(Long id) {
		try {
			//return jdbcTemplate.queryForObject("select  fa.idfactura, fa.numero,df.idproducto, df.cantidad, df.precioventa, fa.total,fa.estado,cl.idcliente, cl.nombre,cl.apellido from Factura as fa join DetalleFactura as df on fa.idfactura = df.idfactura join cliente as cl on fa.idcliente = cl.idcliente where fa.idfactura = ?", new Object[] {id}, mapper);
			return jdbcTemplate.queryForObject("select * from Factura where idfactura = ?", new Object[] {id}, mapper);
		}catch(Exception e) {
			return null;
		}
	}
	public boolean crearFactura(Factura factura) {
		final String sql = "insert into Factura(numero, total, estado, idcliente) values(?,?,?,?)";
		KeyHolder holder = new GeneratedKeyHolder();
	      jdbcTemplate.update((connection) ->{	        
	                    PreparedStatement ps =  connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	                    
	                    ps.setLong(1, factura.getNumero());
	                    ps.setDouble(2,factura.getTotal() );
	                    ps.setString(3, factura.getEstado());
	                    ps.setLong(4, factura.getIdcliente());
	                  
	                    return ps;
	      },holder);
	      //holder.getKeys().forEach((k,v)-> System.out.println("key:"+ k +  "value" + v));
	      Long idf =  Long.valueOf(holder.getKeys().get("GENERATED_KEY").toString());
	      
	      for(DetalleFactura detalle: factura.getDetalleFactura()) {
		    	
	 	      final String sql2 = "Insert into DetalleFactura(idfactura, cantidad, precioVenta, idproducto) values(?,?,?,?)";
	 	     
	 	      jdbcTemplate.update((connection) ->{	        
	               PreparedStatement ps =  connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
	               
	               ps.setLong(1, idf);
	               ps.setDouble(2,detalle.getCantidad() );
	               ps.setDouble(3, detalle.getPrecioVenta());
	               ps.setLong(4, detalle.getIdproducto());
	             
	               return ps;
	 	      },holder);
	 	}
		return true;
	}
	
	public boolean anularFactura(Factura factura) {
		try {
			final String sql = "UPDATE Factura set estado = 'Anulado' where idfactura = ?";
			int i = jdbcTemplate.update(sql,  new Object[] {factura.getIdfactura() });
			if (i>0) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
