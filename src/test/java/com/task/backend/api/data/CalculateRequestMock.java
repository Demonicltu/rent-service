package com.task.backend.api.data;

import com.task.backend.api.dto.CalculateRequestDto;

public class CalculateRequestMock {

    private CalculateRequestMock() {
    }

    public static CalculateRequestDto getCalculateRequestDto() {
        return new CalculateRequestDto(
                0,
                1
        );
    }

    public static CalculateRequestDto getCalculateRequestDto(int commitmentMonths, int returnMonths) {
        return new CalculateRequestDto(
                commitmentMonths,
                returnMonths
        );
    }

}
