package com.rd.product_management_service.mapper;

import com.rd.product_management_service.entities.ProductEntity;
import com.rd.product_management_service.model.Product;
import com.rd.product_management_service.model.ProductRequest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProductMapperTest {

    private final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    @Test
    void toModel_mapsEntityToModel() {
        ProductEntity entity = new ProductEntity();
        entity.setId(1);
        entity.setName("Test Product");
        entity.setPrice(100.0);

        Product model = mapper.toModel(entity);

        assertEquals(1, model.getId());
        assertEquals("Test Product", model.getName());
        assertEquals(100.0, model.getPrice());
    }

    @Test
    void toModel_handlesNullEntity() {
        Product model = mapper.toModel(null);

        assertNull(model);
    }

    @Test
    void entityFromRequest_mapsRequestToEntity() {
        ProductRequest request = new ProductRequest();
        request.setName("New Product");
        request.setPrice(200.0);

        ProductEntity entity = mapper.entityFromRequest(request);

        assertNull(entity.getId());
        assertEquals("New Product", entity.getName());
        assertEquals(200.0, entity.getPrice());
    }

    void entityFromRequest_handlesNullRequest() {
        ProductEntity entity = mapper.entityFromRequest(null);

        assertNull(entity);
    }
}