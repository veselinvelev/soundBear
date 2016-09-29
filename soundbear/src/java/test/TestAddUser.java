import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.user.User;
import model.user.UserJDBCTemplate;

public class TestAddUser {

	@Test
	public void test() {
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

		UserJDBCTemplate userJDBCTemplate = (UserJDBCTemplate) context.getBean("userJDBCTemplate");

		userJDBCTemplate.addUser(new User(0, "pesho", "pesho@abv.bg", "123"));
		userJDBCTemplate.addUser(new User(0, "peshohackera", "peshohakera@abv.bg", "131234"));
		userJDBCTemplate.addUser(new User(0, "asen", "asen@abv.bg", "35436"));
		userJDBCTemplate.addUser(new User(0, "penkahackera", "penkahakera@abv.bg", "54657"));
		userJDBCTemplate.addUser(new User(0, "ivan", "vankata@abv.bg", "57564"));
	}

}