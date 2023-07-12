package com.task.backend.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.task.backend.api.namingstrategy.CapitalSnakeCaseNamingStrategy;

@JsonNaming(CapitalSnakeCaseNamingStrategy.class)
public class ProductDto {

    private final Long id;

    private final String title;

    public ProductDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

}
