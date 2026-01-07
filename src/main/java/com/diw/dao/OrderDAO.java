package com.diw.dao;

import java.util.List;

import com.diw.model.Order;
import com.diw.model.CartItem;

public interface OrderDAO {

	int addOrder(Order order);

	Order getOrderById(int orderId);

	List<Order> getOrdersByUserId(int userId);

	List<Order> getOrdersByRestaurantId(int restaurantId);

	boolean updateOrderStatus(int orderId, String status);

	boolean deleteOrder(int orderId);

	// ✅ NEW METHOD (for checkout – transaction + batch)
	int placeOrder(Order order, List<CartItem> cartItems);
}
