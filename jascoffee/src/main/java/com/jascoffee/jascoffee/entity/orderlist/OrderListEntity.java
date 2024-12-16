package com.jascoffee.jascoffee.entity.orderlist;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "orderlist")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger orderID;

    @Column(nullable = false) // NOT NULL 제약 조건
    private BigInteger userID;

    @Column
    private Integer totalPrice;

    @Column
    private Boolean isCancel;

    @Column
    private LocalDateTime orderedAt;
}
