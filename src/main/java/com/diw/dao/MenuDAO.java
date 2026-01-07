package com.diw.dao;

import java.util.List;
import com.diw.model.Menu;

public interface MenuDAO {

	int addMenu(Menu menu);

	Menu getMenuById(int menuId);

	List<Menu> getMenusByRestaurantId(int restaurantId);

	boolean updateMenu(Menu menu);

	boolean deleteMenu(int menuId);
}
