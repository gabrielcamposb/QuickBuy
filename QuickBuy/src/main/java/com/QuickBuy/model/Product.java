package com.QuickBuy.model;

import java.math.BigDecimal;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{product.name.notblank}")
    @Size(min = 2, max = 100, message = "{product.name.size}")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "{product.price.notnull}")
    @DecimalMin(value = "0.01", message = "{product.price.min}")
    @Column(nullable = false)
    private BigDecimal price;

    @NotNull(message = "{product.quantity.notnull}")
    @PositiveOrZero(message = "{product.quantity.positiveorzero}")
    @Column(nullable = false)
    private Integer stockQuantity;
}
