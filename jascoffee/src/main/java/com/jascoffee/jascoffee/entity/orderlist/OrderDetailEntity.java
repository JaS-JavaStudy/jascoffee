package com.jascoffee.jascoffee.entity.orderlist;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jascoffee.jascoffee.entity.product.Product;
import jakarta.persistence.*;

@Entity
@Table(name = "orderdetail")
public class OrderDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailID;

    @ManyToOne
    @JoinColumn(name = "orderID", nullable = false, referencedColumnName = "orderID")
    private OrderListEntity orderlist;

    @ManyToOne
    @JoinColumn(name = "productID", nullable = false, referencedColumnName = "productID")
    private Product product;

    @JsonProperty("isIce")
    private Boolean isIce;
    @JsonProperty("shot")
    private Integer shot;
    @JsonProperty("pearl")
    private Integer pearl;
    @JsonProperty("milk")
    private Integer milk;
    @JsonProperty("syrup")
    private Integer syrup;
    @JsonProperty("vanilaSyrup")
    private Integer vanilaSyrup;
    @JsonProperty("hazelnutSyrup")
    private Integer hazelnutSyrup;
    @JsonProperty("isWhip")
    private Boolean isWhip;
    @JsonProperty("price")
    private Integer price;

    public void setOrderlist(OrderListEntity orderlist) {
        this.orderlist = orderlist;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setIsIce(Boolean ice) {
        isIce = ice;
    }

    public void setShot(Integer shot) {
        this.shot = shot;
    }

    public void setPearl(Integer pearl) {
        this.pearl = pearl;
    }

    public void setMilk(Integer milk) {
        this.milk = milk;
    }

    public void setSyrup(Integer syrup) {
        this.syrup = syrup;
    }

    public void setVanilaSyrup(Integer vanilaSyrup) {
        this.vanilaSyrup = vanilaSyrup;
    }

    public void setHazelnutSyrup(Integer hazelnutSyrup) {
        this.hazelnutSyrup = hazelnutSyrup;
    }

    public void setIsWhip(Boolean whip) {
        isWhip = whip;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getOrderDetailID() {
        return orderDetailID;
    }

    public OrderListEntity getOrderlist() {
        return orderlist;
    }

    public Product getProduct() {
        return product;
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

    public Integer getVanilaSyrup() {
        return vanilaSyrup;
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
