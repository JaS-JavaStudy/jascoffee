package com.jascoffee.jascoffee.repository.orderlist;

import com.jascoffee.jascoffee.entity.orderlist.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {
    List<OrderDetailEntity> findByOrderDetailID(Long orderDetailID);
}
