package com.code.shoppingcart.service;

import com.code.shoppingcart.dto.CartDto;
import com.code.shoppingcart.dto.GenericPage;
import com.code.shoppingcart.dto.ResponseDto;
import com.code.shoppingcart.exceptions.RecordNotFoundException;
import com.code.shoppingcart.model.CartEntity;
import com.code.shoppingcart.model.ProductEntity;
import com.code.shoppingcart.repository.CartRepository;
import com.code.shoppingcart.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository,
                           ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<ResponseDto> create(CartDto dto) {

        ProductEntity productEntity = productRepository.findById(dto.getProductId()).orElseThrow(() ->
                new RecordNotFoundException(String.format("Product object not found for : %s",
                        dto.getProductId()), ""));

        GenericPage<CartDto> genericPage = new GenericPage<>();

        try {

            int qty;
            double price;

            if ("cartoon".equals(dto.getOrderType())) {

                qty = productEntity.getUnitsPerCartoon() * dto.getQty();
                price = dto.getQty() * productEntity.getPricePerCartoon();

            } else {

                qty = dto.getQty();
                price = (productEntity.getPricePerCartoon() / productEntity.getUnitsPerCartoon() * dto.getQty())
                        * (1 + productEntity.getUnitDiscount());

            }

            CartEntity cartEntity;
            Optional<CartEntity> existing = cartRepository.findByUserIdAndProductId(dto.getUserId(), dto.getProductId());

            double roundedPrice = BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP).doubleValue();

            if (existing.isPresent()) {
                cartEntity = existing.get();
                cartEntity.setPrice(cartEntity.getPrice() + roundedPrice);
                cartEntity.setQty(cartEntity.getQty() + qty);
            } else {
                cartEntity = dto.toEntity();
                cartEntity.setPrice(roundedPrice);
                cartEntity.setQty(qty);
            }

            cartRepository.save(cartEntity);

            Page<CartEntity> cartEntityPage = cartRepository
                    .findByUserIdAndProductId(dto.getProductId(), dto.getUserId(),
                            PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, "id")));

            genericPage.setData(cartEntityPage.stream().map(CartEntity::toDto).collect(Collectors.toList()));
            BeanUtils.copyProperties(cartEntityPage, genericPage);

        } catch (Exception e) {
            log.error("ERROR WHILE SAVING CART {}", e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(genericPage);
    }

    @Override
    public ResponseEntity<ResponseDto> getByUserId(int userId) {

        GenericPage<CartDto> genericPage = new GenericPage<>();

        Page<CartEntity> cartEntityPage = cartRepository
                .findByUserId(userId,
                        PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, "id")));

        genericPage.setData(cartEntityPage.stream().map(this::setProductName).collect(Collectors.toList()));
        BeanUtils.copyProperties(cartEntityPage, genericPage);

        return ResponseEntity.status(HttpStatus.OK).body(genericPage);
    }

    CartDto setProductName(CartEntity cartEntity) {

        ProductEntity productEntity = productRepository.findById(cartEntity.getProductId()).orElseThrow(() ->
                new RecordNotFoundException(String.format("Product object not found for : %s",
                        cartEntity.getProductId()), ""));

        CartDto cartDto = cartEntity.toDto();
        cartDto.setProductName(productEntity.getName());

        return cartDto;

    }
}
