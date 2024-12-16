package com.jascoffee.jascoffee.dto.product;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOptionCreateDTO {
    private String optionName;     // 옵션명
    private Integer optionPrice;   // 옵션 가격
}