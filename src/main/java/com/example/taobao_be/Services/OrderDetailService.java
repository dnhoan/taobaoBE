package com.example.taobao_be.Services;

import com.example.taobao_be.DTO.OrderDetailDTO;
import com.example.taobao_be.models.Order;
import com.example.taobao_be.models.OrderDetail;
import com.example.taobao_be.repositories.OrderDetailRepository;
import com.example.taobao_be.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ModelMapper modelMapper;

    public OrderDetailDTO createOrUpdate(OrderDetailDTO orderDetailDTO) throws Exception {
        Optional<Order> order = this.orderRepository.findById(orderDetailDTO.getOrderId());
        if(order.isPresent()) {
            OrderDetail orderDetail = this.modelMapper.map(orderDetailDTO, OrderDetail.class);
            orderDetail.setOrder(order.get());
            return this.modelMapper.map(this.orderDetailRepository.save(orderDetail), OrderDetailDTO.class);
        }
        throw new Exception("Order not exist");
    }

    public void deleteOrderDetail(Long orderDetailId) throws Exception {
        log.info("delete order detail id: {}", orderDetailId);
        if(!this.orderDetailRepository.existsById(orderDetailId)) {
            throw new Exception("Id order detail not exist");
        }
        this.orderDetailRepository.deleteById(orderDetailId);
    }
}
