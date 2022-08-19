package com.example.taobao_be.DTO;

import com.example.taobao_be.models.OrderDetail;
import com.example.taobao_be.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;

    private String orderId;

    private UserDTO user;

    private Integer orderStatus;

    private String status;

    private String deliveryAddress;

    private String customerName;

    private String phoneNumber;

    private Double totalMoney;

    private String note;

    private String cancelNote;

    private Date ctime;

    private Date mtime;

    private List<OrderDetailDTO> orderDetailDTOS;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", orderStatus=" + orderStatus +
                ", status='" + status + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", customerName='" + customerName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", totalMoney=" + totalMoney +
                ", note='" + note + '\'' +
                ", cancelNote='" + cancelNote + '\'' +
                ", ctime=" + ctime +
                ", mtime=" + mtime +
                '}';
    }
}
