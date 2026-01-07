package com.diw.servlet;

import java.io.IOException;
import java.util.ArrayList;

import com.diw.dao.OrderDAO;
import com.diw.daoimplementation.OrderDAOImpl;
import com.diw.model.Cart;
import com.diw.model.CartItem;
import com.diw.model.Order;
import com.diw.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/placeOrder")
public class PlaceOrderServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		// 1️⃣ Get logged-in user
		User user = (User) session.getAttribute("loggedUser");

		// 2️⃣ Get cart
		Cart cart = (Cart) session.getAttribute("cart");

		// 3️⃣ Validation
		if (user == null || cart == null || cart.getItems().isEmpty()) {
			response.sendRedirect("home.jsp");
			return;
		}

		// 4️⃣ Read form data
		String paymentMode = request.getParameter("paymentMode");

		// 5️⃣ Calculate total amount
		double totalAmount = 0;
		for (CartItem item : cart.getItems()) {
			totalAmount += item.getPrice() * item.getQuantity();
		}

		// 6️⃣ Get restaurantId safely from cart
		int restaurantId = cart.getItems().iterator().next().getRestaurantId();

		// 7️⃣ Create Order object
		Order order = new Order();
		order.setUserId(user.getUserId());
		order.setRestaurantId(restaurantId);
		order.setTotalAmount(totalAmount);
		order.setStatus("PLACED");
		order.setPaymentMode(paymentMode);

		// 8️⃣ Call DAO (transaction + batch handled inside DAO)
		OrderDAO orderDAO = new OrderDAOImpl();
		int orderId = orderDAO.placeOrder(order, new ArrayList<>(cart.getItems()));

		// 9️⃣ Check result
		if (orderId == 0) {
			response.sendRedirect("cart.jsp");
			return;
		}

		// 🔟 Clear cart
		session.removeAttribute("cart");

		// 1️⃣1️⃣ Store orderId for confirmation page
		session.setAttribute("lastOrderId", orderId);

		// 1️⃣2️⃣ Redirect to confirmation page
		response.sendRedirect("orderconfirmation.jsp");
	}
}
