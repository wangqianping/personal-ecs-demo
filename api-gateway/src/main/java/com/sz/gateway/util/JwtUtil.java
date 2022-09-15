package com.sz.gateway.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Map;

/**
 * jwt 工具类
 *
 * @author wangqianping
 * @date 2022-09-15
 */
public class JwtUtil {


    private final Integer EXPIRATION = 30 * 60;

    private final String SECRET = "test_secret_at_20220915_by_wqp";

    /**
     * 解析token
     */
    public Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            System.out.println("token解码异常");
            return null;
        }

        return jwt.getClaims();
    }

}
