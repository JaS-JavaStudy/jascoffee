package com.jascoffee.jascoffee.entity.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productID")
    private Long productId;

    @Column(name = "ProductName")
    private String productName;

    @Column(name = "Price")
    private Integer price;

    @Column(name = "Category")
    private String category;

    // ProductOption과의 양방향 관계 설정
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default  // Builder 패턴 사용시 options 필드 초기화
    private List<ProductOption> options = new ArrayList<>();

    // 연관관계 편의 메서드
    public void addOption(ProductOption option) {
        this.options.add(option);  // 상품의 옵션 목록에 추가
        option.setProduct(this);   // 옵션의 상품 설정
    }
}