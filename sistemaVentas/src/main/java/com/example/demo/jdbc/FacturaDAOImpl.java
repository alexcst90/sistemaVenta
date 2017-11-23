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

import com.example.demo.entity.FacturaVO;
import com.example.demo.rest.FacturaService;

@Component
public class FacturaDAOImpl {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	FacturaService facturaService;
	
	RowMapper<FacturaVO> mapper = new RowMapper<FacturaVO>() {
		public FacturaVO mapRow(ResultSet rs, int rowNum) throws SQLException{
			
			FacturaVO factvo = new FacturaVO();
			factvo.setIdfactura(rs.getLong("idfactura"));
			factvo.setNumero(rs.getInt("numero"));
			factvo.setCantidad(rs.getInt("cantidad"));
			factvo.setPrecioVenta(rs.getDouble("precioVenta"));
			factvo.setTotal(rs.getDouble("total"));
			factvo.setEstado(rs.getString("estado"));
			factvo.setNombre(rs.getString("nombre"));
			factvo.setApellido(rs.getString("apellido"));
			factvo.setIdproducto(rs.getInt("idproducto"));
			factvo.setIdcliente(rs.getLong("idcliente"));
			return factvo;
		}		
	};
	
	public List<FacturaVO> findAllFacturas() throws SQLException{
		List<FacturaVO> factvo = jdbcTemplate.query("select  fa.idfactura, fa.numero,df.idproducto, df.cantidad, df.precioventa, fa.total,fa.estado,cl.idcliente, cl.nombre,cl.apellido from Factura as fa join DetalleFactura as df on fa.idfactura = df.idfactura join cliente as cl on fa.idcliente = cl.idcliente;", mapper);		
		return factvo;
	}
	
	public FacturaVO findById(Long id) {
		try {
			return jdbcTemplate.queryForObject("select  fa.idfactura, fa.numero,df.idproducto, df.cantidad, df.precioventa, fa.total,fa.estado,cl.idcliente, cl.nombre,cl.apellido from Factura as fa join DetalleFactura as df on fa.idfactura = df.idfactura join cliente as cl on fa.idcliente = cl.idcliente where fa.idfactura = ?", new Object[] {id}, mapper);
		}catch(Exception e) {
			return null;
		}
	}
	public boolean crearFactura(FacturaVO facturavo) {
		final String sql = "insert into Factura(numero, total, estado, idcliente) values(?,?,?,?)";
		KeyHolder holder = new GeneratedKeyHolder();
	      jdbcTemplate.update((connection) ->{	        
	                    PreparedStatement ps =  connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	                    
	                    ps.setLong(1, facturavo.getNumero());
	                    ps.setDouble(2,facturavo.getTotal() );
	                    ps.setString(3, facturavo.getEstado());
	                    ps.setLong(4, facturavo.getIdcliente());
	                  
	                    return ps;
	      },holder);
	     final String sql2 = "Insert into DetalleFactura(idfactura, cantidad, precioVenta, idproducto) values(?,?,?,?)";
	     
	      jdbcTemplate.update((connection) ->{	        
              PreparedStatement ps =  connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
              
              ps.setLong(1, facturavo.getIdfactura());
              ps.setDouble(2,facturavo.getCantidad() );
              ps.setDouble(3, facturavo.getPrecioVenta());
              ps.setLong(4, facturavo.getIdproducto());
            
              return ps;
	      },holder);
		return true;
	}
	
	public boolean anularFactura(FacturaVO factura) {
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
