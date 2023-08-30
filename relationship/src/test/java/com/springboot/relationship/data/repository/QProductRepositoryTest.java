package com.springboot.relationship.data.repository;

import com.springboot.relationship.data.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.springboot.relationship.data.entity.QProduct;

import java.util.Optional;
//import java.util.function.Predicate;
import com.querydsl.core.types.Predicate;
@SpringBootTest
public class QProductRepositoryTest {

    @Autowired
    QProductRepository qProductRepository;

    @Test
    public void queryDSLTest1(){
        Predicate predicate = (Predicate) QProduct.product.name.containsIgnoreCase("펜")
                .and(QProduct.product.price.between(1000,2500));

        Optional<Product> foundProduct = qProductRepository.findOne((com.querydsl.core.types.Predicate) predicate);

        if(foundProduct.isPresent()){
            Product product = foundProduct.get();
            System.out.println(product.getNumber());
            System.out.println( product.getName());
            System.out.println(product.getPrice());
            System.out.println(product.getStock());
        }
    }


    @Test
    public void queryDSLTest2(){
        QProduct qProduct = QProduct.product;

        Iterable<Product> productList = qProductRepository.findAll(
                qProduct.name.contains("펜")
                        .and(qProduct.price.between(550,1500))
        );

        for(Product product: productList) {
            System.out.println("Product Number:" + product.getNumber());
            System.out.println("Product Name" + product.getName());
            System.out.println("Product Price" + product.getPrice());
            System.out.println("Product Stock" + product.getStock());
        }
    }

}
