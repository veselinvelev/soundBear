package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.JsonObject;

import model.user.UserJDBCTemplate;

@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		UserJDBCTemplate userJDBCTemplate = (UserJDBCTemplate) context.getBean("userJDBCTemplate");
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		
		JsonObject validation = new JsonObject();
		validation.addProperty("validUsername", userJDBCTemplate.isValidUsername(username));
		validation.addProperty("validEmail", userJDBCTemplate.isValidEmail(email));
		
		response.getWriter().print(validation.toString());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
