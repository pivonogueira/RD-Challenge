package com.rd.product_management_service.mapper;

import com.rd.product_management_service.entities.ProductEntity;
import com.rd.product_management_service.model.Product;
import com.rd.product_management_service.model.ProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toModel(ProductEntity productEntity);

    @Mapping(target = "id", ignore = true)
    ProductEntity entityFromRequest(ProductRequest request);
}
