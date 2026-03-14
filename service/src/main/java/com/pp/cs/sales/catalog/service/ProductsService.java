package com.pp.cs.sales.catalog.service;

import com.pp.cs.sales.catalog.common.enums.CountryCode;
import com.pp.cs.sales.catalog.dto.ProductReqDto;
import com.pp.cs.sales.catalog.dto.ProductRespDto;

import java.util.List;

public interface ProductsService {

    public List<ProductRespDto> getProducts(CountryCode countryCode);
}
