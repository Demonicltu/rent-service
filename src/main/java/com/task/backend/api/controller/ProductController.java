package com.task.backend.api.controller;

import com.task.backend.api.dto.CalculateRequestDto;
import com.task.backend.api.dto.ProductDetailsDto;
import com.task.backend.api.dto.ProductDto;
import com.task.backend.api.dto.ProductPriceDto;
import com.task.backend.api.exception.InvalidCommitmentPlanException;
import com.task.backend.api.exception.ProductNotFoundException;
import com.task.backend.api.exception.RequestException;
import com.task.backend.api.exception.error.ApplicationError;
import com.task.backend.api.service.PriceService;
import com.task.backend.api.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    private final PriceService calculateService;

    public ProductController(ProductService productService,
                             PriceService calculateService) {
        this.productService = productService;
        this.calculateService = calculateService;
    }

    @GetMapping
    public List<ProductDto> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public ProductDetailsDto getProduct(@PathVariable("id") Long productId) {
        try {
            return productService.getProduct(productId);
        } catch (ProductNotFoundException e) {
            throw new RequestException(ApplicationError.PRODUCT_NOT_FOUND);
        }
    }

    @PostMapping("/{id}/price")
    public ProductPriceDto calculatePrice(@PathVariable("id") Long productId,
                                          @RequestBody @Valid CalculateRequestDto calculateRequestDto) {
        try {
            return calculateService.getProductPrice(productId, calculateRequestDto);
        } catch (ProductNotFoundException e) {
            throw new RequestException(ApplicationError.PRODUCT_NOT_FOUND);
        } catch (InvalidCommitmentPlanException e) {
            throw new RequestException(ApplicationError.INVALID_COMMITMENT_PLAN);
        }
    }

}
