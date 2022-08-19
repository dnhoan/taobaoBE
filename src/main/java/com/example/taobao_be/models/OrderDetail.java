package com.example.taobao_be.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "order_detail")
@Setter
@Getter
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "price", nullable = false)
    private Double price;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", amount=" + amount +
                ", price=" + price +
                ", order=" + order +
                ", product=" + product +
                '}';
    }
}