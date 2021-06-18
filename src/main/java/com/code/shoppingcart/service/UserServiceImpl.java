package com.code.shoppingcart.service;


import com.code.shoppingcart.dto.ResponseDto;
import com.code.shoppingcart.dto.user.UserDto;
import com.code.shoppingcart.model.UserEntity;
import com.code.shoppingcart.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService, BaseSpecification {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder passwordEncoder,
                           UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    private String encryptPassword(String password) {

        return passwordEncoder.encode(password);
    }

    @Override
    public ResponseEntity<ResponseDto> create(UserDto userDto) {

        UserEntity userEntity = userDto.toEntity();
        userEntity.setPassword(this.encryptPassword(userDto.getPassword()));
        userEntity.setActive(false);
        userEntity = userRepository.save(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(userEntity.toDto());
    }

    @Override
    public UserDetails loadUserByUsername(String userName) {

        UserEntity entity = userRepository.findByEmail(userName);

        if (entity == null)
            throw new UsernameNotFoundException(userName);

        return new User(entity.getEmail(),
                entity.getPassword(),
                true,
                true,
                true,
                true,
                new ArrayList<>()
        );
    }

    @Override
    public UserEntity getUserDetailsByEmail(String email) {

        UserEntity entity = userRepository.findByEmail(email);

        if (entity == null)
            throw new UsernameNotFoundException(email);

        return entity;
    }


    @Override
    public void save(UserEntity userEntity) {
        userRepository.save(userEntity);
    }


}
