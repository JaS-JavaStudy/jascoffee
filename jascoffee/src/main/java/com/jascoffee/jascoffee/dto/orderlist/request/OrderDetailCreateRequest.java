package com.jascoffee.jascoffee.dto.orderlist.request;


public class OrderDetailCreateRequest {
    private Long orderID;            // OrderListEntity ID
    private Long productID;          // ProductEntity ID
    private Boolean isIce;
    private Integer shot;
    private Integer pearl;
    private Integer milk;
    private Integer syrup;
    private Integer vanillaSyrup;    // DTO에서는 camelCase 사용
    private Integer hazelnutSyrup;
    private Boolean isWhip;
    private Integer price;

    public  OrderDetailCreateRequest(){}

    public Long getOrderID() {
        return orderID;
    }

    public Long getProductID() {
        return productID;
    }

    public Boolean getIsIce() {
        return isIce;
    }

    public Integer getShot() {
        return shot;
    }

    public Integer getPearl() {
        return pearl;
    }

    public Integer getMilk() {
        return milk;
    }

    public Integer getSyrup() {
        return syrup;
    }

    public Integer getVanillaSyrup() {
        return vanillaSyrup;
    }

    public Integer getHazelnutSyrup() {
        return hazelnutSyrup;
    }

    public Boolean getIsWhip() {
        return isWhip;
    }

    public Integer getPrice() {
        return price;
    }
}
