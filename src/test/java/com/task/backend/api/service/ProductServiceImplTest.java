package com.task.backend.api.service;

import com.task.backend.api.data.ProductMock;
import com.task.backend.api.dto.ProductDto;
import com.task.backend.api.entity.Product;
import com.task.backend.api.exception.ProductNotFoundException;
import com.task.backend.api.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductServiceImplTest {

    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void setup() {
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void testGetProducts() {
        Product product = ProductMock.getProduct();
        ProductDto expectedProductDto = ProductMock.getProductDto();
        when(productRepository.findAllByRentableTrue())
                .thenAnswer(invocation -> Collections.singletonList(product));

        List<ProductDto> products = productService.getProducts();

        verify(productRepository).findAllByRentableTrue();
        assertEquals(1, products.size());
        assertEquals(expectedProductDto.getId(), products.get(0).getId());
        assertEquals(expectedProductDto.getTitle(), products.get(0).getTitle());
    }

    @Test
    void testGetProduct() throws ProductNotFoundException {
        Product product = ProductMock.getProduct();
        ProductDto expectedProduct = ProductMock.getProductDto();
        when(productRepository.findByIdAndRentableTrue(any()))
                .thenAnswer(invocation -> Optional.of(product));

        ProductDto productDto = productService.getProduct(expectedProduct.getId());

        verify(productRepository).findByIdAndRentableTrue(any());
        assertEquals(expectedProduct.getId(), productDto.getId());
        assertEquals(expectedProduct.getTitle(), productDto.getTitle());
    }

    @Test
    void testGetProduct_productNotFoundException() {
        when(productRepository.findByIdAndRentableTrue(any()))
                .thenAnswer(invocation -> Optional.empty());

        ProductNotFoundException exception =
                Assertions.assertThrows(ProductNotFoundException.class, () -> productService.getProduct(1L));

        assertEquals("Product not found with id=1", exception.getMessage());
    }

}
