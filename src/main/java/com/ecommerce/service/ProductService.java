package com.ecommerce.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.ecommerce.dto.ProductRequest;
import com.ecommerce.dto.ProductResponse;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.mapper.ProductMapper;
import com.ecommerce.exception.DuplicateProductException;
import com.ecommerce.exception.ProductNotFoundException;

@Service
public class ProductService {
	
	private final ProductRepository productRepository;
	
	private final ProductMapper productMapper;
	
	public ProductService (ProductRepository productRepository, ProductMapper productMapper) {
		this.productRepository = productRepository;
		this.productMapper = productMapper;
	}
	
	public ProductResponse createProduct(ProductRequest request) {
		
		productRepository.findByName(request.getName())
        .ifPresent(product -> {
            throw new DuplicateProductException(
                    "Product already exists with name: "
                            + request.getName()
            );
        });
		
		Product product = productMapper.toEntity(request);
		
		Product savedProduct = productRepository.save(product);
		
		return productMapper.toResponse(savedProduct);
		
	}
	
	public List<ProductResponse> getAllProducts() {
		
		return productRepository.findAll()
				.stream()
				.map(productMapper::toResponse)
				.toList();
	}
	
	public ProductResponse getProductById(Long id) {
		
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
		
		return productMapper.toResponse(product);
	}
	
	public ProductResponse updateProduct (Long id, ProductRequest request) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
		
		product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setCategory(request.getCategory());
        
        Product updateProduct = productRepository.save(product);
        
        return productMapper.toResponse(updateProduct);
	}
	
	public void deleteProduct(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
		
		productRepository.delete(product);
	}
	
	public Page<ProductResponse> getProducts(int page, int size) {

	    Pageable pageable = PageRequest.of(page, size);

	    return productRepository.findAll(pageable)
	            .map(productMapper::toResponse);
	}

}
