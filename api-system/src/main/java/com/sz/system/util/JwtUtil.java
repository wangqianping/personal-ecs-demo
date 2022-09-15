package com.sz.system.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.sz.system.pojo.entity.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt 工具类
 *
 * @author wangqianping
 * @date 2022-09-15
 */
public class JwtUtil {


    private static final Integer EXPIRATION = 30 * 60;

    private static final String SECRET = "test_secret_at_20220915_by_wqp";

    /**
     * 生成用户token
     *
     * @return
     */
    public static String createToken(User user) {

        //过期时间
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRATION * 1000);

        //header
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        String token = JWT.create()
                .withHeader(header)
                .withClaim("id", user.getId())
                .withClaim("account", user.getAccount())
                .withClaim("name", user.getName())
                .withExpiresAt(expireDate)
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC256(SECRET));

        return token;
    }

    /**
     * 解析token
     */
    public static Map<String, Claim> verifyToken(String token) {
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

    /**
     * 根据token获取用户信息
     *
     * @param token
     * @return
     */
    public static User getUser(String token) {
        Map<String, Claim> stringClaimMap = verifyToken(token);
        User user = new User();
        user.setId(stringClaimMap.get("id").asInt());
        user.setAccount(stringClaimMap.get("account").asString());
        user.setName(stringClaimMap.get("name").asString());
        return user;
    }


}
