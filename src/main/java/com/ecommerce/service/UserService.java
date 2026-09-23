package com.ecommerce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.dto.LoginRequest;
import com.ecommerce.dto.LoginResponse;
import com.ecommerce.dto.UserRequest;
import com.ecommerce.dto.UserResponse;
import com.ecommerce.entity.User;
import com.ecommerce.exception.InvalidCredentialsException;
import com.ecommerce.mapper.UserMapper;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.security.JwtService;

@Service
public class UserService {
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserMapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public UserResponse saveUser(UserRequest request) {
		
		User user = mapper.toEntity(request);
		
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		
		user.setRole("USER");
		
		User savedUser = repository.save(user);
		
		return mapper.toResponse(savedUser);
	}

	
	public LoginResponse login(LoginRequest request) {
		
		Optional<User> optionalUser = repository.findByEmail(request.getEmail());
		
		if (optionalUser.isPresent()) {
			
			User user = optionalUser.get();
			
			if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {

				String token = jwtService.generateToken(user.getEmail());
				return new LoginResponse(token);
				
			}
			
		}
		throw new InvalidCredentialsException("Invalid Email or Password");
	}
	
}
