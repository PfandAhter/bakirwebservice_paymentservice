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
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id")
    private String orderId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "order_quantity")
    private int orderQuantity;

    @Column(name = "active")
    private String active;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Column(name = "status")
    private String status;

}
