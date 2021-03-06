package com.code.shoppingcart.repository;

import com.code.shoppingcart.model.CartEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {

    Optional<CartEntity> findByUserIdAndProductId(int userId, int productId);

    Optional<CartEntity> findByIdAndUserId(int cartId, int userId);

    Page<CartEntity> findByUserIdAndQtyGreaterThan(int userId, int qty, Pageable pageable);

}
