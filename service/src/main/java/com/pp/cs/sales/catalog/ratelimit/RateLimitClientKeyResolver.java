package com.pp.cs.sales.catalog.ratelimit;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * Derives a stable rate-limit key per client (aligned with API gateway style).
 */
public final class RateLimitClientKeyResolver {

    private RateLimitClientKeyResolver() {
    }

    public static String resolve(HttpServletRequest request) {
        String apiKey = request.getHeader("X-Api-Key");
        if (StringUtils.hasText(apiKey)) {
            return "apikey:" + apiKey.trim();
        }
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authorization)) {
            String digest = DigestUtils.md5DigestAsHex(authorization.getBytes(StandardCharsets.UTF_8));
            return "auth:" + digest;
        }
        String xff = request.getHeader("X-Forwarded-For");
        if (StringUtils.hasText(xff)) {
            return "ip:" + xff.split(",")[0].trim();
        }
        if (StringUtils.hasText(request.getRemoteAddr())) {
            return "ip:" + request.getRemoteAddr();
        }
        return "anonymous";
    }
}
