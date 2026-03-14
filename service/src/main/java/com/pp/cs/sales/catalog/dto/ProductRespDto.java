package com.pp.cs.sales.catalog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductRespDto {
    private String id;

    private String name;

    private String code;

    private BigDecimal price;
}
