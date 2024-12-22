package com.jascoffee.jascoffee.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailDTO {
    private Long productId;        // 상품 ID
    private String productName;    // 상품명
    private Integer price;         // 상품 가격
    private String category;       // 상품 카테고리
    private String imageUrl;
    private List<ProductOptionCreateDTO> options;  // 상품의 옵션 목록
}