package org.shawn.intercptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.shawn.pojo.Result;
import org.shawn.utils.JwtUtil;
import org.shawn.utils.ThreadLocalUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //令牌验证
        //1.从请求中获取token

        String token = request.getHeader("Authorization");

        //2.验证token
        try {
            Map<String, Object> claims = JwtUtil.parseToken(token);
            //把token中的id，存放到ThreadLocal中
            ThreadLocalUtil.set(claims);
            //此时验证成功，就需要返回true，这就意味着拦截判断通过，可以放行
            return true;
        } catch (Exception e) {
            //此时http响应状态码为401
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}
