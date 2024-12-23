package com.jascoffee.jascoffee.service.orderlist;

import com.jascoffee.jascoffee.dto.orderlist.request.OrderListCreateRequest;
import com.jascoffee.jascoffee.dto.orderlist.request.OrderListUpdateRequest;
import com.jascoffee.jascoffee.dto.orderlist.response.OrderListResponse;
import com.jascoffee.jascoffee.entity.orderlist.OrderListEntity;
import com.jascoffee.jascoffee.entity.user.UserEntity;
import com.jascoffee.jascoffee.repository.orderlist.OrderDetailRepository;
import com.jascoffee.jascoffee.repository.orderlist.OrderListRepository;
import com.jascoffee.jascoffee.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderListService {

    private final OrderListRepository orderListRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserRepository userRepository;

    // 주문 생성
    public OrderListResponse createOrder(OrderListCreateRequest request) {
        // Entity 변환
        OrderListEntity orderListEntity = OrderListEntity.builder()
                .userID(request.getUserID())
                .totalPrice(request.getTotalPrice())
                .isCancel(request.getIsCancel())
                .orderedAt(LocalDateTime.now())
                .build();

        // 데이터 저장
        OrderListEntity savedEntity = orderListRepository.save(orderListEntity);

        // 응답 DTO 반환
        return OrderListResponse.builder()
                .orderID(savedEntity.getOrderID())
                .userID(savedEntity.getUserID())
                .totalPrice(savedEntity.getTotalPrice())
                .isCancel(savedEntity.getIsCancel())
                .orderedAt(savedEntity.getOrderedAt())
                .build();
    }

    // 주문 수정
    public OrderListResponse updateOrder(OrderListUpdateRequest request) {
        OrderListEntity existingOrder = orderListRepository.findById(request.getOrderID())
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다. ID: " + request.getOrderID()));

        existingOrder.setTotalPrice(request.getTotalPrice());
        if(request.getIsCancel() != null) existingOrder.setIsCancel(request.getIsCancel());
        existingOrder.setOrderedAt(LocalDateTime.now());

        OrderListEntity updatedEntity = orderListRepository.save(existingOrder);

        return OrderListResponse.builder()
                .orderID(updatedEntity.getOrderID())
                .userID(updatedEntity.getUserID())
                .totalPrice(updatedEntity.getTotalPrice())
                .isCancel(updatedEntity.getIsCancel())
                .orderedAt(updatedEntity.getOrderedAt())
                .build();
    }   // 변경 사항 업데이트

    // 주문 삭제
    @Transactional
    public void deleteOrder(long orderID) {
        // OrderDetail 삭제
        orderDetailRepository.deleteByOrderlist_OrderID(orderID);

        // Order 삭제
        orderListRepository.deleteByOrderID(orderID);
    }


    // 특정 주문 조회
    public OrderListResponse getOrder(Long orderID) {
        OrderListEntity orderEntity = orderListRepository.findById(orderID)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다. ID: " + orderID));

        return OrderListResponse.builder()
                .orderID(orderEntity.getOrderID())
                .userID(orderEntity.getUserID())
                .totalPrice(orderEntity.getTotalPrice())
                .isCancel(orderEntity.getIsCancel())
                .orderedAt(orderEntity.getOrderedAt())
                .build();
    }

    // 모든 주문 목록 조회
    public List<OrderListResponse> getAllOrders() {
        List<OrderListEntity> orderEntities = orderListRepository.findAll();

        return orderEntities.stream()
                .map(order -> {
                    // userID로 사용자 정보 조회
                    UserEntity userEntity = userRepository.findById(order.getUserID())
                            .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. ID: " + order.getUserID()));

                    // OrderListResponse 생성
                    return OrderListResponse.builder()
                            .orderID(order.getOrderID())
                            .userID(order.getUserID())
                            .name(userEntity.getName()) // 사용자 이름 설정
                            .mmid(userEntity.getMmid())    // 사용자 mmid 설정
                            .totalPrice(order.getTotalPrice())
                            .isCancel(order.getIsCancel())
                            .orderedAt(order.getOrderedAt())
                            .build();
                })
                .collect(Collectors.toList());
    }


    // 특정 사용자의 모든 주문 조회
    public List<OrderListResponse> getOrdersByUserId(Long userId) {
        // Repository를 이용해 userID로 주문 목록 조회
        List<OrderListEntity> orderEntities = orderListRepository.findByUserID(userId);

        // Entity → Response DTO 변환
        return orderEntities.stream()
                .map(order -> OrderListResponse.builder()
                        .orderID(order.getOrderID())
                        .userID(order.getUserID())
                        .totalPrice(order.getTotalPrice())
                        .isCancel(order.getIsCancel())
                        .orderedAt(order.getOrderedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
