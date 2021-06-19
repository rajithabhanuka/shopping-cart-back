package com.code.shoppingcart.model;

import com.code.shoppingcart.dto.ProductDto;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Data
@Entity(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "units_per_cartoon", nullable = false)
    private Double unitsPerCartoon;

    @Column(name = "price_per_cartoon", nullable = false)
    private Double pricePerCartoon;

    @Column(name = "unit_discount", nullable = false, columnDefinition = "DOUBLE default 1.3")
    private Double unitDiscount;

    @Column(name = "cartoon_discount", nullable = false, columnDefinition = "DOUBLE default 0.1")
    private Double cartoonDiscount;

    @Column(name = "discount_eligibility", columnDefinition = "INTEGER default 3")
    private Integer discountEligibility;

    @Column(name = "image")
    private String image;

    public ProductDto toDto(){
        ProductDto dto = new ProductDto();
        BeanUtils.copyProperties(this,dto);
        return dto;
    }

}
