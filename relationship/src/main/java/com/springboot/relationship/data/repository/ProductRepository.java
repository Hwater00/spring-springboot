package com.springboot.relationship.data.repository;


import com.springboot.relationship.data.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {

}
