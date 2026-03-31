package com.pp.cs.sales.catalog.ratelimit;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * Per-JVM token buckets (not shared across replicas).
 */
@Service
@ConditionalOnProperty(prefix = "catalog.rate-limit", name = "backend", havingValue = "local", matchIfMissing = true)
public class LocalCatalogRateLimiterService implements CatalogRateLimiterService {

    private final LoadingCache<String, Bucket> cache;

    public LocalCatalogRateLimiterService(CatalogRateLimitProperties properties) {
        Bandwidth limit = CatalogBucketConfigurationFactory.bandwidth(properties);
        this.cache = Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterAccess(Duration.ofHours(1))
                .build(key -> Bucket.builder()
                        .addLimit(limit)
                        .build());
    }

    @Override
    public ConsumptionResult tryConsume(String clientKey) {
        Bucket bucket = cache.get(clientKey);
        var probe = bucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            return ConsumptionResult.allowed();
        }
        long retryAfterSeconds = Math.max(1L, (probe.getNanosToWaitForRefill() + 999_999_999L) / 1_000_000_000L);
        return ConsumptionResult.denied(retryAfterSeconds);
    }
}
