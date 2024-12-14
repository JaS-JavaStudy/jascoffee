package com.jascoffee.jascoffee.dto.orderlist.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
public class OrderListUpdateRequest {
    private BigInteger orderID;
    private Integer totalPrice;
    private Boolean isCancel;
}
