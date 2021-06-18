package com.code.shoppingcart.model;

import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
public class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "is_active" , columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean isActive;
}
