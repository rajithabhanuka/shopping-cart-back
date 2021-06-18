package com.code.shoppingcart.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Table(name = "role")
@Entity
@EqualsAndHashCode(callSuper = true)
public class RoleEntity extends BaseModel {

    private String role;

}
