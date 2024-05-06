package com.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service

public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;
    private final RedisTemplate<String, String> redisBlackListTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate,
                        RedisTemplate<String, String> redisBlackListTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisBlackListTemplate = redisBlackListTemplate;
    }

    public void setValues(String key, String data) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, data);
    }

    public void setValues(String key, String data, Duration duration) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, data, duration);
    }

    public String getValues(String key) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(key);
    }

    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }

    public boolean hasValues(String key){
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void setBlackList(String key, String data) {
        redisBlackListTemplate.opsForSet().add(key, data);
    }


    public String getBlackList(String key){
        return redisBlackListTemplate.opsForValue().get(key);
    }

    public void deleteBlackList(String key){
        redisBlackListTemplate.delete(key);
    }

    public boolean hasBlackList(String key){
        return Boolean.TRUE.equals(redisBlackListTemplate.hasKey(key));
    }


}
