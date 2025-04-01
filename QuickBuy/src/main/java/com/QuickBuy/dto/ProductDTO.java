package com.QuickBuy.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    
    @Null(message = "ID must be null on creation")
    private Long id;

    @NotBlank(message = "Product name cannot be blank")
    @Size(min = 2, max = 100, message = "The name must be between 2 and 100 characters long.")
    private String name;

    @NotNull(message = "The price cannot be zero")
    @DecimalMin(value = "0.01", message = "Price must be greater than zero")
    private BigDecimal price;

    @NotNull(message = "The quantity in stock cannot be zero")
    @PositiveOrZero(message = "The quantity in stock must be zero or positive")
    private Integer stockQuantity;
}
