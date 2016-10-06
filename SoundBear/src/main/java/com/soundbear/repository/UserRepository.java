package com.soundbear.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.soundbear.model.app.User;
import com.soundbear.model.app.exceptions.UserException;

@Component
public class UserRepository implements UserDAO {
	private static final int ACTIVATION_LINK_VALID_PERIOD = 5;
	private static final String SET_ACTIVE_STATUS_SQL = "UPDATE users SET is_active = 1 WHERE username=?";
	private static final String UPDATE_PASSWORD_SQL = "UPDATE users SET password = md5(?) WHERE email = ?";
	private static final String UPDATE_USER = "UPDATE users SET username = ?, email = ?, password = md5(?) WHERE username = ?";
	private static final String DELETE_USERS_SQL = "DELETE FROM users";
	private static final String DELETE_USER_SQL = "DELETE FROM users WHERE username = ?";
	private static final String LIST_USERS_SQL = "SELECT * FROM users";
	private static final String GET_USER_SQL = "SELECT * FROM users WHERE username = ? AND password = md5(?)";
	private static final String GET_USER_BY_NAME_SQL = "SELECT * FROM users WHERE username = ?";
	private static final String ADD_USER_SQL = "INSERT INTO users VALUES (null, ?, ?, md5(?),?,?)";
	private static final String VALID_USERNAME_SQL = "SELECT * FROM users WHERE username = ?";
	private static final String VALID_EMAIL_SQL = "SELECT * FROM users WHERE email = ?";
	private static final String DELETE_INACTIVE_USERS_SQL = "DELETE FROM users WHERE is_active = 0 AND DATE_SUB(registration_date, INTERVAL ? MINUTE)";

	private JdbcTemplate jdbcTemplate;

	public UserRepository() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	public UserRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public int addUser(User user) {
		int result = jdbcTemplate.update(ADD_USER_SQL, user.getUsername(), user.getEmail(), user.getPassword(),
				user.isActive(), user.getRegistrationDate());
		return result;
	}

	public void updateActiveStatus(String username) {
		jdbcTemplate.update(SET_ACTIVE_STATUS_SQL, username);
	}

	public User getUser(String username, String password) {

		User user = null;

		try {
			user = jdbcTemplate.queryForObject(GET_USER_SQL, new Object[] { username, password }, new UserMapper());

		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return user;
		}

		System.out.println(user);

		return user;
	}

	public User getUserByName(String username) {

		User user = null;

		try {
			user = jdbcTemplate.queryForObject(GET_USER_BY_NAME_SQL, new Object[] { username }, new UserMapper());
			System.out.println("======================================");
			System.err.println(user);

		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return user;
		}

		System.out.println(user);

		return user;
	}

	public List<User> listUsers() {
		List<User> users = jdbcTemplate.query(LIST_USERS_SQL, new UserMapper());

		return users;
	}

	public void deleteUser(String username) {
		jdbcTemplate.update(DELETE_USER_SQL, username);
	}

	public void updateUser(String username, User user) {
		jdbcTemplate.update(UPDATE_USER, user.getUsername(), user.getEmail(), user.getPassword(), username);
	}

	public void updatePassword(String email, String password) {

		if (email != null && !email.equals("") && password != null && !password.equals("")) {
			jdbcTemplate.update(UPDATE_PASSWORD_SQL, password, email);
		}

	}

	public void deleteUsers() {
		jdbcTemplate.update(DELETE_USERS_SQL);
	}

	public boolean isValidUsername(String username) {
		try {
			jdbcTemplate.queryForObject(VALID_USERNAME_SQL, new Object[] { username }, new UserMapper());
		} catch (EmptyResultDataAccessException e) {
			return true;
		}

		return false;
	}

	public boolean isValidEmail(String email) {
		try {
			jdbcTemplate.queryForObject(VALID_EMAIL_SQL, new Object[] { email }, new UserMapper());
		} catch (EmptyResultDataAccessException e) {
			return true;
		}

		return false;
	}

	public class UserMapper implements RowMapper<User> {

		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = null;
			try {
				user = new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("email"),
						rs.getString("password"), rs.getInt("is_active"), rs.getDate("registration_date"));
			} catch (UserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return user;
		}
	}

	public void clearInactive() {
		jdbcTemplate.update(DELETE_INACTIVE_USERS_SQL, ACTIVATION_LINK_VALID_PERIOD);
	}

}
