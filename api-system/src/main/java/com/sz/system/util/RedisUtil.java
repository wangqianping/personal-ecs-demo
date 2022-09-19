package com.sz.system.util;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wangqianping
 * @date 2022-09-19
 */
@Component
public class RedisUtil {


    @Resource
    private StringRedisTemplate stringRedisTemplate;




}
