package com.task.backend.api.data;

import com.task.backend.api.dto.CommitmentPlanDto;
import com.task.backend.api.dto.ProductDetailsDto;

import java.util.Arrays;
import java.util.List;

public class ProductDetailsMock {

    private ProductDetailsMock() {
    }

    public static ProductDetailsDto getProductDetailsDto() {
        return new ProductDetailsDto(
                1L,
                "Test product",
                getCommitmentPlans()
        );
    }

    private static List<CommitmentPlanDto> getCommitmentPlans() {
        return Arrays.asList(
                new CommitmentPlanDto(0, 15f),
                new CommitmentPlanDto(3, 15f),
                new CommitmentPlanDto(6, 15f));
    }
}
