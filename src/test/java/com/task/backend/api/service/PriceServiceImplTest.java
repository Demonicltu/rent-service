package com.task.backend.api.service;

import com.task.backend.api.data.CalculateRequestMock;
import com.task.backend.api.data.ProductMock;
import com.task.backend.api.dto.CalculateRequestDto;
import com.task.backend.api.dto.ProductPriceDto;
import com.task.backend.api.entity.Product;
import com.task.backend.api.exception.InvalidCommitmentPlanException;
import com.task.backend.api.exception.ProductNotFoundException;
import com.task.backend.api.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PriceServiceImplTest {

    private PriceServiceImpl priceService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void setup() {
        priceService = new PriceServiceImpl(productRepository);
    }

    @ParameterizedTest
    @MethodSource("productPriceTestData")
    void testGetProductPrice(CalculateRequestDto input, int expectedReturnMonths, float expectedFinalPrice)
            throws ProductNotFoundException, InvalidCommitmentPlanException {
        Product product = ProductMock.getProduct();
        when(productRepository.findByIdAndRentableTrue(any()))
                .thenAnswer(invocation -> Optional.of(product));

        ProductPriceDto productPrice = priceService.getProductPrice(1L, input);

        verify(productRepository).findByIdAndRentableTrue(any());
        assertEquals(1L, productPrice.getId());
        assertEquals(product.getTitle(), productPrice.getTitle());
        assertEquals(expectedReturnMonths, productPrice.getReturnMonths());
        assertEquals(input.getCommitmentMonths(), productPrice.getCommitmentMonths());
        assertEquals(expectedFinalPrice, productPrice.getFinalPrice());
    }

    private static Stream<Arguments> productPriceTestData() {
        return Stream.of(
                Arguments.of(CalculateRequestMock.getCalculateRequestDto(0, 0), 1, 70f),
                Arguments.of(CalculateRequestMock.getCalculateRequestDto(3, 0), 3, 125f),
                Arguments.of(CalculateRequestMock.getCalculateRequestDto(6, 0), 6, 185f),
                Arguments.of(CalculateRequestMock.getCalculateRequestDto(3, 2), 2, 105f),
                Arguments.of(CalculateRequestMock.getCalculateRequestDto(6, 4), 4, 175f),
                Arguments.of(CalculateRequestMock.getCalculateRequestDto(3, 4), 4, 175f),
                Arguments.of(CalculateRequestMock.getCalculateRequestDto(6, 7), 7, 280f),
                Arguments.of(CalculateRequestMock.getCalculateRequestDto(0, 10), 10, 385f)
        );
    }

    @Test
    void testGetProductPrice_productNotFoundException() {
        when(productRepository.findByIdAndRentableTrue(any()))
                .thenAnswer(invocation -> Optional.empty());

        ProductNotFoundException exception =
                Assertions.assertThrows(ProductNotFoundException.class,
                        () -> priceService.getProductPrice(1L, CalculateRequestMock.getCalculateRequestDto()));

        assertEquals("Product not found with id=1", exception.getMessage());
    }

    @Test
    void testGetProductPrice_invalidCommitmentPlanException() {
        Product product = ProductMock.getProduct();
        when(productRepository.findByIdAndRentableTrue(any()))
                .thenAnswer(invocation -> Optional.of(product));

        InvalidCommitmentPlanException exception =
                Assertions.assertThrows(InvalidCommitmentPlanException.class,
                        () -> priceService.getProductPrice(1L, new CalculateRequestDto(-1, 1)));

        assertEquals("Invalid commitment plan of -1 months", exception.getMessage());
    }

}
