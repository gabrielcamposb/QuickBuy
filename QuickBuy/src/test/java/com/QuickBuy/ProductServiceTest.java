package com.QuickBuy;

import com.QuickBuy.dto.ProductDTO;
import com.QuickBuy.exception.BusinessException;
import com.QuickBuy.exception.ResourceNotFoundException;
import com.QuickBuy.mapper.ProductMapper;
import com.QuickBuy.model.Product;
import com.QuickBuy.repository.ProductRepository;
import com.QuickBuy.service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product(1L, "Product1", BigDecimal.valueOf(10), 10);
        productDTO = new ProductDTO(1L, "Product1", BigDecimal.valueOf(10), 10);
    }

    @Test
    void shouldFindById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        ProductDTO foundProduct = productService.findById(1L);

        assertNotNull(foundProduct);
        assertEquals("Product1", foundProduct.getName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.findById(1L));
    }

    @Test
    void shouldCreateProduct() {
        when(productMapper.toModel(productDTO)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        ProductDTO createdProduct = productService.create(productDTO);

        assertNotNull(createdProduct);
        assertEquals("Product1", createdProduct.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void shouldUpdateProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toModel(productDTO)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        ProductDTO updatedProduct = productService.update(1L, productDTO);

        assertNotNull(updatedProduct);
        assertEquals("Product1", updatedProduct.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void shouldThrowExceptionWhenProductToUpdateNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.update(1L, productDTO));
    }

    @Test
    void shouldDeleteProduct() {
        when(productRepository.existsById(1L)).thenReturn(true);

        productService.delete(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenProductToDeleteNotFound() {
        when(productRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> productService.delete(1L));
    }

    @Test
    void shouldDecreaseStock() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        productService.decreaseStock(1L, 5);

        assertEquals(5, product.getStockQuantity());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void shouldThrowExceptionWhenInsufficientStock() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertThrows(BusinessException.class, () -> productService.decreaseStock(1L, 20));
    }

    @Test
    void shouldThrowExceptionWhenDecreasingNegativeQuantity() {
        assertThrows(BusinessException.class, () -> productService.decreaseStock(1L, -5));
    }
}
