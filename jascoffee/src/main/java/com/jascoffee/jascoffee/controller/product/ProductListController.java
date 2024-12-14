package com.jascoffee.jascoffee.controller.product;

import com.jascoffee.jascoffee.dto.product.ProductListDTO;
import com.jascoffee.jascoffee.service.product.ProductListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor //생성자 자동 생성
public class ProductListController {

    private final ProductListService productListService;

    @GetMapping
    public ResponseEntity<List<ProductListDTO>> getAllProducts() {
        List<ProductListDTO> products = productListService.getAllProducts();
        return ResponseEntity.ok(products);
    }
}
