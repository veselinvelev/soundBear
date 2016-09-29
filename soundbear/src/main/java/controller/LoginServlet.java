package controller;

import java.io.IOException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.user.User;
import model.user.UserDAO;
import model.user.UserJDBCTemplate;

@WebServlet("/Login")
public class LoginServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		super.doGet(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Scanner sc = new Scanner(request.getInputStream());
		StringBuilder buf = new StringBuilder();
		while (sc.hasNextLine()) {
			buf.append(sc.nextLine());
		}
		JsonObject json = (JsonObject) new JsonParser().parse(buf.toString());
		String username = null;
		String password = null;

		if (json != null) {
			username = json.get("username").getAsString().trim();
			password = json.get("password").getAsString().trim();
		}

		User user = null;

		if (isStringValid(username) && isStringValid(password)) {

			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

			UserJDBCTemplate userJDBCTemplate = (UserJDBCTemplate) context.getBean("userJDBCTemplate");
			UserDAO userDAO = userJDBCTemplate;
			user = userDAO.getUser(username, password);
			if (user != null) {
				System.out.println("USER" + user);

				HttpSession session = request.getSession();
				session.setAttribute(LOGGED_USER, user);
				System.out.println("LOGGED");

			} else {
				System.out.println("NOT LOGGED");
				response.sendRedirect("../views/register.jsp");
				return;
			}

		} else {

		}

		// System.out.println("===============================");
		// System.out.println(username);
		// System.out.println(password);

	}

}
