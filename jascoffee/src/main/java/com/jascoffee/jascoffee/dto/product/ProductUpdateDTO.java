package com.jascoffee.jascoffee.dto.product;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductUpdateDTO {
    private String productName;
    private int price;
    private String category;
    private List<ProductOptionCreateDTO> options;
}
