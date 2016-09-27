import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.user.User;
import model.user.UserJDBCTemplate;

public class TestGetUser {

	@Test
	public void test() {
		
		ApplicationContext context = 
	             new ClassPathXmlApplicationContext("Beans.xml");

	      UserJDBCTemplate userJDBCTemplate = 
	      (UserJDBCTemplate)context.getBean("userJDBCTemplate");
	      
	      User pesho = userJDBCTemplate.getUser("peshohakera", "131234");
	      
	      System.out.println(pesho);
		
		
		
	}

}
