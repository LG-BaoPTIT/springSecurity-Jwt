package com.example.spring.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.entity.Users;
import com.example.spring.jwt.JwtTokenProvider;
import com.example.spring.payload.request.SignupRequest;
import com.example.spring.payload.response.MessageResponse;
import com.example.spring.service.RoleService;
import com.example.spring.service.UserService;
import com.example.spring.entity.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired 
	PasswordEncoder encoder;
	
	@PostMapping("/sigup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest){
		
		if(userService.existsByUserName(signupRequest.getUserName())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: username is already"));
		}
		
		if(userService.existsByUserName(signupRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: email is already"));
		}
		Users user = new Users();
		user.setUserName(signupRequest.getUserName());
		user.setPassWord(encoder.encode(signupRequest.getPassWord()));
		user.setEmail(signupRequest.getEmail());
		
		Set<String> strRole = signupRequest.getListRole();
		Set<Roles> listRoles = new HashSet<>();
		
		if(strRole == null) {
			Roles userRoles = roleService.findByRoleName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
			listRoles.add(userRoles);
		}else {
			 strRole.forEach(role ->{
				switch(role) {
					case "admin":
						Roles adminRoles = roleService.findByRoleName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
						listRoles.add(adminRoles);
					case "moderator":
						Roles modRoles = roleService.findByRoleName(ERole.ROLE_MODERATOR).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
						listRoles.add(modRoles);
					case "user":
						Roles userRoles = roleService.findByRoleName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
						listRoles.add(userRoles);
				}
			 });
		}
		user.setListRole(listRoles);
		userService.saveOrUpdate(user);
		return ResponseEntity.ok(new MessageResponse("User registered successfully"));
		
	}
}



