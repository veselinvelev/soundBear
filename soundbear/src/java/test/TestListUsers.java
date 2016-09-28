import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.user.User;
import model.user.UserJDBCTemplate;

public class TestListUsers {

	@Test
	public void test() {
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

		UserJDBCTemplate userJDBCTemplate = (UserJDBCTemplate) context.getBean("userJDBCTemplate");

		List<User> users = userJDBCTemplate.listUsers();

		System.out.println(users);
	}

}
