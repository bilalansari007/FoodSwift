package com.diw.dao;

import java.util.List;
import com.diw.model.OrderItem;

public interface OrderItemDAO {

	int addOrderItem(OrderItem orderItem);

	List<OrderItem> getItemsByOrderId(int orderId);

	boolean deleteItemsByOrderId(int orderId);
}
