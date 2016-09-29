package model.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<User> {

	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println(rs + " " + rowNum);
		
		User user = new User(
				rs.getInt("user_id"), 
				rs.getString("username"), 
				rs.getString("email"), 
				rs.getString("password"));
		
		return user;
	}
}
