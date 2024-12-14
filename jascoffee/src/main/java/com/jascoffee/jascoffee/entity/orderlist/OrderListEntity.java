package com.jascoffee.jascoffee.entity.orderlist;

import jakarta.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "orderlist")
public class OrderListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger orderID;

    @Column(nullable = false)  // NOT NULL 제약 조건
    private BigInteger userID;

    @Column
    private Integer totalPrice;

    @Column
    private Boolean isCancel;

    @Column
    private LocalDateTime orderedAt;

    // 기본 생성자
    public OrderListEntity() {
    }

    public BigInteger getOrderID() {
        return orderID;
    }

    public void setOrderID(BigInteger orderID) {
        this.orderID = orderID;
    }

    public BigInteger getUserID() {
        return userID;
    }

    public void setUserID(BigInteger userID) {
        this.userID = userID;
    }

    public Integer getTotalPrice() {
        return totalPrice = totalPrice;
    }

    public Boolean getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(Boolean isCancel) {
        this.isCancel = isCancel;
    }

    public LocalDateTime getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(LocalDateTime orderedAt) {
        this.orderedAt = orderedAt;
    }
}
