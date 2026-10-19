package heima.item.filter;

import heima.item.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
@Slf4j
//@WebFilter(urlPatterns = "/*")
public class TokenFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response1=(HttpServletResponse) response;
        HttpServletRequest request1=(HttpServletRequest) request;
        String requestURI=request1.getRequestURI();
        if(requestURI.contains("/login")){
            log.info("登录请求，放行");
            chain.doFilter(request1,response1);
            return;
        }
        String token=request1.getHeader("token");
        if(token==null||token.isEmpty()){
            log.info("令牌为空，响应401");
            response1.setStatus(401);
            return;
        }
        try{
            JwtUtils.parseToken(token);
        } catch (Exception e) {
            log.info("令牌非法，响应401");
            response1.setStatus(401);
            return;
        }
        log.info("令牌合法，放行");
        chain.doFilter(request1,response1);
    }
}
