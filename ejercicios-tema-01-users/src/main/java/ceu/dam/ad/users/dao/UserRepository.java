package ceu.dam.ad.users.dao;
 
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
import ceu.dam.ad.users.model.User;
 
public class UserRepository {
	
	/** Debe insertar un usuario en BBDD. Devuelve el ID generado. */
	public Long insert(Connection conn, User user) throws SQLException {
		String sql = "insert into user values(NULL,?,?,?,?,?,?)"; 
		PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, user.getUsername());
		stmt.setString(2, user.getEmail());
		stmt.setString(3, user.getPassword());
		stmt.setString(4, user.getName());
		if (user.getLastLoginDate() == null) {
			stmt.setDate(5, null);
		}
		else {
			stmt.setDate(5, Date.valueOf(user.getLastLoginDate())); 
		}
		stmt.setDate(6, Date.valueOf(user.getCreatedDate()));
		stmt.executeUpdate();
		ResultSet rs = stmt.getGeneratedKeys();
 
		if (rs.next()) {
			return rs.getLong(1);
		}
		return null;
 
	}
 
	
	/**
	 * Debe actualizar todos los datos de un usuario y devolver el número de
	 * registros actualizados.
	 */
	public Integer update(Connection conn, User user) throws SQLException {
	    String sql = "UPDATE user "
	               + "SET username = ?, email = ?, password = ?, name = ?, "
	               + "last_login_date = ?, created_date = ? "
	               + "WHERE id = ?";
 
	    PreparedStatement stmt = conn.prepareStatement(sql);
 
	    stmt.setString(1, user.getUsername());
	    stmt.setString(2, user.getEmail());
	    stmt.setString(3, user.getPassword());
	    stmt.setString(4, user.getName());
        stmt.setDate(5, Date.valueOf(user.getLastLoginDate())); // Podría ser null, habría que mejorar esto
	    stmt.setDate(6, Date.valueOf(user.getCreatedDate())); 
	    stmt.setLong(7, user.getId());
 
	    return stmt.executeUpdate();
	}

	/**
	 * Debe consultar un usuario por su email y devolverlo. Si no existe, devolverá
	 * null
	 */
	public User getByEmail(Connection conn, String email) throws SQLException {
		String sql = "select * from user where email = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, email);
		return consultarUser(stmt);
	}
 
	/**
	 * Debe consultar un usuario por su ID y devolverlo. Si no existe, devolverá
	 * null. NOTA: no dupliques código
	 */
	public User getById(Connection conn, Long id) throws SQLException {
		String sql = "select * from user where id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setLong(1, id);
		return consultarUser(stmt);
	}
 
	/**
	 * Debe consultar un usuario por su email y devolverlo. Si no existe, devolverá
	 * null. NOTA: no dupliques código
	 */
	public User getByUserName(Connection conn, String userName) throws SQLException {
		String sql = "select * from user where username = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, userName);
		return consultarUser(stmt);
	}
 
	private User consultarUser(PreparedStatement stmt) throws SQLException {
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			User user = new User();
			user.setId(rs.getLong("id"));
			user.setUsername(rs.getString("username"));
			user.setEmail(rs.getString("email"));
			user.setPassword(rs.getString("password"));
			user.setName(rs.getString("name"));
			Date lastDateLogin = rs.getDate("last_login_date");
			user.setLastLoginDate(lastDateLogin == null ? null : lastDateLogin.toLocalDate());
			user.setCreatedDate(rs.getDate("created_date").toLocalDate());
			return user;
		}
		return null;
	}
 
}