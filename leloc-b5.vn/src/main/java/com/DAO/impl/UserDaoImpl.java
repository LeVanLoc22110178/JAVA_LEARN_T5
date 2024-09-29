package com.DAO.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.DAO.IUserDao;
import com.configs.DBConnectMySQL;
import com.models.UserModel;

public class UserDaoImpl implements IUserDao {

	public Connection conn = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;

	@Override
	public UserModel FindByUsername(String username) {
		String sql = "SELECT * FROM users WHERE username = ? ";
		try {
			new DBConnectMySQL();
			conn = DBConnectMySQL.getDatabaseConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			while (rs.next()) {
				UserModel user = new UserModel();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setUsername(rs.getString("username"));
				user.setFullname(rs.getString("fullname"));
				user.setPassword(rs.getString("password"));
				user.setImages(rs.getString("images"));
				user.setRoleid(Integer.parseInt(rs.getString("roleid")));
				user.setPhone(rs.getString("phone"));
				user.setCreateDate(rs.getDate("createDate"));
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	

	@Override
	public boolean register(String email, String password, String username, String fullname, String phone) {
		String sql = "INSERT INTO users (email, password, username, fullname, phone, roleid) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			new DBConnectMySQL();
			conn = DBConnectMySQL.getDatabaseConnection();
			ps = conn.prepareStatement(sql);

			ps.setString(1, email);
			ps.setString(2, password); // Make sure to hash the password in real applications
			ps.setString(3, username);
			ps.setString(4, fullname);
			ps.setString(5, phone);
			ps.setInt(6, 1); // Automatically set roleId to 2

			int rowsAffected = ps.executeUpdate(); // Execute the update here
			return rowsAffected > 0; // Return true if insert was successful
		} catch (Exception e) {
			e.printStackTrace(); // Log the exception for debugging
			return false; // Return false if there was an error
		} finally {
			// Close resources in finally block to avoid memory leaks
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public boolean updateUser(String currentUsername, String newUsername, String newEmail, String newPassword,
			String newFullname, String newImages) {
		String sql = "UPDATE users SET username = ?, email = ?, password = ?, fullname = ?, images = ? WHERE username = ?";
		try {
			new DBConnectMySQL();
			conn = DBConnectMySQL.getDatabaseConnection();
			if (conn == null) {
				System.out.println("Failed to establish a connection.");
				return false;
			}

			ps = conn.prepareStatement(sql);
			ps.setString(1, newUsername);
			ps.setString(2, newEmail);
			ps.setString(3, newPassword); // Hash this if needed
			ps.setString(4, newFullname);
			ps.setString(5, newImages);
			ps.setString(6, currentUsername);

			int rowsAffected = ps.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Update successful, rows affected: " + rowsAffected);
				return true;
			} else {
				System.out.println("No rows were updated. Check if currentUsername matches any records.");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace(); // Log SQL exceptions
			return false;
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<UserModel> findAll() {
		String sql = "SELECT * FROM users";
		List<UserModel> list = new ArrayList<UserModel>();
		try {
			new DBConnectMySQL();
			conn = DBConnectMySQL.getDatabaseConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new UserModel(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
						rs.getString("images"), rs.getString("fullname"), rs.getString("email"), rs.getString("phone"),
						rs.getInt("roleid"), rs.getDate("createDate")));
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public UserModel findById(int id) {
		String sql = "SELECT * FROM users WHERE id = ? ";
		try {
			new DBConnectMySQL();
			conn = DBConnectMySQL.getDatabaseConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				UserModel user = new UserModel();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setUsername(rs.getString("username"));
				user.setFullname(rs.getString("fullname"));
				user.setPassword(rs.getString("password"));
				user.setImages(rs.getString("images"));
				user.setRoleid(Integer.parseInt(rs.getString("roleid")));
				user.setPhone(rs.getString("phone"));
				user.setCreateDate(rs.getDate("createDate"));
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void insert(UserModel user) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO users(id, username, password, images, fullname, email) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			new DBConnectMySQL();
			conn = DBConnectMySQL.getDatabaseConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, user.getId());
			ps.setString(2, user.getUsername());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getImages());
			ps.setString(5, user.getFullname());
			ps.setString(6, user.getEmail());
			ps.executeUpdate();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean checkExistEmail(String email) {
		boolean duplicate = false;
		String query = "select * from users where email = ?";
		try {
			conn = DBConnectMySQL.getDatabaseConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, email);
			rs = ps.executeQuery();
			if (rs.next()) {
				duplicate = true;
			}
			ps.close();
			conn.close();
		} catch (Exception ex) {
		}
		return duplicate;
	}

	@Override
	public boolean checkExistUsername(String username) {
		boolean duplicate = false;
		String query = "select * from users where username = ?";
		try {
			conn = DBConnectMySQL.getDatabaseConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				duplicate = true;
			}
			ps.close();
			conn.close();
		} catch (Exception ex) {
		}
		return duplicate;
	}

	@Override
	public boolean checkExistPhone(String phone) {
		// TODO Auto-generated method stub
		return false;
	}

}
