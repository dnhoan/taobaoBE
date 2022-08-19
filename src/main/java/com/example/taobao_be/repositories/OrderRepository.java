package com.example.taobao_be.repositories;

import com.example.taobao_be.DTO.OrderDTO;
import com.example.taobao_be.models.Category;
import com.example.taobao_be.models.Order;
import com.example.taobao_be.models.Product;
import com.example.taobao_be.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where o.user.id = :user_id")
    Page<Order> findOrdersByUserId(long user_id, Pageable pageable);

    Order findOrdersByOrderId(String order_id);

    @Modifying
    @Query("Update Order o SET o.orderStatus = :newStatus WHERE o.id = :id")
    void updateOrderStatus(Integer newStatus, Long id);

    boolean existsByOrderId(String order_id);
}
