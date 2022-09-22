package com.sz.gateway.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

/**
 * jwt 工具类
 *
 * @author wangqianping
 * @date 2022-09-15
 */
public class JwtUtil {


    private final static String SECRET = "test_secret_at_20220915_by_wqp";


    public static void verifyToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
        } catch (SignatureVerificationException e) {
            //签名异常
            e.printStackTrace();
        } catch (TokenExpiredException e) {
            //令牌过期异常
            e.printStackTrace();
        } catch (AlgorithmMismatchException e) {
            //算法不一致异常
            e.printStackTrace();
        } catch (InvalidClaimException e) {
            //失效的payload异常
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
        }

    }


}
