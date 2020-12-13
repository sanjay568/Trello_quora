package com.upgrad.quora.service.dao;

import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;
import com.upgrad.quora.service.entity.*;

@Repository
public class UserDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public UserEntity createUser(UserEntity entity) {
		//entityManager.getTransaction().begin();
		entityManager.persist(entity);
		//entityManager.getTransaction().commit();
		return entity;
	}
	
	public UserEntity getUser(String uuid) {
		
		try {
				return entityManager.
				createNamedQuery("userByUuid", UserEntity.class).
				setParameter("uuid",uuid).getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
		
	}
	
	public UserEntity getUserByName(String username) {
		
		try {
				return entityManager.
				createNamedQuery("userByName", UserEntity.class).
				setParameter("username",username).getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
		
	}

	@Transactional
	public void createUserAuth(int id, String uuid, Integer userId, String access_token, ZonedDateTime expireTime,
			ZonedDateTime loginTime) {

		UserAuth userAuth =new UserAuth();
		userAuth.setUuid(uuid);
		userAuth.setUserId(userId);
		userAuth.setAccessToken(access_token);
		userAuth.setExpireAt(expireTime.now().toString());
		entityManager.persist(userAuth);
		System.out.println("Auth Added >>>>>>>>>>>>>>>>");
		
	}
	
	public UserAuth findWithAccessToken(String access_token) {
		
		
		return entityManager.
				createNamedQuery("userByAccessToken", UserAuth.class).
				setParameter("accessToken",access_token).getSingleResult();

		
	}

	@Transactional
	public UserAuth updateLogoutTime(String access_token) {
		
		UserAuth userAuth=findWithAccessToken(access_token);
		userAuth.setLogoutAt(ZonedDateTime.now().toString());
		entityManager.persist(userAuth);
		return userAuth;
		
	}

	public UserAuth getUserByAccessToken(String access_token) {
		UserAuth userAuth=findWithAccessToken(access_token);
		
		return null;
	}

	public UserEntity findUserById(int userId) {
		
		return entityManager.find(UserEntity.class,userId);
	}

	@Transactional
	public void deleteUser(int userId) {
		UserEntity userEntity =entityManager.find(UserEntity.class,userId);
		entityManager.remove(userEntity);
	}
}
