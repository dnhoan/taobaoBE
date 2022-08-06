package com.example.taobao_be.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "order_id", nullable = false, length = 50)
    private String orderId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "order_status", nullable = false)
    private Integer orderStatus;

    @Lob
    @Column(name = "status")
    private String status;

    @Column(name = "delivery_address", nullable = false, length = 225)
    private String deliveryAddress;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "total_money", nullable = false)
    private Double totalMoney;

    @Column(name = "note")
    @Type(type = "org.hibernate.type.TextType")
    private String note;

    @Column(name = "cancel_note")
    @Type(type = "org.hibernate.type.TextType")
    private String cancelNote;

    @Column(name = "ctime", nullable = false)
    private Instant ctime;

    @Column(name = "mtime")
    private Instant mtime;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private List<OrderDetail> orderDetailList;
}