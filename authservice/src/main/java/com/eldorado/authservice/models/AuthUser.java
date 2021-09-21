package com.eldorado.authservice.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * @author Mohammad Faizan AuthUser :Pojo class for AuthUser Entity
 */

@Entity
public class AuthUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	private String firstName;
	private String lastName;

	@Column(name = "email", unique = true)
	@NotBlank(message = "Email is mandatory")
	private String email;

	@Column(name = "password")
	@NotBlank(message = "Password is mandatory")
	private String password;

	@ElementCollection
	private List<String> lastThreePasswords = new ArrayList<>();

	@CreatedBy
	@Column(nullable = false, updatable = false)
	private String createdBy;

	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime created;

	@LastModifiedBy
	@Column(nullable = false)
	private String modifiedBy;

	@LastModifiedDate
	@Column(nullable = false)
	private LocalDateTime modified;

	@Column(name = "enabled")
	private boolean enabled = false;

	@Column(name = "accountNonExpired")
	private boolean accountNonExpired = true;

	@Column(name = "credentialsNonExpired")
	private boolean credentialsNonExpired = true;

	@Column(name = "accountNonLocked")
	private boolean accountNonLocked = true;

	@Column(name = "lastModifiedTime")
	private String lastModifiedTime;
	
	@Column(name = "address1")
	private String address1;
	
	@Column(name = "address2")
	private String address2;
	
	@Column(name = "address3")
	private String address3;
	
	@Column(name = "image")
	private String image;
	
	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	/*
	 * Many to Many mapping between user and role entities
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	/**
	 * No argument constructor for AuthUser
	 */
	public AuthUser() {

	}

	/**
	 * Argument constructor for AuthUser
	 * 
	 * @param AuthUser
	 */
	public AuthUser(AuthUser user) {
		this.password = user.getPassword();
		this.email = user.getEmail();
		this.enabled = user.isEnabled();
		this.accountNonExpired = user.isAccountNonExpired();
		this.credentialsNonExpired = user.isCredentialsNonExpired();
		this.accountNonLocked = user.isAccountNonLocked();
		this.roles = user.getRoles();
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the id
	 *
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param the id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the lastThreePasswords
	 *
	 */
	public List<String> getLastThreePasswords() {
		return lastThreePasswords;
	}

	/**
	 * @param lastThreePasswords the lastThreePasswords to set
	 *
	 */
	public void setLastThreePasswords(List<String> lastThreePasswords) {
		this.lastThreePasswords = lastThreePasswords;
	}

	/*
	 * Return Type:String Value :user first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/*
	 * Function to set the user first name Return Type:void
	 *
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/*
	 * Return Type:String Value :user last name
	 */
	public String getLastName() {
		return lastName;
	}

	/*
	 * Return Type:Long Value :user id
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/*
	 * Function to get user roles Return Type:Set Value :user roles set
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	/*
	 * Function to set user roles Return Type:void
	 *
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the created
	 */
	public LocalDateTime getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @return the modified
	 */
	public LocalDateTime getModified() {
		return modified;
	}

	/**
	 * @param modified the modified to set
	 */
	public void setModified(LocalDateTime modified) {
		this.modified = modified;
	}

	/**
	 * @return boolean {@value User enabled or not}
	 */

	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Function to set enabled for user
	 * 
	 * @param boolean
	 * @return void
	 */

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return boolean {@value User accountNonExpired or not}
	 */
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	/**
	 * Function to set setAccountNonExpired for user
	 * 
	 * @param boolean
	 * @return void
	 */
	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	/**
	 * @return boolean {@value User isCredentialsNonExpired or not}
	 */
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	/**
	 * Function to set setCredentialsNonExpired for user
	 * 
	 * @param boolean
	 * @return void
	 */
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	/**
	 * @return boolean {@value User isCredentialsNonExpired or not}
	 */
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	/**
	 * Function to set setAccountNonLocked for user
	 * 
	 * @param boolean
	 * @return void
	 */
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public LocalDateTime getCreatedDateTime() {
		return LocalDateTime.parse(lastModifiedTime, DateTimeFormatter.ISO_DATE_TIME);
	}

	/**
	 * @param createdDateTime the createdDateTime to set with LocalDateTime to
	 *                        String conversion
	 */
	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.lastModifiedTime = createdDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
	}

	/**
	 * @param createdDateTime the createdDateTime to set
	 */
	public void setCreatedDateTime(String createdDateTime) {
		this.lastModifiedTime = createdDateTime;
	}

}
