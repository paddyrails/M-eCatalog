package com.pp.cs.sales.catalog.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="Products")
public class ProductEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name", nullable = false)
    private  String name;

    @Column(name="code", nullable = false)
    private  String code;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name="us_price")
    private BigDecimal usPrice;

    @Column(name="mexico_price")
    private BigDecimal mexicoPrice;

    @Column(name = "canada_price")
    private BigDecimal canadaPrice;
}
