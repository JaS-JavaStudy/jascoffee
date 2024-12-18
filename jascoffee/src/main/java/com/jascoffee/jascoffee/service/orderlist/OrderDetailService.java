package com.jascoffee.jascoffee.service.orderlist;

import com.jascoffee.jascoffee.dto.orderlist.request.OrderDetailCreateRequest;
import com.jascoffee.jascoffee.dto.orderlist.response.OrderDetailResponse;
import com.jascoffee.jascoffee.entity.orderlist.OrderDetailEntity;
import com.jascoffee.jascoffee.entity.orderlist.OrderListEntity;
import com.jascoffee.jascoffee.entity.product.Product;
import com.jascoffee.jascoffee.repository.orderlist.OrderDetailRepository;
import com.jascoffee.jascoffee.repository.orderlist.OrderListRepository;
import com.jascoffee.jascoffee.repository.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderListRepository orderListRepository;  // 추가
    private final ProductRepository productRepository;      // 추가


    public OrderDetailService(OrderDetailRepository orderDetailRepository , OrderListRepository orderListRepository, ProductRepository productRepository){
        this.orderDetailRepository = orderDetailRepository;
        this.orderListRepository = orderListRepository;
        this.productRepository = productRepository;
    }

    public void saveOrderDetail(OrderDetailCreateRequest request) {
        // OrderDetailEntity 객체 생성
        OrderDetailEntity detail = new OrderDetailEntity();

        // OrderListEntity와 ProductEntity를 repository에서 가져옴
        OrderListEntity orderlist = orderListRepository.findById(request.getOrderID())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Order ID: " + request.getOrderID()));
        Product product = productRepository.findById(request.getProductID())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Product ID: " + request.getProductID()));

        // OrderDetailEntity에 값 설정
        detail.setOrderlist(orderlist);
        detail.setProduct(product);
        detail.setIsIce(request.getIsIce());
        detail.setShot(request.getShot());
        detail.setPearl(request.getPearl());
        detail.setMilk(request.getMilk());
        detail.setSyrup(request.getSyrup());
        detail.setVanilaSyrup(request.getVanillaSyrup());
        detail.setHazelnutSyrup(request.getHazelnutSyrup());
        detail.setIsWhip(request.getIsWhip());
        detail.setPrice(request.getPrice());

        // 저장
        orderDetailRepository.save(detail);
    }

    public List<OrderDetailResponse> getOrderDetails() {
        return orderDetailRepository.findAll()
                .stream()
                .map(OrderDetailResponse::new)
                .collect(Collectors.toList());
    }
    public List<OrderDetailResponse> getOrderDetailByorderDetailID(Long orderDetailID) {
        List<OrderDetailEntity> orderdetails = orderDetailRepository.findByOrderDetailID(orderDetailID);
        return orderdetails.stream()
                .map(OrderDetailResponse::new)
                .collect(Collectors.toList());
    }


    public void deleteOrderDetail(Long detailID) {
        orderDetailRepository.deleteById(detailID);
    }
}
