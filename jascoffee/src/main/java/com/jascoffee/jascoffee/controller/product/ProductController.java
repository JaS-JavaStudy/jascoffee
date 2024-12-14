package com.jascoffee.jascoffee.controller.product;

import com.jascoffee.jascoffee.dto.product.*;
import com.jascoffee.jascoffee.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor // final이나 @NonNull이 붙은 필드에 대한 생성자를 자동으로 생성
public class ProductController {

    // @RequiredArgsConstructor 해서 자동 생성 되는데 빨간 줄은 계속 뜨네용,,
    private final ProductService productService;


    // 상품 등록
    @PostMapping
    public ResponseEntity<ProductDetailDTO> createProduct(@RequestBody ProductCreateDTO productCreateDTO) {
        ProductDetailDTO createdProduct = productService.createProduct(productCreateDTO);
        return ResponseEntity.ok(createdProduct);
    }


    // 전체 상품 목록 조회
    @GetMapping
    public ResponseEntity<List<ProductListDTO>> getAllProducts() {
        List<ProductListDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }


    // 상품 상세 정보 조회
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailDTO> getProductDetail(@PathVariable("productId") Long productId) {
        ProductDetailDTO product = productService.getProductDetail(productId);
        return ResponseEntity.ok(product);
    }
}