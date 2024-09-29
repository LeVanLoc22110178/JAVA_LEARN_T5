package com.DAO;

import java.util.List;

import com.models.UserModel;

public interface IUserDao {
	public List<UserModel> findAll();
	UserModel findById(int id);
	void insert(UserModel user);
	public boolean updateUser(String currentUsername, String newUsername, String newEmail, String newPassword, String newFullname, String newImages);
	public UserModel FindByUsername(String username);
	boolean checkExistEmail(String email);
	boolean checkExistUsername(String username);
	boolean checkExistPhone(String phone);
	public boolean register(String email, String password, String username, String fullname, String phone);
}
