package com.code.shoppingcart.model;

import com.code.shoppingcart.dto.user.UserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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
