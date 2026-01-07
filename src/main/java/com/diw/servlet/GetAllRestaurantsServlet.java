package com.diw.servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.diw.dao.RestaurantDAO;
import com.diw.daoimplementation.RestaurantDAOImpl;
import com.diw.model.Restaurant;

@WebServlet("/restaurants")
public class GetAllRestaurantsServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RestaurantDAO restaurantDAO = new RestaurantDAOImpl();
		List<Restaurant> restaurants = restaurantDAO.getAllRestaurants();

		request.setAttribute("restaurants", restaurants);

		request.getRequestDispatcher("home.jsp").forward(request, response);
	}
}
