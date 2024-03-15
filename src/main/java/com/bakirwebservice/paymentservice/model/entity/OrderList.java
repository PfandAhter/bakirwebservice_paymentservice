package com.bakirwebservice.paymentservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "order_list")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class OrderList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderid;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "product_code")
    private Long productCode;

    @Column(name = "order_quantity")
    private int orderQuantity;

    @Column(name = "active")
    private int active;

    @Column(name = "tracking_number")
    private Long trackingNumber;

    @Column(name = "status")
    private String status;

}
