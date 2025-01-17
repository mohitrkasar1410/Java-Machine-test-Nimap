//package com.sar.gateway.util;
//
//import io.jsonwebtoken.Jwts;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//@Component
//public class JwtUtil {
//
//    private final String JWT_SECRET = "c29tZSByYW5kb20gc3RyaW5nIHdoY2hlIGlzIGxvbmdlciB0aGFuIHRoZSBhdmVyYWdlIHZhbHVlcyBhbGxvd3Vz";
//
//    public void validateToken(String token) throws Exception {
//        try {
//            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
//        } catch (Exception ex) {
//            throw new RuntimeException("Invalid token", ex);
//        }
//    }
//}
//
