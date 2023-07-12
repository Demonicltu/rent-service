package com.task.backend.api.service;

import com.task.backend.api.dto.ProductDetailsDto;
import com.task.backend.api.exception.ProductNotFoundException;
import com.task.backend.api.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> getProducts();

    ProductDetailsDto getProduct(Long productId) throws ProductNotFoundException;

}
