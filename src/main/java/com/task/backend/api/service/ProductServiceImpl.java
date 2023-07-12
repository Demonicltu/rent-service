package com.task.backend.api.service;

import com.task.backend.api.dto.ProductDetailsDto;
import com.task.backend.api.dto.ProductDto;
import com.task.backend.api.entity.Product;
import com.task.backend.api.exception.ProductNotFoundException;
import com.task.backend.api.mapper.ProductMapper;
import com.task.backend.api.repository.ProductRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Cacheable(value = "productListCache")
    public List<ProductDto> getProducts() {
        return productRepository.findAllByRentableTrue()
                                .stream()
                                .map(ProductMapper::toProductDto)
                                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "productCache")
    public ProductDetailsDto getProduct(Long productId) throws ProductNotFoundException {
        Product product = productRepository
                .findByIdAndRentableTrue(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id=" + productId));

        return ProductMapper.toProductDetailsDto(product);
    }

}
