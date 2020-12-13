package com.upgrad.quora.service.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users", schema = "public")

@NamedQueries(
        {
                @NamedQuery(name = "userByUuid", query = "select u from UserEntity u where u.uuid = :uuid"),
                @NamedQuery(name = "userByEmail", query = "select u from UserEntity u where u.email =:email"),
                @NamedQuery(name = "userByName", query = "select u from UserEntity u where u.userName =:username"),
                @NamedQuery(name = "userByAccessToken", query = "select u from UserAuth u where u.accessToken =:accessToken"),
                
           
        }
)

public class UserEntity implements Serializable{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @Size(max = 64)
    private String uuid;
    
    @Column(name = "firstname")
    @NotNull
    @Size(max = 200)
    private String firstName;

    @Column(name = "lastname")
    @NotNull
    @Size(max = 200)
    private String lastName;
    
    @Column(name = "username")
    @NotNull
    @Size(max = 200)
    private String userName;

    @Column(name = "email")
    @NotNull
    @Size(max = 200)
    private String email;

    //@ToStringExclude
    @Column(name = "password")
    private String password;
    
    @Column(name = "salt")
    @Size(max = 200)
    private String salt;

    @Column(name = "country")
    @NotNull
    @Size(max = 50)
    private String country;
    
    @Column(name = "aboutme")
    @NotNull
    @Size(max = 200)
    private String aboutMe;
    
    @Column(name = "dob")
    @NotNull
    @Size(max = 200)
    private String dateOfBirth;
    
    @Column(name = "role")
    @NotNull
    @Size(max = 200)
    private String role;
    
    @Column(name = "contactnumber")
    @NotNull
    @Size(max = 50)
    private String contactNumber;

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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}  
}