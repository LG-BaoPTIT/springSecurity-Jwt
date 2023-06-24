package com.example.spring.entity;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="Users")
@Data
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "UserId")
	private int userId;
	
	@Column(name = "UserName", unique = true, nullable = false)
	private String userName;
	
	@Column(name = "PassWord", nullable = false)
	@JsonIgnore
	private String passWord;
	
	@JsonFormat(pattern = "dd/mm/yyyy")
	private Date created;
	
	@Column(name = "Email", unique = true, nullable = false)
	private String email;
	
	@Column(name = "Phone")
	private String phone;
	
	@Column(name = "UserStatus")
	private boolean userStatus;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "User_Role", joinColumns = @JoinColumn(name = "UserId"), inverseJoinColumns = @JoinColumn(name = "RoleId"))
	private Set<Roles> listRole = new HashSet<>();
	
}
