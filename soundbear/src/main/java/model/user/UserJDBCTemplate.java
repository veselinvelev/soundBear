package model.user;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class UserJDBCTemplate implements UserDAO {
	private static final String UPDATE_USER = "UPDATE users SET username = ?, email = ?, password = md5(?) WHERE username = ?";
	private static final String DELETE_USERS_SQL = "DELETE FROM users";
	private static final String DELETE_USER_SQL = "DELETE FROM users WHERE username = ?";
	private static final String LIST_USERS_SQL = "SELECT * FROM users";
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

		User user = jdbcTemplateObject.queryForObject(GET_USER_SQL, new Object[] { username, password },
				new UserMapper());

		return user;
	}

	public List<User> listUsers() {
		List<User> users = jdbcTemplateObject.query(LIST_USERS_SQL, new UserMapper());

		return users;
	}

	public void deleteUser(String username) {
		jdbcTemplateObject.update(DELETE_USER_SQL, username);
	}

	public void updateUser(String username, User user) {
		jdbcTemplateObject.update(UPDATE_USER, user.getUsername(), user.getEmail(), user.getPassword(), username);
	}

	public void deleteUsers() {
		jdbcTemplateObject.update(DELETE_USERS_SQL);
	}

}
