package com.task.backend.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.task.backend.api.namingstrategy.CapitalSnakeCaseNamingStrategy;

import javax.validation.constraints.PositiveOrZero;

@JsonNaming(CapitalSnakeCaseNamingStrategy.class)
public class CalculateRequestDto {

    private final int commitmentMonths;

    private final int returnMonths;

    @JsonCreator
    public CalculateRequestDto(
            @PositiveOrZero int commitmentMonths,
            int returnMonths) {
        this.commitmentMonths = commitmentMonths;
        this.returnMonths = returnMonths;
    }

    public int getCommitmentMonths() {
        return commitmentMonths;
    }

    public int getReturnMonths() {
        return returnMonths;
    }

}
