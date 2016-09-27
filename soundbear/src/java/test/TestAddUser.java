

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.user.User;
import model.user.UserJDBCTemplate;
public class TestAddUser {

	@Test
	public void test() {
		ApplicationContext context = 
	             new ClassPathXmlApplicationContext("Beans.xml");

	      UserJDBCTemplate userJDBCTemplate = 
	      (UserJDBCTemplate)context.getBean("userJDBCTemplate");
	    
	      userJDBCTemplate.addUser(new User(0, "peshohakera", "peshohakera@av.bg", "131234"));
	}

}