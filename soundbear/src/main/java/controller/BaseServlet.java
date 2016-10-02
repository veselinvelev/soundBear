package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BaseServlet
 */
public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected static final String LOGGED_USER = "loggedUser";
	protected static final String RESPONSE_STATUS = "status";
	protected static final String RESPONSE_STATUS_OK = "ok";
	protected static final String RESPONSE_STATUS_NOT = "no";



	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Pragma", "No-cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-cache");

		if (request.getSession(false) == null) {
			response.sendRedirect("./");
			return;
		}

	}


	protected boolean isStringValid(String str) {

		if (str != null && str.length() > 0) {
			return true;
		}

		return false;
	}

}
