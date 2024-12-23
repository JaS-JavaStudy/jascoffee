package com.jascoffee.jascoffee.dto.orderlist.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Builder
public class OrderListResponse {
    private Long orderID;
    private String name;
    private String mmid;
    private Long userID;
    private Integer totalPrice;
    private Boolean isCancel;
    private LocalDateTime orderedAt;
}
