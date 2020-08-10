package com.spring.study;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.JedisSentinelPool;

@SpringBootTest
class StudyApplicationTests {

    @Autowired
    JedisSentinelPool jedisSentinelPool;

    @Test
    void contextLoads() {
        jedisSentinelPool.getResource();

    }

}
