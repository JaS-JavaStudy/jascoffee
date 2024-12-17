package com.jascoffee.jascoffee.controller.orderlist;


import com.jascoffee.jascoffee.dto.orderlist.request.OrderDetailCreateRequest;
import com.jascoffee.jascoffee.dto.orderlist.response.OrderDetailResponse;
import com.jascoffee.jascoffee.entity.orderlist.OrderDetailEntity;
import com.jascoffee.jascoffee.service.orderlist.OrderDetailService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderDetailController {
    private OrderDetailService orderDetailService;

    public OrderDetailController(OrderDetailService orderDetailService){
        this.orderDetailService = orderDetailService;
    }
    @PostMapping("/orderdetail")
    public void saveOrderDetail(@RequestBody OrderDetailCreateRequest request) {
        orderDetailService.saveOrderDetail(request);
    }

    @GetMapping("/orderdetailAll")
    public List<OrderDetailResponse> getOrderDetails() {
        return orderDetailService.getOrderDetails();
    }

//    @GetMapping("/orderdetail")
//    public List<OrderDetailResponse> getOrderDetail(){
//        return orderDetailService.getOrderDetail();
//    }


//    @DeleteMapping("/orderdetails/{detailID}")
//    public void deleteOrderDetail(@PathVariable Long detailID) {
//        orderDetailService.deleteOrderDetail(detailID);
//    }


}
