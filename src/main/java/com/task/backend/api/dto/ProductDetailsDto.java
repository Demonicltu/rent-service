package com.task.backend.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.task.backend.api.namingstrategy.CapitalSnakeCaseNamingStrategy;

import java.util.List;

@JsonNaming(CapitalSnakeCaseNamingStrategy.class)
public class ProductDetailsDto extends ProductDto {

    private final List<CommitmentPlanDto> commitmentPlans;

    public ProductDetailsDto(Long id, String title, List<CommitmentPlanDto> commitmentPlans) {
        super(id, title);
        this.commitmentPlans = commitmentPlans;
    }

    public List<CommitmentPlanDto> getCommitmentPlans() {
        return commitmentPlans;
    }

}
