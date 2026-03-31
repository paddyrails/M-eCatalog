package com.pp.cs.sales.catalog.ratelimit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.Refill;

import java.time.Duration;

public final class CatalogBucketConfigurationFactory {

    private CatalogBucketConfigurationFactory() {
    }

    public static Bandwidth bandwidth(CatalogRateLimitProperties props) {
        Refill refill = Refill.intervally(
                props.getReplenishPerPeriod(),
                Duration.ofSeconds(Math.max(1, props.getRefillPeriodSeconds())));
        return Bandwidth.classic(props.getBurstCapacity(), refill);
    }

    public static BucketConfiguration bucketConfiguration(CatalogRateLimitProperties props) {
        return BucketConfiguration.builder()
                .addLimit(bandwidth(props))
                .build();
    }
}
