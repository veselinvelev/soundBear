import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.user.User;
import model.user.UserJDBCTemplate;

public class TestUpdateUser {

	@Test
	public void test() {
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

		UserJDBCTemplate userJDBCTemplate = (UserJDBCTemplate) context.getBean("userJDBCTemplate");

		userJDBCTemplate.updateUser("peshohackera", new User(0, "peter", "peter@abv.bg", "2341"));
	}

}
