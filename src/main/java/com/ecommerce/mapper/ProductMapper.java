package com.ecommerce.mapper;

import org.springframework.stereotype.Component;

import com.ecommerce.dto.ProductRequest;
import com.ecommerce.dto.ProductResponse;
import com.ecommerce.entity.Product;

@Component
public class ProductMapper {
	
	public Product toEntity(ProductRequest request) {
		
		Product product = new Product();
		
		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setQuantity(request.getQuantity());
		product.setCategory(request.getCategory());
		
		return product;	
	}
	
	public ProductResponse toResponse(Product product) {
		ProductResponse response = new ProductResponse();
		
		response.setId(product.getId());
		response.setName(product.getName());
		response.setDescription(product.getDescription());
		response.setPrice(product.getPrice());
		response.setQuantity(product.getQuantity());
		response.setCategory(product.getCategory());
		
		return response;
	}

}
