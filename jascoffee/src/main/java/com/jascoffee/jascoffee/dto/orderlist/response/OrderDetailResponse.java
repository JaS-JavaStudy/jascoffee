package com.jascoffee.jascoffee.dto.orderlist.response;

import com.jascoffee.jascoffee.entity.orderlist.OrderDetailEntity;
import lombok.Getter;

@Getter
public class OrderDetailResponse {
    private Long orderDetailID;
    private Long orderID;
    private Long productID;
    private Boolean isIce;
    private Integer shot;
    private Integer pearl;
    private Integer milk;
    private Integer syrup;
    private Integer vanillaSyrup;
    private Integer hazelnutSyrup;
    private Boolean isWhip;
    private Integer price;

    public OrderDetailResponse(OrderDetailEntity detail) {
        this.orderDetailID = detail.getOrderDetailID();
        this.orderID = detail.getOrderlist().getOrderID();
        this.productID = detail.getProduct().getProductId();
        this.isIce = detail.getIsIce();
        this.shot = detail.getShot();
        this.pearl = detail.getPearl();
        this.milk = detail.getMilk();
        this.syrup = detail.getSyrup();
        this.vanillaSyrup = detail.getVanilaSyrup();
        this.hazelnutSyrup = detail.getHazelnutSyrup();
        this.isWhip = detail.getIsWhip();
        this.price = detail.getPrice();
    }
//    public OrderDetailResponse(Long orderDetailID, Long orderID)
}
