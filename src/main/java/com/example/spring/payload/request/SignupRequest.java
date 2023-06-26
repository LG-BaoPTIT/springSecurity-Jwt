package com.example.spring.payload.request;

import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
	private String userName;
	private String passWord;
	private String email;
	private String phone;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date created = new Date();
	private boolean userStatus = true;
	private Set<String> listRole;
	
}
