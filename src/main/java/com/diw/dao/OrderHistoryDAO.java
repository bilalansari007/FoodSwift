package com.diw.dao;

import java.util.List;
import com.diw.model.OrderHistory;

public interface OrderHistoryDAO {

	int addOrderHistory(OrderHistory orderHistory);

	List<OrderHistory> getHistoryByUserId(int userId);

	OrderHistory getHistoryByOrderId(int orderId);
}
