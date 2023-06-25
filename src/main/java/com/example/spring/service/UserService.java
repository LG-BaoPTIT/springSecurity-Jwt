package com.example.spring.service;

import com.example.spring.entity.Users;

public interface UserService {
	Users findByUserName(String userName);
	boolean existsByUserName(String userName);
	boolean existsByEmail(String email);
	Users saveOrUpdate(Users user);
}
