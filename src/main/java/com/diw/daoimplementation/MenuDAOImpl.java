package com.diw.daoimplementation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.diw.dao.MenuDAO;
import com.diw.model.Menu;
import com.diw.utility.DBConnection;

public class MenuDAOImpl implements MenuDAO {

	private static final String INSERT_QUERY = "INSERT INTO menu (restaurant_id, name, description, price, is_available, image_path) "
			+ "VALUES (?, ?, ?, ?, ?, ?)";

	private static final String GET_BY_ID_QUERY = "SELECT * FROM menu WHERE menu_id=?";

	private static final String GET_BY_RESTAURANT_QUERY = "SELECT * FROM menu WHERE restaurant_id=?";

	private static final String UPDATE_QUERY = "UPDATE menu SET restaurant_id=?, name=?, description=?, price=?, is_available=?, image_path=? "
			+ "WHERE menu_id=?";

	private static final String DELETE_QUERY = "DELETE FROM menu WHERE menu_id=?";

	@Override
	public int addMenu(Menu menu) {
		try (Connection con = DBConnection.connection(); PreparedStatement ps = con.prepareStatement(INSERT_QUERY)) {

			ps.setInt(1, menu.getRestaurantId());
			ps.setString(2, menu.getName());
			ps.setString(3, menu.getDescription());
			ps.setDouble(4, menu.getPrice());
			ps.setBoolean(5, menu.isAvailable());
			ps.setString(6, menu.getImagePath());

			return ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Menu getMenuById(int menuId) {
		Menu menu = null;

		try (Connection con = DBConnection.connection(); PreparedStatement ps = con.prepareStatement(GET_BY_ID_QUERY)) {

			ps.setInt(1, menuId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				menu = extractMenu(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return menu;
	}

	@Override
	public List<Menu> getMenusByRestaurantId(int restaurantId) {
		List<Menu> list = new ArrayList<>();

		try (Connection con = DBConnection.connection();
				PreparedStatement ps = con.prepareStatement(GET_BY_RESTAURANT_QUERY)) {

			ps.setInt(1, restaurantId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				list.add(extractMenu(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean updateMenu(Menu menu) {
		try (Connection con = DBConnection.connection(); PreparedStatement ps = con.prepareStatement(UPDATE_QUERY)) {

			ps.setInt(1, menu.getRestaurantId());
			ps.setString(2, menu.getName());
			ps.setString(3, menu.getDescription());
			ps.setDouble(4, menu.getPrice());
			ps.setBoolean(5, menu.isAvailable());
			ps.setString(6, menu.getImagePath());
			ps.setInt(7, menu.getMenuId());

			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteMenu(int menuId) {
		try (Connection con = DBConnection.connection(); PreparedStatement ps = con.prepareStatement(DELETE_QUERY)) {

			ps.setInt(1, menuId);
			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	
	private Menu extractMenu(ResultSet resultSet) throws SQLException {

		int menuId = resultSet.getInt("menu_id");
		int restaurantId = resultSet.getInt("restaurant_id");
		String name = resultSet.getString("name");
		String description = resultSet.getString("description");
		double price = resultSet.getDouble("price");
		boolean isAvailable = resultSet.getBoolean("is_available");
		String imagePath = resultSet.getString("image_path");

		Menu menu = new Menu(menuId, restaurantId, name, description, price, isAvailable, imagePath);

		return menu;
	}
}
