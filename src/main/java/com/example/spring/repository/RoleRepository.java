package com.example.spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spring.entity.Roles;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Integer>{
	Optional<Roles> findByRoleName(String roleName);
	
}
