package com.example.spring.serviceImp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring.entity.ERole;
import com.example.spring.entity.Roles;
import com.example.spring.repository.RoleRepository;
import com.example.spring.service.RoleService;

@Service
public class RoleServiceImp implements RoleService {
	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public Optional<Roles> findByRoleName(ERole roleName) {
		// TODO Auto-generated method stub
		return roleRepository.findByRoleName(roleName);
	}

}
