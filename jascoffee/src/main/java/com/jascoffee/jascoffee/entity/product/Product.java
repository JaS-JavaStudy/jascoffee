package com.jascoffee.jascoffee.entity.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productID")        // DB 컬럼명이랑 정확히 일치시킴
    private Long productId;

    @Column(name = "ProductName")      // DB 컬럼명이랑 정확히 일치시킴
    private String productName;

    @Column(name = "Price")           // DB 컬럼명이랑 정확히 일치시킴
    private Integer price;

    @Column(name = "Category")        // DB 컬럼명이랑 정확히 일치시킴
    private String category;
}