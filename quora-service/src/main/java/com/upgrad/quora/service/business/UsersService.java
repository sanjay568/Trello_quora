package com.upgrad.quora.service.business;

import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuth;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;

@Service
public class UsersService {
	
	@Autowired
	private UserDao userDao;

	public UserEntity getUserByName(String username) {
		
		return userDao.getUserByName(username);
	}
	
	public UserEntity createUser(UserEntity userEntity) {
		
		return userDao.createUser(userEntity);
	}

	public void saveUserToAuth(int id, String uuid, Integer userId, String access_token, ZonedDateTime expireTime,
			ZonedDateTime loginTime) {
		// TODO Auto-generated method stub
		
		userDao.createUserAuth(id,uuid,userId,access_token,expireTime,loginTime);
		
	}

	public UserAuth findWithAccessToken(String authentication) {
		
		return userDao.findWithAccessToken(authentication);
	}

	public UserAuth updateLogout(String access_token) {
		
		return userDao.updateLogoutTime(access_token);
		
	}

	public UserAuth getUserByToken(String access_token) throws Exception {
		
		UserAuth authentication= userDao.findWithAccessToken(access_token);
		return authentication;
	}

	public UserEntity getUserById(int userId) {
		
		return userDao.findUserById(userId);
		
	}

	public void deleteUser(int userId) {
		userDao.deleteUser(userId);
		
	}
}
