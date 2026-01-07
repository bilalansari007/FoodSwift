package com.diw.daoimplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.diw.dao.UserDAO;
import com.diw.model.User;
import com.diw.utility.DBConnection;

public class UserDAOImpl implements UserDAO {
	private static final String INSERT_USER_QUERY = " insert into `user`(`name`,`username`,`password`,`email`,`phone`,`address`) values(?,?,?,?,?,?)";
	private static final String GET_USER_BY_LOGIN = "SELECT * FROM user WHERE username = ? OR email = ?";
	private static final String GET_USER_QUERY = "select * from `user` where `userId`=?";
	private static final String UPDATE_USER_QUERY = "UPDATE `user` SET `name`=?, `password`=?, `email`=?, `phone`=?, `address`=? WHERE `userId`=?";
	private static final String DELETE_USER_QUERY = " DELETE FROM `user` where `userId` = ? ";
	private static final String GET_ALL_USERS_QUERY = "select * from `user`";

	@Override
	public void addUser(User user) {

		try (Connection connection = DBConnection.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_QUERY);) {

			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getUsername());
			preparedStatement.setString(3, user.getPassword());
			preparedStatement.setString(4, user.getEmail());
			preparedStatement.setString(5, user.getPhone());
			preparedStatement.setString(6, user.getAddress());

			int res = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
//-------------------------------------------------------------------------------->>>

	@Override
	public User getUser(int userId) {

		User user = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = DBConnection.connection();
			preparedStatement = connection.prepareStatement(GET_USER_QUERY);

			preparedStatement.setInt(1, userId);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				user = extractUser(resultSet);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;

	}

//-------------------------------------------------------------------------------->>>

	@Override
	public void updateUser(User user) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DBConnection.connection();
			preparedStatement = connection.prepareStatement(UPDATE_USER_QUERY);

			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getPhone());
			preparedStatement.setString(5, user.getAddress());
			preparedStatement.setInt(6, user.getUserId());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
//-------------------------------------------------------------------------------->>>

	@Override
	public void deleteUser(int userId) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DBConnection.connection();
			preparedStatement = connection.prepareStatement(DELETE_USER_QUERY);
			preparedStatement.setInt(1, userId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
//-------------------------------------------------------------------------------->>>

	@Override
	public List<User> getAllUsers() {

		ArrayList<User> usersList = new ArrayList<User>();

		Connection connection = null;
		Statement statement = null;

		try {
			connection = DBConnection.connection();
			statement = connection.createStatement();

			ResultSet resultSet = statement.executeQuery(GET_ALL_USERS_QUERY);

			while (resultSet.next()) {
				User user = extractUser(resultSet);
				usersList.add(user);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return usersList;
	}

//-------------------------------------------------------------------------------->>>
	@Override
	public User getUserByUsernameOrEmail(String loginId) {

		User user = null;

		try (Connection con = DBConnection.connection();
				PreparedStatement ps = con.prepareStatement(GET_USER_BY_LOGIN)) {

			ps.setString(1, loginId);
			ps.setString(2, loginId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				user = extractUser(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

//-------------------------------------------------------------------------------->>>

	User extractUser(ResultSet resultSet) throws SQLException {
		int userId = resultSet.getInt("userId");
		String name = resultSet.getString("name");
		String username = resultSet.getString("username");
		String password = resultSet.getString("password");
		String email = resultSet.getString("email");
		String phone = resultSet.getString("phone");
		String address = resultSet.getString("address");

		User user = new User(userId, name, username, password, email, phone, address, null, null);

		return user;
	}

}
