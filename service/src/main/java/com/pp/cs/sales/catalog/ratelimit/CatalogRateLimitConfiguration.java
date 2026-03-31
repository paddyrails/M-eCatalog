package com.pp.cs.sales.catalog.ratelimit;

import io.github.bucket4j.distributed.ExpirationAfterWriteStrategy;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.time.Duration;

@Configuration
@EnableConfigurationProperties(CatalogRateLimitProperties.class)
public class CatalogRateLimitConfiguration {

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnProperty(prefix = "catalog.rate-limit", name = "backend", havingValue = "redis")
    public RedisClient catalogRateLimitRedisClient(
            @Value("${spring.data.redis.host:localhost}") String host,
            @Value("${spring.data.redis.port:6379}") int port,
            @Value("${spring.data.redis.password:}") String password) {
        RedisURI.Builder builder = RedisURI.builder().withHost(host).withPort(port);
        if (StringUtils.hasText(password)) {
            builder.withPassword(password.toCharArray());
        }
        return RedisClient.create(builder.build());
    }

    @Bean(destroyMethod = "close")
    @ConditionalOnProperty(prefix = "catalog.rate-limit", name = "backend", havingValue = "redis")
    public StatefulRedisConnection<String, byte[]> catalogRateLimitRedisConnection(RedisClient catalogRateLimitRedisClient) {
        return catalogRateLimitRedisClient.connect(
                RedisCodec.of(StringCodec.UTF8, ByteArrayCodec.INSTANCE));
    }

    @Bean
    @ConditionalOnProperty(prefix = "catalog.rate-limit", name = "backend", havingValue = "redis")
    public ProxyManager<String> catalogRedisProxyManager(
            StatefulRedisConnection<String, byte[]> catalogRateLimitRedisConnection) {
        return LettuceBasedProxyManager.builderFor(catalogRateLimitRedisConnection)
                .withExpirationStrategy(
                        ExpirationAfterWriteStrategy.basedOnTimeForRefillingBucketUpToMax(Duration.ofMinutes(10)))
                .build();
    }
}
