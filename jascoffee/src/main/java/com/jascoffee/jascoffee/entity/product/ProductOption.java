package com.jascoffee.jascoffee.entity.product;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "productOption")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "optionID")
    private Long optionId;

    @Column(name = "optionName")
    private String optionName;

    @Column(name = "optionPrice")
    private Integer optionPrice;

    @ManyToOne(fetch = FetchType.LAZY)  // 지연 로딩 설정
    @JoinColumn(name = "productID")     // 외래키 설정
    private Product product;

    // Product 설정을 위한 메서드
    protected void setProduct(Product product) {
        this.product = product;
    }
}