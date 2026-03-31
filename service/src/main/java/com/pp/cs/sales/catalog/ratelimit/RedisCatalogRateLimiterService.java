package com.pp.cs.sales.catalog.ratelimit;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * Distributed token buckets backed by Redis (shared across catalog replicas).
 */
@Service
@ConditionalOnProperty(prefix = "catalog.rate-limit", name = "backend", havingValue = "redis")
public class RedisCatalogRateLimiterService implements CatalogRateLimiterService {

    private final ProxyManager<String> proxyManager;
    private final BucketConfiguration configuration;

    public RedisCatalogRateLimiterService(
            ProxyManager<String> catalogRedisProxyManager,
            CatalogRateLimitProperties properties) {
        this.proxyManager = catalogRedisProxyManager;
        this.configuration = CatalogBucketConfigurationFactory.bucketConfiguration(properties);
    }

    @Override
    public ConsumptionResult tryConsume(String clientKey) {
        Bucket bucket = proxyManager.builder().build(clientKey, () -> configuration);
        var probe = bucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            return ConsumptionResult.allowed();
        }
        long retryAfterSeconds = Math.max(1L, (probe.getNanosToWaitForRefill() + 999_999_999L) / 1_000_000_000L);
        return ConsumptionResult.denied(retryAfterSeconds);
    }
}
