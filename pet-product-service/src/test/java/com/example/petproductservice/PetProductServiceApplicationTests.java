package com.example.petproductservice;

import com.example.petproductservice.model.Product;
import com.example.petproductservice.repository.ProductRepository;
import com.example.petproductservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.verification.VerificationMode;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateProduct() {
        Product testProduct = Product.builder()
                .id("1")
                .name("Test product")
                .description("Test description")
                .price(BigDecimal.valueOf(100))
                .build();
        when(productRepository.save(testProduct)).thenReturn(testProduct);
        Product createdProduct = productService.createProduct(testProduct);

        assertEquals(testProduct, createdProduct);
        verify(productRepository, times(1)).save(testProduct);
    }


    @Test
    void testGetAllProducts() {

        Product product1 = Product.builder()
                .id("1")
                .name("Iphone 15")
                .description("description")
                .price(BigDecimal.valueOf(5000))
                .build();
        Product product2 = Product.builder()
                .id("2")
                .name("Iphone 13")
                .description("description")
                .price(BigDecimal.valueOf(1000))
                .build();
        List<Product> productList = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(productList);
        List<Product> retrievedProducts = productService.getAllProducts();

        assertEquals(productList.size(), retrievedProducts.size());
        assertEquals(productList, retrievedProducts);
        verify(productRepository, times(1)).findAll();
    }
}