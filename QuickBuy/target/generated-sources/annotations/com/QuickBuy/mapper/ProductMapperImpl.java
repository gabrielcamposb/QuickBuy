package com.QuickBuy.mapper;

import com.QuickBuy.dto.ProductDTO;
import com.QuickBuy.model.Product;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-01T11:10:57-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.v20250325-2231, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDTO toDTO(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDTO.ProductDTOBuilder productDTO = ProductDTO.builder();

        productDTO.id( product.getId() );
        productDTO.name( product.getName() );
        productDTO.price( product.getPrice() );
        productDTO.stockQuantity( product.getStockQuantity() );

        return productDTO.build();
    }

    @Override
    public Product toModel(ProductDTO productDTO) {
        if ( productDTO == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.id( productDTO.getId() );
        product.name( productDTO.getName() );
        product.price( productDTO.getPrice() );
        product.stockQuantity( productDTO.getStockQuantity() );

        return product.build();
    }
}
