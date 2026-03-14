package com.pp.cs.sales.catalog.api;

import com.pp.cs.sales.catalog.dto.ProductRespDto;
import com.pp.cs.sales.catalog.common.enums.CountryCode;
import com.pp.cs.sales.catalog.common.enums.TeamCode;
import com.pp.cs.sales.catalog.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/api/sales/catalog/v1/products", produces = "application/json")
public class ProductsApiController {

    @Autowired
    private ProductsService productsService;

    @GetMapping
    public ResponseEntity<List<ProductRespDto>> getProducts(@RequestParam CountryCode country)
    {

        List<ProductRespDto> countryProducts = this.productsService.getProducts(country);
        return ResponseEntity.ok(countryProducts);
    }
}
