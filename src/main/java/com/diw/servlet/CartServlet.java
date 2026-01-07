package com.diw.servlet;

import com.diw.model.Cart;
import com.diw.model.CartItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}

		String action = request.getParameter("action");

		if ("add".equals(action)) {
			addItem(request, cart);
		} else if ("update".equals(action)) {
			updateItem(request, cart);
		} else if ("remove".equals(action)) {
			removeItem(request, cart);
		} else if ("clear".equals(action)) {
			cart.clear();
		}

		response.sendRedirect("cart.jsp");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.sendRedirect("cart.jsp");
	}

	

	private void addItem(HttpServletRequest request, Cart cart) {

		int menuId = Integer.parseInt(request.getParameter("menuId"));
		int restaurantId = Integer.parseInt(request.getParameter("restaurantId"));
		String name = request.getParameter("name");
		double price = Double.parseDouble(request.getParameter("price"));

		CartItem item = new CartItem(menuId, restaurantId, name, 1, price);

		cart.addItem(item);
	}

	private void updateItem(HttpServletRequest request, Cart cart) {

		int menuId = Integer.parseInt(request.getParameter("menuId"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));

		cart.updateItem(menuId, quantity);
	}

	private void removeItem(HttpServletRequest request, Cart cart) {

		int menuId = Integer.parseInt(request.getParameter("menuId"));
		cart.removeItem(menuId);
	}
}
