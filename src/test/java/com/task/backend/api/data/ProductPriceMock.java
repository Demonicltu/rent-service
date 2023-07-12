package com.task.backend.api.data;

import com.task.backend.api.dto.ProductPriceDto;

public class ProductPriceMock {

    private ProductPriceMock() {
    }

    public static ProductPriceDto getProductPriceDto() {
        return new ProductPriceDto(
                1L,
                "Test product",
                0,
                1,
                0.15f
        );
    }

}
