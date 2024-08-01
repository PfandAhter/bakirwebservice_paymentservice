package com.bakirwebservice.paymentservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "shopping_cart")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "cart_id")
    private String shoppingCartId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "order_quantity")
    private int orderQuantity;

    @Column(name = "active")
    private int active;

    @Column(name = "status")
    private String status;

}
