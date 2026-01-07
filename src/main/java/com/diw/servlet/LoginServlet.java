package com.diw.servlet;

import java.io.IOException;
import java.security.MessageDigest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.diw.dao.UserDAO;
import com.diw.daoimplementation.UserDAOImpl;
import com.diw.model.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");

		// 🔑 NEW: read redirect info
		String redirect = request.getParameter("redirect");
		String restaurantId = request.getParameter("restaurantId");

		String encryptedPassword = encryptPassword(password);

		UserDAO userDAO = new UserDAOImpl();
		User user = userDAO.getUserByUsernameOrEmail(loginId);

		if (user != null && user.getPassword().equals(encryptedPassword)) {

			HttpSession session = request.getSession();
			session.setAttribute("loggedUser", user);

			// 🔑 NEW: decide where to go after login
			if ("menu".equals(redirect) && restaurantId != null) {
				response.sendRedirect("menu?restaurantId=" + restaurantId);
			} else {
				response.sendRedirect("home.jsp");
			}

		} else {
			request.setAttribute("error", "Invalid username/email or password");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

	private String encryptPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] bytes = md.digest(password.getBytes());
			StringBuilder sb = new StringBuilder();

			for (byte b : bytes) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
