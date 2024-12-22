package com.jascoffee.jascoffee.controller.product;

import com.jascoffee.jascoffee.dto.product.*;
import com.jascoffee.jascoffee.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 상품 등록 (이미지 포함)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDetailDTO> createProduct(
            @RequestPart("data") ProductCreateDTO productCreateDTO,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            ProductDetailDTO createdProduct = productService.createProduct(productCreateDTO, image);
            return ResponseEntity.ok(createdProduct);
        } catch (IOException e) {
            // 파일 처리 중 오류 발생 시
            return ResponseEntity.internalServerError().build();
        }
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

    // 상품 삭제
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    // 상품 수정 (이미지 포함)
    @PutMapping(value = "/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDetailDTO> updateProduct(
            @PathVariable("productId") Long productId,
            @RequestPart("data") ProductUpdateDTO productUpdateDTO,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            ProductDetailDTO updatedProduct = productService.updateProduct(productId, productUpdateDTO, image);
            return ResponseEntity.ok(updatedProduct);
        } catch (IOException e) {
            // 파일 처리 중 오류 발생 시
            return ResponseEntity.internalServerError().build();
        }
    }

}