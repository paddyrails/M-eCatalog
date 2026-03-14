package com.pp.cs.sales.catalog.service;

import com.pp.cs.sales.catalog.common.enums.CountryCode;
import com.pp.cs.sales.catalog.dao.ProductsDao;
import com.pp.cs.sales.catalog.dto.ProductReqDto;
import com.pp.cs.sales.catalog.dto.ProductRespDto;
import com.pp.cs.sales.catalog.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductsServiceImpl implements ProductsService{

    @Autowired
    private ProductsDao productsDao;

    @Override
    public List<ProductRespDto> getProducts(CountryCode countryCode) {
        List<ProductEntity> allProducts = this.productsDao.findAll();
        List<ProductRespDto> allProductsForCountry = allProducts.stream().map(product -> {
            ProductRespDto respProduct = new ProductRespDto();
            respProduct.setId(product.getId());
            respProduct.setName(product.getName());
            if(countryCode == CountryCode.US){
                respProduct.setPrice(product.getUsPrice());
            } else if (countryCode == CountryCode.CAN) {
                respProduct.setPrice(product.getCanadaPrice());
            } else if (countryCode == CountryCode.MEX) {
                respProduct.setPrice(product.getMexicoPrice());
            }
            return respProduct;
        }).toList();

        return allProductsForCountry;
    }
}
