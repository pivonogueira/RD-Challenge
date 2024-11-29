package com.rd.product_management_service.controller;

import com.rd.product_management_service.model.Product;
import com.rd.product_management_service.model.ProductRequest;
import com.rd.product_management_service.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductsControllerTest {

    @Mock
    private ProductService service;

    @InjectMocks
    private ProductsController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void productsPost_createProduct() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        ProductRequest productRequest = new ProductRequest();
        Product product = new Product();
        product.setId(1);
        when(service.registerProduct(any(ProductRequest.class))).thenReturn(product);

        ResponseEntity<Product> response = controller.productsPost(productRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(product, response.getBody());
        verify(service, times(1)).registerProduct(productRequest);
    }

    @Test
    void productsIdPut_updatesProduct() {
        ProductRequest request = new ProductRequest();
        Product product = new Product();
        when(service.updateProduct(anyInt(), any(ProductRequest.class))).thenReturn(product);

        ResponseEntity<Product> response = controller.productsIdPut(1, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
        verify(service, times(1)).updateProduct(1, request);
    }

    @Test
    void productsGet_returnsAllProducts() {
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(service.getAllProducts()).thenReturn(products);

        ResponseEntity<List<Product>> response = controller.productsGet();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
        verify(service, times(1)).getAllProducts();
    }

    @Test
    void productsIdGet_returnsProductById() {
        Product product = new Product();
        when(service.findById(anyInt())).thenReturn(product);

        ResponseEntity<Product> response = controller.productsIdGet(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
        verify(service, times(1)).findById(1);
    }

    @Test
    void productsIdDelete_deletesProduct() {
        doNothing().when(service).deleteProduct(anyInt());

        ResponseEntity<Void> response = controller.productsIdDelete(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).deleteProduct(1);
    }
}