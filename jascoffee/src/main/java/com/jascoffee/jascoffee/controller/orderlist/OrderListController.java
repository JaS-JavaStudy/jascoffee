package com.jascoffee.jascoffee.controller.orderlist;

import com.jascoffee.jascoffee.dto.orderlist.request.OrderListCreateRequest;
import com.jascoffee.jascoffee.dto.orderlist.request.OrderListUpdateRequest;
import com.jascoffee.jascoffee.dto.orderlist.response.OrderListResponse;
import com.jascoffee.jascoffee.service.orderlist.OrderListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("/api/orderlist")
@RequiredArgsConstructor
public class OrderListController {

    private final OrderListService orderListService;

    // 주문 생성
    @PostMapping
    public ResponseEntity<OrderListResponse> createOrder(@RequestBody OrderListCreateRequest request) {
        // service 호출 -> 주문 생성
        OrderListResponse response = orderListService.createOrder(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 주문 수정
    @PutMapping("/{orderID}")
    public ResponseEntity<OrderListResponse> updateOrder(@PathVariable BigInteger orderID, @RequestBody OrderListUpdateRequest request) {
        request.setOrderID(orderID);

        OrderListResponse response = orderListService.updateOrder(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
