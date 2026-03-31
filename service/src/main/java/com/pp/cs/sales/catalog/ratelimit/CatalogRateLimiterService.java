package com.pp.cs.sales.catalog.ratelimit;

public interface CatalogRateLimiterService {

    ConsumptionResult tryConsume(String clientKey);
}
