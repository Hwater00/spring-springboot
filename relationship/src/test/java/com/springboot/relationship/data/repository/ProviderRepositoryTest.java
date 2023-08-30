package com.springboot.relationship.data.repository;


import com.springboot.relationship.data.entity.Product;
import com.springboot.relationship.data.entity.Provider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.beans.Transient;
import java.util.List;

@SpringBootTest
public class ProviderRepositoryTest {
    @Autowired
    ProviderRepository providerRepository;
    @Autowired
    ProductRepository productRepository;

    @Test
    void relationshipTest1(){
        Provider provider = new Provider();
        provider.setName("oo물산");

        providerRepository.save(provider);

        Product product = new Product();
        product.setName("가위");
        product.setPrice(5000);
        product.setStock(500);
        product.setProvider(provider);

        productRepository.save(product);

        // -- 테스트-- 생성한 연관관계 테스트
        System.out.println("product:"+productRepository.findById(1L)
                .orElseThrow(RuntimeException::new));

        System.out.println("provider"+productRepository.findById(1L)
                .orElseThrow(RuntimeException::new).getProvider());

//        System.out.println("Product:"+productRepository.findById(
//                product.getNumber()).get().getProvider());

    }

    @Test
    void relationshipTest(){
        Provider provider= new Provider();
        provider.setName("oo상사");
        providerRepository.save(provider);

        Product product1 = new Product();
        product1.setName("펜");
        product1.setPrice(2000);
        product1.setStock(100);
        product1.setProvider(provider);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("가방");
        product2.setPrice(20000);
        product2.setStock(200);
        product2.setProvider(provider);
        productRepository.save(product2);

        List<Product> products = providerRepository.findById(provider.getId()).get().getProductList();


    }
}
