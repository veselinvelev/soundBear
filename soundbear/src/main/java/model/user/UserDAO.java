package model.user;

import java.util.List;

import javax.sql.DataSource;

public interface UserDAO {
	public void setDataSource(DataSource dataSource);
	
	 public void addUser(User user);
	 
	 public User getUser(String user, String password);
	 
	 public List<User> listUsers();
	 
	 public void deleteUser(String username);
	 
	 public void updateUser(User user);
	 
}
