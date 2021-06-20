package com.code.shoppingcart.controller;

import com.code.shoppingcart.dto.ProductDto;
import com.code.shoppingcart.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMVC;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService mockProductService;

    @Test
    public void createProductTest() throws Exception {

        String body = "{\n" +
                "    \"username\":\"rajithabhanuka1@gmail.com\",\n" +
                "    \"password\": \"PPassword@123\"\n" +
                "}";

        mockMVC.perform(MockMvcRequestBuilders.post("/authenticate")
                .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ProductDto productDto = getProducts();

        Mockito.when(mockProductService.create(Mockito.any(ProductDto.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED)
                .body(productDto));

        String input = objectMapper.writeValueAsString(productDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.
                post("/api/products")
                .accept(MediaType.APPLICATION_JSON)
                .content(input)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMVC.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        String outputJson = response.getContentAsString();
        Assertions.assertThat(outputJson).isEqualTo(input);
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    private ProductDto getProducts() {

        ProductDto productDto = new ProductDto();
        productDto.setId(1);
        productDto.setName("Penguin-ears");
        productDto.setUnitsPerCartoon(20);
        productDto.setPricePerCartoon(BigDecimal.valueOf(175.00).doubleValue());
        productDto.setCartoonDiscount(0.1);
        productDto.setDiscountEligibility(3);
        productDto.setUnitDiscount(0.3);
        productDto.setImage("https://www.pngkey.com/png/full/35-353179_image-ears-png-club-penguin-wiki-fandom-logotipo.png");

        return productDto;

    }


}
