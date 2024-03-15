package com.bakirwebservice.paymentservice.repository;


import com.bakirwebservice.paymentservice.model.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends JpaRepository<Balance,Long> {

    Balance findBalanceByUsername (String username);

}
