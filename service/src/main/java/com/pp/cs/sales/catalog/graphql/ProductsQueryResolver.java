package com.pp.cs.sales.catalog.graphql;

import com.pp.cs.sales.catalog.common.enums.CountryCode;
import com.pp.cs.sales.catalog.dto.ProductRespDto;
import com.pp.cs.sales.catalog.service.ProductsService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProductsQueryResolver {

    private final ProductsService productsService;

    public ProductsQueryResolver(ProductsService productsService) {
        this.productsService = productsService;
    }

    @QueryMapping
    public List<CatalogProductView> products(@Argument CountryCode country) {
        return productsService.getProducts(country).stream()
                .map(this::toView)
                .toList();
    }

    private CatalogProductView toView(ProductRespDto dto) {
        return new CatalogProductView(
                dto.getName(),
                dto.getCode(),
                dto.getPrice()
        );
    }
}
