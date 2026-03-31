package com.pp.cs.sales.catalog.ratelimit;

public final class ConsumptionResult {

    private final boolean allowed;
    private final long retryAfterSeconds;

    private ConsumptionResult(boolean allowed, long retryAfterSeconds) {
        this.allowed = allowed;
        this.retryAfterSeconds = retryAfterSeconds;
    }

    public static ConsumptionResult allowed() {
        return new ConsumptionResult(true, 0L);
    }

    public static ConsumptionResult denied(long retryAfterSeconds) {
        return new ConsumptionResult(false, retryAfterSeconds);
    }

    public boolean isAllowed() {
        return allowed;
    }

    public long getRetryAfterSeconds() {
        return retryAfterSeconds;
    }
}
