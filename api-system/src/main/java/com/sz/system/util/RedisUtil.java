package com.sz.system.util;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author wangqianping
 * @date 2022-09-19
 */
@Component
public class RedisUtil {


    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void setStr(String key,String value){
        stringRedisTemplate.opsForValue().set(key,value);
    }

    public void setExpireStr(String key, String value, long expireTime, TimeUnit timeUnit){
        stringRedisTemplate.opsForValue().set(key,value,expireTime,timeUnit
        );
    }

    public void remove(String key){
        stringRedisTemplate.delete(key);
    }

}
