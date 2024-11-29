package com.rd.product_management_service.service;

import com.rd.product_management_service.entities.ProductEntity;
import com.rd.product_management_service.exceptions.CustomException;
import com.rd.product_management_service.mapper.ProductMapper;
import com.rd.product_management_service.model.Product;
import com.rd.product_management_service.model.ProductRequest;
import com.rd.product_management_service.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper mapper;

    @Transactional
    public Product registerProduct(ProductRequest request) {
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new CustomException("400", "Product name is invalid");
        }

        if (request.getPrice() <= 0) {
            throw new CustomException("400", "Product price must be positive");
        }
        ProductEntity product = productRepository.save(mapper.entityFromRequest(request));
        log.info("Product registered. ID: {}, Name: {}, Price: {}", product.getId(), product.getName(), product.getPrice());
        return mapper.toModel(product);
    }

    @Transactional
    public Product updateProduct(Integer id, ProductRequest request) {
        ProductEntity product = productRepository.findById(id).orElseThrow(()
                -> new CustomException("404", "Product not found with id: " + id));

        if (request.getName() == null || request.getName().isEmpty()) {
            throw new CustomException("400", "Product name is invalid");
        }

        if (request.getPrice() <= 0) {
            throw new CustomException("400", "Product price must be positive");
        }

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        productRepository.save(product);
        log.info("Product updated. ID: {}, Name: {}, Price: {}", product.getId(), product.getName(), product.getPrice());
        return mapper.toModel(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll().stream().map(mapper::toModel).toList();
    }

    public Product findById(Integer id) {
        return productRepository.findById(id).stream().map(mapper::toModel).findFirst().orElseThrow(
                () -> new CustomException("404", "Product not found with id: " + id));
    }

    @Transactional
    public void deleteProduct(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new CustomException("404", "Product not found with id: " + id);
        }
        productRepository.deleteById(id);
        log.info("Product deleted. ID: {}", id);
    }
}
