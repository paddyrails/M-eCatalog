package com.pp.cs.sales.catalog.graphql;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * GraphQL-facing product shape: only fields clients need (no id, timestamps, etc.).
 */
@Getter
@AllArgsConstructor
public class CatalogProductView {

    private final String name;
    private final String code;
    private final BigDecimal price;
}
