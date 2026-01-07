<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Register - FoodSwift</title>
<link rel="stylesheet" href="css/styles.css">
</head>

<body class="register-page">

	<header class="header">
		<a href="index.jsp" class="logo">FoodSwift</a>
	</header>

	<div class="register-container">
		<div class="register-card">
			<h2>Create Account</h2>
			<p class="subtitle">Join FoodSwift and start ordering delicious
				food</p>

			<%
				String error = (String) request.getAttribute("error");
				if (error != null) {
			%>
			<div class="error-message"><%= error %></div>
			<%
				}
			%>

			<form action="register" method="post">

				<div class="form-group">
					<label>Name</label> <input type="text" name="name" required>
				</div>

				<div class="form-group">
					<label>Username</label> <input type="text" name="username" required>
				</div>

				<div class="form-group">
					<label>Password</label> <input type="password" name="password"
						required minlength="6" autocomplete="new-password">
				</div>

				<div class="form-group">
					<label>Confirm Password</label> <input type="password"
						name="confirmPassword" required minlength="6"
						autocomplete="new-password">
				</div>

				<div class="form-group">
					<label>Email</label> <input type="email" name="email" required
						pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}"
						title="Enter a valid email like example@gmail.com">
				</div>

				<div class="form-group">
					<label>Phone</label> <input type="text" name="phone" required
						pattern="[0-9]{10}" maxlength="10"
						title="Enter 10 digit phone number">
				</div>

				<div class="form-group">
					<label>Address</label>
					<textarea name="address" required></textarea>
				</div>

				<button type="submit" class="btn btn-primary">Register</button>
			</form>

			<div class="login-link">
				Already have an account? <a href="login.jsp">Login here</a>
			</div>

		</div>
	</div>

</body>
</html>
