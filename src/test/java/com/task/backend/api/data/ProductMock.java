package com.task.backend.api.data;

import com.task.backend.api.dto.ProductDto;
import com.task.backend.api.entity.Price;
import com.task.backend.api.entity.Product;

import java.util.Arrays;

public class ProductMock {

    private ProductMock() {
    }

    public static ProductDto getProductDto() {
        return new ProductDto(
                1L,
                "Test product"
        );
    }

    public static Product getProduct() {
        return new Product(
                1L,
                "Test product",
                true,
                Arrays.asList(new Price(1L, 0, 35),
                        new Price(1L, 3, 30),
                        new Price(1L, 6, 25))
        );
    }

}
