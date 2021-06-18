package com.code.shoppingcart.model;

import com.code.shoppingcart.dto.user.UserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Table
@Entity(name = "user")
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends BaseModel {

    @Column(unique = true)
    private String email;

    private String name;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    private String password;

    @Column(name = "role_id")
    private int roleId;

    public UserDto toDto(){
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(this,dto);
        return dto;
    }

}
