package com.springboot.jpa.data.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ProductDto {
    private String name;
    private int price;
    private int stock;
}
