package com.springboot.relationship.data.entity;

import lombok.*;
import javax.persistence.*;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name="product")
public class Product extends BaseEntity{
    /* 상품 테이블 */
    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본 키 생성을 데이터베이스에 위임 = Auto increament
    private Long number;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price; // 객체값을 갖고 있는 것이 hashMap 유리함. int가 아닌 Integer

    @Column(nullable = false)
    private Integer stock;

    //매핑
   @OneToOne(mappedBy = "product")
    private  ProductDetail productDetail;

   @ManyToOne
   @JoinColumn(name="provider_id")
   @ToString.Exclude
   private Provider provider;


}
