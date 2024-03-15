package com.bakirwebservice.paymentservice.repository;

import com.bakirwebservice.paymentservice.model.entity.OrderList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderList,Long> {
    @Value("SELECT * FROM payment_service.order_list WHERE customer_name = ? and active = ?")
    OrderList findByCustomerNameAndActive (String customerName,int active);


    @Value("SELECT * FROM payment_service.order_list WHERE customer_name = ? and active = ?")
    List<OrderList> findOrderListsByCustomerNameAndActive(String customerName, int active);

    @Value("SELECT * FROM payment_service.order_list WHERE customer_name = ? and product_code = ?")
    OrderList findByCustomerNameAndProductCode(String customerName , Long productCode);
}
