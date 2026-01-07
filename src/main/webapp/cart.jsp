<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.Collection"%>
<%@ page import="com.diw.model.Cart"%>
<%@ page import="com.diw.model.CartItem"%>
<%@ page import="com.diw.model.User"%>

<%
User user = (User) session.getAttribute("loggedUser");
Cart cart = (Cart) session.getAttribute("cart");
Collection<CartItem> items = (cart != null) ? cart.getItems() : null;

// ⚠️ Restaurant ID used only to go back to SAME restaurant menu
Integer restId = (Integer) session.getAttribute("currentRestaurantId");

double total = 0;
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Your Cart - FoodSwift</title>
<link rel="stylesheet" href="css/styles.css">
</head>

<body>

	<!-- Header -->
	<header class="header white-bg">
		<div class="header-container">
			<div class="logo gradient-text">FoodSwift</div>

			<div class="header-user">
				<% if (user != null) { %>
				<span class="welcome-text">Welcome, <%= user.getName() %></span> <a
					href="logout" class="logout-btn">Logout</a>
				<% } %>
			</div>
		</div>
	</header>

	<div class="container">

		<h1 class="page-title">Your Cart</h1>

		<% if (items != null && !items.isEmpty()) { %>

		<% for (CartItem item : items) {
				double itemTotal = item.getPrice() * item.getQuantity();
				total += itemTotal;
			%>

		<!-- Cart Item Card -->
		<div class="cart-item-card">

			<div class="item-info">
				<strong><%= item.getName() %></strong> <span class="item-price">₹
					<%= itemTotal %></span>
			</div>

			<div class="qty-controls">

				<!-- Minus -->
				<form action="cart" method="post">
					<input type="hidden" name="action" value="update"> <input
						type="hidden" name="menuId" value="<%= item.getItemId() %>">
					<input type="hidden" name="quantity"
						value="<%= item.getQuantity() - 1 %>">
					<button class="qty-btn" type="submit">−</button>
				</form>

				<span class="qty-value"><%= item.getQuantity() %></span>

				<!-- Plus -->
				<form action="cart" method="post">
					<input type="hidden" name="action" value="update"> <input
						type="hidden" name="menuId" value="<%= item.getItemId() %>">
					<input type="hidden" name="quantity"
						value="<%= item.getQuantity() + 1 %>">
					<button class="qty-btn" type="submit">+</button>
				</form>

				<!-- Remove -->
				<form action="cart" method="post">
					<input type="hidden" name="action" value="remove"> <input
						type="hidden" name="menuId" value="<%= item.getItemId() %>">
					<button class="remove-btn" type="submit">Remove</button>
				</form>

			</div>
		</div>

		<% } %>

		<!-- Cart Footer -->
		<div class="footer-box">
			<div class="total-amount">
				Total: <span>₹ <%= total %></span>
			</div>

			<div class="action-buttons">

				<% if (restId != null) { %>
				<a href="menu?restaurantId=<%= restId %>" class="btn btn-secondary">
					Add More Items </a>
				<% } %>

				<form action="checkout.jsp" method="post">
					<button class="btn" type="submit">Proceed to Checkout</button>
				</form>

			</div>
		</div>

		<% } else { %>

		<!-- Empty Cart -->
		<div class="empty-cart">
			<div class="empty-cart-icon">🛒</div>
			<h2>Your cart is empty</h2>
			<a href="restaurants" class="btn">Browse Restaurants</a>
		</div>

		<% } %>

	</div>

</body>
</html>
