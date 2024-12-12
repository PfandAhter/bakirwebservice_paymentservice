package com.bakirwebservice.paymentservice.repository;

import com.bakirwebservice.paymentservice.model.entity.ShoppingCart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,String> {

    @Query("SELECT sc FROM ShoppingCart sc WHERE ( sc.customerName = ?1 and sc.productCode = ?2) and sc.active = 1")
    ShoppingCart findByCustomerNameAndProductCode(String customerName , String productCode);

    @Query("select sc from ShoppingCart sc where (sc.customerName = ?1) and sc.active LIKE 'ACTIVE'")
    List<ShoppingCart> findShoppingCartsByCriteria (String customerName);

}
