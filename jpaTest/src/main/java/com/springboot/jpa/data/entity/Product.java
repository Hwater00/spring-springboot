package com.springboot.jpa.data.entity;

//import jakarta.persistence.*;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(exclude = "name")
@Table(name="product")
public class Product {

    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본 키 생성을 데이터베이스에 위임 = Auto increament
    private Long number;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price; // 객체값을 갖고 있는 것이 hashMap 유리함. int가 아닌 Integer

    @Column(nullable = false)
    private Integer stock;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
