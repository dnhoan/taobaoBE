package com.example.taobao_be.controllers;

import com.example.taobao_be.Constants;
import com.example.taobao_be.DTO.OrderDTO;
import com.example.taobao_be.DTO.OrderDetailDTO;
import com.example.taobao_be.DTO.ResponseDTO;
import com.example.taobao_be.Services.OrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController()
@RequestMapping("api")
@CrossOrigin(Constants.baseUrlFE)
@Slf4j
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("admin/order_detail")
    public ResponseEntity<ResponseDTO> createOrderDetail(@RequestBody OrderDetailDTO orderDetailDTO) {
        try {
            OrderDetailDTO orderDetail = this.orderDetailService.createOrUpdate(orderDetailDTO);
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .data(Map.of("order_detail", orderDetail))
                            .statusCode(HttpStatus.CREATED.value())
                            .status(HttpStatus.CREATED)
                            .message("Thêm sản phẩm vào đơn hàng thành công")
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            log.error("insert order detail", e);
            return ResponseEntity.internalServerError().body(
                    ResponseDTO.builder()
                            .message("Lỗi thêm sản phẩm vào đơn hàng")
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        }
    }

    @DeleteMapping("admin/order_detail/{order_detail_id}")
    public ResponseEntity<ResponseDTO> deleteOrderDetail(@PathVariable("order_detail_id") Long order_detail_id) {
        try {
            this.orderDetailService.deleteOrderDetail(order_detail_id);
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .message("Xóa sản phẩm vào đơn hàng thành công")
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            log.error("delete order detail", e);
            return ResponseEntity.internalServerError().body(
                    ResponseDTO.builder()
                            .message(e.getMessage())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        }
    }

    @PutMapping("admin/order_detail")
    public ResponseEntity<ResponseDTO> updateOrderDetail(@RequestBody OrderDetailDTO orderDetailDTO) {
        try {
            OrderDetailDTO orderDetail = this.orderDetailService.createOrUpdate(orderDetailDTO);
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .data(Map.of("order_detail", orderDetail))
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .message("Cập nhật thành công")
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            log.error("update order detail", e);
            return ResponseEntity.internalServerError().body(
                    ResponseDTO.builder()
                            .message("Lỗi cập nhật đơn hàng")
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        }
    }
}
