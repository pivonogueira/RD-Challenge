package com.rd.product_management_service.controller;

import com.rd.product_management_service.api.ProductsApi;
import com.rd.product_management_service.model.Product;
import com.rd.product_management_service.model.ProductRequest;
import com.rd.product_management_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
public class ProductsController implements ProductsApi {

    @Autowired
    private ProductService service;

    @Override
    public ResponseEntity<Product> productsPost(ProductRequest productInput) {
        Product product = service.registerProduct(productInput);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(location).body(product);
    }

    @Override
    public ResponseEntity<Product> productsIdPut(Integer id, ProductRequest productInput) {
        Product product = service.updateProduct(id, productInput);
        return ResponseEntity.ok().body(product);
    }

    @Override
    public ResponseEntity<List<Product>> productsGet() {
        return ResponseEntity.ok().body(service.getAllProducts());
    }

    @Override
    public ResponseEntity<Product> productsIdGet(Integer id) {
        Product product = service.findById(id);
        return ResponseEntity.ok().body(product);
    }

    @Override
    public ResponseEntity<Void> productsIdDelete(Integer id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }


}
