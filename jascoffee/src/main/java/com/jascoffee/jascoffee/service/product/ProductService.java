package com.jascoffee.jascoffee.service.product;

import com.jascoffee.jascoffee.dto.product.*;
import com.jascoffee.jascoffee.entity.product.Product;
import com.jascoffee.jascoffee.entity.product.ProductOption;
import com.jascoffee.jascoffee.repository.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// 이미지 관련 임포트
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor // final이나 @NonNull이 붙은 필드에 대한 생성자를 자동으로 생성
@Transactional
public class ProductService {

    private final ProductRepository productRepository;


    // 상품 등록
    // 옵션까지 한 번에 등록되게 구현되어있습니다
    /**
     POST http://localhost:8080/products
     {
        "productName": "초코케이크",
        "price": 6500,
        "category": "Dessert",
        "options": [
             {
                 "optionName": "초코시럽 추가",
                 "optionPrice": 300
             },
             {
                 "optionName": "따뜻하게 데우기",
                 "optionPrice": 0
             }
         ]
     }
    **/
    public ProductDetailDTO createProduct(ProductCreateDTO productCreateDTO, MultipartFile image) throws IOException {
        // 이미지 처리
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            imageUrl = saveImage(image);
        }

        // 1. 상품 엔티티 생성
        Product product = Product.builder()
                .productName(productCreateDTO.getProductName())
                .price(productCreateDTO.getPrice())
                .category(productCreateDTO.getCategory())
                .imageUrl(imageUrl)
                .build();

        // 2. 옵션 엔티티 생성 및 연관관계 설정
        if (productCreateDTO.getOptions() != null && !productCreateDTO.getOptions().isEmpty()) {
            productCreateDTO.getOptions().forEach(optionDTO -> {
                ProductOption option = ProductOption.builder()
                        .optionName(optionDTO.getOptionName())
                        .optionPrice(optionDTO.getOptionPrice())
                        .build();
                product.addOption(option);
            });
        }

        // 3. 저장 및 반환
        Product savedProduct = productRepository.save(product);
        return convertToDetailDTO(savedProduct);
    }

    private String saveImage(MultipartFile file) throws IOException {
        String uploadDir = "uploads/images";
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir).resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return "/images/" + fileName;
    }


    // 전체 상품 목록 조회
    // GET http://localhost:8080/products
    @Transactional(readOnly = true)
    public List<ProductListDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToListDTO)
                .collect(Collectors.toList());
    }


    // 상품 상세 정보 조회
    // GET http://localhost:8080/products/1
    @Transactional(readOnly = true)
    public ProductDetailDTO getProductDetail(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));
        return convertToDetailDTO(product);
    }

    // DTO 변환 메서드
    private ProductListDTO convertToListDTO(Product product) {
        return ProductListDTO.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .category(product.getCategory())
                .imageUrl(product.getImageUrl())
                .build();
    }

    private ProductDetailDTO convertToDetailDTO(Product product) {
        List<ProductOptionCreateDTO> options = product.getOptions().stream()
                .map(option -> ProductOptionCreateDTO.builder()
                        .optionName(option.getOptionName())
                        .optionPrice(option.getOptionPrice())
                        .build())
                .collect(Collectors.toList());

        return ProductDetailDTO.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .category(product.getCategory())
                .imageUrl(product.getImageUrl())
                .options(options)
                .build();
    }

    // 상품 삭제
    public void deleteProduct(Long productId) {
        // 상품 조회
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));

        // 상품 삭제 (Cascade 옵션에 따라 옵션도 자동으로 삭제됨)
        productRepository.delete(product);
    }

    // 상품 수정
    public ProductDetailDTO updateProduct(Long productId, ProductUpdateDTO productUpdateDTO, MultipartFile image) throws IOException {
        // 1. 상품 조회
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));

        // 2. 이미지 처리
        if (image != null && !image.isEmpty()) {
            String imageUrl = saveImage(image);
            product.setImageUrl(imageUrl);
        }

        // 3. 상품 정보 업데이트
        product.setProductName(productUpdateDTO.getProductName());
        product.setPrice(productUpdateDTO.getPrice());
        product.setCategory(productUpdateDTO.getCategory());

        // 4. 옵션 업데이트
        updateProductOptions(product, productUpdateDTO.getOptions());

        // 5. 저장 및 반환
        Product updatedProduct = productRepository.save(product);
        return convertToDetailDTO(updatedProduct);
    }

    // 옵션 업데이트 (기존 옵션 삭제 후 새 옵션 추가)
    private void updateProductOptions(Product product, List<ProductOptionCreateDTO> newOptions) {
        // 기존 옵션 삭제
        product.getOptions().clear();

        // 새로운 옵션 추가
        if (newOptions != null && !newOptions.isEmpty()) {
            newOptions.forEach(optionDTO -> {
                ProductOption newOption = ProductOption.builder()
                        .optionName(optionDTO.getOptionName())
                        .optionPrice(optionDTO.getOptionPrice())
                        .build();
                product.addOption(newOption); // 연관 관계 설정
            });
        }
    }

}