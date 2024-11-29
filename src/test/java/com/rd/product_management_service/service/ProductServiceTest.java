package com.rd.product_management_service.service;

import com.rd.product_management_service.entities.ProductEntity;
import com.rd.product_management_service.exceptions.CustomException;
import com.rd.product_management_service.mapper.ProductMapper;
import com.rd.product_management_service.model.Product;
import com.rd.product_management_service.model.ProductRequest;
import com.rd.product_management_service.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductServiceTest {
    
    private final static String PRODUCT_NAME = "Test Product";
    private final static String UPDATED_PRODUCT_NAME = "Updated Product";
    private final static Double PRODUCT_PRICE = 100.0;
    private final static Double UPDATED_PRODUCT_PRICE = 150.0;

    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerProduct_createsProduct() {
        ProductRequest request = new ProductRequest();
        request.setName(PRODUCT_NAME);
        request.setPrice(PRODUCT_PRICE);

        ProductEntity entity = new ProductEntity();
        entity.setId(1);
        entity.setName(PRODUCT_NAME);
        entity.setPrice(PRODUCT_PRICE);

        Product product = new Product();
        product.setId(1);
        product.setName(PRODUCT_NAME);
        product.setPrice(PRODUCT_PRICE);

        when(mapper.entityFromRequest(any(ProductRequest.class))).thenReturn(entity);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(entity);
        when(mapper.toModel(any(ProductEntity.class))).thenReturn(product);

        Product result = productService.registerProduct(request);

        assertEquals(product, result);
        verify(productRepository, times(1)).save(entity);
    }

    @Test
    void registerProduct_throwsExceptionForInvalidName() {
        ProductRequest request = new ProductRequest();
        request.setName("");
        request.setPrice(PRODUCT_PRICE);

        CustomException exception = assertThrows(CustomException.class, () -> productService.registerProduct(request));
        assertEquals("400", exception.getErrorCode());
        assertEquals("Product name is invalid", exception.getMessage());
    }

    @Test
    void registerProduct_throwsExceptionForInvalidPrice() {
        ProductRequest request = new ProductRequest();
        request.setName(PRODUCT_NAME);
        request.setPrice(-10.0);

        CustomException exception = assertThrows(CustomException.class, () -> productService.registerProduct(request));
        assertEquals("400", exception.getErrorCode());
        assertEquals("Product price must be positive", exception.getMessage());
    }

    @Test
    void updateProduct_updatesExistingProduct() {
        ProductRequest request = new ProductRequest();
        request.setName(UPDATED_PRODUCT_NAME);
        request.setPrice(UPDATED_PRODUCT_PRICE);

        ProductEntity entity = new ProductEntity();
        entity.setId(1);
        entity.setName(UPDATED_PRODUCT_NAME);
        entity.setPrice(UPDATED_PRODUCT_PRICE);

        Product product = new Product();
        product.setId(1);
        product.setName(UPDATED_PRODUCT_NAME);
        product.setPrice(UPDATED_PRODUCT_PRICE);

        when(productRepository.findById(anyInt())).thenReturn(Optional.of(entity));
        when(productRepository.save(any(ProductEntity.class))).thenReturn(entity);
        when(mapper.toModel(any(ProductEntity.class))).thenReturn(product);

        Product result = productService.updateProduct(1, request);

        assertEquals(product, result);
        verify(productRepository, times(1)).save(entity);
    }

    @Test
    void updateProduct_throwsExceptionForNonExistentProduct() {
        ProductRequest request = new ProductRequest();
        request.setName(UPDATED_PRODUCT_NAME);
        request.setPrice(UPDATED_PRODUCT_PRICE);

        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> productService.updateProduct(1, request));
        assertEquals("404", exception.getErrorCode());
        assertEquals("Product not found with id: 1", exception.getMessage());
    }

    @Test
    void updateProduct_throwsExceptionForInvalidName() {
        ProductRequest request = new ProductRequest();
        request.setName("");
        request.setPrice(UPDATED_PRODUCT_PRICE);

        ProductEntity entity = new ProductEntity();
        entity.setId(1);
        entity.setName(PRODUCT_NAME);
        entity.setPrice(PRODUCT_PRICE);

        when(productRepository.findById(anyInt())).thenReturn(Optional.of(entity));

        CustomException exception = assertThrows(CustomException.class, () -> productService.updateProduct(1, request));
        assertEquals("400", exception.getErrorCode());
        assertEquals("Product name is invalid", exception.getMessage());
    }

    @Test
    void updateProduct_throwsExceptionForInvalidPrice() {
        ProductRequest request = new ProductRequest();
        request.setName(UPDATED_PRODUCT_NAME);
        request.setPrice(-10.0);

        ProductEntity entity = new ProductEntity();
        entity.setId(1);
        entity.setName(PRODUCT_NAME);
        entity.setPrice(PRODUCT_PRICE);

        when(productRepository.findById(anyInt())).thenReturn(Optional.of(entity));

        CustomException exception = assertThrows(CustomException.class, () -> productService.updateProduct(1, request));
        assertEquals("400", exception.getErrorCode());
        assertEquals("Product price must be positive", exception.getMessage());
    }

    @Test
    void deleteProduct_deletesExistingProduct() {
        when(productRepository.existsById(anyInt())).thenReturn(true);
        doNothing().when(productRepository).deleteById(anyInt());

        productService.deleteProduct(1);

        verify(productRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteProduct_throwsExceptionForNonExistentProduct() {
        when(productRepository.existsById(anyInt())).thenReturn(false);

        CustomException exception = assertThrows(CustomException.class, () -> productService.deleteProduct(1));
        assertEquals("404", exception.getErrorCode());
        assertEquals("Product not found with id: 1", exception.getMessage());
    }
}