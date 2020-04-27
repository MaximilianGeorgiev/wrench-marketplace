package com.sampleproject.wrenchmarketplace.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sampleproject.wrenchmarketplace.validators.ValidEmail;

@Entity
@Table(name = "USERS")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id", nullable = false, unique = true)
	private int id;

	@Column(name = "username")
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String username;

	@Column(name = "password")
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String password;

	@Column(name = "firstName")
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String firstName;

	@Column(name = "secondName")
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String secondName;

	@Column(name = "email")
	@ValidEmail
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String email;

	@Column(name = "age")
	private int age;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Collection<Role> roles;

	@Column(name = "enabled")
	private int enabled; // required by Spring Security default USERS table

	public User() {
	}

	public User(int id, String username, String password, String firstName, String lastName, String email, int age) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.secondName = lastName;
		this.email = email;
		this.age = age;
		this.enabled = 1;
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

	// no camel case since thyemelaf wants 100% match between field name and getter
	// and setter names
	public String getfirstName() {
		return firstName;
	}

	public void setfirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getsecondName() {
		return secondName;
	}

	public void setsecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public int getEnabled() {
		return this.enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
}
