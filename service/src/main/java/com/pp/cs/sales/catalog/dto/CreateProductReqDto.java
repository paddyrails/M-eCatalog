package com.pp.cs.sales.catalog.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CreateProductReqDto {

    private  String name;

    private  String code;

    private String description;

    private BigDecimal usPrice;

    private BigDecimal mexicoPrice;

    private BigDecimal canadaPrice;
}
