package com.jascoffee.jascoffee.repository.orderlist;

import com.jascoffee.jascoffee.entity.orderlist.OrderListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface OrderListRepository extends JpaRepository<OrderListEntity, BigInteger> {
}