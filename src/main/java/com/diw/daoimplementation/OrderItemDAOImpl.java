package com.diw.daoimplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.diw.dao.OrderItemDAO;
import com.diw.model.OrderItem;
import com.diw.utility.DBConnection;

public class OrderItemDAOImpl implements OrderItemDAO {

	private static final String INSERT_ORDER_ITEM = "INSERT INTO order_items (order_id, menu_id, quantity, item_total) VALUES (?, ?, ?, ?)";

	private static final String GET_ITEMS_BY_ORDER = "SELECT * FROM order_items WHERE order_id=?";

	private static final String DELETE_ITEMS_BY_ORDER = "DELETE FROM order_items WHERE order_id=?";

	@Override
	public int addOrderItem(OrderItem orderItem) {
		try (Connection con = DBConnection.connection();
				PreparedStatement ps = con.prepareStatement(INSERT_ORDER_ITEM)) {

			ps.setInt(1, orderItem.getOrderId());
			ps.setInt(2, orderItem.getMenuId());
			ps.setInt(3, orderItem.getQuantity());
			ps.setDouble(4, orderItem.getItemTotal());

			return ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<OrderItem> getItemsByOrderId(int orderId) {
		List<OrderItem> list = new ArrayList<>();

		try (Connection con = DBConnection.connection();
				PreparedStatement ps = con.prepareStatement(GET_ITEMS_BY_ORDER)) {

			ps.setInt(1, orderId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				list.add(extractOrderItem(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean deleteItemsByOrderId(int orderId) {
		try (Connection con = DBConnection.connection();
				PreparedStatement ps = con.prepareStatement(DELETE_ITEMS_BY_ORDER)) {

			ps.setInt(1, orderId);
			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private OrderItem extractOrderItem(ResultSet resultSet) throws SQLException {

		int orderItemId = resultSet.getInt("order_item_id");
		int orderId = resultSet.getInt("order_id");
		int menuId = resultSet.getInt("menu_id");
		int quantity = resultSet.getInt("quantity");
		double itemTotal = resultSet.getDouble("item_total");

		OrderItem orderItem = new OrderItem(orderItemId, orderId, menuId, quantity, itemTotal);

		return orderItem;
	}
}
