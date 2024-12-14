package com.jascoffee.jascoffee.service.product;

import com.jascoffee.jascoffee.dto.product.ProductListDTO;
import com.jascoffee.jascoffee.entity.product.Product;
import com.jascoffee.jascoffee.repository.product.ProductRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //생성자 자동 생성
public class ProductListService {

    private final ProductRepository productRepository;


    public List<ProductListDTO> getAllProducts() {

        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ProductListDTO convertToDTO(Product product) {
        return ProductListDTO.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .category(product.getCategory())
                .build();
    }

}
