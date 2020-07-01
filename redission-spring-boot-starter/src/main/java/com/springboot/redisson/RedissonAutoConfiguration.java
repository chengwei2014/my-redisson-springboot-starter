package com.springboot.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redission自动装配
 * 2020-06-30
 */
@ConditionalOnClass(Redisson.class)
@EnableConfigurationProperties(RedissonProperties.class)
@Configuration
public class RedissonAutoConfiguration {
    @Bean
    public RedissonClient redissonClient(RedissonProperties redissonProperties){
        Config config = new Config();
        String prefix = "redis://";
        if (redissonProperties.isSsl()){
            prefix = "rediss://";
        }
        config.useSingleServer().setAddress(prefix + redissonProperties.getHost() + ":" + redissonProperties.getPort()).
                setConnectTimeout(redissonProperties.getTimeout());
        return Redisson.create(config);
    }
}
