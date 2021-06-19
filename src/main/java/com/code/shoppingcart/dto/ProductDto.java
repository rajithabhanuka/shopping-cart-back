package com.code.shoppingcart.dto;

import com.code.shoppingcart.model.ProductEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;


@Data
public class ProductDto implements ResponseDto{

    private int id;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "units_per_cartoon")
    private Double unitsPerCartoon;

    @JsonProperty(value = "price_per_cartoon")
    private Double pricePerCartoon;

    @JsonProperty(value = "unit_discount")
    private Double unitDiscount;

    @JsonProperty(value = "cartoon_discount")
    private Double cartoonDiscount;

    @JsonProperty(value = "discount_eligibility")
    private Integer discountEligibility;

    @JsonProperty(value = "image")
    private String image;

    public ProductEntity toEntity() {
        ProductEntity userEntity = new ProductEntity();
        BeanUtils.copyProperties(this, userEntity);
        return userEntity;
    }

}
