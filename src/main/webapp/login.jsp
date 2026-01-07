<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login - FoodSwift</title>
<link rel="stylesheet" href="css/styles.css">
</head>

<body class="auth-page">

	<div class="login-card">

		<div class="login-header">
			<h1>FoodSwift</h1>
			<p>Welcome back! Login to continue</p>
		</div>

		<div class="login-body">

			<%
				String error = (String) request.getAttribute("error");
				if (error != null) {
			%>
			<div class="error-message"><%= error %></div>
			<%
				}
			%>

			<form action="login" method="post">

				<!-- ✅ FIX 1: prevent "null" values -->
				<input type="hidden" name="redirect"
					value="<%= request.getParameter("redirect") != null ? request.getParameter("redirect") : "" %>">

				<input type="hidden" name="restaurantId"
					value="<%= request.getParameter("restaurantId") != null ? request.getParameter("restaurantId") : "" %>">

				<div class="form-group">
					<label for="loginId">Username or Email</label> <input type="text"
						id="loginId" name="loginId" required autocomplete="username"
						placeholder="Enter your username or email">
				</div>

				<div class="form-group">
					<label for="password">Password</label> <input type="password"
						id="password" name="password" required
						autocomplete="current-password" placeholder="Enter your password">
				</div>

				<button type="submit" class="btn btn-primary">Login</button>
			</form>

			<div class="divider"></div>

			<p class="register-link">
				Don't have an account? <a href="register.jsp">Create one</a>
			</p>

		</div>
	</div>

</body>
</html>
