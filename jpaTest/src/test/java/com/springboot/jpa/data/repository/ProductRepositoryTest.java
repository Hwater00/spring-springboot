package com.springboot.jpa.data.repository;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.springboot.jpa.data.entity.Product;
import com.springboot.jpa.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// 예제 7.17
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;  // 테스트하고 싶은 레포지토리를 가져옴
    @Test
    void save() {
        // given
        Product product = new Product();
        product.setName("펜");
        product.setPrice(1000);
        product.setStock(1000);
        // when
        Product savedProduct = productRepository.save(product);
        // then
        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getPrice(), savedProduct.getPrice());
        assertEquals(product.getStock(), savedProduct.getStock());
    }
}