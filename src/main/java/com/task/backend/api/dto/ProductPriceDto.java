package com.task.backend.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.task.backend.api.namingstrategy.CapitalSnakeCaseNamingStrategy;

@JsonNaming(CapitalSnakeCaseNamingStrategy.class)
public class ProductPriceDto extends ProductDto {

    private final int commitmentMonths;

    private final int returnMonths;

    private final float finalPrice;

    public ProductPriceDto(Long id, String title, int commitmentMonths, int returnMonths, float finalPrice) {
        super(id, title);
        this.commitmentMonths = commitmentMonths;
        this.returnMonths = returnMonths;
        this.finalPrice = finalPrice;
    }

    public int getCommitmentMonths() {
        return commitmentMonths;
    }

    public int getReturnMonths() {
        return returnMonths;
    }

    public float getFinalPrice() {
        return finalPrice;
    }

}
