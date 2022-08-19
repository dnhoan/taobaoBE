package com.example.taobao_be.Services;

import com.example.taobao_be.DTO.OrderDTO;
import com.example.taobao_be.DTO.OrderDetailDTO;
import com.example.taobao_be.DTO.PageDTO;
import com.example.taobao_be.DTO.ProductDTO;
import com.example.taobao_be.models.Order;
import com.example.taobao_be.models.OrderDetail;
import com.example.taobao_be.models.Product;
import com.example.taobao_be.repositories.OrderDetailRepository;
import com.example.taobao_be.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ModelMapper modelMapper;
    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    public OrderDTO create(OrderDTO orderDTO) throws Exception {
        try {

            Order order = this.modelMapper.map(orderDTO, Order.class);
            order.setOrderId(String.valueOf(this.timestamp.getTime()));
            order.setStatus("1");
            order.setOrderStatus(0);
            order.setTotalMoney(this.calculateTotalMoney(orderDTO));
            order.setCtime(this.timestamp);
            this.orderRepository.saveAndFlush(order);
            List<OrderDetail> orderDetailList = orderDTO.getOrderDetailDTOS().stream().map(o -> {
                OrderDetail orderDetail = this.modelMapper.map(o, OrderDetail.class);
                orderDetail.setOrder(order);
                return orderDetail;
            }).collect(Collectors.toList());
            this.orderDetailRepository.saveAllAndFlush(orderDetailList);
            OrderDTO newOrderDTO = this.modelMapper.map(order, OrderDTO.class);
            newOrderDTO.setOrderDetailDTOS(
                    orderDetailList.stream().map(o -> this.modelMapper.map(o, OrderDetailDTO.class)).collect(Collectors.toList())
            );
            return newOrderDTO;
        } catch (Exception e) {
            throw new Exception("Lỗi cập nhật order");
        }
    }

    public OrderDTO update(OrderDTO orderDTO) throws Exception {
        try {
            Order order = this.modelMapper.map(orderDTO, Order.class);
            order.setTotalMoney(this.calculateTotalMoney(orderDTO));
            order.setMtime(this.timestamp);


            List<OrderDetail> orderDetailList = orderDTO.getOrderDetailDTOS().stream().map(o -> {
                OrderDetail orderDetail = this.modelMapper.map(o, OrderDetail.class);
                orderDetail.setOrder(order);
                log.info("order detail {}", orderDetail);
                return orderDetail;
            }).collect(Collectors.toList());
            this.orderDetailRepository.saveAllAndFlush(orderDetailList);
            order.setOrderDetailList(orderDetailList);
            log.info("before save and flush order {}", order);

            this.orderRepository.save(order);

            log.info("after save and flush order {}", order);

            OrderDTO newOrderDTO = this.modelMapper.map(order, OrderDTO.class);
            newOrderDTO.setOrderDetailDTOS(
                    orderDetailList.stream().map(o -> this.modelMapper.map(o, OrderDetailDTO.class)).collect(Collectors.toList())
            );
            return newOrderDTO;
        } catch (Exception e) {
            throw new Exception("Lỗi cập nhật order");
        }
    }

    public PageDTO<OrderDTO> getAll(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Order> page = this.orderRepository.findAll(pageable);
        return getOrderDTOPageDTO(page);
    }


    public PageDTO<OrderDTO> getOrdersByUserId(Long userId, int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Order> page = this.orderRepository.findOrdersByUserId(userId, pageable);
        return getOrderDTOPageDTO(page);
    }

    public OrderDTO getOrderByOrderId(String orderId) throws Exception {
        if(!this.orderRepository.existsByOrderId(orderId)) {
            throw new Exception("OrderId not exist");
        }
        Order order  = this.orderRepository.findOrdersByOrderId(orderId);
        return getOrderDTO(order);
    }

    private OrderDTO getOrderDTO(Order order) {
        List<OrderDetailDTO> orderDetailDTOS = order.getOrderDetailList().stream().map(o -> this.modelMapper.map(o, OrderDetailDTO.class)).collect(Collectors.toList());
        OrderDTO o = this.modelMapper.map(order, OrderDTO.class);
        o.setOrderDetailDTOS(orderDetailDTOS);
        return o;
    }

    private PageDTO<OrderDTO> getOrderDTOPageDTO(Page<Order> page) {
        List<OrderDTO> orderDTOS = page.stream().map(u -> {
            return getOrderDTO(u);
        }).collect(Collectors.toList());
        return new PageDTO<OrderDTO>(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                orderDTOS,
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious()
        );
    }


    private Double calculateTotalMoney(OrderDTO orderDTO) {
        return orderDTO.getOrderDetailDTOS().stream().mapToDouble(o -> o.getPrice() * o.getAmount()).sum();
    }

    public void updateOrderStatus(Integer newStatus, Long id) throws Exception {
        log.info("update status order {} to new status {}",id, newStatus );
       try {
           this.orderRepository.updateOrderStatus(newStatus, id);
       } catch (Exception e) {
           throw new Exception("Lỗi cập nhật trạng thái đơn hàng");
       }
    }
}
