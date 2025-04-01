package com.QuickBuy.service;

import com.QuickBuy.dto.ProductDTO;
import com.QuickBuy.exception.BusinessException;
import com.QuickBuy.exception.ResourceNotFoundException;
import com.QuickBuy.mapper.ProductMapper;
import com.QuickBuy.model.Product;
import com.QuickBuy.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return productMapper.toDTO(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toDTO);
    }

    @Transactional
    public ProductDTO create(ProductDTO productDTO) {
        validateProduct(productDTO);

        if (productRepository.existsByName(productDTO.getName())) {
            throw new BusinessException("Product with name '" + productDTO.getName() + "' already exists.");
        }

        Product product = productMapper.toModel(productDTO);
        product = productRepository.save(product);
        return productMapper.toDTO(product);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        validateProduct(productDTO);

        existingProduct.setName(productDTO.getName());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setStockQuantity(productDTO.getStockQuantity());

        return productMapper.toDTO(productRepository.save(existingProduct));
    }

    @Transactional
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        productRepository.delete(product);
    }

    @Transactional
    public void decreaseStock(Long productId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new BusinessException("Quantity to decrease must be greater than zero");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        if (product.getStockQuantity() < quantity) {
            throw new BusinessException("Insufficient stock for product: " + product.getName());
        }

        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);
    }

    private void validateProduct(ProductDTO productDTO) {
        if (productDTO.getName() == null || productDTO.getName().trim().isEmpty()) {
            throw new BusinessException("Product name must not be empty");
        }
        if (productDTO.getPrice() == null || productDTO.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Product price must be greater than zero");
        }
        if (productDTO.getStockQuantity() == null || productDTO.getStockQuantity() < 0) {
            throw new BusinessException("Product quantity in stock must not be negative");
        }
    }
}
