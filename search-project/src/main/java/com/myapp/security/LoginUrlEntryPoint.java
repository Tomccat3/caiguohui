package com.myapp.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *      基于角色的登录入口控制器
 * @author cgh
 */
public class LoginUrlEntryPoint extends LoginUrlAuthenticationEntryPoint {

    private PathMatcher pathMatcher = new AntPathMatcher();
    private final Map<String, String> authEntryPointMap;

    public LoginUrlEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
        authEntryPointMap = new HashMap<>();

        //普通用户登录入口映射
        authEntryPointMap.put("/user/**", "/user/login");
        //管理员
        authEntryPointMap.put("/admin/**", "/admin/login");
    }

    @Override
    protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {

        String uri = request.getRequestURI().replace(request.getContextPath(), "");
        for (Map.Entry<String, String> entry : this.authEntryPointMap.entrySet()) {
            if (this.pathMatcher.match(entry.getKey(), uri)){
                return entry.getValue();
            }
        }
        return super.determineUrlToUseForThisRequest(request, response, exception);
    }
}
