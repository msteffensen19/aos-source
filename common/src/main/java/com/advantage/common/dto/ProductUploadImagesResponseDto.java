package com.advantage.common.dto;

import java.util.List;

/**
 * Created by kubany on 12/12/2017.
 */
public class ProductUploadImagesResponseDto{

    public List<ProductResponseDto> productResponseDtos;

    public ProductUploadImagesResponseDto(List<ProductResponseDto> productResponseDtos) {
        setImageId(productResponseDtos);
    }

    public List<ProductResponseDto> getProductResponseDto() {
        return productResponseDtos;
    }

    public void setImageId(List<ProductResponseDto> productResponseDtos) {
        this.productResponseDtos = productResponseDtos;
    }
}
