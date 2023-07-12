package com.task.backend.api.service;

import com.task.backend.api.dto.CalculateRequestDto;
import com.task.backend.api.dto.ProductPriceDto;
import com.task.backend.api.exception.InvalidCommitmentPlanException;
import com.task.backend.api.exception.ProductNotFoundException;

public interface PriceService {

    ProductPriceDto getProductPrice(Long productId, CalculateRequestDto calculateRequestDto)
            throws ProductNotFoundException, InvalidCommitmentPlanException;

}
