package com.QuickBuy.mapper;

import com.QuickBuy.dto.ProductDTO;
import com.QuickBuy.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    
    ProductDTO toDTO(Product product);
    
    Product toModel(ProductDTO productDTO);
}