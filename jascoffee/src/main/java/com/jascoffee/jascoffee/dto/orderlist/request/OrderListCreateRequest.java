package com.jascoffee.jascoffee.dto.orderlist.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
public class OrderListCreateRequest {
    // orderID는 서버에서 자동 생성
    private Long userID;
    private Integer totalPrice;
    private Boolean isCancel;

// 롬복 안 쓸 경우
//    // Getter & Setter
//    public BigInteger getUserID() {
//        return userID;
//    }
//
//    public void setUserID(BigInteger userID) {
//        this.userID = userID;
//    }
//
//    public Integer getTotalPrice() {
//        return totalPrice;
//    }
//
//    public void setTotalPrice(Integer totalPrice) {
//        this.totalPrice = totalPrice;
//    }
//
//    public Boolean getIsCancel() {
//        return isCancel;
//    }
//
//    public void setIsCancel(Boolean isCancel) {
//        this.isCancel = isCancel;
//    }

}
