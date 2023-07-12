package com.task.backend.api.controller;

import com.task.backend.api.data.CalculateRequestMock;
import com.task.backend.api.data.ProductDetailsMock;
import com.task.backend.api.data.ProductMock;
import com.task.backend.api.data.ProductPriceMock;
import com.task.backend.api.documentation.ProductDocumentation;
import com.task.backend.api.dto.CalculateRequestDto;
import com.task.backend.api.dto.ProductDetailsDto;
import com.task.backend.api.dto.ProductDto;
import com.task.backend.api.dto.ProductPriceDto;
import com.task.backend.api.exception.InvalidCommitmentPlanException;
import com.task.backend.api.exception.ProductNotFoundException;
import com.task.backend.api.exception.error.ApplicationError;
import com.task.backend.api.service.PriceService;
import com.task.backend.api.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ProductControllerTest extends BaseTestController {

    @MockBean
    private ProductService productService;

    @MockBean
    private PriceService priceService;

    @Test
    void testGetProducts() throws Exception {
        ProductDto expectedProductDto = ProductMock.getProductDto();
        when(productService.getProducts())
                .thenReturn(Collections.singletonList(expectedProductDto));

        MvcResult mvcResult = getMockMvc()
                .perform(
                        RestDocumentationRequestBuilders.get("/api/v1/products")
                                                        .with(httpBasic("user", "pass"))
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(ProductDocumentation.documentGetProducts())
                .andDo(print())
                .andReturn();

        String responseString = mvcResult.getResponse().getContentAsString();
        List<ProductDto> responseList = Arrays.asList(mapper.readValue(responseString, ProductDto[].class));

        assertEquals(1, responseList.size());
        assertEquals(expectedProductDto.getId(), responseList.get(0).getId());
        assertEquals(expectedProductDto.getTitle(), responseList.get(0).getTitle());
    }

    @Test
    void testGetProductDetails() throws Exception {
        ProductDetailsDto expectedProductDetailsDto = ProductDetailsMock.getProductDetailsDto();
        when(productService.getProduct(any()))
                .thenAnswer(invocation -> expectedProductDetailsDto);

        MvcResult mvcResult = getMockMvc()
                .perform(
                        RestDocumentationRequestBuilders.get("/api/v1/products/{id}", 1L)
                                                        .with(httpBasic("user", "pass"))
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(ProductDocumentation.documentGetProductDetails())
                .andDo(print())
                .andReturn();

        String responseString = mvcResult.getResponse().getContentAsString();
        ProductDetailsDto response = mapper.readValue(responseString, ProductDetailsDto.class);

        assertEquals(expectedProductDetailsDto.getId(), response.getId());
        assertEquals(expectedProductDetailsDto.getTitle(), response.getTitle());
        assertEquals(expectedProductDetailsDto.getCommitmentPlans().size(), response.getCommitmentPlans().size());
    }

    @Test
    void testGetProductDetails_productNotFoundException() throws Exception {
        when(productService.getProduct(any()))
                .thenThrow(new ProductNotFoundException("Test exception"));

        var mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/api/v1/products/{id}", 1L)
                                                                  .with(httpBasic("user", "pass"))
                                                                  .contentType(MediaType.APPLICATION_JSON)
                                                                  .accept(MediaType.APPLICATION_JSON);

        testException(status().isNotFound(), mockHttpServletRequestBuilder, ApplicationError.PRODUCT_NOT_FOUND);
    }

    @Test
    void testCalculatePrice() throws Exception {
        CalculateRequestDto calculateRequestDto = CalculateRequestMock.getCalculateRequestDto();
        ProductPriceDto expectedProductPriceDto = ProductPriceMock.getProductPriceDto();
        when(priceService.getProductPrice(any(), any()))
                .thenAnswer(invocation -> expectedProductPriceDto);

        MvcResult mvcResult = getMockMvc()
                .perform(
                        RestDocumentationRequestBuilders.post("/api/v1/products/{id}/price", 1L)
                                                        .with(httpBasic("user", "pass"))
                                                        .content(mapper.writeValueAsString(calculateRequestDto))
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(ProductDocumentation.documentGetProductPrice())
                .andDo(print())
                .andReturn();

        String responseString = mvcResult.getResponse().getContentAsString();
        ProductPriceDto response = mapper.readValue(responseString, ProductPriceDto.class);

        assertEquals(expectedProductPriceDto.getId(), response.getId());
        assertEquals(expectedProductPriceDto.getTitle(), response.getTitle());
        assertEquals(expectedProductPriceDto.getReturnMonths(), response.getReturnMonths());
        assertEquals(expectedProductPriceDto.getCommitmentMonths(), response.getCommitmentMonths());
        assertEquals(expectedProductPriceDto.getFinalPrice(), response.getFinalPrice());
    }

    @Test
    void testCalculatePrice_productNotFoundException() throws Exception {
        CalculateRequestDto calculateRequestDto = CalculateRequestMock.getCalculateRequestDto();
        when(priceService.getProductPrice(any(), any()))
                .thenThrow(new ProductNotFoundException("Test exception"));

        var builder = RestDocumentationRequestBuilders.post("/api/v1/products/{id}/price", 1L)
                                                      .with(httpBasic("user", "pass"))
                                                      .content(mapper.writeValueAsString(calculateRequestDto))
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .accept(MediaType.APPLICATION_JSON);

        testException(status().isNotFound(), builder, ApplicationError.PRODUCT_NOT_FOUND);
    }

    @Test
    void testCalculatePrice_invalidCommitmentPlanException() throws Exception {
        CalculateRequestDto calculateRequestDto = CalculateRequestMock.getCalculateRequestDto();
        when(priceService.getProductPrice(any(), any()))
                .thenThrow(new InvalidCommitmentPlanException("Test exception"));

        var builder = RestDocumentationRequestBuilders.post("/api/v1/products/{id}/price", 1L)
                                                      .with(httpBasic("user", "pass"))
                                                      .content(mapper.writeValueAsString(calculateRequestDto))
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .accept(MediaType.APPLICATION_JSON);

        testException(status().isNotFound(), builder, ApplicationError.INVALID_COMMITMENT_PLAN);
    }

}
