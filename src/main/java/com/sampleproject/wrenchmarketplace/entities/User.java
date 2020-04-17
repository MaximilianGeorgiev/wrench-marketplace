package com.sampleproject.wrenchmarketplace.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sampleproject.wrenchmarketplace.validators.FieldMatch;
import com.sampleproject.wrenchmarketplace.validators.ValidEmail;


@Entity
@Table(name="USER")
@FieldMatch.List({@FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")})
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="Id", nullable = false, unique = true)
	private int id;
	
	@Column(name="username")
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String username;
	
	
	@Column(name="password")
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String password;
	
	@Column(name="email")
	@ValidEmail
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String email;
	
	public User() {
		
	}

	public User(int id, String username, String password, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
