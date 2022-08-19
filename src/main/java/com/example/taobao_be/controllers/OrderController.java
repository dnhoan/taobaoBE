package com.example.taobao_be.controllers;

import com.example.taobao_be.Constants;
import com.example.taobao_be.DTO.OrderDTO;
import com.example.taobao_be.DTO.PageDTO;
import com.example.taobao_be.DTO.ProductDTO;
import com.example.taobao_be.DTO.ResponseDTO;
import com.example.taobao_be.Services.OrderService;
import com.example.taobao_be.models.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController()
@RequestMapping("api")
@CrossOrigin(Constants.baseUrlFE)
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;


    @GetMapping("orders")
    public ResponseEntity<ResponseDTO> getOrderByUserId(
            @RequestParam(value = "user_id") Long user_id,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "20") int limit
    ) {
        PageDTO<OrderDTO> orderDTOPageDTO = this.orderService.getOrdersByUserId(user_id, offset, limit);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(OK)
                        .statusCode(OK.value())
                        .data(Map.of("orders", orderDTOPageDTO))
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("admin/orders")
    public ResponseEntity<ResponseDTO> getAllOrders(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "20") int limit
    ) {
        PageDTO<OrderDTO> orderDTOPageDTO = this.orderService.getAll(offset, limit);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(OK)
                        .statusCode(OK.value())
                        .data(Map.of("orders", orderDTOPageDTO))
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("admin/orders/{order_id}")
    public ResponseEntity<ResponseDTO> getOrderByOrderId(
            @PathVariable("order_id") String order_id
    ) {
        try {
            OrderDTO newOrderDTO = this.orderService.getOrderByOrderId(order_id);
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .data(Map.of("order", newOrderDTO))
                            .statusCode(OK.value())
                            .status(OK)
                            .message("Success")
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            log.error("create order", e);
            return ResponseEntity.internalServerError().body(
                    ResponseDTO.builder()
                            .message(e.getMessage())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        }
    }

    @PostMapping("orders")
    public ResponseEntity<ResponseDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        try {
            OrderDTO newOrderDTO = this.orderService.create(orderDTO);
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .data(Map.of("order", newOrderDTO))
                            .statusCode(HttpStatus.CREATED.value())
                            .status(HttpStatus.CREATED)
                            .message("Tạo đơn hàng thành công")
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            log.error("create order", e);
            return ResponseEntity.internalServerError().body(
                    ResponseDTO.builder()
                            .message("Lỗi tạo đơn hàng")
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        }
    }

    @PutMapping("admin/orders")
    public ResponseEntity<ResponseDTO> updateOrder(@RequestBody OrderDTO orderDTO) {
        try {
            OrderDTO newOrderDTO = this.orderService.update(orderDTO);
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .data(Map.of("order", newOrderDTO))
                            .statusCode(OK.value())
                            .status(OK)
                            .message("Cập nhật đơn hàng thành công")
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            log.error("update order", e);
            return ResponseEntity.internalServerError().body(
                    ResponseDTO.builder()
                            .message("Lỗi cập nhật đơn hàng")
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        }
    }

    @PutMapping("admin/orders/{id}")
    public ResponseEntity<ResponseDTO> updateOrderStatus(
            @PathVariable("id") Long id,
            @RequestBody Map<String, Integer> orderStatus) {
        try {
            this.orderService.updateOrderStatus(orderStatus.get("newOrderStatus"), id);
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .statusCode(OK.value())
                            .status(OK)
                            .message("Cập nhật trạng thái đơn hàng thành công")
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            log.error("update order", e);
            return ResponseEntity.internalServerError().body(
                    ResponseDTO.builder()
                            .message("Lỗi cập nhật trạng thái đơn hàng")
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        }
    }
}
