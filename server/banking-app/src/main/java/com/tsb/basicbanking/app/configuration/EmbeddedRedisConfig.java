package com.tsb.basicbanking.app.configuration;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import redis.embedded.RedisServer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddedRedisConfig {
    private RedisServer redisServer;

    @PostConstruct
    public void startRedis() throws Exception {
        // Start the embedded Redis server
        redisServer = new RedisServer(6380); // Use the default Redis port
        redisServer.start();
        System.out.println("Redis server started");
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }

    @Bean
    public RedisServer redisServer() {
        return redisServer;
    }
}
