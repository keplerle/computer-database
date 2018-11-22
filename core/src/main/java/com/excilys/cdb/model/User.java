package com.excilys.cdb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "username",nullable = false, unique = true)
	private String username;
	@Column(name = "salted_password",nullable = false)
	private String saltedPassword;
	@Nullable
	@ManyToOne
	@JoinColumn(name = "role")
	private Role role;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getSaltedPassword() {
		return saltedPassword;
	}
	public void setSaltedPassword(String saltedPassword) {
		this.saltedPassword = saltedPassword;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	
	
}
