package com.upgrad.quora.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.upgrad.quora.service.business.UsersService;
import com.upgrad.quora.service.entity.UserAuth;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;

@RestController
public class CommonController {

	@Autowired
	private UsersService userService;
	
	@RequestMapping(value="/userprofile/{userId}",method = RequestMethod.GET)
	public ResponseEntity<Object> getUsrProfile(@PathVariable("userId") int userId,@RequestHeader("access_token") String access_token){
		UserAuth userAuth=null;
		try {
		  userAuth=userService.getUserByToken(access_token);
		  if(userAuth.getLogoutAt()!=null) {
				AuthorizationFailedException auth=  new AuthorizationFailedException("ATHR-002","User is signed out.Sign in first to get user details");
				return new ResponseEntity<Object>(auth,HttpStatus.OK);
		  }
		} catch (Exception e) {
			if(userAuth==null) {
				AuthorizationFailedException auth= new AuthorizationFailedException("ATHR-001","User has not signed in");
			    return new ResponseEntity<Object>(auth,HttpStatus.OK);
			}
			
		}
		UserEntity userEn = userService.getUserById(userId);
		if(userEn.getUuid()==null) {
			 UserNotFoundException userNF= new UserNotFoundException("USR-001","User with entered uuid does not exist");
			 return new ResponseEntity<Object>(userNF,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(userEn,HttpStatus.OK);
	
		
	}
}
