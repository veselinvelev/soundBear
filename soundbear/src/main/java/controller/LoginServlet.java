package controller;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
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

import model.mail.EmailUtil;
import model.user.User;
import model.user.UserDAO;
import model.user.UserJDBCTemplate;

@WebServlet("/Login")
public class LoginServlet extends BaseServlet {

	private static final String RESET_MSG = "Your password has been reset to : ";
	private static final String PASSWORD_RESET = "Password Reset";
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		UserJDBCTemplate userJDBCTemplate = (UserJDBCTemplate) context.getBean("userJDBCTemplate");

		final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		final String email = request.getParameter("email").trim();
		System.out.println(email);
		JsonObject jsonResponse = new JsonObject();
		Pattern pattern = Pattern.compile(EMAIL_REGEX);
		Matcher matcher = pattern.matcher(email);

		System.out.println(email.isEmpty());
		System.out.println(matcher.matches());
		System.out.println(userJDBCTemplate.isValidEmail(email));

		if (email.isEmpty() || !matcher.matches() || userJDBCTemplate.isValidEmail(email)) {

			jsonResponse.addProperty(RESPONSE_STATUS, RESPONSE_STATUS_NOT);
			response.getWriter().print(jsonResponse.toString());
		} else {

			final String newPassword = User.genPassword().substring(0, 8);
			userJDBCTemplate.updatePassword(email, newPassword);

			new Thread() {
				public void run() {
					try {
						EmailUtil.sendEmail(email, RESET_MSG + newPassword, PASSWORD_RESET);
					} catch (AddressException e) {
						e.printStackTrace();
					} catch (MessagingException e) {
						e.printStackTrace();
					}

				};
			}.start();

			jsonResponse.addProperty(RESPONSE_STATUS, RESPONSE_STATUS_OK);
			response.getWriter().print(jsonResponse.toString());

		}

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
		JsonObject jsonResponse = new JsonObject();

		if (isStringValid(username) && isStringValid(password)) {

			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

			UserJDBCTemplate userJDBCTemplate = (UserJDBCTemplate) context.getBean("userJDBCTemplate");
			UserDAO userDAO = userJDBCTemplate;
			user = userDAO.getUser(username, password);
			if (user != null) {
				HttpSession session = request.getSession();
				session.setAttribute(LOGGED_USER, user);

				jsonResponse.addProperty(RESPONSE_STATUS, RESPONSE_STATUS_OK);
				response.getWriter().print(jsonResponse.toString());

			} else {
				jsonResponse.addProperty(RESPONSE_STATUS, RESPONSE_STATUS_NOT);
				response.getWriter().print(jsonResponse.toString());
			}

		} else {
			jsonResponse.addProperty(RESPONSE_STATUS, RESPONSE_STATUS_NOT);
			response.getWriter().print(jsonResponse.toString());
		}

	}

}
