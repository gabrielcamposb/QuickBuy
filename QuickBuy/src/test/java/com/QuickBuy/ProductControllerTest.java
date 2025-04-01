package com.QuickBuy;

import com.QuickBuy.dto.ProductDTO;
import com.QuickBuy.exception.ResourceNotFoundException;
import com.QuickBuy.mapper.ProductMapper;
import com.QuickBuy.model.Product;
import com.QuickBuy.repository.ProductRepository;
import com.QuickBuy.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product(1L, "Product1", BigDecimal.valueOf(10), 10);
        new ProductDTO(1L, "Product1", BigDecimal.valueOf(10), 10);
    }

    @Test
    void shouldUpdateProduct() {
        ProductDTO updatedProductDTO = new ProductDTO(1L, "Updated Product", BigDecimal.valueOf(15), 20);
        Product updatedProduct = new Product(1L, "Updated Product", BigDecimal.valueOf(15), 20);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toModel(updatedProductDTO)).thenReturn(updatedProduct);
        when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);
        when(productMapper.toDTO(updatedProduct)).thenReturn(updatedProductDTO);

        ProductDTO result = productService.update(1L, updatedProductDTO);

        assertNotNull(result);
        assertEquals("Updated Product", result.getName());
        assertEquals(15, result.getPrice().doubleValue());
        assertEquals(20, result.getStockQuantity());
        verify(productRepository, times(1)).save(updatedProduct);
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void shouldGetProductById() {
        ProductDTO productDTO = new ProductDTO(1L, "Product1", BigDecimal.valueOf(10), 10);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        ProductDTO result = productService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Product1", result.getName());
        assertEquals(10, result.getPrice().doubleValue());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void shouldGetAllProducts() {
        Product product1 = new Product(1L, "Product1", BigDecimal.valueOf(10), 10);
        Product product2 = new Product(2L, "Product2", BigDecimal.valueOf(20), 5);
        ProductDTO productDTO1 = new ProductDTO(1L, "Product1", BigDecimal.valueOf(10), 10);
        ProductDTO productDTO2 = new ProductDTO(2L, "Product2", BigDecimal.valueOf(20), 5);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> page = new PageImpl<>(List.of(product1, product2));

        when(productRepository.findAll(pageable)).thenReturn(page);
        when(productMapper.toDTO(product1)).thenReturn(productDTO1);
        when(productMapper.toDTO(product2)).thenReturn(productDTO2);

        List<ProductDTO> products = productService.findAll(pageable).getContent();

        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals("Product1", products.get(0).getName());
        assertEquals("Product2", products.get(1).getName());
    }

    @Test
    void shouldDeleteProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(product);

        productService.delete(1L);

        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            productService.delete(1L);
        });

        verify(productRepository, never()).delete(any());
    }

    @Test
    void shouldCreateProduct() {
        ProductDTO newProductDTO = new ProductDTO(null, "New Product", BigDecimal.valueOf(12.99), 15);
        Product newProduct = new Product(null, "New Product", BigDecimal.valueOf(12.99), 15);

        when(productMapper.toModel(newProductDTO)).thenReturn(newProduct);
        when(productRepository.save(any(Product.class))).thenReturn(newProduct);
        when(productMapper.toDTO(newProduct)).thenReturn(newProductDTO);

        ProductDTO result = productService.create(newProductDTO);

        assertNotNull(result);
        assertEquals("New Product", result.getName());
        assertEquals(12.99, result.getPrice().doubleValue());
        assertEquals(15, result.getStockQuantity());
        verify(productRepository, times(1)).save(newProduct);
    }
}
