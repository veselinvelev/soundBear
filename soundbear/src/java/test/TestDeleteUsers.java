import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.user.UserJDBCTemplate;

public class TestDeleteUsers {

	@Test
	public void test() {
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

		UserJDBCTemplate userJDBCTemplate = (UserJDBCTemplate) context.getBean("userJDBCTemplate");
		
		userJDBCTemplate.deleteUsers();
	}

}
