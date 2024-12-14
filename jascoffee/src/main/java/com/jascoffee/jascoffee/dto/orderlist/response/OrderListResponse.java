package com.jascoffee.jascoffee.dto.orderlist.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Builder
public class OrderListResponse {
    private BigInteger orderID;
    private BigInteger userID;
    private Integer totalPrice;
    private Boolean isCancel;
    private LocalDateTime orderedAt;
}
