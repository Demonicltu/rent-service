package com.task.backend.api.mapper;

import com.task.backend.api.dto.CommitmentPlanDto;
import com.task.backend.api.dto.ProductDetailsDto;
import com.task.backend.api.dto.ProductDto;
import com.task.backend.api.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    private ProductMapper() {
    }

    public static ProductDto toProductDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getTitle()
        );
    }

    public static ProductDetailsDto toProductDetailsDto(Product product) {
        List<CommitmentPlanDto> commitmentPlans = product.getPrice().stream()
                                                         .map(price -> new CommitmentPlanDto(price.getCommitmentMonths(),
                                                                 price.getValue()))
                                                         .collect(Collectors.toList());
        return new ProductDetailsDto(
                product.getId(),
                product.getTitle(),
                commitmentPlans
        );
    }

}
