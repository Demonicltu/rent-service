package com.task.backend.api.documentation;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.removeHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

public class ProductDocumentation {

    private ProductDocumentation() {
    }

    public static RestDocumentationResultHandler documentGetProducts() {
        return document(
                "getProducts",
                preprocessRequest(removeHeaders("Content-Length", "Host"), prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                        fieldWithPath("[].ID").type(JsonFieldType.NUMBER).description("Id"),
                        fieldWithPath("[].TITLE").type(JsonFieldType.STRING).description("Title")
                )
        );
    }

    public static RestDocumentationResultHandler documentGetProductDetails() {
        return document(
                "getProductDetails",
                preprocessRequest(removeHeaders("Content-Length", "Host"), prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                        parameterWithName("id").description("Product id")
                ),
                responseFields(
                        fieldWithPath("ID").type(JsonFieldType.NUMBER).description("Id"),
                        fieldWithPath("TITLE").type(JsonFieldType.STRING).description("Title"),
                        fieldWithPath("COMMITMENT_PLANS[].COMMITMENT_MONTHS").type(JsonFieldType.NUMBER).description("Commitment plan length (months)"),
                        fieldWithPath("COMMITMENT_PLANS[].PRICE").type(JsonFieldType.NUMBER).description("Commitment plan price per month")
                )
        );
    }

    public static RestDocumentationResultHandler documentGetProductPrice() {
        return document(
                "getProductPrice",
                preprocessRequest(removeHeaders("Content-Length", "Host"), prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                        parameterWithName("id").description("Product id")
                ),
                requestFields(
                        fieldWithPath("COMMITMENT_MONTHS").type(JsonFieldType.NUMBER).description("Commitment plan"),
                        fieldWithPath("RETURN_MONTHS").type(JsonFieldType.NUMBER)
                                                     .description("Number of months after which the equipment will be returned")
                                                     .optional()
                ),
                responseFields(
                        fieldWithPath("ID").type(JsonFieldType.NUMBER).description("Id"),
                        fieldWithPath("TITLE").type(JsonFieldType.STRING).description("Title"),
                        fieldWithPath("COMMITMENT_MONTHS").type(JsonFieldType.NUMBER).description("Commitment plan"),
                        fieldWithPath("RETURN_MONTHS").type(JsonFieldType.NUMBER)
                                                     .description("Number of months after which the equipment will be returned"),
                        fieldWithPath("FINAL_PRICE").type(JsonFieldType.NUMBER).description("Total price")
                )
        );
    }

}
