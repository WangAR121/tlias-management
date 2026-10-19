package heima.item.interceptor;

import heima.item.utils.CurrentHolder;
import heima.item.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
@Slf4j
@Component
public class tokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpServletResponse response1=(HttpServletResponse) response;
        HttpServletRequest request1=(HttpServletRequest) request;
        String requestURI=request1.getRequestURI();
        String token=request1.getHeader("token");
        if(token==null||token.isEmpty()){
            log.info("令牌为空，响应401");
            response1.setStatus(401);
            return false;
        }
        try{
            Claims claims =JwtUtils.parseToken(token);
            Integer empId=Integer.valueOf(claims.get("id").toString());
            CurrentHolder.setCurrentId(empId);
            log.info("当前登录员工id{},将其存入threadlocal",empId);
        } catch (Exception e) {
            log.info("令牌非法，响应401");
            response1.setStatus(401);
            return false;
        }
        log.info("令牌合法，放行");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        CurrentHolder.removeCurrentId();
    }
}
