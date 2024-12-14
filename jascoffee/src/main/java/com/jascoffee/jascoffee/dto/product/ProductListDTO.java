package com.jascoffee.jascoffee.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductListDTO {
    private Long productId;
    private String productName;
    private Integer price;
    private String category;
}
