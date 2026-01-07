<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.Collection"%>
<%@ page import="com.diw.model.Cart"%>
<%@ page import="com.diw.model.CartItem"%>
<%@ page import="com.diw.model.User"%>

<%
    User user = (User) session.getAttribute("loggedUser");
    Cart cart = (Cart) session.getAttribute("cart");
    Collection<CartItem> items = (cart != null) ? cart.getItems() : null;

    // ✅ FIX 1: Calculate total BEFORE rendering UI (clean logic)
    double total = 0;
    if (items != null) {
        for (CartItem item : items) {
            total += item.getPrice() * item.getQuantity();
        }
    }

    // ✅ Security check
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Checkout - FoodSwift</title>
<link rel="stylesheet" href="css/styles.css">
</head>

<body>

	<!-- ✅ FIX 2: Unified header structure (same as other pages) -->
	<header class="header white-bg">
		<div class="header-container">
			<div class="logo gradient-text">FoodSwift</div>
			<div class="header-user">
				<span class="welcome-text">Welcome, <%= user.getName() %></span> <a
					href="logout" class="logout-btn">Logout</a>
			</div>
		</div>
	</header>

	<div class="container">

		<h1 class="page-title">Checkout</h1>

		<% if (items != null && !items.isEmpty()) { %>

		<form action="placeOrder" method="post">

			<!-- ✅ FIX 3: safety hidden field -->
			<input type="hidden" name="checkout" value="true">

			<div class="checkout-grid">

				<!-- LEFT: FORMS -->
				<div class="checkout-forms">

					<div class="card">
						<h3>Delivery To</h3>
						<div class="delivery-info">
							<div class="info-row">
								<strong>Name:</strong>
								<%= user.getName() %></div>
							<div class="info-row">
								<strong>Email:</strong>
								<%= user.getEmail() %></div>
							<div class="info-row">
								<strong>Phone:</strong>
								<%= user.getPhone() %></div>
						</div>
					</div>

					<div class="card">
						<h3>Delivery Address</h3>
						<textarea name="address" rows="4" required
							placeholder="House No, Street, Landmark, City, PIN"></textarea>
					</div>

					<div class="card">
						<h3>Payment Method</h3>
						<div class="payment-options">

							<label class="payment-option"> <input type="radio"
								name="paymentMode" value="COD" checked> Cash on Delivery
							</label> <label class="payment-option"> <input type="radio"
								name="paymentMode" value="UPI"> UPI (Google Pay /
								PhonePe / Paytm)
							</label> <label class="payment-option"> <input type="radio"
								name="paymentMode" value="CARD"> Credit / Debit Card
							</label> <label class="payment-option"> <input type="radio"
								name="paymentMode" value="NETBANKING"> Net Banking
							</label>

						</div>
					</div>

				</div>

				<!-- RIGHT: ORDER SUMMARY -->
				<div class="order-summary">
					<div class="card">
						<h3>Order Summary</h3>

						<% for (CartItem item : items) { %>
						<div class="order-item">
							<span class="item-name"> <%= item.getName() %> × <%= item.getQuantity() %>
							</span> <span class="item-price"> ₹ <%= item.getPrice() * item.getQuantity() %>
							</span>
						</div>
						<% } %>

						<div class="summary-divider"></div>

						<div class="total-row">
							<strong>Total</strong> <strong class="total-amount">₹ <%= total %></strong>
						</div>

						<!-- ✅ FIX 4: spacing-safe CTA -->
						<button type="submit" class="btn">Place Order</button>
					</div>
				</div>

			</div>
		</form>

		<% } else { %>

		<div class="empty-cart">
			<div class="empty-cart-icon">🛒</div>
			<p>Your cart is empty. Add items to proceed.</p>
			<a href="index.jsp" class="btn">Browse Restaurants</a>
		</div>

		<% } %>

	</div>

</body>
</html>
