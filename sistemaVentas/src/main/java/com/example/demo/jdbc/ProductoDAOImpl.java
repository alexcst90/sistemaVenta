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

import com.example.demo.entity.Producto;
import com.example.demo.rest.ProductoService;

@Component
public class ProductoDAOImpl implements ProductoService{
	
	
	
	
	@Autowired
	
	private JdbcTemplate jdbcTemplate;
	ProductoService productoService;

	RowMapper<Producto> mapper = new RowMapper<Producto>() {
		public Producto mapRow(ResultSet rs, int rowNum) throws SQLException{
			
			Producto prod = new Producto();
			prod.setIdProducto(rs.getLong("idproducto"));
			prod.setNombreProducto(rs.getString("nombre"));
			prod.setPrecioLista(rs.getDouble("precioLista"));
			prod.setDescripcion(rs.getString("descripcion"));
			prod.setStock(rs.getInt("stock"));
			return prod;
		}		
	};
	 
	
	@Override
	public List<Producto> findAllProducto() {
		List<Producto> pro = jdbcTemplate.query("SELECT * from Producto", mapper);	    
		  return pro;
	}
	@Override
	public Producto findById(Long id){
		try {
			return jdbcTemplate.queryForObject("select * from Producto where idproducto = ?", new Object[] { id}, mapper);
		}catch(Exception e) {
			return null;
		}
		
					
	}
	@Override
	public boolean isProductoExist(Producto producto) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean saveProduct(Producto producto) {
		try {
			final String sql ="insert into Producto (idproducto, nombre, precioLista, descripcion, stock) values(?,?,?,?,?)";
			  KeyHolder holder = new GeneratedKeyHolder();
		      jdbcTemplate.update((connection) ->{	        
		                    PreparedStatement ps =  connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		                    
		                    ps.setLong(1, producto.getIdproducto());
		                    ps.setString(2, producto.getNombreProducto());
		                    ps.setDouble(3, producto.getPrecioLista());
		                    ps.setString(4, producto.getDescripcion());		                    
		                    ps.setInt(5, producto.getStock());
		                    return ps;		
		      },holder);
		      
		      //producto.setIdProducto(Long.valueOf(holder.getKeys().get("id").toString()));
		      return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	@Override
	public boolean updateProducto(Producto producto) {
		try {
			final String sql = "update Producto set nombre = ?, precioLista = ?, descripcion = ?, stock = ? where idproducto = ?";
			int i = jdbcTemplate.update(sql, new Object[] {producto.getNombreProducto(),producto.getPrecioLista(), producto.getDescripcion(), producto.getStock(),producto.getIdproducto()});
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
	
	@Override
	public boolean deleteProducto(Producto producto) {
		try {
			final String sql ="DELETE FROM Producto where idproducto = ?";
			int i = jdbcTemplate.update(sql, new Object[] { producto.getIdproducto()});
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
