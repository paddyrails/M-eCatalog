package com.pp.cs.sales.catalog.ratelimit;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 50)
@ConditionalOnProperty(prefix = "catalog.rate-limit", name = "enabled", havingValue = "true", matchIfMissing = true)
public class CatalogRateLimiterFilter extends OncePerRequestFilter {

    private final CatalogRateLimitProperties properties;
    private final CatalogRateLimiterService rateLimiterService;

    public CatalogRateLimiterFilter(
            CatalogRateLimitProperties properties,
            CatalogRateLimiterService rateLimiterService) {
        this.properties = properties;
        this.rateLimiterService = rateLimiterService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        if (!properties.isEnabled()) {
            return true;
        }
        String path = request.getRequestURI();
        for (String prefix : properties.getSkipPathPrefixes()) {
            if (path.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String key = "catalog:" + RateLimitClientKeyResolver.resolve(request);
        ConsumptionResult result = rateLimiterService.tryConsume(key);
        if (result.isAllowed()) {
            filterChain.doFilter(request, response);
            return;
        }
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.setHeader("Retry-After", String.valueOf(result.getRetryAfterSeconds()));
        response.setContentType("application/json");
        response.getWriter().write(
                "{\"error\":\"rate_limit_exceeded\",\"message\":\"Too many requests\",\"retryAfterSeconds\":"
                        + result.getRetryAfterSeconds()
                        + "}");
    }
}
