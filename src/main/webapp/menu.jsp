<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.diw.model.Menu"%>
<%@ page import="com.diw.model.User"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Menu - FoodSwift</title>
<!-- Updated CSS file reference to styles.css -->
<link rel="stylesheet" href="css/styles.css">
</head>

<body>

	<%
	User user = (User) session.getAttribute("loggedUser");
	List<Menu> menuList = (List<Menu>) request.getAttribute("menuList");
	%>

	<!-- Header -->
	<div class="header">
		<h2>FoodSwift</h2>

		<div class="header-nav">
			<%
			if (user != null) {
			%>
			<span class="welcome-text">Welcome, <%=user.getName()%></span> <a
				href="logout">Logout</a>
			<%
			} else {
			%>
			<a href="login.jsp">Login</a> <a href="register.jsp">Register</a>
			<%
			}
			%>
		</div>
	</div>

	<!-- Menu Container -->
	<div class="menu-container">

		<div class="page-title">
			<h1>Our Menu</h1>
			<p>Delicious food delivered to your doorstep</p>
		</div>

		<%
		if (menuList != null && !menuList.isEmpty()) {
		%>

		<%
		for (Menu m : menuList) {
		%>

		<div class="menu-card">
			<div class="menu-info">
				<div>
					<h3><%=m.getName()%></h3>
					<p><%=m.getDescription()%></p>
				</div>
				<div class="price">
					₹<%=m.getPrice()%></div>
			</div>

			<div class="menu-image-section">
				<img src="<%=m.getImagePath()%>" alt="<%=m.getName()%>">

				<%
				if (user != null) {
				%>

				<form action="cart" method="post">
					<input type="hidden" name="action" value="add"> <input
						type="hidden" name="menuId" value="<%=m.getMenuId()%>">
					<input type="hidden" name="restaurantId"
						value="<%=m.getRestaurantId()%>"> <input type="hidden"
						name="name" value="<%=m.getName()%>"> <input
						type="hidden" name="price" value="<%=m.getPrice()%>">

					<button class="add-btn">Add to Cart</button>
				</form>

				<%
				} else {
				%>

				<a class="login-link"
					href="login.jsp?redirect=menu&restaurantId=<%=m.getRestaurantId()%>">
					Login to Order </a>

				<%
				}
				%>
			</div>
		</div>

		<%
		}
		%>

		<%
		} else {
		%>

		<div class="empty-state">
			<p>No menu items available at the moment</p>
		</div>

		<%
		}
		%>

	</div>

</body>
</html>
