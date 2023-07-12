package com.task.backend.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.task.backend.api.namingstrategy.CapitalSnakeCaseNamingStrategy;

@JsonNaming(CapitalSnakeCaseNamingStrategy.class)
public class CommitmentPlanDto {

    private final int commitmentMonths;

    private final float price;

    public CommitmentPlanDto(int commitmentMonths, float price) {
        this.commitmentMonths = commitmentMonths;
        this.price = price;
    }

    public int getCommitmentMonths() {
        return commitmentMonths;
    }

    public float getPrice() {
        return price;
    }

}
