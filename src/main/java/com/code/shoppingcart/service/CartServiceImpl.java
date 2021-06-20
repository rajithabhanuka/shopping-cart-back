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
    public ResponseEntity<ResponseDto> addToCart(CartDto dto) {

        ProductEntity productEntity = productRepository.findById(dto.getProductId()).orElseThrow(() ->
                new RecordNotFoundException(String.format("Product object not found for : %s",
                        dto.getProductId()), ""));

        GenericPage<CartDto> genericPage = new GenericPage<>();

        try {

            int qty;
            double price;

            if ("cartoon".equals(dto.getOrderType())) {

                qty = productEntity.getUnitsPerCartoon() * dto.getQty();

            } else {

                qty = dto.getQty();

            }

            CartEntity cartEntity;
            Optional<CartEntity> existing = cartRepository.findByUserIdAndProductId(dto.getUserId(), dto.getProductId());


            if (existing.isPresent()) {

                log.info("CART IS PRESENT");

                cartEntity = existing.get();
                qty = cartEntity.getQty() + qty;

                log.info("NEW QUANTITY IS: {}", qty);

                // Get the discount margin (E.x minimum cartoons to be bought to get the discount)
                int discountMargin = productEntity.getDiscountEligibility();
                log.info("DISCOUNT MARGIN FOR CARTOON: {}", discountMargin);

                // Calculate minimum units to eligible to discount based on cartoons
                int minimumQtyToDiscount = discountMargin * productEntity.getUnitsPerCartoon();
                log.info("MINIMUM ELIGIBLE UNITS FOR DISCOUNT: {}", minimumQtyToDiscount);

                // Checking whether entered qty eligible or not
                price = calculatePrice(qty, productEntity);

                cartEntity = existing.get();
                cartEntity.setPrice(price);

            } else {
                cartEntity = dto.toEntity();

                cartEntity.setPrice(calculatePrice(qty, productEntity));

            }
            cartEntity.setQty(qty);

            cartRepository.save(cartEntity);

            Page<CartEntity> cartEntityPage = cartRepository
                    .findByUserIdAndQtyGreaterThan(dto.getUserId(), 0,
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
                .findByUserIdAndQtyGreaterThan(userId, 0,
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

    private double calculatePrice(int qty, ProductEntity productEntity) {

        double price;

        // Calculate the excess qty
        int excessQty = (qty % productEntity.getUnitsPerCartoon());

        // Calculate cartoons
        int cartoons = (qty / productEntity.getUnitsPerCartoon());

        log.info("CARTOONS: {} , UNITS {} ", cartoons, excessQty);

        double cartoonsPrice;

        if (cartoons >= productEntity.getDiscountEligibility()) {

            // Applying discount for cartoons if eligible
            cartoonsPrice = (cartoons * (1 - (productEntity.getCartoonDiscount())) * productEntity.getPricePerCartoon());
        } else {
            // if not calculate as usual
            cartoonsPrice = (cartoons * productEntity.getPricePerCartoon());
        }

        // calculate price for excess qty it should multiply by 1 + penalty discount
        double priceForExcessUnits = excessQty * (productEntity.getPricePerCartoon() * (1 + productEntity.getUnitDiscount())) / productEntity.getUnitsPerCartoon();

        log.info("CARTOONS PRICE: {} , UNITS PRICE {} ", cartoonsPrice, priceForExcessUnits);

        price = cartoonsPrice + priceForExcessUnits;

        price = BigDecimal.valueOf(price).setScale(3, RoundingMode.HALF_UP).doubleValue();

        return price;

    }
}
