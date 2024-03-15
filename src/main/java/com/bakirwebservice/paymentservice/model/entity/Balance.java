package com.bakirwebservice.paymentservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.sql.Timestamp;


@Entity
@Table(name = "balance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "balance_id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "money_code")
    private String money_code;

    @Column(name = "last_update")
    private Timestamp last_update;
}
