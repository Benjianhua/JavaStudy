package com.spring.study.redis;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.sentinel.master}")
    String masterName;

    @Value("${spring.redis.sentinel.nodes}")
    String sentinelNodes;

    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public JedisPoolConfig getJedisConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        return config;
    }

    @Bean
    public JedisSentinelPool getJedisSentinelPool() {

        Set<String> nodes = new HashSet<>();

        Arrays.asList(sentinelNodes.split(",")).forEach(s -> {
            nodes.add(s);
        });

        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool(masterName, nodes, getJedisConfig(),"123456");

        return jedisSentinelPool;
    }

}
