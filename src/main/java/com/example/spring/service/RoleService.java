package com.example.spring.service;

import java.util.Optional;

import com.example.spring.entity.ERole;
import com.example.spring.entity.Roles;

public interface RoleService {
	Optional<Roles> findByRoleName(ERole roleName);
}
