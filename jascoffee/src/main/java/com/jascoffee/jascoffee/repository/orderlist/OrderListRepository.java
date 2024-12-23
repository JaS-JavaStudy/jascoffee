package com.jascoffee.jascoffee.repository.orderlist;

import com.jascoffee.jascoffee.entity.orderlist.OrderListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface OrderListRepository extends JpaRepository<OrderListEntity, Long> {
    List<OrderListEntity> findByUserID(Long userID);
    int deleteByOrderID(Long orderID);
}