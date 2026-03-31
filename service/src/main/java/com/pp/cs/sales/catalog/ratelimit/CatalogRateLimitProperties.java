package com.pp.cs.sales.catalog.ratelimit;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "catalog.rate-limit")
public class CatalogRateLimitProperties {

    /**
     * When false, the filter is a no-op.
     */
    private boolean enabled = true;

    /**
     * {@code local}: in-process buckets (per JVM). {@code redis}: shared buckets via Redis (multiple replicas).
     */
    private String backend = "local";

    /**
     * Tokens added per refill interval (see {@link #refillPeriodSeconds}).
     */
    private int replenishPerPeriod = 20;

    /**
     * Length of each refill window in seconds.
     */
    private int refillPeriodSeconds = 1;

    /**
     * Maximum tokens in the bucket (burst).
     */
    private int burstCapacity = 40;

    /**
     * Request path prefixes excluded from rate limiting (Servlet path, starts with /).
     */
    private List<String> skipPathPrefixes = new ArrayList<>(List.of(
            "/actuator",
            "/swagger-ui",
            "/v3/api-docs",
            "/graphiql"
    ));

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getBackend() {
        return backend;
    }

    public void setBackend(String backend) {
        this.backend = backend;
    }

    public int getReplenishPerPeriod() {
        return replenishPerPeriod;
    }

    public void setReplenishPerPeriod(int replenishPerPeriod) {
        this.replenishPerPeriod = replenishPerPeriod;
    }

    public int getRefillPeriodSeconds() {
        return refillPeriodSeconds;
    }

    public void setRefillPeriodSeconds(int refillPeriodSeconds) {
        this.refillPeriodSeconds = refillPeriodSeconds;
    }

    public int getBurstCapacity() {
        return burstCapacity;
    }

    public void setBurstCapacity(int burstCapacity) {
        this.burstCapacity = burstCapacity;
    }

    public List<String> getSkipPathPrefixes() {
        return skipPathPrefixes;
    }

    public void setSkipPathPrefixes(List<String> skipPathPrefixes) {
        this.skipPathPrefixes = skipPathPrefixes;
    }
}
