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
import java.util.List;

@RestController
@RequestMapping("/api/orderlist")  // API 기본 경로
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

    // 전체 주문 목록 조회
    @GetMapping
    public ResponseEntity<List<OrderListResponse>> getAllOrders() {
        List<OrderListResponse> responseList = orderListService.getAllOrders();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    // 특정 주문 조회
    @GetMapping("/{orderID}")
    public ResponseEntity<OrderListResponse> getOrder(@PathVariable BigInteger orderID) {
        OrderListResponse response = orderListService.getOrder(orderID);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
