package com.diw.daoimplementation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.diw.dao.RestaurantDAO;
import com.diw.model.Restaurant;
import com.diw.utility.DBConnection;

public class RestaurantDAOImpl implements RestaurantDAO {

	private static final String INSERT_QUERY = "INSERT INTO restaurant (name, cuisine_type, delivery_time, address, rating, is_active, image_path) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

	private static final String GET_BY_ID_QUERY = "SELECT * FROM restaurant WHERE restaurant_id = ?";

	private static final String GET_ALL_QUERY = "SELECT * FROM restaurant";

	private static final String UPDATE_QUERY = "UPDATE restaurant SET name=?, cuisine_type=?, delivery_time=?, address=?, rating=?, is_active=?, image_path=? "
			+ "WHERE restaurant_id=?";

	private static final String DELETE_QUERY = "DELETE FROM restaurant WHERE restaurant_id=?";

	@Override
	public int addRestaurant(Restaurant restaurant) {
		try (Connection con = DBConnection.connection(); PreparedStatement ps = con.prepareStatement(INSERT_QUERY)) {

			ps.setString(1, restaurant.getName());
			ps.setString(2, restaurant.getCuisineType());
			ps.setInt(3, restaurant.getDeliveryTime());
			ps.setString(4, restaurant.getAddress());
			ps.setFloat(5, restaurant.getRating());
			ps.setBoolean(6, restaurant.isActive());
			ps.setString(7, restaurant.getImagePath());

			return ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Restaurant getRestaurantById(int restaurantId) {
		Restaurant restaurant = null;

		try (Connection con = DBConnection.connection(); PreparedStatement ps = con.prepareStatement(GET_BY_ID_QUERY)) {

			ps.setInt(1, restaurantId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				restaurant = mapRestaurant(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return restaurant;
	}

	@Override
	public List<Restaurant> getAllRestaurants() {
		List<Restaurant> list = new ArrayList<>();

		try (Connection con = DBConnection.connection();
				PreparedStatement ps = con.prepareStatement(GET_ALL_QUERY);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				list.add(mapRestaurant(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean updateRestaurant(Restaurant restaurant) {
		try (Connection con = DBConnection.connection(); PreparedStatement ps = con.prepareStatement(UPDATE_QUERY)) {

			ps.setString(1, restaurant.getName());
			ps.setString(2, restaurant.getCuisineType());
			ps.setInt(3, restaurant.getDeliveryTime());
			ps.setString(4, restaurant.getAddress());
			ps.setFloat(5, restaurant.getRating());
			ps.setBoolean(6, restaurant.isActive());
			ps.setString(7, restaurant.getImagePath());
			ps.setInt(8, restaurant.getRestaurantId());

			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteRestaurant(int restaurantId) {
		try (Connection con = DBConnection.connection(); PreparedStatement ps = con.prepareStatement(DELETE_QUERY)) {

			ps.setInt(1, restaurantId);
			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private Restaurant mapRestaurant(ResultSet resultSet) throws SQLException {

		int restaurantId = resultSet.getInt("restaurant_id");
		String name = resultSet.getString("name");
		String cuisineType = resultSet.getString("cuisine_type");
		int deliveryTime = resultSet.getInt("delivery_time");
		String address = resultSet.getString("address");
		float rating = resultSet.getFloat("rating");
		boolean isActive = resultSet.getBoolean("is_active");
		String imagePath = resultSet.getString("image_path");

		Restaurant restaurant = new Restaurant(restaurantId, name, cuisineType, deliveryTime, address, rating, isActive,
				imagePath);

		return restaurant;
	}

}
