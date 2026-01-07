<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.diw.model.Restaurant"%>
<%@ page import="com.diw.model.User"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>FoodSwift - Order food online from your favorite
	restaurants</title>
<link rel="stylesheet" href="css/styles.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800&display=swap"
	rel="stylesheet">
</head>

<body>

	<%
    User user = (User) session.getAttribute("loggedUser");
%>

	<!-- Header -->
	<header class="header white-bg">
		<div class="header-container">
			<div class="logo gradient-text">FoodSwift</div>

			<nav class="nav-links">
				<% if (user != null) { %>
				<span class="welcome-text">Welcome, <%= user.getName() %></span> <a
					href="logout" class="logout-btn">Logout</a>
				<% } else { %>
				<a href="login.jsp">Login</a> <a href="register.jsp"
					class="login-btn">Register</a>
				<% } %>
			</nav>
		</div>
	</header>

	<!-- Hero Section -->
	<section class="hero-section">
		<h1 class="hero-title">Delicious food, delivered to your door</h1>
		<p class="hero-subtitle">Order from your favorite restaurants and
			track your delivery in real-time</p>

		<div class="search-container">
			<div class="search-wrapper">
				<span class="search-icon">🔍</span> <input type="text"
					class="search-input"
					placeholder="Search for restaurants or cuisines...">
			</div>
		</div>

		<h2 class="section-title">Popular Restaurants</h2>
	</section>

	<%
    List<Restaurant> restaurants =
        (List<Restaurant>) request.getAttribute("restaurants");
%>

	<!-- Restaurant Grid -->
	<div class="restaurant-container">

		<% if (restaurants != null && !restaurants.isEmpty()) { %>

		<% for (Restaurant r : restaurants) { %>

		<a href="menu?restaurantId=<%= r.getRestaurantId() %>"
			class="restaurant-card-link">

			<article class="restaurant-card">
				<div class="restaurant-image-wrapper">
					<img src="<%= r.getImagePath() %>"
						alt="<%= r.getName() %> - <%= r.getCuisineType() %> cuisine">
					<div class="restaurant-badge"><%= r.getCuisineType() %></div>
				</div>

				<div class="restaurant-info">
					<h3 class="restaurant-name"><%= r.getName() %></h3>

					<div class="restaurant-meta">
						<div class="rating-wrapper">
							⭐
							<%= r.getRating() %>
						</div>
						<div class="delivery-time">
							<%= r.getDeliveryTime() %>
							mins
						</div>
					</div>
				</div>
			</article>

		</a>

		<% } %>

		<% } else { %>

		<div class="empty-state">
			<div class="empty-state-icon">🍽️</div>
			<h2>No restaurants available</h2>
			<p>We're working on bringing delicious food to your area. Check
				back soon!</p>
		</div>

		<% } %>

	</div>

</body>
</html>
