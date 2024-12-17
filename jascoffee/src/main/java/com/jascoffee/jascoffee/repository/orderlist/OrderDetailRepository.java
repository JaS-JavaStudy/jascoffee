package com.jascoffee.jascoffee.repository.orderlist;

import com.jascoffee.jascoffee.entity.orderlist.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {
}
