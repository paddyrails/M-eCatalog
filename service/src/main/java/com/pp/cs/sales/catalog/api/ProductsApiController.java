package com.pp.cs.sales.catalog.api;

import com.pp.cs.sales.catalog.dto.CreateProductReqDto;
import com.pp.cs.sales.catalog.dto.ProductRespDto;
import com.pp.cs.sales.catalog.common.enums.CountryCode;
import com.pp.cs.sales.catalog.common.enums.TeamCode;
import com.pp.cs.sales.catalog.entity.ProductEntity;
import com.pp.cs.sales.catalog.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<ProductEntity> createProduct(@RequestBody CreateProductReqDto createProductReqDto){
        return ResponseEntity.ok(this.productsService.createProduct(createProductReqDto));
    }

}
