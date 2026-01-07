package com.diw.daoimplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.diw.dao.OrderHistoryDAO;
import com.diw.model.OrderHistory;
import com.diw.utility.DBConnection;

public class OrderHistoryDAOImpl implements OrderHistoryDAO {

	private static final String INSERT_HISTORY = "INSERT INTO order_history (order_id, user_id, total_amount, status, order_date) VALUES (?, ?, ?, ?, ?)";

	private static final String GET_HISTORY_BY_USER = "SELECT * FROM order_history WHERE user_id=?";

	private static final String GET_HISTORY_BY_ORDER = "SELECT * FROM order_history WHERE order_id=?";

	@Override
	public int addOrderHistory(OrderHistory orderHistory) {
		try (Connection con = DBConnection.connection(); PreparedStatement ps = con.prepareStatement(INSERT_HISTORY)) {

			ps.setInt(1, orderHistory.getOrderId());
			ps.setInt(2, orderHistory.getUserId());
			ps.setDouble(3, orderHistory.getTotalAmount());
			ps.setString(4, orderHistory.getStatus());
			ps.setTimestamp(5, new java.sql.Timestamp(orderHistory.getOrderDate().getTime()));

			return ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<OrderHistory> getHistoryByUserId(int userId) {
		List<OrderHistory> list = new ArrayList<>();

		try (Connection con = DBConnection.connection();
				PreparedStatement ps = con.prepareStatement(GET_HISTORY_BY_USER)) {

			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				list.add(extractOrderHistory(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public OrderHistory getHistoryByOrderId(int orderId) {
		OrderHistory history = null;

		try (Connection con = DBConnection.connection();
				PreparedStatement ps = con.prepareStatement(GET_HISTORY_BY_ORDER)) {

			ps.setInt(1, orderId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				history = extractOrderHistory(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return history;
	}

	private OrderHistory extractOrderHistory(ResultSet resultSet) throws SQLException {

		int orderHistoryId = resultSet.getInt("order_history_id");
		int orderId = resultSet.getInt("order_id");
		int userId = resultSet.getInt("user_id");
		double totalAmount = resultSet.getDouble("total_amount");
		String status = resultSet.getString("status");
		java.util.Date orderDate = resultSet.getTimestamp("order_date");

		OrderHistory orderHistory = new OrderHistory(orderHistoryId, orderId, userId, totalAmount, status, orderDate);

		return orderHistory;
	}
}
