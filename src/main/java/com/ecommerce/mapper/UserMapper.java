package com.ecommerce.mapper;

import org.springframework.stereotype.Component;

import com.ecommerce.dto.UserRequest;
import com.ecommerce.dto.UserResponse;
import com.ecommerce.entity.User;

@Component
public class UserMapper {
	
	public User toEntity(UserRequest request) {
		User user = new User();
		
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		
		return user;
	}
	
	public UserResponse toResponse(User user) {
		UserResponse response = new UserResponse();
		
		response.setId(user.getId());
		response.setName(user.getName());
		response.setEmail(user.getEmail());
		
		return response;
	}

}
