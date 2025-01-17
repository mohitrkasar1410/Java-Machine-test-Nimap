//package com.sar.gateway.filter;
//
//import com.sar.gateway.util.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.http.HttpHeaders;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
//
//    @Autowired
//    private RouteValidator validator;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    public AuthenticationFilter() {
//        super(Config.class);
//    }
//
//    @Override
//    public GatewayFilter apply(Config config) {
//        return ((exchange, chain) -> {
//            if(validator.isSecured.test(exchange.getRequest())){
//                ///header contains token or not
//                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
//                    throw new RuntimeException("Authorization header not present");
//                }
//
//                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
//                if(authHeader!=null && authHeader.startsWith("Bearer ")){
//                    authHeader= authHeader.substring(7);
//                }
//                try{
//                    //REST call to AUTH service
//                    jwtUtil.validateToken(authHeader);
//                }catch(Exception e){
//                    throw new RuntimeException("Invalid token");
//                }
//            }
//            return chain.filter(exchange);
//        });
//    }
//
//    public static class Config{
//
//    }
//}
