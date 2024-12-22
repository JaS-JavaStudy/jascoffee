package com.jascoffee.jascoffee.controller.orderlist;

import com.jascoffee.jascoffee.dto.orderlist.request.OrderListCreateRequest;
import com.jascoffee.jascoffee.dto.orderlist.request.OrderListUpdateRequest;
import com.jascoffee.jascoffee.dto.orderlist.response.OrderListResponse;
import com.jascoffee.jascoffee.dto.user.UserDTO;
import com.jascoffee.jascoffee.entity.user.UserEntity;
import com.jascoffee.jascoffee.jwt.JWTTokenProvider;
import com.jascoffee.jascoffee.repository.user.UserRepository;
import com.jascoffee.jascoffee.service.orderlist.OrderListService;
import com.jascoffee.jascoffee.service.user.UserService;
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
    private final JWTTokenProvider jwtTokenProvider;
    private final UserService userService;
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
    public ResponseEntity<OrderListResponse> updateOrder(@PathVariable("orderID") Long orderID, @RequestBody OrderListUpdateRequest request) {
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
    // ** 마이페이지: 본인의 모든 주문 목록 조회 **
    @GetMapping("/my")
    public ResponseEntity<List<OrderListResponse>> getMyOrders(@RequestHeader("access") String token) {
        // 1. JWT에서 account 추출
        String account = jwtTokenProvider.getAccountFromToken(token);

        // 2. UserService를 통해 account로 UserEntity 조회
        UserEntity user = userService.findByAccount(account);

        // 3. UserEntity에서 userId 추출
        Long userId = user.getUserID(); // `getId`는 UserEntity의 primary key를 가져옴

        // 4. userId를 이용해 주문 목록 조회 (OrderService에서 구현)
        List<OrderListResponse> orderList = orderListService.getOrdersByUserId(userId);

        // 5. 결과 반환
        return ResponseEntity.ok(orderList);
    }
}
