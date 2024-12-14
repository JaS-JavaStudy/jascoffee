package com.jascoffee.jascoffee.repository.product;

import com.jascoffee.jascoffee.entity.product.Product;
import com.jascoffee.jascoffee.entity.product.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
    // 특정 상품의 모든 옵션 찾기
    List<ProductOption> findByProduct(Product product);
}
