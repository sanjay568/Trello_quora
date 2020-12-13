package com.upgrad.quora.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity(name = "UserAuth")
@Table(name = "user_auth",schema = "public")
public class UserAuth {

	
		@Id
	    @Column(name = "id")
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;

	    @Column(name = "uuid")
	    @Size(max = 64)
	    private String uuid;
	    
	    @Column(name="user_id")
	    private Integer userId;
	    
	    @Column(name="access_token")
	    private String accessToken;
	    
	    @Column(name="expires_at")
	    private String expireAt;
	    
	    @Column(name="login_at")
	    private String loginAt;
	    
	    @Column(name="logout_at")
	    private String logoutAt;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getUuid() {
			return uuid;
		}

		public void setUuid(String uuid) {
			this.uuid = uuid;
		}

		public Integer getUserId() {
			return userId;
		}

		public void setUserId(Integer userId2) {
			this.userId = userId2;
		}

		public String getAccessToken() {
			return accessToken;
		}

		public void setAccessToken(String accessToken) {
			this.accessToken = accessToken;
		}

		public String getExpireAt() {
			return expireAt;
		}

		public void setExpireAt(String expireAt) {
			this.expireAt = expireAt;
		}

		public String getLoginAt() {
			return loginAt;
		}

		public void setLoginAt(String loginAt) {
			this.loginAt = loginAt;
		}

		public String getLogoutAt() {
			return logoutAt;
		}

		public void setLogoutAt(String logoutAt) {
			this.logoutAt = logoutAt;
		}
	    
	    
	    
}
