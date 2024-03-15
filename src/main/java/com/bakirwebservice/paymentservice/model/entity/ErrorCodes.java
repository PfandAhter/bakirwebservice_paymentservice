package com.bakirwebservice.paymentservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "error_codes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ErrorCodes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "error")
    private String error;

    @Column(name = "error_code")
    private Long error_code;

    @Column(name = "checked")
    private int checked;

    @Column(name = "error_description")
    private String error_description;

}
