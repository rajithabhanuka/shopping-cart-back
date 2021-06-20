package com.code.shoppingcart.service;

import com.code.shoppingcart.dto.GenericPage;
import com.code.shoppingcart.dto.ProductDto;
import com.code.shoppingcart.dto.ResponseDto;
import com.code.shoppingcart.model.ProductEntity;
import com.code.shoppingcart.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<ResponseDto> create(ProductDto dto) {

        ProductEntity productEntity = dto.toEntity();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productRepository.save(productEntity).toDto());
    }

    /**
     *
     * @param page for pagination
     * @param size for pagination
     * @return al the products with pagination
     */
    @Override
    public ResponseEntity<ResponseDto> getAll(int page, int size) {

        Page<ProductEntity> productEntityPage = productRepository
                .findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id")));

        GenericPage<ProductDto> genericPage = new GenericPage<>();
        genericPage.setData(productEntityPage.stream().map(ProductEntity::toDto).collect(Collectors.toList()));
        BeanUtils.copyProperties(productEntityPage, genericPage);

        return ResponseEntity.status(HttpStatus.OK).body(genericPage);
    }
}
