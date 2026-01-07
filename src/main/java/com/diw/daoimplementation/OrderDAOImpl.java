package com.diw.daoimplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.diw.dao.OrderDAO;
import com.diw.model.CartItem;
import com.diw.model.Order;
import com.diw.utility.DBConnection;

public class OrderDAOImpl implements OrderDAO {

	private static final String INSERT_ORDER = "INSERT INTO orders (user_id, restaurant_id, total_amount, status, payment_mode) VALUES (?, ?, ?, ?, ?)";

	private static final String GET_ORDER_BY_ID = "SELECT * FROM orders WHERE order_id=?";

	private static final String GET_ORDERS_BY_USER = "SELECT * FROM orders WHERE user_id=?";

	private static final String GET_ORDERS_BY_RESTAURANT = "SELECT * FROM orders WHERE restaurant_id=?";

	private static final String UPDATE_ORDER_STATUS = "UPDATE orders SET status=? WHERE order_id=?";

	private static final String DELETE_ORDER = "DELETE FROM orders WHERE order_id=?";

	// ---------------- EXISTING METHOD (UNCHANGED) ----------------
	@Override
	public int addOrder(Order order) {

		int orderId = 0;

		try (Connection con = DBConnection.connection();
				PreparedStatement ps = con.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {

			ps.setInt(1, order.getUserId());
			ps.setInt(2, order.getRestaurantId());
			ps.setDouble(3, order.getTotalAmount());
			ps.setString(4, order.getStatus());
			ps.setString(5, order.getPaymentMode());

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				orderId = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return orderId;
	}

	// ---------------- NEW METHOD (CHECKOUT) ----------------
	@Override
	public int placeOrder(Order order, List<CartItem> cartItems) {

		int orderId = 0;
		Connection con = null;

		try {
			con = DBConnection.connection();
			con.setAutoCommit(false); // 🔴 transaction start

			// 1️⃣ Insert into orders
			PreparedStatement psOrder = con.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);

			psOrder.setInt(1, order.getUserId());
			psOrder.setInt(2, order.getRestaurantId());
			psOrder.setDouble(3, order.getTotalAmount());
			psOrder.setString(4, order.getStatus());
			psOrder.setString(5, order.getPaymentMode());

			psOrder.executeUpdate();

			ResultSet rs = psOrder.getGeneratedKeys();
			if (rs.next()) {
				orderId = rs.getInt(1);
			}

			// 2️⃣ Insert order items (BATCH)
			String itemSql = "INSERT INTO order_items (order_id, menu_id, quantity, item_total) VALUES (?, ?, ?, ?)";

			PreparedStatement psItem = con.prepareStatement(itemSql);

			for (CartItem item : cartItems) {
				psItem.setInt(1, orderId);
				psItem.setInt(2, item.getItemId());
				psItem.setInt(3, item.getQuantity());
				psItem.setDouble(4, item.getPrice() * item.getQuantity());
				psItem.addBatch();
			}

			psItem.executeBatch();

			con.commit(); // ✅ commit everything
			return orderId;

		} catch (Exception e) {
			try {
				if (con != null)
					con.rollback(); // ❌ rollback everything
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
		}

		return 0;
	}

	// ---------------- EXISTING METHODS (UNCHANGED) ----------------
	@Override
	public Order getOrderById(int orderId) {
		Order order = null;

		try (Connection con = DBConnection.connection(); PreparedStatement ps = con.prepareStatement(GET_ORDER_BY_ID)) {

			ps.setInt(1, orderId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				order = extractOrder(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return order;
	}

	@Override
	public List<Order> getOrdersByUserId(int userId) {
		List<Order> list = new ArrayList<>();

		try (Connection con = DBConnection.connection();
				PreparedStatement ps = con.prepareStatement(GET_ORDERS_BY_USER)) {

			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				list.add(extractOrder(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Order> getOrdersByRestaurantId(int restaurantId) {
		List<Order> list = new ArrayList<>();

		try (Connection con = DBConnection.connection();
				PreparedStatement ps = con.prepareStatement(GET_ORDERS_BY_RESTAURANT)) {

			ps.setInt(1, restaurantId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				list.add(extractOrder(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean updateOrderStatus(int orderId, String status) {
		try (Connection con = DBConnection.connection();
				PreparedStatement ps = con.prepareStatement(UPDATE_ORDER_STATUS)) {

			ps.setString(1, status);
			ps.setInt(2, orderId);
			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteOrder(int orderId) {
		try (Connection con = DBConnection.connection(); PreparedStatement ps = con.prepareStatement(DELETE_ORDER)) {

			ps.setInt(1, orderId);
			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private Order extractOrder(ResultSet resultSet) throws SQLException {

		int orderId = resultSet.getInt("order_id");
		int userId = resultSet.getInt("user_id");
		int restaurantId = resultSet.getInt("restaurant_id");
		java.util.Date orderDate = resultSet.getTimestamp("order_date");
		double totalAmount = resultSet.getDouble("total_amount");
		String status = resultSet.getString("status");
		String paymentMode = resultSet.getString("payment_mode");

		return new Order(orderId, userId, restaurantId, orderDate, totalAmount, status, paymentMode);
	}
}
