package model.user;

import java.sql.ResultSet;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserJDBCTemplate implements UserDAO {
	private static final String UPDATE_USER = "UPDATE users SET username = ?, email = ?, password = md5(?) WHERE username = ?";
	private static final String DELETE_USERS_SQL = "DELETE FROM users";
	private static final String DELETE_USER_SQL = "DELETE FROM users WHERE username = ?";
	private static final String LIST_USERS_SQL = "SELECT * FROM users";
	private static final String GET_USER_SQL = "SELECT * FROM users WHERE username = ? AND password = md5(?)";
	private static final String ADD_USER_SQL = "INSERT INTO users VALUES (null, ?, ?, md5(?))";
	private static final String VALID_USERNAME_SQL = "SELECT * FROM users WHERE username = ?";
	private static final String VALID_EMAIL_SQL = "SELECT * FROM users WHERE email = ?";

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

		System.out.println("===========================");
		System.out.println(username);
		System.out.println(password);
		System.out.println(jdbcTemplateObject);

		User user = null;

		try {
			user = jdbcTemplateObject.queryForObject(GET_USER_SQL, new Object[] { username, password },
					new UserMapper());

		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return user;
		}

		System.out.println(user);

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

	public boolean isValidUsername(String username) {
		try {
			jdbcTemplateObject.queryForObject(VALID_USERNAME_SQL, new Object[] { username }, new UserMapper());
		}
		catch (EmptyResultDataAccessException e) {
			return true;
		}

		return false;
	}
	
	public boolean isValidEmail(String email){
		try {
			jdbcTemplateObject.queryForObject(VALID_EMAIL_SQL, new Object[] { email }, new UserMapper());
		}
		catch (EmptyResultDataAccessException e) {
			return true;
		}

		return false;
	}
}
