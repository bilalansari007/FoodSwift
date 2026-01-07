package com.diw.servlet;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.diw.dao.UserDAO;
import com.diw.daoimplementation.UserDAOImpl;
import com.diw.model.User;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter("name");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");

		if (!password.equals(confirmPassword)) {
			request.setAttribute("error", "Passwords do not match");
			request.getRequestDispatcher("register.jsp").forward(request, response);
			return;
		}

		String encryptedPassword = encryptPassword(password);

		User user = new User();
		user.setName(name);
		user.setUsername(username);
		user.setPassword(encryptedPassword);
		user.setEmail(email);
		user.setPhone(phone);
		user.setAddress(address);

		UserDAO userDAO = new UserDAOImpl();
		userDAO.addUser(user);

		response.sendRedirect("login.jsp");
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

		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
