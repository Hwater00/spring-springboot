package com.springboot.relationship.data.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="product_detail")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode
public class ProductDetail extends BaseEntity {
    /* 상품정보 테이블 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @OneToOne  //: null 값 허옹
    //@OneToOne(optional = false) // null 값 허용하지 않음
    @JoinColumn(name="product_number") // 외래키 관리= 주인
    @ToString.Exclude //주로 연관 관계가 있는 엔티티 클래스에서 사용되지만, 연관 관계가 있는 어느 쪽에나 적용할 수 있습니다
    private  Product product;
}

