package com.services.impl;

import com.DAO.impl.UserDaoImpl;
import com.DAO.IUserDao;
import com.models.UserModel;
import com.services.IUserServices;

public class UserServicesImpl implements IUserServices {
	IUserDao UserDao = new UserDaoImpl(); // lay toan bo ham trong tang Dao cua User

	@Override
	public UserModel login(String username, String password) {
		UserModel user = this.FindByUserName(username);
		if (user != null && password.equals(user.getPassword())) {
			return user;
		}
		return null;
	}

	@Override
	public boolean checkExistEmail(String email) {
		return UserDao.checkExistEmail(email);
	}

	@Override
	public boolean checkExistUsername(String username) {
		return UserDao.checkExistUsername(username);
	}

	@Override
	public boolean register(String email, String password, String username, String fullname, String phone) {
		// TODO Auto-generated method stub
		return UserDao.register(email, password, username, fullname, phone);
	}

	@Override
	public boolean updateUser(String currentUsername, String newUsername, String newEmail, String newPassword,
			String newFullname, String newImages) {
		// TODO Auto-generated method stub
		return UserDao.updateUser(currentUsername, newUsername, newEmail, newPassword, newFullname, newImages);
	}

	@Override
	public boolean checkExistPhone(String phone) {
		return UserDao.checkExistPhone(phone);
	}

	@Override
	public void insert(UserModel user) {
		UserDao.insert(user);
		
	}

	@Override
	public UserModel FindByUserName(String username) {
		return UserDao.FindByUsername(username);
	}

}
