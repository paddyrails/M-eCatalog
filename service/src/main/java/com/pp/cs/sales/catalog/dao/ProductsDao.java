package com.pp.cs.sales.catalog.dao;

import com.pp.cs.sales.catalog.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsDao extends JpaRepository<ProductEntity, String> {
}
