package com.task.backend.api.namingstrategy;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class CapitalSnakeCaseNamingStrategy extends PropertyNamingStrategy.SnakeCaseStrategy {

    @Override
    public String translate(String input) {
        String translate = super.translate(input);
        return translate.toUpperCase();
    }

}
