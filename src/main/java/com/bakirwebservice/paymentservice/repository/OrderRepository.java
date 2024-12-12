package com.bakirwebservice.paymentservice.repository;

import com.bakirwebservice.paymentservice.model.entity.OrderList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderList,String> {
    @Value("SELECT sc FROM shopping_cart sc WHERE (sc.customer_name = ?1) and sc.active = ?2")
    OrderList findByCustomerNameAndActive (String customerName,String active);


    @Value("SELECT * FROM payment_service.order_list WHERE customer_name = ?1 and active = ?2")
    List<OrderList> findOrderListsByCustomerNameAndActive(String customerName, String active);

    @Value("SELECT * FROM payment_service.order_list WHERE (customer_name = ?1 and product_code = ?2) and status = 1")
    OrderList findByCustomerNameAndProductCode(String customerName , String productCode);

    @Query("SELECT c from OrderList c where c.productCode in (?1)")
    List<OrderList> findAllProductByCriteria(List<String> productCode);

    @Query("select ol from OrderList ol where ol.customerName = ?1 AND ol.productCode = ?2")
    OrderList findOrderListByCustomerNameAndProductCode(String username, String productId);


    @Query("SELECT oc FROM OrderList oc WHERE (oc.trackingNumber = ?2 and oc.customerName = ?1) and oc.active = 2")
    List<OrderList> findByCustomerNameAndTrackingNumber (String username , String trackingNumber);



}
