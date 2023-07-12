package com.task.backend.api.service;

import com.task.backend.api.dto.CalculateRequestDto;
import com.task.backend.api.dto.ProductPriceDto;
import com.task.backend.api.entity.Price;
import com.task.backend.api.entity.Product;
import com.task.backend.api.exception.InvalidCommitmentPlanException;
import com.task.backend.api.exception.ProductNotFoundException;
import com.task.backend.api.repository.ProductRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class PriceServiceImpl implements PriceService {

    private static final int NO_COMMITMENT = 0;
    private final ProductRepository productRepository;

    public PriceServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Cacheable(value = "productPriceCache", key = "{#productId, #calculateRequestDto.commitmentMonths, #calculateRequestDto.returnMonths}")
    public ProductPriceDto getProductPrice(Long productId, CalculateRequestDto calculateRequestDto)
            throws ProductNotFoundException, InvalidCommitmentPlanException {
        Product product = productRepository
                .findByIdAndRentableTrue(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id=" + productId));

        float price = calculatePrice(product, calculateRequestDto);

        return new ProductPriceDto(
                productId,
                product.getTitle(),
                calculateRequestDto.getCommitmentMonths(),
                getReturnMonths(calculateRequestDto),
                price);
    }

    private float calculatePrice(Product product, CalculateRequestDto calculateRequestDto) throws InvalidCommitmentPlanException {
        var priceMap = product.getPrice().stream()
                              .collect(Collectors.toMap(Price::getCommitmentMonths, Price::getValue));
        if (!priceMap.containsKey(calculateRequestDto.getCommitmentMonths())) {
            throw new InvalidCommitmentPlanException(
                    "Invalid commitment plan of " + calculateRequestDto.getCommitmentMonths() + " months");
        }

        int commitment = getFinalCommitment(calculateRequestDto);
        return priceMap.get(NO_COMMITMENT) + priceMap.get(commitment) * getReturnMonths(calculateRequestDto);
    }

    public int getFinalCommitment(CalculateRequestDto calculateRequestDto) {
        int returnMonths = calculateRequestDto.getReturnMonths();
        int commitmentMonths = calculateRequestDto.getCommitmentMonths();

        if (returnMonths == 0 || commitmentMonths == returnMonths) {
            return commitmentMonths;
        }
        return NO_COMMITMENT;
    }

    private int getReturnMonths(CalculateRequestDto calculateRequestDto) {
        if (calculateRequestDto.getReturnMonths() > 0) {
            return calculateRequestDto.getReturnMonths();
        }

        return calculateRequestDto.getCommitmentMonths() == 0 ? 1 : calculateRequestDto.getCommitmentMonths();
    }

}
