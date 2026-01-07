package com.diw.servlet;

import com.diw.dao.MenuDAO;
import com.diw.daoimplementation.MenuDAOImpl;
import com.diw.model.Menu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int restaurantId = Integer.parseInt(request.getParameter("restaurantId"));

		// ✅ ADD THIS (ONLY IMPORTANT CHANGE)
		HttpSession session = request.getSession();
		session.setAttribute("currentRestaurantId", restaurantId);

		MenuDAO menuDAO = new MenuDAOImpl();
		List<Menu> menuList = menuDAO.getMenusByRestaurantId(restaurantId);

		request.setAttribute("menuList", menuList);
		request.setAttribute("restaurantId", restaurantId);

		request.getRequestDispatcher("menu.jsp").forward(request, response);
	}
}
