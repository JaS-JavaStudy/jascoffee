package com.jascoffee.jascoffee.repository.product;

import com.jascoffee.jascoffee.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // findBy + 필드명 - Spring Data JPA의 메서드 이름 규칙
    // SELETC * FROM product WHERE category = ? 와 같음
    List<Product> findByCategory(String category);


}
