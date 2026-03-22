package com.pp.cs.sales.catalog.service;

import com.pp.cs.sales.catalog.common.enums.CountryCode;
import com.pp.cs.sales.catalog.dto.CreateProductReqDto;
import com.pp.cs.sales.catalog.dto.ProductRespDto;
import com.pp.cs.sales.catalog.entity.ProductEntity;

import java.util.List;

public interface ProductsService {

    public List<ProductRespDto> getProducts(CountryCode countryCode);

    public ProductEntity createProduct(CreateProductReqDto createProductReqDto);
}
