package com.upgrad.quora.api.controller;

import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upgrad.quora.api.model.SigninResponse;
import com.upgrad.quora.api.model.SignoutResponse;
import com.upgrad.quora.api.model.SignupUserRequest;
import com.upgrad.quora.service.business.JwtTokenProvider;
import com.upgrad.quora.service.business.PasswordCryptographyProvider;
import com.upgrad.quora.service.business.UsersService;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthenticationFailedException;
import com.upgrad.quora.service.exception.SignOutRestrictedException;
import com.upgrad.quora.service.exception.SignUpRestrictedException;

@RestController
@RequestMapping("/user")
public class UserController {
	

	@Autowired
	private UsersService usersService;
	
	@RequestMapping("/signup")
	public ResponseEntity<Object> signup(@RequestBody SignupUserRequest signupUserRequest) throws SignUpRestrictedException{
		
		String username= signupUserRequest.getUserName();
		String email= signupUserRequest.getEmailAddress();
		UserEntity user= usersService.getUserByName(username);
		UserEntity userEntity = new UserEntity();
		if(user!=null) {
			if(user.getEmail().equalsIgnoreCase(email)) {
				SignUpRestrictedException signUpError= new SignUpRestrictedException("SGR-002","This user has already been registered, try with any other emailId");
				
				return new ResponseEntity<Object>(signUpError,HttpStatus.NOT_ACCEPTABLE);
			}
				
			SignUpRestrictedException signUpError1= new SignUpRestrictedException("SGR-001","Try any other Username, this Username has already been taken");
			return new ResponseEntity<Object>(signUpError1,HttpStatus.NOT_ACCEPTABLE);
		}
		else {
			
			userEntity.setUuid(UUID.randomUUID().toString());
			userEntity.setFirstName(signupUserRequest.getFirstName());
			userEntity.setLastName(signupUserRequest.getLastName());
			userEntity.setUserName(signupUserRequest.getUserName());
			userEntity.setEmail(signupUserRequest.getEmailAddress());
			userEntity.setSalt("xyz123");
			userEntity.setPassword(PasswordCryptographyProvider.encrypt(signupUserRequest.getPassword(),"xyz123"));
			userEntity.setCountry(signupUserRequest.getCountry());
			userEntity.setAboutMe(signupUserRequest.getAboutMe());
			userEntity.setDateOfBirth(signupUserRequest.getDob());
			userEntity.setRole("nonadmin");
			userEntity.setContactNumber(signupUserRequest.getContactNumber());
			
			userEntity=usersService.createUser(userEntity);
		}
		
		return new ResponseEntity<Object>("User Sucessfully Created Uuid : "+userEntity.getUuid(),HttpStatus.OK);
	}
	
	
	@PostMapping("/signin")
	public ResponseEntity<Object> signup(@RequestHeader("authentication") String authentication){
		
		String split[]= authentication.split(" ");
		String username= split[1];
		String decodedUsername= new String(Base64.getDecoder().decode(username));
		String split2[]= decodedUsername.split(":");
		
		
		UserEntity userEntity = usersService.getUserByName(split2[0]);
		System.out.println("Username : "+split2[0]);
		if(userEntity==null) {
			AuthenticationFailedException authenticationFailedException= new AuthenticationFailedException("ATH-001","This username does not exist");
			return new ResponseEntity<Object>(authenticationFailedException,HttpStatus.NOT_FOUND);
			
		}
		else {
			String salt = userEntity.getSalt();
			String enyPasw= new PasswordCryptographyProvider().encrypt(split2[1],salt);
			System.out.println("eny >> "+enyPasw);
			if(!enyPasw.equalsIgnoreCase(userEntity.getPassword())) {
				AuthenticationFailedException authenticationFailedException= new AuthenticationFailedException("ATH-002","Password failed");
				return new ResponseEntity<Object>(authenticationFailedException,HttpStatus.FORBIDDEN);
			}
				
			JwtTokenProvider jwtTokenProvider=new JwtTokenProvider(userEntity.getPassword());
			ZonedDateTime dateTime=ZonedDateTime.now();
			
			String access_token=jwtTokenProvider.generateToken(userEntity.getUuid(), ZonedDateTime.now(),dateTime.withMinute(30));
			usersService.saveUserToAuth(userEntity.getId(),userEntity.getUuid(),userEntity.getId(),access_token,dateTime.withMinute(30),ZonedDateTime.now());
			//return new ResponseEntity<Object>(authenticationFailedException,HttpStatus.EXPECTATION_FAILED);
			
			SigninResponse signinResponse =new SigninResponse();
			signinResponse.setId(userEntity.getUuid());
			signinResponse.setMessage("SIGNED IN SUCCESSFULLY");
			
			HttpHeaders responseHeaders = new HttpHeaders();
		    responseHeaders.set("access_token ", 
		    		access_token);

		    return ResponseEntity.ok()
		      .headers(responseHeaders)
		      .body(signinResponse);
		}
		//return null;
		
	}
	
	@PostMapping("/signout")
	public ResponseEntity<Object> signOut(@RequestHeader("access_token") String access_token){
		
		
		if(usersService.findWithAccessToken(access_token)==null) {
			SignOutRestrictedException signUpError1= new SignOutRestrictedException("SGR-001","User is not Signed in");
			return new ResponseEntity<Object>(signUpError1,HttpStatus.NOT_ACCEPTABLE);
		}
		else {
			String uuid=usersService.updateLogout(access_token).getUuid();
			SignoutResponse response = new SignoutResponse();
			response.setId(uuid);
			response.setMessage("SIGNED OUT SUCCESSFULLY");
			return new ResponseEntity<Object>(response,HttpStatus.OK);
		}
		
	}

}
