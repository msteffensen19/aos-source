package com.advantage.online.store.dto;

public class PromotedProductDto {
    private String staringPrice;
    private String promotionHeader;
    private String promotionSubHeader;
    private String promotionImageId;
    private ProductDto product;

    public PromotedProductDto(String staringPrice, String promotionHeader, String promotionSubHeader,
                                                                String promotionImageId, ProductDto product) {
        this.staringPrice = staringPrice;
        this.promotionHeader = promotionHeader;
        this.promotionSubHeader = promotionSubHeader;
        this.promotionImageId = promotionImageId;
        this.product = product;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public String getStaringPrice() {
        return staringPrice;
    }

    public void setStaringPrice(String staringPrice) {
        this.staringPrice = staringPrice;
    }

    public String getPromotionHeader() {
        return promotionHeader;
    }

    public void setPromotionHeader(String promotionHeader) {
        this.promotionHeader = promotionHeader;
    }

    public String getPromotionSubHeader() {
        return promotionSubHeader;
    }

    public void setPromotionSubHeader(String promotionSubHeader) {
        this.promotionSubHeader = promotionSubHeader;
    }

    public String getPromotionImageId() {
        return promotionImageId;
    }

    public void setPromotionImageId(String promotionImageId) {
        this.promotionImageId = promotionImageId;
    }
}
