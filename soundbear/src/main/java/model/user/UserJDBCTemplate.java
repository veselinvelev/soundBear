package model.user;

import java.util.List;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserJDBCTemplate implements UserDAO {
	private static final String GET_USER_SQL = "SELECT * FROM users WHERE username = ? AND password = md5(?)";
	private static final String ADD_USER_SQL = "INSERT INTO users VALUES (null, ?, ?, md5(?))";
	// private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource) {
		// this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public void addUser(User user) {
		jdbcTemplateObject.update(ADD_USER_SQL, user.getUsername(), user.getEmail(), user.getPassword());
	}

	public User getUser(String username, String password) {
		
		User user = jdbcTemplateObject.queryForObject(GET_USER_SQL, new Object[]{username,password},new UserMapper());
		
		return user;
	}

	public List<User> listUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteUser(String username) {
		// TODO Auto-generated method stub

	}

	public void updateUser(User user) {
		// TODO Auto-generated method stub

	}

}
