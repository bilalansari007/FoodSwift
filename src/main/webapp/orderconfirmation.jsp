<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.diw.model.User"%>

<%
// Get logged-in user and last order id from session
User user = (User) session.getAttribute("loggedUser");
Integer orderId = (Integer) session.getAttribute("lastOrderId");

// Security check: prevent direct access
if (user == null || orderId == null) {
	response.sendRedirect("restaurants");
	return;
}

// OPTIONAL: remove orderId after displaying (prevents refresh reuse)
session.removeAttribute("lastOrderId");
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Order Confirmed - FoodSwift</title>
<link rel="stylesheet" href="css/styles.css">
</head>

<body>

	<!-- Header -->
	<header class="header">
		<div class="header-content">
			<a href="restaurants" class="logo">FoodSwift</a>

			<div class="nav-links">
				<span class="welcome-text">Hi, <%=user.getName()%></span> <a
					href="logout" class="logout-btn">Logout</a>
			</div>
		</div>
	</header>

	<!-- Order Success Section -->
	<div class="success-container">

		<div class="success-header">
			<div class="success-icon">
				<div class="checkmark"></div>
			</div>
			<h1>Order Placed Successfully!</h1>
			<p>Your food is being prepared</p>
		</div>

		<div class="success-content">

			<div class="user-greeting">
				Thank you, <strong><%=user.getName()%></strong> 🎉
			</div>

			<div class="order-id-section">
				<div class="order-id-label">Your Order ID</div>
				<div class="order-id">
					#<%=orderId%></div>
			</div>

			<div class="info-text">We’ve received your order and sent the
				details to your registered email. Sit back and relax while we
				prepare your delicious meal!</div>

			<div class="button-group">
				<a href="restaurants" class="btn btn-primary">Order More Food</a>
			</div>

		</div>
	</div>

</body>
</html>
