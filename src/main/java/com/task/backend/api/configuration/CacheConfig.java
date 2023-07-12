package com.task.backend.api.configuration;

import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    private final long cacheTimeProductList;

    private final long cacheSizeProductList;

    private final long cacheTimeProduct;

    private final long cacheSizeProduct;

    private final long cacheTimeProductPrice;

    private final long cacheSizeProductPrice;

    public CacheConfig(
            @Value("${cache.product.list.time}") long cacheTimeProductList,
            @Value("${cache.product.list.size}") long cacheSizeProductList,
            @Value("${cache.product.specific.time}") long cacheTimeProduct,
            @Value("${cache.product.specific.size}") long cacheSizeProduct,
            @Value("${cache.product.price.time}") long cacheTimeProductPrice,
            @Value("${cache.product.price.size}") long cacheSizeProductPrice) {
        this.cacheTimeProductList = cacheTimeProductList;
        this.cacheSizeProductList = cacheSizeProductList;
        this.cacheTimeProduct = cacheTimeProduct;
        this.cacheSizeProduct = cacheSizeProduct;
        this.cacheTimeProductPrice = cacheTimeProductPrice;
        this.cacheSizeProductPrice = cacheSizeProductPrice;
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager() {
            @Override
            protected Cache createConcurrentMapCache(final String name) {
                switch (name) {
                    case "productListCache":
                        return new ConcurrentMapCache(name,
                                CacheBuilder.newBuilder()
                                            .expireAfterWrite(cacheTimeProductList, TimeUnit.HOURS)
                                            .maximumSize(cacheSizeProductList)
                                            .build()
                                            .asMap(), false
                        );
                    case "productCache":
                        return new ConcurrentMapCache(name,
                                CacheBuilder.newBuilder()
                                            .expireAfterWrite(cacheTimeProduct, TimeUnit.HOURS)
                                            .maximumSize(cacheSizeProduct)
                                            .build()
                                            .asMap(), false
                        );
                    case "productPriceCache":
                        return new ConcurrentMapCache(name,
                                CacheBuilder.newBuilder()
                                            .expireAfterWrite(cacheTimeProductPrice, TimeUnit.HOURS)
                                            .maximumSize(cacheSizeProductPrice)
                                            .build()
                                            .asMap(), false
                        );
                    default:
                        return new ConcurrentMapCache(name);
                }
            }
        };
    }

}
